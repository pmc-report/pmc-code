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
@TableName("TA_BIW3_OPR")
public class TaBiw3OprEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Integer taBiw3OprId;
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
	 * 设备ID
	 */
	private String facilityDesc;
	/**
	 * 站点
	 */
	private String station;
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
	 * 实际设备opr
	 */
	private Float actualEquipmentOpr;
	/**
	 * 实际生产opr
	 */
	private Float actualProductionOpr;
	/**
	 * 目标设备opr
	 */
	private Float targetEquipmentOpr;
	/**
	 * 目标生产opr
	 */
	private Float targetProductionOpr;
	/**
	 * 生产量
	 */
	private Integer productionVolume;
	/**
	 * opr等级
	 */
	private String oprLevel;
	/**
	 * 时间频率
	 */
	private String frequency;
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
	 * 工作日
	 */
	private Date workDay;
	/**
	 * 设备ID
	 */
	private Integer facilityId;
	/**
	 * 停机时间
	 */
	private Integer downtime;

}
