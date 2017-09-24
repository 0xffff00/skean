package party.threebody.herd.util;

import com.drew.imaging.FileType;
import com.drew.imaging.FileTypeDetector;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifDirectoryBase;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.jpeg.JpegDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import party.threebody.herd.domain.ImageMedia;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.TimeZone;

public class ImageMetaUtils {

    static final Logger logger = LoggerFactory.getLogger(ImageMetaUtils.class);

    public static ImageMedia parseExifInfo(Path src) throws ImageProcessingException, IOException {
        return parseExifInfo(Files.newInputStream(src));
    }

    public static ImageMedia parseExifInfo(InputStream src) throws ImageProcessingException, IOException {
        BufferedInputStream bis = new BufferedInputStream(src);
        Metadata metadata = null;
        ImageMedia imageMedia = new ImageMedia();

        FileType fileType = FileTypeDetector.detectFileType(bis);
        imageMedia.setType(fileType.toString().toLowerCase());
        metadata = ImageMetadataReader.readMetadata(bis);


        JpegDirectory jpegDirectory = metadata.getFirstDirectoryOfType(JpegDirectory.class);
        if (jpegDirectory != null) {   // is a jpeg
            try {
                imageMedia.fillHeight(jpegDirectory.getImageHeight());
                imageMedia.fillWidth(jpegDirectory.getImageWidth());
            } catch (MetadataException e) {
                //no op
            }
            imageMedia.fillExifBitDepth(jpegDirectory.getInteger(JpegDirectory.TAG_DATA_PRECISION));
        }


        ExifIFD0Directory exifIFD0Directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
        if (exifIFD0Directory != null) {
            fillTagValue(imageMedia, exifIFD0Directory, ExifDirectoryBase.TAG_MAKE);
            fillTagValue(imageMedia, exifIFD0Directory, ExifDirectoryBase.TAG_MODEL);
            fillTagValue(imageMedia, exifIFD0Directory, ExifDirectoryBase.TAG_COLOR_SPACE);
            fillTagValue(imageMedia, exifIFD0Directory, ExifDirectoryBase.TAG_EXPOSURE_TIME);
            fillTagValue(imageMedia, exifIFD0Directory, ExifDirectoryBase.TAG_WHITE_BALANCE);
            fillTagValue(imageMedia, exifIFD0Directory, ExifDirectoryBase.TAG_APERTURE);
            fillTagValue(imageMedia, exifIFD0Directory, ExifDirectoryBase.TAG_DATETIME);
            fillTagValue(imageMedia, exifIFD0Directory, ExifDirectoryBase.TAG_DATETIME_ORIGINAL);
        }

        ExifSubIFDDirectory exifSubIFDDirectory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
        if (exifSubIFDDirectory != null) {
            fillTagValue(imageMedia, exifSubIFDDirectory, ExifSubIFDDirectory.TAG_MAKE);
            fillTagValue(imageMedia, exifSubIFDDirectory, ExifSubIFDDirectory.TAG_MODEL);
            fillTagValue(imageMedia, exifSubIFDDirectory, ExifSubIFDDirectory.TAG_COLOR_SPACE);
            fillTagValue(imageMedia, exifSubIFDDirectory, ExifSubIFDDirectory.TAG_EXPOSURE_TIME);
            fillTagValue(imageMedia, exifSubIFDDirectory, ExifSubIFDDirectory.TAG_WHITE_BALANCE);
            fillTagValue(imageMedia, exifSubIFDDirectory, ExifSubIFDDirectory.TAG_APERTURE);
            fillTagValue(imageMedia, exifSubIFDDirectory, ExifSubIFDDirectory.TAG_DATETIME);
            fillTagValue(imageMedia, exifSubIFDDirectory, ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
        }


        return imageMedia;
    }


    private static void fillTagValue(ImageMedia imageMedia, ExifDirectoryBase exifDirectory, int exifTagType) {
        String tagValStr = exifDirectory.getString(exifTagType);
        switch (exifTagType) {
            case ExifDirectoryBase.TAG_MAKE:
                imageMedia.fillExifMake(tagValStr);
                break;
            case ExifDirectoryBase.TAG_MODEL:
                imageMedia.fillExifModel(tagValStr);
                break;
            case ExifDirectoryBase.TAG_COLOR_SPACE:
                imageMedia.fillExifColorSpace(tagValStr);
                break;
            case ExifDirectoryBase.TAG_EXPOSURE_TIME:
                imageMedia.fillExifExposureTime(tagValStr);
                break;
            case ExifDirectoryBase.TAG_WHITE_BALANCE:
                imageMedia.fillExifWhiteBalance(tagValStr);
                break;
            case ExifDirectoryBase.TAG_APERTURE:
                imageMedia.fillExifAperture(tagValStr);
                break;
            case ExifDirectoryBase.TAG_DATETIME:
            case ExifDirectoryBase.TAG_DATETIME_ORIGINAL:
            case ExifDirectoryBase.TAG_DATETIME_DIGITIZED:
                imageMedia.fillExifDateTime(exifDirectory.getDate(exifTagType, TimeZone.getDefault()));
                break;

            case ExifDirectoryBase.TAG_IMAGE_HEIGHT:
                try {
                    imageMedia.fillHeight(exifDirectory.getInt(exifTagType));
                } catch (MetadataException e) {
                    //NO-OP
                }
                break;
            case ExifDirectoryBase.TAG_IMAGE_WIDTH:
                try {
                    imageMedia.fillWidth(exifDirectory.getInt(exifTagType));
                } catch (MetadataException e) {
                    //NO-OP
                }
                break;
        }
    }

}