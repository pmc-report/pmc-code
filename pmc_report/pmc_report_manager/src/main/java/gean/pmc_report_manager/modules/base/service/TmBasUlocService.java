package gean.pmc_report_manager.modules.base.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_manager.modules.base.entity.TmBasUlocEntity;

/**
 * 工位信息
 *
 * @author Jason
 * @email xxxxx@gmail.com
 * @date 2019-03-22 09:54:06
 */
public interface TmBasUlocService extends IService<TmBasUlocEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    List<TmBasUlocEntity> queryUloc(String line);
}

