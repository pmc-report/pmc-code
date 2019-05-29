package gean.pmc_report_manager.modules.report.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gean.pmc_report_common.common.utils.DateUtils;
import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_common.common.utils.R;
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

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("report:fault:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = equFaultService.queryEquFaultByParam(params);
        TaEquFaultEntity totalDur = equFaultService.queryTotalMins(params);
    	int duration = totalDur==null?0:totalDur.getDuration();
        return R.ok().put("page", page).put("duration", duration);
    }
    
    @RequestMapping("/exportFault")
    public void exportEquFault(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String,Object> params) {
        List<TaEquFaultEntity> result = equFaultService.queryExportFault(params);
        TaEquFaultEntity totalDur = equFaultService.queryTotalMins(params);
        List<EquFaultExport> exportList = new ArrayList<EquFaultExport>();
        if(result!= null && !result.isEmpty()) {
        	for (TaEquFaultEntity taEquFaultEntity : result) {
           		EquFaultExport exportfault = new EquFaultExport();
        	
        		exportfault.setShop(params.get("shop") == null ? "" : (String)params.get("shop"));
        		exportfault.setLine(params.get("area") == null ? "" : (String)params.get("area"));
        		exportfault.setZone(params.get("zone") == null ? "" : (String)params.get("zone"));
        		exportfault.setStation(params.get("station") == null ? "" : (String)params.get("station"));
        		exportfault.setJobId(params.get("jobId") == null ? "" : (String)params.get("jobId"));
        		exportfault.setEquipment(params.get("equipment") == null ? "" : (String)params.get("equipment"));
        		Object objstart = params.get("sTime");
        		if(objstart != null && "".equals(objstart)) {
        			exportfault.setStartTime((String)params.get("sTime") + " 00:00:00");
        		}else {
        			exportfault.setStartTime(null);
        		}
        	
        		Object objend = params.get("eTime");
        		if(objend != null && "".equals(objend)) {
        			exportfault.setEndTime((String)params.get("eTime") + " 23:59:59");
        		}else {
        			exportfault.setEndTime(null);
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
				exportfault.setPosWord31(taEquFaultEntity.getPosWord31());
				exportfault.setFaultDescription(taEquFaultEntity.getFaultDescription());
				exportfault.setReasonCode(taEquFaultEntity.getReasonCode() == null ? "" : taEquFaultEntity.getReasonCode().toString());
				exportfault.setReasonDescription(taEquFaultEntity.getReasonDescription());
				exportfault.setStartTime_2(taEquFaultEntity.getStartTime() == null ? "" : DateUtils.format(taEquFaultEntity.getStartTime(), DateUtils.DATE_TIME_PATTERN));
				exportfault.setEndTime_2(taEquFaultEntity.getEndTime() == null ? "" : DateUtils.format(taEquFaultEntity.getEndTime(), DateUtils.DATE_TIME_PATTERN));
				exportfault.setDuration(taEquFaultEntity.getDuration() == null ? "" : taEquFaultEntity.getDuration().toString());
				exportfault.setDuration_2(totalDur.getDuration() == null ? "0" : totalDur.getDuration().toString());
	        	exportList.add(exportfault);
        	}
        }else {
        	EquFaultExport exportfault = new EquFaultExport();
        	exportfault.setShop(params.get("shop") == null ? "" : (String)params.get("shop"));
        	exportfault.setLine(params.get("area") == null ? "" : (String)params.get("area"));
        	exportfault.setZone(params.get("zone") == null ? "" : (String)params.get("zone"));
        	exportfault.setStation(params.get("station") == null ? "" : (String)params.get("station"));
        	exportfault.setJobId(params.get("jobId") == null ? "" : (String)params.get("jobId"));
        	exportfault.setEquipment(params.get("equipment") == null ? "" : (String)params.get("equipment"));
        	Object objstart = params.get("sTime");
        	if(objstart != null && "".equals(objstart)) {
        		exportfault.setStartTime((String)params.get("sTime") + " 00:00:00");
        	}else {
        		exportfault.setStartTime("");
        	}
        	
        	Object objend = params.get("eTime");
        	if(objend != null && "".equals(objend)) {
        		exportfault.setEndTime((String)params.get("eTime") + " 23:59:59");
        	}else {
        		exportfault.setEndTime("");
        	}
        	exportList.add(exportfault);
        }
        
    	String exoprt = params.get("exoprtType") == null ? "word" : (String)params.get("exoprtType");
    	try {
    		InputStream is = this.getClass().getResourceAsStream("exportModel/TaEquFault_Word.jasper");//获取同包下模版文件
    		if(exoprt.equals("excel")) {
        		is = this.getClass().getResourceAsStream("exportModel/TaEquFault_Excel.jasper");
        	}
    		JasperExportUtils.export(exportList, exoprt, is, request, response);
    	} catch (Exception e) {
			e.getMessage();
		}
    }
}
