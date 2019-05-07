package gean.pmc_report_manager.modules.report.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gean.pmc_report_common.common.utils.R;
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
        return R.ok().put("area", areaList).put("zoneList", areaList.get(0).getZoneList());
    }

}
