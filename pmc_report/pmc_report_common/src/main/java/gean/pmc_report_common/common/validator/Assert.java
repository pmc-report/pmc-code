package gean.pmc_report_common.common.validator;

import org.apache.commons.lang.StringUtils;

import gean.pmc_report_common.common.exception.ReportException;

/**
 * 数据校验
 *
 */
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new ReportException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new ReportException(message);
        }
    }
}
