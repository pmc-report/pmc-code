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

import gean.pmc_report_common.common.utils.DateUtils;
import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_common.common.utils.R;
import gean.pmc_report_common.common.utils.StringUtils;
import gean.pmc_report_manager.common.utils.JasperExportUtils;
import gean.pmc_report_manager.modules.report.service.FaultLossesOccurrencesService;
import gean.pmc_report_manager.modules.report.vo.EquFaultExport;
import gean.pmc_report_manager.modules.report.vo.FaultVo;
import gean.pmc_report_manager.modules.report.vo.LossOPRVo;

@RestController
@RequestMapping("/report/loss")
public class FaultLossController {

	@Autowired
	private FaultLossesOccurrencesService lossService;
	
	
	@RequestMapping("/oprLoss")
	public R findOprLoss(@RequestParam Map<String, Object> params) {
		List<LossOPRVo> list = lossService.queryOprEchart(params);
		return R.ok().put("oprLoss", list);
	}
	
	@RequestMapping("/minutesLoss")
	public R findMinutesLoss(@RequestParam Map<String, Object> params) {
		List<LossOPRVo> list = lossService.queryMinutesEchart(params);
		return R.ok().put("minutesLoss", list).put("week",DateUtils.weekOfYear());
	}
	
	@RequestMapping("/equipmentLoss")
	public R findAllLoss(@RequestParam Map<String, Object> params) {
		List<LossOPRVo> list = lossService.queryEquEchart(params);
		return R.ok().put("equLoss", list);
	}
	
	@RequestMapping("/lossList")
	public R findLossList(@RequestParam Map<String, Object> params) {
		List<LossOPRVo> list = lossService.queryFaultLossOcc(params);
		return R.ok().put("list", list);
	}
	
	@RequestMapping("/exportFault")
    public void exportEquFault(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String,Object> params) {

		List<LossOPRVo> result = lossService.queryFaultLossOcc(params);
        List<LossOPRVo> exportList = new ArrayList<LossOPRVo>();
        if(result!= null && !result.isEmpty()) {
        	for (LossOPRVo loss : result) {
        		loss.setShop(!StringUtils.isNotBlank((String)params.get("shop")) ? "" : (String)params.get("shop"));
        		loss.setArea(!StringUtils.isNotBlank((String)params.get("area")) ? "ALL" : (String)params.get("area"));
        		loss.setZone(!StringUtils.isNotBlank((String)params.get("zone")) ? "ALL" : (String)params.get("zone"));
        		loss.setShift(!StringUtils.isNotBlank((String)params.get("shift")) ? "ALL" : (String)params.get("shift"));
        		loss.setOprEchart(!StringUtils.isNotBlank((String)params.get("echarepxport")) ? "" : (String)params.get("echarepxport"));
        		loss.setMinLossEchart(!StringUtils.isNotBlank((String)params.get("echarepxport1")) ? "" : (String)params.get("echarepxport1"));
        		loss.setProdLossEchart(!StringUtils.isNotBlank((String)params.get("echarepxport2")) ? "" : (String)params.get("echarepxport2"));
	        	exportList.add(loss);
        	}
        }
    	String exoprt = params.get("type") == null ? "word" : (String)params.get("type");
    	try {
    		InputStream is = EquFaultController.class.getResourceAsStream("exportModel/fault_Loss.jasper");//获取同包下模版文件
    		if(exoprt.equals("excel")) {
        		is = this.getClass().getResourceAsStream("exportModel/fault_Loss.jasper");
        	}
    		String exportName = "Fault_Loss_报表";
    		JasperExportUtils.export(exportList, exoprt, is, request, response,exportName);
    	} catch (Exception e) {
			e.getMessage();
		}
    }
}
