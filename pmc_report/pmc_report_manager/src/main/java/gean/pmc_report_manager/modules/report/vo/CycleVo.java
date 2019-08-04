package gean.pmc_report_manager.modules.report.vo;

import java.util.Date;

import lombok.Data;

@Data
public class CycleVo {
	
	private String shop;
	
	private String area;
	
	private String zone;
	
	private String station;
	
	private String equipment;
	
	private String shift;
	
	private Date startTime;
	
	private Date endTime;
	
	private String jobId;
	
	private Integer facilityId;
	
	private String facilityDesc;
	
	private Integer cycleTime;
	
	private String cycleType;
	
	private Integer goodCycleCount;
	
	private Integer badCycleCount;
	
	private Integer totalCycleCount;
	
	private Integer designCycleTime;
	
	private Integer stdCycleTime;
	
	private Integer minCycleTime;
	
	private Integer maxCycleTime;
	
	private Integer avgCycleTime;
	
	private Float effeciveCycleTime;
	
	private Float cycleOffset;
	
	private Integer totalCycleTime;
	
	private Integer goodCycleTime;
	
	private Integer badCycleTime;
	

}
