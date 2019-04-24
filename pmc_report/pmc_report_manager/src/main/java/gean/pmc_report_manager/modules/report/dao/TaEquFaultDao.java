package gean.pmc_report_manager.modules.report.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import gean.pmc_report_datasource.annotation.DataSource;
import gean.pmc_report_manager.modules.report.entity.PmcBiwFaultEntity;
import gean.pmc_report_manager.modules.report.vo.PageParamVo;

/**
 * ${comments}
 * 
 * @author Jason
 * @email xxxxx@gmail.com
 * @date 2019-03-30 09:27:53
 */
@Mapper
public interface TaEquFaultDao extends BaseMapper<PmcBiwFaultEntity> {
	
	@DataSource("slave2")
	@Transactional
	List<PmcBiwFaultEntity> qureyFualtList(PageParamVo vo);
	
	public PmcBiwFaultEntity queryTotalMins(Map<String, String> params);

}
