package gean.pmc_report_manager.modules.report.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import gean.pmc_report_manager.modules.report.entity.TaEquFaultEntity;
import gean.pmc_report_manager.modules.report.vo.FaultOrderVo;
import gean.pmc_report_manager.modules.report.vo.FaultVo;
import gean.pmc_report_manager.modules.report.vo.LossOPRVo;
import gean.pmc_report_manager.modules.report.vo.PageParamVo;

/**
 * ${comments}
 * 
 * @author Jason
 * @email xxxxx@gmail.com
 * @date 2019-03-30 09:27:53
 */
@Mapper
public interface TaEquFaultDao extends BaseMapper<TaEquFaultEntity> {
	
	List<FaultVo> qureyFualtList(PageParamVo vo);
	
	TaEquFaultEntity queryTotalMins(PageParamVo vo);
	
	String queryFacilityDesc(Integer facilityId);
	
	List<LossOPRVo> queryTop10Fault(PageParamVo vo);
	
	List<LossOPRVo> queryFaultLoss(PageParamVo vo);
	
	List<FaultOrderVo> queryCurrShiftFaults(PageParamVo vo);
	
	List<FaultOrderVo> queryPreShiftFaults(PageParamVo vo);
	
	List<FaultVo> queryFaultsList(PageParamVo vo);

}
