package gean.pmc_report_manager.modules.report.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_common.common.utils.StringUtils;
import gean.pmc_report_manager.modules.report.dao.PmcOprDao;
import gean.pmc_report_manager.modules.report.entity.PmcOprEntity;
import gean.pmc_report_manager.modules.report.service.PmcOprService;
import gean.pmc_report_manager.modules.report.vo.OprReportVo;

@Service("pmcOprService")
public class PmcOprServiceImpl extends ServiceImpl<PmcOprDao, PmcOprEntity> implements PmcOprService{

	@Override
	public List<PmcOprEntity> queryEcharts(Map<String ,Object>params){ 
		Map<String, String> pmcOprEntity = new HashMap<String, String>();
		for(String str : params.keySet()) {
			pmcOprEntity.put(str, params.get(str) != null ? params.get(str).toString() : null);
		}
		return baseMapper.queryEcharts(pmcOprEntity);
	}
	
	@Override
	public PageUtils queryOprReport(Map<String, Object> params) {
		// TODO Auto-generated method stub
//		System.out.println(params.toString());
		int pageSize = StringUtils.isNotNull(params.get("page")) ? Integer.parseInt(params.get("page").toString()) : null;
		int offset = StringUtils.isNotNull(params.get("limit")) ? Integer.parseInt(params.get("limit").toString()) : null;
//	    int offset = Integer.valueOf(params.get("rowoffset").toString());
		Map<String, String> pmcOprEntity = new HashMap<String, String>();
		for(String str : params.keySet()) {
			pmcOprEntity.put(str, params.get(str) != null ? params.get(str).toString() : null);
		}
		int currPage = (offset/pageSize)+1;
		List<PmcOprEntity> oprList = baseMapper.queryOprByParam(pmcOprEntity);
		if(StringUtils.isNotEmpty(oprList)) {
			/*
			 * 1.判断页面查询规则
			 * 2.取出开始年月日作为map key，相同日期相加
			 */
			Map<String,Object> groupMap = new HashMap<>();
			List<OprReportVo> dayList = new ArrayList<>();
			
			for(PmcOprEntity vo: oprList) {
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
		}
		
		return null;
	}
	
	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		return null;
	}

}
