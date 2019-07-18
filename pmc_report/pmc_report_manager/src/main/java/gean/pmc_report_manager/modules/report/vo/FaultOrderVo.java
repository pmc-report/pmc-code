package gean.pmc_report_manager.modules.report.vo;

import lombok.Data;

@Data
public class FaultOrderVo {

	private float mins;
	private Integer stn;
	private String description;
	private Integer occ;
	//private float duration;
}
