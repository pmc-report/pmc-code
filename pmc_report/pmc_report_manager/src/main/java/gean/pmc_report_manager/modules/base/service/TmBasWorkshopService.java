package gean.pmc_report_manager.modules.base.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_manager.modules.base.entity.TmBasWorkshopEntity;

/**
 * 车间基本信息
 *
 * @author Jason
 * @email xxxxx@gmail.com
 * @date 2019-03-19 16:45:21
 */
public interface TmBasWorkshopService extends IService<TmBasWorkshopEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    List<TmBasWorkshopEntity> queryAllShop();
}

