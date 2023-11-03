package ecnu.dll.construction.analysis.others;

import cn.edu.ecnu.collection.ArraysUtils;
import cn.edu.ecnu.io.print.MyPrint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.run.main_process.b_comparision_run.extended.tool.DAMEpsilonLocalPrivacyTable;
import ecnu.dll.construction.run.main_process.b_comparision_run.extended.tool.LocalPrivacyTable;
import ecnu.dll.construction.run.main_process.b_comparision_run.extended.tool.SubsetGeoIEpsilonLocalPrivacyTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ParameterObservation {
    protected static final String damKey = "DAM";
    protected static final String subsetGeoIKey = "SubsetGeoI";
    /**
     * 测试给定路径下damBudgetTable各行（length）对应的budget的限制
     * 只针对dam转subsetGeoI的情况，是subsetGeoI对dam参数的反向限制。因为subsetGeoI如果epsilon过大且length过大，会造成内存爆炸增长
     * @return
     */
    public static Map<Double, Map<String, List<Double>>> getLimitationMap() {
        // 读取 damBudgetTable 和 subsetGeoITable的对应关系
        DAMEpsilonLocalPrivacyTable damEpsilonLocalPrivacyTable;
        SubsetGeoIEpsilonLocalPrivacyTable subsetGeoIEpsilonLPTable;
//        damEpsilonLocalPrivacyTable = DAMEpsilonLocalPrivacyTable.readTable(Constant.damBudgetLPTableGeneratedTotalPath);
//        subsetGeoIEpsilonLPTable = SubsetGeoIEpsilonLocalPrivacyTable.readTable(Constant.subsetGeoIBudgetLPTableGeneratedTotalPath);
        damEpsilonLocalPrivacyTable = DAMEpsilonLocalPrivacyTable.readTable(Constant.damBudgetLPTableGeneratedPathOnlyForBasic);
        subsetGeoIEpsilonLPTable = SubsetGeoIEpsilonLocalPrivacyTable.readTable(Constant.subsetGeoIBudgetLPTableGeneratedPathOnlyForBasic);

        // 选取subGeoIEpsilonLPTable中的每行的最大值lineMaxLPValue和最小值lineMinLPValue，查找他们在damEpsilonLPTable中对应的epsilon
        // 这里要保证两个table的sizeD和epsilon的取值域完全相同
        List<Double> sizeDList = damEpsilonLocalPrivacyTable.getSizeDList();
        List<Double> epsilonList = damEpsilonLocalPrivacyTable.getBudgetList();
        Map<Double, Map<String, List<Double>>> result = new TreeMap<>();
        List<Double> damLimitation, subsetGeoILimitation;
        Map<String, List<Double>> tempInnerMap;
        Double tempDAMMaxValue, tempDAMMinValue, tempSubGeoIMaxValue, tempSubGeoIMinValue;
        for (Double sizeD : sizeDList) {

            tempDAMMaxValue = damEpsilonLocalPrivacyTable.getMaxLocalPrivacyValueInLowerBoundTable(sizeD);
            tempDAMMinValue = damEpsilonLocalPrivacyTable.getMinLocalPrivacyValueInLowerBoundTable(sizeD);
            tempSubGeoIMaxValue = subsetGeoIEpsilonLPTable.getMaxLocalPrivacyValueInUpperBoundTable(sizeD);
            tempSubGeoIMinValue = subsetGeoIEpsilonLPTable.getMinLocalPrivacyValueInUpperBoundTable(sizeD);

            tempInnerMap = new TreeMap<>();
            damLimitation = new ArrayList<>();
            subsetGeoILimitation = new ArrayList<>();

            // 因为降序排列，这里对应比较epsilon的最小值
            if (tempDAMMaxValue > tempSubGeoIMaxValue) {    // 需要限制dam的epsilon的最小值
                subsetGeoILimitation.add(epsilonList.get(0));
                damLimitation.add(damEpsilonLocalPrivacyTable.getEpsilonByLowerBoundLocalPrivacy(sizeD, tempSubGeoIMaxValue));

            } else if (tempDAMMaxValue < tempSubGeoIMaxValue) {     // 需要限制subsetGeoI的最大值
                damLimitation.add(epsilonList.get(0));
                subsetGeoILimitation.add(subsetGeoIEpsilonLPTable.getEpsilonByUpperBoundLocalPrivacy(sizeD, tempDAMMaxValue));
            } else {
                damLimitation.add(epsilonList.get(0));
                subsetGeoILimitation.add(epsilonList.get(0));
            }

            if (tempDAMMinValue < tempSubGeoIMinValue) {
                subsetGeoILimitation.add(epsilonList.get(epsilonList.size()-1));
                damLimitation.add(damEpsilonLocalPrivacyTable.getEpsilonByLowerBoundLocalPrivacy(sizeD, tempSubGeoIMinValue));
            } else if (tempDAMMinValue > tempSubGeoIMinValue) {
                damLimitation.add(epsilonList.get(epsilonList.size()-1));
                subsetGeoILimitation.add(subsetGeoIEpsilonLPTable.getEpsilonByUpperBoundLocalPrivacy(sizeD, tempDAMMinValue));
            } else {
                damLimitation.add(epsilonList.get(epsilonList.size()-1));
                subsetGeoILimitation.add(epsilonList.get(epsilonList.size()-1));
            }

            tempInnerMap.put(damKey, damLimitation);
            tempInnerMap.put(subsetGeoIKey, subsetGeoILimitation);


            result.put(sizeD, tempInnerMap);
        }

        return result;
    }

    public static List<Double> getNonDecreaseSizeD(LocalPrivacyTable localPrivacyTable) {
        double[][] table = localPrivacyTable.getlPTable();
        double[] tempArray;
        List<Double> result = new ArrayList<>();
        List<Double> sizeDList = localPrivacyTable.getSizeDList();
        for (int i = 0; i < table.length; i++) {
            tempArray = table[i];
            boolean descending = ArraysUtils.isDescending(tempArray);
            if (!descending) {
                result.add(sizeDList.get(i));
            }
        }
        return result;
    }

    public static void main0(String[] args) {
        DAMEpsilonLocalPrivacyTable damEpsilonLocalPrivacyTable = DAMEpsilonLocalPrivacyTable.readTable(Constant.damBudgetLPTableGeneratedTotalPath);
        SubsetGeoIEpsilonLocalPrivacyTable subsetGeoIEpsilonLPTable = SubsetGeoIEpsilonLocalPrivacyTable.readTable(Constant.subsetGeoIBudgetLPTableGeneratedTotalPath);

        List<Double> damNonDecreaseSizeDList = getNonDecreaseSizeD(damEpsilonLocalPrivacyTable);
        List<Double> subsetGeoINonDecreaseSizeDList = getNonDecreaseSizeD(subsetGeoIEpsilonLPTable);
        MyPrint.showList(damNonDecreaseSizeDList);
        MyPrint.showSplitLine("*", 150);
        MyPrint.showList(subsetGeoINonDecreaseSizeDList);

    }

    public static void main(String[] args) {
        Map<Double, Map<String, List<Double>>> limitationMap = getLimitationMap();
        MyPrint.showMap(limitationMap);
    }

}
