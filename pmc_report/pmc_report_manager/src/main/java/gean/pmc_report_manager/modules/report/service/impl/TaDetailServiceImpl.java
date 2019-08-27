package gean.pmc_report_manager.modules.report.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gean.pmc_report_common.common.utils.StringUtils;
import gean.pmc_report_manager.modules.base.service.AtPmcMasterdataConfigService;
import gean.pmc_report_manager.modules.report.service.TaDetailService;
import gean.pmc_report_manager.modules.report.service.TaEquFaultService;
import gean.pmc_report_manager.modules.report.vo.FaultVo;
import gean.pmc_report_manager.modules.report.vo.MasterDataVo;

@Service("taDetailService")
public class TaDetailServiceImpl implements TaDetailService{

	@Autowired
	private AtPmcMasterdataConfigService masterService;
	
	@Autowired
	private TaEquFaultService faultService;
	
	@Override
	public List<FaultVo> queryTAVInfo(Map<String, Object> param) {
		// TODO Auto-generated method stub
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

}
