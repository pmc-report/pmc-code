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
 * @date 2019-04-15 17:06:26
 */
@Data
@TableName("AT_PMC_MASTERDATA_CONFIG")
public class AtPmcMasterdataConfigEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * $column.comments
	 */
	@TableId
	private Integer atrKey;
	/**
	 * $column.comments
	 */
	private Integer siteNum;
	/**
	 * $column.comments
	 */
	private String atrName;
	/**
	 * $column.comments
	 */
	private Integer purgeStatus;
	/**
	 * $column.comments
	 */
	private Date creationTime;
	/**
	 * $column.comments
	 */
	private Date creationTimeU;
	/**
	 * $column.comments
	 */
	private String creationTimeZ;
	/**
	 * $column.comments
	 */
	private Date lastModifiedTime;
	/**
	 * $column.comments
	 */
	private Date lastModifiedTimeU;
	/**
	 * $column.comments
	 */
	private String lastModifiedTimeZ;
	/**
	 * $column.comments
	 */
	private Integer xfrInsertPid;
	/**
	 * $column.comments
	 */
	private Integer xfrUpdatePid;
	/**
	 * $column.comments
	 */
	private String trxId;
	/**
	 * $column.comments
	 */
	private Integer parentKey;
	/**
	 * $column.comments
	 */
	private Integer designCycleTimeI;
	/**
	 * $column.comments
	 */
	private Integer eolFlagY;
	/**
	 * $column.comments
	 */
	private String equipmentS;
	/**
	 * $column.comments
	 */
	private Integer facilityIdI;
	/**
	 * $column.comments
	 */
	private String lineS;
	/**
	 * $column.comments
	 */
	private String shopS;
	/**
	 * $column.comments
	 */
	private String stationS;
	/**
	 * $column.comments
	 */
	private Float targetMtbfF;
	/**
	 * $column.comments
	 */
	private Float targetTavF;
	/**
	 * $column.comments
	 */
	private Integer tavPosI;
	/**
	 * $column.comments
	 */
	private Integer tavStationI;
	/**
	 * $column.comments
	 */
	private String zoneS;
	/**
	 * $column.comments
	 */
	private Integer productionFlagY;
	/**
	 * $column.comments
	 */
	private String equipmentTypeS;
	/**
	 * $column.comments
	 */
	private Integer standardCycleTimeI;
	/**
	 * $column.comments
	 */
	private String facilityDescS;
	/**
	 * $column.comments
	 */
	private Float targetEquipmentOprF;
	/**
	 * $column.comments
	 */
	private Float targetOeeF;
	/**
	 * $column.comments
	 */
	private Float targetProductionOprF;
	/**
	 * $column.comments
	 */
	private Integer updateFlagI;

}
