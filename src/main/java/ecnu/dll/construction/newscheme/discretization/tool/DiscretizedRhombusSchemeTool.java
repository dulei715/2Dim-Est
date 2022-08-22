package ecnu.dll.construction.newscheme.discretization.tool;

public class DiscretizedRhombusSchemeTool {
    /**
     *
     * @param epsilon
     * @param sizeD
     * @return
     */
    public static Integer getOptimalSizeBOfRhombusScheme(double epsilon, int sizeD) {
        double mA = Math.exp(epsilon) - 1 - epsilon;
        double mB = 1 - (1 - epsilon) * Math.exp(epsilon);
        return (int)Math.ceil((2*mB+Math.sqrt(4*mB*mB+2*Math.exp(epsilon)*mA*mB))/(2*Math.exp(epsilon)*mA) * sizeD);
    }
}
