package ecnu.dll.construction.compared.square_wave.single.discretization;

import ecnu.dll.construction.compared.square_wave.single.SquareWave;
import tools.RandomUtil;

public class BucketizingOptimalSquareWave extends SquareWave {
    private Integer inputSize = null;
    private Integer outputSize = null;


    public BucketizingOptimalSquareWave(Double epsilon, Integer inputSize) {
        super(epsilon);
        this.inputSize = inputSize;
        this.b = (int)Math.floor(((this.epsilon - 1)*Math.exp(this.epsilon) + 1)*this.inputSize/(2 * Math.exp(this.epsilon) * (Math.exp(this.epsilon) - 1 - this.epsilon)));
        this.outputSize = 2*this.b + inputSize;
        this.constQ = 1/((2*this.b+1)*Math.exp(this.epsilon)+this.inputSize-1);
        this.constP = this.constQ * Math.exp(this.epsilon);

    }

    /**
     * 将0到inputSize-1的坐标i映射到0到outputSize-1的坐标j. 其中初始映射为i到j+b
     * @param realIndex
     * @return
     */
    public Integer getNoiseIndex(Integer realIndex) {
        int initializeNoiseIndex = realIndex + this.b;
        int highReportLeftIndex = initializeNoiseIndex - this.b;
        int highReportRightIndex = initializeNoiseIndex +this.b;
        double randomPart = Math.random();
        if (randomPart < this.constP * (2*this.b+1)) {
            // 高概率部分
            return RandomUtil.getRandomInteger(highReportLeftIndex, highReportRightIndex);
        }
        // 低概率部分
        return RandomUtil.getTwoPartRandomInteger(0, highReportLeftIndex - 1, highReportRightIndex + 1, this.outputSize - 1);
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
