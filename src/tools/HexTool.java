package tools;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import org.apache.mina.core.buffer.IoBuffer;

public class HexTool
{
    private static final char[] HEX;
    
    public static String toString(final byte byteValue) {
        final int tmp = byteValue << 8;
        final char[] retstr = { HexTool.HEX[tmp >> 12 & 0xF], HexTool.HEX[tmp >> 8 & 0xF] };
        return String.valueOf(retstr);
    }
    
    public static String toString(final IoBuffer buf) {
        buf.flip();
        final byte[] arr = new byte[buf.remaining()];
        buf.get(arr);
        final String ret = toString(arr);
        buf.flip();
        buf.put(arr);
        return ret;
    }
    
    public static String toString(final int intValue) {
        return Integer.toHexString(intValue);
    }
    
    public static String toString(final byte[] bytes) {
        final StringBuilder hexed = new StringBuilder();
        for (int i = 0; i < bytes.length; ++i) {
            hexed.append(toString(bytes[i]));
            hexed.append(' ');
        }
        return hexed.substring(0, hexed.length() - 1);
    }
    
    public static String toStringFromAscii(final byte[] bytes) {
        final byte[] ret = new byte[bytes.length];
        for (int x = 0; x < bytes.length; ++x) {
            if (bytes[x] < 32 && bytes[x] >= 0) {
                ret[x] = 46;
            }
            else {
                final int chr = bytes[x] & 0xFF;
                ret[x] = (byte)chr;
            }
        }
        final String encode = "gbk";
        try {
            final String str = new String(ret, encode);
            return str;
        }
        catch (UnsupportedEncodingException ex) {
            return "";
        }
    }
    
    public static String toPaddedStringFromAscii(final byte[] bytes) {
        final String str = toStringFromAscii(bytes);
        final StringBuilder ret = new StringBuilder(str.length() * 3);
        for (int i = 0; i < str.length(); ++i) {
            ret.append(str.charAt(i));
            ret.append("  ");
        }
        return ret.toString();
    }
    
    public static byte[] getByteArrayFromHexString(final String hex) {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int nexti = 0;
        int nextb = 0;
        boolean highoc = true;
    Block_2:
        while (true) {
            int number = -1;
            while (number == -1) {
                if (nexti == hex.length()) {
                    break Block_2;
                }
                final char chr = hex.charAt(nexti);
                if (chr >= '0' && chr <= '9') {
                    number = chr - '0';
                }
                else if (chr >= 'a' && chr <= 'f') {
                    number = chr - 'a' + 10;
                }
                else if (chr >= 'A' && chr <= 'F') {
                    number = chr - 'A' + 10;
                }
                else {
                    number = -1;
                }
                ++nexti;
            }
            if (highoc) {
                nextb = number << 4;
                highoc = false;
            }
            else {
                nextb |= number;
                highoc = true;
                baos.write(nextb);
            }
        }
        return baos.toByteArray();
    }
    
    public static String getOpcodeToString(final int op) {
        return "0x" + StringUtil.getLeftPaddedStr(Integer.toHexString(op).toUpperCase(), '0', 4);
    }
    
    static {
        HEX = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
    }
}
