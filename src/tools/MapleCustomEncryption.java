package tools;

public class MapleCustomEncryption
{
    public static byte[] encryptData(final byte[] data) {
        for (int j = 0; j < 6; ++j) {
            byte remember = 0;
            byte dataLength = (byte)(data.length & 0xFF);
            if (j % 2 == 0) {
                for (int i = 0; i < data.length; ++i) {
                    byte cur = data[i];
                    cur = BitTools.rollLeft(cur, 3);
                    cur += dataLength;
                    cur = (remember ^= cur);
                    cur = BitTools.rollRight(cur, dataLength & 0xFF);
                    cur = (byte)(~cur & 0xFF);
                    cur += 72;
                    --dataLength;
                    data[i] = cur;
                }
            }
            else {
                for (int i = data.length - 1; i >= 0; --i) {
                    byte cur = data[i];
                    cur = BitTools.rollLeft(cur, 4);
                    cur += dataLength;
                    cur = (remember ^= cur);
                    cur ^= 0x13;
                    cur = BitTools.rollRight(cur, 3);
                    --dataLength;
                    data[i] = cur;
                }
            }
        }
        return data;
    }
    
    public static byte[] decryptData(final byte[] data) {
        for (int j = 1; j <= 6; ++j) {
            byte remember = 0;
            byte dataLength = (byte)(data.length & 0xFF);
            byte nextRemember = 0;
            if (j % 2 == 0) {
                for (int i = 0; i < data.length; ++i) {
                    byte cur = data[i];
                    cur -= 72;
                    cur = (byte)(~cur & 0xFF);
                    cur = (nextRemember = BitTools.rollLeft(cur, dataLength & 0xFF));
                    cur ^= remember;
                    remember = nextRemember;
                    cur -= dataLength;
                    cur = BitTools.rollRight(cur, 3);
                    data[i] = cur;
                    --dataLength;
                }
            }
            else {
                for (int i = data.length - 1; i >= 0; --i) {
                    byte cur = data[i];
                    cur = BitTools.rollLeft(cur, 3);
                    cur = (nextRemember = (byte)(cur ^ 0x13));
                    cur ^= remember;
                    remember = nextRemember;
                    cur -= dataLength;
                    cur = BitTools.rollRight(cur, 4);
                    data[i] = cur;
                    --dataLength;
                }
            }
        }
        return data;
    }
}
