package gean.pmc_report_generator.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理器
 *
 */
@RestControllerAdvice
public class ReportExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 处理自定义异常
	 */
	@ExceptionHandler(ReportException.class)
	public R handleRRException(ReportException e){
		R r = new R();
		r.put("code", e.getCode());
		r.put("msg", e.getMessage());

		return r;
	}

	@ExceptionHandler(Exception.class)
	public R handleException(Exception e){
		logger.error(e.getMessage(), e);
		return R.error();
	}
}
