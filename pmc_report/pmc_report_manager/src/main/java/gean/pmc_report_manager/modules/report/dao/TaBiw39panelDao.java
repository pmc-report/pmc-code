package gean.pmc_report_manager.modules.report.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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
    
    List<PanelVo> queryTop10DownTime(PageParamVo vo);
}
