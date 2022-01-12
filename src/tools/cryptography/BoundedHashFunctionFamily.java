package tools.cryptography;

import tools.basic.BasicArray;
import tools.cryptography.hash_function_impl.BoundedHashFunction;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class BoundedHashFunctionFamily {
    protected List<Key> keyList = null;
    protected Integer[] functionIndexArray = null;
    protected BoundedHashFunction[] hashFunctionFamily = null;

    public BoundedHashFunctionFamily(int hashFunctionSize, int upperBound) {
        if (hashFunctionSize <= 0) {
            throw new RuntimeException("The size of hash function should be positive");
        }
        try {
            this.keyList = KeyUtils.getKeyList(HashFunction.instanceName, hashFunctionSize);
            functionIndexArray = BasicArray.getIncreaseIntegerNumberArray(0, 1, hashFunctionSize - 1);
            this.hashFunctionFamily = new BoundedHashFunction[hashFunctionSize];
            for (int i = 0; i < hashFunctionSize; i++) {
                this.hashFunctionFamily[i] = new BoundedHashFunction(this.keyList.get(i), upperBound);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    public Integer[] getFunctionIndexArray() {
        return this.functionIndexArray;
    }

    public HashFunction getFunction(Integer functionIndex) {
        if (functionIndex < 0) {
            throw new RuntimeException("The index of hash function should not be negative!");
        }
        return hashFunctionFamily[functionIndex];
    }

    public Long getHashValue(Integer functionIndex, String rawValueStr) {
        if (functionIndex < 0) {
            throw new RuntimeException("The index of hash function should not be negative!");
        }
        return hashFunctionFamily[functionIndex].getHashValue(rawValueStr);
    }

    public static void main(String[] args) {
        int hashFunctionSize = 10;
        BoundedHashFunctionFamily hashFunctionFamily = new BoundedHashFunctionFamily(hashFunctionSize, 100);
        String rawDataStr = "haufiaf;naskdvnc";
        for (int i = 0; i < hashFunctionSize; i++) {
            Long hashValue = hashFunctionFamily.getHashValue(i, rawDataStr);
            System.out.println(hashValue);
        }
    }



}
