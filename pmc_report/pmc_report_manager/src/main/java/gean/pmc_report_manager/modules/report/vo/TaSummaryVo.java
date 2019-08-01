package gean.pmc_report_manager.modules.report.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class TaSummaryVo {

	private String shop;
	private String zone;
	private String area;
	private String jobId;
	private String shift;
	private String startTime;
	private String endTime;
	private Integer comparisonWeek;
	private Integer currentWeek;
	private Float targetTa;
	private Float preActualTa;
	private Integer preVol;
	private Float preGap;
	private Float curActualTa;
	private Float curGap;
	private Integer curVol;
	private Float improve;
	private String trend;
	private String preWeekNo;
	private String curWeekNo;
	private String zoneTaEchart;
	private String trendChart;
	
	@JsonFormat(pattern="dd/MM") 
	private Date week;
	
	
}
