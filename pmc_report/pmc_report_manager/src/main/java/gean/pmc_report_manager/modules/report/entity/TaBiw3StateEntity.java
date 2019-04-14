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
 * @date 2019-04-11 10:56:37
 */
@Data
@TableName("TA_BIW3_STATE")
public class TaBiw3StateEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Integer taBiw3StateId;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 车间
	 */
	private String shop;
	/**
	 * 区域
	 */
	private String area;
	/**
	 * zone
	 */
	private String zone;
	/**
	 * 设备
	 */
	private String equipment;
	/**
	 * 设备描述
	 */
	private String facilityDesc;
	/**
	 * 站点
	 */
	private String station;
	/**
	 * 设备ID
	 */
	private Integer facilityId;
	/**
	 * 车型
	 */
	private String jobId;
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
	private Integer duration;
	/**
	 * 状态
	 */
	private String state;
	/**
	 * 设计周期
	 */
	private Integer designCycleTime;
	/**
	 * 班次
	 */
	private String shift;
	/**
	 * $column.comments
	 */
	private Boolean tavFlag;
	/**
	 * 历史标识
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
