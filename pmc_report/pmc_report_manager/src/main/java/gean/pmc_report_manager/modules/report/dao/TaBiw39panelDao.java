package gean.pmc_report_manager.modules.report.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import gean.pmc_report_datasource.annotation.DataSource;
import gean.pmc_report_manager.modules.report.entity.TaBiw39panelEntity;
import gean.pmc_report_manager.modules.report.vo.PageParamVo;
import gean.pmc_report_manager.modules.report.vo.PanelVo;

/**
 * ${comments}
 * 
 * @author Jason
 * @email xxxxx@gmail.com
 * @date 2019-04-13 14:15:33
 */
@Mapper
public interface TaBiw39panelDao extends BaseMapper<TaBiw39panelEntity> {
	
	//@DataSource("slave3")
	//@Transactional
    List<PanelVo> queryEchart(PageParamVo vo);
    
	//@DataSource("slave3")
	//@Transactional
    List<PanelVo> queryTop10DownTimeOld(PageParamVo vo);
    
	///@DataSource("slave3")
	//@Transactional
    List<PanelVo> queryTotalDurationTimeOld(PageParamVo vo);
    
	//@DataSource("slave3")
	//@Transactional
    List<PanelVo> queryTop10DownTimeNew(PageParamVo vo);
    
	//@DataSource("slave3")
	//@Transactional
    List<PanelVo> queryTotalDurationTimeNew(PageParamVo vo);
}
