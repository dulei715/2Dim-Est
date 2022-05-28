package tools.struct.point;

import tools.io.print.MyPrint;

import java.util.HashMap;
import java.util.Map;

public class TwoDimensionalIntegerPoint extends IntegerPoint implements Comparable<TwoDimensionalIntegerPoint>{
    public TwoDimensionalIntegerPoint() {
        super(0,0);
    }

    public TwoDimensionalIntegerPoint(int xIndex, int yIndex) {
        super(xIndex, yIndex);
    }

    public TwoDimensionalIntegerPoint(int[] indexes) {
        super(indexes);
    }


    public Integer getXIndex() {
        return this.valueArray[0];
    }

    public void setXIndex(int xIndex) {
        this.valueArray[0] = xIndex;
    }

    public Integer getYIndex() {
        return valueArray[1];
    }

    public void setYIndex(int yIndex) {
        this.valueArray[1] = yIndex;
    }

    public int[] getIndex() {
        return new int[]{this.valueArray[0], this.valueArray[1]};
    }

    @Override
    public String toString() {
        return "TwoDimensionalIntegerPoint{" +
                "xIndex=" + valueArray[0] +
                ", yIndex=" + valueArray[1] +
                '}';
    }

    @Override
    public int compareTo(TwoDimensionalIntegerPoint twoDimensionalIntegerPoint) {
        if (this == twoDimensionalIntegerPoint) return 0;
        int xCMP = this.valueArray[0].compareTo(twoDimensionalIntegerPoint.valueArray[0]);
        if (xCMP != 0) {
            return xCMP;
        }
        int yCMP = this.valueArray[1].compareTo(twoDimensionalIntegerPoint.valueArray[1]);
        return yCMP;
    }

    @Override
    public int hashCode() {
        //todo: 设计hashcode
        return this.valueArray[0].hashCode() * this.valueArray[1].hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TwoDimensionalIntegerPoint)) {
            return false;
        }
        TwoDimensionalIntegerPoint comparePoint = (TwoDimensionalIntegerPoint)obj;
        return (this.valueArray[0].equals(comparePoint.valueArray[0]) && this.valueArray[1].equals(comparePoint.valueArray[1]));
    }

    public static void main(String[] args) {
        TwoDimensionalIntegerPoint pointA = new TwoDimensionalIntegerPoint(2, 3);
        TwoDimensionalIntegerPoint pointB = new TwoDimensionalIntegerPoint(2, 3);
//        System.out.println(pointA.equals(pointB));
        Map<TwoDimensionalIntegerPoint, Integer> map = new HashMap<>();
        map.put(pointA, 2);
        MyPrint.showMap(map);
        MyPrint.showSplitLine("*", 100);
        map.put(pointB, 3);
        MyPrint.showMap(map);

        Integer a = 10;
        System.out.println(a.hashCode());
    }
}
