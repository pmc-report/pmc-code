package gean.pmc_report_manager.modules.base.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import gean.pmc_report_common.common.validator.ValidatorUtils;
import gean.pmc_report_manager.modules.base.entity.TmBasShiftEntity;
import gean.pmc_report_manager.modules.base.service.TmBasShiftService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_common.common.utils.R;



/**
 * ${comments}
 *
 * @author Jason
 * @email xxxxx@gmail.com
 * @date 2019-04-04 11:17:03
 */
@RestController
@RequestMapping("/modules/report/shift")
public class ShiftController {
    @Autowired
    private TmBasShiftService shiftService;

    
    @RequestMapping("/findShift")
    public R queryAllShift() {
    	
    	List<TmBasShiftEntity> shiftList=shiftService.queryShift();
    	
		return R.ok().put("tmBasShift", shiftList);
    }
    
    
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("report:tmbasshift:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = shiftService.queryPage(params);

        return R.ok().put("list", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{tmBasShiftId}")
    @RequiresPermissions("report:tmbasshift:info")
    public R info(@PathVariable("tmBasShiftId") Integer tmBasShiftId){
        TmBasShiftEntity tmBasShift = shiftService.getById(tmBasShiftId);

        return R.ok().put("tmBasShift", tmBasShift);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("report:tmbasshift:save")
    public R save(@RequestBody TmBasShiftEntity tmBasShift){
        shiftService.save(tmBasShift);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("report:tmbasshift:update")
    public R update(@RequestBody TmBasShiftEntity tmBasShift){
        ValidatorUtils.validateEntity(tmBasShift);
        shiftService.updateById(tmBasShift);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("report:tmbasshift:delete")
    public R delete(@RequestBody Integer[] tmBasShiftIds){
        shiftService.removeByIds(Arrays.asList(tmBasShiftIds));

        return R.ok();
    }

}
