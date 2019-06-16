package gean.pmc_report_manager.modules.report.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import gean.pmc_report_common.common.utils.DateUtils;
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
		/*
		 * 1.根据查询条件将需要计算OPR的zone全部查出来作为基础数据
		 * 2.遍历所有的zone查询zone的OPR数据生成zone OPR 返回
		 * 3.根据返回的zone OPR数据组装 Area OPR 
		 */
		//查询参数统一处理
		PageParamVo vo = new PageParamVo(params);
		String current = DateUtils.format(new Date(), DateUtils.DATE_PATTERN);
		
		//返回结果集
		List<AreaOprVo> areaList = new ArrayList<>();
		List<ZoneOprVo> zoneOprList = new ArrayList<ZoneOprVo>();
		
		//返回结果
		AreaOprVo areaVo = new AreaOprVo();
		
		//1.1获取标志位的zone计算OPR
		List<MasterDataVo> OPRDataList = masterDataService.queryOPRData(params);
		
		//1.2获取标识位的zone代表整个area OPR
		MasterDataVo areaEol = masterDataService.queryEolArea(params);
		
		//1.3获取area班次计划产量
		Integer shiftPlan = masterDataService.queryShiftPlan(params);
		
		//1.4匹配标志位的zone
		Map<String,Object> areaMap = new HashMap<>();
		if(areaEol!=null) {
			String areaKey = areaEol.getZoneNo()+"_"+areaEol.getFacilityId();
			areaMap.put(areaKey, areaEol);
		}
		
		//2遍历根据每个需要计算OPR的zone编号和设备ID去查对应的OPR数据
		if (OPRDataList != null && !OPRDataList.isEmpty()) {
			List<String> zoneList = new ArrayList<String>();
			List<Integer> facilityIdList = new ArrayList<Integer>();

			for (MasterDataVo masterVos : OPRDataList) {
				zoneList.add(masterVos.getZoneNo());
				facilityIdList.add(masterVos.getFacilityId());
			}

			params.put("zoneList", zoneList);
			params.put("facilityIdList", facilityIdList);
			PageParamVo paramsVo = new PageParamVo(params);
			String shift = params.get("shift") == null ? "ALL" : (String)params.get("shift");
			paramsVo.setShift(shift);
			String workDay = (String)params.get("sTime")==null? current : (String)params.get("sTime");
			paramsVo.setWorkDay(workDay);
			List<ZoneOprVo> zoneOprVoList = generateZoneOprNew(paramsVo, OPRDataList);
			
			if(!areaMap.isEmpty()) {
				for(ZoneOprVo zoneOpr : zoneOprVoList) {
					zoneOprList.add(zoneOpr);
					String zoneKey = zoneOpr.getZone() + "_" + zoneOpr.getFacilityId();
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
			} else {
				areaVo.setArea(vo.getArea());
				areaVo.setActual(0);
				areaVo.setShiftPlan(shiftPlan);
				Integer variation = shiftPlan - 0;
				areaVo.setVariation(variation);
				areaVo.setEquipmentOpr(0.0f);
				areaVo.setProductionOpr(0.0f);
				areaVo.setZoneList(zoneOprVoList);
				areaList.add(areaVo);
			}
		}
		/*for(MasterDataVo masterVo : OPRDataList) {
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
			//根据标志位生成area OPR
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
		}*/
		return areaList;
	}
	
	private List<ZoneOprVo> generateZoneOprNew(PageParamVo paramsVo, List<MasterDataVo> oPRDataList) {
		
		List<ZoneOprVo> result = new ArrayList<ZoneOprVo>();
		
		//小数格式化
		DecimalFormat df = new DecimalFormat("##0.00");
		
		List<ZoneOprVo> oprStates = baseMapper.queryStarvedAndblocked(paramsVo);
		//List<ZoneOprVo> oprDownTime = baseMapper.queryDownTime(paramsVo);
		//List<ZoneOprVo> oprGoodPartCount = baseMapper.queryGoodPartCount(paramsVo);
		List<ZoneOprVo> oprZone = baseMapper.queryZoneOpr(paramsVo);
		String workingDay = paramsVo.getWorkDay().replace("-", "");
		paramsVo.setWorkDay(workingDay==null?"":workingDay);
		List<ZoneOprVo> oprEquipAvail = baseMapper.queryTechAvali(paramsVo);
		
		for(int i = 0 ; i< oPRDataList.size();i++) {
			ZoneOprVo oprVo = new ZoneOprVo();
			oprVo.setZone(oPRDataList.get(i).getZoneNo());
			oprVo.setFacilityId(oPRDataList.get(i).getFacilityId());
			oprVo.setCycleTime(oPRDataList.get(i).getDesignCycleTime());
			
			if(oprStates!=null && !oprStates.isEmpty()) {
				for (ZoneOprVo oprVoStates : oprStates) {
					if(oprVo.getZone().equals(oprVoStates.getZone()) && oprVo.getFacilityId().equals(oprVoStates.getFacilityId())) {
						if(oprVoStates.getStarved() != null) {
							oprVo.setStarved(oprVoStates.getStarved());
						}else{
							oprVo.setStarved(0);
						}
						if(oprVoStates.getBlocked() != null) {
							oprVo.setBlocked(oprVoStates.getBlocked());
						}else {
							oprVo.setBlocked(0);
						}
						break;
					}else {
						oprVo.setStarved(0);
						oprVo.setBlocked(0);
					}
				}
			}else {
				oprVo.setStarved(0);
				oprVo.setBlocked(0);
			}
		
			if(oprZone != null && !oprZone.isEmpty()) {
				for (ZoneOprVo oprVoDownTime : oprZone) {
					if(oprVo.getZone().equals(oprVoDownTime.getZone()) && oprVo.getFacilityId().equals(oprVoDownTime.getFacilityId())) {
						if(oprVoDownTime.getDownTime() != null) {
							oprVo.setDownTime(oprVoDownTime.getDownTime());
						}else{
							oprVo.setDownTime(0);
						}
						break;
					}else {
						oprVo.setDownTime(0);
					}
				}
			}else {
				oprVo.setDownTime(0);
			}
		
			if(oprEquipAvail != null && !oprEquipAvail.isEmpty()) {
				for (ZoneOprVo oprVoEquipAvail : oprEquipAvail) {
					if(oprVo.getZone().equals(oprVoEquipAvail.getZone())) {
						String equipAvail = df.format(oprVoEquipAvail.getEquipAvail());
						if(equipAvail != null) {
							oprVo.setEquipAvail(Float.parseFloat(equipAvail)*100);
						}else{
							oprVo.setEquipAvail(0f);
						}
						break;
					}else {
						oprVo.setEquipAvail(0f);
					}
				}
			}else {
				oprVo.setEquipAvail(0f);
			}
		
			if(oprZone !=null && !oprZone.isEmpty()) {
				for (ZoneOprVo oprVoGoodPartCount : oprZone) {
					if(oprVo.getZone().equals(oprVoGoodPartCount.getZone()) && oprVo.getFacilityId().equals(oprVoGoodPartCount.getFacilityId())) {
						if(oprVoGoodPartCount.getProductionVolume() != null) {
							oprVo.setGoodPartCount(oprVoGoodPartCount.getProductionVolume());
						}else {
							oprVo.setGoodPartCount(0);
						}
						break;
					}else {
						oprVo.setGoodPartCount(0);
					}
				}
			}else {
				oprVo.setGoodPartCount(0);
			}
			if(oprZone != null && !oprZone.isEmpty()) {
				for (ZoneOprVo oprAvaTime : oprZone) {
					if(oprVo.getZone().equals(oprAvaTime.getZone())) {

						// 设备OPR = （zone产量 * 设计节拍时间）/设备有效生产时间
						Float equipmentOpr = oprAvaTime.getEquipmentOpr();
						if(equipmentOpr != null 
								&& !Float.isNaN(equipmentOpr) 
								&& !Float.isInfinite(equipmentOpr)) {
							oprVo.setEquipmentOpr(Float.parseFloat(df.format(equipmentOpr))*100);
						}else {
							oprVo.setEquipmentOpr(0.00f);
						}
						
						// 生产OPR = （zone产量 * 标准节拍时间）/设备有效生产时间
						Float productionOpr = oprAvaTime.getProductionOpr();
						if (productionOpr != null 
								&& !Float.isNaN(productionOpr) 
								&& !Float.isInfinite(productionOpr)) {
							oprVo.setProductionOpr(Float.parseFloat(df.format(productionOpr))*100);
						} else {
							oprVo.setProductionOpr(0.00f);
						}
						
						break;
					}
				}
			}
			
			result.add(oprVo);
		}
		return result;
	}

	//3
	/*public ZoneOprVo generateZoneOpr(PageParamVo vo){
		
		ZoneOprVo zoneVo = new ZoneOprVo();
		zoneVo.setZone(vo.getZone());
		
		//获取堵料和缺料的持续时间
		ZoneOprVo starvedAndblocked = baseMapper.queryStarvedAndblocked(vo);
		if(starvedAndblocked!= null) {
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
		
	}*/
}
