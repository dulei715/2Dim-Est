package tools.struct.point;

import java.util.Objects;

public class TwoDimensionalPoint extends Point implements Comparable<TwoDimensionalPoint> {

    public TwoDimensionalPoint() {
        super(0.0, 0.0);
    }

    public TwoDimensionalPoint(double xIndex, double yIndex) {
        super(xIndex, yIndex);
    }

    public TwoDimensionalPoint(double[] indexes) {
        super(indexes);
    }

    public Double getXIndex() {
        return this.valueArray[0];
    }

    public void setXIndex(double xIndex) {
        this.valueArray[0] = xIndex;
    }

    public Double getYIndex() {
        return valueArray[1];
    }

    public void setYIndex(double yIndex) {
        this.valueArray[1] = yIndex;
    }

    public double[] getIndex() {
        return new double[]{this.valueArray[0], this.valueArray[1]};
    }

    @Override
    public String toString() {
        return "TwoDimensionalPoint{" +
                "xIndex=" + valueArray[0] +
                ", yIndex=" + valueArray[1] +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TwoDimensionalPoint twoDimensionalPoint = (TwoDimensionalPoint) o;
        return Double.compare(twoDimensionalPoint.getXIndex(), valueArray[0]) == 0 &&
                Double.compare(twoDimensionalPoint.getYIndex(), valueArray[1]) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(valueArray[0], valueArray[1]);
    }


    @Override
    public int compareTo(TwoDimensionalPoint twoDimensionalPoint) {
        if (this == twoDimensionalPoint) return 0;
        int xCMP = this.valueArray[0].compareTo(twoDimensionalPoint.valueArray[0]);
        if (xCMP != 0) {
            return xCMP;
        }
        int yCMP = this.valueArray[1].compareTo(twoDimensionalPoint.valueArray[1]);
        return yCMP;
    }
}
