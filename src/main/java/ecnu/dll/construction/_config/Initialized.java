package ecnu.dll.construction._config;

import ecnu.dll.construction.run.main_process.b_comparision_run.extended.tool.DAMEpsilonLocalPrivacyTable;
import ecnu.dll.construction.run.main_process.b_comparision_run.extended.tool.SubsetGeoIEpsilonLocalPrivacyTable;

public class Initialized {
    public static DAMEpsilonLocalPrivacyTable damELPTotalTable, damELPBasicTable, damELPExtendedTable, damELPKLTable;
    public static SubsetGeoIEpsilonLocalPrivacyTable subGeoIELPTotalTable, subGeoIELPBasicTable, subGeoIELPExtendedTable, subGeoIELPKLTable;
    static {
        damELPTotalTable = DAMEpsilonLocalPrivacyTable.readTable(Constant.damBudgetLPTableGeneratedTotalPath);
        subGeoIELPTotalTable = SubsetGeoIEpsilonLocalPrivacyTable.readTable(Constant.subsetGeoIBudgetLPTableGeneratedTotalPath);

        damELPBasicTable = DAMEpsilonLocalPrivacyTable.readTable(Constant.damBudgetLPTableGeneratedPathOnlyForBasic);
        subGeoIELPBasicTable = SubsetGeoIEpsilonLocalPrivacyTable.readTable(Constant.subsetGeoIBudgetLPTableGeneratedPathOnlyForBasic);

//        damELPExtendedTable = DAMEpsilonLocalPrivacyTable.readTable(Constant.damBudgetLPTableGeneratedPathOnlyForExtended);
//        subGeoIELPExtendedTable = SubsetGeoIEpsilonLocalPrivacyTable.readTable(Constant.subsetGeoIBudgetLPTableGeneratedPathOnlyForExtended);

//        damELPTableOnlyForKLDivergence = DAMEpsilonLocalPrivacyTable.readTable(Constant.damBudgetLPTableGeneratedTotalPath);
//        subGeoIELPTableOnlyForKLDivergence = SubsetGeoIEpsilonLocalPrivacyTable.readTable(Constant.subsetGeoIBudgetLPTableGeneratedTotalPath);
//        damELPTableOnlyForKLDivergence = DAMEpsilonLocalPrivacyTable.readTable(Constant.damBudgetLPTableGeneratedPathOnlyForKLDivergence);
//        subGeoIELPTableOnlyForKLDivergence = SubsetGeoIEpsilonLocalPrivacyTable.readTable(Constant.subsetGeoIBudgetLPTableGeneratedPathOnlyForKLDivergence);
        // 这里调整参数，onlyForKLDivergence的与之前的相同
    }
}
