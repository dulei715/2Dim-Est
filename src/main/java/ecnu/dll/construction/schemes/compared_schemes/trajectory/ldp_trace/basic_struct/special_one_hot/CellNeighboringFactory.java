package ecnu.dll.construction.schemes.compared_schemes.trajectory.ldp_trace.basic_struct.special_one_hot;

public class CellNeighboringFactory {
    private int rowSize;
    private int colSize;
    public CellNeighboringFactory(int rowSize, int colSize) {
        this.rowSize = rowSize;
        this.colSize = colSize;
    }
    public CellNeighboring generateCellNeighboringObject(int rowIndex, int colIndex) {
        return new CellNeighboring(this.rowSize, this.colSize, rowIndex, colIndex);
    }
}
