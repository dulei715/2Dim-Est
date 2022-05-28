package tools.struct.point;

import tools.basic.BasicArray;

import java.util.Arrays;

public abstract class IntegerPoint {
    protected Integer dimensionalSize = null;
    protected Integer[] valueArray = null;

    public IntegerPoint(Integer dimensionalSize) {
        this.dimensionalSize = dimensionalSize;
        this.valueArray = new Integer[this.dimensionalSize];
        BasicArray.setIntArrayToZero(this.valueArray);
    }

    public IntegerPoint(Integer[] valueArray) {
        if (valueArray == null || valueArray.length < 1) {
            throw new RuntimeException("The parameter is not valid!");
        }
        this.dimensionalSize = valueArray.length;
        this.valueArray = valueArray;
    }

    public IntegerPoint(int... values) {
        this.dimensionalSize = values.length;
        this.valueArray = new Integer[this.dimensionalSize];
        for (int i = 0; i < this.dimensionalSize; i++) {
            this.valueArray[i] = values[i];
        }
    }


    public Integer getDimensionalSize() {
        return dimensionalSize;
    }

    public Integer[] getValueArray() {
        return valueArray;
    }

    public void setValueArray(Integer[] valueArray) {
        if (valueArray == null || valueArray.length < 1) {
            throw new RuntimeException("The parameter is not valid!");
        }
        this.dimensionalSize = valueArray.length;
        this.valueArray = valueArray;
    }

    public void setValues(int... values) {
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

    public void setDeclaredIndexValue(int dimensionIndex, int value) {
        this.valueArray[dimensionIndex] = value;
    }

    @Override
    public String toString() {
        return "Point{" +
                "dimensionalSize=" + dimensionalSize +
                ", valueArray=" + Arrays.toString(valueArray) +
                '}';
    }

}
