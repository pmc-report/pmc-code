package gean.pmc_report_manager.modules.report.vo;

import lombok.Data;

@Data
public class EquFaultExport {
	private String shop;
	private String line;
	private String line_2;
	private String zone;
	private String zone_2;
	private String equipment;
	private String station;
	private String station_2;
	private String jobId;
	private String jobId_2;
	private String facilityId;
	private String facilityId_2;
	private String facilityDesc;
	private String startTime;
	private String startTime_2;
	private String endTime;
	private String endTime_2;
	private String duration;
	private String duration_2;
	private String faultWord1;
	private String faultWord2;
	private String faultWord3;
	private String faultDescription;
	private String reasonCode;
	private String reasonDescription;
	private String shift;
	private Integer posWord31;
}
