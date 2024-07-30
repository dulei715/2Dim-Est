package ecnu.dll.construction.run._1_total_run.main_repeat_process.version_2.total_run;

import cn.edu.dll.result.FileTool;
import cn.edu.dll.signal.CatchSignal;
import ecnu.dll.construction._config.Constant;
import ecnu.dll.construction.run._1_total_run.main_repeat_process.version_2.run_utils.RunUtils;

import java.io.File;
import java.util.List;

public class CrimeRun {
    public static void main(String[] args) {
        CatchSignal catchSignal = new CatchSignal();
        catchSignal.startCatch();

        String datasetTag = "crime";
        double[] epsilonArray = Constant.ALTER_PRIVACY_BUDGET_ARRAY;
        Double epsilonDefault = Constant.DEFAULT_PRIVACY_BUDGET;
        double[] sizeDArray = Constant.ALTER_SIDE_LENGTH_NUMBER_SIZE;
        Double sizeDDefault = Constant.DEFAULT_SIDE_LENGTH_NUMBER_SIZE;
        String basicOutputDirPath = FileTool.getPath(Constant.basicDatasetPath, "output", "round_"+args[0], datasetTag);
        File file = new File(basicOutputDirPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        RunUtils.runByThreads(datasetTag, epsilonArray, epsilonDefault, sizeDArray, sizeDDefault, basicOutputDirPath);
    }
}
