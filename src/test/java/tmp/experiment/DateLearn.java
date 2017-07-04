package tmp.experiment;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateLearn {

	static Logger logger=LoggerFactory.getLogger(DateLearn.class);
	public static void main(String[] args) { 
		System.out.println(DateFormatUtils.ISO_8601_EXTENDED_DATETIME_FORMAT.format(System.currentTimeMillis()));
		System.out.println(DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.format(System.currentTimeMillis()));
		System.out.println(DateFormatUtils.ISO_8601_EXTENDED_DATETIME_TIME_ZONE_FORMAT.format(System.currentTimeMillis()));
		logger.info("sfsd");
	}

}
