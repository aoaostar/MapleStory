package tools;

import client.MapleCharacter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FileoutputUtil
{
    private static final SimpleDateFormat sdfT;
    public static String fixdam_mg;
    public static String fixdam_ph;
    public static String MobVac_log;
    public static String hack_log;
    public static String ban_log;
    public static String Acc_Stuck;
    public static String Login_Error;
    public static String Movement_Log;
    public static String IP_Log;
    public static String Zakum_Log;
    public static String Horntail_Log;
    public static String Pinkbean_Log;
    public static String ScriptEx_Log;
    public static String PacketEx_Log;
    public static final SimpleDateFormat sdf;
    private static final SimpleDateFormat sdf_;
    
    public static void logToFile_chr(final MapleCharacter chr, final String file, final String msg) {
        logToFile(file, "\r\n" + CurrentReadable_Time() + " 账号 " + chr.getClient().getAccountName() + " 名称 " + chr.getName() + " (" + chr.getId() + ") 等级 " + chr.getLevel() + " 地图 " + chr.getMapId() + " " + msg, false);
    }
    
    public static void logToFile(final String file, final String msg) {
        logToFile(file, msg, false);
    }
    
    public static void logToFile(final String file, final String msg, final boolean notExists) {
        FileOutputStream out = null;
        try {
            File outputFile = new File(file);
            if (outputFile.exists() && outputFile.isFile() && outputFile.length() >= 10240000L) {
                outputFile.renameTo(new File(file.substring(0, file.length() - 4) + "_" + FileoutputUtil.sdfT.format(Calendar.getInstance().getTime()) + file.substring(file.length() - 4, file.length())));
                outputFile = new File(file);
            }
            if (outputFile.getParentFile() != null) {
                outputFile.getParentFile().mkdirs();
            }
            out = new FileOutputStream(file, true);
            if (!out.toString().contains(msg) || !notExists) {
                final OutputStreamWriter osw = new OutputStreamWriter(out, "UTF-8");
                osw.write(msg);
                osw.flush();
            }
        }
        catch (IOException ex) {}
        finally {
            try {
                if (out != null) {
                    out.close();
                }
            }
            catch (IOException ex2) {}
        }
    }
    
    public static void packetLog(final String file, final String msg) {
        final boolean notExists = false;
        FileOutputStream out = null;
        try {
            File outputFile = new File(file);
            if (outputFile.exists() && outputFile.isFile() && outputFile.length() >= 1024000L) {
                outputFile.renameTo(new File(file.substring(0, file.length() - 4) + "_" + FileoutputUtil.sdfT.format(Calendar.getInstance().getTime()) + file.substring(file.length() - 4, file.length())));
                outputFile = new File(file);
            }
            if (outputFile.getParentFile() != null) {
                outputFile.getParentFile().mkdirs();
            }
            out = new FileOutputStream(file, true);
            if (!out.toString().contains(msg) || !notExists) {
                final OutputStreamWriter osw = new OutputStreamWriter(out, "UTF-8");
                osw.write(msg);
                osw.flush();
            }
        }
        catch (IOException ex) {}
        finally {
            try {
                if (out != null) {
                    out.close();
                }
            }
            catch (IOException ex2) {}
        }
    }
    
    public static void log(final String file, final String msg) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file, true);
            out.write(("\r\n------------------------ " + CurrentReadable_Time() + " ------------------------\r\n").getBytes());
            out.write(msg.getBytes());
        }
        catch (IOException ex) {}
        finally {
            try {
                if (out != null) {
                    out.close();
                }
            }
            catch (IOException ex2) {}
        }
    }
    
    public static void outputFileError(final String file, final Throwable t) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file, true);
            out.write(("\r\n------------------------ " + CurrentReadable_Time() + " ------------------------\r\n").getBytes());
            out.write(getString(t).getBytes());
        }
        catch (IOException ex) {}
        finally {
            try {
                if (out != null) {
                    out.close();
                }
            }
            catch (IOException ex2) {}
        }
    }
    
    public static String CurrentReadable_Date() {
        return FileoutputUtil.sdf_.format(Calendar.getInstance().getTime());
    }
    
    public static String CurrentReadable_Time() {
        return FileoutputUtil.sdf.format(Calendar.getInstance().getTime());
    }
    
    public static String getString(final Throwable e) {
        String retValue = null;
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            retValue = sw.toString();
        }
        finally {
            try {
                if (pw != null) {
                    pw.close();
                }
                if (sw != null) {
                    sw.close();
                }
            }
            catch (IOException ex) {}
        }
        return retValue;
    }
    
    public static String NowTime() {
        final Date now = new Date();
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        final String hehe = dateFormat.format(now);
        return hehe;
    }
    
    public static void hiredMerchLog(final String file, final String msg) {
        final String newfile = "logs/雇佣商人/" + file + ".txt";
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(newfile, true);
            out.write(("[" + CurrentReadable_Time() + "] ").getBytes());
            out.write(msg.getBytes());
            out.write("\r\n".getBytes());
        }
        catch (IOException ex) {}
        finally {
            try {
                if (out != null) {
                    out.close();
                }
            }
            catch (IOException ex2) {}
        }
    }
    
    static {
        sdfT = new SimpleDateFormat("yyyy年MM月dd日HH時mm分ss秒");
        FileoutputUtil.fixdam_mg = "logs/魔法伤害修正.rtf";
        FileoutputUtil.fixdam_ph = "logs/物理伤害修正.rtf";
        FileoutputUtil.MobVac_log = "logs/Log_吸怪.txt";
        FileoutputUtil.hack_log = "logs/Log_怀疑外挂.rtf";
        FileoutputUtil.ban_log = "logs/Log_封号.rtf";
        FileoutputUtil.Acc_Stuck = "logs/Log_卡账号.rtf";
        FileoutputUtil.Login_Error = "logs/Log_登录错误.rtf";
        FileoutputUtil.Movement_Log = "logs/移动出错.log";
        FileoutputUtil.IP_Log = "logs/Log_账号IP.rtf";
        FileoutputUtil.Zakum_Log = "logs/Log_扎昆.rtf";
        FileoutputUtil.Horntail_Log = "logs/Log_暗黑龙王.rtf";
        FileoutputUtil.Pinkbean_Log = "logs/Log_品克缤.rtf";
        FileoutputUtil.ScriptEx_Log = "logs/Log_Script_脚本异常.rtf";
        FileoutputUtil.PacketEx_Log = "logs/Log_Packet_封包异常.rtf";
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf_ = new SimpleDateFormat("yyyy-MM-dd");
    }
}
