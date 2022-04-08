package ecnu.dll.construction.compared.geo_i.discretization;

import ecnu.dll.construction.compared.geo_i.SimpleGeoI;
import tools.struct.point.TwoDimensionalDoublePoint;

public class DiscretizationGeoI extends SimpleGeoI {

    public DiscretizationGeoI(Double radius, Double angle) {
        super(radius, angle);
    }

    public TwoDimensionalDoublePoint getNoisePosition() {
        return null;
    }



}
