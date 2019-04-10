package gean.pmc_report_manager.modules.report.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Jason
 * @email xxxxx@gmail.com
 * @date 2019-03-30 09:27:53
 */
@Data
@TableName("TA_EQU_FAULT")
public class TaEquFaultEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 设备故障ID
	 */
	@TableId
	private Integer taEquFaultId;
	/**
	 * 区域
	 */
	private String area;
	/**
	 * zone 区
	 */
	private String zone;
	/**
	 * 工位
	 */
	private String station;
	/**
	 * 设备ID
	 */
	private Integer facilityId;
	/**
	 * 设备说明
	 */
	private String facilityTex;
	/**
	 * 车型
	 */
	private String jobId;
	/**
	 * $column.comments
	 */
	private String faultWord1;
	/**
	 * $column.comments
	 */
	private String faultWord2;
	/**
	 * $column.comments
	 */
	private String faultWord3;
	/**
	 * $column.comments
	 */
	private String word31;
	/**
	 * 故障描述
	 */
	private String faultDesc;
	/**
	 * 原因代码
	 */
	private String reasonCode;
	/**
	 * 原因描述
	 */
	private String reasonDesc;
	/**
	 * 开始时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;
	/**
	 * 持续时间
	 */
	private String duration;
	/**
	 * 最后更新时间
	 */
	private Date lastUpdateTime;
	/**
	 * 最后更新用户
	 */
	private String lastUpdateUsername;
	/**
	 * $column.comments
	 */
	private Integer optCounter;

}
