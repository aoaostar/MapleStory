package tools.wztosql;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import provider.MapleData;
import provider.MapleDataProvider;
import provider.MapleDataProviderFactory;

public class WzStringDumper宠物数据
{
    public static void main(final String[] args) throws FileNotFoundException, IOException {
        final File stringFile = MapleDataProviderFactory.fileInwzPath("String.wz");
        final MapleDataProvider stringProvider = MapleDataProviderFactory.getDataProvider(stringFile);
        final MapleData pet = stringProvider.getData("Pet.img");
        final String output = args[0];
        final File outputDir = new File(output);
        final File petTxt = new File(output + "/Pet.txt");
        outputDir.mkdir();
        petTxt.createNewFile();
        System.out.println("开始提取宠物数据....");
        try (final PrintWriter writer = new PrintWriter(new FileOutputStream(petTxt))) {
            for (final MapleData child : pet.getChildren()) {
                writer.println("INSERT INTO `cashshop_modified_items` VALUES ('600500', '8000', '0', '1', '" + child.getName() + "', '0', '0', '0', '2', '1', '0', '0', '0', '0', '0'");
            }
            writer.flush();
        }
        System.out.println("宠物数据提取完成....");
    }
}
