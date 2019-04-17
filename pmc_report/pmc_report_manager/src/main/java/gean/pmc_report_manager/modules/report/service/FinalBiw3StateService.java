package gean.pmc_report_manager.modules.report.service;

import com.baomidou.mybatisplus.extension.service.IService;
import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_manager.modules.report.entity.FinalBiw3StateEntity;

import java.util.Map;

/**
 * ${comments}
 *
 * @author ''
 * @email xxxxx@gmail.com
 * @date 2019-04-17 21:02:05
 */
public interface FinalBiw3StateService extends IService<FinalBiw3StateEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

