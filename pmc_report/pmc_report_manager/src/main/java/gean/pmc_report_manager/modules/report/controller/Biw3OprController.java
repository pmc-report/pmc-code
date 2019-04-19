package gean.pmc_report_manager.modules.report.controller;

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
import gean.pmc_report_manager.modules.report.entity.TaBiw3OprEntity;
import gean.pmc_report_manager.modules.report.service.TaBiw3OprService;
import gean.pmc_report_manager.modules.report.vo.AreaOprVo;



/**
 * ${comments}
 *
 * @author ''
 * @email xxxxx@gmail.com
 * @date 2019-04-10 13:54:04
 */
@RestController
@RequestMapping("report/opr")
public class Biw3OprController {
    @Autowired
    private TaBiw3OprService oprService;
       
    
    /**
     * 表格
     */
    @RequestMapping("/list")
    @RequiresPermissions("report:biw3opr:list")
    public R queryList(@RequestParam Map<String, Object> params){
    	List<AreaOprVo> areaList = oprService.queryOprForArea(params);
        return R.ok().put("areaList", areaList).put("zoneList", areaList.get(0).getZoneList());
    }

    /**
     * 列表
     */
    @RequestMapping("/list备用")
    @RequiresPermissions("report:tabiw3opr:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = oprService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{taBiw3OprId}")
    @RequiresPermissions("report:tabiw3opr:info")
    public R info(@PathVariable("taBiw3OprId") Integer taBiw3OprId){
        TaBiw3OprEntity taBiw3Opr = oprService.getById(taBiw3OprId);

        return R.ok().put("taBiw3Opr", taBiw3Opr);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("report:tabiw3opr:save")
    public R save(@RequestBody TaBiw3OprEntity taBiw3Opr){
        oprService.save(taBiw3Opr);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("report:tabiw3opr:update")
    public R update(@RequestBody TaBiw3OprEntity taBiw3Opr){
        ValidatorUtils.validateEntity(taBiw3Opr);
        oprService.updateById(taBiw3Opr);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("report:tabiw3opr:delete")
    public R delete(@RequestBody Integer[] taBiw3OprIds){
        oprService.removeByIds(Arrays.asList(taBiw3OprIds));

        return R.ok();
    }

}
