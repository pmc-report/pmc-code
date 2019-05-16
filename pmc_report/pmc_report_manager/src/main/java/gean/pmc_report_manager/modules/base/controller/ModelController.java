package gean.pmc_report_manager.modules.base.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gean.pmc_report_common.common.utils.R;
import gean.pmc_report_manager.modules.base.service.TmBasModelService;
import gean.pmc_report_manager.modules.sys.entity.SysDictEntity;
import gean.pmc_report_manager.modules.sys.service.SysDictService;



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
	public R findJobI(@RequestParam Map<String, Object> params){
		List<String> jobIdList=tmBasModelService.queryJobId(params);
		return new R().put("modelList", jobIdList);
	}
    
    /**
     * 根据type查询value
     */
    @RequestMapping("/getType/{param}")
    public R getType(@PathVariable("param") String param){
        List<SysDictEntity> dict = sysDictService.getType(param);

        return R.ok().put("dict", dict);
    }

}
