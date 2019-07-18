package gean.pmc_report_manager.modules.report.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import gean.pmc_report_manager.modules.base.service.AtPmcMasterdataConfigService;
import gean.pmc_report_manager.modules.report.dao.TaEquFaultDao;
import gean.pmc_report_manager.modules.report.entity.TaEquFaultEntity;
import gean.pmc_report_manager.modules.report.service.FaultOrderService;
import gean.pmc_report_manager.modules.report.vo.FaultOrderVo;
import gean.pmc_report_manager.modules.report.vo.PageParamVo;

@Service("faultOrderService")
public class FaultOrderServiceImpl extends ServiceImpl<TaEquFaultDao, TaEquFaultEntity> implements FaultOrderService{

	@Autowired
	private AtPmcMasterdataConfigService masterService;
	
	@Override
	public List<FaultOrderVo> queryCurrTop10Fualt(Map<String, Object> param,int flag) {
		
		Map<String,Object> currShiftInfo = masterService.queryCurrentShift();
		
		Date st = (Date)currShiftInfo.get("START_TIME_T");
		Date et = (Date)currShiftInfo.get("END_TIME_T");
		
		//Map<String,Object> beforeShiftInfo = masterService.queryBeforeShift();
		
		PageParamVo paramVo = new PageParamVo(param);
		paramVo.setStartTime(st);
		paramVo.setEndTime(et);
		paramVo.setFlag(flag);
		
		List<FaultOrderVo>  list = baseMapper.queryCurrShiftFaults(paramVo);
		if(list.size()>0) {
			return list;
		}
		return null;
	}

	@Override
	public List<FaultOrderVo> queryPreTop10Fualt(Map<String, Object> param, int flag) {
		
		Map<String,Object> beforeShiftInfo = masterService.queryBeforeShift();
		
		Date st = (Date)beforeShiftInfo.get("START_TIME_T");
		Date et = (Date)beforeShiftInfo.get("END_TIME_T");
		
		PageParamVo paramVo = new PageParamVo(param);
		paramVo.setStartTime(st);
		paramVo.setEndTime(et);
		paramVo.setFlag(flag);
		
		List<FaultOrderVo>  list = baseMapper.queryPreShiftFaults(paramVo);
		if(list.size()>0) {
			return list;
		}
		return null;
	}

}
