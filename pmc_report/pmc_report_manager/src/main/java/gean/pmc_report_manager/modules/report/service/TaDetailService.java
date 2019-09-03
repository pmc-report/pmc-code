package gean.pmc_report_manager.modules.report.service;

import java.util.List;
import java.util.Map;

import gean.pmc_report_manager.modules.report.vo.FaultVo;
import gean.pmc_report_manager.modules.report.vo.MsDataVo;
import gean.pmc_report_manager.modules.report.vo.TaDetailVo;

public interface TaDetailService {
	
	/**
	 * 查询设备TA和设备故障信息
	 * @param param
	 * @return
	 */
	Map<String,List<TaDetailVo>> queryTAVInfo(Map<String,Object> param);
	
	List<FaultVo> findFaults(Map<String, Object> param);

	/**
	 * 查询TAV信息
	 * @param params
	 * @return
	 */
	Map<String,List<MsDataVo>> queryTaDetailList(Map<String, Object> params);
}
