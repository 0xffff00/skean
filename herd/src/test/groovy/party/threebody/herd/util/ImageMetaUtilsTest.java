package party.threebody.herd.util;

import org.junit.Test;
import party.threebody.herd.domain.ImageMedia;

import java.nio.file.Paths;

import static org.junit.Assert.*;

public class ImageMetaUtilsTest {

    @Test
    public void parseExifInfo() throws Exception{
        ImageMedia imageMedia=ImageMetaUtils.parseExifInfo(
                Paths.get("C:\\Users\\hzk\\Pictures\\623\\623toall\\mmexport1498244563795.jpg"));

        System.out.println(imageMedia);

    }
}