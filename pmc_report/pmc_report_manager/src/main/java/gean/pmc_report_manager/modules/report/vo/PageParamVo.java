package gean.pmc_report_manager.modules.report.vo;

import java.util.Date;
import java.util.Map;

import gean.pmc_report_common.common.utils.DateUtils;
import lombok.Data;

@Data
public class PageParamVo {
	private String shop;
	private String line;
	private String zone;
	private String station;
	private String frequency;
	private String shift;
	private Date startTime;
	private Date endTime;
	private String area;
	private String jobId;

	public PageParamVo(Map<String, Object> params) {
		shop = (String)params.get("shop");
		line = (String)params.get("line");
		zone = (String)params.get("zone");
		station = (String)params.get("station");
		frequency = (String)params.get("frequency");
		shift = (String)params.get("shift");
		startTime = DateUtils.stringToDate(
				params.get("sTime").toString(), DateUtils.DATE_TIME_PATTERN);
		endTime = DateUtils.stringToDate(
				params.get("eTime").toString(), DateUtils.DATE_TIME_PATTERN);
		area = (String)params.get("area");
		jobId = (String)params.get("jobId");
	}
}
