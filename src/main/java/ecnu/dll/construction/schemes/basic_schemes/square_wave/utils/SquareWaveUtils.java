package ecnu.dll.construction.schemes.basic_schemes.square_wave.utils;

public class SquareWaveUtils {
    public static Double getOptimalB(Double epsilon) {
        double topValue = Math.exp(epsilon);
        return (epsilon * topValue - topValue + 1) / (2 * topValue * (topValue - 1 - epsilon));
    }
}
