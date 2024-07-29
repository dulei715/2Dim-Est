package ecnu.dll.construction.newscheme.continued;



import cn.edu.dll.basic.BasicCalculation;
import cn.edu.dll.basic.RandomUtil;
import cn.edu.dll.io.write.PointWrite;
import cn.edu.dll.struct.point.DoublePoint;
import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;

import java.util.ArrayList;
import java.util.List;
@Deprecated
public class RhombusPlane {
    protected Double epsilon = null;
    protected Double constB = null;
    protected Double constP = null;
    protected Double constQ = null;

    /**
     * 1. 分母 2b^2e^\epsilon+4b+1
     * 2. 整体为低概率时占比 2b^2+4b+1
     */
    private Double[] constValues = new Double[2];


    public RhombusPlane(Double epsilon) {
        this.epsilon = epsilon;
        double mA = Math.exp(this.epsilon) - 1 - this.epsilon;
        double mB = 1 - (1-this.epsilon)*Math.exp(this.epsilon);
        this.constB = (2*mB+Math.sqrt(4*mB*mB+2*Math.exp(this.epsilon)*mA*mB))/(2*Math.exp(this.epsilon)*mA);
        double tempA = 2*this.constB*this.constB;
        double tempB = 4*this.constB+1;
        this.constValues[0] = tempA * Math.exp(this.epsilon) + tempB;
        this.constValues[1] = tempA + tempB;

        this.constQ = 1 / this.constValues[0];
        this.constP = this.constQ * Math.exp(this.epsilon);
    }

    protected Double getWaveValue(TwoDimensionalDoublePoint inputValue) {
        if (BasicCalculation.get1Norm(inputValue) <= this.constB) {
            return this.constP;
        }
        return this.constQ;
    }

    /**
     * 包含左边和现编边框，不包含右边和上边边框
     *
     * 假设总概率表达式为δ（2b^2e^ε+4b+1）
     * 将整个概率分成 S（2b^2(e^ε-1)/δ） 和 T（2b^2+4b+1/δ） 两个部分， 其中第二部分代表8边形
     * 将8边形分成矩形 A（2b+1）*(1+b)和矩形B（1*b）
     * @param realValue
     * @return
     */
    public TwoDimensionalDoublePoint getNoiseValue(TwoDimensionalDoublePoint realValue) {
        Double xIndex = realValue.getXIndex();
        Double yIndex = realValue.getYIndex();
        if (xIndex < 0 || xIndex > 1 || yIndex < 0 || yIndex > 1) {
            throw new RuntimeException("The real value is out of domain!");
        }
        // 分割两个概率
        double totalLowProbability = this.constValues[1] * this.constQ;
        double tempRandomA = Math.random(), tempRandomB = Math.random();
        double xOutput, yOutput;
        if (tempRandomA < totalLowProbability) {
            // 含低概率部分:对应面积为 2b^2+4b+1
            // 再次分割为占比 (2b^2+3b+1):b
            double smallRectangleRatio = this.constB / this.constValues[1];
            if (tempRandomB < smallRectangleRatio) {
                // 小矩形部分: x随机返回[0,1); y随机返回[-b,0)
                xOutput = Math.random();
                yOutput = RandomUtil.getRandomDouble(-this.constB, 0.0);
            } else {
                // 大矩形部分: x随机返回[-b,1+b); y随机返回 [0,1+b)
                xOutput = RandomUtil.getRandomDouble(-this.constB, 1+this.constB);
                yOutput = RandomUtil.getRandomDouble(0.0, 1+this.constB);
                if (xOutput - yOutput + this.constB + 1 < 0) {
                    // 左上角边长为b的三角形
                    //平移还原到右下角
                    xOutput += (this.constB+1);
                    yOutput -= (this.constB+1);
                }
                if (xOutput + yOutput - this.constB - 2 > 0) {
                    // 右上角边长为b的三角形
                    // 平移还原到左下角
                    xOutput -= (this.constB+1);
                    yOutput -= (this.constB+1);
                }
            }
        } else {
            // 只含高概率部分:对应面积为 2b^2*(e^\epsilon-1)
            // 均匀返回以realValue为中心，1范数为b的菱形内的点。方法是：利用转轴公式将旋转后的坐标映射到旋转前的坐标
            double halfIntervalSize = this.constB / Math.sqrt(2);
            double xOutputTemp, yOutputTemp;
            xOutputTemp = RandomUtil.getRandomDouble(-halfIntervalSize, halfIntervalSize) / Math.sqrt(2);
            yOutputTemp = RandomUtil.getRandomDouble(-halfIntervalSize, halfIntervalSize) / Math.sqrt(2);
            xOutput = xOutputTemp - yOutputTemp;
            yOutput = xOutputTemp + yOutputTemp;
            xOutput += xIndex;
            yOutput += yIndex;
        }
        return new TwoDimensionalDoublePoint(xOutput, yOutput);
    }

    public static void main(String[] args) {
        int size = 20000;
        double xInput, yInput;
        double epsilon = 0.5;
        TwoDimensionalDoublePoint outputPoint;
        RhombusPlane rhombusPlane = new RhombusPlane(epsilon);
        List<DoublePoint> pointList = new ArrayList<>();
        String outputPath = "D:\\test\\output.txt";
        PointWrite pointWrite = new PointWrite();
        pointWrite.startWriting(outputPath);
        for (int i = 0; i < size; i++) {
            xInput = Math.random();
            yInput = Math.random();
            outputPoint = rhombusPlane.getNoiseValue(new TwoDimensionalDoublePoint(xInput, yInput));
            pointList.add(outputPoint);
        }
        pointWrite.writePoint(pointList);
    }



























}
