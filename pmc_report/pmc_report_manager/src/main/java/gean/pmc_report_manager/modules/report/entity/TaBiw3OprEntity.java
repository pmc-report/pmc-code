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
@TableName("Final_BIW3_OPR")
public class TaBiw3OprEntity implements Serializable {
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
	private Float actualEquipmentOpr;
	/**
	 * $column.comments
	 */
	private Float actualProductionOpr;
	/**
	 * $column.comments
	 */
	private Float targetEquipmentOpr;
	/**
	 * $column.comments
	 */
	private Float targetProductionOpr;
	/**
	 * $column.comments
	 */
	private Integer productionVolume;
	/**
	 * $column.comments
	 */
	private String oprLevel;
	/**
	 * $column.comments
	 */
	private String frequency;
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
	private Date workDay;
	/**
	 * $column.comments
	 */
	private Integer facilityId;
	/**
	 * $column.comments
	 */
	private Integer downtime;


}
