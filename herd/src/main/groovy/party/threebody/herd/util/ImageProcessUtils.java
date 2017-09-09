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




    /**
     * compress to a JPG, using quality parameter. EXIF will be loss.
     * according Java API, jpgWriter.setOutput can only set an OutputStream
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


    /**
     *
     * @param srcImage src image
     * @return a java BufferedImage
     * @throws IOException
     */
    public static BufferedImage scaleImage(InputStream srcImage, double scaling) throws IOException {
        return scaleImage(ImageIO.read(srcImage), scaling);
    }

    public static BufferedImage scaleImage(BufferedImage srcImage, double scaling) throws IOException {
        if (scaling==1){
            return srcImage;
        }
        //PROGRESSIVE_BILINEAR maybe best
        return Thumbnails.of(srcImage).scalingMode(ScalingMode.PROGRESSIVE_BILINEAR)
                .scale(scaling).asBufferedImage();
    }


}
