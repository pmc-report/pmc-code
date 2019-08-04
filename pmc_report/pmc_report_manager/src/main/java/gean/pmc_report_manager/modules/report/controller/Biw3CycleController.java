package gean.pmc_report_manager.modules.report.controller;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_common.common.utils.R;
import gean.pmc_report_common.common.utils.StringUtils;
import gean.pmc_report_manager.common.utils.JasperExportUtils;
import gean.pmc_report_manager.modules.report.service.TaBiw3CycleService;
import gean.pmc_report_manager.modules.report.vo.CycleVo;
import gean.pmc_report_manager.modules.report.vo.CycleVoExport;

@RestController
@RequestMapping("report/cycle")
public class Biw3CycleController {
	
	
	@Autowired
	private TaBiw3CycleService biw3CycleService;
	
	/**
	 * 获取列表
	 * @param CycleVo 
	 * @param List 
	 */
	@RequestMapping("/list")
    @RequiresPermissions("report:cycle:list")
    public R list(@RequestParam Map<String, Object> params){
    	 PageUtils page = biw3CycleService.queryBiw3CycleByParam(params);
    	return R.ok().put("page", page);
	}

	/**
	 * 获取Echart图
	 */
	@RequestMapping("/echarts")
    public R list2(@RequestParam Map<String, Object> params){
		  List<CycleVo> list = biw3CycleService.queryEchart(params);
	      return R.ok().put("list", list);
	    }

	/**
	 * 导出表数据
	 * @param request
	 * @param response
	 * @param params
	 */
	@RequestMapping("/exportCycle")
    public void exportCycle(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String,Object> params) {    	

		//复用查询
		List<CycleVo> list = biw3CycleService.queryEchart(params);
		//导出集合
    	List<CycleVoExport> exportList = new ArrayList<CycleVoExport>();
    	if (list!= null && !list.isEmpty()) {
    		for (CycleVo cycleVo : list) {
    			CycleVoExport exportCycle = new CycleVoExport();
    			exportCycle.setShop(!StringUtils.isNotBlank((String)params.get("shop")) ? "" : (String)params.get("shop"));
    			exportCycle.setArea(!StringUtils.isNotBlank((String)params.get("area")) ? "ALL" : (String)params.get("area"));
    			exportCycle.setZone(!StringUtils.isNotBlank((String)params.get("zone")) ? "ALL" : (String)params.get("zone"));
    			exportCycle.setStation(!StringUtils.isNotBlank((String)params.get("station")) ? "ALL" : (String)params.get("station"));
    			exportCycle.setJobId(!StringUtils.isNotBlank((String)params.get("jobId")) ? "ALL" : (String)params.get("jobId"));
    			exportCycle.setEquipment(!StringUtils.isNotBlank((String)params.get("equipment")) ? "ALL" : (String)params.get("equipment"));
    			exportCycle.setShift(!StringUtils.isNotBlank((String)params.get("shift")) ? "ALL" : (String)params.get("shift"));
        		Object objstart = params.get("sTime");
        		if(objstart != null && !"".equals(objstart)) {
        			exportCycle.setStartTime((String)params.get("sTime") + " 00:00:00");
        		}else {
        			exportCycle.setStartTime("");
        		}
        	
        		Object objend = params.get("eTime");
        		if(objend != null && !"".equals(objend)) {
        			exportCycle.setEndTime((String)params.get("eTime") + " 23:59:59");
        		}else {
        			exportCycle.setEndTime("");
        		}
        		DecimalFormat df = new DecimalFormat("##0.00");
        		exportCycle.set_station(cycleVo.getStation());
        		exportCycle.set_facilityId(cycleVo.getFacilityId());
        		exportCycle.setFacilityDesc(cycleVo.getFacilityDesc());
        		exportCycle.set_jobId(cycleVo.getJobId());
        		exportCycle.setStdCycleTime(cycleVo.getStdCycleTime());
				exportCycle.setDesignCycleTime(cycleVo.getDesignCycleTime());
				exportCycle.setMinCycleTime(cycleVo.getMinCycleTime());
				exportCycle.setMaxCycleTime(cycleVo.getMaxCycleTime());
				exportCycle.setAvgCycleTime(cycleVo.getAvgCycleTime());
				float effeCycleTime = (float)cycleVo.getTotalCycleTime()/(cycleVo.getGoodCycleTime()+cycleVo.getBadCycleTime());
				exportCycle.setEffeciveCycleTime(Float.parseFloat(df.format(effeCycleTime)));
				exportCycle.setCycleOffset(Float.parseFloat(df.format(effeCycleTime-cycleVo.getDesignCycleTime())));
				exportCycle.setEcharepxport(!StringUtils.isNotBlank((String)params.get("echarepxport"))?"":(String)params.get("echarepxport"));
				exportList.add(exportCycle);
			}
        } else {
        	CycleVoExport exportCycle = new CycleVoExport();
        	exportCycle.setShop(!StringUtils.isNotBlank((String)params.get("shop")) ? "" : (String)params.get("shop"));
        	exportCycle.setArea(!StringUtils.isNotBlank((String)params.get("area")) ? "ALL" : (String)params.get("area"));
        	exportCycle.setZone(!StringUtils.isNotBlank((String)params.get("zone")) ? "ALL" : (String)params.get("zone"));
        	exportCycle.setStation(!StringUtils.isNotBlank((String)params.get("station")) ? "ALL" : (String)params.get("station"));
        	exportCycle.setJobId(!StringUtils.isNotBlank((String)params.get("jobId")) ? "ALL" : (String)params.get("jobId"));
        	exportCycle.setEquipment(!StringUtils.isNotBlank((String)params.get("equipment")) ? "ALL" : (String)params.get("equipment"));
        	exportCycle.setShift(!StringUtils.isNotBlank((String)params.get("shift")) ? "ALL" : (String)params.get("shift"));
        	Object objstart = params.get("sTime");
        	if(objstart != null && !"".equals(objstart)) {
        		exportCycle.setStartTime((String)params.get("sTime") + " 00:00:00");
        	}else {
        		exportCycle.setStartTime("");
        	}
        	
        	Object objend = params.get("eTime");
        	if(objend != null && !"".equals(objend)) {
        		exportCycle.setEndTime((String)params.get("eTime") + " 23:59:59");
        	}else {
        		exportCycle.setEndTime("");
        	}
        	exportList.add(exportCycle);
        }
    	String exoprt = params.get("exportType") == null ? "word" : (String)params.get("exportType");
    	try {
    		InputStream is = this.getClass().getResourceAsStream("exportModel/Biw3Cycle.jasper");
    	
    		String exportName = "设备节拍报表";
    		JasperExportUtils.export(exportList, exoprt, is, request, response,exportName);
    	} catch (Exception e) {
			e.getMessage();
		}
    }
  
}
