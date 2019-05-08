package gean.pmc_report_manager.modules.report.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gean.pmc_report_common.common.utils.R;
import gean.pmc_report_manager.common.utils.JasperExportUtils;
import gean.pmc_report_manager.modules.report.service.TaBiw3OprService;
import gean.pmc_report_manager.modules.report.vo.AreaOprVo;
import gean.pmc_report_manager.modules.report.vo.ZoneOprVo;



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

    /**
     * 导出测试
     * @param request
     * @param response
     * @param params
     */
    @RequestMapping("/exportOpr")
    public void exportOpr(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String,Object> params) {
    	/*List<ZoneOprVo> list = new ArrayList<ZoneOprVo>();
    	ZoneOprVo oprVo = new ZoneOprVo();
    	oprVo.setZone("Z01");
    	list.add(oprVo);
    	list.add(oprVo);*/
    	List<AreaOprVo> list = new ArrayList<AreaOprVo>();
    	AreaOprVo areaVo = new AreaOprVo();
    	areaVo.setArea("Undenbody");
    	areaVo.setEquipmentOpr(2.00f);
    	areaVo.setProductionOpr(3.00f);
    	areaVo.setZone("Z02");
    	list.add(areaVo);
    	list.add(areaVo);
    	InputStream is = this.getClass().getResourceAsStream("exportModel/OPR_Map.jasper");//获取同包下模版文件
    	try {
    		JasperExportUtils.export(list, "word", is, request, response);
		} catch (Exception e) {
			e.getMessage();
		}
    	
    }
}
