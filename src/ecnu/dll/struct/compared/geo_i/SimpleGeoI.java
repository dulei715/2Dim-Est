package ecnu.dll.struct.compared.geo_i;

import tools.struct.PolarPoint;

public class SimpleGeoI extends GeoIndistinguishability {


    public SimpleGeoI(Double radius, Double angle) {
        super(radius, angle);
    }

    protected static Double getLambertWValue(Double inputValue) {
        //todo: Lambert W 函数实现
        return null;
    }

    @Override
    public PolarPoint getNoiseValue() {
        Double randomAngle = Math.random() * 2 * Math.PI;
        Double randomRadius = (getLambertWValue((Math.random() - 1)/Math.E) + 1) * (-1) / Math.E;
        return new PolarPoint(randomRadius, randomAngle);
    }
}
