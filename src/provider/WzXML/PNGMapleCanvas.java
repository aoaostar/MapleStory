package provider.WzXML;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.PixelInterleavedSampleModel;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.awt.image.WritableRaster;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import provider.MapleCanvas;

public class PNGMapleCanvas implements MapleCanvas
{
    private static final int[] ZAHLEN;
    private final int height;
    private final int width;
    private final int dataLength;
    private final int format;
    private final byte[] data;
    
    public PNGMapleCanvas(final int width, final int height, final int dataLength, final int format, final byte[] data) {
        this.height = height;
        this.width = width;
        this.dataLength = dataLength;
        this.format = format;
        this.data = data;
    }
    
    @Override
    public int getHeight() {
        return this.height;
    }
    
    @Override
    public int getWidth() {
        return this.width;
    }
    
    public int getFormat() {
        return this.format;
    }
    
    public byte[] getData() {
        return this.data;
    }
    
    @Override
    public BufferedImage getImage() {
        int sizeUncompressed = 0;
        int size8888 = 0;
        int maxWriteBuf = 2;
        int maxHeight = 3;
        byte[] writeBuf = new byte[maxWriteBuf];
        switch (this.getFormat()) {
            case 1:
            case 513: {
                sizeUncompressed = this.getHeight() * this.getWidth() * 4;
                break;
            }
            case 2: {
                sizeUncompressed = this.getHeight() * this.getWidth() * 8;
                break;
            }
            case 517: {
                sizeUncompressed = this.getHeight() * this.getWidth() / 128;
                break;
            }
        }
        size8888 = this.getHeight() * this.getWidth() * 8;
        if (size8888 > maxWriteBuf) {
            maxWriteBuf = size8888;
            writeBuf = new byte[maxWriteBuf];
        }
        if (this.getHeight() > maxHeight) {
            maxHeight = this.getHeight();
        }
        final Inflater dec = new Inflater();
        dec.setInput(this.getData(), 0, this.dataLength);
        int declen = 0;
        final byte[] uc = new byte[sizeUncompressed];
        try {
            declen = dec.inflate(uc);
        }
        catch (DataFormatException ex) {
            throw new RuntimeException("zlib fucks", ex);
        }
        dec.end();
        switch (this.getFormat()) {
            case 1: {
                for (int i = 0; i < sizeUncompressed; ++i) {
                    final byte low = (byte)(uc[i] & 0xF);
                    final byte high = (byte)(uc[i] & 0xF0);
                    writeBuf[i << 1] = (byte)((low << 4 | low) & 0xFF);
                    writeBuf[(i << 1) + 1] = (byte)(high | (high >>> 4 & 0xF));
                }
                break;
            }
            case 2: {
                writeBuf = uc;
                break;
            }
            case 513: {
                for (int i = 0; i < declen; i += 2) {
                    final byte bBits = (byte)((uc[i] & 0x1F) << 3);
                    final byte gBits = (byte)((uc[i + 1] & 0x7) << 5 | (uc[i] & 0xE0) >> 3);
                    final byte rBits = (byte)(uc[i + 1] & 0xF8);
                    writeBuf[i << 1] = (byte)(bBits | bBits >> 5);
                    writeBuf[(i << 1) + 1] = (byte)(gBits | gBits >> 6);
                    writeBuf[(i << 1) + 2] = (byte)(rBits | rBits >> 5);
                    writeBuf[(i << 1) + 3] = -1;
                }
                break;
            }
            case 517: {
                byte b = 0;
                int pixelIndex = 0;
                for (int j = 0; j < declen; ++j) {
                    for (int k = 0; k < 8; ++k) {
                        b = (byte)(((uc[j] & 1 << 7 - k) >> 7 - k) * 255);
                        for (int l = 0; l < 16; ++l) {
                            pixelIndex = (j << 9) + (k << 6) + l * 2;
                            writeBuf[pixelIndex] = b;
                            writeBuf[pixelIndex + 2] = (writeBuf[pixelIndex + 1] = b);
                            writeBuf[pixelIndex + 3] = -1;
                        }
                    }
                }
                break;
            }
        }
        final DataBufferByte imgData = new DataBufferByte(writeBuf, sizeUncompressed);
        final SampleModel sm = new PixelInterleavedSampleModel(0, this.getWidth(), this.getHeight(), 4, this.getWidth() * 4, PNGMapleCanvas.ZAHLEN);
        final WritableRaster imgRaster = Raster.createWritableRaster(sm, imgData, new Point(0, 0));
        final BufferedImage aa = new BufferedImage(this.getWidth(), this.getHeight(), 2);
        aa.setData(imgRaster);
        return aa;
    }
    
    static {
        ZAHLEN = new int[] { 2, 1, 0, 3 };
    }
}
