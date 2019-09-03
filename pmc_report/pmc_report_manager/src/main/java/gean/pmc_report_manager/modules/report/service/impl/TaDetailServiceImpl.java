package gean.pmc_report_manager.modules.report.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gean.pmc_report_common.common.utils.StringUtils;
import gean.pmc_report_manager.modules.base.service.AtPmcMasterdataConfigService;
import gean.pmc_report_manager.modules.report.service.TaBiw39panelService;
import gean.pmc_report_manager.modules.report.service.TaDetailService;
import gean.pmc_report_manager.modules.report.service.TaEquFaultService;
import gean.pmc_report_manager.modules.report.vo.FaultVo;
import gean.pmc_report_manager.modules.report.vo.MasterDataVo;
import gean.pmc_report_manager.modules.report.vo.MsDataVo;
import gean.pmc_report_manager.modules.report.vo.TaDetailVo;

@Service("taDetailService")
public class TaDetailServiceImpl implements TaDetailService{

	@Autowired
	private AtPmcMasterdataConfigService masterService;
	
	@Autowired
	private TaEquFaultService faultService;
	
	@Autowired
	private TaBiw39panelService taBiw39panelService; 
	
	@Override
	public Map<String,List<TaDetailVo>> queryTAVInfo(Map<String, Object> param) {
		
		Map<String,List<TaDetailVo>> groupMap = new HashMap<>();
		
		List<TaDetailVo> taDetailList = new ArrayList<>();
		
		List<String> mss = masterService.queryMS(param);
		
		List<MsDataVo> msList = taBiw39panelService.queryMsList(param);
		
		List<FaultVo> faultList = this.findFaults(param);
		
		for(String key : mss) {
			TaDetailVo taVo = new TaDetailVo();
			List<FaultVo> faults = new ArrayList<>();
			List<MsDataVo> ms = new ArrayList<>();
			for(MsDataVo msVo : msList) {
				if(key.equals(msVo.getEquipment())) {
					ms.add(msVo);
				}
			}
			for(FaultVo fVo: faultList) {
				if(key.equals(fVo.getStation())) {
					faults.add(fVo);
				}
			}
			taVo.setEquipment(key);
			taVo.setFaultList(faults);
			taVo.setMsList(ms);
			taDetailList.add(taVo);
		}
		
		if(!StringUtils.isEmpty(taDetailList)) {
			for(TaDetailVo msVo : taDetailList) {
				String equ = msVo.getEquipment();
				if(StringUtils.isNotBlank(equ)) {
					List<TaDetailVo> tempList = groupMap.get(equ);
					if(!StringUtils.isEmpty(tempList)) {
						tempList.add(msVo);
					}else {
						tempList = new ArrayList<>();
						tempList.add(msVo);
						groupMap.put(equ, tempList);
					}
				}
			}
		}
		if(!StringUtils.isEmpty(mss)) {
			for(String key : mss) {
				if(StringUtils.isNotBlank(key)) {
					if(!groupMap.containsKey(key)) {
						List<TaDetailVo> ms = new ArrayList<>();
						TaDetailVo vo = new TaDetailVo();
						vo.setEquipment(key);
						ms.add(vo);
						groupMap.put(key, ms);
					}
				}
			}
		}
		
		Map<String, List<TaDetailVo>> sortMap = new TreeMap<String, List<TaDetailVo>>(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				// TODO Auto-generated method stub
				return o1.compareTo(o2);
			}
		});
		sortMap.putAll(groupMap);

		return sortMap;
	}
	
	public List<FaultVo> findFaults(Map<String, Object> param){
		/*
		 * 1.查询主数据中的TAV Mapping
		 * 2.查询故障数据，按照TAV 分组
		 * 3.组装数据返回
		 */
		//result
		List<FaultVo> resultList = new ArrayList<>();
		//分组集合
		List<String> tavList = new ArrayList<>();
		//TAV mapping
		List<MasterDataVo> masterDataList = masterService.queryTAV(param);
		//故障数据集
		List<FaultVo> faultList = faultService.queryFaultsList(param);
		if(!StringUtils.isEmpty(masterDataList)){
			//遍历组装数据
			for(MasterDataVo vo : masterDataList) {
				String msKey = vo.getEquipmentNo()+","+vo.getFacilityId();
				tavList.add(msKey);
			}
		}
		
		//组装数据
		if(!StringUtils.isEmpty(faultList)) {
			for(int i=0;i<tavList.size();i++) {
				String msNo = tavList.get(i);
				String[] msInfo = msNo.split(",");
				String equipment = msInfo[0];
				Integer facilityId = Integer.parseInt(msInfo[1]);
				for(FaultVo vo : faultList) {
					if(facilityId.equals(vo.getFacilityId())) {
						vo.setStation(equipment);
						resultList.add(vo);
					}
				}
			}
		}
		
		return resultList;
	}
	
	
	@Override
	public Map<String,List<MsDataVo>> queryTaDetailList(Map<String, Object> params) {
	
		Map<String,List<MsDataVo>> groupMap = new HashMap<>();
		
		List<String> mss = masterService.queryMS(params);
		
		List<MsDataVo> list = taBiw39panelService.queryMsList(params);
		
		if(!StringUtils.isEmpty(list)) {
			for(MsDataVo msVo : list) {
				String equ = msVo.getEquipment();
				if(StringUtils.isNotBlank(equ)) {
					if(mss.contains(equ)) {
						List<MsDataVo> tempList = groupMap.get(equ);
						if(!StringUtils.isEmpty(tempList)) {
							tempList.add(msVo);
						}else {
							tempList = new ArrayList<>();
							tempList.add(msVo);
							groupMap.put(equ, tempList);
						}
					}
				}
			}
		}
		if(!StringUtils.isEmpty(mss)) {
			for(String key : mss) {
				if(StringUtils.isNotBlank(key)) {
					if(!groupMap.containsKey(key)) {
						List<MsDataVo> ms = new ArrayList<>();
						MsDataVo vo = new MsDataVo();
						vo.setEquipment(key);
						ms.add(vo);
						groupMap.put(key, ms);
					}
				}
			}
		}
		
		Map<String, List<MsDataVo>> sortMap = new TreeMap<String, List<MsDataVo>>(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				// TODO Auto-generated method stub
				return o1.compareTo(o2);
			}
		});
		sortMap.putAll(groupMap);

		
		return sortMap;
	}

}
