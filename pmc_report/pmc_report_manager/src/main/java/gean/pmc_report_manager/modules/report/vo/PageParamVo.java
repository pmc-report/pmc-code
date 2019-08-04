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
	private String level;
	private Integer flag;
	
	
	
	public PageParamVo(Map<String, Object> params) {
		
		limit = (String)params.get("limit")==null?"10":(String)params.get("limit");
		page = (String)params.get("page")==null?"1":(String)params.get("page");
		shop = (String)params.get("shop")==null?"":(String)params.get("shop");
		line = (String)params.get("line")==null?"":(String)params.get("line");
		zone = (String)params.get("zone")==null?"":(String)params.get("zone");
		equipment = (String)params.get("equipment")==null?"":(String)params.get("equipment");
		station = (String)params.get("station")==null?"":(String)params.get("station");
		frequency = (String)params.get("frequency")==null?"":(String)params.get("frequency");
		shift = (String)params.get("shift")==null?"":(String)params.get("shift");
		String sTime = (String)params.get("sTime");
		if(sTime!=null&&!"".equals(sTime)) {
			sTime = sTime+" 00:00:00";
			startTime = DateUtils.stringToDate(sTime, DateUtils.DATE_TIME_PATTERN);
		}
		String eTime = (String)params.get("eTime");
		if(eTime!=null&&!"".equals(eTime)) {
			eTime = eTime+" 23:59:59";
			endTime = DateUtils.stringToDate(eTime, DateUtils.DATE_TIME_PATTERN);
		}
		
		if(sTime!=null&&!"".equals(sTime)) {
			workDay = sTime.replaceAll("-", "");
		}
		
		area = (String)params.get("area");
		jobId = (String)params.get("jobId")==null?"":(String)params.get("jobId");
		facilityId = (Integer)params.get("facilityId");
		fromDate0 = (String)params.get("fromDate[0]")==null?"0":(String)params.get("fromDate[0]");
		//fromDate0 = fromDate0 + " 00:00:00";
		fromDate1 = (String)params.get("fromDate[1]")==null?"0":(String)params.get("fromDate[1]");
		//fromDate1 = fromDate1 + " 00:00:00";
		fromDate2 = (String)params.get("fromDate[2]")==null?"0":(String)params.get("fromDate[2]");
		//fromDate2 = fromDate2 + " 00:00:00";
		fromDate3 = (String)params.get("fromDate[3]")==null?"0":(String)params.get("fromDate[3]");
		//fromDate3 = fromDate3 + " 00:00:00";
		fromDate4 = (String)params.get("fromDate[4]")==null?"0":(String)params.get("fromDate[4]");
		//fromDate4 = fromDate4 + " 00:00:00";
		fromDate5 = (String)params.get("fromDate[5]")==null?"0":(String)params.get("fromDate[5]");
		//fromDate5 = fromDate5 + " 00:00:00";
		fromDate6 = (String)params.get("fromDate[6]")==null?"0":(String)params.get("fromDate[6]");
		//fromDate6 = fromDate6 + " 00:00:00";
		
		toDate0 = (String)params.get("toDate[0]")==null?"0":(String)params.get("toDate[0]");
		//toDate0 = toDate0 + " 23:59:59";
		toDate1 = (String)params.get("toDate[1]")==null?"0":(String)params.get("toDate[1]");
		//toDate1 = toDate1 + " 23:59:59";
		toDate2 = (String)params.get("toDate[2]")==null?"0":(String)params.get("toDate[2]");
		//toDate2 = toDate2 + " 23:59:59";
		toDate3 = (String)params.get("toDate[3]")==null?"0":(String)params.get("toDate[3]");
		//toDate3 = toDate3 + " 23:59:59";
		toDate4 = (String)params.get("toDate[4]")==null?"0":(String)params.get("toDate[4]");
		//toDate4 = toDate4 + " 23:59:59";
		toDate5 = (String)params.get("toDate[5]")==null?"0":(String)params.get("toDate[5]");
		//toDate5 = toDate5 + " 23:59:59";
		toDate6 = (String)params.get("toDate[6]")==null?"0":(String)params.get("toDate[6]");
		//toDate6 = toDate6 + " 23:59:59";
		zoneList = params.get("zoneList") == null ? new ArrayList<String>() : (ArrayList<String>) params.get("zoneList");
		facilityIdList = params.get("facilityIdList") == null ? new ArrayList<Integer>() : (ArrayList<Integer>) params.get("facilityIdList");
	}
}
