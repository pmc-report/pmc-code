package gean.pmc_report_manager.modules.report.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import gean.pmc_report_manager.modules.report.dao.TaBiw3OprDao;
import gean.pmc_report_manager.modules.report.dao.TaEquFaultDao;
import gean.pmc_report_manager.modules.report.entity.TaEquFaultEntity;
import gean.pmc_report_manager.modules.report.service.FaultLossesOccurrencesService;
import gean.pmc_report_manager.modules.report.vo.LossOPRVo;
import gean.pmc_report_manager.modules.report.vo.PageParamVo;

@Service("faultLossesOccurrencesService")
public class FaultLossesOccurrencesServiceImpl extends ServiceImpl<TaEquFaultDao, TaEquFaultEntity> implements FaultLossesOccurrencesService{

	@Autowired
	private TaBiw3OprDao oprDao;
	
	/**
	 * 查询 Trend Chart OPR
	 */
	@Override
	public List<LossOPRVo> queryOprEchart(Map<String, Object> params) {
		
		PageParamVo paramVo = new PageParamVo(params);
		
		DecimalFormat df = new DecimalFormat("##0.00");
		List<LossOPRVo> oprLossList = oprDao.queryLossOpr(paramVo);
		List<LossOPRVo>  oprLossResult = new ArrayList<>();
		
		if(oprLossList.size()>0) {
			
			for(LossOPRVo vo : oprLossList) {
				
				LossOPRVo oprLossVo = new LossOPRVo();
				if(vo.getMonday()!=null) {
					oprLossVo.setMonday(vo.getMonday());
				}
				if(vo.getActualEOpr()> -1 ) {
					oprLossVo.setActualEOpr(Float.parseFloat(df.format(vo.getActualEOpr())));
					oprLossVo.setTargetEOpr(Float.parseFloat(df.format(vo.getTargetEOpr())));
				}
				if(vo.getActualPOpr()> -1 ) {
					oprLossVo.setActualPOpr(Float.parseFloat(df.format(vo.getActualPOpr())));
					oprLossVo.setTargetPOpr(Float.parseFloat(df.format(vo.getTargetPOpr())));
				}
				oprLossVo.setStartTime(vo.getStartTime());
				oprLossVo.setWeek(vo.getWeek());
				oprLossVo.setWeekRange(vo.getWeekRange());
				oprLossVo.setWeekNo(vo.getWeekNo());
				oprLossResult.add(oprLossVo);
			}
		}
		if(oprLossResult.size()>0) {
			return oprLossResult;
		}
		return null;
	}
	
	/**
	 * 查询 Production Loss Pareto
	 */
	@Override
	public List<LossOPRVo> queryEquEchart(Map<String, Object> params) {

		PageParamVo paramVo = new PageParamVo(params);
		
		List<LossOPRVo> faultLossList = new ArrayList<>();
		List<LossOPRVo> top10FaultList = baseMapper.queryTop10Fault(paramVo);
		
		if(top10FaultList.size()>0) {
			
			for(LossOPRVo vo : top10FaultList) {
				
				LossOPRVo faultLossVo = new LossOPRVo();
				faultLossVo.setFacilityId2(vo.getFacilityId2());
				if(vo.getDuration()> -1) {
					faultLossVo.setDuration(vo.getDuration());
				}
				faultLossList.add(faultLossVo);
			}
		}
		if(faultLossList.size()>0) {
			return faultLossList;
		}
		return null;
	}

	/**
	 * 查询 Trend Chart Minutes Loss
	 */
	@Override
	public List<LossOPRVo> queryMinutesEchart(Map<String, Object> params) {
		
		PageParamVo paramVo = new PageParamVo(params);
		
		List<LossOPRVo>  allFaultLossResult = new ArrayList<>();
		List<LossOPRVo> allFaultList = baseMapper.queryFaultLoss(paramVo);
		
		if(allFaultList.size()>0) {
			
			Map<Integer,Integer> groupMap = new HashMap<>();
			int totalLoss = 0;
			for(LossOPRVo vo : allFaultList) {
				if(vo.getWeekNo2()==null||vo.getWeekNo2()<=0)continue;
				if(groupMap.containsKey(vo.getWeekNo2())) {
					totalLoss += (int)groupMap.get(vo.getWeekNo2());
				}else {
					groupMap.put(vo.getWeekNo2(), vo.getLoss());
				}
			}
			
			for(Integer week : groupMap.keySet()) {
				LossOPRVo allLossVo = new LossOPRVo();
				allLossVo.setWeekNo2(week);
				allLossVo.setLoss(groupMap.get(week)/60);
				allFaultLossResult.add(allLossVo);
			}
		}
		if(allFaultLossResult.size()>0) {
			return allFaultLossResult;
		}
		return null;
	}


	@Override
	public List<LossOPRVo> queryFaultLossOcc(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
