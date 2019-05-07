package gean.pmc_report_manager.modules.report.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import gean.pmc_report_manager.modules.base.service.AtPmcMasterdataConfigService;
import gean.pmc_report_manager.modules.report.dao.TaBiw3OprDao;
import gean.pmc_report_manager.modules.report.entity.TaBiw3OprEntity;
import gean.pmc_report_manager.modules.report.service.TaBiw3OprService;
import gean.pmc_report_manager.modules.report.vo.AreaOprVo;
import gean.pmc_report_manager.modules.report.vo.MasterDataVo;
import gean.pmc_report_manager.modules.report.vo.PageParamVo;
import gean.pmc_report_manager.modules.report.vo.ZoneOprVo;


@Service("taBiw3OprService")
public class TaBiw3OprServiceImpl extends ServiceImpl<TaBiw3OprDao, TaBiw3OprEntity> implements TaBiw3OprService {
	
	@Autowired
	private AtPmcMasterdataConfigService masterDataService;
	

	@Override
	public List<AreaOprVo> queryOprForArea(Map<String, Object> params) {
		// TODO Auto-generated method stub
		/*
		 * 1.根据查询条件将需要计算OPR的zone全部查出来作为基础数据
		 * 2.遍历所有的zone查询zone的OPR数据生成zone OPR 返回
		 * 3.根据返回的zone OPR数据组装 Area OPR 
		 */
		PageParamVo vo = new PageParamVo(params);
		
		List<AreaOprVo> areaList = new ArrayList<>();
		List<ZoneOprVo> zoneOprList = new ArrayList<ZoneOprVo>();
		
		AreaOprVo areaVo = new AreaOprVo();
		
		DecimalFormat df = new DecimalFormat("##0.00");
		
		//1.1
		MasterDataVo areaEol = masterDataService.queryEolArea(params);
		
		//1.2
		List<MasterDataVo> OPRDataList = masterDataService.queryOPRData(params);
		
		//1.3
		Integer shiftPlan = masterDataService.queryShiftPlan(params);
		
		//1.4
		Map<String,Object> areaMap = new HashMap<>();
		if(areaEol!=null) {
			String areaKey = areaEol.getZoneNo()+"_"+areaEol.getFacilityId();
			areaMap.put(areaKey, areaEol);
		}
		
		//2
		float equipmentOpr = 0f,productionOpr = 0f;
		for(MasterDataVo masterVo : OPRDataList) {
			vo.setZone(masterVo.getZoneNo());
			vo.setFacilityId(masterVo.getFacilityId());
			ZoneOprVo zoneOpr = this.generateZoneOpr(vo);
			zoneOpr.setCycleTime(masterVo.getDesignCycleTime());
			
			//设备OPR = （zone产量 * 设计节拍时间）/设备有效生产时间
			equipmentOpr = (zoneOpr.getGoodPartCount() * masterVo.getDesignCycleTime())/zoneOpr.getAvailableTime();
			if(!Float.isNaN(equipmentOpr)&&!Float.isInfinite(equipmentOpr)) {
				zoneOpr.setEquipmentOpr(Float.parseFloat(df.format(equipmentOpr)));
			}else {
				zoneOpr.setEquipmentOpr(0.00f);
			}
			//生产OPR = （zone产量 * 标准节拍时间）/设备有效生产时间
			productionOpr = (zoneOpr.getGoodPartCount() * masterVo.getStandardCycleTime())/zoneOpr.getAvailableTime();
			if(!Float.isNaN(productionOpr)&&!Float.isInfinite(productionOpr)) {
				zoneOpr.setProductionOpr(Float.parseFloat(df.format(productionOpr)));
			}else {
				zoneOpr.setProductionOpr(0.00f);
			}
			zoneOprList.add(zoneOpr);
			
			String zoneKey = masterVo.getZoneNo()+"_"+masterVo.getFacilityId();
			if(areaMap.containsKey(zoneKey)) {
				areaVo.setArea(areaEol.getLineNo());
				areaVo.setActual(zoneOpr.getGoodPartCount());
				areaVo.setShiftPlan(shiftPlan);
				Integer variation = shiftPlan - zoneOpr.getGoodPartCount();
				areaVo.setVariation(variation);
				areaVo.setEquipmentOpr(zoneOpr.getEquipmentOpr());
				areaVo.setProductionOpr(zoneOpr.getProductionOpr());
				areaVo.setZoneList(zoneOprList);
				areaList.add(areaVo);
			}
		}
		return areaList;
	}
	
	//3
	public ZoneOprVo generateZoneOpr(PageParamVo vo){
		
		ZoneOprVo zoneVo = new ZoneOprVo();
		zoneVo.setZone(vo.getZone());
		
		//获取堵料和缺料的持续时间
		ZoneOprVo starvedAndblocked = baseMapper.queryStarvedAndblocked(vo);
		if(starvedAndblocked!=null) {
			zoneVo.setBlocked(starvedAndblocked.getBlocked());
		}else {
			zoneVo.setBlocked(0);
		}
		if(starvedAndblocked!=null) {
			zoneVo.setStarved(starvedAndblocked.getStarved());
		}else {
			zoneVo.setStarved(0);
		}
		//获取总停机时间
		Integer downTime = baseMapper.queryDownTime(vo);
		if(downTime!=null) {
			zoneVo.setDownTime(downTime);
		}else {
			zoneVo.setDownTime(0);
		}
		
		//获取小时合格件产量
		Integer partCount = baseMapper.queryGoodPartCount(vo);
		if(partCount!=null) {
			zoneVo.setGoodPartCount(partCount);
		}else {
			zoneVo.setGoodPartCount(0);
		}
		
		//获取设备可用率
		Float techAvali = baseMapper.queryTechAvali(vo);
		if(techAvali!=null) {
			zoneVo.setEquipAvail(techAvali);
		}else {
			zoneVo.setEquipAvail(0f);
		}
		
		//获取设备正常生产时间
		Float availableTime = baseMapper.queryAvailableTime(vo);
		if(availableTime!=null) {
			zoneVo.setAvailableTime(availableTime);
		}else {
			zoneVo.setAvailableTime(0f);
		}
		return zoneVo;
		
	}
}
