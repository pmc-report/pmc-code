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
 * ${comments}
 * 
 * @author ''
 * @email xxxxx@gmail.com
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
}
