package tmp.learning;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class LearnImageIO {

    public static void main(String[] args) {
        //compress jpg
        try {
            Path root = Paths.get("D:\\dat0\\git0\\sk78\\skean\\herd\\src\\test\\resources");
            Path picsDir = root.resolve("pics1");
            Path destDir = root.resolve("pics1");
            Files.walk(picsDir).filter(Files::isRegularFile).filter(p -> !p.toString().contains(".q"))
                    .forEach(p -> {
                        System.out.println(p);
                        try {
                            compressJPG(p, destDir.resolve(p.getFileName().toString() + ".q5.jpg"));
                        } catch (IOException e) {
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
    static void compressJPG(Path src, Path dest) throws IOException {
        BufferedImage srcImage = ImageIO.read(src.toFile());
        File destFile = dest.toFile();
        final ImageWriter jpgWriter = ImageIO.getImageWritersByFormatName("jpg").next();
        final ImageWriteParam jpgWriteParam = jpgWriter.getDefaultWriteParam();

        jpgWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        jpgWriteParam.setCompressionQuality(0.5f);
        jpgWriter.setOutput(new FileImageOutputStream(destFile));
        IIOImage iioImage = new IIOImage(srcImage, null, null);
        jpgWriter.write(null, iioImage, jpgWriteParam);
        jpgWriter.dispose();

    }
/**
 *  https://stackoverflow.com/questions/24745147/java-resize-image-without-losing-quality(sonight.jpg)
 *
 */

    static void scaleJPG(){

    }

}
