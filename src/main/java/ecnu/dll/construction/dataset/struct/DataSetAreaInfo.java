package ecnu.dll.construction.dataset.struct;

public class DataSetAreaInfo {
    private String dataSetPath = null;
    private Double xBound = null;
    private Double yBound = null;
    private Double length = null;

    public DataSetAreaInfo(String dataSetPath, Double xBound, Double yBound, Double length) {
        this.dataSetPath = dataSetPath;
        this.xBound = xBound;
        this.yBound = yBound;
        this.length = length;
    }

    public String getDataSetPath() {
        return dataSetPath;
    }

    public void setDataSetPath(String dataSetPath) {
        this.dataSetPath = dataSetPath;
    }

    public Double getxBound() {
        return xBound;
    }

    public void setxBound(Double xBound) {
        this.xBound = xBound;
    }

    public Double getyBound() {
        return yBound;
    }

    public void setyBound(Double yBound) {
        this.yBound = yBound;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "DataSetAreaInfo{" +
                "dataSetPath='" + dataSetPath + '\'' +
                ", xBound=" + xBound +
                ", yBound=" + yBound +
                ", length=" + length +
                '}';
    }
}
