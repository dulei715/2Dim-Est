package tools.cryptography;


import tools.io.print.MyPrint;

import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class KeyUtils {
    public static List<Key> getKeyList(String keyName, int keyNumber) throws NoSuchAlgorithmException {
        if (keyNumber <= 0) {
            throw new RuntimeException("The key number should be positive!");
        }
        KeyGenerator keyGenerator = KeyGenerator.getInstance(keyName);
        List<Key> keyList = new ArrayList<>(keyNumber);
        for (int i = 0; i < keyNumber; i++) {
            keyList.add(keyGenerator.generateKey());
        }
        return keyList;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String keyName = "HmacSHA1";
        int keyNumber = 10;
        List<Key> keyList = KeyUtils.getKeyList(keyName, keyNumber);
        MyPrint.showList(keyList, "\r\n");
    }

}
