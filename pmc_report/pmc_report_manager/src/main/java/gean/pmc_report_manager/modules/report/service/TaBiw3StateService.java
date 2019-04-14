package gean.pmc_report_manager.modules.report.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_manager.modules.report.entity.TaBiw3StateEntity;
import gean.pmc_report_manager.modules.report.vo.ZoneOprVo;

/**
 * ${comments}
 *
 * @author ''
 * @email xxxxx@gmail.com
 * @date 2019-04-11 10:56:37
 */
public interface TaBiw3StateService extends IService<TaBiw3StateEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    List<ZoneOprVo> queryOprForZone(Map<String, Object> params);
}

