package tools.differential_privacy.ldp.frequency_oracle.foImp;

import tools.RandomUtil;
import tools.cryptography.BoundedHashFunctionFamily;
import tools.cryptography.HashFunction;
import tools.differential_privacy.ldp.frequency_oracle.FrequencyOracle;
import tools.differential_privacy.ldp.frequency_oracle.basic_struct.HashFunctionResponsePair;

public class OptimizedLocalHashing implements FrequencyOracle<String, HashFunctionResponsePair> {

    protected Integer g = null;
    protected String[] data = null;
    public Integer[] hashFunctionIndexArray = null;
    public BoundedHashFunctionFamily hashFunctionFamily = null;
    public GeneralizedRandomizedResponse<Long> gRR = null;

    public OptimizedLocalHashing(double epsilon, int functionArraySize, int upperBound) {
        this.g = upperBound;
        this.hashFunctionFamily = new BoundedHashFunctionFamily(functionArraySize, this.g);
        this.hashFunctionIndexArray = this.hashFunctionFamily.getFunctionIndexArray();
        this.gRR = new GeneralizedRandomizedResponse(epsilon, this.hashFunctionIndexArray);

    }


    //todo: design hash function first
    @Override
    public HashFunctionResponsePair perturb(String rawData) {
        Integer hashFunctionIndex = RandomUtil.getRandomInteger(this.hashFunctionIndexArray[0], this.hashFunctionIndexArray[this.hashFunctionIndexArray.length - 1]);
        HashFunction hashFunction = this.hashFunctionFamily.getFunction(hashFunctionIndex);
        Long hashValue = hashFunction.getHashValue(rawData);
        Long responseValue = this.gRR.perturb(hashValue);
        return new HashFunctionResponsePair(hashFunction, responseValue);
    }

    @Override
    public double aggregate(HashFunctionResponsePair data, int noiseEstimate) {
        return 0;
    }
}
