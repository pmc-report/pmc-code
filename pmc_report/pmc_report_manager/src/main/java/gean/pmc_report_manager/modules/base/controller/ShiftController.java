package gean.pmc_report_manager.modules.base.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gean.pmc_report_common.common.utils.R;
import gean.pmc_report_manager.modules.base.service.TmBasShiftService;



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
    	
    	List<String> shiftList=shiftService.queryShift();
    	
		return R.ok().put("shiftList", shiftList);
    }
    
}
