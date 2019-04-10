package gean.pmc_report_manager.modules.report.service;

import com.baomidou.mybatisplus.extension.service.IService;

import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_manager.modules.report.entity.FinalBiw3OprEntity;

import java.util.Map;

/**
 * ${comments}
 *
 * @author Jason
 * @email xxxxx@gmail.com
 * @date 2019-03-11 14:13:04
 */
public interface FinalBiw3OprService extends IService<FinalBiw3OprEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

