package ecnu.dll.construction.newscheme;

import tools.struct.point.TwoDimensionalPoint;

public class RhombusPlane {
    protected Double epsilon = null;
    protected Double constB = null;
    protected Double constP = null;

    protected Double getWaveValue(TwoDimensionalPoint inputValue) {
        if (Math.abs(inputValue) <= this.b) {
            return this.constP;
        }
        return this.constQ;
    }

}
