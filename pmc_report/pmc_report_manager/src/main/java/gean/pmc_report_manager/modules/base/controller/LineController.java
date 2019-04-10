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
import gean.pmc_report_manager.modules.base.entity.TmBasLineEntity;
import gean.pmc_report_manager.modules.base.service.TmBasLineService;



/**
 * 产线基础信息表
 *
 * @author Jason
 * @email xxxxx@gmail.com
 * @date 2019-03-22 09:53:33
 */
@RestController
@RequestMapping("/modules/report/area")
public class LineController {
    @Autowired
    private TmBasLineService tmBasLineService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("report:tmbasline:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = tmBasLineService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{tmBasLineId}")
    @RequiresPermissions("report:tmbasline:info")
    public R info(@PathVariable("tmBasLineId") Integer tmBasLineId){
        TmBasLineEntity tmBasLine = tmBasLineService.getById(tmBasLineId);

        return R.ok().put("tmBasLine", tmBasLine);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("report:tmbasline:save")
    public R save(@RequestBody TmBasLineEntity tmBasLine){
        tmBasLineService.save(tmBasLine);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("report:tmbasline:update")
    public R update(@RequestBody TmBasLineEntity tmBasLine){
        ValidatorUtils.validateEntity(tmBasLine);
        tmBasLineService.updateById(tmBasLine);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("report:tmbasline:delete")
    public R delete(@RequestBody Integer[] tmBasLineIds){
        tmBasLineService.removeByIds(Arrays.asList(tmBasLineIds));

        return R.ok();
    }
    
    /**
     * 根据shop查询产线
     */
    @RequestMapping("/findArea")
    public R queryLine(String shopNo) {
    	List<TmBasLineEntity> list = tmBasLineService.queryLine(shopNo);
    	
    	return R.ok().put("areaList", list);
    }

}
