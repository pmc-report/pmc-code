package gean.pmc_report_manager.modules.report.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import gean.pmc_report_manager.modules.report.entity.TaBiw3OprEntity;
import gean.pmc_report_manager.modules.report.vo.PageParamVo;
import gean.pmc_report_manager.modules.report.vo.ZoneOprVo;

/**
 * ${comments}
 * 
 * @author ''
 * @email xxxxx@gmail.com
 * @date 2019-04-10 13:54:04
 */
@Mapper
public interface TaBiw3OprDao extends BaseMapper<TaBiw3OprEntity> {
	
	List<TaBiw3OprEntity> qureyOPRList(PageParamVo vo);
	
	List<ZoneOprVo> queryStarvedAndblocked(PageParamVo vo);
	
	List<ZoneOprVo> queryDownTime(PageParamVo vo);
	
	List<ZoneOprVo> queryGoodPartCount(PageParamVo vo);
	
	List<ZoneOprVo> queryTechAvali(PageParamVo vo);
	
	List<ZoneOprVo> queryZoneOpr(PageParamVo vo);
}
