package client;

import java.util.*;
import java.security.*;
import java.io.*;

public class LoginCryptoLegacy
{
    private static final Random rand;
    private static final char[] iota64;
    
    public static String hashPassword(final String password) {
        final byte[] randomBytes = new byte[6];
        LoginCryptoLegacy.rand.setSeed(System.currentTimeMillis());
        LoginCryptoLegacy.rand.nextBytes(randomBytes);
        return myCrypt(password, genSalt(randomBytes));
    }
    
    public static boolean checkPassword(final String password, final String hash) {
        return myCrypt(password, hash).equals(hash);
    }
    
    public static boolean isLegacyPassword(final String hash) {
        return hash.substring(0, 3).equals("$H$");
    }
    
    private static String myCrypt(final String password, String seed) throws RuntimeException {
        String out = null;
        int count = 8;
        if (!seed.substring(0, 3).equals("$H$")) {
            final byte[] randomBytes = new byte[6];
            LoginCryptoLegacy.rand.nextBytes(randomBytes);
            seed = genSalt(randomBytes);
        }
        final String salt = seed.substring(4, 12);
        if (salt.length() != 8) {
            throw new RuntimeException("Error hashing password - Invalid seed.");
        }
        try {
            final MessageDigest digester = MessageDigest.getInstance("SHA-1");
            digester.update((salt + password).getBytes("iso-8859-1"), 0, (salt + password).length());
            byte[] sha1Hash = digester.digest();
            do {
                final byte[] CombinedBytes = new byte[sha1Hash.length + password.length()];
                System.arraycopy(sha1Hash, 0, CombinedBytes, 0, sha1Hash.length);
                System.arraycopy(password.getBytes("iso-8859-1"), 0, CombinedBytes, sha1Hash.length, password.getBytes("iso-8859-1").length);
                digester.update(CombinedBytes, 0, CombinedBytes.length);
                sha1Hash = digester.digest();
            } while (--count > 0);
            out = seed.substring(0, 12);
            out += encode64(sha1Hash);
        }
        catch (NoSuchAlgorithmException | UnsupportedEncodingException ex2) {
            ex2.printStackTrace();
            System.err.println("Error hashing password." + ex2.getMessage());
        }
        if (out == null) {
            throw new RuntimeException("Error hashing password - out = null");
        }
        return out;
    }
    
    private static String genSalt(final byte[] Random) {
        final StringBuilder Salt = new StringBuilder("$H$");
        Salt.append(LoginCryptoLegacy.iota64[30]);
        Salt.append(encode64(Random));
        return Salt.toString();
    }
    
    private static String convertToHex(final byte[] data) {
        final StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; ++i) {
            int halfbyte = data[i] >>> 4 & 0xF;
            int two_halfs = 0;
            do {
                if (0 <= halfbyte && halfbyte <= 9) {
                    buf.append((char)(48 + halfbyte));
                }
                else {
                    buf.append((char)(97 + (halfbyte - 10)));
                }
                halfbyte = (data[i] & 0xF);
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }
    
    public static String encodeSHA1(final String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        final MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        return convertToHex(md.digest());
    }
    
    private static String encode64(final byte[] Input) {
        final int iLen = Input.length;
        final int oDataLen = (iLen * 4 + 2) / 3;
        final int oLen = (iLen + 2) / 3 * 4;
        final char[] out = new char[oLen];
        int i0;
        int i2;
        int i3;
        int o0;
        int o2;
        int o3;
        int o4;
        for (int ip = 0, op = 0; ip < iLen; i0 = (Input[ip++] & 0xFF), i2 = ((ip < iLen) ? (Input[ip++] & 0xFF) : 0), i3 = ((ip < iLen) ? (Input[ip++] & 0xFF) : 0), o0 = i0 >>> 2, o2 = ((i0 & 0x3) << 4 | i2 >>> 4), o3 = ((i2 & 0xF) << 2 | i3 >>> 6), o4 = (i3 & 0x3F), out[op++] = LoginCryptoLegacy.iota64[o0], out[op++] = LoginCryptoLegacy.iota64[o2], out[op] = ((op < oDataLen) ? LoginCryptoLegacy.iota64[o3] : '='), ++op, out[op] = ((op < oDataLen) ? LoginCryptoLegacy.iota64[o4] : '='), ++op) {}
        return new String(out);
    }
    
    static {
        rand = new Random();
        iota64 = new char[64];
        int i = 0;
        LoginCryptoLegacy.iota64[i++] = '.';
        LoginCryptoLegacy.iota64[i++] = '/';
        for (char c = 'A'; c <= 'Z'; ++c) {
            LoginCryptoLegacy.iota64[i++] = c;
        }
        for (char c = 'a'; c <= 'z'; ++c) {
            LoginCryptoLegacy.iota64[i++] = c;
        }
        for (char c = '0'; c <= '9'; ++c) {
            LoginCryptoLegacy.iota64[i++] = c;
        }
    }
}
