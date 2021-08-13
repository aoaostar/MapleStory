package scripting;

import java.io.File;

public class EncodingDetect
{
    public static String getJavaEncode(final String filePath) {
        return getJavaEncode(new File(filePath));
    }
    
    public static String getJavaEncode(final File file) {
        final BytesEncodingDetect s = new BytesEncodingDetect();
        final String fileCode = BytesEncodingDetect.javaname[s.detectEncoding(file)];
        return fileCode;
    }
}
