package gean.pmc_report_manager.modules.report.service;

import com.baomidou.mybatisplus.extension.service.IService;
import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_manager.modules.report.entity.TaEquFaultEntity;

import java.util.Map;

/**
 * ${comments}
 *
 * @author Jason
 * @email xxxxx@gmail.com
 * @date 2019-03-30 09:27:53
 */
public interface EquFaultService extends IService<TaEquFaultEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    /**
     * 根据页面参数查询设备故障
     * @param params
     * @return
     */
    PageUtils queryEquFaultByParam(Map<String, Object> params);
}

