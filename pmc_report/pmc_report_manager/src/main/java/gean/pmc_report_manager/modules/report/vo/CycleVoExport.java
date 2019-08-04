package gean.pmc_report_manager.modules.report.vo;

import lombok.Data;

@Data
public class CycleVoExport {
	
	private String shop;
	
	private String area;
	
	private String zone;
	
	private String station;
	
	private String equipment;
	
	private String _station;
	
	private String _equipment;
	
	private String shift;
	
	private String startTime;
	
	private String endTime;
	
	private String jobId;
	
	private String _jobId;
	
	private String facilityId;
	
	private Integer _facilityId;
	
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
	
	private String echarepxport;
	

}
