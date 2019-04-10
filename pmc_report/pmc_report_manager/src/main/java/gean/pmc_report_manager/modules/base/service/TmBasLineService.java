package gean.pmc_report_manager.modules.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_manager.modules.base.entity.TmBasLineEntity;
import gean.pmc_report_manager.modules.base.entity.TmBasWorkshopEntity;

import java.util.List;
import java.util.Map;

/**
 * 产线基础信息表
 *
 * @author Jason
 * @email xxxxx@gmail.com
 * @date 2019-03-22 09:53:33
 */
public interface TmBasLineService extends IService<TmBasLineEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    List<TmBasLineEntity> queryLine(String shop);
}

