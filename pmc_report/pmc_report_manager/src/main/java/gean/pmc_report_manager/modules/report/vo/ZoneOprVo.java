package gean.pmc_report_manager.modules.report.vo;


import lombok.Data;

@Data
public class ZoneOprVo {

	private String starved;
	private String zone;
	private Integer goodPartCount;
	private Integer downTime;
	private Float productionOpr;
	private Float equipmentOpr;
	private Integer equipAvail;
	private Integer cycleTime;
	private String blocked;
	private Integer facilityId;
}
