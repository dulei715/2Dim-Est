package ecnu.dll.construction.newscheme.discretization;


import cn.edu.ecnu.basic.RandomUtil;
import cn.edu.ecnu.io.write.PointWrite;
import cn.edu.ecnu.statistic.StatisticTool;
import cn.edu.ecnu.struct.Grid;
import cn.edu.ecnu.struct.point.TwoDimensionalDoublePoint;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction.newscheme.basic.DiscretizedPlaneInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class DiscretizedRhombusPlane implements DiscretizedPlaneInterface {
    protected Double epsilon = null;
    // 记录grid边长的大小
    protected Double gridLength = null;
    // 记录输入的正方形边长除以grid边长的大小
    protected Integer sizeInputLen = null;
    protected Integer sizeB = null;
    // 记录输入长度除以grid长度的大小
    protected Integer sizeD = null;
    protected Double constP = null;
    protected Double constQ = null;

    protected Double[][] transformMatrix;

    private List<TwoDimensionalIntegerPoint> rawIntegerPointTypeList = null;
    private List<TwoDimensionalIntegerPoint> noiseIntegerPointTypeList = null;

    /**
     * 1. 高概率部分 2b^2+2b+1
     * 2. 低概率部分 d^2+4db-4b-1
     * 3. 1和2的总和 2b^2-2b+4db+d^2
     * 4. 总面积中大矩形部分 2b^2+3db-2b+d^2-d
     * 5. 总面积中小矩形部分 d+db
     */
    private Integer[] constValues = new Integer[5];

    public DiscretizedRhombusPlane(Double epsilon, Double gridLength, Double constB, Double inputLength) {
        this.epsilon = epsilon;
        this.gridLength = gridLength;
        //todo: 假设向上取整
        this.sizeB = (int)Math.ceil(constB / gridLength);
        //todo: 假设向上取整
        this.sizeD = (int)Math.ceil(inputLength / gridLength);
        this.constValues[0] = 2*this.sizeB*(this.sizeB+1)+1;
        this.constValues[1] = this.sizeD * (this.sizeD + 4*this.sizeB) - 4*this.sizeB - 1;
        this.constValues[2] = this.constValues[0] + this.constValues[1];
        this.constValues[4] = this.sizeD * (1 + this.sizeB);
        this.constValues[3] = this.constValues[2] - this.constValues[4];

        this.constQ = 1 / (this.constValues[0] * Math.exp(epsilon) + this.constValues[1]);
        this.constP = this.constQ * Math.exp(epsilon);
        this.rawIntegerPointTypeList = getRawIntegerPointTypeList(this.sizeD);
        this.noiseIntegerPointTypeList = getNoiseIntegerPointTypeList(this.sizeD, this.sizeB);
        this.setTransformMatrix();
    }

    public DiscretizedRhombusPlane(Double epsilon, Double gridLength, Double inputLength) {
        this.epsilon = epsilon;
        this.gridLength = gridLength;
        //todo: 假设向上取整
        double mA = Math.exp(epsilon) - 1 - epsilon;
        double mB = 1 - (1 - epsilon) * Math.exp(epsilon);
        this.sizeB = (int)Math.ceil((2*mB+Math.sqrt(4*mB*mB+2*Math.exp(epsilon)*mA*mB))/(2*Math.exp(epsilon)*mA));
        //todo: 假设向上取整
        this.sizeD = (int)Math.ceil(inputLength / gridLength);
        this.constValues[0] = 2*this.sizeB*(this.sizeB+1)+1;
        this.constValues[1] = this.sizeD * (this.sizeD + 4*this.sizeB) - 4*this.sizeB - 1;
        this.constValues[2] = this.constValues[0] + this.constValues[1];
        this.constValues[4] = this.sizeD * (1 + this.sizeB);
        this.constValues[3] = this.constValues[2] - this.constValues[4];

        this.constQ = 1 / (this.constValues[0] * Math.exp(epsilon) + this.constValues[1]);
        this.constP = this.constQ * Math.exp(epsilon);
        this.rawIntegerPointTypeList = getRawIntegerPointTypeList(this.sizeD);
        this.noiseIntegerPointTypeList = getNoiseIntegerPointTypeList(this.sizeD, this.sizeB);
        this.setTransformMatrix();
    }

    public static List<TwoDimensionalIntegerPoint> getRawIntegerPointTypeList(Integer sizeD) {
        int  totalSize = sizeD * sizeD;
        List<TwoDimensionalIntegerPoint> resultList = new ArrayList<>(totalSize);
        for (int i = 0; i < sizeD; i++) {
            for (int j = 0; j < sizeD; j++) {
                resultList.add(new TwoDimensionalIntegerPoint(i, j));
            }
        }
        return resultList;
    }

    public static List<TwoDimensionalIntegerPoint> getNoiseIntegerPointTypeList(Integer sizeD, Integer sizeB) {
        Integer positiveBound = sizeD + sizeB;
        // 记录右上边界线在x轴和y轴的截距
        Integer positiveIntercept = sizeB + 2 * sizeD - 2;
        // 记录左上和右下边界线分别在x轴和y轴上的截距(的绝对值)
        Integer negativeIntercept = sizeB + sizeD - 1;

        List<TwoDimensionalIntegerPoint> result = new ArrayList<>();
        for (int i = -sizeB; i < positiveBound; i++) {
            for (int j = -sizeB; j < positiveBound; j++) {
                if (i + j + sizeB >= 0 && i + j - positiveIntercept <= 0 && i - j + negativeIntercept >= 0 && i - j - negativeIntercept <= 0) {
                    result.add(new TwoDimensionalIntegerPoint(i, j));
                }
            }
        }
        return result;
    }

    public void setTransformMatrix() {
        int outputSize = this.noiseIntegerPointTypeList.size();
        int inputSize = this.rawIntegerPointTypeList.size();
        this.transformMatrix = new Double[outputSize][inputSize];
        int differDistance;
        TwoDimensionalIntegerPoint tempOutputPoint, tempInputPoint;
        int tempOutputElementX, tempOutputElementY, tempInputElementX, tempInputElementY;
        for (int j = 0; j < outputSize; j++) {
            tempOutputPoint = this.noiseIntegerPointTypeList.get(j);
            tempOutputElementX = tempOutputPoint.getXIndex();
            tempOutputElementY = tempOutputPoint.getYIndex();
            for (int i = 0; i < inputSize; i++) {
                tempInputPoint = this.rawIntegerPointTypeList.get(i);
                tempInputElementX = tempInputPoint.getXIndex();
                tempInputElementY = tempInputPoint.getYIndex();
                differDistance = Math.abs(tempOutputElementX - tempInputElementX) + Math.abs(tempOutputElementY - tempInputElementY);
                if (differDistance <= this.sizeB) {
                    this.transformMatrix[j][i] = this.constP;
                } else {
                    this.transformMatrix[j][i] = this.constQ;
                }
            }
        }
    }




    @Override
    public TwoDimensionalIntegerPoint getNoiseValue(TwoDimensionalIntegerPoint originalPoint) {
//        Integer[] indexes = Grid.toGridIndex(this.gridLength, originalPoint.getValueArray());
        int[] indexes = originalPoint.getIndex();
        if (indexes[0] < 0 || indexes[0] > this.sizeD || indexes[1] < 0 || indexes[1] > this.sizeD) {
            throw new RuntimeException("The real value is out of domain!");
        }
        // 分割两个概率
        double containLowProbability = this.constValues[2] * this.constQ;
        double tempRandomA = Math.random(), tempRandomB = Math.random();
        int xOutput, yOutput;
        if (tempRandomA < containLowProbability) {
            // 含低概率部分:对应面积为 2b^2-2b+4bd+d^2
            // 再次分割为占比 (2b^2+3bd-2b-d+d^2):(d+d*b)
            double smallRectangleRatio = this.constValues[4] * 1.0 / this.constValues[2];
            //todo: 修改两个为离散的
            if (tempRandomB < smallRectangleRatio) {
                // 小矩形部分: x随机返回[0,d-1]; y随机返回[-b-1,-1],其中-b-1部分对应着b+d-1部分
                xOutput = RandomUtil.getRandomInteger(0, this.sizeD - 1);
                yOutput = RandomUtil.getRandomInteger(-this.sizeB-1, -1);
                if (yOutput < -this.sizeB) {
                    yOutput += (2*this.sizeB + this.sizeD);
                }
            } else {
                // 大矩形部分: x随机返回[-b,b+d-1]; y随机返回 [0,b+d-2]
                xOutput = RandomUtil.getRandomInteger(-this.sizeB, this.sizeB + this.sizeD - 1);
                yOutput = RandomUtil.getRandomInteger(0, this.sizeB + this.sizeD - 2);
                // todo:lll
                if (xOutput - yOutput + this.sizeB + this.sizeD - 1 < 0) {
                    // 左上角边长为b的三角形
                    //平移还原到右下角
                    xOutput += (this.sizeB+this.sizeD);
                    yOutput -= (this.sizeB+this.sizeD-1);
                }
                if (xOutput + yOutput - this.sizeB - 2*this.sizeD + 2 > 0) {
                    // 右上角边长为b的三角形
                    // 平移还原到左下角
                    xOutput -= (this.sizeB+this.sizeD);
                    yOutput -= (this.sizeB+this.sizeD-1);
                }
            }
        } else {
            // 只含高概率部分
            // 和d为1的时候情况相同
            double smallRectangleRatio = (this.sizeB + 1) * 1.0 / this.constValues[0];
            //todo: 修改两个为离散的
            if (tempRandomB < smallRectangleRatio) {
                // 小矩形部分: x随机返回[0,0]; y随机返回[-b-1,-1],其中-b-1部分对应着b部分
                xOutput = 0;
                yOutput = RandomUtil.getRandomInteger(-this.sizeB-1, -1);
                if (yOutput < -this.sizeB) {
                    yOutput += (2*this.sizeB + 1);
                }
            } else {
                // 大矩形部分: x随机返回[-b,b]; y随机返回 [0,b-1]
                xOutput = RandomUtil.getRandomInteger(-this.sizeB, this.sizeB);
                yOutput = RandomUtil.getRandomInteger(0, this.sizeB - 1);
                if (xOutput - yOutput + this.sizeB < 0) {
                    // 左上角边长为b的三角形
                    //平移还原到右下角
                    xOutput += (this.sizeB + 1);
                    yOutput -= this.sizeB;
                }
                if (xOutput + yOutput - this.sizeB > 0) {
                    // 右上角边长为b的三角形
                    // 平移还原到左下角
                    xOutput -= (this.sizeB+1);
                    yOutput -= this.sizeB;
                }
                xOutput += indexes[0];
                yOutput += indexes[1];
            }
        }
        return new TwoDimensionalIntegerPoint(xOutput, yOutput);
    }

    @Override
    public TreeMap<TwoDimensionalIntegerPoint, Double> statistic(List<TwoDimensionalIntegerPoint> valueList) {
        Integer[] noiseValueCountArray = StatisticTool.countHistogramNumber(this.noiseIntegerPointTypeList,valueList);
        Double[] resultRatio = StatisticTool.getTwoDimensionalExpectationMaximizationSmooth(this.transformMatrix, noiseValueCountArray, Constant.DEFAULT_STOP_VALUE_TAO, Constant.DEFAULT_ONE_DIMENSIONAL_COEFFICIENTS, this.initAverageRatio);
        TreeMap<TwoDimensionalIntegerPoint, Double> resultMap = new TreeMap<>();
        for (int i = 0; i < this.rawIntegerPointTypeList.size(); i++) {
            resultMap.put(this.rawIntegerPointTypeList.get(i), resultRatio[i]);
        }
        return resultMap;
    }

    public static void main(String[] args) {
        int size = 20000;
        double xInput, yInput;
        double epsilon = 0.5;
        double lenD = 3.0, gridLen = 0.5;
        double lenB = 2.5;
        Integer[] integerIndexes;
        TwoDimensionalDoublePoint doubleValuePoint;
        TwoDimensionalIntegerPoint outputPoint;
        DiscretizedRhombusPlane rhombusPlane = new DiscretizedRhombusPlane(epsilon, gridLen, lenB, lenD);
        List<TwoDimensionalIntegerPoint> pointList = new ArrayList<>();
        String outputPath = "D:\\test\\output3.txt";
        PointWrite pointWrite = new PointWrite();
        pointWrite.startWriting(outputPath);
        for (int i = 0; i < size; i++) {
            xInput = Math.random();
            yInput = Math.random();
            integerIndexes = Grid.toGridIndex(rhombusPlane.gridLength, xInput, yInput);
            outputPoint = rhombusPlane.getNoiseValue(new TwoDimensionalIntegerPoint(integerIndexes[0], integerIndexes[1]));
            pointList.add(outputPoint);
        }
//        Map<TwoDimensionalIntegerPoint, Integer> map = StatisticTool.countTwoDimensionalIntegerPointNumber(pointList);
        Map<TwoDimensionalIntegerPoint, Integer> map = StatisticTool.countHistogramNumber(pointList);
        pointWrite.writeStatisticIntegerPoint(map);
        pointWrite.endWriting();
    }

}
