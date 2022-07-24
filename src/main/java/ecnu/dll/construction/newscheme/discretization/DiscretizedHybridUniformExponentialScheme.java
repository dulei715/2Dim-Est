package ecnu.dll.construction.newscheme.discretization;

import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;

public class DiscretizedHybridUniformExponentialScheme extends AbstractDiscretizedScheme {

    public DiscretizedHybridUniformExponentialScheme(Double epsilon, Double gridLength, Double constB, Double inputLength, Double kParameter, Double xLeft, Double yLeft) {
        super(epsilon, gridLength, constB, inputLength, kParameter, xLeft, yLeft);
    }


    @Override
    protected void setConstPQ() {

    }

    @Override
    public Integer getOptimalSizeB() {
        return null;
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
