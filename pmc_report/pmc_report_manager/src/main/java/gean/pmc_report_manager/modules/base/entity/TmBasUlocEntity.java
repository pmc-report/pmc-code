package gean.pmc_report_manager.modules.base.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 工位信息
 * 
 * @author Jason
 * @email xxxxx@gmail.com
 * @date 2019-03-22 09:54:06
 */
@Data
@TableName("TM_BAS_ULOC")
public class TmBasUlocEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 工位本地标识码
	 */
	@TableId
	private Integer tmBasUlocId;
	/**
	 * 工位编号
	 */
	private String ulocNo;
	/**
	 * 车间ID
	 */
	private Integer tmBasWorkshopId;
	/**
	 * 上次更改时间
	 */
	private Date lastUpdateTime;
	/**
	 * 更改用户
	 */
	private String lastUpdateUsername;
	/**
	 * 工位顺序号
	 */
	private Integer ulocWorkstationsequence;
	/**
	 * 是否激活
	 */
	private Integer markStatus;
	/**
	 * 工位名称
	 */
	private String ulocName;
	/**
	 * 产线ID
	 */
	private Integer tmBasLineId;
	/**
	 * 车间编号
	 */
	private String workshopNo;
	/**
	 * 工厂ID
	 */
	private Integer tmBasPlantId;
	/**
	 * 工厂编号
	 */
	private String plantNo;
	/**
	 * 主线匹配工位
	 */
	private Integer mapUloc;
	/**
	 * $column.comments
	 */
	private Integer optCounter;
	/**
	 * 前置扫描点
	 */
	private String scanNo;
	/**
	 * 产线编号
	 */
	private String lineNo;
	/**
	 * 距离车身数
	 */
	private Integer distanceVarNum;
	/**
	 * $column.comments
	 */
	private Integer tmBasWorksectionId;
	/**
	 * $column.comments
	 */
	private Integer jisReadStatus;
	/**
	 * $column.comments
	 */
	private Integer planReadStatus;
	/**
	 * $column.comments
	 */
	private Integer innerReadStatus;
	/**
	 * $column.comments
	 */
	private Integer mrReadStatus;
	/**
	 * $column.comments
	 */
	private Integer pdeliveryReadStatus;
	/**
	 * $column.comments
	 */
	private Integer settelProStatus;
	/**
	 * $column.comments
	 */
	private String userforWorkshop;
	/**
	 * $column.comments
	 */
	private String tlsReadStatus;
	/**
	 */
	private Integer atcLongReadStatus;

}
