package tools.io.read;

import tools.io.print.MyPrint;
import tools.struct.point.TwoDimensionalPoint;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PointRead {

    public static final String SPLIT_TAG = " ";

    protected List<TwoDimensionalPoint> twoDimensionalPointList = null;
    protected String filePath;
    protected Integer dataSize = null;

    public PointRead(String filePath) {
        this.filePath = filePath;
    }

    /**
     * The first line should be the size of the dataset
     */
    public void readPointWithFirstLineCount(int scale) {
        BufferedReader bufferedReader = null;
        String line = null;
        String[] dataElement;
        int i = 0;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(this.filePath)));
            this.dataSize = Integer.valueOf(bufferedReader.readLine());
            this.twoDimensionalPointList = new ArrayList<>(this.dataSize);
            while ((line = bufferedReader.readLine()) != null) {
                ++i;
                if (i % scale != 0) {
                    continue;
                }
                dataElement = line.split(SPLIT_TAG);
                this.twoDimensionalPointList.add(new TwoDimensionalPoint(Double.valueOf(dataElement[0]), Double.valueOf(dataElement[1])));
            }
//            if (!this.dataSize.equals(this.pointList.size())) {
//                throw new RuntimeException("The size of dataset is not inconsistent!");
//            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Integer readPointSizeWithFirstLineCount(String filePath) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
            return Integer.valueOf(bufferedReader.readLine());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void readPointWithFirstLineCount() {
        this.readPointWithFirstLineCount(1);
    }

    public static List<TwoDimensionalPoint> readPointWithFirstLineCount(String filePath) {
        BufferedReader bufferedReader = null;
        String line = null;
        String[] dataElement;
        int dataSize;
        List<TwoDimensionalPoint> twoDimensionalPointList = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
            dataSize = Integer.valueOf(bufferedReader.readLine());
            twoDimensionalPointList = new ArrayList<>(dataSize);
            while ((line = bufferedReader.readLine()) != null) {
                dataElement = line.split(SPLIT_TAG);
                twoDimensionalPointList.add(new TwoDimensionalPoint(Double.valueOf(dataElement[0]), Double.valueOf(dataElement[1])));
            }
            if (dataSize != twoDimensionalPointList.size()) {
                throw new RuntimeException("The size of dataset is not inconsistent!");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return twoDimensionalPointList;
    }

    public static List<TwoDimensionalPoint> readTopKPointWithFirstLineCount(String filePath, int k) {
        BufferedReader bufferedReader = null;
        String line = null;
        String[] dataElement;
        int dataSize;
        List<TwoDimensionalPoint> twoDimensionalPointList = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
            dataSize = Integer.valueOf(bufferedReader.readLine());
            twoDimensionalPointList = new ArrayList<>(dataSize);
            int i = 0;
            while ((line = bufferedReader.readLine()) != null && i < k) {
                dataElement = line.split(SPLIT_TAG);
                twoDimensionalPointList.add(new TwoDimensionalPoint(Double.valueOf(dataElement[0]), Double.valueOf(dataElement[1])));
                ++i;
            }
            if (dataSize != twoDimensionalPointList.size()) {
                throw new RuntimeException("The size of dataset is not inconsistent!");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return twoDimensionalPointList;
    }

    public List<TwoDimensionalPoint> getPointList() {
        return twoDimensionalPointList;
    }

    public String getFilePath() {
        return filePath;
    }

    public Integer getTotalDataSize() {
        return dataSize;
    }

    public Integer getRealDataSize() {
        return this.twoDimensionalPointList.size();
    }

    public static void main(String[] args) {
        String filePath = "E:\\1.学习\\4.论文\\程鹏\\dataset\\dataset\\Chengdu\\chengdu.node";
        PointRead pointRead = new PointRead(filePath);
        pointRead.readPointWithFirstLineCount();
        List<TwoDimensionalPoint> twoDimensionalPointSet = pointRead.getPointList();
        MyPrint.showList(twoDimensionalPointSet);
    }
}
