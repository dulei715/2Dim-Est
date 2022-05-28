package tools.cryptography.hash_function_impl;

import tools.cryptography.HashFunction;
import tools.number.BasicTransform;

import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class SimpleHashFunction extends HashFunction {

    private Integer rangeUpperBound = null;

    protected SimpleHashFunction(Integer key) throws NoSuchAlgorithmException, InvalidKeyException {
        super(new SecretKeySpec(BasicTransform.toByteValue(key), HashFunction.instanceName));
    }

    public SimpleHashFunction(Integer key, Integer rangeUpperBound) throws NoSuchAlgorithmException, InvalidKeyException {
        super(new SecretKeySpec(BasicTransform.toByteValue(key), HashFunction.instanceName));
        this.rangeUpperBound = rangeUpperBound;
    }


    public Integer getHashValue(Integer rawData) {
        byte[] tempResult = super.getHashByteValue(BasicTransform.toByteValue(rawData));
        byte[] validResult;
        if(tempResult.length <= 4) {
            validResult = tempResult;
        } else {
            validResult = new byte[4];
            for (int i = 0; i < validResult.length; i++) {
                validResult[i] = tempResult[i];
            }
        }
        Long middleResult = BasicTransform.toLong(validResult);
        int result = (int) (middleResult % this.rangeUpperBound);
        return result;
    }

    @Override
    public Long getHashValue(String rawDataStr) {
        return null;
    }
}
