package tools.struct;

public class PolarPoint {
    protected Double radius = null;
    protected Double angle = null;

    public PolarPoint(Double radius, Double angle) {
        this.radius = radius;
        this.angle = angle;
    }

    public static PolarPoint valueOf(Double radius, Double angle) {
        return new PolarPoint(radius, angle);
    }

}
