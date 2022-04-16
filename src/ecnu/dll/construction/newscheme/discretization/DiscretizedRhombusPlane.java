package ecnu.dll.construction.newscheme.discretization;

import tools.RandomUtil;
import tools.io.write.PointWrite;
import tools.statistic.StatisticTool;
import tools.struct.Grid;
import tools.struct.point.TwoDimensionalIntegerPoint;
import tools.struct.point.TwoDimensionalDoublePoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DiscretizedRhombusPlane {
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
    }



//    private
    public TwoDimensionalIntegerPoint getNoiseValue(TwoDimensionalDoublePoint realValue) {
        Integer[] indexes = Grid.toGridIndex(this.gridLength, realValue.getValueArray());

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

    public static void main(String[] args) {
        int size = 20000;
        double xInput, yInput;
        double epsilon = 0.5;
        double lenD = 3.0, gridLen = 0.5;
        double lenB = 2.5;
        TwoDimensionalIntegerPoint outputPoint;
        DiscretizedRhombusPlane rhombusPlane = new DiscretizedRhombusPlane(epsilon, gridLen, lenB, lenD);
        List<TwoDimensionalIntegerPoint> pointList = new ArrayList<>();
        String outputPath = "D:\\test\\output3.txt";
        PointWrite pointWrite = new PointWrite();
        pointWrite.startWriting(outputPath);
        for (int i = 0; i < size; i++) {
            xInput = Math.random();
            yInput = Math.random();
            outputPoint = rhombusPlane.getNoiseValue(new TwoDimensionalDoublePoint(xInput, yInput));
            pointList.add(outputPoint);
        }
        Map<TwoDimensionalIntegerPoint, Integer> map = StatisticTool.countTwoDimensionalIntegerPointNumber(pointList);
        pointWrite.writeStatisticIntegerPoint(map);
        pointWrite.endWriting();
    }

}
