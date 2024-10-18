package ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.special_struct;

import cn.edu.dll.struct.one_hot.OneHot;

public class LengthOneHot extends OneHot<Integer> {
    protected LengthOneHot(boolean... booleans) {
        super(booleans);
    }

    @Override
    public OneHot<Integer> getInstance(boolean... booleans) {
        return new LengthOneHot(booleans);
    }

    @Override
    protected int toOneHotDataIndex(Integer integer) {
        return integer;
    }
}
