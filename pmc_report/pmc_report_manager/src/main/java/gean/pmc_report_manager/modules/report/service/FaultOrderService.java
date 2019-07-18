package gean.pmc_report_manager.modules.report.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import gean.pmc_report_manager.modules.report.entity.TaEquFaultEntity;
import gean.pmc_report_manager.modules.report.vo.FaultOrderVo;

/**
 * Top10 fault service
 * @author Jason D
 *
 */
public interface FaultOrderService  extends IService<TaEquFaultEntity>{

	/**
	 * 查询当前班次前十的
	 * @param param
	 * @return
	 */
	List<FaultOrderVo> queryCurrTop10Fualt(Map<String,Object> param,int flag);
	
	/**
	 * 查询前一班次前十的
	 * @param param
	 * @return
	 */
	List<FaultOrderVo> queryPreTop10Fualt(Map<String,Object> param,int flag);
}
