package gean.pmc_report_manager.modules.report.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	private Integer weekRange;
	
	@JsonFormat(pattern="dd/MM") 
	private Date monday;
	
	private Integer weekNo;
	private float tav;
	private float targetTav;
	private Date startTime;
	private float mtbf;
	private float targetMtbf;
	private Date week;
	
	
}
