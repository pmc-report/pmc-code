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
import gean.pmc_report_manager.modules.base.service.AtPmcMasterdataConfigService;
import gean.pmc_report_manager.modules.base.service.TmBasZoneService;
import gean.pmc_report_manager.modules.report.vo.MasterDataVo;
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
    private AtPmcMasterdataConfigService masterService;
    
   
    /**
     * 根据产线查工位
     */
    @RequestMapping("/findZone")
    public R queryZone(@RequestParam Map<String,Object> params) {
    	List<MasterDataVo> list = masterService.queryMasterDataZone(params);
    	
    	return R.ok().put("zoneList", list);
    }
    
    @RequestMapping("/findStation")
    public R queryStation(@RequestParam Map<String, Object> params) {
    	List<MasterDataVo> list = masterService.queryMasterDataStation(params);
    	
    	return R.ok().put("stationList", list);
    }
    
    @RequestMapping("/findEqu")
    public R queryEqu(@RequestParam Map<String, Object> params) {
    	List<MasterDataVo> list = masterService.queryMasterDataEquipment(params);
    	
    	return R.ok().put("equipmentList", list);
    }
    
}
