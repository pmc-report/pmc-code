package gean.pmc_report_manager.modules.report.vo;

import lombok.Data;

@Data
public class MasterDataVo {

	/*** 车间*/
	private String shopNo;
	
	/*** 区域*/
    private String lineNo;
    
    /*** 工作区*/
    private String zoneNo;
    
    /*** 工站*/
    private String stationNo;
    
    /*** 设备*/
    private String equipmentNo;
    
    /*** 设备id*/
    private Integer facilityId;
    
    /*** 设备类型*/
    private String equipmentType;
    
    /*** 设计节拍时间*/
    private Integer designCycleTime;
    

}
