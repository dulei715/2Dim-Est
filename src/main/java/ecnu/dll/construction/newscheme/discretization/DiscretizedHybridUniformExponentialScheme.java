package ecnu.dll.construction.newscheme.discretization;


import cn.edu.ecnu.basic.BasicArray;
import cn.edu.ecnu.basic.RandomUtil;
import cn.edu.ecnu.collection.SetUtils;
import cn.edu.ecnu.struct.pair.IdentityPair;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction.newscheme.discretization.struct.Annular;
import ecnu.dll.construction.newscheme.discretization.struct.AnnularIndex;
import ecnu.dll.construction.newscheme.discretization.struct.MultipleRelativeElement;
import ecnu.dll.construction.newscheme.discretization.tool.DiscretizedDiskSchemeTool;
import ecnu.dll.construction.newscheme.discretization.tool.DiscretizedHybridUniformExponentialSchemeTool;

import java.util.*;

@SuppressWarnings("Duplicates")
public class DiscretizedHybridUniformExponentialScheme extends AbstractDiscretizedScheme {

    // 记录45度边的高概率范围占据的格子坐标在x轴上的投影
    private List<Integer> upperIndex45List = null;
    // 记录实际的45度边长度在x轴上的投影减去0.5
    private List<Double> index45PrimeList = null;

    private Integer upperIndex45;
    private List<IdentityPair<Integer>> innerCellIndexList = null;
    private List<IdentityPair<Integer>> outerCellIndexList = null;
    private List<Double> outerCellAreaSizeList = null;
    /**
     * 记录每个annular的面积权重，按照距离指数衰减
     */
    private List<Double> annularAreaWeight = null;





    // 记录中间annular信息
    private List<Annular> annularAreaList = null;
    // 记录45方向annular信息
    private List<Annular> annularLineList = null;
    // 记录中心点为最右上方点时，外侧点的坐标
    private List<TwoDimensionalIntegerPoint> outputBorderOuterCellList = null;

    private TreeSet<TwoDimensionalIntegerPoint> outerCellSet = null;

    /**
     * 记录环形衰减部分
     *      一维记录的是环形累积总面积
     *      二维记录的是记录如下分类的累积量
     *          (1) 4部分坐标轴
     *          (2) 4部分45度边
     *          (3) 8部分中间cell
     *
     */
    private MultipleRelativeElement<Double> annularPart = null;

    /**
     *
     * 记录低概率部分各项累积面积
     *      (1) 纯低概率部分 A_q
     *      (2) (1) + (1-A_{π/4}) * 4，其中 A_{π/4} 是sizeB半径在45方向所交的cell的shrank面积
     *      (3) (2) + (A_{SO} - Shrink(A_{SO})) * 8，其中A_{SO}是sizeB半径的边缘cell面积和
     *
     */
    private Double[] lowSplitPartArray = null;



    public DiscretizedHybridUniformExponentialScheme(Double epsilon, Double gridLength, Double constB, Double inputLength, Double kParameter, Double xLeft, Double yLeft) {
        super(epsilon, gridLength, constB, inputLength, kParameter, xLeft, yLeft);
        if (this.sizeB < 1) {
            throw new RuntimeException("The value of sizeB is less than 1 (not support!) !");
        }
        Double tempWeight;
        this.annularAreaWeight = new ArrayList<>(this.sizeB);
        // 这里优化了，直接从0开始计数
        for (int tempB = 0; tempB < this.sizeB; tempB++) {
            tempWeight = Math.exp((1-tempB*1.0/this.sizeB));
            this.annularAreaWeight.add(tempWeight);
        }
        this.initialize();
    }


    public void setAnnularAreaListElement(Integer sizeB, Annular element) {
        this.annularAreaList.set(sizeB - 2, element);
    }
    public Annular getAnnularAreaListElement(Integer sizeB) {
        return this.annularAreaList.get(sizeB - 2);
    }
    public Annular getLastAnnularAreaListElement() {
        return this.annularAreaList.get(this.annularAreaList.size()-1);
    }
    public void setAnnularLineListElement(Integer sizeB, Annular element) {
        this.annularLineList.set(sizeB - 1, element);
    }
    public Annular getAnnularLineListElement(Integer sizeB) {
        return this.annularLineList.get(sizeB - 1);
    }
    public Annular getLastAnnularLineListElement() {
        return this.annularLineList.get(this.annularLineList.size()-1);
    }
    public Double getAnnularWeight(Integer sizeB) {
        if (sizeB > this.sizeB) {
            return 1.0;
        }
        return this.annularAreaWeight.get(sizeB - 1);
    }

    /**
     * 初始化基本参数
     */
    public void initialize() {
        this.setRawIntegerPointTypeList();
        // 1. 设置内部annular相关信息和非低概率最大边界信息(位置和shrank面积)
        this.setAnnularListAndOuterCell();

        // 2. 初始化非低概率的最远45方向cell
        this.upperIndex45 = (int) Math.ceil(this.sizeB / Math.sqrt(2) - 0.5);

        // 3. 初始化内部cell列表
        this.innerCellIndexList = DiscretizedDiskSchemeTool.getInnerCell(this.outerCellIndexList);

        this.setConstPQ();
        this.setNoiseIntegerPointTypeList();
        this.setTransformMatrix();

    }

    public AnnularIndex getAnnularIndex(TwoDimensionalIntegerPoint relativePoint) {
        Integer xIndex = relativePoint.getXIndex();
        Integer yIndex = relativePoint.getYIndex();
        // 处理纯低概率的情况（该cell的左下角边界点到(0,0)cell的距离在最大sizeB之外）
        Double leftBoundAngleLength = Math.sqrt((xIndex-0.5)*(xIndex-0.5) + (yIndex-0.5) * (yIndex-0.5));
        if (leftBoundAngleLength >= this.sizeB) {
            return new AnnularIndex(-1, 1.0);
        }
        if (yIndex.equals(0)) {
            // 坐标轴情况
            if (xIndex.equals(0)) {
                xIndex = 1;
            }
            return new AnnularIndex(xIndex, 1.0);
        }
        if (xIndex.equals(yIndex)) {
            // 45方向情况
            Double sqrt2 = Math.sqrt(2);
            Double b45 = sqrt2 * xIndex;
            Integer leftSizeB = (int) Math.floor(b45), rightSizeB = (int) Math.ceil(b45);
            if (leftSizeB > leftBoundAngleLength) {
                // 说明 leftSizeB 与该cell相交且为涵盖该cell的中心点 （那rightSizeB一定涵盖了该中心点[不论rightSizeB是否与该cell相交]）
                Double areaSize = this.getAnnularLineListElement(leftSizeB).getPartAreaMap().get(relativePoint);
                return new AnnularIndex(leftSizeB, areaSize);
            }
            // else:说明rightSizeB必然和该cell相交，且涵盖其中心点
            return new AnnularIndex(rightSizeB, 1.0);
        }
        Double bAngle = Math.sqrt(xIndex * xIndex + yIndex * yIndex);
        // 左下角边界到(0,0)cell的距离
//        Double leftBoundAngle = Math.sqrt((xIndex-0.5)*(xIndex-0.5) + (yIndex-0.5) * (yIndex-0.5));
        Integer leftSizeB = (int) Math.floor(bAngle), rightSizeB = (int) Math.ceil(bAngle);
        if (leftSizeB > leftBoundAngleLength) {
            // 可以证明 当leftSizeB与该cell相交且涵盖该cell的中心点时，rightB一定覆盖了该中心点
            Double areaSize = this.getAnnularAreaListElement(leftSizeB).getPartAreaMap().get(relativePoint);
            return new AnnularIndex(leftSizeB, areaSize);
        }
        // else:可以证明，当leftSizeB与该cell不相交时，rightSizeB必然与该cell相交，且涵盖其中心点
        return new AnnularIndex(rightSizeB, 1.0);
    }



    /**
     * 设置(0,π/4) 内 Annular list
     */
    private void setAnnularListAndOuterCell() {
        this.annularAreaList = new ArrayList<>();
        this.annularLineList = new ArrayList<>();

        if (this.sizeB <= 0) {
            return;
        }
        TwoDimensionalIntegerPoint tempPoint;
        Annular areaAnnular = null, lineAnnular;
        TreeMap<TwoDimensionalIntegerPoint, Double> tempTreeMap;
        int tempB = 1;
        List<IdentityPair<Integer>> leftAreaOuterPointList, rightAreaOuterPointList;
        IdentityPair<Integer> leftLineOuterPoint, rightLineOuterPoint;
        leftAreaOuterPointList = new ArrayList<>();
        leftLineOuterPoint = DiscretizedDiskSchemeTool.calculate45EdgeIndex(tempB);
//        this.setAnnularLineListElement(tempB, Annular.getDefaultLineAnnular());
        this.annularLineList.add(Annular.getDefaultLineAnnular());
        for (tempB = 2; tempB <= this.sizeB; tempB++) {
            rightAreaOuterPointList = DiscretizedDiskSchemeTool.calculateOuterCellIndexList(tempB);
            areaAnnular = DiscretizedHybridUniformExponentialSchemeTool.getInnerAreaAnnular(leftAreaOuterPointList, rightAreaOuterPointList, tempB - 1, tempB);
//            this.setAnnularAreaListElement(tempB, areaAnnular);
            this.annularAreaList.add(areaAnnular);
            leftAreaOuterPointList = rightAreaOuterPointList;

            rightLineOuterPoint = DiscretizedDiskSchemeTool.calculate45EdgeIndex(tempB);
            lineAnnular = DiscretizedHybridUniformExponentialSchemeTool.get45LinearAnnular(leftLineOuterPoint, rightLineOuterPoint, tempB - 1, tempB);
//            this.setAnnularLineListElement(tempB, lineAnnular);
            this.annularLineList.add(lineAnnular);
            leftLineOuterPoint = rightLineOuterPoint;
        }
        this.outerCellIndexList = leftAreaOuterPointList;
        if (areaAnnular != null) {
            tempTreeMap = areaAnnular.getPartAreaMap();
            this.outerCellAreaSizeList = new ArrayList<>(this.outerCellIndexList.size());
            for (IdentityPair<Integer> pointPair : this.outerCellIndexList) {

                this.outerCellAreaSizeList.add(tempTreeMap.get(new TwoDimensionalIntegerPoint(pointPair)));
            }
        }
    }

    /**
     * 设置最大可能概率p和最小可能概率q，以及设置annularList每一项annular的totalProbability
     */
    @Override
    protected void setConstPQ() {
        /**
         *
         *  1. 统计整个加权面积
         *      (1) 坐标轴方环形、外面积
         *      (2) 45方向环形、外面积
         *      (3) 中间部分 annular 和外面积
         *  2. 计算最大可能概率为 p ，计算最小可能概率为 q
         *  3. 设置p和q
         *
         */
        this.annularPart = new MultipleRelativeElement<>();
        List<MultipleRelativeElement<Double>> multipleRelativeElementList = new ArrayList<>();
        List<MultipleRelativeElement<Double>> tempElementList;

        double index45Area, totalLowAreaSize;
        Integer tempB;
        double accumulatedWeightedAreaSize = 0, tempValue, tempWeight;
        Annular tempInnerAnnular, temp45Annular, tempAnnular;
        double originalLowAreaSize = this.sizeD * this.sizeD + 4 * this.sizeB * (this.sizeD - 1) - 1;
//        this.setAnnularListAndOuterCell();
        /*
            1. 统计整个加权面积
                针对每个annular包含：(1) 4个坐标轴；(2)4个45边；(3) 8部分内部cell
         */
        //
        // 遍历整个非低概率面积并记录累计函数
        // (1) sizeB = 1
        tempB = 1;
        tempWeight = this.getAnnularWeight(tempB);
        index45Area = DiscretizedDiskSchemeTool.get45EdgeIndexSplitArea(tempB);
        tempElementList = new ArrayList<>();

        accumulatedWeightedAreaSize += 1.0 * 4 * tempWeight;    // 坐标轴方向
        tempElementList.add(new MultipleRelativeElement<>(accumulatedWeightedAreaSize));
        accumulatedWeightedAreaSize += index45Area * 4 * tempWeight;    // 45 方向
        tempElementList.add(new MultipleRelativeElement<>(accumulatedWeightedAreaSize));
        accumulatedWeightedAreaSize += 1.0 * tempWeight;        // 中心点
        tempElementList.add(new MultipleRelativeElement<>(accumulatedWeightedAreaSize));
        multipleRelativeElementList.add(new MultipleRelativeElement<>(accumulatedWeightedAreaSize, tempElementList));
        // (2) sizeB >= 2: 首先添加
        for (tempB = 2; tempB <= this.sizeB; tempB++) {
            tempWeight = this.getAnnularWeight(tempB);
            tempValue = 0;
            tempElementList = new ArrayList<>();

            tempValue += 1.0 * 4 * tempWeight;
            tempElementList.add(new MultipleRelativeElement<>(tempValue));

            temp45Annular = this.getAnnularLineListElement(tempB);
            tempValue += temp45Annular.getTotalAreaSize() * 4 * tempWeight;
            tempElementList.add(new MultipleRelativeElement<>(tempValue));

            tempInnerAnnular = this.getAnnularAreaListElement(tempB);
            tempValue += tempInnerAnnular.getTotalAreaSize() * 8 * tempWeight;
            tempElementList.add(new MultipleRelativeElement<>(tempValue));

            accumulatedWeightedAreaSize += tempValue;
            multipleRelativeElementList.add(new MultipleRelativeElement<>(accumulatedWeightedAreaSize, tempElementList));

        }

        this.annularPart.setValue(accumulatedWeightedAreaSize);
        this.annularPart.setRelativeElementList(multipleRelativeElementList);


        /*
            计算q和最大可能p
         */
        // 1. 获取纯低概率面积+边缘剩余面积 （他们的返回概率都是q）
        Double index45RemainAreaSize = 1 - DiscretizedDiskSchemeTool.get45EdgeIndexSplitArea(this.sizeB);
        Double outerRemainAreaSize;
        if (this.sizeB < 2) {
            outerRemainAreaSize = 0.0;
        } else {
            tempAnnular = this.getAnnularAreaListElement(this.sizeB);
            outerRemainAreaSize = tempAnnular.getPartAreaMap().size() - tempAnnular.getTotalPartAreaSize();
        }
        this.lowSplitPartArray = new Double[3];
        this.lowSplitPartArray[0] = originalLowAreaSize;
        this.lowSplitPartArray[1] = this.lowSplitPartArray[0] + index45RemainAreaSize * 4;
        totalLowAreaSize = this.lowSplitPartArray[2] = this.lowSplitPartArray[1] + outerRemainAreaSize * 8;

        Double totalWeightedAreaSize = accumulatedWeightedAreaSize + totalLowAreaSize;
        this.constQ = 1 / totalWeightedAreaSize;
        this.constP = this.constQ * Math.exp(this.epsilon);

    }

    @Deprecated
    @Override
    public Integer getOptimalSizeB() {
        return null;
    }

    @Override
    public void setNoiseIntegerPointTypeList() {
        Set<TwoDimensionalIntegerPoint> outerCellIndexSet;
        Collection<IdentityPair<Integer>> outerCellIndexCollection;
        Integer upperIndex45;
//        Integer upperIndex45 = this.getLastAnnularLineListElement().getPartAreaMap().lastKey().getXIndex();
        TreeMap<TwoDimensionalIntegerPoint, Double> tempMap = this.getLastAnnularLineListElement().getPartAreaMap();
        if (tempMap.size() > 0) {
            upperIndex45 = tempMap.lastKey().getXIndex();
        } else {
            upperIndex45 = this.getLastAnnularLineListElement().getRemainAreaMap().lastKey().getXIndex();
        }
        if (this.sizeB < 2) {
            outerCellIndexCollection = new ArrayList<>();
            this.noiseIntegerPointTypeList = DiscretizedDiskSchemeTool.getNoiseIntegerPointTypeList(outerCellIndexCollection, this.sizeD, this.sizeB, upperIndex45);
            return;
        }
        outerCellIndexSet = this.getLastAnnularAreaListElement().getPartAreaMap().keySet();
        outerCellIndexCollection = TwoDimensionalIntegerPoint.toIdentityPair(outerCellIndexSet);
        this.noiseIntegerPointTypeList = DiscretizedDiskSchemeTool.getNoiseIntegerPointTypeList(outerCellIndexCollection, this.sizeD, this.sizeB, upperIndex45);
    }

    @Override
    public void setTransformMatrix() {
        int outputSize = this.noiseIntegerPointTypeList.size();
        int inputSize = this.rawIntegerPointTypeList.size();
        this.transformMatrix = new Double[outputSize][inputSize];
        Integer tempSizeB;
        Double tempShrankArea;
        TwoDimensionalIntegerPoint tempOutputPoint, tempInputPoint, tempRelativePoint;
        int tempOutputElementX, tempOutputElementY, tempInputElementX, tempInputElementY;
        int relativeX, relativeY;
        AnnularIndex tempAnnularIndex;
        for (int j = 0; j < outputSize; j++) {
            tempOutputPoint = this.noiseIntegerPointTypeList.get(j);
            tempOutputElementX = tempOutputPoint.getXIndex();
            tempOutputElementY = tempOutputPoint.getYIndex();
            for (int i = 0; i < inputSize; i++) {
                tempInputPoint = this.rawIntegerPointTypeList.get(i);
                tempInputElementX = tempInputPoint.getXIndex();
                tempInputElementY = tempInputPoint.getYIndex();

                // 将相对位置调整到[0,π/4]范围内
                relativeX = Math.abs(tempOutputElementX - tempInputElementX);
                relativeY = Math.abs(tempOutputElementY - tempInputElementY);
                if (relativeX < relativeY) {
                    int shift = relativeX;
                    relativeX = relativeY;
                    relativeY = shift;
                }
                tempRelativePoint = new TwoDimensionalIntegerPoint(relativeX, relativeY);
                tempAnnularIndex = this.getAnnularIndex(tempRelativePoint);
                tempSizeB = tempAnnularIndex.getPartSizeB();
                if (tempSizeB < 0) {
                    // 纯低概率情况
                    this.transformMatrix[j][i] = this.constQ;
                } else {
                    tempShrankArea = tempAnnularIndex.getPartAreaSize();
                    this.transformMatrix[j][i] = this.constQ * (this.getAnnularWeight(tempSizeB) * tempShrankArea + this.getAnnularWeight(tempSizeB+1) * (1-tempShrankArea));
                }
            }
        }
    }

    @Override
    public TwoDimensionalIntegerPoint getNoiseValue(TwoDimensionalIntegerPoint originalPoint) {
        /**
         * 1. 确定是低概率部分还是其他部分
         */
        double splitPointA = this.constQ * this.lowSplitPartArray[this.lowSplitPartArray.length-1];
        Double randomA = RandomUtil.getRandomDouble(0.0, 1.0);
        Integer tempIndex, randomInteger;
        Integer tempX = null, tempY = null, shiftIndex, originalX, originalY;
        originalX = originalPoint.getXIndex();
        originalY = originalPoint.getYIndex();
        if (randomA < splitPointA) {
            // 纯低概率部分
            Integer randomIndex = null, judge45 = null;
            IdentityPair<Integer> chosenPair = null;
            tempIndex = RandomUtil.getRandomIndexGivenCumulativeCountPoint(this.lowSplitPartArray);
            switch (tempIndex) {
                // 随机返回外部节点
                case 0:
                    TreeSet<TwoDimensionalIntegerPoint> pureLowCellsByPointSet = DiscretizedDiskSchemeTool.getResidualPureLowCellsByPoint(originalPoint, this.noiseIntegerPointTypeList, this.innerCellIndexList, this.outerCellIndexList, this.sizeB, this.upperIndex45);
                    int pureLowCellSize = pureLowCellsByPointSet.size();
                    randomIndex = RandomUtil.getRandomInteger(0, pureLowCellSize - 1);
                    TwoDimensionalIntegerPoint chosenPoint = SetUtils.getElementByIndex(pureLowCellsByPointSet, randomIndex);
                    return chosenPoint;
//                    break;
                case 1:
                    tempX = tempY = this.upperIndex45;
                    randomInteger = RandomUtil.getRandomInteger(0, 3);
                    switch (randomInteger) {
                        case 1: tempX *= -1; break;
                        case 2: tempY *= -1; break;
                        case 3: tempX *= -1; tempY *= -1; break;
                    }
                    return new TwoDimensionalIntegerPoint(originalX + tempX, originalY + tempY);
//                    break;

                // 随机返回外边界cell
                case 2:
                    Double[] residualOuterAreaSizeArray = BasicArray.getLinearTransform(this.outerCellAreaSizeList, -1, 1);
                    randomIndex = RandomUtil.getRandomIndexGivenCountPoint(residualOuterAreaSizeArray);
                    chosenPair = this.outerCellIndexList.get(randomIndex);
                    tempX = chosenPair.getKey();
                    tempY = chosenPair.getValue();
                    judge45 = RandomUtil.getRandomInteger(0, 1);
                    if (judge45 == 1) {
                        shiftIndex = tempX;
                        tempX = tempY;
                        tempY = shiftIndex;
                    }
                    randomInteger = RandomUtil.getRandomInteger(0, 3);
                    switch (randomInteger) {
                        case 1: tempX *= -1; break;
                        case 2: tempY *= -1; break;
                        case 3: tempX *= -1; tempY *= -1; break;
                    }
                    return new TwoDimensionalIntegerPoint(originalX + tempX, originalY + tempY);
//                    break;
            }
        } else {
            // Annular部分
            /*
                1. 选出annular部分
             */
            Double[] accumulatedWeightedAnnularArray = this.annularPart.getRelativeElementValueArray();
            Integer chosenAnnularIndex = RandomUtil.getRandomIndexGivenCumulativeCountPoint(accumulatedWeightedAnnularArray);
            Integer chosenB = chosenAnnularIndex + 1;
            Integer chosenPosition, chosenIndex;
            List<TwoDimensionalIntegerPoint> candidatePointList;
            /*
                2. 选出是坐标轴方向，45方向，还是其他方向
             */
            MultipleRelativeElement<Double> chosenAnnularElement = this.annularPart.getRelativeElementList().get(chosenAnnularIndex);
            Double[] accumulatedWeightedPartArray = chosenAnnularElement.getRelativeElementValueArray();
            Integer chosenPartIndex = RandomUtil.getRandomIndexGivenCumulativeCountPoint(accumulatedWeightedPartArray);
            randomInteger = RandomUtil.getRandomInteger(0, 3);
            switch (chosenPartIndex) {
                // 返回坐标轴的其中之一， 基本坐标轴cell坐标就是(chosenB, 0)
                case 0:
                    tempX = tempY = 0;
                    switch (randomInteger) {
                        case 0: tempX = chosenB; break;
                        case 1: tempX = chosenB * -1; break;
                        case 2: tempY = chosenB; break;
                        case 3: tempY = chosenB * -1; break;
                    }
                    break;
                // 返回45方向其中之一
                case 1:
                    Annular chosenLineAnnular = this.getAnnularLineListElement(chosenB);
                    candidatePointList = chosenLineAnnular.getRelatedPointList();
                    Double[] lineAreaArray = chosenLineAnnular.getRelatedPointAreaList().toArray(new Double[0]);
                    chosenPosition = RandomUtil.getRandomIndexGivenCountPoint(lineAreaArray);
                    chosenIndex = candidatePointList.get(chosenPosition).getXIndex(); // 此处xIndex和yIndex一样
                    tempX = tempY = chosenIndex;
                    switch (randomInteger) {
                        case 1: tempX *= -1; break;
                        case 2: tempY *= -1; break;
                        case 3: tempX *= -1; tempY *= -1; break;
                    }
                    break;
                // 如果chosenAnnularIndex是第一个，则返回原始点，否则返回中间部分
                case 2:
                    if (chosenAnnularIndex == 0) {
                        // 返回原始点
                        tempX = tempY = 0;
                    } else {
                        // 返回中间部分点
                        Annular chosenAreaAnnular = this.getAnnularAreaListElement(chosenB);
                        candidatePointList = chosenAreaAnnular.getRelatedPointList();
                        Double[] areaAreaArray = chosenAreaAnnular.getRelatedPointAreaList().toArray(new Double[0]);
                        chosenPosition = RandomUtil.getRandomIndexGivenCountPoint(areaAreaArray);
                        TwoDimensionalIntegerPoint chosenPoint = candidatePointList.get(chosenPosition);
                        tempX = chosenPoint.getXIndex();
                        tempY = chosenPoint.getYIndex();
                        Integer judge45 = RandomUtil.getRandomInteger(0, 1);
                        if (judge45 == 1) {
                            shiftIndex = tempX;
                            tempX = tempY;
                            tempY = shiftIndex;
                        }
                        switch (randomInteger) {
                            case 1: tempX *= -1; break;
                            case 2: tempY *= -1; break;
                            case 3: tempX *= -1; tempY*= -1; break;
                        }
                    }
                    break;
            }
            return new TwoDimensionalIntegerPoint(originalX + tempX, originalY + tempY);
        }

        return null;
    }
}
