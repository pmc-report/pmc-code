package gean.pmc_report_manager.modules.base.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 车间基本信息
 * 
 * @author Jason
 * @email xxxxx@gmail.com
 * @date 2019-03-19 16:45:21
 */
@Data
@TableName("TM_BAS_WORKSHOP")
public class TmBasWorkshopEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 车间ID
	 */
	@TableId
	private Integer tmBasWorkshopId;
	/**
	 * 车间编号
	 */
	private String shopNo;
	/**
	 * 工厂ID
	 */
	private Integer tmBasPlantId;
	/**
	 * SAP车间编号
	 */
	private String sapWorkshopNo;
	/**
	 * 车间中文名称
	 */
	private String shopName;
	/**
	 * 车间英文名称
	 */
	private String workshopNameE;
	/**
	 * 车间中文简称
	 */
	private String workshopNameCS;
	/**
	 * 车间英文简称
	 */
	private String workshopNameES;
	/**
	 * 上次更改时间
	 */
	private Date lastUpdateTime;
	/**
	 * 更改用户
	 */
	private String lastUpdateUsername;
	/**
	 * 是否激活
	 */
	private Integer markStatus;
	/**
	 * $column.comments
	 */
	private Integer optCounter;
	/**
	 * 工厂编号
	 */
	private String plantNo;
	/**
	 * $column.comments
	 */
	private String deliveryUnitNo;
	/**
	 * 线边仓库
	 */
	private String warehouseNo;

}
