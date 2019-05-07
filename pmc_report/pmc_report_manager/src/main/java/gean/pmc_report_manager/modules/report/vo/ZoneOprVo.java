package gean.pmc_report_manager.modules.report.vo;


import lombok.Data;

@Data
public class ZoneOprVo {

	private Integer starved;
	private String zone;
	private Integer goodPartCount;
	private Integer downTime;
	private Float productionOpr;
	private Float equipmentOpr;
	private Float equipAvail;
	private Integer cycleTime;
	private Integer blocked;
	private Integer facilityId;
	private Float availableTime;
}
