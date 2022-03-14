package ecnu.dll.struct.compared.geo_i;

import tools.struct.PolarPoint;
import tools.struct.point.Point;
import tools.struct.point.TwoDimensionalPoint;

public class DiscretizationGeoI extends SimpleGeoI{

    public DiscretizationGeoI(Double radius, Double angle) {
        super(radius, angle);
    }

    public TwoDimensionalPoint getNoisePosition() {
        return null;
    }



}
