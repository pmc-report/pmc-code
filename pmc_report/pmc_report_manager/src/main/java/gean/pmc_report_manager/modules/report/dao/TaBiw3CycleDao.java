package gean.pmc_report_manager.modules.report.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import gean.pmc_report_manager.modules.report.entity.TaBiw3CycleEntity;
import gean.pmc_report_manager.modules.report.vo.CycleVo;
import gean.pmc_report_manager.modules.report.vo.PageParamVo;

@Mapper
public interface TaBiw3CycleDao extends BaseMapper<TaBiw3CycleEntity>{
	
	List<CycleVo> qureyCycleList(PageParamVo vo);
	
	List<CycleVo> qureyEchart(PageParamVo vo);

	List<Integer> qureyTotalList(PageParamVo vo);

}
