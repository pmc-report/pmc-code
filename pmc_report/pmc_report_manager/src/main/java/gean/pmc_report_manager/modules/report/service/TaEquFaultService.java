package gean.pmc_report_manager.modules.report.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_manager.modules.report.entity.TaEquFaultEntity;
import gean.pmc_report_manager.modules.report.vo.FaultVo;

/**
 * ${comments}
 *
 * @author Jason
 * @email xxxxx@gmail.com
 * @date 2019-03-30 09:27:53
 */
public interface TaEquFaultService extends IService<TaEquFaultEntity> {
	
	TaEquFaultEntity queryTotalMins(Map<String, Object> params);

    PageUtils queryPage(Map<String, Object> params);
    
    /**
     * 根据页面参数查询设备故障
     * @param params
     * @return
     */
    PageUtils queryEquFaultByParam(Map<String, Object> params);
    
    /**
     * 导出
     * @param params
     * @return
     */
    List<FaultVo> queryExportFault(Map<String, Object> params);

    
    List<FaultVo> queryFaultsList(Map<String, Object> params);
}

