package ecnu.dll.construction.run._2_split_run._1_grid_statistic;

import cn.edu.ecnu.basic.StringUtil;
import cn.edu.ecnu.constant_values.ConstantValues;
import cn.edu.ecnu.io.print.MyPrint;
import cn.edu.ecnu.io.read.TwoDimensionalPointRead;
import cn.edu.ecnu.struct.point.TwoDimensionalDoublePoint;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.dataset.struct.DataSetAreaInfo;
import ecnu.dll.construction.run._1_total_run.main_process.c_data_set_run.DataSetRun;
import ecnu.dll.construction.run._2_split_run._1_grid_statistic.basic_run.GridStatistic;

import java.util.List;

public class GridStatisticHandle {

    public static void main(String[] args) {
        new GridStatisticHandle().handleDataSet(Constant.crimeDataSetArray);
    }



    public void handleDataSet(DataSetAreaInfo[] datasetInfoArray) {
//        DataSetAreaInfo[] crimeDataSetArray = Constant.crimeDataSetArray;
//        DataSetAreaInfo[] nycDataSetArray = Constant.nycDataSetArray;
//        DataSetAreaInfo twoDimNormalDataSet = Constant.twoDimNormalDataSet;
//        DataSetAreaInfo twoDimZipfDataSet = Constant.twoDimZipfDataSet;
//        DataSetAreaInfo twoDimNormalMultiCentersDataSet = Constant.twoDimMultipleCenterNormalDataSet;

        System.out.println("Start Crime Grid Statistic Handle ...");
        String dataSetPath, outputPath;
        double xBound, yBound;
        double length;
        for (DataSetAreaInfo dataSetInfo : datasetInfoArray) {

            dataSetPath = dataSetInfo.getDataSetPath();
            TwoDimensionalPointRead pointRead = new TwoDimensionalPointRead(dataSetPath);
            pointRead.readPointWithFirstLineCount();
            List<TwoDimensionalDoublePoint> doublePointList = pointRead.getPointList();

            // 处理 b 的变化
            outputPath = StringUtil.join(ConstantValues.FILE_SPLIT, dataSetInfo.getParentPath(), Constant.B_CHANGE_DIRECTORY_NAME, Constant.RAW_DATA_DEFAULT_DISTRIBUTION_FILE_NAME);
            xBound = dataSetInfo.getxBound();
            yBound = dataSetInfo.getyBound();
            length = dataSetInfo.getLength();
//            GridStatistic.readAndGridDataAndWriteStatistic(dataSetPath, dataSetName, xBound, yBound, length);
            new Thread(new GridStatistic(doublePointList, outputPath, xBound, yBound, length, Constant.DEFAULT_SIDE_LENGTH_NUMBER_SIZE_for_b_change)).start();


            // 处理 budget 的变化
            outputPath = StringUtil.join(ConstantValues.FILE_SPLIT, dataSetInfo.getParentPath(), Constant.BUDGET_CHANGE_DIRECTORY_NAME, Constant.RAW_DATA_DEFAULT_DISTRIBUTION_FILE_NAME);
            new Thread(new GridStatistic(doublePointList, outputPath, xBound, yBound, length, Constant.DEFAULT_SIDE_LENGTH_NUMBER_SIZE));
            // 处理 d 的变化
        }

    }
}
