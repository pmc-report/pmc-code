package gean.pmc_report_manager.modules.report.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gean.pmc_report_common.common.utils.R;
import gean.pmc_report_common.common.utils.StringUtils;
import gean.pmc_report_manager.common.utils.JasperExportUtils;
import gean.pmc_report_manager.modules.report.service.TaBiw39panelService;
import gean.pmc_report_manager.modules.report.service.TaDetailService;
import gean.pmc_report_manager.modules.report.vo.DetailVo;
import gean.pmc_report_manager.modules.report.vo.FaultVo;
import gean.pmc_report_manager.modules.report.vo.MsDataVo;
import gean.pmc_report_manager.modules.report.vo.TaDetailVo;

@RestController
@RequestMapping("modules/report/detail")
public class BiwTaDetailController {
	
	@Autowired
	private TaBiw39panelService taBiw39panelService; 
	
	@Autowired
	private TaDetailService taDetailService;
	
	
	/**
	 * 获取MS列表
	 *//*
	@RequestMapping("/listMS")
	public R list(@RequestParam Map<String, Object> params){
		List<MsDataVo> list = taBiw39panelService.queryMsList(params);
		return R.ok().put("list",list);
	}*/
	
	/**
	 * 获取ta列表
	 */
	@RequestMapping("/list")
	public R list2(@RequestParam Map<String, Object> params){
		Map<String,List<TaDetailVo>> map = taDetailService.queryTAVInfo(params);
		return R.ok().put("map",map);
	}
	
	/**
	 * 导出功能
	 */
	
	@RequestMapping("/exportDetail")
	 public void exportDetail(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String,Object> params) { 
		Map<String,List<MsDataVo>> msMap = taDetailService.queryTaDetailList(params);
    	List<FaultVo> faultList = taDetailService.findFaults(params);
    	List<DetailVo> dList = new ArrayList<DetailVo>();
    	
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
    	Set<String> equSet = msMap.keySet();
    	int count = 0;
    	for(String key : equSet) {
    		if(count==0) {
    			detailVo.setMsList(msMap.get(key));
        		List<FaultVo> tavList = new ArrayList<>();
        		for(FaultVo vo : faultList) {
        			if(key.equals(vo.getStation())) {
        				tavList.add(vo);
        			}
        		}
        		if(StringUtils.isEmpty(tavList)) {
        			FaultVo vo = new FaultVo();
        			vo.setStation(key);
        			tavList.add(vo);
        		}
        		detailVo.setFaultList(tavList);
    		}
    		if(count==1) {
    			detailVo.setMsList1(msMap.get(key));
        		List<FaultVo> tavList = new ArrayList<>();
        		for(FaultVo vo : faultList) {
        			if(key.equals(vo.getStation())) {
        				tavList.add(vo);
        			}
        		}
        		if(StringUtils.isEmpty(tavList)) {
        			FaultVo vo = new FaultVo();
        			vo.setStation(key);
        			tavList.add(vo);
        		}
        		detailVo.setFaultList1(tavList);
    		}
    		if(count==2) {
    			detailVo.setMsList2(msMap.get(key));
        		List<FaultVo> tavList = new ArrayList<>();
        		for(FaultVo vo : faultList) {
        			if(key.equals(vo.getStation())) {
        				tavList.add(vo);
        			}
        		}
        		if(StringUtils.isEmpty(tavList)) {
        			FaultVo vo = new FaultVo();
        			vo.setStation(key);
        			tavList.add(vo);
        		}
        		detailVo.setFaultList2(tavList);
    		}
    		if(count==3) {
    			detailVo.setMsList3(msMap.get(key));
        		List<FaultVo> tavList = new ArrayList<>();
        		for(FaultVo vo : faultList) {
        			if(key.equals(vo.getStation())) {
        				tavList.add(vo);
        			}
        		}
        		if(StringUtils.isEmpty(tavList)) {
        			FaultVo vo = new FaultVo();
        			vo.setStation(key);
        			tavList.add(vo);
        		}
        		detailVo.setFaultList3(tavList);
    		}
    		if(count==4) {
    			detailVo.setMsList4(msMap.get(key));
        		List<FaultVo> tavList = new ArrayList<>();
        		for(FaultVo vo : faultList) {
        			if(key.equals(vo.getStation())) {
        				tavList.add(vo);
        			}
        		}
        		if(StringUtils.isEmpty(tavList)) {
        			FaultVo vo = new FaultVo();
        			vo.setStation(key);
        			tavList.add(vo);
        		}
        		detailVo.setFaultList4(tavList);
    		}
    		if(count==5) {
    			detailVo.setMsList5(msMap.get(key));
        		List<FaultVo> tavList = new ArrayList<>();
        		for(FaultVo vo : faultList) {
        			if(key.equals(vo.getStation())) {
        				tavList.add(vo);
        			}
        		}
        		if(StringUtils.isEmpty(tavList)) {
        			FaultVo vo = new FaultVo();
        			vo.setStation(key);
        			tavList.add(vo);
        		}
        		detailVo.setFaultList5(tavList);
    		}
    		if(count==6) {
    			detailVo.setMsList6(msMap.get(key));
        		List<FaultVo> tavList = new ArrayList<>();
        		for(FaultVo vo : faultList) {
        			if(key.equals(vo.getStation())) {
        				tavList.add(vo);
        			}
        		}
        		if(StringUtils.isEmpty(tavList)) {
        			FaultVo vo = new FaultVo();
        			vo.setStation(key);
        			tavList.add(vo);
        		}
        		detailVo.setFaultList6(tavList);
    		}
    		if(count==7) {
    			detailVo.setMsList7(msMap.get(key));
        		List<FaultVo> tavList = new ArrayList<>();
        		for(FaultVo vo : faultList) {
        			if(key.equals(vo.getStation())) {
        				tavList.add(vo);
        			}
        		}
        		if(StringUtils.isEmpty(tavList)) {
        			FaultVo vo = new FaultVo();
        			vo.setStation(key);
        			tavList.add(vo);
        		}
        		detailVo.setFaultList7(tavList);
    		}
    		if(count==8) {
    			detailVo.setMsList8(msMap.get(key));
        		List<FaultVo> tavList = new ArrayList<>();
        		for(FaultVo vo : faultList) {
        			if(key.equals(vo.getStation())) {
        				tavList.add(vo);
        			}
        		}
        		if(StringUtils.isEmpty(tavList)) {
        			FaultVo vo = new FaultVo();
        			vo.setStation(key);
        			tavList.add(vo);
        		}
        		detailVo.setFaultList8(tavList);
    		}
    		if(count==9) {
    			detailVo.setMsList9(msMap.get(key));
        		List<FaultVo> tavList = new ArrayList<>();
        		for(FaultVo vo : faultList) {
        			if(key.equals(vo.getStation())) {
        				tavList.add(vo);
        			}
        		}
        		if(StringUtils.isEmpty(tavList)) {
        			FaultVo vo = new FaultVo();
        			vo.setStation(key);
        			tavList.add(vo);
        		}
        		detailVo.setFaultList9(tavList);
    		}
    		if(count==10) {
    			detailVo.setMsList10(msMap.get(key));
        		List<FaultVo> tavList = new ArrayList<>();
        		for(FaultVo vo : faultList) {
        			if(key.equals(vo.getStation())) {
        				tavList.add(vo);
        			}
        		}
        		if(StringUtils.isEmpty(tavList)) {
        			FaultVo vo = new FaultVo();
        			vo.setStation(key);
        			tavList.add(vo);
        		}
        		detailVo.setFaultList10(tavList);
    		}
    		if(count==11) {
    			detailVo.setMsList11(msMap.get(key));
        		List<FaultVo> tavList = new ArrayList<>();
        		for(FaultVo vo : faultList) {
        			if(key.equals(vo.getStation())) {
        				tavList.add(vo);
        			}
        		}
        		if(StringUtils.isEmpty(tavList)) {
        			FaultVo vo = new FaultVo();
        			vo.setStation(key);
        			tavList.add(vo);
        		}
        		detailVo.setFaultList11(tavList);
    		}
    		if(count==12) {
    			detailVo.setMsList12(msMap.get(key));
        		List<FaultVo> tavList = new ArrayList<>();
        		for(FaultVo vo : faultList) {
        			if(key.equals(vo.getStation())) {
        				tavList.add(vo);
        			}
        		}
        		if(StringUtils.isEmpty(tavList)) {
        			FaultVo vo = new FaultVo();
        			vo.setStation(key);
        			tavList.add(vo);
        		}
        		detailVo.setFaultList12(tavList);
    		}
    		if(count==13) {
    			detailVo.setMsList1(msMap.get(key));
        		List<FaultVo> tavList = new ArrayList<>();
        		for(FaultVo vo : faultList) {
        			if(key.equals(vo.getStation())) {
        				tavList.add(vo);
        			}
        		}
        		if(StringUtils.isEmpty(tavList)) {
        			FaultVo vo = new FaultVo();
        			vo.setStation(key);
        			tavList.add(vo);
        		}
        		detailVo.setFaultList13(tavList);
    		}
    		if(count==14) {
    			detailVo.setMsList14(msMap.get(key));
        		List<FaultVo> tavList = new ArrayList<>();
        		for(FaultVo vo : faultList) {
        			if(key.equals(vo.getStation())) {
        				tavList.add(vo);
        			}
        		}
        		if(StringUtils.isEmpty(tavList)) {
        			FaultVo vo = new FaultVo();
        			vo.setStation(key);
        			tavList.add(vo);
        		}
        		detailVo.setFaultList14(tavList);
    		}
    		if(count==15) {
    			detailVo.setMsList15(msMap.get(key));
        		List<FaultVo> tavList = new ArrayList<>();
        		for(FaultVo vo : faultList) {
        			if(key.equals(vo.getStation())) {
        				tavList.add(vo);
        			}
        		}
        		if(StringUtils.isEmpty(tavList)) {
        			FaultVo vo = new FaultVo();
        			vo.setStation(key);
        			tavList.add(vo);
        		}
        		detailVo.setFaultList15(tavList);
    		}
    		if(count==16) {
    			detailVo.setMsList16(msMap.get(key));
        		List<FaultVo> tavList = new ArrayList<>();
        		for(FaultVo vo : faultList) {
        			if(key.equals(vo.getStation())) {
        				tavList.add(vo);
        			}
        		}
        		if(StringUtils.isEmpty(tavList)) {
        			FaultVo vo = new FaultVo();
        			vo.setStation(key);
        			tavList.add(vo);
        		}
        		detailVo.setFaultList16(tavList);
    		}
    		if(count==17) {
    			detailVo.setMsList17(msMap.get(key));
        		List<FaultVo> tavList = new ArrayList<>();
        		for(FaultVo vo : faultList) {
        			if(key.equals(vo.getStation())) {
        				tavList.add(vo);
        			}
        		}
        		if(StringUtils.isEmpty(tavList)) {
        			FaultVo vo = new FaultVo();
        			vo.setStation(key);
        			tavList.add(vo);
        		}
        		detailVo.setFaultList17(tavList);
    		}
    		if(count==18) {
    			detailVo.setMsList18(msMap.get(key));
        		List<FaultVo> tavList = new ArrayList<>();
        		for(FaultVo vo : faultList) {
        			if(key.equals(vo.getStation())) {
        				tavList.add(vo);
        			}
        		}
        		if(StringUtils.isEmpty(tavList)) {
        			FaultVo vo = new FaultVo();
        			vo.setStation(key);
        			tavList.add(vo);
        		}
        		detailVo.setFaultList18(tavList);
    		}
    		
    		count++;
    	}
		
    	dList.add(detailVo);
    	String exoprt = params.get("exportType") == null ? "word" : (String)params.get("exportType");
    	try {
    		InputStream is = this.getClass().getResourceAsStream("exportModel/TA_Detail_2.jasper");//获取同包下模版文件
    		String exportName = "TA_Detail_报表";
    		JasperExportUtils.export(dList, exoprt, is, request, response,exportName);

    	} catch (Exception e) {
    		params.clear();
			e.getMessage();
		}
	} 
	

}
