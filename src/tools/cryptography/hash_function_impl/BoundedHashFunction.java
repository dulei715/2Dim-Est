package tools.cryptography.hash_function_impl;

import tools.cryptography.HashFunction;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;

public class BoundedHashFunction extends HashFunction {
    protected Integer upperBound = null;
//    private Key key = null;
//    private Random random = null;


    public BoundedHashFunction(Key key, Integer upperBound) throws InvalidKeyException {
        super(key);
        this.upperBound = upperBound;
    }

    public BoundedHashFunction(Integer upperBound) {
        super();
        this.upperBound = upperBound;
    }


    public Long getHashValue(byte[] rawValue) {
        // todo: 此处暂时设置为long，之后根据需要改成 BigInteger
        Long hashLongValue = getHashLongValue(rawValue);
        long reminder = hashLongValue % this.upperBound;
        if (reminder < 0) {
            reminder = this.upperBound + reminder;
        }
        // 保证返回的是 1 到 upperBound之间的整数
        return reminder + 1;
    }

    @Override
    public Long getHashValue(String rawValueStr) {
        byte[] rawValue = rawValueStr.getBytes();
        return getHashValue(rawValue);
    }

    public static void main(String[] args) {
        BoundedHashFunction hashFunction = new BoundedHashFunction(100);
        String data = "sdfjoifjovnldznsvd;o";
        BigInteger result = hashFunction.getHashBigIntegerValue(data);
        Long result2 = hashFunction.getHashValue(data);
        System.out.println(result);
        System.out.println(result2);
    }

}
