package ecnu.dll.construction.schemes.new_scheme.discretization.struct;

import java.util.Objects;

@Deprecated
public class AreaMessage {
    /**
     * 记录给定的圆半径
     */
    private Integer radius = null;
    /**
     * 记录该圆与给定的点相交的面积大小
     * 如果点在圆内，面积为1；如果点在圆边界上，面积为近似面积；如果点在圆边界外，面积为0
     */
    private Double innerAreaSize = 0.0;

//    public AreaMessage() {
//    }

    public AreaMessage(Integer radius, Double innerAreaSize) {
        this.radius = radius;
        this.innerAreaSize = innerAreaSize;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    public Double getInnerAreaSize() {
        return innerAreaSize;
    }

    public void setInnerAreaSize(Double innerAreaSize) {
        this.innerAreaSize = innerAreaSize;
    }

    public Double getOuterAreaSize() {
        return 1 - this.innerAreaSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AreaMessage areaMessage = (AreaMessage) o;
        return Objects.equals(radius, areaMessage.radius) &&
                Objects.equals(innerAreaSize, areaMessage.innerAreaSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(radius, innerAreaSize);
    }
}
