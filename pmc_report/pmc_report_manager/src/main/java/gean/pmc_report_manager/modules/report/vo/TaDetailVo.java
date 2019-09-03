package gean.pmc_report_manager.modules.report.vo;

import java.util.List;

import lombok.Data;

@Data
public class TaDetailVo {

	private String shop;
	
	private String area;
	
	private String zone;
	
	private String shift;
	
	private String startTime;
	
	private String endTime;
	
	private String jobId;
	
	private String equipment;
	
	private List<MsDataVo> msList;
	
	private List<FaultVo> faultList;
}
