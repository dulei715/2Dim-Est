package ecnu.dll.construction.schemes.new_scheme.discretization.struct;

import java.lang.reflect.Array;
import java.util.List;

public class MultipleRelativeElement<T extends Comparable<T>> {
    private T value;
    private List<MultipleRelativeElement<T>> relativeElementList;

    public MultipleRelativeElement() {
    }

    public MultipleRelativeElement(T value) {
        this.value = value;
    }

    public MultipleRelativeElement(T value, List<MultipleRelativeElement<T>> relativeElementList) {
        this.value = value;
        this.relativeElementList = relativeElementList;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public List<MultipleRelativeElement<T>> getRelativeElementList() {
        return relativeElementList;
    }

    public void setRelativeElementList(List<MultipleRelativeElement<T>> relativeElementList) {
        this.relativeElementList = relativeElementList;
    }

    public T[] getRelativeElementValueArray() {
        int size = this.relativeElementList.size();
        T[] resultArray = (T[]) Array.newInstance(this.value.getClass(), size);
        for (int i = 0; i < size; i++) {
            resultArray[i] = this.relativeElementList.get(i).value;
        }
        return resultArray;
    }

}
