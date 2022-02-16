package ecnu.dll.struct.compared.geo_i;

import tools.struct.PolarPoint;

public abstract class GeoIndistinguishability {

    protected Double radius = null;
    protected Double angle = null;

    public GeoIndistinguishability(Double radius, Double angle) {
        this.radius = radius;
        this.angle = angle;
    }

    public abstract PolarPoint getNoisePolarPoint();

    public static void main(String[] args) {
        System.out.println("Hello Goe-I!");
    }
}
