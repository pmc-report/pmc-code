package gean.pmc_report_manager.modules.report.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import gean.pmc_report_common.common.utils.DateUtils;
import gean.pmc_report_common.common.utils.StringUtils;
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
			
			Collections.sort(allFaultLossResult, new Comparator<LossOPRVo>() {
				@Override
				public int compare(LossOPRVo o1, LossOPRVo o2) {
					int i = o1.getWeekNo2() - o2.getWeekNo2();
					return i;
				}});
			return allFaultLossResult;
		}
		return null;
	}


	@Override
	public List<LossOPRVo> queryFaultLossOcc(Map<String, Object> params) {
	
		/*
		 * 1.遍历list 周做key 对象做value 存map
		 * 2.遍历map
		 */
		PageParamVo paramVo = new PageParamVo(params);
		Map<Integer,List<LossOPRVo>> groupMap = new HashMap<>();
		List<LossOPRVo> allFaultList = baseMapper.queryFaultLoss(paramVo);
		List<LossOPRVo> lossList = new ArrayList<>();
		if(allFaultList.size()>0) {
			/*
			 * 组装数据-遍历所有的数据，按照weekNo、设备ID划分
			 */
			for(LossOPRVo vo : allFaultList) {
				Integer idKey = vo.getFacilityId();
				if(StringUtils.isNotNull(idKey)) {
					List<LossOPRVo> tempList = groupMap.get(idKey);
					if(!StringUtils.isEmpty(tempList)) {
						tempList.add(vo);
					}else {
						tempList = new ArrayList<>();
						tempList.add(vo);
						groupMap.put(idKey, tempList);
					}
				}
			}
			
			Set<Integer> idSet = groupMap.keySet();
			for(Integer id : idSet) {
				List<LossOPRVo> list = groupMap.get(id);
				if(!StringUtils.isEmpty(list)) {
					LossOPRVo loss = generateLossList(list);
					lossList.add(loss);
				}
			}
		}
		
		Collections.sort(lossList, new Comparator<LossOPRVo>() {
			@Override
			public int compare(LossOPRVo o1, LossOPRVo o2) {
				int i = o1.getFacilityId() - o2.getFacilityId();
				return i;
			}});
		
		return lossList;
	}

	private LossOPRVo generateLossList(List<LossOPRVo> lossList){
		
		DecimalFormat df = new DecimalFormat("##0.00");
		Calendar ca = Calendar.getInstance();
		int curWeek = DateUtils.weekOfYear();
		float loss = 0.00f;
		int occ = 0;
		boolean flag1 = true;
		boolean flag2 = true;
		boolean flag3 = true;
		boolean flag4 = true;
		boolean flag5 = true;
		boolean flag6 = true;
		boolean flag7 = true;
		boolean flag8 = true;
		boolean flag9 = true;
		boolean flag10 = true;
		boolean flag11 = true;
		boolean flag12 = true;
		LossOPRVo lossVo = new LossOPRVo();
		//遍历分组后的lossList数据
		for(LossOPRVo vo : lossList) {
			//生成最近12周的周数
			//创建实例对象
			if(StringUtils.isNotNull(vo.getWeekNo2())) {
				Float faultLoss = Float.parseFloat(df.format((float)vo.getLoss()/60));
				if(curWeek==vo.getWeekNo2()) {
					lossVo.setLoss1(faultLoss);
					lossVo.setOcc1(vo.getOcc()==null?0:vo.getOcc());
					lossVo.setFacilityId(vo.getFacilityId());
					lossVo.setFacilityDesc(vo.getFacilityDesc());
					lossVo.setInput("");
					lossVo.setPps("");
					lossVo.setYear(ca.get(Calendar.YEAR)+"");
					lossVo.setWeek1(vo.getWeekNo2());
					flag1 = false;
				}else if(flag1){
					lossVo.setLoss1(loss);
					lossVo.setOcc1(occ);
					lossVo.setFacilityId(vo.getFacilityId());
					lossVo.setFacilityDesc(vo.getFacilityDesc());
					lossVo.setInput("");
					lossVo.setPps("");
					lossVo.setYear(ca.get(Calendar.YEAR)+"");
					lossVo.setWeek1(curWeek);
					flag1 = false;
				}
				if(curWeek-1==vo.getWeekNo2()) {
					lossVo.setLoss2(faultLoss);
					lossVo.setOcc2(vo.getOcc()==null?0:vo.getOcc());
					lossVo.setFacilityId(vo.getFacilityId());
					lossVo.setFacilityDesc(vo.getFacilityDesc());
					lossVo.setInput("");
					lossVo.setPps("");
					lossVo.setYear(ca.get(Calendar.YEAR)+"");
					lossVo.setWeek2(vo.getWeekNo2());
					flag2 = false;
				}else if(flag2) {
					lossVo.setLoss2(loss);
					lossVo.setOcc2(occ);
					lossVo.setFacilityId(vo.getFacilityId());
					lossVo.setFacilityDesc(vo.getFacilityDesc());
					lossVo.setInput("");
					lossVo.setPps("");
					lossVo.setYear(ca.get(Calendar.YEAR)+"");
					lossVo.setWeek2(curWeek-1);
					flag2 = false;
				}
				if(curWeek-2==vo.getWeekNo2()) {
					lossVo.setLoss3(faultLoss);
					lossVo.setOcc3(vo.getOcc()==null?0:vo.getOcc());
					lossVo.setFacilityId(vo.getFacilityId());
					lossVo.setFacilityDesc(vo.getFacilityDesc());
					lossVo.setInput("");
					lossVo.setPps("");
					lossVo.setYear(ca.get(Calendar.YEAR)+"");
					lossVo.setWeek3(vo.getWeekNo2());
					flag2 = false;
				}else if(flag3) {
					lossVo.setLoss3(loss);
					lossVo.setOcc3(occ);
					lossVo.setFacilityId(vo.getFacilityId());
					lossVo.setFacilityDesc(vo.getFacilityDesc());
					lossVo.setInput("");
					lossVo.setPps("");
					lossVo.setYear(ca.get(Calendar.YEAR)+"");
					lossVo.setWeek3(curWeek-2);
					flag3 = false;
				}
				if(curWeek-3==vo.getWeekNo2()) {
					lossVo.setLoss4(faultLoss);
					lossVo.setOcc4(vo.getOcc()==null?0:vo.getOcc());
					lossVo.setFacilityId(vo.getFacilityId());
					lossVo.setFacilityDesc(vo.getFacilityDesc());
					lossVo.setInput("");
					lossVo.setPps("");
					lossVo.setYear(ca.get(Calendar.YEAR)+"");
					lossVo.setWeek4(vo.getWeekNo2());
					flag4 = false;
				}else if(flag4) {
					lossVo.setLoss4(loss);
					lossVo.setOcc4(occ);
					lossVo.setFacilityId(vo.getFacilityId());
					lossVo.setFacilityDesc(vo.getFacilityDesc());
					lossVo.setInput("");
					lossVo.setPps("");
					lossVo.setYear(ca.get(Calendar.YEAR)+"");
					lossVo.setWeek4(curWeek-3);
					flag4 = false;
				}
				if(curWeek-4==vo.getWeekNo2()) {
					lossVo.setLoss5(faultLoss);
					lossVo.setOcc5(vo.getOcc()==null?0:vo.getOcc());
					lossVo.setFacilityId(vo.getFacilityId());
					lossVo.setFacilityDesc(vo.getFacilityDesc());
					lossVo.setInput("");
					lossVo.setPps("");
					lossVo.setYear(ca.get(Calendar.YEAR)+"");
					lossVo.setWeek5(vo.getWeekNo2());
					flag5 = false;
				}else if(flag5) {
					lossVo.setLoss5(loss);
					lossVo.setOcc5(occ);
					lossVo.setFacilityId(vo.getFacilityId());
					lossVo.setFacilityDesc(vo.getFacilityDesc());
					lossVo.setInput("");
					lossVo.setPps("");
					lossVo.setYear(ca.get(Calendar.YEAR)+"");
					lossVo.setWeek5(curWeek-4);
					flag5 = false;
				}
				if(curWeek-5==vo.getWeekNo2()) {
					lossVo.setLoss6(faultLoss);
					lossVo.setOcc6(vo.getOcc()==null?0:vo.getOcc());
					lossVo.setFacilityId(vo.getFacilityId());
					lossVo.setFacilityDesc(vo.getFacilityDesc());
					lossVo.setInput("");
					lossVo.setPps("");
					lossVo.setYear(ca.get(Calendar.YEAR)+"");
					lossVo.setWeek6(vo.getWeekNo2());
					flag6 = false;
				}else if(flag6) {
					lossVo.setLoss6(loss);
					lossVo.setOcc6(occ);
					lossVo.setFacilityId(vo.getFacilityId());
					lossVo.setFacilityDesc(vo.getFacilityDesc());
					lossVo.setInput("");
					lossVo.setPps("");
					lossVo.setYear(ca.get(Calendar.YEAR)+"");
					lossVo.setWeek6(curWeek-5);
					flag6 = false;
				}
				if(curWeek-6==vo.getWeekNo2()) {
					lossVo.setLoss7(faultLoss);
					lossVo.setOcc7(vo.getOcc()==null?0:vo.getOcc());
					lossVo.setFacilityId(vo.getFacilityId());
					lossVo.setFacilityDesc(vo.getFacilityDesc());
					lossVo.setInput("");
					lossVo.setPps("");
					lossVo.setYear(ca.get(Calendar.YEAR)+"");
					lossVo.setWeek7(vo.getWeekNo2());
					flag7 = false;
				}else if(flag7) {
					lossVo.setLoss7(loss);
					lossVo.setOcc7(occ);
					lossVo.setFacilityId(vo.getFacilityId());
					lossVo.setFacilityDesc(vo.getFacilityDesc());
					lossVo.setInput("");
					lossVo.setPps("");
					lossVo.setYear(ca.get(Calendar.YEAR)+"");
					lossVo.setWeek7(curWeek-6);
					flag7 = false;
				}
				if(curWeek-7==vo.getWeekNo2()) {
					lossVo.setLoss8(faultLoss);
					lossVo.setOcc8(vo.getOcc()==null?0:vo.getOcc());
					lossVo.setFacilityId(vo.getFacilityId());
					lossVo.setFacilityDesc(vo.getFacilityDesc());
					lossVo.setInput("");
					lossVo.setPps("");
					lossVo.setYear(ca.get(Calendar.YEAR)+"");
					lossVo.setWeek8(vo.getWeekNo2());
					flag8 = false;
				}else if(flag8){
					lossVo.setLoss8(loss);
					lossVo.setOcc8(occ);
					lossVo.setFacilityId(vo.getFacilityId());
					lossVo.setFacilityDesc(vo.getFacilityDesc());
					lossVo.setInput("");
					lossVo.setPps("");
					lossVo.setYear(ca.get(Calendar.YEAR)+"");
					lossVo.setWeek8(curWeek-7);
					flag8 = false;
				}
				if(curWeek-8==vo.getWeekNo2()) {
					lossVo.setLoss9(faultLoss);
					lossVo.setOcc9(vo.getOcc()==null?0:vo.getOcc());
					lossVo.setFacilityId(vo.getFacilityId());
					lossVo.setFacilityDesc(vo.getFacilityDesc());
					lossVo.setInput("");
					lossVo.setPps("");
					lossVo.setYear(ca.get(Calendar.YEAR)+"");
					lossVo.setWeek9(vo.getWeekNo2());
					flag9 = false;
				}else if(flag9){
					lossVo.setLoss9(loss);
					lossVo.setOcc9(occ);
					lossVo.setFacilityId(vo.getFacilityId());
					lossVo.setFacilityDesc(vo.getFacilityDesc());
					lossVo.setInput("");
					lossVo.setPps("");
					lossVo.setYear(ca.get(Calendar.YEAR)+"");
					lossVo.setWeek9(curWeek-8);
					flag9 = false;
				}
				if(curWeek-9==vo.getWeekNo2()) {
					lossVo.setLoss10(faultLoss);
					lossVo.setOcc10(vo.getOcc()==null?0:vo.getOcc());
					lossVo.setFacilityId(vo.getFacilityId());
					lossVo.setFacilityDesc(vo.getFacilityDesc());
					lossVo.setInput("");
					lossVo.setPps("");
					lossVo.setYear(ca.get(Calendar.YEAR)+"");
					lossVo.setWeek10(vo.getWeekNo2());
					flag10 = false;
				}else if(flag10){
					lossVo.setLoss10(loss);
					lossVo.setOcc10(occ);
					lossVo.setFacilityId(vo.getFacilityId());
					lossVo.setFacilityDesc(vo.getFacilityDesc());
					lossVo.setInput("");
					lossVo.setPps("");
					lossVo.setYear(ca.get(Calendar.YEAR)+"");
					lossVo.setWeek10(curWeek-9);
					flag10 = false;
				}
				if(curWeek-10==vo.getWeekNo2()) {
					lossVo.setLoss11(faultLoss);
					lossVo.setOcc11(vo.getOcc()==null?0:vo.getOcc());
					lossVo.setFacilityId(vo.getFacilityId());
					lossVo.setFacilityDesc(vo.getFacilityDesc());
					lossVo.setInput("");
					lossVo.setPps("");
					lossVo.setYear(ca.get(Calendar.YEAR)+"");
					lossVo.setWeek11(vo.getWeekNo2());
					flag11 = false;
				}else if(flag11){
					lossVo.setLoss11(loss);
					lossVo.setOcc11(occ);
					lossVo.setFacilityId(vo.getFacilityId());
					lossVo.setFacilityDesc(vo.getFacilityDesc());
					lossVo.setInput("");
					lossVo.setPps("");
					lossVo.setYear(ca.get(Calendar.YEAR)+"");
					lossVo.setWeek11(curWeek-10);
					flag11 = false;
				}
				if(curWeek-11==vo.getWeekNo2()) {
					lossVo.setLoss12(faultLoss);
					lossVo.setOcc12(vo.getOcc()==null?0:vo.getOcc());
					lossVo.setFacilityId(vo.getFacilityId());
					lossVo.setFacilityDesc(vo.getFacilityDesc());
					lossVo.setInput("");
					lossVo.setPps("");
					lossVo.setYear(ca.get(Calendar.YEAR)+"");
					lossVo.setWeek12(vo.getWeekNo2());
					flag12 = false;
				}else if(flag12){
					lossVo.setLoss12(loss);
					lossVo.setOcc12(occ);
					lossVo.setFacilityId(vo.getFacilityId());
					lossVo.setFacilityDesc(vo.getFacilityDesc());
					lossVo.setInput("");
					lossVo.setPps("");
					lossVo.setYear(ca.get(Calendar.YEAR)+"");
					lossVo.setWeek12(curWeek-11);
					flag12 = false;
				}
			}
		}
		return lossVo;
	}
	
}
