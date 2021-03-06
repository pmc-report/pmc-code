package gean.pmc_report_manager.modules.report.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
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
	
    List<PanelVo> queryEchart(PageParamVo vo);
    
    @Transactional
    @DataSource("slave1")
    PanelVo queryTarTavMtbf(PageParamVo vo);

    List<PanelVo> queryTop10DownTimeOld(PageParamVo vo);
    
    List<PanelVo> queryTotalDurationTimeOld(PageParamVo vo);
    
    List<PanelVo> queryTop10DownTimeNew(PageParamVo vo);
    
    List<PanelVo> queryTotalDurationTimeNew(PageParamVo vo);
    
    List<PanelVo> queryTop10OccurrenceOld(PageParamVo vo);
    
    List<PanelVo> queryTop10OccurrenceNew(PageParamVo vo);
    
    Map<String,Object> queryTaSummary(PageParamVo vo);
    
    Map<String,Object> queryVol(PageParamVo vo);
    
    List<PanelVo> queryTrendChart(PageParamVo vo);
    
    List<TaBiw39panelEntity> queryMsList(PageParamVo vo);
    
    
}
