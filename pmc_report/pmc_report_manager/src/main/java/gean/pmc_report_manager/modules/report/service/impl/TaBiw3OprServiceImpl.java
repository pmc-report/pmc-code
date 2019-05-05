package gean.pmc_report_manager.modules.report.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_common.common.utils.StringUtils;
import gean.pmc_report_manager.common.utils.Query;
import gean.pmc_report_manager.modules.report.dao.TaBiw3OprDao;
import gean.pmc_report_manager.modules.report.entity.TaBiw3OprEntity;
import gean.pmc_report_manager.modules.report.service.TaBiw3OprService;
import gean.pmc_report_manager.modules.report.vo.AreaOprVo;
import gean.pmc_report_manager.modules.report.vo.OprReportVo;
import gean.pmc_report_manager.modules.report.vo.PageParamVo;
import gean.pmc_report_manager.modules.report.vo.ZoneOprVo;


@Service("taBiw3OprService")
public class TaBiw3OprServiceImpl extends ServiceImpl<TaBiw3OprDao, TaBiw3OprEntity> implements TaBiw3OprService {
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TaBiw3OprEntity> page = this.page(
                new Query<TaBiw3OprEntity>().getPage(params),
                new QueryWrapper<TaBiw3OprEntity>()
        );

        return new PageUtils(page);
    }

	@Override
	public List<AreaOprVo> queryOprForArea(Map<String, Object> params) {
		// TODO Auto-generated method stub
		PageParamVo vo = new PageParamVo(params);
		
		List<TaBiw3OprEntity> areaOprList = baseMapper.qureyOPRList(vo);
		
		List<AreaOprVo> aOprList = new ArrayList<>();
		List<ZoneOprVo> zOprList = new ArrayList<>();
		if(!StringUtils.isEmpty(areaOprList)) {
			for(TaBiw3OprEntity en : areaOprList) {
				if("zone".equals(en.getLevels())) {
					ZoneOprVo zvo = new ZoneOprVo();
					zvo.setZone(en.getZone());
					//zvo.setEquipmentOpr(en.getActualEquipmentOpr());
					//zvo.setProductionOpr(en.getActualProductionOpr());
					zOprList.add(zvo);
				}
				AreaOprVo avo = new AreaOprVo();
				avo.setActual(196);
				avo.setArea(en.getLine());
				//avo.setEquipmentOpr(en.getActualEquipmentOpr());
				//avo.setProductionOpr(en.getTargetProductionOpr()/en.getActualProductionOpr());
				//avo.setShiftPlan(en.getTargetProductionOpr());
				avo.setVariation(0);
				avo.setZone(en.getZone());
				avo.setZoneList(zOprList);
				aOprList.add(avo);
			}
			return aOprList;
		}
		return null;
	}

	@Override
	public PageUtils queryOprReport(Map<String, Object> params) {
		/*// TODO Auto-generated method stub
//		System.out.println(params.toString());
		int pageSize = StringUtils.isNotNull(params.get("page")) ? Integer.parseInt(params.get("page").toString()) : null;
		int offset = StringUtils.isNotNull(params.get("limit")) ? Integer.parseInt(params.get("limit").toString()) : null;
//	    int offset = Integer.valueOf(params.get("rowoffset").toString());
		Map<String, String> pmcOprEntity = new HashMap<String, String>();
		for(String str : params.keySet()) {
			pmcOprEntity.put(str, params.get(str) != null ? params.get(str).toString() : null);
		}
		int currPage = (offset/pageSize)+1;
		List<TaBiw3OprEntity> oprList = baseMapper.queryOprByParam(pmcOprEntity);
		if(StringUtils.isNotEmpty(oprList)) {
			
			 * 1.判断页面查询规则
			 * 2.取出开始年月日作为map key，相同日期相加
			 
			Map<String,Object> groupMap = new HashMap<>();
			List<OprReportVo> dayList = new ArrayList<>();
			
			for(TaBiw3OprEntity vo: oprList) {
				try {
					String dayKey = vo.getShop()+"_"+ vo.getWorkingday();
					if(groupMap.containsKey(dayKey)) {
						OprReportVo v1 = dayList.get(0);
						v1.setProductionVolume(vo.getAvailableTime());
						if(StringUtils.isNotNull(vo.getProductionOpr())) {
							Float opr = vo.getProductionOpr();
							Float count = opr+=opr;
							v1.setPopr(count);
						}
						if(vo.getProductionVolume()>0) {
							Integer eopr = vo.getAvailableTime()/vo.getProductionVolume();
							v1.setEopr(Float.parseFloat(eopr.toString()));
						}
						//dayList.add(v1);
					}else {
						OprReportVo v2 = new OprReportVo();
						v2.setOprTarget(vo.getTarOpr().toString());
						v2.setProductionVolume(vo.getAvailableTime());
						if(StringUtils.isNotNull(vo.getProductionOpr())) {
							Float opr = vo.getProductionOpr();
							Float count = opr+=opr;
							v2.setPopr(count);
						}
						if(vo.getProductionVolume()>0) {
							Integer eopr = vo.getAvailableTime()/vo.getProductionVolume();
							v2.setEopr(Float.parseFloat(eopr.toString()));
						}
						v2.setAvailableTime(vo.getAvailableTime());
						v2.setWorkingDay(vo.getWorkingday().toString());
						dayList.add(v2);
						groupMap.put(dayKey, dayList);
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					continue;
				}
			}
			return new PageUtils(dayList, dayList.size(), pageSize, currPage);
		}*/
		
		return null;
	}

	@Override
	public List<TaBiw3OprEntity> queryEcharts(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return null;
	}
}
