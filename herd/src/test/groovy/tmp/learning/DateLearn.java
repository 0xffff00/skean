package tmp.learning;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateLearn {

    static Logger logger = LoggerFactory.getLogger(DateLearn.class);

    public static void main(String[] args) {

        System.out.println(DateFormatUtils.ISO_8601_EXTENDED_DATETIME_FORMAT.format(System.currentTimeMillis()));
        System.out.println(DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.format(System.currentTimeMillis()));
        System.out.println(
                DateFormatUtils.ISO_8601_EXTENDED_DATETIME_TIME_ZONE_FORMAT.format(System.currentTimeMillis()));
        System.out.println(DateFormatUtils.SMTP_DATETIME_FORMAT.format(System.currentTimeMillis()));
        System.out.println(DateFormatUtils.ISO_8601_EXTENDED_TIME_FORMAT.format(System.currentTimeMillis()));
        System.out.println("--------");
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE));
        // System.out.println(LocalDateTime.now().format(DateTimeFormatter.ISO_INSTANT));
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        System.out
                .println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MMM/d HH:mm:ss", Locale.FRANCE)));

        logger.info("sfsd");

    }

}
