package gean.pmc_report_manager.modules.report.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("PMC_BIW_CYCLE")
public class TaBiw3CycleEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String shop;
	
	private String area;
	
	private String zone;
	
	private Integer facilityId;
	
	private String facilityDesc;
	
	private Date startTime;
	
	private Date endTime;
	
	private Integer cycleTime;
	
	private String cycleType;
	
	private Integer goodCycleCount;
	
	private Integer badCycleCount;
	
	private Integer totalCycleCount;
	
	private Integer productId1;
	
	private Integer productId2;
	
	private String shift;
	
	private Integer cycleStatePerformance;
	
	private Float performancePercentage;
	
	private Boolean tavFlag;
	
	private String equipment;
	
	private String station;
	
	private Integer designCycleTime;
	
	private Boolean productionFlag;
	
	private String jobId;
	
	private Date dbTimeStamp;
	
	
	
	

}
