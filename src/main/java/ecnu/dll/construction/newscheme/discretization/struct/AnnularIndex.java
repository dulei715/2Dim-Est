package ecnu.dll.construction.newscheme.discretization.struct;

public class AnnularIndex {
    /**
     * 记录与给定cell相交的最小sizeB.如果完全不在sizeB范围内，则记为-1.
     */
    private Integer partSizeB = null;
    /**
     * 记录与给定cell相交的最小size截取的shrank面积. 如果完全不在sizeB范围内，则记为1.0.
     */
    private Double partAreaSize = null;

    public AnnularIndex(Integer partSizeB, Double partAreaSize) {
        this.partSizeB = partSizeB;
        this.partAreaSize = partAreaSize;
    }

    public Integer getPartSizeB() {
        return partSizeB;
    }

    public void setPartSizeB(Integer partSizeB) {
        this.partSizeB = partSizeB;
    }

    public Double getPartAreaSize() {
        return partAreaSize;
    }

    public void setPartAreaSize(Double partAreaSize) {
        this.partAreaSize = partAreaSize;
    }
}
