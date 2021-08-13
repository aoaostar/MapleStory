package tools.wztosql;

import java.util.*;
import tools.*;
import handling.*;
import java.io.*;

public class ConvertOpcodes
{
    public static void main(final String[] args) throws IOException {
        final StringBuilder sb = new StringBuilder();
        final Scanner input = new Scanner(System.in);
        System.out.println("欢迎来到操作代码转换器。  \r\n你的操作码将被转换成十六进制和十进制数字（无论你选择），  \r\n它们将被保存在一个新的文本文件中。 .");
        System.out.println("你想转换的操作码是什么？十六进制还是十进制？ ");
        final boolean decimal = "十进制的".equals(input.next().toLowerCase());
        sb.append("RecvOps.txt 转换为十六进制数据:").append("\r\n");
        for (final RecvPacketOpcode recv : RecvPacketOpcode.values()) {
            sb.append("\r\n").append(recv.name()).append(" = ").append(decimal ? Short.valueOf(recv.getValue()) : HexTool.getOpcodeToString(recv.getValue()));
        }
        System.out.println("\r\n请输入文本文件名文件将被保存到新的操作码：  \r\n");
        final FileOutputStream out = new FileOutputStream(input.next() + ".txt", false);
        sb.append("SendOps.txt 转换为十六进制数据:").append("\r\n");
        for (final SendPacketOpcode send : SendPacketOpcode.values()) {
            sb.append("\r\n").append(send.name()).append(" = ").append(decimal ? Short.valueOf(send.getValue()) : HexTool.getOpcodeToString(send.getValue()));
        }
        System.out.println("\r\n\r\n");
        out.write(sb.toString().getBytes());
    }
}
