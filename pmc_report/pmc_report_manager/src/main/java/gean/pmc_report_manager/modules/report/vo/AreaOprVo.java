package gean.pmc_report_manager.modules.report.vo;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class AreaOprVo {

	private String area;
	private Integer actual;
	private Integer shiftPlan;
	private String zone;
	private Integer variation;
	private Float productionOpr;
	private Float equipmentOpr;
	private List<ZoneOprVo> zoneList;
	private String shift;
	private String shop;
	private String stime;
}
