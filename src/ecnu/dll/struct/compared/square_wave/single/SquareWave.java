package ecnu.dll.struct.compared.square_wave.single;

public class SquareWave extends GeneralWave {

    protected Double constP = null;
    protected Double constQ = null;
    protected Double epsilon = null;
    protected Integer b;

    public SquareWave(Double epsilon, Integer b) {
        this.epsilon = epsilon;
        this.b = b;
        this.constP = Math.exp(this.epsilon) / (2*this.b*Math.exp(this.epsilon) + 1);
        this.constQ = 1 - 2 * this.b * this.constP;
    }

    @Override
    public Double getWaveValue(Double inputValue) {
        if (Math.abs(inputValue) <= this.b) {
            return this.constP;
        }
        return this.constQ;
    }


}
