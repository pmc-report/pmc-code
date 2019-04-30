package gean.pmc_report_manager.modules.report.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_manager.modules.report.entity.PmcOprEntity;

public interface PmcOprService extends IService<PmcOprEntity>{
	
	PageUtils queryPage(Map<String, Object> params);
	
    PageUtils queryOprReport(Map<String, Object> params);
	
    List<PmcOprEntity> queryEcharts(Map<String, Object> params);
}
