package party.threebody.herd.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.resizers.configurations.ScalingMode;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import party.threebody.skean.lang.DateTimeFormatters;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 *
 */
public class ImageConverter {
    static final Logger logger = LoggerFactory.getLogger(ImageConverter.class);

    private String name;
    private int maxWidth;
    private int minWidth;
    private int maxHeight;
    private int minHeight;
    private boolean priorToMin; //when min and max constraints conflicts (min>max), prior to min constraint

    private double compressQuality;
    private double bppThresToTryNoCompress;  // min BPP(bit per pixel) threshold that make compression enabled

    private double fixedScaling;
    private boolean scalingPreferSimpleFraction;

    private MediaType targetType;

    private ImageConverter() {
        this.compressQuality = 0.6;
        this.scalingPreferSimpleFraction = true;
        this.bppThresToTryNoCompress = 0;
        this.priorToMin = false;
        this.name = DateTimeFormatters.PURENUM_YEAR2SEC.format(LocalDateTime.now());
    }


    public static ImageConverter toJPG() {
        ImageConverter c = new ImageConverter();
        c.targetType = MediaType.JPEG;
        return c;
    }

    public ImageConverter name(String name) {
        this.name = name;
        return this;
    }

    public ImageConverter edgeNoMoreThan(int maxEdge) {
        maxWidth = maxHeight = maxEdge;
        priorToMin = false;
        return this;
    }

    public ImageConverter edgeNoLessThan(int minEdge) {
        minWidth = minHeight = minEdge;
        priorToMin = true;
        return this;
    }

    public ImageConverter scaling(double scaling) {
        this.fixedScaling = scaling;
        return this;
    }

    public ImageConverter scalingPreferSimpleFraction(boolean b) {
        this.scalingPreferSimpleFraction = b;
        return this;
    }

    public ImageConverter compressQuality(double q) {
        compressQuality = q;
        return this;
    }


    public ImageConverter noCompressIfBppBelow(double bpp) {
        bppThresToTryNoCompress = bpp;
        return this;
    }


    public double calculateScaling(int w, int h) {
        if (fixedScaling != 0) {
            return fixedScaling;
        }
        if (w == 0 || h == 0) {
            throw new IllegalArgumentException("zero width or height");
        }
        double MRw = maxWidth / (double) w, mRw = minWidth / (double) w;
        double MRh = maxHeight / (double) h, mRh = minHeight / (double) h;
        if (mRw > 1) mRw = 1;
        if (mRh > 1) mRh = 1;
        if (MRw == 0 || MRw > 1) MRw = 1;
        if (MRh == 0 || MRh > 1) MRh = 1;
        double MR = Math.min(MRw, MRh), mR = Math.max(mRw, mRh);
        if (mR > MR) {  //min and max constraints conflicts
            return priorToMin ? mR : MR;
        }
        if (mR == MR) {
            return mR;
        }
        if (scalingPreferSimpleFraction) {
            return toSimpleFractionAsPossible(mR, MR);
        } else {
            return mR;
        }


    }

    public String getName(){return name;}

    private static final double MAX_RATIO_AVALIABLE = 0.9999;

    private static final double[] BEST_RATIOS = {
            1 / 12.0,   //0.08330
            1 / 10.0,   //0.10000
            1 / 8.0,    //0.1250
            3 / 16.0,   //0.1875
            1 / 4.0,    //0.2500
            3 / 8.0,    //0.3750
            1 / 2.0,    //0.5000
            3 / 5.0,    //0.6000
            2 / 3.0,    //0.6667
            3 / 4.0,    //0.7500
            4 / 5.0,    //0.8000
            7 / 8.0,    //0.8750
            15 / 16.0     //0.9375

    };

    /**
     * <li>1 , when r >=1 </li>
     * <li>a BEST_RATIOS, when r among two of them</li>
     * <li>r itself , when r < all of BEST_RATIOS </li>
     */
    private static double toSimpleFractionAsPossible(double min, double max) {
        if (max < BEST_RATIOS[0]) {
            return min;
        }
        for (int i = 0; i < BEST_RATIOS.length; i++) {
            if (min <= BEST_RATIOS[i]) {
                if (max >= BEST_RATIOS[i]) {
                    return BEST_RATIOS[i];
                } else {
                    return min;
                }
            }
        }
        return 1;
    }



     public void convertToJPG(File srcImage, File destImage) throws IOException {
        long bitLen = srcImage.getTotalSpace();
        BufferedImage bufferedImage = ImageIO.read(srcImage);
        int w = bufferedImage.getWidth(), h = bufferedImage.getHeight();
        double bpp = bitLen / (double) w / (double) h;
         double scaling = calculateScaling(w, h);
        if (scaling==1 && bpp< bppThresToTryNoCompress){
            //direct copy instead of scaling & compressing
            FileUtils.copyFile(srcImage,destImage);
            logger.info("convertToJPG OK: {} ({}x{}) just direct copy.",
                    srcImage.getName(),w, h);
        }else{
            Thumbnails.of(srcImage)
                    .scalingMode(ScalingMode.PROGRESSIVE_BILINEAR)
                    .scale(scaling)
                    .outputQuality(compressQuality)
                    .toFile(destImage);
            int sizePercentToSrc = (int)Math.round(srcImage.getTotalSpace()/(double)bitLen*100);
            logger.info("convertToJPG OK: {} ({}x{}) => ({}x{}),q={}. {}% size.",
                    srcImage.getName(),w, h, w * scaling, h * scaling, compressQuality,sizePercentToSrc);
        }

    }



}
