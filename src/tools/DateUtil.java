package tools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil
{
    private static final long FT_UT_OFFSET = 116444520000000000L;
    private static final SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
    private static final SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-mm-dd");

    public static boolean isDST() {
        return TimeZone.getDefault().inDaylightTime(new Date());
    }
    
    public static long getFileTimestamp(final long timeStampinMillis) {
        return getFileTimestamp(timeStampinMillis, false);
    }
    
    public static long getFileTimestamp(long timeStampinMillis, final boolean roundToMinutes) {
        if (isDST()) {
            timeStampinMillis -= 3600000L;
        }
        timeStampinMillis += 50400000L;
        long time;
        if (roundToMinutes) {
            time = timeStampinMillis / 1000L / 60L * 600000000L;
        }
        else {
            time = timeStampinMillis * 10000L;
        }
        return time + 116444520000000000L;
    }

    public static String getCurrentDateStr() {
        return sdf1.format(new Date());
    }

    public static String getCurrentDateStr2() {
        return sdf2.format(new Date());
    }
}
