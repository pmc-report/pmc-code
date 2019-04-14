package gean.pmc_report_manager.modules.base.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author ''
 * @email xxxxx@gmail.com
 * @date 2019-04-13 23:24:54
 */
@Data
@TableName("TM_BAS_ZONE")
public class TmBasZoneEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * zone Id
	 */
	@TableId
	private Integer tmBasZoneId;
	/**
	 * 产线Id
	 */
	private Integer tmBasLineId;
	/**
	 * zone编号
	 */
	private String zoneNo;
	/**
	 * 产线编号
	 */
	private String lineNo;
	/**
	 * 最后更新时间
	 */
	private Date lastUpdateTime;
	/**
	 * 最后更新用户
	 */
	private String lastUpdateUsername;
	/**
	 * 是否启用（1：启用，0：未启用）
	 */
	private Integer markStatus;
	/**
	 * 车间编号
	 */
	private String workshopNo;

}
