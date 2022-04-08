package tools.io.write;

import tools.basic.BasicArray;
import tools.struct.point.TwoDimensionalIntegerPoint;
import tools.struct.point.TwoDimensionalDoublePoint;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public class PointWrite extends BasicWrite {

    public PointWrite() {
    }

    public PointWrite(String OUTPUT_SPLIT_SYMBOL) {
        super(OUTPUT_SPLIT_SYMBOL);
    }

    public void writeDoublePoint(Collection<TwoDimensionalDoublePoint> dataCollection) {
        int i = 0;
        TwoDimensionalDoublePoint tempTwoDimensionalDoublePoint;
        try {
            super.bufferedWriter.write(String.valueOf(dataCollection.size()));
            super.bufferedWriter.newLine();
            for (TwoDimensionalDoublePoint twoDimensionalDoublePoint : dataCollection) {
                super.bufferedWriter.write(String.valueOf(twoDimensionalDoublePoint.getXIndex()));
                super.bufferedWriter.write(super.OUTPUT_SPLIT_SYMBOL);
                super.bufferedWriter.write(String.valueOf(twoDimensionalDoublePoint.getYIndex()));
                super.bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeIntegerPoint(Collection<TwoDimensionalIntegerPoint> dataCollection) {
        int i = 0;
        TwoDimensionalDoublePoint tempTwoDimensionalDoublePoint;
        try {
            super.bufferedWriter.write(String.valueOf(dataCollection.size()));
            super.bufferedWriter.newLine();
            for (TwoDimensionalIntegerPoint twoDimensionalDoublePoint : dataCollection) {
                super.bufferedWriter.write(String.valueOf(twoDimensionalDoublePoint.getXIndex()));
                super.bufferedWriter.write(super.OUTPUT_SPLIT_SYMBOL);
                super.bufferedWriter.write(String.valueOf(twoDimensionalDoublePoint.getYIndex()));
                super.bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void writeStatisticIntegerPoint(Map<TwoDimensionalIntegerPoint, Integer> dataCollection) {
        TwoDimensionalIntegerPoint tempPoint;
        Integer tempInteger;
        try {
            super.bufferedWriter.write(String.valueOf(dataCollection.size()));
            super.bufferedWriter.newLine();
            for (Map.Entry<TwoDimensionalIntegerPoint, Integer> entry : dataCollection.entrySet()) {
                tempPoint = entry.getKey();
                tempInteger = entry.getValue();
                super.bufferedWriter.write(tempPoint.getXIndex() + OUTPUT_SPLIT_SYMBOL);
                super.bufferedWriter.write(tempPoint.getYIndex() + OUTPUT_SPLIT_SYMBOL);
                super.bufferedWriter.write(String.valueOf(tempInteger));
                super.bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
