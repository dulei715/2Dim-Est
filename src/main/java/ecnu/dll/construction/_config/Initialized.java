package ecnu.dll.construction._config;

import ecnu.dll.construction.run.main_process.b_comparision_run.extended.tool.DAMEpsilonLocalPrivacyTable;
import ecnu.dll.construction.run.main_process.b_comparision_run.extended.tool.SubsetGeoIEpsilonLocalPrivacyTable;

public class Initialized {
    public static DAMEpsilonLocalPrivacyTable damELPTable;
    public static SubsetGeoIEpsilonLocalPrivacyTable subGeoIELPTable;
    static {
        damELPTable = DAMEpsilonLocalPrivacyTable.readTable(Constant.damBudgetLPTableGeneratedPath);
        subGeoIELPTable = SubsetGeoIEpsilonLocalPrivacyTable.readTable(Constant.subsetGeoIBudgetLPTableGeneratedPath);
    }
}
