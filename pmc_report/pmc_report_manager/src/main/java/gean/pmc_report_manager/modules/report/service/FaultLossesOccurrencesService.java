package gean.pmc_report_manager.modules.report.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_manager.modules.report.entity.TaEquFaultEntity;
import gean.pmc_report_manager.modules.report.vo.LossOPRVo;

/**
 * 故障损失
 * @author Jason D
 * @date 20190711
 */
public interface FaultLossesOccurrencesService extends IService<TaEquFaultEntity>{

	/**
	 * 查询图表数据
	 * @param params
	 * @return echart data list
	 */
	List<LossOPRVo> queryOprEchart(Map<String, Object> params);
	List<LossOPRVo> queryEquEchart(Map<String, Object> params);
	List<LossOPRVo> queryMinutesEchart(Map<String, Object> params);
	/**
	 * 查询表格数据
	 * @param params
	 * @return fault loss list
	 */
	List<LossOPRVo> queryFaultLossOcc(Map<String, Object> params);
}
