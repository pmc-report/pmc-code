package gean.pmc_report_manager.modules.report.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author ''
 * @email xxxxx@gmail.com
 * @date 2019-04-17 21:02:05
 */
@Data
@TableName("Final_BIW3_STATE")
public class FinalBiw3StateEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * $column.comments
	 */
	@TableId
	private Integer rowId;
	/**
	 * $column.comments
	 */
	private Date dbTimeStamp;
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
	private String facilityDesc;
	/**
	 * $column.comments
	 */
	private String station;
	/**
	 * $column.comments
	 */
	private Integer facilityId;
	/**
	 * $column.comments
	 */
	private String jobId;
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
	private Integer duration;
	/**
	 * $column.comments
	 */
	private String state;
	/**
	 * $column.comments
	 */
	private Integer designCycleTime;
	/**
	 * $column.comments
	 */
	private String shift;
	/**
	 * $column.comments
	 */
	private Boolean tavFlag;
	/**
	 * $column.comments
	 */
	private Integer historical;
	/**
	 * $column.comments
	 */
	private Integer processedFlag;
	/**
	 * $column.comments
	 */
	private Long workDay;

}
