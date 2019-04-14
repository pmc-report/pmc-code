package gean.pmc_report_manager.modules.report.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_manager.modules.report.entity.TaBiw3OprEntity;
import gean.pmc_report_manager.modules.report.vo.AreaOprVo;

/**
 * ${comments}
 *
 * @author ''
 * @email xxxxx@gmail.com
 * @date 2019-04-10 13:54:04
 */
public interface TaBiw3OprService extends IService<TaBiw3OprEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    List<AreaOprVo> queryOprForArea(Map<String, Object> params);
}

