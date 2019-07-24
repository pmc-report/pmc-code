package gean.pmc_report_manager.modules.report.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gean.pmc_report_common.common.utils.R;
import gean.pmc_report_manager.modules.report.service.FaultLossesOccurrencesService;
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
		return R.ok().put("minutesLoss", list);
	}
	
	@RequestMapping("/equipmentLoss")
	public R findAllLoss(@RequestParam Map<String, Object> params) {
		List<LossOPRVo> list = lossService.queryEquEchart(params);
		return R.ok().put("equLoss", list);
	}
}
