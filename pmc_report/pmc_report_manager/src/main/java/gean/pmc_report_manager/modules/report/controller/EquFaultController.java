package gean.pmc_report_manager.modules.report.controller;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_common.common.utils.R;
import gean.pmc_report_manager.modules.report.entity.TaEquFaultEntity;
import gean.pmc_report_manager.modules.report.service.TaEquFaultService;



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

    @RequestMapping("/duration")
    public R queryTotalMins(@RequestParam Map<String, Object> params) {
    	TaEquFaultEntity totalDur = equFaultService.queryTotalMins(params);
    	Integer duration = totalDur.getDuration();
    	return R.ok().put("duration", duration);
    }
    
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("report:fault:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = equFaultService.queryEquFaultByParam(params);

        return R.ok().put("page", page);
    }

}
