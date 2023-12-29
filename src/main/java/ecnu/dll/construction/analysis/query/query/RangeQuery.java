package ecnu.dll.construction.analysis.query.query;

public class RangeQuery {
    public static double getCountQueryResult(double[][] data, int[] xRange, int[] yRange) {
        double result = 0;
        for (int i = xRange[0]; i <= xRange[1]; i++) {
            for (int j = yRange[0]; j <= yRange[1]; j++) {
                result += data[i][j];
            }
        }
        return result;
    }
}
