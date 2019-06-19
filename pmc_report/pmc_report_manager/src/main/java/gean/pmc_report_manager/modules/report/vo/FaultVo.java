package gean.pmc_report_manager.modules.report.vo;

import java.util.Date;

import lombok.Data;

@Data
public class FaultVo {

	private String shop;
	private String zone;
	private String line;
	private String equipment;
	private String station;
	private String jobId;
	private Integer facilityId;
	private String facilityDesc;
	private Date startTime;
	private Date endTime;
	private Integer duration;
	private Integer faultWord1;
	private Integer faultWord2;
	private Integer faultWord3;
	private String faultDescription;
	private Integer reasonCode;
	private String reasonDescription;
    private Integer posWord31;
    private Integer totalDuration;
}
