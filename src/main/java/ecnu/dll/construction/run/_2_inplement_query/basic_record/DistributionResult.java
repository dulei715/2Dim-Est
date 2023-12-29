package ecnu.dll.construction.run._2_inplement_query.basic_record;

public class DistributionResult {
    private Integer xSize = null;
    private Integer ySize = null;
    private double[][] distributionMatrix = null;

    public DistributionResult(double[][] distributionMatrix) {
        this.distributionMatrix = distributionMatrix;
        this.xSize = distributionMatrix.length;
        this.ySize = distributionMatrix[0].length;
    }

    public double[][] getDistributionMatrix() {
        return distributionMatrix;
    }

    public void setDistributionMatrix(double[][] distributionMatrix) {
        this.distributionMatrix = distributionMatrix;
        this.xSize = distributionMatrix.length;
        this.ySize = distributionMatrix[0].length;
    }

    public Integer getxSize() {
        return xSize;
    }

    public Integer getySize() {
        return ySize;
    }
}
