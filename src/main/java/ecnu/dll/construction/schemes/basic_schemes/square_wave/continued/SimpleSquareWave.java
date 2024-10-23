package ecnu.dll.construction.schemes.basic_schemes.square_wave.continued;

import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;

import java.util.List;
import java.util.TreeMap;

public class SimpleSquareWave extends DoubleSquareWave<TwoDimensionalDoublePoint> {

    public SimpleSquareWave(Double epsilon) {
        super(epsilon);
    }

    public SimpleSquareWave(Double epsilon, Double b) {
        super(epsilon, b);
    }

    @Override
    public TreeMap<TwoDimensionalDoublePoint, Double> statistic(List<TwoDimensionalDoublePoint> valueList) {
        return null;
    }
}
