package provider.WzXML;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import provider.MapleCanvas;

public class FileStoredPngMapleCanvas implements MapleCanvas
{
    private final File file;
    private int width;
    private int height;
    private BufferedImage image;
    
    public FileStoredPngMapleCanvas(final int width, final int height, final File fileIn) {
        this.width = width;
        this.height = height;
        this.file = fileIn;
    }
    
    @Override
    public int getHeight() {
        return this.height;
    }
    
    @Override
    public int getWidth() {
        return this.width;
    }
    
    @Override
    public BufferedImage getImage() {
        this.loadImageIfNecessary();
        return this.image;
    }
    
    private void loadImageIfNecessary() {
        if (this.image == null) {
            try {
                this.image = ImageIO.read(this.file);
                this.width = this.image.getWidth();
                this.height = this.image.getHeight();
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
