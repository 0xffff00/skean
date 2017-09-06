package party.threebody.herd.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.resizers.configurations.ScalingMode;
import org.apache.commons.lang.math.NumberUtils;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * https://stackoverflow.com/questions/24745147/java-resize-image-without-losing-quality
 * https://stackoverflow.com/questions/17108234/setting-jpg-compression-level-with-imageio-in-java
 */
public class ImageProcessUtils {

    private ImageProcessUtils() {
    }


    public static ImageOutputStream compressToJPG(BufferedImage srcImage, Path destPath, double quality) throws IOException {
        ImageOutputStream ios = new FileImageOutputStream(destPath.toFile());
        compressToJPG(srcImage, ios, quality);
        return ios;
    }

    /**
     * compress to a JPG, using quality parameter. EXIF will be loss.
     */
    protected static void compressToJPG(BufferedImage srcImage, ImageOutputStream dest, double quality) throws IOException {

        final ImageWriter jpgWriter = ImageIO.getImageWritersByFormatName("jpg").next();
        final ImageWriteParam jpgWriteParam = jpgWriter.getDefaultWriteParam();
        jpgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        jpgWriteParam.setCompressionQuality((float) quality);
        jpgWriter.setOutput(dest);
        IIOImage iioImage = new IIOImage(srcImage, null, null);
        jpgWriter.write(null, iioImage, jpgWriteParam);
        jpgWriter.dispose();
    }

    static double calcRatio(int w, int h, int maxw, int maxh) {
        return NumberUtils.min(maxw / (double) w, maxh / (double) h, 1.0);
    }


    static final double MAX_RATIO_AVALIABLE = 0.9999;

    static final double[] BEST_RATIOS = {
            1 / 12.0,    //0.08330
            1 / 10.0,    //0.10000
            1 / 8.0,    //0.125
            3 / 16.0,   //0.188
            1 / 4.0,    //0.250
            3 / 8.0,    //0.375
            1 / 2.0,    //0.500
            3 / 5.0,    //0.600
            2 / 3.0,    //0.667
            3 / 4.0,    //0.750
            4 / 5.0,    //0.800
            7 / 8.0,    //0.875
    };

    /**
     * calc a ratio r that ：
     * w1=r*w, h1=r*h
     * w1>=min,h1>=min
     * also, denominator of r in an irreducible fraction style should be better to be smallest
     *
     * @param w   width
     * @param h   height
     * @param min min length
     * @return
     */
    public static double calcRatio(int w, int h, int min) {
        double r0 = min / (double) Math.min(w, h);
        if (r0 > 1) {
            return 1;
        }
        int n = BEST_RATIOS.length, i = n - 1;
        for (; i != 0; i--) {
            if (r0 > BEST_RATIOS[i]) {
                return BEST_RATIOS[i + 1];
            }
        }

        return r0;


    }

    /**
     * 3:2
     * 4:3
     * 16:9
     * 8:3
     *
     * @param srcImage src image
     * @return a java BufferedImage
     * @throws IOException
     */
    public static BufferedImage scaleImageByMinEdge(InputStream srcImage, int minEdge) throws IOException {
        return scaleImage(ImageIO.read(srcImage), minEdge);
    }
    public static BufferedImage scaleImageByMinEdge(BufferedImage srcImage, int minEdge) throws IOException {
        int h = srcImage.getHeight();
        int w = srcImage.getWidth();
        double r=calcRatio(w,h,minEdge);
        return scaleImage(srcImage, r);
    }



    public static BufferedImage scaleImage(InputStream srcImage, int pixels) throws IOException {
        return scaleImage(ImageIO.read(srcImage), pixels);
    }

    public static BufferedImage scaleImage(BufferedImage srcImage, int pixels) throws IOException {
        int h = srcImage.getHeight();
        int w = srcImage.getWidth();
        double ratio = pixels / (double) (h * w);
        return scaleImage(srcImage, ratio);
    }

    public static BufferedImage scaleImage(InputStream srcImage, double ratio) throws IOException {
        return scaleImage(ImageIO.read(srcImage), ratio);
    }

    public static BufferedImage scaleImage(BufferedImage srcImage, double ratio) throws IOException {
        if (ratio > MAX_RATIO_AVALIABLE) {
            //don't scale if ratio too close to 1
            return srcImage;
        }
        //PROGRESSIVE_BILINEAR maybe best
        return Thumbnails.of(srcImage).scalingMode(ScalingMode.PROGRESSIVE_BILINEAR)
                .scale(ratio).asBufferedImage();
    }


}
