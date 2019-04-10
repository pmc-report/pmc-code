package gean.pmc_report_manager.modules.base.entity;

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
 * @date 2019-04-03 14:00:04
 */
@Data
@TableName("TM_BAS_MODEL")
public class TmBasModelEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 车型ID
	 */
	@TableId
	private Integer tmBasModelId;
	/**
	 * 工厂ID
	 */
	private Integer tmBasWorkshopId;
	/**
	 * 工厂编号
	 */
	private String workshopNo;
	/**
	 * 车型编号
	 */
	private String modelNo;
	/**
	 * 最后更新时间
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

}
