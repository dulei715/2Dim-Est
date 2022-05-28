package ecnu.dll.construction.basicscheme.square_wave;

public class SquareWave extends GeneralWave {

    protected Double constP = null;

    // 将范围扩展2b(e^\epsilon-1)，使得可以通过均匀选择选出满足条件的随机值
    private Double extendedMaxValue = null;

    public SquareWave(Double epsilon) {
        super(epsilon);
    }

    public SquareWave(Double epsilon, Integer b) {
        super(epsilon);
        this.b = b;
        this.constP = Math.exp(this.epsilon) / (2*this.b*Math.exp(this.epsilon) + 1);
        this.constQ = 1 - 2 * this.b * this.constP;
        this.extendedMaxValue = 1 + 2*b*Math.exp(this.epsilon) - this.b;
    }

    @Override
    protected Double getWaveValue(Double inputValue) {
        if (Math.abs(inputValue) <= this.b) {
            return this.constP;
        }
        return this.constQ;
    }

    @Override
    public Double getNoiseValue(Double realValue) {
        if (realValue < 0 || realValue > 1) {
            throw new RuntimeException("The real value is out of domain!");
        }
        double randomValue = Math.random() * (1+2*this.b*Math.exp(this.epsilon)) - this.b;
        if (randomValue < 1 + this.b) {
            return randomValue;
        }
        return (randomValue - 1 - this.b) / (Math.exp(this.epsilon) - 1) + realValue - this.b;
    }


}
