package gean.pmc_report_manager.modules.report.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import gean.pmc_report_common.common.utils.DateUtils;
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
	
		/*
		 * 1.遍历list 周做key 对象做value 存map
		 * 2.遍历map
		 */
		PageParamVo paramVo = new PageParamVo(params);
		Map<String,Object> groupMap = new LinkedHashMap<>();
		List<LossOPRVo> allFaultList = baseMapper.queryFaultLoss(paramVo);
		List<LossOPRVo> lossList = new ArrayList<>();
		DecimalFormat df = new DecimalFormat("##0.00");
		Calendar ca = Calendar.getInstance();
		if(allFaultList.size()>0) {
			
			LossOPRVo lossVo = null;
			/*
			 * 组装数据-遍历所有的数据，按照weekNo、设备ID划分
			 */
			int curWeek = DateUtils.weekOfYear();
			float loss = 0.00f;
			int occ = 0;
			for(LossOPRVo vo : allFaultList) {
				for(int i=curWeek;i>curWeek-12;i--) {
					System.out.println(i);
					lossVo = new LossOPRVo();
					if(i>vo.getWeekNo2()) {
						lossVo.setLoss1(loss);
						lossVo.setOcc1(occ);
						lossVo.setWeek1(i);
						lossVo.setFacilityDesc(vo.getFacilityDesc());
						lossVo.setFacilityId(vo.getFacilityId());
						lossVo.setPps("");
						lossVo.setInput("");
						lossVo.setYear(ca.get(Calendar.YEAR)+"");
					}
					
					if(vo.getRn()==1) {
						lossVo.setLoss1(loss);
						lossVo.setOcc1(occ);
						lossVo.setWeek1(i);
						lossVo.setFacilityDesc(vo.getFacilityDesc());
						lossVo.setFacilityId(vo.getFacilityId());
						lossVo.setPps("");
						lossVo.setInput("");
						lossVo.setYear(ca.get(Calendar.YEAR)+"");
					}else if(vo.getRn()==1&&i==vo.getWeekNo2()){
						lossVo.setLoss1(vo.getLoss1()==null?loss:vo.getLoss1());
						lossVo.setOcc1(vo.getOcc1()==null?occ:vo.getOcc1());
						lossVo.setWeek1(i);
						lossVo.setFacilityDesc(vo.getFacilityDesc());
						lossVo.setFacilityId(vo.getFacilityId());
						lossVo.setPps("");
						lossVo.setInput("");
						lossVo.setYear(ca.get(Calendar.YEAR)+"");
					}
					if(vo.getRn()==2&&i>vo.getWeekNo2()) {
						System.out.println(i+"-----------"+vo.getWeekNo2());
						lossVo.setLoss2(vo.getLoss2()==null?loss:vo.getLoss2());
						lossVo.setOcc2(vo.getOcc2()==null?occ:vo.getOcc2());
						lossVo.setWeek2(i);
					}
				}
				/*if(loss.getRn()==1) {
					lossVo = new LossOPRVo();
					lossVo.setFacilityDesc(loss.getFacilityDesc());
					lossVo.setFacilityId(loss.getFacilityId());
					lossVo.setPps("");
					lossVo.setInput("");
					lossVo.setYear(ca.get(Calendar.YEAR)+"");
					lossVo.setWeek1(loss.getWeekNo2());
					lossVo.setWeek2(loss.getWeekNo2()-1);
					lossVo.setWeek3(loss.getWeekNo2()-2);
					lossVo.setWeek4(loss.getWeekNo2()-3);
					lossVo.setWeek5(loss.getWeekNo2()-4);
					lossVo.setWeek6(loss.getWeekNo2()-5);
					lossVo.setWeek7(loss.getWeekNo2()-6);
					lossVo.setWeek8(loss.getWeekNo2()-7);
					lossVo.setWeek9(loss.getWeekNo2()-8);
					lossVo.setWeek10(loss.getWeekNo2()-9);
					lossVo.setWeek11(loss.getWeekNo2()-10);
					lossVo.setWeek12(loss.getWeekNo2()-11);
					lossVo.setLoss1(Float.parseFloat(df.format((float)loss.getLoss()/60)));
					lossVo.setOcc1(loss.getOcc());
					lossList.add(lossVo);
				}else {
					groupMap.put(loss.getFacilityId()+"-"+loss.getRn(),loss.getLoss()+"-"+loss.getOcc());
				}*/
			}
		}
		generateLossList(lossList,groupMap);
		
		return lossList;
	}

	private void generateLossList(List<LossOPRVo> lossList,Map<String,Object> groupMap){
		
		DecimalFormat df = new DecimalFormat("##0.00");
		for(LossOPRVo vo : lossList) {
			for(int i=2;i<12;i++) {
				String key = vo.getFacilityId()+"-"+i;
				String value = (String)groupMap.get(key);
				if(value!=null) {
					String[] valArr = value.split("-");
					int lo = Integer.parseInt(valArr[0]);
					Float loss = Float.parseFloat(df.format((float)lo/60));
					int occ = Integer.parseInt(valArr[1]);
					if(i==2) {
						vo.setLoss2(loss);
						vo.setOcc2(occ);
					}
					if(i==3) {
						vo.setLoss3(loss);
						vo.setOcc3(occ);
					}
					if(i==4) {
						vo.setLoss4(loss);
						vo.setOcc4(occ);
					}
					if(i==5) {
						vo.setLoss5(loss);
						vo.setOcc5(occ);
					}
					if(i==6) {
						vo.setLoss6(loss);
						vo.setOcc6(occ);
					}
					if(i==7) {
						vo.setLoss7(loss);
						vo.setOcc7(occ);
					}
					if(i==8) {
						vo.setLoss8(loss);
						vo.setOcc8(occ);
					}
					if(i==9) {
						vo.setLoss9(loss);
						vo.setOcc9(occ);
					}
					if(i==10) {
						vo.setLoss10(loss);
						vo.setOcc10(occ);
					}
					if(i==11) {
						vo.setLoss11(loss);
						vo.setOcc11(occ);
					}
					if(i==12) {
						vo.setLoss12(loss);
						vo.setOcc12(occ);
					}
				}
			}
		}
	}
}
