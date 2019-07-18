package gean.pmc_report_manager.modules.base.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import gean.pmc_report_datasource.annotation.DataSource;
import gean.pmc_report_manager.modules.base.entity.AtPmcMasterdataConfigEntity;
import gean.pmc_report_manager.modules.report.vo.MasterDataVo;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author ''
 * @date 2019-04-15 17:06:26
 */
@Mapper
public interface AtPmcMasterdataConfigDao extends BaseMapper<AtPmcMasterdataConfigEntity> {
	
	/** 所有车间*/
	@Transactional
    @DataSource("slave1")
	List<MasterDataVo> queryAllShop();

    
    /** 车间下的区域*/
	@Transactional
    @DataSource("slave1")
    List<MasterDataVo> queryAllLineForShop(String params);
    
    /** 区域下的zone*/
	@Transactional
    @DataSource("slave1")
    List<MasterDataVo> queryAllZoneForLine(Map<String, Object> params);
    
    /** zone下的工位*/
	@Transactional
    @DataSource("slave1")
    List<MasterDataVo> queryAllStationForZone(Map<String, Object> params);
    
    /** 工位下的设备*/
	@Transactional
    @DataSource("slave1")
    List<MasterDataVo> queryEquipmentForStation(Map<String, Object> params);
	
	 /** 工位下的设备*/
	@Transactional
    @DataSource("slave1")
    List<String> queryDates(String params);
	
	/** OPR基础查询：设备ID、zone、设计节拍时间*/
	@Transactional
    @DataSource("slave1")
    List<MasterDataVo> queryOPRData(Map<String, Object> params);
	
	/** OPR shift plan*/
	@Transactional
    @DataSource("slave1")
    Integer queryOPRShitfPlan(Map<String, Object> params);
	
	/** Area OPR EOL*/
	@Transactional
    @DataSource("slave1")
	List<MasterDataVo> queryEolArea(Map<String, Object> params);
	
	/** 查当前一班次信息*/
	@Transactional
    @DataSource("slave2")
	 Map<String,Object> queryBeforeShift();
	
	/** 查当当班次信息*/
	@Transactional
    @DataSource("slave2")
	 Map<String,Object> queryCurrentShift();
	
}
