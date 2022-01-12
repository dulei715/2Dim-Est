package ecnu.dll.struct.compared.square_wave.single;

public abstract class GeneralWave {

    protected abstract Double getWaveValue(Double inputValue);

    public Double getReturnProbability(Double realValue, Double sanitizedValue) {
         return getWaveValue(realValue - sanitizedValue);
    }

}
