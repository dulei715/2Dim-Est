package ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.special_struct;

import cn.edu.dll.struct.one_hot.OneHot;

public class LengthOneHot extends OneHot<Integer> {
    @Override
    public void setElement(Integer integer) {
        int index = toOneHotDataIndex(integer);
        this.data[index] = ONE;
    }

    protected LengthOneHot(boolean... booleans) {
        super(booleans);
    }

    @Override
    public OneHot<Integer> getInstance(boolean... booleans) {
        return new LengthOneHot(booleans);
    }


    protected int toOneHotDataIndex(Integer integer) {
        return integer;
    }
}
