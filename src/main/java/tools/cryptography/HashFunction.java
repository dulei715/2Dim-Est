package tools.cryptography;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public abstract class HashFunction {

    public static final String instanceName = "HmacSHA1";
    public static KeyGenerator DEFAULT_KEY_GENERATOR;

    protected Mac mac = null;

    protected Key key = null;

    static {
        try {
            DEFAULT_KEY_GENERATOR = KeyGenerator.getInstance(instanceName);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    protected HashFunction(Key key) throws InvalidKeyException {
        this.key = key;
        try {
            this.mac = Mac.getInstance(instanceName);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        this.mac.init(key);
    }

    public HashFunction() {
        this.key = DEFAULT_KEY_GENERATOR.generateKey();
        try {
            this.mac = Mac.getInstance(instanceName);
            this.mac.init(this.key);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    protected byte[] getHashByteValue(byte[] rawData) {
        return this.mac.doFinal(rawData);
    }

    protected byte[] getHashByteValue(String rawDataStr) {
        byte[] rawData = rawDataStr.getBytes();
        return this.mac.doFinal(rawData);
    }

    protected BigInteger getHashBigIntegerValue(byte[] rawData) {
        byte[] hashByteValue = getHashByteValue(rawData);
        BigInteger bigInteger = new BigInteger(hashByteValue);
        return bigInteger;
    }

    protected BigInteger getHashBigIntegerValue(String rawDataStr) {
        byte[] rawData = rawDataStr.getBytes();
        byte[] hashByteValue = getHashByteValue(rawData);
        BigInteger bigInteger = new BigInteger(hashByteValue);
        return bigInteger;
    }

    public Long getHashLongValue(byte[] rawData) {
        byte[] hashByteValue = getHashByteValue(rawData);
        BigInteger bigInteger = new BigInteger(hashByteValue);
        return bigInteger.longValue();
    }

    public Long getHashLongValue(String rawDataStr) {
        byte[] rawData = rawDataStr.getBytes();
        byte[] hashByteValue = getHashByteValue(rawData);
        BigInteger bigInteger = new BigInteger(hashByteValue);
        return bigInteger.longValue();
    }

    public abstract Long getHashValue(String rawDataStr);



    public static void main(String[] args) throws NoSuchAlgorithmException {
//        MessageDigest messageDigest = MessageDigest.getInstance("sha1");
//        byte[] digest = messageDigest.digest("hello".getBytes());
//        MyPrint.showByteArray(digest, ",");

    }
}
