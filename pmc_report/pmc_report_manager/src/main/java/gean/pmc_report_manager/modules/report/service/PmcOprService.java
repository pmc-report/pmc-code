package gean.pmc_report_manager.modules.report.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_manager.modules.report.entity.PmcOprEntity;
import gean.pmc_report_manager.modules.report.vo.PageParamVo;

/**
 * ${comments}
 *
 * @author Jason
 * @email xxxxx@gmail.com
 * @date 2019-03-14 13:22:17
 */
public interface PmcOprService extends IService<PmcOprEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    PageUtils queryOprReport(Map<String, Object> params);
}

