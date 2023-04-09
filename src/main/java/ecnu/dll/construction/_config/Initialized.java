package ecnu.dll.construction._config;

import ecnu.dll.construction.run.main_process.b_comparision_run.extended.tool.DAMEpsilonLocalPrivacyTable;
import ecnu.dll.construction.run.main_process.b_comparision_run.extended.tool.SubsetGeoIEpsilonLocalPrivacyTable;

public class Initialized {
    public static DAMEpsilonLocalPrivacyTable damELPTable, damELPTableOnlyForKLDivergence;
    public static SubsetGeoIEpsilonLocalPrivacyTable subGeoIELPTable, subGeoIELPTableOnlyForKLDivergence;
    static {
        damELPTable = DAMEpsilonLocalPrivacyTable.readTable(Constant.damBudgetLPTableGeneratedPath);
        subGeoIELPTable = SubsetGeoIEpsilonLocalPrivacyTable.readTable(Constant.subsetGeoIBudgetLPTableGeneratedPath);
//        damELPTableOnlyForKLDivergence = DAMEpsilonLocalPrivacyTable.readTable(Constant.damBudgetLPTableGeneratedPathOnlyForKLDivergence);
//        subGeoIELPTableOnlyForKLDivergence = SubsetGeoIEpsilonLocalPrivacyTable.readTable(Constant.subsetGeoIBudgetLPTableGeneratedPathOnlyForKLDivergence);
        // 这里调整参数，onlyForKLDivergence的与之前的相同
        damELPTableOnlyForKLDivergence = DAMEpsilonLocalPrivacyTable.readTable(Constant.damBudgetLPTableGeneratedPath);
        subGeoIELPTableOnlyForKLDivergence = SubsetGeoIEpsilonLocalPrivacyTable.readTable(Constant.subsetGeoIBudgetLPTableGeneratedPath);
    }
}
