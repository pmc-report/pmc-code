package gean.pmc_report_manager.modules.report.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import gean.pmc_report_manager.modules.report.entity.TaBiw39panelEntity;
import gean.pmc_report_manager.modules.report.vo.MsDataVo;
import gean.pmc_report_manager.modules.report.vo.PanelVo;

/**
 * ${comments}
 *
 * @author Jason
 * @email xxxxx@gmail.com
 * @date 2019-04-13 14:15:33
 */
public interface TaBiw39panelService extends IService<TaBiw39panelEntity> {

    String queryTa(Map<String, Object> params);
    
    List<PanelVo> queryEchart(Map<String, Object> params);
    
    List<PanelVo> queryTop10DownTime(Map<String, Object> params);
    
    List<PanelVo> queryTop10Occurrence(Map<String, Object> params);
    
    List<MsDataVo> queryMsList(Map<String, Object> params);
}

