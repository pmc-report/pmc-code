package gean.pmc_report_manager.modules.report.service;

import java.util.List;
import java.util.Map;

import gean.pmc_report_manager.modules.report.vo.TaSummaryVo;

public interface TaSummaryService {

	/**
	 * 查询设备可用率对比结果
	 * @param param
	 * @return
	 */
	List<TaSummaryVo> queryTaSummary (Map<String,Object> param);
	
	/**
	 * 查询趋势EChart
	 */
	List<TaSummaryVo> queryTrendCahrt(Map<String,Object> param);
}
