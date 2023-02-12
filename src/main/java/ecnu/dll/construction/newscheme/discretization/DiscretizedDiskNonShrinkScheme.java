package ecnu.dll.construction.newscheme.discretization;

import cn.edu.ecnu.basic.BasicArray;
import cn.edu.ecnu.basic.RandomUtil;
import cn.edu.ecnu.collection.SetUtils;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction.newscheme.discretization.state.DiscretizedDiskPlaneState;
import ecnu.dll.construction.newscheme.discretization.tool.DiscretizedDiskSchemeTool;

import java.util.*;

@SuppressWarnings("ALL")
/**
 * 过中心点认为占全部，不过中心点认为不占。
 */
public class DiscretizedDiskNonShrinkScheme extends AbstractDiscretizedScheme {

    // 记录45度边的高概率范围占据的格子坐标在x轴上的投影
    private Integer upperIndex45 = null;
    // 记录实际的45度边长度在x轴上的投影减去0.5
    private Double index45Prime = null;
    // 记录45度边纯高概率部分占据的格子坐标在x轴上的投影
    private Integer lowerIndex45 = null;
    private Double edge45Area = null;



    // 记录0到45方向之间（不含边界）的内侧点坐标
    private List<TwoDimensionalIntegerPoint> innerCellIndexList = null;
    // 记录0和45方向之间（不含边界）的高低概率交界点(混合概率处)
    private List<TwoDimensionalIntegerPoint> highProbabilityBorderCellIndexList = null;
    // 记录中心点为最右上方点时，外侧点的坐标
    private List<TwoDimensionalIntegerPoint> outputBorderOuterCellList = null;

    // 记录坐标和45方向之间（不含边界）的最外侧点应该近似的面积大小
    private List<Double> highProbabilityBorderCellAreaSizeList = null;

    // 记录内部点、边界点、外部点的种类，用于transformMatrix的设置
    private Map<TwoDimensionalIntegerPoint, Integer> relative45State = null;

    /**
     *
     * 记录高概率部分的各项累积面积
     *      (1) 1
     *      (2) (1) + sizeB * 4
     *      (3) (2) + sizeB_{π/4} * 4
     *      (4) (3) + A_{π/4} * 4
     *      (5) (4) + A_{SI} * 8
     *      (6) (5) + Shrink(A_{SO}) * 8
     *
     *
     */
    private Double[] highSplitPartArray = null;
    /**
     *
     * 记录低概率部分各项累积面积
     *      (1) A_q
     *      (2) (1) + (1-A_{π/4}) * 4
     *      (3) (2) + (A_{SO} - Shrink(A_{SO})) * 8
     *
     */
    private Double[] lowSplitPartArray = null;


    public DiscretizedDiskNonShrinkScheme(Double epsilon, Double gridLength, Double constB, Double inputLength, Double kParameter, Double xLeft, Double yLeft) {
        super(epsilon, gridLength, constB, inputLength, kParameter, xLeft, yLeft);
//        this.setIndex45();
        this.setConstPQ();
        this.setRawIntegerPointTypeList();
        this.setNoiseIntegerPointTypeList();
        this.setTransformMatrix();
    }


    public DiscretizedDiskNonShrinkScheme(Double epsilon, Double gridLength, Double inputLength, Double kParameter, Double xLeft, Double yLeft) {
        super(epsilon, gridLength, inputLength, kParameter, xLeft, yLeft);
//        this.setIndex45();
        this.setConstPQ();
        this.setRawIntegerPointTypeList();
        this.setNoiseIntegerPointTypeList();
        this.setTransformMatrix();
    }

    @Override
    public void resetEpsilon(Double epsilon, boolean whetherResetSizeB) {
        this.epsilon = epsilon;
        if (whetherResetSizeB) {
            this.sizeB = this.getOptimalSizeB();
        }
        this.setConstPQ();
//        this.setRawIntegerPointTypeList();
        this.setNoiseIntegerPointTypeList();
        this.setTransformMatrix();
    }

    private void setIndex45() {
        this.index45Prime = this.sizeB / Math.sqrt(2) - 0.5;
        this.upperIndex45 = (int) Math.ceil(this.index45Prime);
        this.lowerIndex45 = (int) Math.floor(this.index45Prime);
        double differ = this.index45Prime - this.lowerIndex45;
        if (differ < 0.5) {
            //不过中心店，设置为0
            this.edge45Area = 0.0;
        } else {
            // 过中心点，设置为1
            this.edge45Area = 1.0;
        }
    }


    @Override
    public Integer getOptimalSizeB() {
        return DiscretizedDiskSchemeTool.getOptimalSizeBOfDiskScheme(this.epsilon, this.sizeD);
    }

    private void setHighProbabilityBorderCellIndexList() {
        this.highProbabilityBorderCellIndexList = DiscretizedDiskSchemeTool.calculateHighProbabilityBorderCellIndexList(this.sizeB);
    }


    // 需要先调用setHighProbabilityBorderCellIndexList
    private void setInnerCell() {
        this.innerCellIndexList = DiscretizedDiskSchemeTool.getInnerCell(this.highProbabilityBorderCellIndexList);
    }



    /**
     * 设置0到45之间（不含边界）的外边界经过shrink后的面积(只可能是0或者1)
     */
    private void setHighProbabilityBorderCellAreaSize() {
        this.highProbabilityBorderCellAreaSizeList = new ArrayList<>(this.highProbabilityBorderCellIndexList.size());
        int tempX, tempY;
        double delta, tempShrinkAreaSize;
        TwoDimensionalIntegerPoint tempPair;
        for (int i = 0; i < this.highProbabilityBorderCellIndexList.size(); i++) {
            tempPair = this.highProbabilityBorderCellIndexList.get(i);
            tempX = tempPair.getXIndex();
            tempY = tempPair.getYIndex();
            tempShrinkAreaSize = DiscretizedDiskSchemeTool.getShrinkAreaSizeConstrainInTwoValue(this.sizeB, tempX, tempY);
            this.highProbabilityBorderCellAreaSizeList.add(tempShrinkAreaSize);
        }
    }



    /**
     * 设置0到45之间（不含边界）的输出边界cell列表
     */
    private void setOutputBorderOuterCellList() {

        this.outputBorderOuterCellList = DiscretizedDiskSchemeTool.getOutputBorderOuterCellList(this.highProbabilityBorderCellIndexList, this.sizeD, this.sizeB);

    }


    /**
     * 设置constP和constQ
     * 同时设置 highSplitPartArray 和 lowSplitPartArray
     */
    @Override
    protected void setConstPQ() {
        double highParameter, lowParameter;
        double totalShrinkArea = 0;
        double originalLowAreaSize = this.sizeD * this.sizeD + 4 * this.sizeB * (this.sizeD - 1) - 1;

        //对outerCellIndex和outerCellAreaSizeList的设置
        setHighProbabilityBorderCellIndexList();
        setHighProbabilityBorderCellAreaSize();
        setInnerCell();

        for (int i = 0; i < this.highProbabilityBorderCellAreaSizeList.size(); i++) {
            totalShrinkArea += this.highProbabilityBorderCellAreaSizeList.get(i);
        }

        this.setIndex45();

        this.highSplitPartArray = new Double[6];
        this.lowSplitPartArray = new Double[3];

        // 设置高概率分段部分
        this.highSplitPartArray[0] = 1.0;
        this.highSplitPartArray[1] = this.highSplitPartArray[0] + this.sizeB * 4;
        this.highSplitPartArray[2] = this.highSplitPartArray[1] + this.lowerIndex45 * 4;
        this.highSplitPartArray[3] = this.highSplitPartArray[2] + this.edge45Area * 4;
        this.highSplitPartArray[4] = this.highSplitPartArray[3] + this.innerCellIndexList.size() * 8;
        highParameter = this.highSplitPartArray[5] = this.highSplitPartArray[4] + totalShrinkArea * 8;


        // 设置低概率分段部分
        this.lowSplitPartArray[0] = originalLowAreaSize;
        this.lowSplitPartArray[1] = this.lowSplitPartArray[0] + (1 - this.edge45Area) * 4;
        lowParameter = this.lowSplitPartArray[2] = this.lowSplitPartArray[1] + (this.highProbabilityBorderCellAreaSizeList.size() - totalShrinkArea) * 8;

        this.constQ = 1.0 / (highParameter * Math.exp(this.epsilon) + lowParameter);
        this.constP = this.constQ * Math.exp(this.epsilon);

    }

    /**
     *
     */
    private void setRelative45State() {
        this.relative45State = new HashMap<>();
        /*
            设置高概率坐标轴边界和低概率坐标轴边界
         */
        for (int i = 0; i <= this.sizeB; i++) {
            this.relative45State.put(new TwoDimensionalIntegerPoint(i, 0), DiscretizedDiskPlaneState.INNER_STATE);
        }
        for (int i = this.sizeB + 1; i <= this.sizeB + this.sizeD - 1; i++) {
            this.relative45State.put(new TwoDimensionalIntegerPoint(i, 0), DiscretizedDiskPlaneState.OUTER_STATE);
        }

        /*
            设置高概率45边界，混合45边界和低概率45边界
         */
        int index45OutputBorder = this.upperIndex45 + this.sizeD - 1;
        for (int i = 1; i <= this.lowerIndex45; i++) {
            this.relative45State.put(new TwoDimensionalIntegerPoint(i, i), DiscretizedDiskPlaneState.INNER_STATE);
        }
        if (this.edge45Area > 0) {
            this.relative45State.put(new TwoDimensionalIntegerPoint(this.upperIndex45, this.upperIndex45), DiscretizedDiskPlaneState.EDGE45_State);
        } else {
            this.relative45State.put(new TwoDimensionalIntegerPoint(this.upperIndex45, this.upperIndex45), DiscretizedDiskPlaneState.OUTER_STATE);
        }
        for (int i = this.upperIndex45 + 1; i <= index45OutputBorder; i++) {
            this.relative45State.put(new TwoDimensionalIntegerPoint(i, i), DiscretizedDiskPlaneState.OUTER_STATE);
        }


        /*
            设置高概率边界集合状态和两侧(高概率和低概率)的状态
         */
        TwoDimensionalIntegerPoint tempPair;
        int tempX, tempY, borderX;
        int k;
        this.setOutputBorderOuterCellList();
        for (k = 0; k < this.highProbabilityBorderCellIndexList.size(); k++) {
            tempPair = this.highProbabilityBorderCellIndexList.get(k);
            tempX = tempPair.getXIndex();
            tempY = tempPair.getYIndex();
            this.relative45State.put(new TwoDimensionalIntegerPoint(tempX, tempY), DiscretizedDiskPlaneState.EDGE_STATE);
            for (int i = tempY + 1; i < tempX; i++) {
                this.relative45State.put(new TwoDimensionalIntegerPoint(i, tempY), DiscretizedDiskPlaneState.INNER_STATE);
            }
            borderX = this.outputBorderOuterCellList.get(k).getXIndex();
            for (int i = tempX + 1; i <= borderX; i++) {
                this.relative45State.put(new TwoDimensionalIntegerPoint(i, tempY), DiscretizedDiskPlaneState.OUTER_STATE);
            }
        }
        // 剩余低概率部分
        for (; k < this.outputBorderOuterCellList.size(); k++) {
            tempPair = this.outputBorderOuterCellList.get(k);
            borderX = tempPair.getXIndex();
            tempY = tempPair.getYIndex();
            for (int i = tempY + 1; i <= borderX; i++) {
                this.relative45State.put(new TwoDimensionalIntegerPoint(i, tempY), DiscretizedDiskPlaneState.OUTER_STATE);
            }
        }

    }



    @Override
    public void setNoiseIntegerPointTypeList() {

        this.noiseIntegerPointTypeList = DiscretizedDiskSchemeTool.getNoiseIntegerPointTypeList(this.highProbabilityBorderCellIndexList, this.sizeD, this.sizeB, this.upperIndex45);
    }

    @Override
    public void setTransformMatrix() {
        int outputSize = this.noiseIntegerPointTypeList.size();
        int inputSize = this.rawIntegerPointTypeList.size();
        this.transformMatrix = new Double[outputSize][inputSize];
        int differDistance;
        TwoDimensionalIntegerPoint tempOutputPoint, tempInputPoint, tempRelativePoint;
        int tempOutputElementX, tempOutputElementY, tempInputElementX, tempInputElementY;
        int relativeX, relativeY;
        Integer tempState;
        Double tempShrankArea;
        this.setRelative45State();
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
                tempState = this.relative45State.get(tempRelativePoint);

                //todo: 处理tempState为null的情况
                if (tempState.equals(DiscretizedDiskPlaneState.INNER_STATE)) {
                    this.transformMatrix[j][i] = this.constP;
                } else if (tempState.equals(DiscretizedDiskPlaneState.OUTER_STATE)) {
                    this.transformMatrix[j][i] = this.constQ;
                } else if(tempState.equals(DiscretizedDiskPlaneState.EDGE45_State)) {
                    this.transformMatrix[j][i] = this.constP * this.edge45Area + this.constQ * (1 - this.edge45Area);
                } else {
                    // outercell的是按照y坐标排列的，第一个坐标为1，往后依次增加1.
                    tempShrankArea = this.highProbabilityBorderCellAreaSizeList.get(relativeY - 1);
                    this.transformMatrix[j][i] = this.constP * tempShrankArea + this.constQ * (1 - tempShrankArea);
                }
            }
        }
    }

    @Override
    public TwoDimensionalIntegerPoint getNoiseValue(TwoDimensionalIntegerPoint originalPoint) {
        /**
         * 1. 确定高概率部分还是低概率部分
         */
        double splitPointA = this.constP * this.highSplitPartArray[this.highSplitPartArray.length-1];
        Double randomA = RandomUtil.getRandomDouble(0.0, 1.0);
        Integer tempIndex;
        Integer tempX = null, tempY = null, shiftIndex, originalX, originalY;
        originalX = originalPoint.getXIndex();
        originalY = originalPoint.getYIndex();
        if (randomA < splitPointA) {
            // 高概率部分
            tempIndex = RandomUtil.getRandomIndexGivenCumulativeCountPoint(this.highSplitPartArray);
            if (tempIndex.equals(0)) {
                // 返回本身
                return originalPoint;
            }
            Integer randomInteger = RandomUtil.getRandomInteger(0, 3);
            TwoDimensionalIntegerPoint chosenPair = null;
            Integer judge45 = null;
            switch (tempIndex) {

                // 随机返回四段坐标轴上的cell
                case 1:
                    tempX = tempY = 0;
                    Integer chosenIndex = RandomUtil.getRandomInteger(1, this.sizeB);
                    switch (randomInteger) {
                        case 0: tempX = chosenIndex; break;
                        case 1: tempX = chosenIndex * -1; break;
                        case 2: tempY = chosenIndex; break;
                        case 3: tempY = chosenIndex * -1; break;
                    }
//                    return new TwoDimensionalIntegerPoint(orignialX + tempX, originalY + tempY);
                    break;

                // 随机返回四段45方向的cell
                case 2:
                    Integer choseIndex = RandomUtil.getRandomInteger(1, this.lowerIndex45);
                    tempX = tempY = choseIndex;
                    switch (randomInteger) {
                        case 1: tempX *= -1; break;
                        case 2: tempY *= -1; break;
                        case 3: tempX *= -1; tempY *= -1; break;
                    }
//                    return new TwoDimensionalIntegerPoint(orignialX + tempX, originalY + tempY);
                    break;

                // 返回 45边界点
                case 3:
                    tempX = tempY = this.upperIndex45;
                    switch (randomInteger) {
                        case 1: tempX *= -1; break;
                        case 2: tempY *= -1; break;
                        case 3: tempX *= -1; tempY *= -1; break;
                    }
                    break;

                // 随机返回内部cell
                case 4:
                    chosenPair = RandomUtil.getRandomElement(this.innerCellIndexList);
                    tempX = chosenPair.getXIndex();
                    tempY = chosenPair.getYIndex();
                    judge45 = RandomUtil.getRandomInteger(0, 1);
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

//                    return new TwoDimensionalIntegerPoint(orignialX + tempX, originalY + tempY);
                    break;

                // 随机返回外边界cell
                case 5:
                    Integer randomIndex = RandomUtil.getRandomIndexGivenCountPoint(this.highProbabilityBorderCellAreaSizeList);
                    chosenPair = this.highProbabilityBorderCellIndexList.get(randomIndex);
                    tempX = chosenPair.getXIndex();
                    tempY = chosenPair.getYIndex();
                    judge45 = RandomUtil.getRandomInteger(0, 1);
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
                    break;
            }
            return new TwoDimensionalIntegerPoint(originalX + tempX, originalY + tempY);
        } else {
            // 低概率部分
            Integer randomIndex = null, judge45 = null;
            TwoDimensionalIntegerPoint chosenPair = null;
            tempIndex = RandomUtil.getRandomIndexGivenCumulativeCountPoint(this.lowSplitPartArray);
            Integer randomInteger = RandomUtil.getRandomInteger(0, 3);
            switch (tempIndex) {
                // 随机返回外部节点
                case 0:
                    TreeSet<TwoDimensionalIntegerPoint> pureLowCellsByPointSet = DiscretizedDiskSchemeTool.getResidualPureLowCellsByPoint(originalPoint, this.noiseIntegerPointTypeList, this.innerCellIndexList, this.highProbabilityBorderCellIndexList, this.sizeB, this.upperIndex45);
                    int pureLowCellSize = pureLowCellsByPointSet.size();
                    randomIndex = RandomUtil.getRandomInteger(0, pureLowCellSize - 1);
                    TwoDimensionalIntegerPoint chosenPoint = SetUtils.getElementByIndex(pureLowCellsByPointSet, randomIndex);
                    return chosenPoint;
//                    break;
                case 1:
                    tempX = tempY = this.upperIndex45;

                    switch (randomInteger) {
                        case 1: tempX *= -1; break;
                        case 2: tempY *= -1; break;
                        case 3: tempX *= -1; tempY *= -1; break;
                    }
                    return new TwoDimensionalIntegerPoint(originalX + tempX, originalY + tempY);
//                    break;

                // 随机返回外边界cell
                case 2:
                    Double[] residualOuterAreaSizeArray = BasicArray.getLinearTransform(this.highProbabilityBorderCellAreaSizeList, -1, 1);
                    randomIndex = RandomUtil.getRandomIndexGivenCountPoint(residualOuterAreaSizeArray);
                    chosenPair = this.highProbabilityBorderCellIndexList.get(randomIndex);
                    tempX = chosenPair.getXIndex();
                    tempY = chosenPair.getYIndex();
                    judge45 = RandomUtil.getRandomInteger(0, 1);
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
                    return new TwoDimensionalIntegerPoint(originalX + tempX, originalY + tempY);
//                    break;
            }
        }
        return null;
    }
}
