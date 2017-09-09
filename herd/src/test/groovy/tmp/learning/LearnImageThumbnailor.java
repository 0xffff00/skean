package tmp.learning;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.resizers.configurations.ScalingMode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LearnImageThumbnailor {

    static String srcDir = "C:\\Users\\hzk\\Pictures\\623";
    static String destDir = "D:\\tmp\\herdThumbs\\FULLq7coob";

    public static void main(String[] args) {

        compressJPGs();
    }

    static void compressJPGs() {

        try {
            Files.createDirectories(Paths.get(destDir));
            Files.walk(Paths.get(srcDir)).filter(Files::isRegularFile)
                    .forEach(p -> {
                        Path dest = Paths.get(destDir).resolve(p.getFileName());
                        try {
                            compressJPG(p.toFile(), dest.toFile(), 0.7);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println(dest);
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    static void compressJPG(File file, File dest, double q) throws IOException {
        Thumbnails.of(file)
                .scalingMode(ScalingMode.PROGRESSIVE_BILINEAR)
                .scale(1)
                //.outputQuality(q)
                .toFile(dest);
    }
}
