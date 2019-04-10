package gean.pmc_report_manager.modules.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_manager.modules.base.entity.TmBasModelEntity;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 *
 * @author Jason
 * @email xxxxx@gmail.com
 * @date 2019-04-03 14:00:04
 */
public interface TmBasModelService extends IService<TmBasModelEntity> {

	List<TmBasModelEntity>queryJobId(Map<String, Object> params);
    PageUtils queryPage(Map<String, Object> params);
}

