package gean.pmc_report_manager.modules.report.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_manager.modules.report.entity.PmcBiwFaultEntity;

/**
 * ${comments}
 *
 * @author Jason
 * @email xxxxx@gmail.com
 * @date 2019-03-30 09:27:53
 */
public interface EquFaultService extends IService<PmcBiwFaultEntity> {
	
	List<PmcBiwFaultEntity> queryTotalMins(Map<String, Object> params);

    PageUtils queryPage(Map<String, Object> params);
    
    /**
     * 根据页面参数查询设备故障
     * @param params
     * @return
     */
    PageUtils queryEquFaultByParam(Map<String, Object> params);
}

