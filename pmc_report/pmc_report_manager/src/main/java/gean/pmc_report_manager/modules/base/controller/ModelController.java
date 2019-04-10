package gean.pmc_report_manager.modules.base.controller;


import java.util.Arrays;
import java.util.List;
import java.util.Map;

import gean.pmc_report_common.common.validator.ValidatorUtils;
import gean.pmc_report_manager.modules.base.entity.TmBasModelEntity;
import gean.pmc_report_manager.modules.base.service.TmBasModelService;
import gean.pmc_report_manager.modules.sys.entity.SysDictEntity;
import gean.pmc_report_manager.modules.sys.service.SysDictService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_common.common.utils.R;



/**
 *
 * @author Jason
 * @email xxxxx@gmail.com
 * @date 2019-04-03 14:00:04
 */
@RestController
@RequestMapping("/modules/report/model")
public class ModelController {
	
    @Autowired
    private TmBasModelService tmBasModelService;
    
    @Autowired
    private SysDictService sysDictService;
    
    
    @RequestMapping("/findJobId")
	public List<TmBasModelEntity> findJobI(@RequestParam Map<String, Object> params){
		List<TmBasModelEntity>jobIdList=tmBasModelService.queryJobId(params);
		return jobIdList;
	}
    
    /**
     * 根据type查询value
     */
    @RequestMapping("/getType/{param}")
    public R getType(@PathVariable("param") String param){
        List<SysDictEntity> dict = sysDictService.getType(param);

        return R.ok().put("dict", dict);
    }

	
  
    /**
     * 列表
     * Test
     */
    @RequestMapping("/list")
    @RequiresPermissions("report:tmbasmodel:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = tmBasModelService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{tmBasModelId}")
    @RequiresPermissions("report:tmbasmodel:info")
    public R info(@PathVariable("tmBasModelId") Integer tmBasModelId){
        TmBasModelEntity tmBasModel = tmBasModelService.getById(tmBasModelId);

        return R.ok().put("tmBasModel", tmBasModel);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("report:tmbasmodel:save")
    public R save(@RequestBody TmBasModelEntity tmBasModel){
        tmBasModelService.save(tmBasModel);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("report:tmbasmodel:update")
    public R update(@RequestBody TmBasModelEntity tmBasModel){
        ValidatorUtils.validateEntity(tmBasModel);
        tmBasModelService.updateById(tmBasModel);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("report:tmbasmodel:delete")
    public R delete(@RequestBody Integer[] tmBasModelIds){
        tmBasModelService.removeByIds(Arrays.asList(tmBasModelIds));

        return R.ok();
    }

}
