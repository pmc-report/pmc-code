package gean.pmc_report_manager.modules.report.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_manager.modules.report.entity.TaBiw3CycleEntity;
import gean.pmc_report_manager.modules.report.vo.CycleVo;


public interface TaBiw3CycleService extends IService<TaBiw3CycleEntity>{
	
	/**
	 * 根据页面参数查询
	 * @param params
	 * @return
	 */
	PageUtils queryBiw3CycleByParam(Map<String, Object> params);
	
	List<CycleVo> queryEchart(Map<String, Object> params);

	

}
