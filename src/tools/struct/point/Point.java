package tools.struct.point;

import tools.basic.BasicArray;

import java.util.Arrays;

public abstract class Point {
    protected Integer dimensionalSize = null;
    protected Double[] valueArray = null;

    public Point(Integer dimensionalSize) {
        this.dimensionalSize = dimensionalSize;
        this.valueArray = new Double[this.dimensionalSize];
        BasicArray.setDoubleArrayToZero(this.valueArray);
    }

    public Point(Double[] valueArray) {
        if (valueArray == null || valueArray.length < 1) {
            throw new RuntimeException("The parameter is not valid!");
        }
        this.dimensionalSize = valueArray.length;
        this.valueArray = valueArray;
    }

    public Point(double... values) {
        this.dimensionalSize = values.length;
        this.valueArray = new Double[this.dimensionalSize];
        for (int i = 0; i < this.dimensionalSize; i++) {
            this.valueArray[i] = values[i];
        }
    }


    public Integer getDimensionalSize() {
        return dimensionalSize;
    }

    public Double[] getValueArray() {
        return valueArray;
    }

    public void setValueArray(Double[] valueArray) {
        if (valueArray == null || valueArray.length < 1) {
            throw new RuntimeException("The parameter is not valid!");
        }
        this.dimensionalSize = valueArray.length;
        this.valueArray = valueArray;
    }

    public void setValues(double... values) {
        if (values.length != this.dimensionalSize) {
            throw new RuntimeException("The number of values is not equaled to the dimension!");
        }
        for (int i = 0; i < this.dimensionalSize; i++) {
            this.valueArray[i] = values[i];
        }
    }

    public double getDeclaredIndexValue(int dimensionIndex) {
        return this.valueArray[dimensionIndex];
    }

    public void setDeclaredIndexValue(int dimensionIndex, double value) {
        this.valueArray[dimensionIndex] = value;
    }

    @Override
    public String toString() {
        return "Point{" +
                "dimensionalSize=" + dimensionalSize +
                ", valueArray=" + Arrays.toString(valueArray) +
                '}';
    }

    public static void main(String[] args) {
        Double value = new Double(7.8);
        Point point = new BasicPoint(1);
        point.setDeclaredIndexValue(0, value);
        System.out.println(point);
        value = 9.0;
        System.out.println(point);
    }

}
