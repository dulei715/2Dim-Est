package ecnu.dll.construction.basicscheme.geo_i;


import cn.edu.dll.math.LambertW;
import cn.edu.dll.struct.PolarPoint;
import ecnu.dll.construction._config.Constant;

public class GeoIndistinguishability {


    public static PolarPoint getNoise(double epsilon) {
        Double randomAngle = Math.random() * 2 * Math.PI;
        Double randomRadius = (LambertW.getMinusOneValue((Math.random() - 1)/Math.E, Constant.DEFAULT_PRECISION) + 1) * (-1) / epsilon;
        return new PolarPoint(randomRadius, randomAngle);
    }



}
