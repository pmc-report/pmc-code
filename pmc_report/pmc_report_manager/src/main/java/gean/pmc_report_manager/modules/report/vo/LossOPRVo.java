package gean.pmc_report_manager.modules.report.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class LossOPRVo {

	private Integer weekRange;
	
	@JsonFormat(pattern="dd/MM") 
	private Date monday;
	
	private Integer weekNo;
	private Date week;
	private float actualPOpr;
	private float actualEOpr;
	private Integer downTime;
	private float targetEOpr;
	private float targetPOpr;
	private Date startTime;
	
	private Integer rn;
	private Integer occ;
	private Integer loss;
	private Integer facilityId;
	private Integer weekNo2;
	private Date minStartTime;
	
	private Integer facilityId2;
	private Integer duration;
	
}
