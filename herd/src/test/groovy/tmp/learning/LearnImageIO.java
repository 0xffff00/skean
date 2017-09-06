package tmp.learning;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.resizers.configurations.ScalingMode;
import org.apache.commons.lang.math.NumberUtils;
import party.threebody.herd.domain.ImageMedia;
import party.threebody.herd.util.ImageMetaUtils;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LearnImageIO {

    public static void main(String[] args) {
        //compress jpg
        Path root = Paths.get("D:\\dat0\\git0\\sk78\\skean\\herd\\src\\test\\resources");
        Path picsDir = root.resolve("pics1");
        Path destDir = root.resolve("pics1");
        try {

            Files.walk(picsDir).filter(Files::isRegularFile).filter(p -> !p.toString().contains("pictmp"))
                    .forEach(p -> {
                        System.out.println(p);

                        try {
                            ImageMedia imageMedia = ImageMetaUtils.parseExifInfo(p);
                            int w = imageMedia.getWidth();
                            int h = imageMedia.getHeight();
                            int maxw=4000;
                            int maxh=maxw*3/4;
                            double q = 0.6;
                            double r = calcRatio(w, h, maxw, maxh);
                            long fsize = Files.size(p);
                            BufferedImage bufferedImage = scaleJPG(Files.newInputStream(p), r);
                            String fname=String.format("%s.f%sq%s.jpg",p.getFileName(),maxw,(int)(q*10));
                            Path pDest = destDir.resolve("pictmp").resolve(fname);
                            ImageOutputStream ios = new FileImageOutputStream(pDest.toFile());
                            compressJPG(bufferedImage, ios, q);

                        } catch (IOException | ImageProcessingException e) {
                            e.printStackTrace();
                        }
                    });

            Files.walk(picsDir).filter(Files::isRegularFile)
                    .sorted((x,y)->x.getFileName().compareTo(y.getFileName()))
                    .forEach(p -> {
                        try {
                            ImageMedia imageMedia = ImageMetaUtils.parseExifInfo(p);
                            int w = imageMedia.getWidth();
                            int h = imageMedia.getHeight();
                            long fsize = Files.size(p);
                            double density = fsize * 100 / (double) (w * h);
                            System.out.printf("den=%06.4f %s\n", density, p.toString());

                        } catch (IOException | ImageProcessingException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * https://stackoverflow.com/questions/17108234/setting-jpg-compression-level-with-imageio-in-java
     */
    static void compressJPG(BufferedImage src, Path dest) throws IOException {


    }

    static void compressJPG(BufferedImage srcImage, ImageOutputStream dest, double q) throws IOException {
        final ImageWriter jpgWriter = ImageIO.getImageWritersByFormatName("jpg").next();
        final ImageWriteParam jpgWriteParam = jpgWriter.getDefaultWriteParam();
        jpgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        jpgWriteParam.setCompressionQuality((float) q);
        jpgWriter.setOutput(dest);
        IIOImage iioImage = new IIOImage(srcImage, null, null);
        jpgWriter.write(null, iioImage, jpgWriteParam);
        jpgWriter.dispose();
    }

    static double calcRatio(int w, int h, int maxw, int maxh) {
        return NumberUtils.min(maxw / (double) w, maxh / (double) h, 1.0);
    }

    /**
     * https://stackoverflow.com/questions/24745147/java-resize-image-without-losing-quality(sonight.jpg)
     */

    static BufferedImage scaleJPG(InputStream src, double ratio) {
        if (ratio>0.9999){
            try {
                return ImageIO.read(src);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            return Thumbnails.of(src).scalingMode(ScalingMode.PROGRESSIVE_BILINEAR)
                    .scale(ratio).asBufferedImage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    static void readAndPrintExif(Path src) throws ImageProcessingException, IOException {
        Metadata metadata = ImageMetadataReader.readMetadata(src.toFile());
        System.out.print(">>>>>>>>>>>>>>>>>>>>>>>>>>>" + src);
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                //System.out.println(tag);
                System.out.format("[%s] - %s = %s\n", directory.getName(), tag.getTagName(), tag.getDescription());
            }
            if (directory.hasErrors()) {
                for (String error : directory.getErrors()) {
                    System.err.format("ERROR: %s", error);
                }
            }
        }
    }


}
