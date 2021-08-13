package tools;

import handling.RecvPacketOpcode;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Formatter;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

public class GetInfo {
    public static void main(String[] args) {
        Config();
        getConfig();
        all();
//        System.setProperty("server_property_file_path","E:/game/2020mxd079/079sever/HuaiMS_服务端配置.properties");
//        System.setProperty("server_property_db_path","E:/game/2020mxd079/079sever/HuaiMS_数据库配置.properties");
//        System.setProperty("server_property_shop_path","E:/game/2020mxd079/079sever/HuaiMS_封商城道具.properties");
//        System.setProperty("server_property_fish_path","E:/game/2020mxd079/079sever/HuaiMS_钓鱼设置.properties");


    }

    public static void getIpconfig() {
        Map<String, String> map = System.getenv();
        System.out.println(map);
        System.out.println(map.get("USERNAME"));
        System.out.println(map.get("COMPUTERNAME"));
        System.out.println(map.get("USERDOMAIN"));
        System.out.println(map.get("USER"));
    }

    public static void all() {
        Properties 設定檔 = System.getProperties();
        System.out.println("Java的運行環境版本：" + 設定檔.getProperty("java.version"));
        System.out.println("Java的運行環境供應商：" + 設定檔.getProperty("java.vendor"));
        System.out.println("Java供應商的URL：" + 設定檔.getProperty("java.vendor.url"));
        System.out.println("Java的安裝路徑：" + 設定檔.getProperty("java.home"));
        System.out.println("Java的虛擬機規範版本：" + 設定檔.getProperty("java.vm.specification.version"));
        System.out.println("Java的虛擬機規範供應商：" + 設定檔.getProperty("java.vm.specification.vendor"));
        System.out.println("Java的虛擬機規範名稱：" + 設定檔.getProperty("java.vm.specification.name"));
        System.out.println("Java的虛擬機實現版本：" + 設定檔.getProperty("java.vm.version"));
        System.out.println("Java的虛擬機實現供應商：" + 設定檔.getProperty("java.vm.vendor"));
        System.out.println("Java的虛擬機實現名稱：" + 設定檔.getProperty("java.vm.name"));
        System.out.println("Java運行時環境規範版本：" + 設定檔.getProperty("java.specification.version"));
        System.out.println("Java運行時環境規範名稱：" + 設定檔.getProperty("java.specification.name"));
        System.out.println("Java的類格式版本號：" + 設定檔.getProperty("java.class.version"));
        System.out.println("Java的類路徑：" + 設定檔.getProperty("java.class.path"));
        System.out.println("加載庫時搜索的路徑列表：" + 設定檔.getProperty("java.library.path"));
        System.out.println("默認的臨時文件路徑：" + 設定檔.getProperty("java.io.tmpdir"));
        System.out.println("一個或多個擴展目錄的路徑：" + 設定檔.getProperty("java.ext.dirs"));
        System.out.println("操作系統的構架：" + 設定檔.getProperty("os.arch"));
        System.out.println("操作系統的版本：" + 設定檔.getProperty("os.version"));
        System.out.println("文件分隔符：" + 設定檔.getProperty("file.separator"));
        System.out.println("路徑分隔符：" + 設定檔.getProperty("path.separator"));
        System.out.println("行分隔符：" + 設定檔.getProperty("line.separator"));
        System.out.println("用戶的賬戶名稱：" + 設定檔.getProperty("user.name"));
        System.out.println("用戶的主目錄：" + 設定檔.getProperty("user.home"));
        System.out.println("用戶的當前工作目錄：" + 設定檔.getProperty("user.dir"));
    }

    public static void Config() {
        try {
            InetAddress addr = InetAddress.getLocalHost();
            String ip = addr.getHostAddress();
            String hostName = addr.getHostName();
            System.out.println("本機IP：" + ip + "\n本機名稱:" + hostName);
            Properties 設定檔 = System.getProperties();
            System.out.println("操作系統的名稱：" + 設定檔.getProperty("os.name"));
            System.out.println("操作系統的版本：" + 設定檔.getProperty("os.version"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static void getConfig() {
        try {
            InetAddress address = InetAddress.getLocalHost();
            NetworkInterface ni = NetworkInterface.getByInetAddress(address);
            byte[] mac = ni.getHardwareAddress();
            if (mac == null)
                mac = (ni.getInetAddresses().nextElement()).getAddress();
            String sIP = address.getHostAddress();
            String sMAC = "";
            Formatter formatter = new Formatter();
            for (int i = 0; i < mac.length; i++) {
                sMAC = formatter.format(Locale.getDefault(), "%02X%s", new Object[] { Byte.valueOf(mac[i]), (i < mac.length - 1) ? "-" : "" }).toString();
            }
            System.out.println("IP：" + sIP);
            System.out.println("MAC：" + sMAC);
        } catch (SocketException|UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
