package party.threebody.skean.jdbc;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import party.threebody.skean.jdbc.util.SqlAndArgs;
import party.threebody.skean.jdbc.util.SqlPrintUtils;

public class SqlPrinter {

	static final Logger logger = LoggerFactory.getLogger(SqlPrinter.class);
	private ChainedJdbcTemplateContext context;

	public SqlPrinter(ChainedJdbcTemplateContext context) {
		super();
		this.context = context;
	}

	public void printSql(SqlAndArgs sa) {
		if (!context.isPrintSqlAndResult()) {
			return;
		}
		logger.info(">>>>>>>>>>>   SQL  >>>>>>>>>>>\n{}", sa.toAnsiString());
	}

	public void printRowNumAffected(int rowNumAffected) {
		if (!context.isPrintSqlAndResult()) {
			return;
		}
		logger.info("<<<<<<<<<<< {} rows(s) affected. <<<<<<<<<<<\n{}", rowNumAffected);
	}

	public <T> void printResultBean(T resultBean) {
		printResultList(Arrays.asList(resultBean));
	}

	public <T> void printResultList(List<T> resultList) {
		if (!context.isPrintSqlAndResult()) {
			return;
		}
		int count = resultList.size();
		String text = null;
		if (count == 0) {
			text = "(None)";
		} else {
			int rsmax = context.getMaxCharsToPrintSqlResult();
			int lnmax = context.getMaxCharsToPrintInOneLine() - 1;
			StringBuilder sb = new StringBuilder();
			int i = 0, ln = 0;
			for (i = 0, ln = 0; i < count; i++) {
				String str = resultList.get(0).toString();
				if (sb.length() + str.length() > rsmax) {
					break;
				}
				ln += str.length();
				sb.append(str);
				if (ln > lnmax) {
					sb.append("\n");
					ln = 0;
				} else {
					sb.append("\t");
					ln += 4;
				}
			}
			if (i < count) {
				sb.append("...(other ").append(count - i).append(" items left)");
			}
			text = sb.toString();
		}
		text = SqlPrintUtils.ansiFormat(text, SqlPrintUtils.ANSI.YELLOW + SqlPrintUtils.ANSI.BLACK_BG);
		logger.info("<<<<<<<<<<< {} row(s) returned: <<<<<<<<<<<\n{}", count, text);

	}

}
