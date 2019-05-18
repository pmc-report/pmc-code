package gean.pmc_report_manager.modules.report.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
	private Integer facilityId;
	private String limit;
	private String page;
	private String fromDate0;
	private String fromDate1;
	private String fromDate2;
	private String fromDate3;
	private String fromDate4;
	private String fromDate5;
	private String fromDate6;
	private String toDate0;
	private String toDate1;
	private String toDate2;
	private String toDate3;
	private String toDate4;
	private String toDate5;
	private String toDate6;
	private String workDay;
	private List<String> zoneList;
	private List<Integer> facilityIdList;
	
	
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
		
		if(StringUtils.isNotNull(params.get("sTime"))) {
			String str = (String)params.get("sTime");
			workDay = str.replaceAll("-", "");
		}
		
		area = (String)params.get("area");
		jobId = (String)params.get("jobId")==null?"":(String)params.get("jobId");
		facilityId = (Integer)params.get("facilityId");
		fromDate0 = (String)params.get("fromDate[0]")==null?"0":(String)params.get("fromDate[0]");
		fromDate1 = (String)params.get("fromDate[1]")==null?"0":(String)params.get("fromDate[1]");
		fromDate2 = (String)params.get("fromDate[2]")==null?"0":(String)params.get("fromDate[2]");
		fromDate3 = (String)params.get("fromDate[3]")==null?"0":(String)params.get("fromDate[3]");
		fromDate4 = (String)params.get("fromDate[4]")==null?"0":(String)params.get("fromDate[4]");
		fromDate5 = (String)params.get("fromDate[5]")==null?"0":(String)params.get("fromDate[5]");
		fromDate6 = (String)params.get("fromDate[6]")==null?"0":(String)params.get("fromDate[6]");
		
		toDate0 = (String)params.get("toDate[0]")==null?"0":(String)params.get("toDate[0]");
		toDate1 = (String)params.get("toDate[1]")==null?"0":(String)params.get("toDate[1]");
		toDate2 = (String)params.get("toDate[2]")==null?"0":(String)params.get("toDate[2]");
		toDate3 = (String)params.get("toDate[3]")==null?"0":(String)params.get("toDate[3]");
		toDate4 = (String)params.get("toDate[4]")==null?"0":(String)params.get("toDate[4]");
		toDate5 = (String)params.get("toDate[5]")==null?"0":(String)params.get("toDate[5]");
		toDate6 = (String)params.get("toDate[6]")==null?"0":(String)params.get("toDate[6]");
		zoneList = params.get("zoneList") == null ? new ArrayList<String>() : (ArrayList<String>) params.get("zoneList");
		facilityIdList = params.get("facilityIdList") == null ? new ArrayList<Integer>() : (ArrayList<Integer>) params.get("facilityIdList");
	}
}
