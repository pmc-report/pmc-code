package gean.pmc_report_manager.modules.report.controller;

import java.util.Arrays;
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
import gean.pmc_report_manager.modules.report.entity.FinalBiw3OprEntity;
import gean.pmc_report_manager.modules.report.service.FinalBiw3OprService;



/**
 * ${comments}
 *
 * @author Jason
 * @email xxxxx@gmail.com
 * @date 2019-03-11 14:13:04
 */
@RestController
@RequestMapping("report/finalbiw3opr")
public class FinalBiw3OprController {
    @Autowired
    private FinalBiw3OprService finalBiw3OprService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("report:finalbiw3opr:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = finalBiw3OprService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{rowId}")
    @RequiresPermissions("report:finalbiw3opr:info")
    public R info(@PathVariable("rowId") Integer rowId){
        FinalBiw3OprEntity finalBiw3Opr = finalBiw3OprService.getById(rowId);

        return R.ok().put("finalBiw3Opr", finalBiw3Opr);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("report:finalbiw3opr:save")
    public R save(@RequestBody FinalBiw3OprEntity finalBiw3Opr){
        finalBiw3OprService.save(finalBiw3Opr);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("report:finalbiw3opr:update")
    public R update(@RequestBody FinalBiw3OprEntity finalBiw3Opr){
        ValidatorUtils.validateEntity(finalBiw3Opr);
        finalBiw3OprService.updateById(finalBiw3Opr);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("report:finalbiw3opr:delete")
    public R delete(@RequestBody Integer[] rowIds){
        finalBiw3OprService.removeByIds(Arrays.asList(rowIds));

        return R.ok();
    }

}
