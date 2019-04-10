package gean.pmc_report_manager.modules.report.vo;

import lombok.Data;

@Data
public class OprReportVo {

	private String oprTarget;
	private Float eopr;
	private Integer productionVolume;
	private Integer availableTime;
	private Float popr;
	private String role;
	private String workingDay;
	
}
