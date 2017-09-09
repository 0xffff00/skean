package party.threebody.herd.util;

public enum MediaType {
    JPEG("jpg"),
    JPEG2000("jp2"),
    PNG("png"),
    BMP("bmp"),
    GIF("gif"),
    TIFF("tiff"),
    WEBP("webp");

    private final String suffix;

    public String getSuffix() {
        return suffix;
    }

    MediaType(String suffix) {
        this.suffix = suffix;
    }
}
