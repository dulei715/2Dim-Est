package ecnu.dll.construction.schemes.basic_schemes.square_wave.basic;

public abstract class GeneralWave {

    protected Integer b = null;
    protected Double constQ = null;
    protected Double epsilon = null;

    public GeneralWave(Double epsilon) {
        this.epsilon = epsilon;
    }

    public GeneralWave(Integer b, Double constQ, Double epsilon) {
        this.b = b;
        this.constQ = constQ;
        this.epsilon = epsilon;
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

    public Integer getB() {
        return b;
    }

    public Double getConstQ() {
        return constQ;
    }

    public Double getEpsilon() {
        return epsilon;
    }

    public abstract Double getNoiseValue(Double realValue);

}
