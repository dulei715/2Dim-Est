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
     * @param taskTwoDimensionalDoublePointList
     * @param workerTwoDimensionalDoublePointList
     * @param interval
     * @return
     */
    public static Map<Double, Integer> getDistanceStatisticDataFromNormData(List<TwoDimensionalDoublePoint> taskTwoDimensionalDoublePointList, List<TwoDimensionalDoublePoint> workerTwoDimensionalDoublePointList, double interval) {
        TreeMap<Double, Integer> countResultMap = new TreeMap<>();
        TwoDimensionalDoublePoint taskTwoDimensionalDoublePoint, workerTwoDimensionalDoublePoint;
        for (int i = 0; i < taskTwoDimensionalDoublePointList.size(); i++) {
            taskTwoDimensionalDoublePoint = taskTwoDimensionalDoublePointList.get(i);
            for (int j = 0; j < workerTwoDimensionalDoublePointList.size(); j++) {
                workerTwoDimensionalDoublePoint = workerTwoDimensionalDoublePointList.get(j);
                Double distance = BasicCalculation.get2Norm(taskTwoDimensionalDoublePoint.getIndex(), workerTwoDimensionalDoublePoint.getIndex());
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
     * @param taskTwoDimensionalDoublePointList
     * @param workerTwoDimensionalDoublePointList
     * @param interval
     * @return
     */
    public static Map<Double, Integer> getDistanceStatisticDataFromLLData(List<TwoDimensionalDoublePoint> taskTwoDimensionalDoublePointList, List<TwoDimensionalDoublePoint> workerTwoDimensionalDoublePointList, double interval) {
        TreeMap<Double, Integer> countResultMap = new TreeMap<>();
        TwoDimensionalDoublePoint taskTwoDimensionalDoublePoint, workerTwoDimensionalDoublePoint;
        for (int i = 0; i < taskTwoDimensionalDoublePointList.size(); i++) {
            taskTwoDimensionalDoublePoint = taskTwoDimensionalDoublePointList.get(i);
            for (int j = 0; j < workerTwoDimensionalDoublePointList.size(); j++) {
                workerTwoDimensionalDoublePoint = workerTwoDimensionalDoublePointList.get(j);
//                Double distance = BasicCalculation.get2Norm(taskPoint.getIndex(), workerPoint.getIndex());
                Double distance = BasicCalculation.getDistanceFrom2LngLat(taskTwoDimensionalDoublePoint.getYIndex(), taskTwoDimensionalDoublePoint.getXIndex(), workerTwoDimensionalDoublePoint.getYIndex(), workerTwoDimensionalDoublePoint.getXIndex());
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


    public static Integer getEqualPointNumberBetweenTwoList(List<TwoDimensionalDoublePoint> taskTwoDimensionalDoublePointList, List<TwoDimensionalDoublePoint> workerTwoDimensionalDoublePointList) {
        TwoDimensionalDoublePoint taskTwoDimensionalDoublePoint, workerTwoDimensionalDoublePoint;
        int equalNumber = 0;
        for (int i = 0; i < taskTwoDimensionalDoublePointList.size(); i++) {
            taskTwoDimensionalDoublePoint = taskTwoDimensionalDoublePointList.get(i);
            for (int j = 0; j < workerTwoDimensionalDoublePointList.size(); j++) {
                workerTwoDimensionalDoublePoint = workerTwoDimensionalDoublePointList.get(j);
                if (taskTwoDimensionalDoublePoint.equals(workerTwoDimensionalDoublePoint)) {
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
        List<TwoDimensionalDoublePoint> taskTwoDimensionalDoublePointList = PointRead.readPointWithFirstLineCount(taskPath);
        List<TwoDimensionalDoublePoint> workerTwoDimensionalDoublePointList = PointRead.readPointWithFirstLineCount(workerPath);
        Map<Double, Integer> resultMap = getDistanceStatisticDataFromNormData(taskTwoDimensionalDoublePointList, workerTwoDimensionalDoublePointList, 5);
        MyPrint.showMap(resultMap);
    }


}
