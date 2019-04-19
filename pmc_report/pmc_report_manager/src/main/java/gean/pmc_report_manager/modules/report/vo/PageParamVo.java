package gean.pmc_report_manager.modules.report.vo;

import java.util.Date;
import java.util.Map;

import gean.pmc_report_common.common.utils.DateUtils;
import gean.pmc_report_common.common.utils.StringUtils;
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
	private String equipment;
	private String limit;
	private String page;

	public PageParamVo(Map<String, Object> params) {
		
		limit = (String)params.get("limit")==null?"0":(String)params.get("limit");
		page = (String)params.get("page")==null?"0":(String)params.get("page");
		shop = (String)params.get("shop")==null?"":(String)params.get("shop");
		line = (String)params.get("line")==null?"":(String)params.get("line");
		zone = (String)params.get("zone")==null?"":(String)params.get("zone");
		equipment = (String)params.get("equipment")==null?"":(String)params.get("equipment");
		station = (String)params.get("station")==null?"":(String)params.get("station");
		frequency = (String)params.get("frequency")==null?"":(String)params.get("frequency");
		shift = (String)params.get("shift")==null?"":(String)params.get("shift");
		if(StringUtils.isNotNull(params.get("sTime"))) {
			startTime = DateUtils.stringToDate(
					params.get("sTime").toString(), DateUtils.DATE_PATTERN);
		}
		
		if(StringUtils.isNotNull(params.get("eTime"))) {
			endTime = DateUtils.stringToDate(
					params.get("eTime").toString(), DateUtils.DATE_PATTERN);
		}
		
		area = (String)params.get("area");
		jobId = (String)params.get("jobId");
	}
}
