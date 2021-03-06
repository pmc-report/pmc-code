package gean.pmc_report_manager.modules.base.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_manager.modules.base.entity.AtPmcMasterdataConfigEntity;
import gean.pmc_report_manager.modules.report.vo.MasterDataVo;

/**
 * ${comments}
 *
 * @author ''
 * @email xxxxx@gmail.com
 * @date 2019-04-15 17:06:26
 */
public interface AtPmcMasterdataConfigService extends IService<AtPmcMasterdataConfigEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    /** 所有车间*/
    List<MasterDataVo> queryMasterDataShop();
    
    /** 车间下的区域*/
    List<MasterDataVo> queryMasterDataLine(String shopNo);
    
    /** 区域下的zone*/
    List<MasterDataVo> queryMasterDataZone(Map<String, Object> params);
    
    /** zone下的工位*/
    List<MasterDataVo> queryMasterDataStation(Map<String, Object> params);
    
    /** 工位下的设备*/
    List<MasterDataVo> queryMasterDataEquipment(Map<String, Object> params);
    
    /** 工位下的设备*/
    List<String> queryDates(String params);
    
    /** 查询OPR的基础参数*/
    List<MasterDataVo> queryOPRData(Map<String, Object> params);
    
    /** OPR shift plan*/
    Integer queryShiftPlan(Map<String, Object> params);
    
    /** Area OPR Eol*/
    MasterDataVo queryEolArea(Map<String, Object> params);
    
    /** 前一班次信息*/
    Map<String,Object> queryBeforeShift();
    /** 当前班次信息*/
    Map<String,Object> queryCurrentShift();
    
    /**查询TAV信息*/
    List<MasterDataVo> queryTAV(Map<String, Object> params);
    
    /**查询MS信息*/
    List<String> queryMS(Map<String, Object> params);
}

