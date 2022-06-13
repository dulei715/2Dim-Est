package ecnu.dll.construction.basicscheme.geo_i;

import cn.edu.ecnu.math.methods.solve_function.iteration_methods.IterationMethod;
import cn.edu.ecnu.struct.PolarPoint;
import cn.edu.ecnu.struct.point.TwoDimensionalDoublePoint;
import cn.edu.ecnu.struct.point.TwoDimensionalIntegerPoint;
import ecnu.dll.construction._config.Constant;

public class DiscretizationGeoI extends IterationMethod {

    private double deltaTheta;
    private double deltaR;
    // 这里统一规定网格长度一致
    private double unitSize; // u
    private double maxRadius;

    private double q;
    private double tempQSqure;
    private double tempA;
    private double tempB;

    private double originalEpsilon;

    private double transformedEpsilonUpperBound;
    private double transformedEpsilonLowerBound;

    public DiscretizationGeoI(double deltaTheta, double deltaR, double unitSize, double maxRadius, double originalEpsilon) {
        this.deltaTheta = deltaTheta;
        this.deltaR = deltaR;
        this.unitSize = unitSize;
        this.maxRadius = maxRadius;

        this.q = this.unitSize / (this.maxRadius * this.deltaTheta);
        this.tempQSqure = this.q * this.q;
        this.tempA = Math.exp(this.unitSize);
        this.tempB = this.tempA * this.tempA;

        this.originalEpsilon = originalEpsilon;

        double temp = this.maxRadius * this.deltaTheta;
        if (this.deltaR > temp) {
            throw new RuntimeException("delta_r is larger than r_max timing delta_theta");
        }

        if (temp >= this.unitSize) {
            throw new RuntimeException("r_max is no less than u dividing delta_theta");
        }

        if (this.q <= 2) {
            throw new RuntimeException("q is no more than 2");
        }

        this.transformedEpsilonUpperBound = 1 / this.unitSize * Math.log(this.q / 2);
        this.transformedEpsilonLowerBound = 0;

        if (this.originalEpsilon < 1/this.unitSize*Math.log((this.q + 2)/(this.q - 2))) {
            throw new RuntimeException("Transformed epsilon will be negative!");
        }

    }

    private double getYValue(double xValue) {
        double temp = 2 * Math.pow(this.tempA, xValue);
        return xValue + 1 / this.unitSize * Math.log((this.q + temp) / (this.q - temp)) - this.originalEpsilon;
    }


    @Override
    protected double getNextValue(double currentValue) {
        double elemA = 4 * Math.pow(this.tempB, currentValue);
        double elemB = 4 * Math.pow(this.tempA, currentValue) * this.q;
        double temp = this.tempQSqure - elemA;
        double candidateNextValue =  currentValue - temp / (temp + elemB) * getYValue(currentValue);
        if (candidateNextValue >= this.transformedEpsilonUpperBound) {
            candidateNextValue = (currentValue + this.transformedEpsilonUpperBound) / 2;
        } else if (candidateNextValue < this.transformedEpsilonLowerBound) {
            candidateNextValue = currentValue / 2;
        }
        return candidateNextValue;
    }

    protected double getTransformEpsilon() {
        double initValue = Math.log(this.q / 4) / this.unitSize;
        if (Double.isInfinite(initValue)) {
            initValue = this.originalEpsilon;
        }
        return super.getValue(initValue, Constant.DEFAULT_PRECISION);
    }


    protected PolarPoint getNoise() {
        double transformEpsilon = this.getTransformEpsilon();
        System.out.println(transformEpsilon);
        return GeoIndistinguishability.getNoise(transformEpsilon);
    }

    private TwoDimensionalIntegerPoint getNearestIntegerPoint(double xIndex, double yIndex) {
        int xPart = (int)Math.floor(xIndex / this.unitSize + 0.5);
        int yPart = (int)Math.floor(yIndex / this.unitSize + 0.5);
        return new TwoDimensionalIntegerPoint(xPart, yPart);
    }

    public TwoDimensionalIntegerPoint getIntegerNoisePoint(TwoDimensionalIntegerPoint originalPoint) {
        TwoDimensionalDoublePoint noiseAdding = this.getNoise().toPoint();
        TwoDimensionalIntegerPoint nearestIntegerAdding = getNearestIntegerPoint(noiseAdding.getXIndex(), noiseAdding.getYIndex());
        return new TwoDimensionalIntegerPoint(originalPoint.getXIndex() + nearestIntegerAdding.getXIndex(), originalPoint.getYIndex() + nearestIntegerAdding.getYIndex());
    }

}
