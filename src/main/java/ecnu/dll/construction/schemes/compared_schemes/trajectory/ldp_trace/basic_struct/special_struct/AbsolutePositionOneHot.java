package ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.special_struct;

import cn.edu.dll.struct.one_hot.OneHot;
import cn.edu.dll.struct.point.TwoDimensionalIntegerPoint;

/**
 * 用来记录起始点或终止点的 one hot 编码
 */
public class AbsolutePositionOneHot extends OneHot<TwoDimensionalIntegerPoint> {
    protected int rowSize;
    protected int colSize;
    public AbsolutePositionOneHot(int rowSize, int colSize) {
        this.rowSize = rowSize;
        this.colSize = colSize;
    }
    protected AbsolutePositionOneHot(boolean... booleans) {
        super(booleans);
    }
    @Override
    public void setElement(TwoDimensionalIntegerPoint point) {
        int index = toOneHotDataIndex(point);
        this.data[index] = ONE;
    }

    @Override
    public OneHot<TwoDimensionalIntegerPoint> getInstance(boolean... booleans) {
        AbsolutePositionOneHot newInstance = new AbsolutePositionOneHot(booleans);
        newInstance.rowSize = this.rowSize;
        newInstance.colSize = this.colSize;
        return newInstance;
    }

    protected int toOneHotDataIndex(TwoDimensionalIntegerPoint point) {
        return point.getXIndex() * this.colSize + point.getYIndex();
    }
}
