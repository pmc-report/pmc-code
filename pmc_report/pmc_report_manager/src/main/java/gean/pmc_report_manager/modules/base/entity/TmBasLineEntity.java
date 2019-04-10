package gean.pmc_report_manager.modules.base.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 产线基础信息表
 * 
 * @author Jason
 * @email xxxxx@gmail.com
 * @date 2019-03-22 09:53:33
 */
@Data
@TableName("TM_BAS_LINE")
public class TmBasLineEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 产线ID
	 */
	@TableId
	private Integer tmBasLineId;
	/**
	 * 产线编号
	 */
	private String lineNo;
	/**
	 * 工厂ID
	 */
	private Integer tmBasPlantId;
	/**
	 * 产线类型
	 */
	private String lineType;
	/**
	 * 车间ID
	 */
	private Integer tmBasWorkshopId;
	/**
	 * 最后更新日期
	 */
	private Date lastUpdateTime;
	/**
	 * 最后更新用户
	 */
	private String lastUpdateUsername;
	/**
	 * SAP对应产线编号
	 */
	private String sapLineNo;
	/**
	 * 产线中文名称
	 */
	private String lineName;
	/**
	 * 产线英文名称
	 */
	private String lineNameE;
	/**
	 * 是否激活
	 */
	private Integer markStatus;
	/**
	 * 产线JPH
	 */
	private Integer lineJph;
	/**
	 * $column.comments
	 */
	private Integer optCounter;
	/**
	 * 工厂编号
	 */
	private String plantNo;
	/**
	 * 车间编号
	 */
	private String workshopNo;

}
