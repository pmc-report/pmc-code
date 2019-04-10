package gean.pmc_report_manager.common.exception;


import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import gean.pmc_report_common.common.exception.ReportException;
import gean.pmc_report_common.common.utils.R;

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

	@ExceptionHandler(DuplicateKeyException.class)
	public R handleDuplicateKeyException(DuplicateKeyException e){
		logger.error(e.getMessage(), e);
		return R.error("数据库中已存在该记录");
	}

	@ExceptionHandler(AuthorizationException.class)
	public R handleAuthorizationException(AuthorizationException e){
		logger.error(e.getMessage(), e);
		return R.error("没有权限，请联系管理员授权");
	}

	@ExceptionHandler(Exception.class)
	public R handleException(Exception e){
		logger.error(e.getMessage(), e);
		return R.error();
	}
}
