package tools.differential_privacy.ldp.frequency_oracle.basic_struct;

import tools.cryptography.HashFunction;

public class HashFunctionResponsePair {
    private HashFunction hashFunction = null;
    private Long responseValue = null;

    public HashFunctionResponsePair() {
    }

    public HashFunctionResponsePair(HashFunction hashFunction, Long responseValue) {
        this.hashFunction = hashFunction;
        this.responseValue = responseValue;
    }

    public HashFunction getHashFunction() {
        return hashFunction;
    }

    public void setHashFunction(HashFunction hashFunction) {
        this.hashFunction = hashFunction;
    }

    public Long getResponseValue() {
        return responseValue;
    }

    public void setResponseValue(Long responseValue) {
        this.responseValue = responseValue;
    }
}
