package tools.struct.point;

public class BasicPoint extends Point {
    public BasicPoint(Integer dimensionalSize) {
        super(dimensionalSize);
    }

    public BasicPoint(Double[] valueArray) {
        super(valueArray);
    }

    public BasicPoint(double... values) {
        super(values);
    }
}
