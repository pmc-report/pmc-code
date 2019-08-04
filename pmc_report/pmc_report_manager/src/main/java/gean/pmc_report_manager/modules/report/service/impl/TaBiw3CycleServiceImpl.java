package gean.pmc_report_manager.modules.report.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_common.common.utils.StringUtils;
import gean.pmc_report_manager.modules.report.dao.TaBiw3CycleDao;
import gean.pmc_report_manager.modules.report.entity.TaBiw3CycleEntity;
import gean.pmc_report_manager.modules.report.service.TaBiw3CycleService;
import gean.pmc_report_manager.modules.report.vo.CycleVo;
import gean.pmc_report_manager.modules.report.vo.PageParamVo;

@Service("taBiw3CycleService")
public class TaBiw3CycleServiceImpl extends ServiceImpl<TaBiw3CycleDao, TaBiw3CycleEntity> implements TaBiw3CycleService{

	@Override
	public PageUtils queryBiw3CycleByParam(Map<String, Object> params) { 
		
		
		
		PageParamVo vo = new PageParamVo(params);
		String str = vo.getEquipment();
		str = str.replaceAll("&amp;", "&");
		vo.setEquipment(str);
		//分页计算
		int pageSize = Integer.parseInt(vo.getLimit());
		int pageNo = Integer.parseInt(vo.getPage());
		Integer startIndex = (pageNo-1)*pageSize+1;
		Integer endIndex = pageNo*pageSize;
		vo.setLimit(endIndex.toString());
		vo.setPage(startIndex.toString());
		
		DecimalFormat df = new DecimalFormat("##0.00");
		List<Integer> countList = baseMapper.qureyTotalList(vo);
		List<CycleVo> cycleList = baseMapper.qureyCycleList(vo);
		List<CycleVo> resultList = new ArrayList<>();
		if(!StringUtils.isEmpty(cycleList)&&!StringUtils.isEmpty(cycleList)) {
			for (CycleVo cycleVo : cycleList) {
				CycleVo cycle = new CycleVo();
					cycle.setStation(cycleVo.getStation());
					cycle.setFacilityId(cycleVo.getFacilityId());
					cycle.setFacilityDesc(cycleVo.getFacilityDesc());
					cycle.setJobId(cycleVo.getJobId());
					cycle.setStdCycleTime(cycleVo.getStdCycleTime());
					cycle.setDesignCycleTime(cycleVo.getDesignCycleTime());
					cycle.setMinCycleTime(cycleVo.getMinCycleTime());
					cycle.setMaxCycleTime(cycleVo.getMaxCycleTime());
					cycle.setAvgCycleTime(cycleVo.getAvgCycleTime());
					float effeCycleTime = (float)cycleVo.getTotalCycleTime()/(cycleVo.getGoodCycleTime()+cycleVo.getBadCycleTime());
					cycle.setEffeciveCycleTime(Float.parseFloat(df.format(effeCycleTime)));
					cycle.setCycleOffset(Float.parseFloat(df.format(effeCycleTime-cycleVo.getDesignCycleTime())));
					resultList.add(cycle);
			}
		
			return new PageUtils(resultList, countList.size(),startIndex,endIndex);
		}
		
		return new PageUtils(resultList,resultList.size(),startIndex,endIndex);
		
	}

	@Override
	public List<CycleVo> queryEchart(Map<String, Object> params) {
		
		String type = (String)params.get("exportType");
		PageParamVo vo = new PageParamVo(params);
		String str = vo.getEquipment();
		str = str.replaceAll("&amp;", "&");
		vo.setEquipment(str);
		List<CycleVo> cycleList = baseMapper.qureyEchart(vo);
		//如果是导出直接返回集合
		if(StringUtils.isNotBlank(type)) {
			return cycleList;
		}
		//图表数据
		List<CycleVo> resultList = new ArrayList<>();
		if(!StringUtils.isEmpty(cycleList)) {
			for (CycleVo cycleVo : cycleList) {
				CycleVo cycle = new CycleVo();
				cycle.setFacilityId(cycleVo.getFacilityId());
				cycle.setMinCycleTime(cycleVo.getMinCycleTime());
				cycle.setMaxCycleTime(cycleVo.getMaxCycleTime());
				cycle.setAvgCycleTime(cycleVo.getAvgCycleTime());
				cycle.setDesignCycleTime(cycleVo.getDesignCycleTime());
				resultList.add(cycle);
			}
		}
		return resultList;
	}

}
