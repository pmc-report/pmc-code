package gean.pmc_report_manager.modules.report.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
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

import gean.pmc_report_common.common.utils.DateUtils;
import gean.pmc_report_common.common.utils.IPUtils;
import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_common.common.utils.R;
import gean.pmc_report_common.common.utils.StringUtils;
import gean.pmc_report_manager.common.utils.JasperExportUtils;
import gean.pmc_report_manager.modules.report.entity.TaEquFaultEntity;
import gean.pmc_report_manager.modules.report.service.TaEquFaultService;
import gean.pmc_report_manager.modules.report.vo.EquFaultExport;


/**
 * ${comments}
 *
 * @author Jason
 * @email xxxxx@gmail.com
 * @date 2019-03-30 09:27:53
 */
@RestController
@RequestMapping("report/fault")
public class EquFaultController {
    @Autowired
    private TaEquFaultService equFaultService;
    
    private static Logger logger = LoggerFactory.getLogger(EquFaultController.class);
    
    Map<String,Object> resultMap = new HashMap<>();

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("report:fault:list")
    public R list(@RequestParam Map<String, Object> params){
    	TaEquFaultEntity totalDur = equFaultService.queryTotalMins(params);
        PageUtils page = equFaultService.queryEquFaultByParam(params);
    	int duration = totalDur==null?0:totalDur.getDuration();
    	resultMap.put("duration", duration);
        return R.ok().put("page", page).put("duration", duration);
    }
    
    @RequestMapping("/exportAll")
    public void exportAll(@RequestParam Map<String,Object> params) {
    	long startTime = System.currentTimeMillis();
    	logger.debug("页面表格加载完成后执行导出查询开始....");
    	List<TaEquFaultEntity> result = equFaultService.queryExportFault(params);
    	long endTime = System.currentTimeMillis();
    	long usedTime = (endTime - startTime)/1000;
    	logger.debug("执行查询结束，耗时："+usedTime+" 秒");
        resultMap.put("result", result);
    }
    
    @RequestMapping("/exportFault")
    public void exportEquFault(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String,Object> params) {
    	logger.debug("执行导出开始....");
    	long startTime = System.currentTimeMillis();
    	List<TaEquFaultEntity> result = (List)resultMap.get("result");
    	logger.debug("需要处理的数据有："+result.size()+" 条");
    	Integer totalDur = (int)resultMap.get("duration");
        List<EquFaultExport> exportList = new ArrayList<EquFaultExport>();
        if(result!= null && !result.isEmpty()) {
        	for (TaEquFaultEntity taEquFaultEntity : result) {
           		EquFaultExport exportfault = new EquFaultExport();
           		
           		exportfault.setShop(!StringUtils.isNotBlank((String)params.get("shop")) ? "" : (String)params.get("shop"));
            	exportfault.setLine(!StringUtils.isNotBlank((String)params.get("area")) ? "ALL" : (String)params.get("area"));
            	exportfault.setZone(!StringUtils.isNotBlank((String)params.get("zone")) ? "ALL" : (String)params.get("zone"));
            	exportfault.setStation(!StringUtils.isNotBlank((String)params.get("station")) ? "ALL" : (String)params.get("station"));
            	exportfault.setJobId(!StringUtils.isNotBlank((String)params.get("jobId")) ? "ALL" : (String)params.get("jobId"));
            	exportfault.setEquipment(!StringUtils.isNotBlank((String)params.get("equipment")) ? "ALL" : (String)params.get("equipment"));
            	exportfault.setShift(!StringUtils.isNotBlank((String)params.get("shift")) ? "ALL" : (String)params.get("shift"));
        		Object objstart = params.get("sTime");
        		if(objstart != null && !"".equals(objstart)) {
        			exportfault.setStartTime((String)params.get("sTime") + " 00:00:00");
        		}else {
        			exportfault.setStartTime("");
        		}
        	
        		Object objend = params.get("eTime");
        		if(objend != null && !"".equals(objend)) {
        			exportfault.setEndTime((String)params.get("eTime") + " 23:59:59");
        		}else {
        			exportfault.setEndTime("");
        		}
        	
				exportfault.setLine_2(taEquFaultEntity.getLine());
				exportfault.setZone_2(taEquFaultEntity.getZone());
				exportfault.setStation_2(taEquFaultEntity.getStation());
				exportfault.setFacilityId_2(taEquFaultEntity.getFacilityId() == null ? "" : taEquFaultEntity.getFacilityId().toString());
				exportfault.setFacilityDesc(taEquFaultEntity.getFacilityDesc());
				exportfault.setJobId_2(taEquFaultEntity.getJobId());
				exportfault.setFaultWord1(taEquFaultEntity.getFaultWord1() == null ? "" : taEquFaultEntity.getFaultWord1().toString());
				exportfault.setFaultWord2(taEquFaultEntity.getFaultWord2() == null ? "" : taEquFaultEntity.getFaultWord2().toString());
				exportfault.setFaultWord3(taEquFaultEntity.getFaultWord3() == null ? "" : taEquFaultEntity.getFaultWord3().toString());
				exportfault.setPosWord31(taEquFaultEntity.getPosWord31()==null ? 0 : taEquFaultEntity.getPosWord31());
				exportfault.setFaultDescription(taEquFaultEntity.getFaultDescription());
				exportfault.setReasonCode(taEquFaultEntity.getReasonCode() == null ? "" : taEquFaultEntity.getReasonCode().toString());
				exportfault.setReasonDescription(taEquFaultEntity.getReasonDescription());
				exportfault.setStartTime_2(taEquFaultEntity.getStartTime() == null ? "" : DateUtils.format(taEquFaultEntity.getStartTime(), DateUtils.DATE_TIME_PATTERN));
				exportfault.setEndTime_2(taEquFaultEntity.getEndTime() == null ? "" : DateUtils.format(taEquFaultEntity.getEndTime(), DateUtils.DATE_TIME_PATTERN));
				//单条持续时间
				Integer duration = taEquFaultEntity.getDuration();
				exportfault.setDuration(DateUtils.longToDate(duration));
				//总持续时间
				//Integer totalDuration = totalDur.getDuration();
				exportfault.setDuration_2(DateUtils.longToDate(totalDur));
	        	exportList.add(exportfault);
        	}
        	long endTime = System.currentTimeMillis();
        	long usedTime = (endTime - startTime)/1000;
        	logger.debug("循环处理的数据结束,耗时："+usedTime+" 秒");
        }else {
        	EquFaultExport exportfault = new EquFaultExport();
        	exportfault.setShop(!StringUtils.isNotBlank((String)params.get("shop")) ? "" : (String)params.get("shop"));
        	exportfault.setLine(!StringUtils.isNotBlank((String)params.get("area")) ? "ALL" : (String)params.get("area"));
        	exportfault.setZone(!StringUtils.isNotBlank((String)params.get("zone")) ? "ALL" : (String)params.get("zone"));
        	exportfault.setStation(!StringUtils.isNotBlank((String)params.get("station")) ? "ALL" : (String)params.get("station"));
        	exportfault.setJobId(!StringUtils.isNotBlank((String)params.get("jobId")) ? "ALL" : (String)params.get("jobId"));
        	exportfault.setEquipment(!StringUtils.isNotBlank((String)params.get("equipment")) ? "ALL" : (String)params.get("equipment"));
        	exportfault.setShift(!StringUtils.isNotBlank((String)params.get("shift")) ? "ALL" : (String)params.get("shift"));
        	Object objstart = params.get("sTime");
        	if(objstart != null && !"".equals(objstart)) {
        		exportfault.setStartTime((String)params.get("sTime") + " 00:00:00");
        	}else {
        		exportfault.setStartTime("");
        	}
        	
        	Object objend = params.get("eTime");
        	if(objend != null && !"".equals(objend)) {
        		exportfault.setEndTime((String)params.get("eTime") + " 23:59:59");
        	}else {
        		exportfault.setEndTime("");
        	}
        	exportList.add(exportfault);
        }
        
    	String exoprt = params.get("exoprtType") == null ? "word" : (String)params.get("exoprtType");
    	try {
    		InputStream is = EquFaultController.class.getResourceAsStream("exportModel/TaEquFault_Word.jasper");//获取同包下模版文件
    		if(exoprt.equals("excel")) {
        		is = this.getClass().getResourceAsStream("exportModel/TaEquFault_Excel.jasper");
        	}
    		String exportName = "设备故障报表";
    		JasperExportUtils.export(exportList, exoprt, is, request, response,exportName);
    		long endTime = System.currentTimeMillis();
        	long usedTime = (endTime - startTime)/1000;
        	logger.debug("导出数据结束,耗时："+usedTime+" 秒");
    	} catch (Exception e) {
			e.getMessage();
		}
    }
}
