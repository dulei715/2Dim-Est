package ecnu.dll.construction.newscheme.discretization;

import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class DiscretizedDiskPlane extends AbstractDiscretizedPlane {

    // 记录内部cell个数
    private Integer innerCellSize = null;
    // 记录坐标方向和45方向，的cell个数
    private Integer edgeCellSize = null;
    private List<Pair<Integer, Integer>> outerCellIndexList = null;
    private List<Double> outerCellAreaSizeList = null;



    public DiscretizedDiskPlane(Double epsilon, Double gridLength, Double constB, Double inputLength) {
        super(epsilon, gridLength, constB, inputLength);
    }

    public DiscretizedDiskPlane(Double epsilon, Double gridLength, Double inputLength) {
        super(epsilon, gridLength, inputLength);
    }

    private void setOuterCellIndex() {
        double sqrt2 = Math.sqrt(2);
        double tempDiff = this.sizeB / sqrt2 - 0.5;
        double r_1 = Math.floor(tempDiff) * sqrt2 + 1/sqrt2;
        double r = Math.sqrt(r_1 * (r_1  - sqrt2) + 1);
        int outerCellSize = (int)Math.ceil(tempDiff) - (int)Math.floor(r / this.sizeB);
        this.outerCellIndexList = new ArrayList<>(outerCellSize);
        int xIndexTemp;
        for (int i = 0; i < outerCellSize; i++) {
            xIndexTemp = (int) Math.ceil(Math.sqrt(this.sizeB - Math.pow(i-0.5,2))-0.5);
            this.outerCellIndexList.add(new Pair<>(xIndexTemp, i));
        }
    }

    private void setOuterCellAreaSize() {

    }

    @Override
    protected void setConstPQ() {
        //
    }

    @Override
    protected Integer getOptimalSizeB() {
        double mA = Math.exp(this.epsilon) - 1 - this.epsilon;
        double mB = 1 - (1 - this.epsilon) * Math.exp(this.epsilon);
        return (int)Math.ceil((2*mB+Math.sqrt(4*mB*mB+Math.PI*Math.exp(epsilon)*mA*mB))/(Math.PI*Math.exp(epsilon)*mA) * this.sizeD);
    }

    @Override
    public void setNoiseIntegerPointTypeList() {

    }

    @Override
    public void setTransformMatrix() {

    }

    @Override
    public TwoDimensionalIntegerPoint getNoiseValue(TwoDimensionalIntegerPoint originalPoint) {
        return null;
    }
}
