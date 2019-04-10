package gean.pmc_report_manager.modules.report.service;

import com.baomidou.mybatisplus.extension.service.IService;

import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_manager.modules.report.entity.PmcBiwStateEntity;

import java.util.Map;

/**
 * ${comments}
 *
 * @author Jason
 * @email xxxxx@gmail.com
 * @date 2019-03-14 09:49:18
 */
public interface PmcBiwStateService extends IService<PmcBiwStateEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

