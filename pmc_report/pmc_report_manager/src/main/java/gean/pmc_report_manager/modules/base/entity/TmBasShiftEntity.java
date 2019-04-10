package gean.pmc_report_manager.modules.base.entity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


/**
 * ${comments}
 * 
 * @author Jason
 * @email xxxxx@gmail.com
 * @date 2019-04-04 11:17:03
 */
@Data
@TableName("TM_BAS_SHIFT")
public class TmBasShiftEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 班次ID
	 */
	@TableId
	private Integer tmBasShiftId;
	/**
	 * 班次编码
	 */
	private String shiftNo;
	/**
	 * 开始时间
	 */
	private String startTime;
	/**
	 * 结束时间
	 */
	private String endTime;
	/**
	 * 休息时间编号
	 */
	private String breakPattern;
	/**
	 * 最后更新时间
	 */
/*	private unknowType lastUpdateTime;
	*//**
	 * 最后更新用户
	 */
	private String lastUpdateUsername;
	/**
	 * 启用状态（1：启用，0：未启用）
	 */
	private Integer markStatus;
	/**
	 * $column.comments
	 */
	private Integer optCounter;
	/**
	 * 车间编号
	 */
	private String workshopNo;
	/**
	 * $column.comments
	 */
	private String shiftDesc;

}
