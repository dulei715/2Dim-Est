package ecnu.dll.construction.structs;

import java.util.Objects;

public class AttributeIndexPair {
    private Integer indexA;
    private Integer indexB;

    public AttributeIndexPair(Integer indexA, Integer indexB) {
        this.indexA = indexA;
        this.indexB = indexB;
    }

    public Integer getIndexA() {
        return indexA;
    }

    public void setIndexA(Integer indexA) {
        this.indexA = indexA;
    }

    public Integer getIndexB() {
        return indexB;
    }

    public void setIndexB(Integer indexB) {
        this.indexB = indexB;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttributeIndexPair that = (AttributeIndexPair) o;
        return Objects.equals(indexA, that.indexA) && Objects.equals(indexB, that.indexB);
    }

    @Override
    public int hashCode() {
        return Objects.hash(indexA, indexB);
    }

    @Override
    public String toString() {
        return "AttributeIndexPair{" +
                "indexA=" + indexA +
                ", indexB=" + indexB +
                '}';
    }
}
