package tools;

public class BitTools
{
    public static int getShort(final byte[] array, final int index) {
        int ret = array[index];
        ret &= 0xFF;
        ret |= (array[index + 1] << 8 & 0xFF00);
        return ret;
    }
    
    public static String getString(final byte[] array, final int index, final int length) {
        final char[] cret = new char[length];
        for (int x = 0; x < length; ++x) {
            cret[x] = (char)array[x + index];
        }
        return String.valueOf(cret);
    }
    
    public static String getMapleString(final byte[] array, final int index) {
        final int length = (array[index] & 0xFF) | (array[index + 1] << 8 & 0xFF00);
        return getString(array, index + 2, length);
    }
    
    public static byte rollLeft(final byte in, final int count) {
        int tmp = in & 0xFF;
        tmp <<= count % 8;
        return (byte)((tmp & 0xFF) | tmp >> 8);
    }
    
    public static byte rollRight(final byte in, final int count) {
        int tmp = in & 0xFF;
        tmp = tmp << 8 >>> count % 8;
        return (byte)((tmp & 0xFF) | tmp >>> 8);
    }
    
    public static byte[] multiplyBytes(final byte[] in, final int count, final int mul) {
        final byte[] ret = new byte[count * mul];
        for (int x = 0; x < count * mul; ++x) {
            ret[x] = in[x % count];
        }
        return ret;
    }
    
    public static int doubleToShortBits(final double d) {
        final long l = Double.doubleToLongBits(d);
        return (int)(l >> 48);
    }
}
