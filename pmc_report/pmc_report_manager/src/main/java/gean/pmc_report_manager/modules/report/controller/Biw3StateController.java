package gean.pmc_report_manager.modules.report.controller;

import java.util.Arrays;
import java.util.Map;

import gean.pmc_report_common.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gean.pmc_report_manager.modules.report.entity.TaBiw3StateEntity;
import gean.pmc_report_manager.modules.report.service.TaBiw3StateService;
import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_common.common.utils.R;



/**
 * ${comments}
 *
 * @author ''
 * @email xxxxx@gmail.com
 * @date 2019-04-11 10:56:37
 */
@RestController
@RequestMapping("report/biw3state")
public class Biw3StateController {
    @Autowired
    private TaBiw3StateService taBiw3StateService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("report:tabiw3state:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = taBiw3StateService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{taBiw3StateId}")
    @RequiresPermissions("report:tabiw3state:info")
    public R info(@PathVariable("taBiw3StateId") Integer taBiw3StateId){
        TaBiw3StateEntity taBiw3State = taBiw3StateService.getById(taBiw3StateId);

        return R.ok().put("taBiw3State", taBiw3State);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("report:tabiw3state:save")
    public R save(@RequestBody TaBiw3StateEntity taBiw3State){
        taBiw3StateService.save(taBiw3State);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("report:tabiw3state:update")
    public R update(@RequestBody TaBiw3StateEntity taBiw3State){
        ValidatorUtils.validateEntity(taBiw3State);
        taBiw3StateService.updateById(taBiw3State);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("report:tabiw3state:delete")
    public R delete(@RequestBody Integer[] taBiw3StateIds){
        taBiw3StateService.removeByIds(Arrays.asList(taBiw3StateIds));

        return R.ok();
    }

}
