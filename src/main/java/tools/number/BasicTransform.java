package tools.number;

import java.util.LinkedList;

public class BasicTransform {
    public static byte[] toByteValue(Integer data) {
        if (data == null) {
            return null;
        }
        if (data.equals(0)) {
            return new byte[]{0};
        }
        LinkedList<Byte> linkedResult = new LinkedList<>();
        Byte temp;
        while (data > 0) {
            temp = data.byteValue();
            linkedResult.addFirst(temp);
            data >>= 8;
        }
        byte[] result = new byte[linkedResult.size()];
        int i = 0;
        for (Byte aByte : linkedResult) {
            result[i++] = aByte;
        }
        return result;
    }

    public static Integer toInteger(byte[] bytes) {
        int len = bytes.length;
        if (len == 1) {
            return new Integer(bytes[0]);
        }
        int result = 0;
        for (int i = 0; i < len; i++) {
            result <<= 8;
            result += Byte.toUnsignedInt(bytes[i]);
        }
        return result;
    }

    public static Long toLong(byte[] bytes) {
        int len = bytes.length;
        if (len == 1) {
            return new Long(bytes[0]);
        }
        Long result = 0L;
        for (int i = 0; i < len; i++) {
            result <<= 8;
            result += Byte.toUnsignedLong(bytes[i]);
        }
        return result;
    }

}
