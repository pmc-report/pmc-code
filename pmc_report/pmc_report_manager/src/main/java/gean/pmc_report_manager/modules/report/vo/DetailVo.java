package gean.pmc_report_manager.modules.report.vo;

import java.util.List;

import lombok.Data;

@Data
public class DetailVo {

	private String shop;
	
	private String area;
	
	private String zone;
	
	private String shift;
	
	private String startTime;
	
	private String endTime;
	
	private String jobId;
	
	private String equipment;
	
	private String station;
	
	private Integer facilityId;
	
	private Integer workDay;
	
	private Float tarTechAvail;
	
	private Float techAvail;
	
	private Integer goodPartCount;
	
	private Float downTime;
	
	private Integer faultOcc;
	
	private Float buildTime;
	
	private Integer duration;
	
	private Integer occ;
	
	private Integer minutes;
	
	private String faultDescription;
	
	private List<FaultVo> faultList;
}
