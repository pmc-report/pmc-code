package gean.pmc_report_manager.modules.report.vo;

import java.util.List;

import lombok.Data;

@Data
public class OprVoExport {
	private String area;
	private Integer actual;
	private Integer shiftPlan;
	private String zone;
	private Integer variation;
	private Float productionOpr;
	private Float equipmentOpr;
	private String shift;
	private String shop;
	private String stime;
	private Integer starved;
	private String zone2; 
	private Integer goodPartCount;
	private Integer downTime;
	private Float productionOpr2;
	private Float equipmentOpr2;
	private Float equipAvail;
	private Integer cycleTime;
	private Integer blocked;
	private Integer facilityId;
	private Float availableTime;
}
