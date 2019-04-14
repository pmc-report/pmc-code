package gean.pmc_report_manager.modules.base.controller;

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
import gean.pmc_report_manager.modules.base.entity.TmBasUlocEntity;
import gean.pmc_report_manager.modules.base.service.TmBasUlocService;



/**
 * 工位信息
 *
 * @author Jason
 * @email xxxxx@gmail.com
 * @date 2019-03-22 09:54:06
 */
@RestController
@RequestMapping("")
public class UlocController {
    @Autowired
    private TmBasUlocService tmBasUlocService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("report:tmbasuloc:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = tmBasUlocService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{tmBasUlocId}")
    @RequiresPermissions("report:tmbasuloc:info")
    public R info(@PathVariable("tmBasUlocId") Integer tmBasUlocId){
        TmBasUlocEntity tmBasUloc = tmBasUlocService.getById(tmBasUlocId);

        return R.ok().put("tmBasUloc", tmBasUloc);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("report:tmbasuloc:save")
    public R save(@RequestBody TmBasUlocEntity tmBasUloc){
        tmBasUlocService.save(tmBasUloc);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("report:tmbasuloc:update")
    public R update(@RequestBody TmBasUlocEntity tmBasUloc){
        ValidatorUtils.validateEntity(tmBasUloc);
        tmBasUlocService.updateById(tmBasUloc);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("report:tmbasuloc:delete")
    public R delete(@RequestBody Integer[] tmBasUlocIds){
        tmBasUlocService.removeByIds(Arrays.asList(tmBasUlocIds));

        return R.ok();
    }

}
