package party.threebody.herd.util;

import com.drew.imaging.FileType;
import com.drew.imaging.FileTypeDetector;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifDirectoryBase;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.jpeg.JpegDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import party.threebody.herd.domain.ImageInfo;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class ImageMetaUtils {

    static final Logger logger = LoggerFactory.getLogger(ImageMetaUtils.class);

    public static ImageInfo parseExifInfo(Path src) throws ImageProcessingException, IOException {
        return parseExifInfo(Files.newInputStream(src));
    }

    public static ImageInfo parseExifInfo(InputStream src) throws ImageProcessingException, IOException {
        BufferedInputStream bis = new BufferedInputStream(src);
        Metadata metadata = null;
        ImageInfo imageInfo = new ImageInfo();

        FileType fileType = FileTypeDetector.detectFileType(bis);
        imageInfo.setType(fileType.toString().toLowerCase());
        metadata = ImageMetadataReader.readMetadata(bis);


        JpegDirectory jpegDirectory = metadata.getFirstDirectoryOfType(JpegDirectory.class);
        if (jpegDirectory != null) {   // is a jpeg
            try {
                imageInfo.fillHeight(jpegDirectory.getImageHeight());
                imageInfo.fillWidth(jpegDirectory.getImageWidth());
            } catch (MetadataException e) {
                //no op
            }
            imageInfo.fillExifBitDepth(jpegDirectory.getInteger(JpegDirectory.TAG_DATA_PRECISION));
        }


        ExifIFD0Directory exifIFD0Directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
        if (exifIFD0Directory != null) {
            fillTagValue(imageInfo, exifIFD0Directory, ExifDirectoryBase.TAG_MAKE);
            fillTagValue(imageInfo, exifIFD0Directory, ExifDirectoryBase.TAG_MODEL);
            fillTagValue(imageInfo, exifIFD0Directory, ExifDirectoryBase.TAG_COLOR_SPACE);
            fillTagValue(imageInfo, exifIFD0Directory, ExifDirectoryBase.TAG_EXPOSURE_TIME);
            fillTagValue(imageInfo, exifIFD0Directory, ExifDirectoryBase.TAG_WHITE_BALANCE);
            fillTagValue(imageInfo, exifIFD0Directory, ExifDirectoryBase.TAG_APERTURE);
            fillTagValue(imageInfo, exifIFD0Directory, ExifDirectoryBase.TAG_DATETIME);
            fillTagValue(imageInfo, exifIFD0Directory, ExifDirectoryBase.TAG_DATETIME_ORIGINAL);
        }

        ExifSubIFDDirectory exifSubIFDDirectory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
        if (exifSubIFDDirectory != null) {
            fillTagValue(imageInfo, exifSubIFDDirectory, ExifSubIFDDirectory.TAG_MAKE);
            fillTagValue(imageInfo, exifSubIFDDirectory, ExifSubIFDDirectory.TAG_MODEL);
            fillTagValue(imageInfo, exifSubIFDDirectory, ExifSubIFDDirectory.TAG_COLOR_SPACE);
            fillTagValue(imageInfo, exifSubIFDDirectory, ExifSubIFDDirectory.TAG_EXPOSURE_TIME);
            fillTagValue(imageInfo, exifSubIFDDirectory, ExifSubIFDDirectory.TAG_WHITE_BALANCE);
            fillTagValue(imageInfo, exifSubIFDDirectory, ExifSubIFDDirectory.TAG_APERTURE);
            fillTagValue(imageInfo, exifSubIFDDirectory, ExifSubIFDDirectory.TAG_DATETIME);
            fillTagValue(imageInfo, exifSubIFDDirectory, ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
        }



        return imageInfo;
    }



    private static void fillTagValue(ImageInfo imageInfo, ExifDirectoryBase exifDirectory, int exifTagType) {
        String tagValStr = exifDirectory.getString(exifTagType);
        switch (exifTagType) {
            case ExifDirectoryBase.TAG_MAKE:
                imageInfo.fillExifMake(tagValStr);
                break;
            case ExifDirectoryBase.TAG_MODEL:
                imageInfo.fillExifModel(tagValStr);
                break;
            case ExifDirectoryBase.TAG_COLOR_SPACE:
                imageInfo.fillExifColorSpace(tagValStr);
                break;
            case ExifDirectoryBase.TAG_EXPOSURE_TIME:
                imageInfo.fillExifExposureTime(tagValStr);
                break;
            case ExifDirectoryBase.TAG_WHITE_BALANCE:
                imageInfo.fillExifWhiteBalance(tagValStr);
                break;
            case ExifDirectoryBase.TAG_APERTURE:
                imageInfo.fillExifAperture(tagValStr);
                break;
            case ExifDirectoryBase.TAG_DATETIME:
            case ExifDirectoryBase.TAG_DATETIME_ORIGINAL:
            case ExifDirectoryBase.TAG_DATETIME_DIGITIZED:
                imageInfo.fillExifDateTime(exifDirectory.getDate(exifTagType));
                break;

            case ExifDirectoryBase.TAG_IMAGE_HEIGHT:
                try {
                    imageInfo.fillHeight(exifDirectory.getInt(exifTagType));
                } catch (MetadataException e) {
                    //NO-OP
                }
                break;
            case ExifDirectoryBase.TAG_IMAGE_WIDTH:
                try {
                    imageInfo.fillWidth(exifDirectory.getInt(exifTagType));
                } catch (MetadataException e) {
                    //NO-OP
                }
                break;
        }
    }

}