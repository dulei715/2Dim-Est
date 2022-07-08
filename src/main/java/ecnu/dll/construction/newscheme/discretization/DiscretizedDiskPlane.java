package ecnu.dll.construction.newscheme.discretization;

import cn.edu.ecnu.basic.BasicArray;
import cn.edu.ecnu.basic.RandomUtil;
import cn.edu.ecnu.collection.SetUtils;
import cn.edu.ecnu.struct.BasicPair;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction.newscheme.discretization.state.DiscretizedDiskPlaneState;
import javafx.util.Pair;

import java.util.*;

@SuppressWarnings("ALL")
public class DiscretizedDiskPlane extends AbstractDiscretizedPlane {

    private Integer index45;
    // 记录内部cell个数
    @Deprecated
    private Integer innerCellSize = null;
    // 记录坐标方向和45方向的cell个数
    private Integer edgeCellSize = null;
    // 记录0到45方向之间（不含边界）的内侧点坐标
    private List<BasicPair<Integer, Integer>> innerCellIndexList = null;
    // 记录0和45方向之间（不含边界）的最外侧点坐标
    private List<BasicPair<Integer, Integer>> outerCellIndexList = null;
    // 记录
    private List<BasicPair<Integer, Integer>> outputBorderOuterCellList = null;

    // 记录坐标和45方向之间（不含边界）的最外侧点应该近似的面积大小
    private List<Double> outerCellAreaSizeList = null;

    // 记录内部点、边界点、外部点的种类，用于transformMatrix的设置
    private Map<TwoDimensionalIntegerPoint, Integer> relative45State = null;

    /**
     * 记录高概率部分的各项累积面积
     *      (1) A_{SI} * 8
     *      (2) (1) + sizeB*4
     *      (3) (2) + sizeB_{π/4}*4
     *      (4) (3) + 1
     *      (5) (4) + Shrink(A_{SO})
     */
    private Double[] highSplitPartArray = new Double[5];
    /**
     * 记录低概率部分各项累积面积
     *      (1) A_q
     *      (2) (1) + A_{SO} - Shrink(A_{SO})
     */
    private Double[] lowSplitPartArray = new Double[2];


    public DiscretizedDiskPlane(Double epsilon, Double gridLength, Double constB, Double inputLength) {
        super(epsilon, gridLength, constB, inputLength);
        this.setIndex45();
    }

    public DiscretizedDiskPlane(Double epsilon, Double gridLength, Double inputLength) {
        super(epsilon, gridLength, inputLength);
        this.setIndex45();
    }

    private void setIndex45() {
        this.index45 = (int) Math.ceil(this.sizeB / Math.sqrt(2) - 0.5);
    }

    /**
     * 根据给定的原始cell，返回整个输出空间中除去高概率部分和中间概率部分的纯低概率的cell的有序集合
     * @param realPoint
     * @return
     */
    private TreeSet<TwoDimensionalIntegerPoint> getResidualPureLowCellsByPoint(TwoDimensionalIntegerPoint realPoint) {
        Integer realPointX = realPoint.getXIndex();
        Integer realPointY = realPoint.getYIndex();

        /*
            1. 构建集合包含：2π范围内部点，四段高概率坐标轴(含原始点)，四段高概率45轴，四部分外边界点
         */
        List<TwoDimensionalIntegerPoint> list = new ArrayList<>();
        Pair<Integer, Integer> tempPair;
        Integer tempX, tempY;
        // (1) 2π范围内部点
        for (int i = 0; i < this.innerCellIndexList.size(); i++) {
            tempPair = this.innerCellIndexList.get(i);
            tempX = tempPair.getKey();
            tempY = tempPair.getValue();
            list.add(new TwoDimensionalIntegerPoint(realPointX+tempX, realPointY+tempY));
            list.add(new TwoDimensionalIntegerPoint(realPointX+tempY, realPointY+tempX));
            list.add(new TwoDimensionalIntegerPoint(realPointX-tempX, realPointY+tempY));
            list.add(new TwoDimensionalIntegerPoint(realPointX-tempY, realPointY+tempX));
            list.add(new TwoDimensionalIntegerPoint(realPointX-tempX, realPointY-tempY));
            list.add(new TwoDimensionalIntegerPoint(realPointX-tempY, realPointY-tempX));
            list.add(new TwoDimensionalIntegerPoint(realPointX+tempX, realPointY-tempY));
            list.add(new TwoDimensionalIntegerPoint(realPointX+tempY, realPointY-tempX));
        }
        // 四段高概率坐标轴(含原始点)
        list.add(realPoint);
        for (int i = 1; i <= this.sizeB; i++) {
            list.add(new TwoDimensionalIntegerPoint(realPointX+i, realPointY));
            list.add(new TwoDimensionalIntegerPoint(realPointX-i, realPointY));
            list.add(new TwoDimensionalIntegerPoint(realPointX, realPointY+i));
            list.add(new TwoDimensionalIntegerPoint(realPointX, realPointY-i));
        }
        // 四段高概率45轴
        for (int i = 1; i <= this.index45; i++) {
            list.add(new TwoDimensionalIntegerPoint(realPointX+i, realPointY+i));
            list.add(new TwoDimensionalIntegerPoint(realPointX-i, realPointY+i));
            list.add(new TwoDimensionalIntegerPoint(realPointX+i, realPointY-i));
            list.add(new TwoDimensionalIntegerPoint(realPointX-i, realPointY-i));
        }
        // 四部分外边界点
        for (int i = 0; i < this.outerCellIndexList.size(); i++) {
            tempPair = this.outerCellIndexList.get(i);
            tempX = tempPair.getKey();
            tempY = tempPair.getValue();
            list.add(new TwoDimensionalIntegerPoint(realPointX+tempX, realPointY+tempY));
            list.add(new TwoDimensionalIntegerPoint(realPointX+tempY, realPointY+tempX));
            list.add(new TwoDimensionalIntegerPoint(realPointX-tempX, realPointY+tempY));
            list.add(new TwoDimensionalIntegerPoint(realPointX-tempY, realPointY+tempX));
            list.add(new TwoDimensionalIntegerPoint(realPointX-tempX, realPointY-tempY));
            list.add(new TwoDimensionalIntegerPoint(realPointX-tempY, realPointY-tempX));
            list.add(new TwoDimensionalIntegerPoint(realPointX+tempX, realPointY-tempY));
            list.add(new TwoDimensionalIntegerPoint(realPointX+tempY, realPointY-tempX));
        }

        /*
            2. 从noise列表中除去上面集合的点
         */
        return SetUtils.getResidualOrderedElement(this.noiseIntegerPointTypeList, list);
    }

    @Override
    protected Integer getOptimalSizeB() {
        double mA = Math.exp(this.epsilon) - 1 - this.epsilon;
        double mB = 1 - (1 - this.epsilon) * Math.exp(this.epsilon);
        return (int)Math.ceil((2*mB+Math.sqrt(4*mB*mB+Math.PI*Math.exp(epsilon)*mA*mB))/(Math.PI*Math.exp(epsilon)*mA) * this.sizeD);
    }

    private void setOuterCellIndex() {
        double sqrt2 = Math.sqrt(2);
        double tempDiff = this.sizeB / sqrt2 - 0.5;
        double r_1 = Math.floor(tempDiff) * sqrt2 + 1/sqrt2;
        double r = Math.sqrt(r_1 * (r_1  - sqrt2) + 1);
        int outerCellSize = (int)Math.ceil(tempDiff) - (int)Math.floor(r / this.sizeB);
        this.outerCellIndexList = new ArrayList<>(outerCellSize);
        int xIndexTemp;
        for (int i = 0; i < outerCellSize; i++) {
            xIndexTemp = (int) Math.ceil(Math.sqrt(this.sizeB * this.sizeB - Math.pow(i-0.5,2))-0.5);
            this.outerCellIndexList.add(new BasicPair<>(xIndexTemp, i));
        }
    }



    // 需要先调用setOuterCellIndex
    @Deprecated
    private void setInnerCellSize() {
        int tempSize = this.index45 * (this.index45 - 2 * this.outerCellIndexList.size() - 1) / 2;
        for (int i = 0; i < this.outerCellIndexList.size(); i++) {
            // 加上每个外部cell对应的x坐标值
            tempSize += this.outerCellIndexList.get(i).getKey();
        }
        this.innerCellSize = tempSize;
    }

    private void setInnerCell() {
        BasicPair<Integer, Integer> tempPair;
        Integer tempX, tempY;
        TreeSet<BasicPair<Integer, Integer>> treeSet = new TreeSet<>();
        for (int k = 0; k < this.outerCellIndexList.size(); k++) {
            tempPair = this.outerCellIndexList.get(k);
            tempX = tempPair.getKey();
            tempY = tempPair.getValue();
            for (int i = k + 2; i < tempX; i++) {
                treeSet.add(new BasicPair<>(i, tempY));
            }
        }
        this.innerCellIndexList = new ArrayList<>(treeSet);
    }

    /**
     * 设置0到45之间（不含边界）的外边界经过shrink后的面积
     */
    private void setOuterCellAreaSize() {
        this.outerCellAreaSizeList = new ArrayList<>(this.outerCellIndexList.size());
        int tempX, tempY;
        double delta, tempShrinkAreaSize;
        Pair<Integer, Integer> tempPair;
        for (int i = 0; i < this.outerCellIndexList.size(); i++) {
            tempPair = this.outerCellIndexList.get(i);
            tempX = tempPair.getKey();
            tempY = tempPair.getValue();
            delta = this.sizeB * 1.0 / (tempX * tempX + tempY * tempY) - 1;
            tempShrinkAreaSize = 4 * (delta * tempX + 0.5) * (delta * tempY + 0.5);
            this.outerCellAreaSizeList.add(tempShrinkAreaSize);
        }
    }

    /**
     * 设置0到45之间（不含边界）的输出边界cell列表
     */
    private void setOutputBorderOuterCellList() {

        this.outputBorderOuterCellList = new ArrayList<>();
        int tempX, tempY, borderX;
        Pair<Integer, Integer> tempPair;
        for (int i = 1; i < this.sizeD; i++) {
            this.outputBorderOuterCellList.add(new BasicPair<>(this.sizeD + this.sizeB - 1, i));
        }
        for (int i = 0; i < this.outerCellIndexList.size(); i++) {
            tempPair = this.outerCellIndexList.get(i);
            this.outputBorderOuterCellList.add(new BasicPair<>(tempPair.getKey(), tempPair.getValue() + this.sizeD - 1));
        }
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
        for (int i = 0; i < this.outerCellAreaSizeList.size(); i++) {
            totalShrinkArea += this.outerCellAreaSizeList.get(i);
        }

//        highParameter = 8 * this.innerCellSize + 4 * (this.sizeB + Math.ceil(this.sizeB / Math.sqrt(2)-0.5)) + 1 + totalShrinkArea;
        highSplitPartArray[0] = 8.0 * this.innerCellSize;
        highSplitPartArray[1] = highSplitPartArray[0] + 4 * this.sizeB;
        highSplitPartArray[2] = highSplitPartArray[1] + 4 * Math.ceil(this.sizeB / Math.sqrt(2)-0.5);
        highSplitPartArray[3] = highSplitPartArray[2] + 1;
        highParameter = highSplitPartArray[4] = highSplitPartArray[3] + totalShrinkArea;

//        lowParameter = originalLowAreaSize + this.outerCellAreaSizeList.size() - totalShrinkArea;
        lowSplitPartArray[0] = originalLowAreaSize;
        lowParameter = lowSplitPartArray[1] = lowSplitPartArray[0] + this.outerCellAreaSizeList.size() - totalShrinkArea;
        this.constQ = 1.0 / (highParameter * Math.exp(this.epsilon) + lowParameter);
        this.constP = this.constQ * Math.exp(this.epsilon);
    }

    /**
     *
     */
    private void setRelative45State() {
        this.relative45State = new HashMap<>();
        /*
            设置高概率坐标轴边界和45边界状态
         */
        for (int i = 0; i <= this.sizeB; i++) {
            this.relative45State.put(new TwoDimensionalIntegerPoint(i, 0), DiscretizedDiskPlaneState.INNER_STATE);
        }
        for (int i = this.sizeB + 1; i <= this.sizeB + this.sizeD - 1; i++) {
            this.relative45State.put(new TwoDimensionalIntegerPoint(i, 0), DiscretizedDiskPlaneState.OUTER_STATE);
        }
        int index45OutputBorder = this.index45 + this.sizeD - 1;
        for (int i = 1; i <= this.index45; i++) {
            this.relative45State.put(new TwoDimensionalIntegerPoint(i, i), DiscretizedDiskPlaneState.INNER_STATE);
        }
        for (int i = this.index45 + 1; i <= index45OutputBorder; i++) {
            this.relative45State.put(new TwoDimensionalIntegerPoint(i, i), DiscretizedDiskPlaneState.OUTER_STATE);
        }


        /*
            设置高概率边界集合状态和两侧(高概率和低概率)的状态
         */
        Pair<Integer, Integer> tempPair;
        int tempX, tempY, borderX;
        int k;
        for (k = 0; k < this.outerCellIndexList.size(); k++) {
            tempPair = this.outerCellIndexList.get(k);
            tempX = tempPair.getKey();
            tempY = tempPair.getValue();
            this.relative45State.put(new TwoDimensionalIntegerPoint(tempX, tempY), DiscretizedDiskPlaneState.OUTER_STATE);
            for (int i = tempY + 1; i < tempX; i++) {
                this.relative45State.put(new TwoDimensionalIntegerPoint(i, tempY), DiscretizedDiskPlaneState.INNER_STATE);
            }
            borderX = this.outputBorderOuterCellList.get(k).getKey();
            for (int i = tempX + 1; i <= borderX; i++) {
                this.relative45State.put(new TwoDimensionalIntegerPoint(i, tempY), DiscretizedDiskPlaneState.OUTER_STATE);
            }
        }
        for (; k < this.outputBorderOuterCellList.size(); k++) {
            tempPair = this.outputBorderOuterCellList.get(k);
            borderX = tempPair.getKey();
            tempY = tempPair.getValue();
            for (int i = tempY + 1; i <= borderX; i++) {
                this.relative45State.put(new TwoDimensionalIntegerPoint(i, tempY), DiscretizedDiskPlaneState.OUTER_STATE);
            }
        }

    }

    @Override
    public void setNoiseIntegerPointTypeList() {
        TreeSet<TwoDimensionalIntegerPoint> treeSet = new TreeSet<>();
        /*
            添加原始cell和其经过右移、上移sizeD个cell所覆盖的cell （含坐标方向）
         */
        int moveDistance, upperIndex;
        moveDistance = upperIndex = this.sizeB + this.sizeD;
        for (int i = -this.sizeB; i < 0; i++) {
            for (int j = 0; j < this.sizeD; j++) {
                treeSet.add(new TwoDimensionalIntegerPoint(i, j));
                treeSet.add(new TwoDimensionalIntegerPoint(i + moveDistance, j));
            }
        }
        for (int i = 0; i < this.sizeD; i++) {
            for (int j = -this.sizeB; j < upperIndex; j++) {
                treeSet.add(new TwoDimensionalIntegerPoint(i, j));
            }
        }


        /*
            添加被打散为四部分的高概率cell (含45方向)
         */
//        int index45 = (int) Math.ceil(this.sizeB / Math.sqrt(2)-0.5);
        // 处理45方向
        for (int i = 1; i <= this.index45; i++) {
            treeSet.add(new TwoDimensionalIntegerPoint(-i, -i));
            treeSet.add(new TwoDimensionalIntegerPoint(-i, i + this.sizeD - 1));
            treeSet.add(new TwoDimensionalIntegerPoint(i + this.sizeD - 1, -i));
            treeSet.add(new TwoDimensionalIntegerPoint(i + this.sizeD - 1, i + this.sizeD - 1));
        }
        // 处理其他部分
        Pair<Integer, Integer> tempPair;
        int tempX, j;
        for (int k = 0; k < this.outerCellIndexList.size(); k++) {
            tempPair = this.outerCellIndexList.get(k);
            j = tempPair.getValue();
            tempX = tempPair.getKey();
            for (int i = j + 1; i <= tempX; i++) {
                treeSet.add(new TwoDimensionalIntegerPoint(-i, -j));
                treeSet.add(new TwoDimensionalIntegerPoint(-j, -i));
                treeSet.add(new TwoDimensionalIntegerPoint(-i, j + this.sizeD - 1));
                treeSet.add(new TwoDimensionalIntegerPoint(-j, i + this.sizeD - 1));
                treeSet.add(new TwoDimensionalIntegerPoint(i + this.sizeD - 1, -j));
                treeSet.add(new TwoDimensionalIntegerPoint(j + this.sizeD - 1, -i));
                treeSet.add(new TwoDimensionalIntegerPoint(i + this.sizeD - 1, j + this.sizeD - 1));
                treeSet.add(new TwoDimensionalIntegerPoint(j + this.sizeD - 1, i + this.sizeD - 1));
            }

        }
        this.noiseIntegerPointTypeList = new ArrayList<>(treeSet);
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
                if (tempState.equals(DiscretizedDiskPlaneState.INNER_STATE)) {
                    this.transformMatrix[j][i] = this.constP;
                } else if (tempState.equals(DiscretizedDiskPlaneState.OUTER_STATE)) {
                    this.transformMatrix[j][i] = this.constQ;
                } else {
                    // outercell的是按照y坐标排列的，第一个坐标为1，往后依次增加1.
                    tempShrankArea = this.outerCellAreaSizeList.get(relativeY - 1);
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
        double splitPointA = this.constQ * this.highSplitPartArray[this.highSplitPartArray.length-1] * Math.exp(this.epsilon);
        Double randomA = RandomUtil.getRandomDouble(0.0, 1.0);
        Integer tempIndex;
        Integer tempX = null, tempY = null, shiftIndex, orignialX, originalY;
        orignialX = originalPoint.getXIndex();
        originalY = originalPoint.getYIndex();
        if (randomA < splitPointA) {
            // 高概率部分
            tempIndex = RandomUtil.getRandomIndexGivenCumulativeCountPoint(this.highSplitPartArray);
            if (tempIndex.equals(3)) {
                // 返回本身
                return originalPoint;
            }
            Integer randomInteger = RandomUtil.getRandomInteger(0, 3);
            BasicPair<Integer,Integer> chosenPair = null;
            Integer judge45 = null;
            switch (tempIndex) {
                // 随机返回内部cell
                case 0:
                    chosenPair = RandomUtil.getRandomElement(this.innerCellIndexList);
                    tempX = chosenPair.getKey();
                    tempY = chosenPair.getValue();
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
                    Integer choseIndex = RandomUtil.getRandomInteger(1, this.index45);
                    tempX = tempY = choseIndex;
                    switch (randomInteger) {
                        case 1: tempX *= -1; break;
                        case 2: tempY *= -1; break;
                        case 3: tempX *= -1; tempY *= -1; break;
                    }
//                    return new TwoDimensionalIntegerPoint(orignialX + tempX, originalY + tempY);
                    break;

                // 随机返回外边界cell
                case 4:
                    Integer randomIndex = RandomUtil.getRandomIndexGivenCountPoint(this.outerCellAreaSizeList);
                    chosenPair = this.outerCellIndexList.get(randomIndex);
                    tempX = chosenPair.getKey();
                    tempY = chosenPair.getValue();
                    judge45 = RandomUtil.getRandomInteger(0, 1);
                    if (judge45 == 1) {
                        shiftIndex = tempX;
                        tempX = tempY;
                        tempY = shiftIndex;
                    }
                    break;
            }
            return new TwoDimensionalIntegerPoint(orignialX + tempX, originalY + tempY);
        } else {
            // 低概率部分
            // todo:
            Integer randomIndex = null, judge45 = null;
            BasicPair<Integer, Integer> chosenPair = null;
            tempIndex = RandomUtil.getRandomIndexGivenCumulativeCountPoint(this.lowSplitPartArray);
            switch (tempIndex) {
                // 随机返回外部节点
                case 0:
                    TreeSet<TwoDimensionalIntegerPoint> pureLowCellsByPointSet = getResidualPureLowCellsByPoint(originalPoint);
                    int pureLowCellSize = pureLowCellsByPointSet.size();
                    randomIndex = RandomUtil.getRandomInteger(0, pureLowCellSize - 1);
                    TwoDimensionalIntegerPoint chosenPoint = SetUtils.getElementByIndex(pureLowCellsByPointSet, randomIndex);
                    return chosenPoint;
//                    break;
                // 随机返回外边界cell
                case 1:
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
                    return new TwoDimensionalIntegerPoint(orignialX + tempX, originalY + tempY);
            }
        }
        return null;
    }
}
