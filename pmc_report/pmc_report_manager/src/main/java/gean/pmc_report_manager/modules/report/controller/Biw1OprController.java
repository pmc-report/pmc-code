package gean.pmc_report_manager.modules.report.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysql.fabric.xmlrpc.base.Array;

import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_common.common.utils.R;
import gean.pmc_report_common.common.validator.ValidatorUtils;
import gean.pmc_report_manager.modules.report.entity.PmcOprEntity;
import gean.pmc_report_manager.modules.report.service.PmcOprService;

@RestController
@RequestMapping("report/pmcopr")
public class Biw1OprController {
	
	@Autowired
    private PmcOprService pmcOprService;
	
	/**
	 * 图表
	 * @param parmas
	 * @return
	 */
	@RequestMapping("/biw1OprImg")
	public R queryEcharts(@RequestParam Map<String, Object> params) { 
//    	System.out.println(params.get("shop"));
    	List<PmcOprEntity> oprEchartslist = pmcOprService.queryEcharts(params);
    	Map<String, Object> resule = new HashMap<String,Object>();
    	List<Integer> weekDayList = new ArrayList<Integer>();
    	List<Float> pOprList = new ArrayList<Float>();
    	List<Float> tarOprList = new ArrayList<Float>();
    	List<Integer> productionVolumeList = new ArrayList<Integer>();
    	for (PmcOprEntity pmcOprEntity : oprEchartslist) {
    		weekDayList.add(pmcOprEntity.getWorkingday());
    		pOprList.add(pmcOprEntity.getProductionOpr());
    		tarOprList.add(pmcOprEntity.getTarOpr());
    		productionVolumeList.add(pmcOprEntity.getProductionVolume());
		}
    	resule.put("weekDay", weekDayList);
    	resule.put("pOpr", pOprList);
     	resule.put("tarOpr", tarOprList);
    	resule.put("productionVolume", productionVolumeList);
    	return R.ok().put("resule", resule);
    }
	
    /**
     * 列表
     */
	@RequestMapping("/list")
    //@RequiresPermissions("report:pmcopr:list")
    public R oprReport(@RequestParam Map<String,Object> params) {
    	PageUtils page = pmcOprService.queryOprReport(params);
		return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{shop}")
    @RequiresPermissions("report:pmcopr:info")
    public R info(@PathVariable("shop") String shop){
        PmcOprEntity pmcOpr = pmcOprService.getById(shop);

        return R.ok().put("pmcOpr", pmcOpr);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("report:pmcopr:save")
    public R save(@RequestBody PmcOprEntity pmcOpr){
        pmcOprService.save(pmcOpr);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("report:pmcopr:update")
    public R update(@RequestBody PmcOprEntity pmcOpr){
        ValidatorUtils.validateEntity(pmcOpr);
        pmcOprService.updateById(pmcOpr);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("report:pmcopr:delete")
    public R delete(@RequestBody String[] shops){
        pmcOprService.removeByIds(Arrays.asList(shops));

        return R.ok();
    }
}
