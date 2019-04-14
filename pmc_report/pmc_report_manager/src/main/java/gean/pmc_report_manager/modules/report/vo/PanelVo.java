package gean.pmc_report_manager.modules.report.vo;

import lombok.Data;

@Data
public class PanelVo {

	private Integer old;
	private Integer _new;
	private Integer occ;
	private Float mins;
	private Integer stn;
	private String description;
	private Float pareto;
	private Integer status;
	
}
