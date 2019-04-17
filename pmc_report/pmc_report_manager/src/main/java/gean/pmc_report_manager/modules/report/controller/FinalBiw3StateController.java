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

import gean.pmc_report_manager.modules.report.entity.FinalBiw3StateEntity;
import gean.pmc_report_manager.modules.report.service.FinalBiw3StateService;
import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_common.common.utils.R;



/**
 * ${comments}
 *
 * @author ''
 * @email xxxxx@gmail.com
 * @date 2019-04-17 21:02:05
 */
@RestController
@RequestMapping("report/finalbiw3state")
public class FinalBiw3StateController {
    @Autowired
    private FinalBiw3StateService finalBiw3StateService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("report:finalbiw3state:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = finalBiw3StateService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{rowId}")
    @RequiresPermissions("report:finalbiw3state:info")
    public R info(@PathVariable("rowId") Integer rowId){
        FinalBiw3StateEntity finalBiw3State = finalBiw3StateService.getById(rowId);

        return R.ok().put("finalBiw3State", finalBiw3State);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("report:finalbiw3state:save")
    public R save(@RequestBody FinalBiw3StateEntity finalBiw3State){
        finalBiw3StateService.save(finalBiw3State);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("report:finalbiw3state:update")
    public R update(@RequestBody FinalBiw3StateEntity finalBiw3State){
        ValidatorUtils.validateEntity(finalBiw3State);
        finalBiw3StateService.updateById(finalBiw3State);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("report:finalbiw3state:delete")
    public R delete(@RequestBody Integer[] rowIds){
        finalBiw3StateService.removeByIds(Arrays.asList(rowIds));

        return R.ok();
    }

}
