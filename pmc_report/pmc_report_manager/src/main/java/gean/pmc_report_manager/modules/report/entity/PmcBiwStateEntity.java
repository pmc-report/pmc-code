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
 * @date 2019-03-14 09:49:18
 */
@Data
@TableName("PMC_BIW_STATE")
public class PmcBiwStateEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * $column.comments
	 */
	private String shop;
	/**
	 * $column.comments
	 */
	private String zone;
	/**
	 * $column.comments
	 */
	private String line;
	/**
	 * $column.comments
	 */
	private String equipment;
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
	private Boolean breakFlag;
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
	private Boolean webFlag;
	/**
	 * $column.comments
	 */
	private String jobId;
	/**
	 * $column.comments
	 */
	private Integer designCycleTime;
	/**
	 * $column.comments
	 */
	@TableId
	private Integer id;
	/**
	 * $column.comments
	 */
	private Date dbTimeStamp;

}
