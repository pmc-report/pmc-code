package gean.pmc_report_manager.modules.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_manager.modules.base.entity.TmBasShiftEntity;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 *
 * @author Jason
 * @email xxxxx@gmail.com
 * @date 2019-04-04 11:17:03
 */
public interface TmBasShiftService extends IService<TmBasShiftEntity> {

	List<String> queryShift();
	
    PageUtils queryPage(Map<String, Object> params);
}

