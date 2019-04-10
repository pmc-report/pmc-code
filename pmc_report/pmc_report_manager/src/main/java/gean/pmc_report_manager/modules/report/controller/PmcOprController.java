package gean.pmc_report_manager.modules.report.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

import gean.pmc_report_manager.modules.report.entity.PmcOprEntity;
import gean.pmc_report_manager.modules.report.service.PmcOprService;
import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_common.common.utils.R;



/**
 * ${comments}
 *
 * @author Jason
 * @email xxxxx@gmail.com
 * @date 2019-03-14 13:22:17
 */
@RestController
@RequestMapping("report/pmcopr")
public class PmcOprController {
    @Autowired
    private PmcOprService pmcOprService;

    
    
    
    
    @RequestMapping("/oprTest")
    public Map<String, Object[]> echartsT(){
    	List<String> date=new ArrayList<>();
    	date.add("2/4/2019");
    	date.add("3/4/2019");
    	date.add("4/4/2019");
    	date.add("5/4/2019");
    	date.add("6/4/2019");
    	
    	List<String> E=new ArrayList<>();
    	E.add("84");
    	E.add("44");
    	E.add("54");
    	E.add("74");
    	E.add("68");
    	
    	
    	
    	
    	
    	Map<String, Object[]> data=new HashMap<String,Object[]>();
    	data.put("date",new Object[] {date} );
    	data.put("E",new Object[] {E} );
    	data.put("P",new Object[] {"57","43","86","71","43"} );
    	data.put("O",new Object[] {"27","86","37","54","29"} );
    	
    	
    	 for(String key : data.keySet()){
 	        System.out.println("我是key键:" + key);
 	        System.out.println("我是value值:" + data.get(key));
 	               //接着进行取list值
 	               List<String> lisMap = new ArrayList<String>();
 	     
 	               for (int i = 0 ; i< lisMap.size() ; i++){
 	                   System.out.println("map中取出List中的value:["+key+ "]的第" + "[" +(i+1)+"]个值："+ lisMap.get(i).toString());
 	               }
 	               
 	             	               
 	    }
 	
    	 
    	 
    	
		return data;
    	
    }
    
    
   
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * opr列表
     */
    @RequestMapping("/oprTable")
    @RequiresPermissions("report:pmcopr:oprTable")
    public R oprReport(@RequestParam Map<String, Object> params) {
    	PageUtils page = pmcOprService.queryOprReport(params);
    	
		return R.ok().put("page", page);
    }
    

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("report:pmcopr:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = pmcOprService.queryPage(params);

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
