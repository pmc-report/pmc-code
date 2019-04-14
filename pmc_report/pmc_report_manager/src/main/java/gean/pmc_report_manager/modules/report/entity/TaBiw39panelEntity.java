package gean.pmc_report_manager.modules.report.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ${comments}
 * 
 * @author Jason
 * @email xxxxx@gmail.com
 * @date 2019-04-13 14:15:33
 */
@Data
@TableName("TA_BIW3_9PANEL")
public class TaBiw39panelEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * $column.comments
	 */
	private String shop;
	/**
	 * $column.comments
	 */
	private String area;
	/**
	 * $column.comments
	 */
	private String zone;
	/**
	 * $column.comments
	 */
	private String equipment;
	/**
	 * $column.comments
	 */
	private Integer facilityId;
	/**
	 * $column.comments
	 */
	private Date startTime;
	/**
	 * $column.comments
	 */
	private Date endTime;
	/**
	 * $column.comments
	 */
	private Integer goodPartCount;
	/**
	 * $column.comments
	 */
	private Float downtime;
	/**
	 * $column.comments
	 */
	private Integer faultOcc;
	/**
	 * $column.comments
	 */
	private Float buildTime;
	/**
	 * $column.comments
	 */
	private Float techAvail;
	/**
	 * $column.comments
	 */
	private Float mtbf;
	/**
	 * $column.comments
	 */
	private String frequency;
	/**
	 * $column.comments
	 */
	private Integer workDay;
	/**
	 * $column.comments
	 */
	private String levels;
	/**
	 * $column.comments
	 */
	private String shift;
	/**
	 * $column.comments
	 */
	private Float tarTechAvail;
	/**
	 * $column.comments
	 */
	private Float tarMtbf;
	/**
	 * $column.comments
	 */
	private Float tarMtbfSum;
	/**
	 * $column.comments
	 */
	private Float tarTechAvailSum;
	/**
	 * $column.comments
	 */
	private Float mtbfSum;
	/**
	 * $column.comments
	 */
	private Float techAvailSum;
	/**
	 * $column.comments
	 */
	private Integer counter;
	/**
	 * $column.comments
	 */
	@TableId
	private Integer taBiw39panelId;
	/**
	 * $column.comments
	 */
	private Date dbTimeStamp;
	/**
	 * $column.comments
	 */
	private Integer historical;
	/**
	 * $column.comments
	 */
	private Integer restDuration;
	/**
	 * $column.comments
	 */
	private Integer processedFlag;

}
