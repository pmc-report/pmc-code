package gean.pmc_report_manager.modules.report.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gean.pmc_report_common.common.utils.R;
import gean.pmc_report_common.common.utils.StringUtils;
import gean.pmc_report_manager.common.utils.JasperExportUtils;
import gean.pmc_report_manager.modules.report.entity.TaBiw39panelEntity;
import gean.pmc_report_manager.modules.report.service.TaBiw39panelService;
import gean.pmc_report_manager.modules.report.service.TaDetailService;
import gean.pmc_report_manager.modules.report.vo.DetailVo;
import gean.pmc_report_manager.modules.report.vo.FaultVo;

@RestController
@RequestMapping("modules/report/detail")
public class BiwTaDetailController {
	
	@Autowired
	private TaBiw39panelService taBiw39panelService; 
	
	@Autowired
	private TaDetailService taDetailService;
	
	
	/**
	 * 获取MS列表
	 */
	@RequestMapping("/listMS")
	public R list(@RequestParam Map<String, Object> params){
		List<TaBiw39panelEntity> list = taBiw39panelService.queryMsList(params);
		return R.ok().put("list",list);
	}
	
	/**
	 * 获取Faults列表
	 */
	@RequestMapping("/listFaults")
	public R list2(@RequestParam Map<String, Object> params){
		List<FaultVo> list = taDetailService.queryTAVInfo(params);
		return R.ok().put("list",list);
	}
	
	/**
	 * 导出功能
	 */
	
	@RequestMapping("/exportDetail")
	 public void exportDetail(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String,Object> params) { 
    	List<TaBiw39panelEntity> msList = taBiw39panelService.queryMsList(params);
    	List<FaultVo> faultList = taDetailService.queryTAVInfo(params);
    	List<DetailVo> dList = new ArrayList<DetailVo>();
    	if (msList!= null && !msList.isEmpty()) {
    		for(TaBiw39panelEntity taBiw39panelEntity  : msList) {
    			DetailVo detailVo = new DetailVo();
    			detailVo.setShop(!StringUtils.isNotBlank((String)params.get("shop")) ? "" : (String)params.get("shop"));
    			detailVo.setArea(!StringUtils.isNotBlank((String)params.get("area")) ? "ALL" : (String)params.get("area"));
    			detailVo.setZone(!StringUtils.isNotBlank((String)params.get("zone")) ? "ALL" : (String)params.get("zone"));
    			detailVo.setJobId(!StringUtils.isNotBlank((String)params.get("jobId")) ? "ALL" : (String)params.get("jobId"));
    			detailVo.setShift(!StringUtils.isNotBlank((String)params.get("shift")) ? "ALL" : (String)params.get("shift"));
        		Object objstart = params.get("sTime");
        		if(objstart != null && !"".equals(objstart)) {
        			detailVo.setStartTime((String)params.get("sTime") + " 00:00:00");
        		}else {
        			detailVo.setStartTime("");
        		}
        	
        		Object objend = params.get("eTime");
        		if(objend != null && !"".equals(objend)) {
        			detailVo.setEndTime((String)params.get("eTime") + " 23:59:59");
        		}else {
        			detailVo.setEndTime("");
        		}
        		detailVo.setEquipment(taBiw39panelEntity.getEquipment());
        		detailVo.setWorkDay(taBiw39panelEntity.getWorkDay());
        		detailVo.setTarTechAvail(taBiw39panelEntity.getTarTechAvail());
        		detailVo.setTechAvail(taBiw39panelEntity.getTechAvail());
        		detailVo.setGoodPartCount(taBiw39panelEntity.getGoodPartCount());
        		detailVo.setDownTime(taBiw39panelEntity.getDowntime());
        		detailVo.setFaultOcc(taBiw39panelEntity.getFaultOcc());
        		detailVo.setBuildTime(taBiw39panelEntity.getBuildTime());
        		dList.add(detailVo);
    		}
    		if(!StringUtils.isEmpty(dList)) {
    			dList.get(dList.size()-1).setFaultList(faultList);
    		}
    	}
    	
    	String exoprt = params.get("exportType") == null ? "word" : (String)params.get("exportType");
    	try {
    		InputStream is = this.getClass().getResourceAsStream("exportModel/TA_Detail.jasper");//获取同包下模版文件
    		String exportName = "TA_Detail_报表";
    		JasperExportUtils.export(dList, exoprt, is, request, response,exportName);

    	} catch (Exception e) {
    		params.clear();
			e.getMessage();
		}
	} 
	

}
