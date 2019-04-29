package gean.pmc_report_manager.modules.report.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_common.common.utils.R;
import gean.pmc_report_common.common.validator.ValidatorUtils;
import gean.pmc_report_manager.modules.report.entity.PmcOprEntity;
import gean.pmc_report_manager.modules.report.service.PmcOprService;

@RestController
@RequestMapping("report/pmcopr")
public class Biw1OprController {
	
	@Autowired
    private PmcOprService pmcOprService;
	
	/**
	 * 图表
	 * @param parmas
	 * @return
	 */
	@RequestMapping("/biw1OprImg")
	public R findBiw1Oprimg(Map<String, Object> parmas) {
		List<String> result = null;
		return R.ok();
	}
	
	@RequestMapping("/biw1opr")
    public R queryBiw1Opr(@RequestParam Map<String, Object> params) {
    	List<PmcOprEntity> pmcbiw1oprList = pmcOprService.queryBiw1Opr(params);
    	return R.ok().put("oprlist", pmcbiw1oprList);
    }
    
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("report:pmcopr:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = pmcOprService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{shop}")
    @RequiresPermissions("report:pmcopr:info")
    public R info(@PathVariable("shop") String shop){
        PmcOprEntity pmcOpr = pmcOprService.getById(shop);

        return R.ok().put("pmcOpr", pmcOpr);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("report:pmcopr:save")
    public R save(@RequestBody PmcOprEntity pmcOpr){
        pmcOprService.save(pmcOpr);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("report:pmcopr:update")
    public R update(@RequestBody PmcOprEntity pmcOpr){
        ValidatorUtils.validateEntity(pmcOpr);
        pmcOprService.updateById(pmcOpr);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("report:pmcopr:delete")
    public R delete(@RequestBody String[] shops){
        pmcOprService.removeByIds(Arrays.asList(shops));

        return R.ok();
    }
}
