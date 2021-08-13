package tools.wztosql;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import provider.MapleData;
import provider.MapleDataProvider;
import provider.MapleDataProviderFactory;

public class WzStringDumper装备数据
{
    public static void main(final String[] args) throws FileNotFoundException, IOException {
        final File stringFile = MapleDataProviderFactory.fileInwzPath("string.wz");
        final MapleDataProvider stringProvider = MapleDataProviderFactory.getDataProvider(stringFile);
        final MapleData cash = stringProvider.getData("Cash.img");
        final MapleData consume = stringProvider.getData("Consume.img");
        final MapleData eqp = stringProvider.getData("Eqp.img").getChildByPath("Eqp");
        final MapleData etc = stringProvider.getData("Etc.img").getChildByPath("Etc");
        final MapleData ins = stringProvider.getData("Ins.img");
        final MapleData pet = stringProvider.getData("Pet.img");
        final MapleData map = stringProvider.getData("Map.img");
        final MapleData mob = stringProvider.getData("Mob.img");
        final MapleData skill = stringProvider.getData("Skill.img");
        final MapleData npc = stringProvider.getData("Npc.img");
        final String output = args[0];
        final File outputDir = new File(output);
        final File cashTxt = new File(output + "/Cash.txt");
        final File useTxt = new File(output + "/Use.txt");
        final File eqpDir = new File(output + "/Equip");
        final File etcTxt = new File(output + "/Etc.txt");
        final File insTxt = new File(output + "/Setup.txt");
        final File petTxt = new File(output + "/Pet.txt");
        final File mapTxt = new File(output + "/Map.txt");
        final File mobTxt = new File(output + "/Mob.txt");
        final File skillTxt = new File(output + "/Skill.txt");
        final File npcTxt = new File(output + "/NPC.txt");
        outputDir.mkdir();
        cashTxt.createNewFile();
        useTxt.createNewFile();
        eqpDir.mkdir();
        etcTxt.createNewFile();
        insTxt.createNewFile();
        petTxt.createNewFile();
        mapTxt.createNewFile();
        mobTxt.createNewFile();
        skillTxt.createNewFile();
        npcTxt.createNewFile();
        System.out.println("提取 Cash.img 數據...");
        PrintWriter writer = new PrintWriter(new FileOutputStream(cashTxt));
        for (final MapleData child : cash.getChildren()) {
            final MapleData nameData = child.getChildByPath("name");
            final MapleData descData = child.getChildByPath("desc");
            String name = "";
            String desc = "(無描述)";
            if (nameData != null) {
                name = (String)nameData.getData();
            }
            if (descData != null) {
                desc = (String)descData.getData();
            }
            writer.println(child.getName() + " - " + name + " - " + desc);
        }
        writer.flush();
        writer.close();
        System.out.println("Cash.img 提取完成.");
        System.out.println("提取 Consume.img 數據...");
        writer = new PrintWriter(new FileOutputStream(useTxt));
        for (final MapleData child : consume.getChildren()) {
            final MapleData nameData = child.getChildByPath("name");
            final MapleData descData = child.getChildByPath("desc");
            String name = "";
            String desc = "(無描述)";
            if (nameData != null) {
                name = (String)nameData.getData();
            }
            if (descData != null) {
                desc = (String)descData.getData();
            }
            writer.println(child.getName() + " - " + name + " - " + desc);
        }
        writer.flush();
        writer.close();
        System.out.println("Consume.img 提取完成.");
        System.out.println("提取 Eqp.img 數據...");
        for (final MapleData child : eqp.getChildren()) {
            System.out.println("提取 " + child.getName() + " 數據...");
            final File eqpFile = new File(output + "/Equip/" + child.getName() + ".txt");
            eqpFile.createNewFile();
            final PrintWriter eqpWriter = new PrintWriter(new FileOutputStream(eqpFile));
            for (final MapleData child2 : child.getChildren()) {
                final MapleData nameData2 = child2.getChildByPath("name");
                final MapleData descData2 = child2.getChildByPath("desc");
                String name2 = "";
                String desc2 = "(無描述)";
                if (nameData2 != null) {
                    name2 = (String)nameData2.getData();
                }
                if (descData2 != null) {
                    desc2 = (String)descData2.getData();
                }
                eqpWriter.println("INSERT INTO `cashshop_modified_items` VALUES ('200900', '4888', '1', '1', '" + child2.getName() + "', '0', '0', '0', '2', '1', '0', '0', '0', '0', '0'");
            }
            eqpWriter.flush();
            eqpWriter.close();
            System.out.println(child.getName() + " 提取完成.");
        }
        System.out.println("Eqp.img 提取完成.");
        System.out.println("提取 Etc.img 數據...");
        writer = new PrintWriter(new FileOutputStream(etcTxt));
        for (final MapleData child : etc.getChildren()) {
            final MapleData nameData = child.getChildByPath("name");
            final MapleData descData = child.getChildByPath("desc");
            String name = "";
            String desc = "(無描述)";
            if (nameData != null) {
                name = (String)nameData.getData();
            }
            if (descData != null) {
                desc = (String)descData.getData();
            }
            writer.println(child.getName() + " - " + name + " - " + desc);
        }
        writer.flush();
        writer.close();
        System.out.println("Etc.img 提取完成.");
        System.out.println("提取 Ins.img 數據...");
        writer = new PrintWriter(new FileOutputStream(insTxt));
        for (final MapleData child : ins.getChildren()) {
            final MapleData nameData = child.getChildByPath("name");
            final MapleData descData = child.getChildByPath("desc");
            String name = "";
            String desc = "(無描述)";
            if (nameData != null) {
                name = (String)nameData.getData();
            }
            if (descData != null) {
                desc = (String)descData.getData();
            }
            writer.println(child.getName() + " - " + name + " - " + desc);
        }
        writer.flush();
        writer.close();
        System.out.println("Ins.img 提取完成.");
        System.out.println("提取 Pet.img 數據...");
        writer = new PrintWriter(new FileOutputStream(petTxt));
        for (final MapleData child : pet.getChildren()) {
            final MapleData nameData = child.getChildByPath("name");
            final MapleData descData = child.getChildByPath("desc");
            String name = "";
            String desc = "(無描述)";
            if (nameData != null) {
                name = (String)nameData.getData();
            }
            if (descData != null) {
                desc = (String)descData.getData();
            }
            writer.println(child.getName() + " - " + name + " - " + desc);
        }
        writer.flush();
        writer.close();
        System.out.println("Pet.img 提取完成.");
        System.out.println("提取 Map.img 數據...");
        writer = new PrintWriter(new FileOutputStream(mapTxt));
        for (final MapleData child : map.getChildren()) {
            writer.println(child.getName());
            writer.println();
            for (final MapleData child3 : child.getChildren()) {
                final MapleData streetData = child3.getChildByPath("streetName");
                final MapleData mapData = child3.getChildByPath("mapName");
                String streetName = "(無數據名)";
                String mapName = "(无地图名)";
                if (streetData != null) {
                    streetName = (String)streetData.getData();
                }
                if (mapData != null) {
                    mapName = (String)mapData.getData();
                }
                writer.println(child3.getName() + " - " + streetName + " - " + mapName);
            }
            writer.println();
        }
        writer.flush();
        writer.close();
        System.out.println("Map.img 提取完成.");
        System.out.println("提取 Mob.img 數據...");
        writer = new PrintWriter(new FileOutputStream(mobTxt));
        for (final MapleData child : mob.getChildren()) {
            final MapleData nameData = child.getChildByPath("name");
            String name3 = "";
            if (nameData != null) {
                name3 = (String)nameData.getData();
            }
            writer.println(child.getName() + " - " + name3);
        }
        writer.flush();
        writer.close();
        System.out.println("Mob.img 提取完成.");
        System.out.println("提取 Skill.img 數據...");
        writer = new PrintWriter(new FileOutputStream(skillTxt));
        for (final MapleData child : skill.getChildren()) {
            final MapleData nameData = child.getChildByPath("name");
            final MapleData descData = child.getChildByPath("desc");
            final MapleData bookData = child.getChildByPath("bookName");
            String name4 = "";
            String desc3 = "";
            if (nameData != null) {
                name4 = (String)nameData.getData();
            }
            if (descData != null) {
                desc3 = (String)descData.getData();
            }
            if (bookData == null) {
                writer.println(child.getName() + " - " + name4 + " - " + desc3);
            }
        }
        writer.flush();
        writer.close();
        System.out.println("Skill.img 提取完成.");
        System.out.println("提取 Npc.img 數據...");
        writer = new PrintWriter(new FileOutputStream(npcTxt));
        for (final MapleData child : npc.getChildren()) {
            final MapleData nameData = child.getChildByPath("name");
            String name3 = "";
            if (nameData != null) {
                name3 = (String)nameData.getData();
            }
            writer.println(child.getName() + " - " + name3);
        }
        writer.flush();
        writer.close();
        System.out.println("Npc.img 提取完成.");
    }
}
