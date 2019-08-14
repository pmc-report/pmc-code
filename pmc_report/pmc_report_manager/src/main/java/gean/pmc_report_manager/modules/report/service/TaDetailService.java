package gean.pmc_report_manager.modules.report.service;

import java.util.List;
import java.util.Map;

import gean.pmc_report_manager.modules.report.vo.FaultVo;

public interface TaDetailService {
	
	/**
	 * 查询TAV信息
	 * @param param
	 * @return
	 */
	List<FaultVo> queryTAVInfo(Map<String,Object> param);

}
