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
	private String facilityDesc;
	private Integer weekNo2;
	private Date minStartTime;
	
	private Integer facilityId2;
	private Integer duration;
	
	private String pps;
	private String input;
	
	private String year;
	private Integer week1;
	private Integer week2;
	private Integer week3;
	private Integer week4;
	private Integer week5;
	private Integer week6;
	private Integer week7;
	private Integer week8;
	private Integer week9;
	private Integer week10;
	private Integer week11;
	private Integer week12;
	private Integer occ1;
	private Float loss1;
	private Integer occ2;
	private Float loss2;
	private Integer occ3;
	private Float loss3;
	private Integer occ4;
	private Float loss4;
	private Integer occ5;
	private Float loss5;
	private Integer occ6;
	private Float loss6;
	private Integer occ7;
	private Float loss7;
	private Integer occ8;
	private Float loss8;
	private Integer occ9;
	private Float loss9;
	private Integer occ10;
	private Float loss10;
	private Integer occ11;
	private Float loss11;
	private Integer occ12;
	private Float loss12;
	
	private String shop;
	private String area;
	private String zone;
	private String shift;
	private String oprEchart;
	private String minLossEchart;
	private String prodLossEchart;
	
}
