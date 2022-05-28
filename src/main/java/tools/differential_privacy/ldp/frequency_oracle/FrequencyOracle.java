package tools.differential_privacy.ldp.frequency_oracle;

public interface FrequencyOracle<I,O> {
    /**
     * Used by each user to perturb her input value
     * @param rawData
     * @return
     */
    O perturb(I rawData);

    /**
     * Used by the aggregator to aggregate and unbia values
     * @param data
     * @return
     */
    double aggregate(O data, int noiseEstimate);
}
