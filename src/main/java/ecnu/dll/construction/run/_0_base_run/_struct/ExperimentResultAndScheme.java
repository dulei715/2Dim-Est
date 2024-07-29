package ecnu.dll.construction.run._0_base_run._struct;

import cn.edu.dll.result.ExperimentResult;
import ecnu.dll.construction.newscheme.discretization.AbstractDiscretizedScheme;

public class ExperimentResultAndScheme {
    private ExperimentResult experimentResult;
    private AbstractDiscretizedScheme abstractDiscretizedScheme;

    public ExperimentResultAndScheme(ExperimentResult experimentResult, AbstractDiscretizedScheme abstractDiscretizedScheme) {
        this.experimentResult = experimentResult;
        this.abstractDiscretizedScheme = abstractDiscretizedScheme;
    }

    public ExperimentResult getExperimentResult() {
        return experimentResult;
    }

    public AbstractDiscretizedScheme getAbstractDiscretizedScheme() {
        return abstractDiscretizedScheme;
    }
}
