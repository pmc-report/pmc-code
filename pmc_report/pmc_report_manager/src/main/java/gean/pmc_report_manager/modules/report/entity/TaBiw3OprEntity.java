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
 * @date 2019-04-10 13:54:04
 */
@Data
@TableName("PMC_OPR")
public class TaBiw3OprEntity implements Serializable {
	private static final long serialVersionUID = 1L;


	/**
	 * $column.comments
	 */
	private String shop;
	/**
	 * $column.comments
	 */
	private String line;
	/**
	 * $column.comments
	 */
	private String zone;
	/**
	 * $column.comments
	 */
	private String station;
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
	private Integer availableTime;
	/**
	 * $column.comments
	 */
	private Integer productionVolume;
	/**
	 * $column.comments
	 */
	private String frequency;
	/**
	 * $column.comments
	 */
	private Integer workingday;
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
	private Float tarOpr;
	/**
	 * $column.comments
	 */
	private Integer designCycleTime;
	/**
	 * $column.comments
	 */
	private Float productionOpr;
}
