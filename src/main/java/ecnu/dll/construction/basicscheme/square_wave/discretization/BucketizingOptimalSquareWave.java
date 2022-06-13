package ecnu.dll.construction.basicscheme.square_wave.discretization;

import cn.edu.ecnu.basic.BasicArray;
import cn.edu.ecnu.basic.RandomUtil;
import cn.edu.ecnu.statistic.StatisticTool;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.basicscheme.square_wave.continued.SquareWave;
import edu.ecnu.dll.tools.collection.ArraysUtils;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class BucketizingOptimalSquareWave extends SquareWave<Integer> {
    private Integer inputSize = null;
    private Integer outputSize = null;

    /*
     * 记录扰动位置和原始位置的转换关系。
     * 行是噪声位置，列是原始位置
     * 列位置被平移了b个单位（因为索引非负，为了方便索引）
     */
    private Double[][] transformMatrix = null;

    private Double[] initAverageRatio = null;


    public BucketizingOptimalSquareWave(Double epsilon, Integer inputSize) {
        super(epsilon);
        this.inputSize = inputSize;
        this.b = (int)Math.floor(((this.epsilon - 1)*Math.exp(this.epsilon) + 1)*this.inputSize/(2 * Math.exp(this.epsilon) * (Math.exp(this.epsilon) - 1 - this.epsilon)));
        this.outputSize = 2*this.b + inputSize;
        this.constQ = 1/((2*this.b+1)*Math.exp(this.epsilon)+this.inputSize-1);
        this.constP = this.constQ * Math.exp(this.epsilon);

        /*
            考虑到噪声索引平移了b个单位，因此对应在索引上不是-b到b，而是0到2b
         */
        this.transformMatrix = new Double[this.outputSize][this.inputSize];
        int doubleB = this.b * 2;
        for (int j = 0; j < this.transformMatrix[0].length; j++) {
            for (int i = 0; i < this.transformMatrix.length; i++) {
                int diff = i - j;
                if (diff >= 0 && diff <= doubleB) {
                    this.transformMatrix[i][j] = this.constP;
                } else {
                    this.transformMatrix[i][j] = this.constQ;
                }
            }
        }

        this.initAverageRatio = new Double[this.inputSize];
        BasicArray.setDoubleArrayTo(this.initAverageRatio, 1.0 / this.initAverageRatio.length);

    }

    /**
     * 将0到inputSize-1的坐标i映射到-b到outputSize-1-b的坐标j.
     * @param realIndex
     * @return
     */
    public Integer getNoiseIndex(Integer realIndex) {
        int initializeNoiseIndex = realIndex;
        int highReportLeftIndex = initializeNoiseIndex - this.b;
        int highReportRightIndex = initializeNoiseIndex + this.b;
        double randomPart = Math.random();
        if (randomPart < this.constP * (2*this.b+1)) {
            // 高概率部分
            return RandomUtil.getRandomInteger(highReportLeftIndex, highReportRightIndex);
        }
        // 低概率部分
        return RandomUtil.getTwoPartRandomInteger(-this.b, highReportLeftIndex - 1, highReportRightIndex + 1, this.outputSize - 1 - this.b);
    }

    @Override
    public TreeMap<Integer, Double> statistic(List<Integer> valueList) {
        // 获取的是扰动后的位置的统计量（没有平移b个单位！）
        Map<Integer, Integer> histogramCount = StatisticTool.countHistogramNumber(valueList);
        Integer[] noiseValueCountArray = new Integer[this.outputSize];
        for (Map.Entry<Integer, Integer> entry : histogramCount.entrySet()) {
            // 记录在noiseValueArray中的扰动位置统计量需要平移b个单位
            noiseValueCountArray[entry.getKey()+this.b] = entry.getValue();
        }
        Double[] resultRatio = StatisticTool.getExpectationMaximizationSmooth(this.transformMatrix, noiseValueCountArray, Constant.DEFAULT_STOP_VALUE_TAO, Constant.DEFAULT_ONE_DIMENSIONAL_COEFFICIENTS, this.initAverageRatio);
        TreeMap<Integer, Double> resultMap = new TreeMap<>();
        for (int i = 0; i < resultRatio.length; i++) {
            resultMap.put(i, resultRatio[i]);
        }
        return resultMap;
    }



    public static void main(String[] args) {
        int inputSize = 10;
        double epsilon = 0.5;
        BucketizingOptimalSquareWave bucketizingOptimalSquareWave = new BucketizingOptimalSquareWave(epsilon, inputSize);
        for (int i = 0; i < 10; i++) {
            System.out.println(bucketizingOptimalSquareWave.getNoiseIndex(0));
        }
    }
}
