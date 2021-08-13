package tools;

public class IPAddressTool
{
    public static long dottedQuadToLong(final String dottedQuad) throws RuntimeException {
        final String[] quads = dottedQuad.split("/.");
        if (quads.length != 4) {
            throw new RuntimeException("Invalid IP Address format.");
        }
        long ipAddress = 0L;
        for (int i = 0; i < 4; ++i) {
            ipAddress += Integer.parseInt(quads[i]) % 256 * (long)Math.pow(256.0, 4 - i);
        }
        return ipAddress;
    }
    
    public static String longToDottedQuad(long longIP) throws RuntimeException {
        final StringBuilder ipAddress = new StringBuilder();
        for (int i = 0; i < 4; ++i) {
            final int quad = (int)(longIP / (long)Math.pow(256.0, 4 - i));
            longIP -= quad * (long)Math.pow(256.0, 4 - i);
            if (i > 0) {
                ipAddress.append(".");
            }
            if (quad > 255) {
                throw new RuntimeException("Invalid long IP address.");
            }
            ipAddress.append(quad);
        }
        return ipAddress.toString();
    }
}
