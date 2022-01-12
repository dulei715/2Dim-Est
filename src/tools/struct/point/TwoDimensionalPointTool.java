package tools.struct.point;

import tools.basic.BasicCalculation;
import tools.io.print.MyPrint;
import tools.io.read.PointRead;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class TwoDimensionalPointTool {

    /**
     * 获取两个集合各个点之间的距离值的统计量（用于距离数据）
     * @param taskTwoDimensionalPointList
     * @param workerTwoDimensionalPointList
     * @param interval
     * @return
     */
    public static Map<Double, Integer> getDistanceStatisticDataFromNormData(List<TwoDimensionalPoint> taskTwoDimensionalPointList, List<TwoDimensionalPoint> workerTwoDimensionalPointList, double interval) {
        TreeMap<Double, Integer> countResultMap = new TreeMap<>();
        TwoDimensionalPoint taskTwoDimensionalPoint, workerTwoDimensionalPoint;
        for (int i = 0; i < taskTwoDimensionalPointList.size(); i++) {
            taskTwoDimensionalPoint = taskTwoDimensionalPointList.get(i);
            for (int j = 0; j < workerTwoDimensionalPointList.size(); j++) {
                workerTwoDimensionalPoint = workerTwoDimensionalPointList.get(j);
                Double distance = BasicCalculation.get2Norm(taskTwoDimensionalPoint.getIndex(), workerTwoDimensionalPoint.getIndex());
                Double ceilDistance = Math.ceil(distance / interval);
                Double keyDistance = ceilDistance * interval;
                if (!countResultMap.containsKey(keyDistance)) {
                    countResultMap.put(keyDistance, 1);
                } else {
                    Integer value = countResultMap.get(keyDistance);
                    countResultMap.put(keyDistance, value + 1);
                }
            }
        }
        return countResultMap;
    }

    /**
     * 获取两个集合各个点之间的距离值的统计量（用于经纬度数据）
     * @param taskTwoDimensionalPointList
     * @param workerTwoDimensionalPointList
     * @param interval
     * @return
     */
    public static Map<Double, Integer> getDistanceStatisticDataFromLLData(List<TwoDimensionalPoint> taskTwoDimensionalPointList, List<TwoDimensionalPoint> workerTwoDimensionalPointList, double interval) {
        TreeMap<Double, Integer> countResultMap = new TreeMap<>();
        TwoDimensionalPoint taskTwoDimensionalPoint, workerTwoDimensionalPoint;
        for (int i = 0; i < taskTwoDimensionalPointList.size(); i++) {
            taskTwoDimensionalPoint = taskTwoDimensionalPointList.get(i);
            for (int j = 0; j < workerTwoDimensionalPointList.size(); j++) {
                workerTwoDimensionalPoint = workerTwoDimensionalPointList.get(j);
//                Double distance = BasicCalculation.get2Norm(taskPoint.getIndex(), workerPoint.getIndex());
                Double distance = BasicCalculation.getDistanceFrom2LngLat(taskTwoDimensionalPoint.getYIndex(), taskTwoDimensionalPoint.getXIndex(), workerTwoDimensionalPoint.getYIndex(), workerTwoDimensionalPoint.getXIndex());
                Double ceilDistance = Math.ceil(distance / interval);
                Double keyDistance = ceilDistance * interval;
                if (!countResultMap.containsKey(keyDistance)) {
                    countResultMap.put(keyDistance, 1);
                } else {
                    Integer value = countResultMap.get(keyDistance);
                    countResultMap.put(keyDistance, value + 1);
                }
            }
        }
        return countResultMap;
    }


    public static Integer getEqualPointNumberBetweenTwoList(List<TwoDimensionalPoint> taskTwoDimensionalPointList, List<TwoDimensionalPoint> workerTwoDimensionalPointList) {
        TwoDimensionalPoint taskTwoDimensionalPoint, workerTwoDimensionalPoint;
        int equalNumber = 0;
        for (int i = 0; i < taskTwoDimensionalPointList.size(); i++) {
            taskTwoDimensionalPoint = taskTwoDimensionalPointList.get(i);
            for (int j = 0; j < workerTwoDimensionalPointList.size(); j++) {
                workerTwoDimensionalPoint = workerTwoDimensionalPointList.get(j);
                if (taskTwoDimensionalPoint.equals(workerTwoDimensionalPoint)) {
                    ++ equalNumber;
                }
            }
        }
        return equalNumber;
    }

    public static void main(String[] args) {
        String basicPath = "E:\\1.学习\\4.数据集\\dataset\\original\\chengdu_total_dataset_km\\task_worker_1_2_0";
        String taskPath = basicPath + "\\task_point.txt";
        String workerPath = basicPath + "\\worker_point.txt";
        List<TwoDimensionalPoint> taskTwoDimensionalPointList = PointRead.readPointWithFirstLineCount(taskPath);
        List<TwoDimensionalPoint> workerTwoDimensionalPointList = PointRead.readPointWithFirstLineCount(workerPath);
        Map<Double, Integer> resultMap = getDistanceStatisticDataFromNormData(taskTwoDimensionalPointList, workerTwoDimensionalPointList, 5);
        MyPrint.showMap(resultMap);
    }


}
