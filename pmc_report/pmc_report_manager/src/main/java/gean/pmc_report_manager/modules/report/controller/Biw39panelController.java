package gean.pmc_report_manager.modules.report.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import freemarker.template.Configuration;
import freemarker.template.Template;
import gean.pmc_report_common.common.utils.DateUtils;
import gean.pmc_report_common.common.utils.R;
import gean.pmc_report_common.common.utils.StringUtils;
import gean.pmc_report_manager.common.utils.JasperExportUtils;
import gean.pmc_report_manager.modules.report.service.TaBiw39panelService;
import gean.pmc_report_manager.modules.report.vo.PanelVo;
import gean.pmc_report_manager.modules.report.vo.PanelVoExport;



/**
 * ${comments}
 *
 * @author Jason
 * @email xxxxx@gmail.com
 * @date 2019-04-13 14:15:33
 */
@RestController
@RequestMapping("modules/report/panel")
public class Biw39panelController {
    @Autowired
    private TaBiw39panelService taBiw39panelService;
    
    Map<String,Object> resultMap;
    
    
    @RequestMapping("/listPrePanel")
    public Object listPrePanel(@RequestParam Map<String,Object> params) {
    	List<PanelVo> list = taBiw39panelService.queryTop10DownTime(params);
        return R.ok().put("preDownTimeList", list);
    }
    
    @RequestMapping("/listCurrPanel")
    public Object listCurrPanel(@RequestParam Map<String,Object> params) {
    	List<PanelVo> list = taBiw39panelService.queryTop10Occurrence(params);
        return R.ok().put("preOccurrenceList", list);
    }

    /**
     * 图表
     */
    @RequestMapping("/echarts")
    public R list(@RequestParam Map<String, Object> params){
       resultMap = new HashMap<>();
       List<PanelVo> list = taBiw39panelService.queryEchart(params);
       for(PanelVo vo : list) {
    	   if(vo.getTargetTav()>0) {
    		   resultMap.put("Target TA", vo.getTargetTav());
    		   break;
    	   }
       }
        return R.ok().put("list", list);
    }

    
    /**
     * 测试用
     */
    @RequestMapping("/panelTest")
    public R panelTest() {
    	return R.ok();
    }
    
    /**
     * 导出echarts图表
     * @param request
     * @param response
     * @param params
     * @throws Exception
     */
    @RequestMapping("/report")
    public void index(HttpServletRequest request,HttpServletResponse response,
			@RequestParam Map<String, Object> params) throws Exception {
    	
		// String realPath=request.getServletContext().getRealPath("pmc_report_manager"); //得到项目的绝对路径
		// System.out.println(realPath);
		// String tagertFile = "d:/Test/word.doc"; //word模板的目录

		String tagertFile = "src/main/java/gean/pmc_report_manager/modules/report/controller/exportModel/word.doc";
		// String sourceFile = "d:/Test"; //word生成的目标目录
		Configuration configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
		// configuration.setDirectoryForTemplateLoading(new File(sourceFile));   //根据绝对路径加载文件
		//System.out.println(this.getClass());
		configuration.setClassForTemplateLoading(this.getClass(), "exportModel"); // 获取需要加载文件的相对路径 只到上级包
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("export/word.doc");
		File f = new File(tagertFile);
		FileUtils.copyInputStreamToFile(is, f);
		Writer writer = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
		Template template = configuration.getTemplate("word.xml");
		Map<String, String> map = new HashMap<>();
		DecimalFormat df = new DecimalFormat("##0.00");
		//组装数据
		for (String str : params.keySet()) {
			map.put(str, params.get(str) == null ? " " : (String)params.get(str));
//			System.out.println(str+" : "+map.get(str));
		}
		map.put("targetTA", df.format(resultMap.get("Target TA")));
		map.put("createDates", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
		template.process(map, writer);
		writer.close();

		File file = new File(tagertFile);
		if (file.exists()) {
			response.setContentType("application/force-download");// 设置强制下载不打开            
			response.addHeader("Content-Disposition", "attachment;fileName= 9Panel报表.doc");
			byte[] buffer = new byte[10240];
			FileInputStream fis = null;
			BufferedInputStream bis = null;
			try {
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);
				OutputStream outputStream = response.getOutputStream();
				int i = bis.read(buffer);
				while (i != -1) {
					outputStream.write(buffer, 0, i);
					i = bis.read(buffer);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (bis != null) {
					try {
						bis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

    
    @RequestMapping("/report/excel")
    public void exportExcel(HttpServletRequest request,HttpServletResponse response,
			@RequestParam Map<String, Object> params) throws Exception {
    	
    	//创建VO list 
    	List<PanelVoExport> voList = generate9PanelVo(params);
    	try {
    		InputStream is = this.getClass().getResourceAsStream("exportModel/9panel.jasper");//获取同包下模版文件
        	String exportName = "9Panel报表";
    		JasperExportUtils.export(voList, "excel", is, request, response,exportName);
		} catch (Exception e) {
			e.getMessage();
		}
    }
    
    private List<PanelVoExport> generate9PanelVo(Map<String, Object> params){
    	
    	DecimalFormat df = new DecimalFormat("##0.00");
    	//创建VO list 
    	List<PanelVoExport> voList = new ArrayList<>();
    	PanelVoExport vo = new PanelVoExport();
    	vo.setShop(!StringUtils.isNotBlank((String)params.get("shop")) ? "" : (String)params.get("shop"));
    	vo.setArea(!StringUtils.isNotBlank((String)params.get("area")) ? "ALL" : (String)params.get("area"));
    	vo.setZone(!StringUtils.isNotBlank((String)params.get("zone")) ? "ALL" : (String)params.get("zone"));
    	vo.setJobId(!StringUtils.isNotBlank((String)params.get("jobId")) ? "ALL" : (String)params.get("jobId"));
    	vo.setShift(!StringUtils.isNotBlank((String)params.get("shift")) ? "ALL" : (String)params.get("shift"));
    	vo.setFromWeek(!StringUtils.isNotBlank((String)params.get("fromWeek")) ? "" : (String)params.get("fromWeek"));
    	vo.setToWeek(!StringUtils.isNotBlank((String)params.get("toWeek")) ? "" : (String)params.get("toWeek"));
    	vo.setFromDates(!StringUtils.isNotBlank((String)params.get("fromDates")) ? "" : (String)params.get("fromDates"));
    	vo.setToDates(!StringUtils.isNotBlank((String)params.get("toDates")) ? "" : (String)params.get("toDates"));
    	vo.setCreateDates(!StringUtils.isNotBlank((String)params.get("createDates")) ? "" : (String)params.get("createDates"));
    	vo.setFromDates_2(!StringUtils.isNotBlank((String)params.get("fromDates")) ? "" : (String)params.get("fromDates"));
    	vo.setTargetTA(df.format(resultMap.get("Target TA")));

    	vo.setEcharepxport(!StringUtils.isNotBlank((String)params.get("echarepxport"))?"":(String)params.get("echarepxport"));
    	vo.setEcharepxport1(!StringUtils.isNotBlank((String)params.get("echarepxport1"))?"":(String)params.get("echarepxport1"));
    	vo.setEcharepxport2(!StringUtils.isNotBlank((String)params.get("echarepxport2"))?"":(String)params.get("echarepxport2"));
    	vo.setdTime_0(!StringUtils.isNotBlank((String)params.get("dTime_0"))?"":(String)params.get("dTime_0"));
    	vo.setdTime_1(!StringUtils.isNotBlank((String)params.get("dTime_1"))?"":(String)params.get("dTime_1"));
    	vo.setdTime_2(!StringUtils.isNotBlank((String)params.get("dTime_2"))?"":(String)params.get("dTime_2"));
    	vo.setdTime_3(!StringUtils.isNotBlank((String)params.get("dTime_3"))?"":(String)params.get("dTime_3"));
    	vo.setdTime_4(!StringUtils.isNotBlank((String)params.get("dTime_4"))?"":(String)params.get("dTime_4"));
    	vo.setdTime_5(!StringUtils.isNotBlank((String)params.get("dTime_5"))?"":(String)params.get("dTime_5"));
    	vo.setdTime_6(!StringUtils.isNotBlank((String)params.get("dTime_6"))?"":(String)params.get("dTime_6"));
    	vo.setdTime_7(!StringUtils.isNotBlank((String)params.get("dTime_7"))?"":(String)params.get("dTime_7"));
    	vo.setdTime_8(!StringUtils.isNotBlank((String)params.get("dTime_8"))?"":(String)params.get("dTime_8"));	
    	vo.setdTime_12(!StringUtils.isNotBlank((String)params.get("dTime_12"))?"":(String)params.get("dTime_12"));
    	vo.setdTime_13(!StringUtils.isNotBlank((String)params.get("dTime_13"))?"":(String)params.get("dTime_13"));
    	vo.setdTime_14(!StringUtils.isNotBlank((String)params.get("dTime_14"))?"":(String)params.get("dTime_14"));
    	vo.setdTime_15(!StringUtils.isNotBlank((String)params.get("dTime_15"))?"":(String)params.get("dTime_15"));
    	vo.setdTime_16(!StringUtils.isNotBlank((String)params.get("dTime_16"))?"":(String)params.get("dTime_16"));
    	
    	vo.setdTime_17(!StringUtils.isNotBlank((String)params.get("dTime_17"))?"":(String)params.get("dTime_17"));
    	vo.setdTime_18(!StringUtils.isNotBlank((String)params.get("dTime_18"))?"":(String)params.get("dTime_18"));
    	vo.setdTime_19(!StringUtils.isNotBlank((String)params.get("dTime_19"))?"":(String)params.get("dTime_19"));
    	vo.setdTime_20(!StringUtils.isNotBlank((String)params.get("dTime_20"))?"":(String)params.get("dTime_20"));
    	vo.setdTime_21(!StringUtils.isNotBlank((String)params.get("dTime_21"))?"":(String)params.get("dTime_21"));
    	vo.setdTime_22(!StringUtils.isNotBlank((String)params.get("dTime_22"))?"":(String)params.get("dTime_22"));
    	vo.setdTime_23(!StringUtils.isNotBlank((String)params.get("dTime_23"))?"":(String)params.get("dTime_23"));
    	vo.setdTime_24(!StringUtils.isNotBlank((String)params.get("dTime_24"))?"":(String)params.get("dTime_24"));
    	
    	//vo.setdTime_25(!StringUtils.isNotBlank((String)params.get("dTime_25"))?"":(String)params.get("dTime_25"));
    	//vo.setdTime_26(!StringUtils.isNotBlank((String)params.get("dTime_26"))?"":(String)params.get("dTime_26"));
    	//vo.setdTime_27(!StringUtils.isNotBlank((String)params.get("dTime_27"))?"":(String)params.get("dTime_27"));
    	//vo.setdTime_28(!StringUtils.isNotBlank((String)params.get("dTime_28"))?"":(String)params.get("dTime_28"));
    	vo.setdTime_29(!StringUtils.isNotBlank((String)params.get("dTime_29"))?"":(String)params.get("dTime_29"));
    	vo.setdTime_30(!StringUtils.isNotBlank((String)params.get("dTime_30"))?"":(String)params.get("dTime_30"));
     	vo.setdTime_31(!StringUtils.isNotBlank((String)params.get("dTime_31"))?"":(String)params.get("dTime_31"));
    	vo.setdTime_32(!StringUtils.isNotBlank((String)params.get("dTime_32"))?"":(String)params.get("dTime_32"));
    	vo.setdTime_33(!StringUtils.isNotBlank((String)params.get("dTime_33"))?"":(String)params.get("dTime_33"));
    	//vo.setdTime_34(!StringUtils.isNotBlank((String)params.get("dTime_34"))?"":(String)params.get("dTime_34"));
    	//vo.setdTime_35(!StringUtils.isNotBlank((String)params.get("dTime_35"))?"":(String)params.get("dTime_35"));
    	//vo.setdTime_36(!StringUtils.isNotBlank((String)params.get("dTime_36"))?"":(String)params.get("dTime_36"));
    	
    	voList.add(vo);
    	
		return voList;
    	
    }
    	
	
}
