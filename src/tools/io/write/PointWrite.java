package tools.io.write;

import tools.struct.point.TwoDimensionalPoint;

import java.io.IOException;
import java.util.Collection;

public class PointWrite extends BasicWrite {

    public PointWrite() {
    }

    public PointWrite(String OUTPUT_SPLIT_SYMBOL) {
        super(OUTPUT_SPLIT_SYMBOL);
    }

    public void writePoint(Collection<TwoDimensionalPoint> dataCollection) {
        int i = 0;
        TwoDimensionalPoint tempTwoDimensionalPoint;
        try {
            super.bufferedWriter.write(String.valueOf(dataCollection.size()));
            super.bufferedWriter.newLine();
            for (TwoDimensionalPoint twoDimensionalPoint : dataCollection) {
                super.bufferedWriter.write(String.valueOf(twoDimensionalPoint.getXIndex()));
                super.bufferedWriter.write(super.OUTPUT_SPLIT_SYMBOL);
                super.bufferedWriter.write(String.valueOf(twoDimensionalPoint.getYIndex()));
                super.bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
