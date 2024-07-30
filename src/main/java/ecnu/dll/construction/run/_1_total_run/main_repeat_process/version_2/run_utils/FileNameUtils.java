package ecnu.dll.construction.run._1_total_run.main_repeat_process.version_2.run_utils;

public class FileNameUtils {
    public static String getFileNameByBudgetAndSizeD(Double budget, Integer sizeD) {
        return String.format("p_%s_d_%d", String.valueOf(budget).replace(".", "-"), sizeD);
    }
}
