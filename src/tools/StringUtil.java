package tools;

import java.nio.charset.Charset;

public class StringUtil
{
    public static String getLeftPaddedStr(final String in, final char padchar, final int length) {
        final StringBuilder builder = new StringBuilder(length);
        for (int x = in.getBytes().length; x < length; ++x) {
            builder.append(padchar);
        }
        builder.append(in);
        return builder.toString();
    }
    
    public static int getlength(final String str) {
        final byte[] bt = str.getBytes(Charset.forName("GBK"));
        return bt.length;
    }
    
    public static String getRightPaddedStr(final String in, final char padchar, final int length) {
        final StringBuilder builder = new StringBuilder(in);
        for (int x = in.getBytes().length; x < length; ++x) {
            builder.append(padchar);
        }
        return builder.toString();
    }
    
    public static String joinStringFrom(final String[] arr, final int start) {
        return joinStringFrom(arr, start, " ");
    }
    
    public static String joinStringFrom(final String[] arr, final int start, final String sep) {
        final StringBuilder builder = new StringBuilder();
        for (int i = start; i < arr.length; ++i) {
            builder.append(arr[i]);
            if (i != arr.length - 1) {
                builder.append(sep);
            }
        }
        return builder.toString();
    }
    
    public static String makeEnumHumanReadable(final String enumName) {
        final StringBuilder builder = new StringBuilder(enumName.length() + 1);
        for (final String word : enumName.split("_")) {
            if (word.length() <= 2) {
                builder.append(word);
            }
            else {
                builder.append(word.charAt(0));
                builder.append(word.substring(1).toLowerCase());
            }
            builder.append(' ');
        }
        return builder.substring(0, enumName.length());
    }
    
    public static int countCharacters(final String str, final char chr) {
        int ret = 0;
        for (int i = 0; i < str.getBytes().length; ++i) {
            if (str.charAt(i) == chr) {
                ++ret;
            }
        }
        return ret;
    }
    
    public static String getReadableMillis(final long startMillis, final long endMillis) {
        final StringBuilder sb = new StringBuilder();
        final double elapsedSeconds = (endMillis - startMillis) / 1000.0;
        final int elapsedSecs = (int)elapsedSeconds % 60;
        final int elapsedMinutes = (int)(elapsedSeconds / 60.0);
        final int elapsedMins = elapsedMinutes % 60;
        final int elapsedHrs = elapsedMinutes / 60;
        final int elapsedHours = elapsedHrs % 24;
        final int elapsedDays = elapsedHrs / 24;
        if (elapsedDays > 0) {
            final boolean mins = elapsedHours > 0;
            sb.append(elapsedDays);
            sb.append(" day").append((elapsedDays > 1) ? "s" : "").append(mins ? ", " : ".");
            if (mins) {
                final boolean secs = elapsedMins > 0;
                if (!secs) {
                    sb.append("and ");
                }
                sb.append(elapsedHours);
                sb.append(" hour").append((elapsedHours > 1) ? "s" : "").append(secs ? ", " : ".");
                if (secs) {
                    final boolean millis = elapsedSecs > 0;
                    if (!millis) {
                        sb.append("and ");
                    }
                    sb.append(elapsedMins);
                    sb.append(" minute").append((elapsedMins > 1) ? "s" : "").append(millis ? ", " : ".");
                    if (millis) {
                        sb.append("and ");
                        sb.append(elapsedSecs);
                        sb.append(" second").append((elapsedSecs > 1) ? "s" : "").append(".");
                    }
                }
            }
        }
        else if (elapsedHours > 0) {
            final boolean mins = elapsedMins > 0;
            sb.append(elapsedHours);
            sb.append(" hour").append((elapsedHours > 1) ? "s" : "").append(mins ? ", " : ".");
            if (mins) {
                final boolean secs = elapsedSecs > 0;
                if (!secs) {
                    sb.append("and ");
                }
                sb.append(elapsedMins);
                sb.append(" minute").append((elapsedMins > 1) ? "s" : "").append(secs ? ", " : ".");
                if (secs) {
                    sb.append("and ");
                    sb.append(elapsedSecs);
                    sb.append(" second").append((elapsedSecs > 1) ? "s" : "").append(".");
                }
            }
        }
        else if (elapsedMinutes > 0) {
            final boolean secs2 = elapsedSecs > 0;
            sb.append(elapsedMinutes);
            sb.append(" minute").append((elapsedMinutes > 1) ? "s" : "").append(secs2 ? " " : ".");
            if (secs2) {
                sb.append("and ");
                sb.append(elapsedSecs);
                sb.append(" second").append((elapsedSecs > 1) ? "s" : "").append(".");
            }
        }
        else if (elapsedSeconds > 0.0) {
            sb.append((int)elapsedSeconds);
            sb.append(" second").append((elapsedSeconds > 1.0) ? "s" : "").append(".");
        }
        else {
            sb.append("None.");
        }
        return sb.toString();
    }
    
    public static int getDaysAmount(final long startMillis, final long endMillis) {
        final double elapsedSeconds = (endMillis - startMillis) / 1000.0;
        final int elapsedMinutes = (int)(elapsedSeconds / 60.0);
        final int elapsedHrs = elapsedMinutes / 60;
        final int elapsedDays = elapsedHrs / 24;
        return elapsedDays;
    }
}
