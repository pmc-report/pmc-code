package gean.pmc_report_manager.modules.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_manager.modules.base.entity.TmBasZoneEntity;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 *
 * @author ''
 * @email xxxxx@gmail.com
 * @date 2019-04-13 23:24:54
 */
public interface TmBasZoneService extends IService<TmBasZoneEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    List<TmBasZoneEntity> queryZone(String line);
}

