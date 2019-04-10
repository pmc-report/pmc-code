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
import gean.pmc_report_manager.modules.base.entity.TmBasWorkshopEntity;
import gean.pmc_report_manager.modules.base.service.TmBasWorkshopService;



/**
 * 车间基本信息
 *
 * @author Jason
 * @email xxxxx@gmail.com
 * @date 2019-03-19 16:45:21
 */
@RestController
@RequestMapping("/modules/report/shop")
public class WorkshopController {
    @Autowired
    private TmBasWorkshopService workshopService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("report:shop:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = workshopService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{shopId}")
    @RequiresPermissions("report:shop:info")
    public R info(@PathVariable("shopId") Integer tmBasWorkshopId){
        TmBasWorkshopEntity tmBasWorkshop = workshopService.getById(tmBasWorkshopId);

        return R.ok().put("tmBasWorkshop", tmBasWorkshop);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("report:shop:save")
    public R save(@RequestBody TmBasWorkshopEntity tmBasWorkshop){
        workshopService.save(tmBasWorkshop);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("report:shop:update")
    public R update(@RequestBody TmBasWorkshopEntity tmBasWorkshop){
        ValidatorUtils.validateEntity(tmBasWorkshop);
        workshopService.updateById(tmBasWorkshop);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("report:shop:delete")
    public R delete(@RequestBody Integer[] tmBasWorkshopIds){
        workshopService.removeByIds(Arrays.asList(tmBasWorkshopIds));

        return R.ok();
    }
    
    /**
     * 初始化工厂下拉框
     */
    @RequestMapping("/findAllShops")
    public R queryAll() {
    	List<TmBasWorkshopEntity> list = workshopService.queryAllShop();
    	return R.ok().put("shopList", list);
    }

}
