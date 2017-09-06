package party.threebody.herd.util;

import com.drew.imaging.ImageProcessingException;
import org.junit.Test;
import party.threebody.herd.domain.ImageMedia;

import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class ImageProcessUtilsTest {

    @Test
    public  void calcRatio() {
        calcR(5184,3456,1000);
        calcR(4000,3000,1000);
        calcR(4208,3120,1000);
        calcR(1280,853,1000);
        calcR(5184,3456,720);
        calcR(4000,3000,720);
        calcR(4208,3120,720);
        calcR(1280,853,720);
        calcR(12840,8523,720);
        calcR(128404,85235,720);
        calcR(4648404,852355,720);
        calcR(753,675,720);
        calcR(432,124,720);

    }

    void calcR(int w,int h,int m){

        double r=ImageProcessUtils.calcRatio(w,h,m);
        System.out.printf("(%d,%d),min=%d -> (%.2f,%.2f)=%.1f, r=%f\n",w,h,m,w*r,h*r,w*h*r*r,r);
    }
}