package gean.pmc_report_manager.modules.report.controller;

import java.util.Arrays;
import java.util.Map;

import gean.pmc_report_common.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gean.pmc_report_manager.modules.report.entity.TaEquFaultEntity;
import gean.pmc_report_manager.modules.report.service.EquFaultService;
import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_common.common.utils.R;



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
    private EquFaultService EquFaultService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("report:fault:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = EquFaultService.queryEquFaultByParam(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{taEquFaultId}")
    @RequiresPermissions("report:taequfault:info")
    public R info(@PathVariable("taEquFaultId") Integer taEquFaultId){
        TaEquFaultEntity taEquFault = EquFaultService.getById(taEquFaultId);

        return R.ok().put("taEquFault", taEquFault);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("report:taequfault:save")
    public R save(@RequestBody TaEquFaultEntity taEquFault){
        EquFaultService.save(taEquFault);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("report:taequfault:update")
    public R update(@RequestBody TaEquFaultEntity taEquFault){
        ValidatorUtils.validateEntity(taEquFault);
        EquFaultService.updateById(taEquFault);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("report:taequfault:delete")
    public R delete(@RequestBody Integer[] taEquFaultIds){
        EquFaultService.removeByIds(Arrays.asList(taEquFaultIds));

        return R.ok();
    }

}
