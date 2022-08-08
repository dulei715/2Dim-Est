package basic;

import cn.edu.ecnu.io.print.MyPrint;
import ecnu.dll.construction.newscheme.discretization.struct.MultipleRelativeElement;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MultipleRelativeElementTest {
    @Test
    public void fun1() {
        MultipleRelativeElement<Double> elementRoot = new MultipleRelativeElement<>(7.2);
        List<MultipleRelativeElement<Double>> relativeElementList = new ArrayList<>();
        relativeElementList.add(new MultipleRelativeElement<>(1.2));
        relativeElementList.add(new MultipleRelativeElement<>(3.4));
        relativeElementList.add(new MultipleRelativeElement<>(5.6));
        relativeElementList.add(new MultipleRelativeElement<>(7.8));

        elementRoot.setRelativeElementList(relativeElementList);

        Double[] result = elementRoot.getRelativeElementValueArray();
        MyPrint.showArray(result);
    }
}
