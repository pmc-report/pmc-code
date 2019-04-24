package gean.pmc_report_manager.modules.report.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class PanelVo {

	private Integer old;
	private Integer oldThenNew;
	private Integer _new;
	private Integer occ1;
	private float mins1;
	private Integer stn1;
	private String description1;
	private Integer occ2;
	private Float mins2;
	private Integer stn2;
	private String description2;
	private float totalDuration1;
	private Integer totalOcc1;
	private float totalDuration2;
	private Integer totalOcc2;
	
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
