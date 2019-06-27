package gean.pmc_report_manager.modules.report.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
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
import gean.pmc_report_common.common.utils.R;
import gean.pmc_report_manager.common.utils.JasperExportUtils;
import gean.pmc_report_manager.modules.report.service.TaBiw3OprService;
import gean.pmc_report_manager.modules.report.vo.AreaOprVo;
import gean.pmc_report_manager.modules.report.vo.OprVoExport;
import gean.pmc_report_manager.modules.report.vo.ZoneOprVo;



/**
 * ${comments}
 *
 * @author ''
 * @email xxxxx@gmail.com
 * @date 2019-04-10 13:54:04
 */
@RestController
@RequestMapping("report/opr")
public class Biw3OprController {
    @Autowired
    private TaBiw3OprService oprService;
       
    
    /**
     * 表格
     */
    @RequestMapping("/list")
    @RequiresPermissions("report:biw3opr:list")
    public R queryList(@RequestParam Map<String, Object> params){
    	List<AreaOprVo> areaList = oprService.queryOprForArea(params);
    	if(areaList.size()>0
    			&&areaList.get(0).getZoneList()!=null
    			&&areaList.get(0).getZoneList().size()>0) {
    		return R.ok().put("area", areaList).put("zoneList", areaList.get(0).getZoneList());
    	}
        return null;
    }

    /**
     * 导出
     * @param request
     * @param response
     * @param params
     */
    @RequestMapping("/exportOpr")
    public void exportOpr(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String,Object> params) {
    	
    	List<AreaOprVo> areaList = oprService.queryOprForArea(params);
    	
    	List<OprVoExport> list = new ArrayList<OprVoExport>();
    	if(areaList != null && !areaList.isEmpty()) {
    		if(areaList.get(0).getZoneList() != null && !areaList.get(0).getZoneList().isEmpty()) {
    			for (ZoneOprVo zoneOprVo : areaList.get(0).getZoneList()) {
    				OprVoExport oprexport = new OprVoExport();
    				oprexport.setShop((String)params.get("shop") == null ? "" : (String)params.get("shop"));
    				oprexport.setShift((String)params.get("shift") == null ? "" : (String)params.get("shift"));
    				oprexport.setStime((String)params.get("sTime") == null ? "" : (String)params.get("sTime")+" 00:00:00");
    				oprexport.setArea((String)params.get("area") == null ? "" : (String)params.get("area"));
    				oprexport.setActual(areaList.get(0).getActual());
    				oprexport.setShiftPlan(areaList.get(0).getShiftPlan());
    				oprexport.setVariation(areaList.get(0).getVariation());
    				oprexport.setProductionOpr(areaList.get(0).getProductionOpr());
    				oprexport.setEquipmentOpr(areaList.get(0).getEquipmentOpr());
        	
    				oprexport.setZone2(zoneOprVo.getZone());
    				oprexport.setGoodPartCount(zoneOprVo.getGoodPartCount());
    				oprexport.setDownTime(DateUtils.msecToTime(zoneOprVo.getDownTime()));
    				oprexport.setProductionOpr2(zoneOprVo.getProductionOpr());
    				oprexport.setEquipmentOpr2(zoneOprVo.getEquipmentOpr());
    				oprexport.setEquipAvail(zoneOprVo.getEquipAvail());
    				oprexport.setBlocked(DateUtils.msecToTime(zoneOprVo.getBlocked()));
    				oprexport.setStarved(DateUtils.msecToTime(zoneOprVo.getStarved()));
    				oprexport.setCycleTime(zoneOprVo.getCycleTime());
        	
    				list.add(oprexport);
				}
    		}else {
    			OprVoExport oprexport = new OprVoExport();
    			oprexport.setShop((String)params.get("shop") == null ? "" : (String)params.get("shop"));
				oprexport.setShift((String)params.get("shift") == null ? "" : (String)params.get("shift"));
				oprexport.setStime((String)params.get("sTime") == null ? "" : (String)params.get("sTime")+" 00:00:00");
				oprexport.setArea((String)params.get("area") == null ? "" : (String)params.get("area"));
				oprexport.setActual(areaList.get(0).getActual());
				oprexport.setShiftPlan(areaList.get(0).getShiftPlan());
				oprexport.setVariation(areaList.get(0).getVariation());
				oprexport.setProductionOpr(areaList.get(0).getProductionOpr());
				oprexport.setEquipmentOpr(areaList.get(0).getEquipmentOpr());
				list.add(oprexport);
    		}
    	}else {
    		OprVoExport oprexport = new OprVoExport();
    		oprexport.setShop((String)params.get("shop") == null ? "" : (String)params.get("shop"));
			oprexport.setShift((String)params.get("shift") == null ? "" : (String)params.get("shift"));
			oprexport.setStime((String)params.get("sTime") == null ? "" : (String)params.get("sTime")+" 00:00:00");
			oprexport.setArea((String)params.get("area") == null ? "" : (String)params.get("area"));
			list.add(oprexport);
    	}
    	String exoprt = params.get("exoprtType") == null ? "word" : (String)params.get("exoprtType");
    	try {
    		InputStream is = this.getClass().getResourceAsStream("exportModel/OPR_Map.jasper");//获取同包下模版文件
        	
        	if(exoprt.equals("excel")) {
        		is = this.getClass().getResourceAsStream("exportModel/OPR_Map_excel.jasper");//获取同包下模版文件
        	}
        	String exportName = "OPR报表";
    		JasperExportUtils.export(list, exoprt, is, request, response,exportName);
		} catch (Exception e) {
			e.getMessage();
		}
    	
    }
}
