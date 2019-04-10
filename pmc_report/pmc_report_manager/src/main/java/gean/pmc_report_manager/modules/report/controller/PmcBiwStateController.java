package gean.pmc_report_manager.modules.report.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_common.common.utils.R;
import gean.pmc_report_common.common.validator.ValidatorUtils;
import gean.pmc_report_manager.modules.report.entity.PmcBiwStateEntity;
import gean.pmc_report_manager.modules.report.service.PmcBiwStateService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;




/**
 * ${comments}
 *
 * @author Jason
 * @email xxxxx@gmail.com
 * @date 2019-03-14 09:49:18
 */
@RestController
@RequestMapping("report/pmcbiwstate")
public class PmcBiwStateController {
    @Autowired
    private PmcBiwStateService pmcBiwStateService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("report:pmcbiwstate:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = pmcBiwStateService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{startTime}")
    @RequiresPermissions("report:pmcbiwstate:info")
    public R info(@PathVariable("startTime") Date startTime){
        PmcBiwStateEntity pmcBiwState = pmcBiwStateService.getById(startTime);

        return R.ok().put("pmcBiwState", pmcBiwState);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("report:pmcbiwstate:save")
    public R save(@RequestBody PmcBiwStateEntity pmcBiwState){
        pmcBiwStateService.save(pmcBiwState);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("report:pmcbiwstate:update")
    public R update(@RequestBody PmcBiwStateEntity pmcBiwState){
        ValidatorUtils.validateEntity(pmcBiwState);
        pmcBiwStateService.updateById(pmcBiwState);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("report:pmcbiwstate:delete")
    public R delete(@RequestBody Date[] startTimes){
        pmcBiwStateService.removeByIds(Arrays.asList(startTimes));

        return R.ok();
    }

}
