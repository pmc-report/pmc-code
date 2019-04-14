package gean.pmc_report_manager.modules.base.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import gean.pmc_report_common.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gean.pmc_report_manager.modules.base.entity.TmBasZoneEntity;
import gean.pmc_report_manager.modules.base.service.TmBasZoneService;
import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_common.common.utils.R;



/**
 * ${comments}
 *
 * @author ''
 * @email xxxxx@gmail.com
 * @date 2019-04-13 23:24:54
 */
@RestController
@RequestMapping("/modules/report/zone")
public class ZoneController {
    @Autowired
    private TmBasZoneService zoneService;
    
    
    /**
     * 根据产线查工位
     */
    @RequestMapping("/findZone")
    public R queryUloc(String lineNo) {
    	List<TmBasZoneEntity> list = zoneService.queryZone(lineNo);
    	
    	return R.ok().put("zoneList", list);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("report:tmbaszone:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = zoneService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{tmBasZoneId}")
    @RequiresPermissions("report:tmbaszone:info")
    public R info(@PathVariable("tmBasZoneId") Integer tmBasZoneId){
        TmBasZoneEntity tmBasZone = zoneService.getById(tmBasZoneId);

        return R.ok().put("tmBasZone", tmBasZone);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("report:tmbaszone:save")
    public R save(@RequestBody TmBasZoneEntity tmBasZone){
    	zoneService.save(tmBasZone);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("report:tmbaszone:update")
    public R update(@RequestBody TmBasZoneEntity tmBasZone){
        ValidatorUtils.validateEntity(tmBasZone);
        zoneService.updateById(tmBasZone);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("report:tmbaszone:delete")
    public R delete(@RequestBody Integer[] tmBasZoneIds){
    	zoneService.removeByIds(Arrays.asList(tmBasZoneIds));

        return R.ok();
    }

}
