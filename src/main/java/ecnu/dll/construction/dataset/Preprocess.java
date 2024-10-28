package ecnu.dll.construction.dataset;

import cn.edu.dll.basic.StringUtil;
import cn.edu.dll.constant_values.ConstantValues;
import cn.edu.dll.io.write.PointWrite;
import cn.edu.dll.struct.point.TwoDimensionalDoublePoint;
import com.csvreader.CsvReader;
import ecnu.dll.construction._config.Constant;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Preprocess {


    public static final String keyLatitudeCrime = "Latitude";
    public static final String keyLongitudeCrime = "Longitude";
    public static final String keyLatitudeNyc = "Pickup_latitude";
    public static final String keyLongitudeNyc = "Pickup_longitude";
//    public String outputPath = new String[]{
//            Constant.datasetPath + "\\test\\chicago_point_A.txt",
//            Constant.datasetPath + "\\test\\chicago_point_B.txt",
//            Constant.datasetPath + "\\test\\chicago_point_C.txt"
//    };
    public String outputPath = Constant.basicDatasetPath + "\\1_real\\1_crime\\chicago_point.txt";

//    public String path = "F:\\dataset\\nyc\\2016_green_taxi_trip_data.csv";
//    public String keyLatitude = "Pickup_latitude";
//    public String keyLongitude = "Pickup_longitude";
//    public String[] outputPathArray = new String[]{
//            "F:\\dataset\\test\\nyc_point_A.txt",
//            "F:\\dataset\\test\\nyc_point_B.txt",
//            "F:\\dataset\\test\\nyc_point_C.txt"
//    };

    public static void readAndOutputDataForChicago(String inputPath, String outputPath, String keyLatitude, String keyLongitude) throws IOException {


        List<TwoDimensionalDoublePoint> pointList = new ArrayList<>();

        CsvReader csvReader = new CsvReader(inputPath, ',', Charset.forName("UTF-8"));

        String valueLatitudeStr;
        String valueLongitudeStr;
        csvReader.readHeaders();
        double valueLatitude, valueLongitude;
        String[] headArray = csvReader.getHeaders();
//        int k = 0;
        while (csvReader.readRecord()) {
            valueLatitudeStr = csvReader.get(keyLatitude);
            valueLongitudeStr = csvReader.get(keyLongitude);
            if (valueLatitudeStr.isEmpty() || valueLongitudeStr.isEmpty()) {
                continue;
            }
            valueLatitude = Double.parseDouble(valueLatitudeStr);
            valueLongitude = Double.parseDouble(valueLongitudeStr);
            // chicago
            pointList.add(new TwoDimensionalDoublePoint(valueLatitude, valueLongitude));
//            if (valueLatitude >= 40 && valueLatitude <=42 && valueLongitude >= -87.9 && valueLongitude <= -87.54) {
//                pointList.add(new TwoDimensionalDoublePoint(valueLatitude, valueLongitude));
//            }
//            if (valueLatitude >= 40 && valueLatitude <=42.05 && valueLongitude >= -87.9 && valueLongitude <= -87.54) {
//                pointList.add(new TwoDimensionalDoublePoint(valueLatitude, valueLongitude));
//            }

        }
        PointWrite pointWrite = new PointWrite();
        pointWrite.startWriting(outputPath);
        pointWrite.writePoint(pointList);
        pointWrite.endWriting();


    }
    public static void readAndOutputDataForNYC(String inputPath, String outputPath, String keyLatitude, String keyLongitude) throws IOException {


        List<TwoDimensionalDoublePoint> pointList = new ArrayList<>();

        CsvReader csvReader = new CsvReader(inputPath, ',', Charset.forName("UTF-8"));

        String valueLatitudeStr;
        String valueLongitudeStr;
        csvReader.readHeaders();
        double valueLatitude, valueLongitude;
        String[] headArray = csvReader.getHeaders();
//        int k = 0;
        while (csvReader.readRecord()) {
            valueLatitudeStr = csvReader.get(keyLatitude);
            valueLongitudeStr = csvReader.get(keyLongitude);
            if (valueLatitudeStr.isEmpty() || valueLongitudeStr.isEmpty()) {
                continue;
            }
            valueLatitude = Double.parseDouble(valueLatitudeStr);
            valueLongitude = Double.parseDouble(valueLongitudeStr);
            // nyc
//            pointList.add(new TwoDimensionalDoublePoint(valueLatitude, valueLongitude));
            if (valueLatitude >= 40.55 && valueLatitude <=40.88 && valueLongitude >= -74.05 && valueLongitude <= -73.73) {
                pointList.add(new TwoDimensionalDoublePoint(valueLatitude, valueLongitude));
            }

//            if (valueLatitude >= 40.65 && valueLatitude <= 40.75 && valueLongitude >= -73.84 && valueLongitude <= -73.74) {
//                this.pointList[0].add(new TwoDimensionalDoublePoint(valueLatitude, valueLongitude));
//            }
//            if (valueLatitude >= 40.65 && valueLatitude <= 40.74 && valueLongitude >= -73.95 && valueLongitude <= -73.86) {
//                this.pointList[1].add(new TwoDimensionalDoublePoint(valueLatitude, valueLongitude));
//            }
//            if (valueLatitude >= 40.82 && valueLatitude <= 40.89 && valueLongitude >= -73.90 && valueLongitude <= -73.83) {
//                this.pointList[2].add(new TwoDimensionalDoublePoint(valueLatitude, valueLongitude));
//            }
        }
        PointWrite pointWrite = new PointWrite();
        pointWrite.startWriting(outputPath);
        pointWrite.writePoint(pointList);
        pointWrite.endWriting();


    }



    public static void main0(String[] args) throws IOException {
//        String inputPathChicago = Constant.datasetPath + "\\1_real\\1_crime\\Chicago_Crimes_2022_01_06.csv";
//        String outputPathChicago = Constant.datasetPath + "\\1_real\\1_crime\\chicago_point.txt";
//        readAndOutputData(inputPathChicago, outputPathChicago, Preprocess.keyLatitudeCrime, Preprocess.keyLongitudeCrime);
        String inputPathSyn = Constant.basicDatasetPath + "\\1_real\\2_nyc\\2016_green_taxi_trip_data.csv";
        String outputPathSyn = Constant.basicDatasetPath + "\\1_real\\2_nyc\\nyc_point.txt";
        readAndOutputDataForChicago(inputPathSyn, outputPathSyn, Preprocess.keyLatitudeNyc, Preprocess.keyLongitudeNyc);
    }
    public static void main(String[] args) throws IOException {
//        String inputPathChicago = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.basicDatasetPath, "1_real", "1_crime", "Chicago_Crimes_2022_01_06.csv");
//        String outputPathChicago = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.basicDatasetPath, "1_real", "1_crime", "chicago_point_total.txt");
//        readAndOutputDataForChicago(inputPathChicago, outputPathChicago, Preprocess.keyLatitudeCrime, Preprocess.keyLongitudeCrime);

        String inputPathSyn = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.basicDatasetPath, "1_real", "2_nyc", "2016_green_taxi_trip_data.csv");
        String outputPathSyn = StringUtil.join(ConstantValues.FILE_SPLIT, Constant.basicDatasetPath, "1_real", "2_nyc", "nyc_point_total.txt");
        readAndOutputDataForNYC(inputPathSyn, outputPathSyn, Preprocess.keyLatitudeNyc, Preprocess.keyLongitudeNyc);
    }



}
