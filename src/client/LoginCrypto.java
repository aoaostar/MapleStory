package client;

import java.util.*;
import tools.*;
import java.security.*;
import java.io.*;

public class LoginCrypto
{
    protected static int extralength;
    private static final String[] Alphabet;
    private static final String[] Number;
    private static final Random rand;
    
    public static String Generate_13DigitAsiasoftPassport() {
        final StringBuilder sb = new StringBuilder();
        sb.append(LoginCrypto.Alphabet[LoginCrypto.rand.nextInt(LoginCrypto.Alphabet.length)]);
        for (int i = 0; i < 11; ++i) {
            sb.append(LoginCrypto.Number[LoginCrypto.rand.nextInt(LoginCrypto.Number.length)]);
        }
        sb.append(LoginCrypto.Alphabet[LoginCrypto.rand.nextInt(LoginCrypto.Alphabet.length)]);
        return sb.toString();
    }
    
    private static String toSimpleHexString(final byte[] bytes) {
        return HexTool.toString(bytes).replace(" ", "").toLowerCase();
    }
    
    private static String hashWithDigest(final String in, final String digest) {
        try {
            final MessageDigest Digester = MessageDigest.getInstance(digest);
            Digester.update(in.getBytes("UTF-8"), 0, in.length());
            final byte[] sha1Hash = Digester.digest();
            return toSimpleHexString(sha1Hash);
        }
        catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException("Hashing the password failed", ex);
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Encoding the string failed", e);
        }
    }
    
    public static String hexSha1(final String in) {
        return hashWithDigest(in, "SHA-1");
    }
    
    private static String hexSha512(final String in) {
        return hashWithDigest(in, "SHA-512");
    }
    
    public static boolean checkSha1Hash(final String hash, final String password) {
        return hash.equals(makeSaltedSha1Hash(password));
    }
    
    public static boolean checkSaltedSha512Hash(final String hash, final String password, final String salt) {
        return hash.equals(makeSaltedSha512Hash(password, salt));
    }
    
    public static String makeSaltedSha512Hash(final String password, final String salt) {
        return hexSha512(password + salt);
    }
    
    public static String makeSaltedSha1Hash(final String password) {
        return hexSha1(password);
    }
    
    public static String makeSalt() {
        final byte[] salt = new byte[16];
        LoginCrypto.rand.nextBytes(salt);
        return toSimpleHexString(salt);
    }
    
    public static String rand_s(final String in) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < LoginCrypto.extralength; ++i) {
            sb.append(LoginCrypto.rand.nextBoolean() ? LoginCrypto.Alphabet[LoginCrypto.rand.nextInt(LoginCrypto.Alphabet.length)] : LoginCrypto.Number[LoginCrypto.rand.nextInt(LoginCrypto.Number.length)]);
        }
        return sb.toString() + in;
    }
    
    public static String rand_r(final String in) {
        return in.substring(LoginCrypto.extralength, LoginCrypto.extralength + 128);
    }
    
    static {
        LoginCrypto.extralength = 6;
        Alphabet = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
        Number = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9" };
        rand = new Random();
    }
}
