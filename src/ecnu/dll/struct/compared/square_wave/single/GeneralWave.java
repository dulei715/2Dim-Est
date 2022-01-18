package ecnu.dll.struct.compared.square_wave.single;

public abstract class GeneralWave {

    protected Integer b;

    public GeneralWave(Integer b) {
        this.b = b;
    }

    protected abstract Double getWaveValue(Double inputValue);

    public Double getReturnProbability(Double realValue, Double sanitizedValue) {
        if (realValue < 0 || realValue > 1) {
            throw new RuntimeException("The input real value is out of domain!");
        }
        if (sanitizedValue < -this.b || sanitizedValue > 1 + this.b) {
            throw new RuntimeException("The output noise value is out of domain!");
        }
        return getWaveValue(realValue - sanitizedValue);
    }

    public abstract Double getNoiseValue(Double realValue);

}
