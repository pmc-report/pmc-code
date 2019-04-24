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
 * @date 2019-04-23 16:10:19
 */
@Data
@TableName("PMC_BIW_FAULT")
public class PmcBiwFaultEntity implements Serializable {
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
	private String area;
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
	private String jobId;
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
	private Integer faultWord1;
	/**
	 * $column.comments
	 */
	private Integer faultWord2;
	/**
	 * $column.comments
	 */
	private Integer faultWord3;
	/**
	 * $column.comments
	 */
	private String faultDescription;
	/**
	 * $column.comments
	 */
	private Integer reasonCode;
	/**
	 * $column.comments
	 */
	private String reasonDescription;
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
	private Integer posWord31;
	/**
	 * $column.comments
	 */
	private Date dbTimeStamp;

}
