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
       DecimalFormat df = new DecimalFormat("##0.00");
       List<PanelVo> list = taBiw39panelService.queryEchart(params);
       for(PanelVo vo : list) {
    	   try {
    		   if(vo.getTargetTav()>0) {
        		   resultMap.put("Target_TA", df.format(vo.getTargetTav()));
        		   break;
        	   }
			
			} catch (Exception e) {
				resultMap.put("Target_TA", "0.0");
				e.printStackTrace();
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
		configuration.setClassicCompatible(true);
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
		map.put("targetTA", (String)resultMap.get("Target_TA"));
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
				resultMap.clear();
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
    		resultMap.clear();
    		params.clear();
		} catch (Exception e) {
			resultMap.clear();
			params.clear();
			e.getMessage();
		}
    }
    
    private List<PanelVoExport> generate9PanelVo(Map<String, Object> params){
    	
    	DecimalFormat df = new DecimalFormat("##0.00");
    	
    	String blankPerato = "iVBORw0KGgoAAAANSUhEUgAAAOoAAAA4CAYAAADzYmRqAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAAEnQAABJ0Ad5mH3gAAACzSURBVHhe7dMBDQAwEAOh+Tfd2fhLwANvwHmiQoCoECAqBIgKAaJCgKgQICoEiAoBokKAqBAgKgSICgGiQoCoECAqBIgKAaJCgKgQICoEiAoBokKAqBAgKgSICgGiQoCoECAqBIgKAaJCgKgQICoEiAoBokKAqBAgKgSICgGiQoCoECAqBIgKAaJCgKgQICoEiAoBokKAqBAgKgSICgGiQoCoECAqBIgKAaJCgKgQICqct30daf8m93wkrwAAAABJRU5ErkJggg==";
    	String blankStatus = "iVBORw0KGgoAAAANSUhEUgAAADwAAAA6CAYAAADspTpvAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAAEnQAABJ0Ad5mH3gAAABUSURBVGhD7c8BAQAwDMOg+zfd+2DBAW/HFNYV1hXWFdYV1hXWFdYV1hXWFdYV1hXWFdYV1hXWFdYV1hXWFdYV1hXWFdYV1hXWFdYV1hXWFdYdC28fRQUsy31WIO8AAAAASUVORK5CYII=";
    	//创建VO list 
    	List<PanelVoExport> voList = new ArrayList<>();
    	PanelVoExport vo = new PanelVoExport();
    	vo.setShop(!StringUtils.isNotBlank((String)params.get("shop")) ? "" : (String)params.get("shop"));
    	vo.setArea(!StringUtils.isNotBlank((String)params.get("area")) ? "ALL" : (String)params.get("area"));
    	vo.setZone(!StringUtils.isNotBlank((String)params.get("zone")) ? "ALL" : (String)params.get("zone"));
    	vo.setJobId(!StringUtils.isNotBlank((String)params.get("jobId")) ? "ALL" : (String)params.get("jobId"));
    	vo.setShift(!StringUtils.isNotBlank((String)params.get("shift")) ? "ALL" : (String)params.get("shift"));
    	vo.setFromWeek(!StringUtils.isNotBlank((String)params.get("formWeek")) ? "" : (String)params.get("formWeek"));
    	vo.setToWeek(!StringUtils.isNotBlank((String)params.get("toWeek")) ? "" : (String)params.get("toWeek"));
    	vo.setFromDates(!StringUtils.isNotBlank((String)params.get("fromDates")) ? "" : (String)params.get("fromDates"));
    	vo.setToDates(!StringUtils.isNotBlank((String)params.get("toDates")) ? "" : (String)params.get("toDates"));
    	vo.setCreateDates(DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
    	vo.setTargetTA((String)resultMap.get("Target_TA"));
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
    	vo.setdTime_7(!StringUtils.isNotBlank((String)params.get("dTime_7"))?blankPerato:(String)params.get("dTime_7"));
    	vo.setdTime_8(!StringUtils.isNotBlank((String)params.get("dTime_8"))?blankStatus:(String)params.get("dTime_8"));	
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
    	vo.setdTime_23(!StringUtils.isNotBlank((String)params.get("dTime_23"))?blankPerato:(String)params.get("dTime_23"));
    	vo.setdTime_24(!StringUtils.isNotBlank((String)params.get("dTime_24"))?blankStatus:(String)params.get("dTime_24"));
    	vo.setdTime_29(!StringUtils.isNotBlank((String)params.get("dTime_29"))?"":(String)params.get("dTime_29"));
    	vo.setdTime_30(!StringUtils.isNotBlank((String)params.get("dTime_30"))?"":(String)params.get("dTime_30"));
     	vo.setdTime_31(!StringUtils.isNotBlank((String)params.get("dTime_31"))?"":(String)params.get("dTime_31"));
    	vo.setdTime_32(!StringUtils.isNotBlank((String)params.get("dTime_32"))?"":(String)params.get("dTime_32"));
    	vo.setdTime_33(!StringUtils.isNotBlank((String)params.get("dTime_33"))?"":(String)params.get("dTime_33"));
    	
    	vo.setdTime_34(!StringUtils.isNotBlank((String)params.get("dTime_34"))?"":(String)params.get("dTime_34"));
    	vo.setdTime_35(!StringUtils.isNotBlank((String)params.get("dTime_35"))?"":(String)params.get("dTime_35"));
    	vo.setdTime_36(!StringUtils.isNotBlank((String)params.get("dTime_36"))?"":(String)params.get("dTime_36"));
    	vo.setdTime_37(!StringUtils.isNotBlank((String)params.get("dTime_37"))?"":(String)params.get("dTime_37"));
    	vo.setdTime_38(!StringUtils.isNotBlank((String)params.get("dTime_38"))?"":(String)params.get("dTime_38"));
    	vo.setdTime_39(!StringUtils.isNotBlank((String)params.get("dTime_39"))?"":(String)params.get("dTime_39"));
    	vo.setdTime_40(!StringUtils.isNotBlank((String)params.get("dTime_40"))?blankPerato:(String)params.get("dTime_40"));
     	vo.setdTime_41(!StringUtils.isNotBlank((String)params.get("dTime_41"))?blankStatus:(String)params.get("dTime_41"));
     	vo.setdTime_46(!StringUtils.isNotBlank((String)params.get("dTime_46"))?"":(String)params.get("dTime_46"));
    	vo.setdTime_47(!StringUtils.isNotBlank((String)params.get("dTime_47"))?"":(String)params.get("dTime_47"));
    	vo.setdTime_48(!StringUtils.isNotBlank((String)params.get("dTime_48"))?"":(String)params.get("dTime_48"));
    	vo.setdTime_49(!StringUtils.isNotBlank((String)params.get("dTime_49"))?"":(String)params.get("dTime_49"));
    	vo.setdTime_50(!StringUtils.isNotBlank((String)params.get("dTime_50"))?"":(String)params.get("dTime_50"));
    	
    	vo.setdTime_51(!StringUtils.isNotBlank((String)params.get("dTime_51"))?"":(String)params.get("dTime_51"));
    	vo.setdTime_52(!StringUtils.isNotBlank((String)params.get("dTime_52"))?"":(String)params.get("dTime_52"));
    	vo.setdTime_53(!StringUtils.isNotBlank((String)params.get("dTime_53"))?"":(String)params.get("dTime_53"));
    	vo.setdTime_54(!StringUtils.isNotBlank((String)params.get("dTime_54"))?"":(String)params.get("dTime_54"));
    	vo.setdTime_55(!StringUtils.isNotBlank((String)params.get("dTime_55"))?"":(String)params.get("dTime_55"));
    	vo.setdTime_56(!StringUtils.isNotBlank((String)params.get("dTime_56"))?"":(String)params.get("dTime_56"));
    	vo.setdTime_57(!StringUtils.isNotBlank((String)params.get("dTime_57"))?blankPerato:(String)params.get("dTime_57"));
     	vo.setdTime_58(!StringUtils.isNotBlank((String)params.get("dTime_58"))?blankStatus:(String)params.get("dTime_58"));
    	vo.setdTime_63(!StringUtils.isNotBlank((String)params.get("dTime_63"))?"":(String)params.get("dTime_63"));
    	vo.setdTime_64(!StringUtils.isNotBlank((String)params.get("dTime_64"))?"":(String)params.get("dTime_64"));
    	vo.setdTime_65(!StringUtils.isNotBlank((String)params.get("dTime_65"))?"":(String)params.get("dTime_65"));
    	vo.setdTime_66(!StringUtils.isNotBlank((String)params.get("dTime_66"))?"":(String)params.get("dTime_66"));
    	vo.setdTime_67(!StringUtils.isNotBlank((String)params.get("dTime_67"))?"":(String)params.get("dTime_67"));
    
    	vo.setdTime_68(!StringUtils.isNotBlank((String)params.get("dTime_68"))?"":(String)params.get("dTime_68"));
    	vo.setdTime_69(!StringUtils.isNotBlank((String)params.get("dTime_69"))?"":(String)params.get("dTime_69"));
    	vo.setdTime_70(!StringUtils.isNotBlank((String)params.get("dTime_70"))?"":(String)params.get("dTime_70"));
    	vo.setdTime_71(!StringUtils.isNotBlank((String)params.get("dTime_71"))?"":(String)params.get("dTime_71"));
    	vo.setdTime_72(!StringUtils.isNotBlank((String)params.get("dTime_72"))?"":(String)params.get("dTime_72"));
    	vo.setdTime_73(!StringUtils.isNotBlank((String)params.get("dTime_73"))?"":(String)params.get("dTime_73"));
    	vo.setdTime_74(!StringUtils.isNotBlank((String)params.get("dTime_74"))?blankPerato:(String)params.get("dTime_74"));
     	vo.setdTime_75(!StringUtils.isNotBlank((String)params.get("dTime_75"))?blankStatus:(String)params.get("dTime_75"));
    	vo.setdTime_80(!StringUtils.isNotBlank((String)params.get("dTime_80"))?"":(String)params.get("dTime_80"));
    	vo.setdTime_81(!StringUtils.isNotBlank((String)params.get("dTime_81"))?"":(String)params.get("dTime_81"));
    	vo.setdTime_82(!StringUtils.isNotBlank((String)params.get("dTime_82"))?"":(String)params.get("dTime_82"));
    	vo.setdTime_83(!StringUtils.isNotBlank((String)params.get("dTime_83"))?"":(String)params.get("dTime_83"));
    	vo.setdTime_84(!StringUtils.isNotBlank((String)params.get("dTime_84"))?"":(String)params.get("dTime_84"));
    
    	vo.setdTime_85(!StringUtils.isNotBlank((String)params.get("dTime_85"))?"":(String)params.get("dTime_85"));
    	vo.setdTime_86(!StringUtils.isNotBlank((String)params.get("dTime_86"))?"":(String)params.get("dTime_86"));
    	vo.setdTime_87(!StringUtils.isNotBlank((String)params.get("dTime_87"))?"":(String)params.get("dTime_87"));
    	vo.setdTime_88(!StringUtils.isNotBlank((String)params.get("dTime_88"))?"":(String)params.get("dTime_88"));
    	vo.setdTime_89(!StringUtils.isNotBlank((String)params.get("dTime_89"))?"":(String)params.get("dTime_89"));
    	vo.setdTime_90(!StringUtils.isNotBlank((String)params.get("dTime_90"))?"":(String)params.get("dTime_90"));
    	vo.setdTime_91(!StringUtils.isNotBlank((String)params.get("dTime_91"))?blankPerato:(String)params.get("dTime_91"));
     	vo.setdTime_92(!StringUtils.isNotBlank((String)params.get("dTime_92"))?blankStatus:(String)params.get("dTime_92"));
    	vo.setdTime_97(!StringUtils.isNotBlank((String)params.get("dTime_97"))?"":(String)params.get("dTime_97"));
    	vo.setdTime_98(!StringUtils.isNotBlank((String)params.get("dTime_98"))?"":(String)params.get("dTime_98"));
    	vo.setdTime_99(!StringUtils.isNotBlank((String)params.get("dTime_99"))?"":(String)params.get("dTime_99"));
    	vo.setdTime_100(!StringUtils.isNotBlank((String)params.get("dTime_100"))?"":(String)params.get("dTime_100"));
    	vo.setdTime_101(!StringUtils.isNotBlank((String)params.get("dTime_101"))?"":(String)params.get("dTime_101"));
    
    	vo.setdTime_102(!StringUtils.isNotBlank((String)params.get("dTime_102"))?"":(String)params.get("dTime_102"));
    	vo.setdTime_103(!StringUtils.isNotBlank((String)params.get("dTime_103"))?"":(String)params.get("dTime_103"));
    	vo.setdTime_104(!StringUtils.isNotBlank((String)params.get("dTime_104"))?"":(String)params.get("dTime_104"));
    	vo.setdTime_105(!StringUtils.isNotBlank((String)params.get("dTime_105"))?"":(String)params.get("dTime_105"));
    	vo.setdTime_106(!StringUtils.isNotBlank((String)params.get("dTime_106"))?"":(String)params.get("dTime_106"));
    	vo.setdTime_107(!StringUtils.isNotBlank((String)params.get("dTime_107"))?"":(String)params.get("dTime_107"));
    	vo.setdTime_108(!StringUtils.isNotBlank((String)params.get("dTime_108"))?blankPerato:(String)params.get("dTime_108"));
     	vo.setdTime_109(!StringUtils.isNotBlank((String)params.get("dTime_109"))?blankStatus:(String)params.get("dTime_109"));
    	vo.setdTime_114(!StringUtils.isNotBlank((String)params.get("dTime_114"))?"":(String)params.get("dTime_114"));
    	vo.setdTime_115(!StringUtils.isNotBlank((String)params.get("dTime_115"))?"":(String)params.get("dTime_115"));
    	vo.setdTime_116(!StringUtils.isNotBlank((String)params.get("dTime_116"))?"":(String)params.get("dTime_116"));
    	vo.setdTime_117(!StringUtils.isNotBlank((String)params.get("dTime_117"))?"":(String)params.get("dTime_117"));
    	vo.setdTime_118(!StringUtils.isNotBlank((String)params.get("dTime_118"))?"":(String)params.get("dTime_118"));
    
    	vo.setdTime_119(!StringUtils.isNotBlank((String)params.get("dTime_119"))?"":(String)params.get("dTime_119"));
    	vo.setdTime_120(!StringUtils.isNotBlank((String)params.get("dTime_120"))?"":(String)params.get("dTime_120"));
    	vo.setdTime_121(!StringUtils.isNotBlank((String)params.get("dTime_121"))?"":(String)params.get("dTime_121"));
    	vo.setdTime_122(!StringUtils.isNotBlank((String)params.get("dTime_122"))?"":(String)params.get("dTime_122"));
    	vo.setdTime_123(!StringUtils.isNotBlank((String)params.get("dTime_123"))?"":(String)params.get("dTime_123"));
    	vo.setdTime_124(!StringUtils.isNotBlank((String)params.get("dTime_124"))?"":(String)params.get("dTime_124"));
    	vo.setdTime_125(!StringUtils.isNotBlank((String)params.get("dTime_125"))?blankPerato:(String)params.get("dTime_125"));
     	vo.setdTime_126(!StringUtils.isNotBlank((String)params.get("dTime_126"))?blankStatus:(String)params.get("dTime_126"));
    	vo.setdTime_131(!StringUtils.isNotBlank((String)params.get("dTime_131"))?"":(String)params.get("dTime_131"));
    	vo.setdTime_132(!StringUtils.isNotBlank((String)params.get("dTime_132"))?"":(String)params.get("dTime_132"));
    	vo.setdTime_133(!StringUtils.isNotBlank((String)params.get("dTime_133"))?"":(String)params.get("dTime_133"));
    	vo.setdTime_134(!StringUtils.isNotBlank((String)params.get("dTime_134"))?"":(String)params.get("dTime_134"));
    	vo.setdTime_135(!StringUtils.isNotBlank((String)params.get("dTime_135"))?"":(String)params.get("dTime_135"));
    
    	vo.setdTime_136(!StringUtils.isNotBlank((String)params.get("dTime_136"))?"":(String)params.get("dTime_136"));
    	vo.setdTime_137(!StringUtils.isNotBlank((String)params.get("dTime_137"))?"":(String)params.get("dTime_137"));
    	vo.setdTime_138(!StringUtils.isNotBlank((String)params.get("dTime_138"))?"":(String)params.get("dTime_138"));
    	vo.setdTime_139(!StringUtils.isNotBlank((String)params.get("dTime_139"))?"":(String)params.get("dTime_139"));
    	vo.setdTime_140(!StringUtils.isNotBlank((String)params.get("dTime_140"))?"":(String)params.get("dTime_140"));
    	vo.setdTime_141(!StringUtils.isNotBlank((String)params.get("dTime_141"))?"":(String)params.get("dTime_141"));
    	vo.setdTime_142(!StringUtils.isNotBlank((String)params.get("dTime_142"))?blankPerato:(String)params.get("dTime_142"));
     	vo.setdTime_143(!StringUtils.isNotBlank((String)params.get("dTime_143"))?blankStatus:(String)params.get("dTime_143"));
    	vo.setdTime_148(!StringUtils.isNotBlank((String)params.get("dTime_148"))?"":(String)params.get("dTime_148"));
    	vo.setdTime_149(!StringUtils.isNotBlank((String)params.get("dTime_149"))?"":(String)params.get("dTime_149"));
    	vo.setdTime_150(!StringUtils.isNotBlank((String)params.get("dTime_150"))?"":(String)params.get("dTime_150"));
    	vo.setdTime_151(!StringUtils.isNotBlank((String)params.get("dTime_151"))?"":(String)params.get("dTime_151"));
    	vo.setdTime_152(!StringUtils.isNotBlank((String)params.get("dTime_152"))?"":(String)params.get("dTime_152"));
    
    	vo.setdTime_153(!StringUtils.isNotBlank((String)params.get("dTime_153"))?"":(String)params.get("dTime_153"));
    	vo.setdTime_154(!StringUtils.isNotBlank((String)params.get("dTime_154"))?"":(String)params.get("dTime_154"));
    	vo.setdTime_155(!StringUtils.isNotBlank((String)params.get("dTime_155"))?"":(String)params.get("dTime_155"));
    	vo.setdTime_156(!StringUtils.isNotBlank((String)params.get("dTime_156"))?"":(String)params.get("dTime_156"));
    	vo.setdTime_157(!StringUtils.isNotBlank((String)params.get("dTime_157"))?"":(String)params.get("dTime_157"));
    	vo.setdTime_158(!StringUtils.isNotBlank((String)params.get("dTime_158"))?"":(String)params.get("dTime_158"));
    	vo.setdTime_159(!StringUtils.isNotBlank((String)params.get("dTime_159"))?blankPerato:(String)params.get("dTime_159"));
     	vo.setdTime_160(!StringUtils.isNotBlank((String)params.get("dTime_160"))?blankStatus:(String)params.get("dTime_160"));
    	vo.setdTime_165(!StringUtils.isNotBlank((String)params.get("dTime_165"))?"":(String)params.get("dTime_165"));
    	vo.setdTime_166(!StringUtils.isNotBlank((String)params.get("dTime_166"))?"":(String)params.get("dTime_166"));
    	vo.setdTime_167(!StringUtils.isNotBlank((String)params.get("dTime_167"))?"":(String)params.get("dTime_167"));
    	vo.setdTime_168(!StringUtils.isNotBlank((String)params.get("dTime_168"))?"":(String)params.get("dTime_168"));
    	vo.setdTime_169(!StringUtils.isNotBlank((String)params.get("dTime_169"))?"":(String)params.get("dTime_169"));
    	
    	vo.setT10DTimeOld(!StringUtils.isNotBlank((String)params.get("t10DTimeOld"))?"":(String)params.get("t10DTimeOld"));
    	vo.setdTimeOldTol(!StringUtils.isNotBlank((String)params.get("dTimeOldTol"))?"":(String)params.get("dTimeOldTol"));
    	vo.setT10DTimeNew(!StringUtils.isNotBlank((String)params.get("t10DTimeNew"))?"":(String)params.get("t10DTimeNew"));
    	vo.setdTimeNewTol(!StringUtils.isNotBlank((String)params.get("dTimeNewTol"))?"":(String)params.get("dTimeNewTol"));
    	
    	vo.setOcc_0(!StringUtils.isNotBlank((String)params.get("occ_0"))?"":(String)params.get("occ_0"));
    	vo.setOcc_1(!StringUtils.isNotBlank((String)params.get("occ_1"))?"":(String)params.get("occ_1"));
    	vo.setOcc_2(!StringUtils.isNotBlank((String)params.get("occ_2"))?"":(String)params.get("occ_2"));
    	vo.setOcc_3(!StringUtils.isNotBlank((String)params.get("occ_3"))?"":(String)params.get("occ_3"));
    	vo.setOcc_4(!StringUtils.isNotBlank((String)params.get("occ_4"))?"":(String)params.get("occ_4"));
    	vo.setOcc_5(!StringUtils.isNotBlank((String)params.get("occ_5"))?"":(String)params.get("occ_5"));
    	vo.setOcc_6(!StringUtils.isNotBlank((String)params.get("occ_6"))?"":(String)params.get("occ_6"));
    	vo.setOcc_7(!StringUtils.isNotBlank((String)params.get("occ_7"))?blankPerato:(String)params.get("occ_7"));
    	vo.setOcc_8(!StringUtils.isNotBlank((String)params.get("occ_8"))?blankStatus:(String)params.get("occ_8"));	
    	vo.setOcc_12(!StringUtils.isNotBlank((String)params.get("occ_12"))?"":(String)params.get("occ_12"));
    	vo.setOcc_13(!StringUtils.isNotBlank((String)params.get("occ_13"))?"":(String)params.get("occ_13"));
    	vo.setOcc_14(!StringUtils.isNotBlank((String)params.get("occ_14"))?"":(String)params.get("occ_14"));
    	vo.setOcc_15(!StringUtils.isNotBlank((String)params.get("occ_15"))?"":(String)params.get("occ_15"));
    	vo.setOcc_16(!StringUtils.isNotBlank((String)params.get("occ_16"))?"":(String)params.get("occ_16"));
    	
    	vo.setOcc_17(!StringUtils.isNotBlank((String)params.get("occ_17"))?"":(String)params.get("occ_17"));
    	vo.setOcc_18(!StringUtils.isNotBlank((String)params.get("occ_18"))?"":(String)params.get("occ_18"));
    	vo.setOcc_19(!StringUtils.isNotBlank((String)params.get("occ_19"))?"":(String)params.get("occ_19"));
    	vo.setOcc_20(!StringUtils.isNotBlank((String)params.get("occ_20"))?"":(String)params.get("occ_20"));
    	vo.setOcc_21(!StringUtils.isNotBlank((String)params.get("occ_21"))?"":(String)params.get("occ_21"));
    	vo.setOcc_22(!StringUtils.isNotBlank((String)params.get("occ_22"))?"":(String)params.get("occ_22"));
    	vo.setOcc_23(!StringUtils.isNotBlank((String)params.get("occ_23"))?blankPerato:(String)params.get("occ_23"));
    	vo.setOcc_24(!StringUtils.isNotBlank((String)params.get("occ_24"))?blankStatus:(String)params.get("occ_24"));
    	vo.setOcc_29(!StringUtils.isNotBlank((String)params.get("occ_29"))?"":(String)params.get("occ_29"));
    	vo.setOcc_30(!StringUtils.isNotBlank((String)params.get("occ_30"))?"":(String)params.get("occ_30"));
     	vo.setOcc_31(!StringUtils.isNotBlank((String)params.get("occ_31"))?"":(String)params.get("occ_31"));
    	vo.setOcc_32(!StringUtils.isNotBlank((String)params.get("occ_32"))?"":(String)params.get("occ_32"));
    	vo.setOcc_33(!StringUtils.isNotBlank((String)params.get("occ_33"))?"":(String)params.get("occ_33"));
    	
    	vo.setOcc_34(!StringUtils.isNotBlank((String)params.get("occ_34"))?"":(String)params.get("occ_34"));
    	vo.setOcc_35(!StringUtils.isNotBlank((String)params.get("occ_35"))?"":(String)params.get("occ_35"));
    	vo.setOcc_36(!StringUtils.isNotBlank((String)params.get("occ_36"))?"":(String)params.get("occ_36"));
    	vo.setOcc_37(!StringUtils.isNotBlank((String)params.get("occ_37"))?"":(String)params.get("occ_37"));
    	vo.setOcc_38(!StringUtils.isNotBlank((String)params.get("occ_38"))?"":(String)params.get("occ_38"));
    	vo.setOcc_39(!StringUtils.isNotBlank((String)params.get("occ_39"))?"":(String)params.get("occ_39"));
    	vo.setOcc_40(!StringUtils.isNotBlank((String)params.get("occ_40"))?blankPerato:(String)params.get("occ_40"));
     	vo.setOcc_41(!StringUtils.isNotBlank((String)params.get("occ_41"))?blankStatus:(String)params.get("occ_41"));
     	vo.setOcc_46(!StringUtils.isNotBlank((String)params.get("occ_46"))?"":(String)params.get("occ_46"));
    	vo.setOcc_47(!StringUtils.isNotBlank((String)params.get("occ_47"))?"":(String)params.get("occ_47"));
    	vo.setOcc_48(!StringUtils.isNotBlank((String)params.get("occ_48"))?"":(String)params.get("occ_48"));
    	vo.setOcc_49(!StringUtils.isNotBlank((String)params.get("occ_49"))?"":(String)params.get("occ_49"));
    	vo.setOcc_50(!StringUtils.isNotBlank((String)params.get("occ_50"))?"":(String)params.get("occ_50"));
    	
    	vo.setOcc_51(!StringUtils.isNotBlank((String)params.get("occ_51"))?"":(String)params.get("occ_51"));
    	vo.setOcc_52(!StringUtils.isNotBlank((String)params.get("occ_52"))?"":(String)params.get("occ_52"));
    	vo.setOcc_53(!StringUtils.isNotBlank((String)params.get("occ_53"))?"":(String)params.get("occ_53"));
    	vo.setOcc_54(!StringUtils.isNotBlank((String)params.get("occ_54"))?"":(String)params.get("occ_54"));
    	vo.setOcc_55(!StringUtils.isNotBlank((String)params.get("occ_55"))?"":(String)params.get("occ_55"));
    	vo.setOcc_56(!StringUtils.isNotBlank((String)params.get("occ_56"))?"":(String)params.get("occ_56"));
    	vo.setOcc_57(!StringUtils.isNotBlank((String)params.get("occ_57"))?blankPerato:(String)params.get("occ_57"));
     	vo.setOcc_58(!StringUtils.isNotBlank((String)params.get("occ_58"))?blankStatus:(String)params.get("occ_58"));
    	vo.setOcc_63(!StringUtils.isNotBlank((String)params.get("occ_63"))?"":(String)params.get("occ_63"));
    	vo.setOcc_64(!StringUtils.isNotBlank((String)params.get("occ_64"))?"":(String)params.get("occ_64"));
    	vo.setOcc_65(!StringUtils.isNotBlank((String)params.get("occ_65"))?"":(String)params.get("occ_65"));
    	vo.setOcc_66(!StringUtils.isNotBlank((String)params.get("occ_66"))?"":(String)params.get("occ_66"));
    	vo.setOcc_67(!StringUtils.isNotBlank((String)params.get("occ_67"))?"":(String)params.get("occ_67"));
    
    	vo.setOcc_68(!StringUtils.isNotBlank((String)params.get("occ_68"))?"":(String)params.get("occ_68"));
    	vo.setOcc_69(!StringUtils.isNotBlank((String)params.get("occ_69"))?"":(String)params.get("occ_69"));
    	vo.setOcc_70(!StringUtils.isNotBlank((String)params.get("occ_70"))?"":(String)params.get("occ_70"));
    	vo.setOcc_71(!StringUtils.isNotBlank((String)params.get("occ_71"))?"":(String)params.get("occ_71"));
    	vo.setOcc_72(!StringUtils.isNotBlank((String)params.get("occ_72"))?"":(String)params.get("occ_72"));
    	vo.setOcc_73(!StringUtils.isNotBlank((String)params.get("occ_73"))?"":(String)params.get("occ_73"));
    	vo.setOcc_74(!StringUtils.isNotBlank((String)params.get("occ_74"))?blankPerato:(String)params.get("occ_74"));
     	vo.setOcc_75(!StringUtils.isNotBlank((String)params.get("occ_75"))?blankStatus:(String)params.get("occ_75"));
    	vo.setOcc_80(!StringUtils.isNotBlank((String)params.get("occ_80"))?"":(String)params.get("occ_80"));
    	vo.setOcc_81(!StringUtils.isNotBlank((String)params.get("occ_81"))?"":(String)params.get("occ_81"));
    	vo.setOcc_82(!StringUtils.isNotBlank((String)params.get("occ_82"))?"":(String)params.get("occ_82"));
    	vo.setOcc_83(!StringUtils.isNotBlank((String)params.get("occ_83"))?"":(String)params.get("occ_83"));
    	vo.setOcc_84(!StringUtils.isNotBlank((String)params.get("occ_84"))?"":(String)params.get("occ_84"));
    
    	vo.setOcc_85(!StringUtils.isNotBlank((String)params.get("occ_85"))?"":(String)params.get("occ_85"));
    	vo.setOcc_86(!StringUtils.isNotBlank((String)params.get("occ_86"))?"":(String)params.get("occ_86"));
    	vo.setOcc_87(!StringUtils.isNotBlank((String)params.get("occ_87"))?"":(String)params.get("occ_87"));
    	vo.setOcc_88(!StringUtils.isNotBlank((String)params.get("occ_88"))?"":(String)params.get("occ_88"));
    	vo.setOcc_89(!StringUtils.isNotBlank((String)params.get("occ_89"))?"":(String)params.get("occ_89"));
    	vo.setOcc_90(!StringUtils.isNotBlank((String)params.get("occ_90"))?"":(String)params.get("occ_90"));
    	vo.setOcc_91(!StringUtils.isNotBlank((String)params.get("occ_91"))?blankPerato:(String)params.get("occ_91"));
     	vo.setOcc_92(!StringUtils.isNotBlank((String)params.get("occ_92"))?blankStatus:(String)params.get("occ_92"));
    	vo.setOcc_97(!StringUtils.isNotBlank((String)params.get("occ_97"))?"":(String)params.get("occ_97"));
    	vo.setOcc_98(!StringUtils.isNotBlank((String)params.get("occ_98"))?"":(String)params.get("occ_98"));
    	vo.setOcc_99(!StringUtils.isNotBlank((String)params.get("occ_99"))?"":(String)params.get("occ_99"));
    	vo.setOcc_100(!StringUtils.isNotBlank((String)params.get("occ_100"))?"":(String)params.get("occ_100"));
    	vo.setOcc_101(!StringUtils.isNotBlank((String)params.get("occ_101"))?"":(String)params.get("occ_101"));
    
    	vo.setOcc_102(!StringUtils.isNotBlank((String)params.get("occ_102"))?"":(String)params.get("occ_102"));
    	vo.setOcc_103(!StringUtils.isNotBlank((String)params.get("occ_103"))?"":(String)params.get("occ_103"));
    	vo.setOcc_104(!StringUtils.isNotBlank((String)params.get("occ_104"))?"":(String)params.get("occ_104"));
    	vo.setOcc_105(!StringUtils.isNotBlank((String)params.get("occ_105"))?"":(String)params.get("occ_105"));
    	vo.setOcc_106(!StringUtils.isNotBlank((String)params.get("occ_106"))?"":(String)params.get("occ_106"));
    	vo.setOcc_107(!StringUtils.isNotBlank((String)params.get("occ_107"))?"":(String)params.get("occ_107"));
    	vo.setOcc_108(!StringUtils.isNotBlank((String)params.get("occ_108"))?blankPerato:(String)params.get("occ_108"));
     	vo.setOcc_109(!StringUtils.isNotBlank((String)params.get("occ_109"))?blankStatus:(String)params.get("occ_109"));
    	vo.setOcc_114(!StringUtils.isNotBlank((String)params.get("occ_114"))?"":(String)params.get("occ_114"));
    	vo.setOcc_115(!StringUtils.isNotBlank((String)params.get("occ_115"))?"":(String)params.get("occ_115"));
    	vo.setOcc_116(!StringUtils.isNotBlank((String)params.get("occ_116"))?"":(String)params.get("occ_116"));
    	vo.setOcc_117(!StringUtils.isNotBlank((String)params.get("occ_117"))?"":(String)params.get("occ_117"));
    	vo.setOcc_118(!StringUtils.isNotBlank((String)params.get("occ_118"))?"":(String)params.get("occ_118"));
    
    	vo.setOcc_119(!StringUtils.isNotBlank((String)params.get("occ_119"))?"":(String)params.get("occ_119"));
    	vo.setOcc_120(!StringUtils.isNotBlank((String)params.get("occ_120"))?"":(String)params.get("occ_120"));
    	vo.setOcc_121(!StringUtils.isNotBlank((String)params.get("occ_121"))?"":(String)params.get("occ_121"));
    	vo.setOcc_122(!StringUtils.isNotBlank((String)params.get("occ_122"))?"":(String)params.get("occ_122"));
    	vo.setOcc_123(!StringUtils.isNotBlank((String)params.get("occ_123"))?"":(String)params.get("occ_123"));
    	vo.setOcc_124(!StringUtils.isNotBlank((String)params.get("occ_124"))?"":(String)params.get("occ_124"));
    	vo.setOcc_125(!StringUtils.isNotBlank((String)params.get("occ_125"))?blankPerato:(String)params.get("occ_125"));
     	vo.setOcc_126(!StringUtils.isNotBlank((String)params.get("occ_126"))?blankStatus:(String)params.get("occ_126"));
    	vo.setOcc_131(!StringUtils.isNotBlank((String)params.get("occ_131"))?"":(String)params.get("occ_131"));
    	vo.setOcc_132(!StringUtils.isNotBlank((String)params.get("occ_132"))?"":(String)params.get("occ_132"));
    	vo.setOcc_133(!StringUtils.isNotBlank((String)params.get("occ_133"))?"":(String)params.get("occ_133"));
    	vo.setOcc_134(!StringUtils.isNotBlank((String)params.get("occ_134"))?"":(String)params.get("occ_134"));
    	vo.setOcc_135(!StringUtils.isNotBlank((String)params.get("occ_135"))?"":(String)params.get("occ_135"));
    
    	vo.setOcc_136(!StringUtils.isNotBlank((String)params.get("occ_136"))?"":(String)params.get("occ_136"));
    	vo.setOcc_137(!StringUtils.isNotBlank((String)params.get("occ_137"))?"":(String)params.get("occ_137"));
    	vo.setOcc_138(!StringUtils.isNotBlank((String)params.get("occ_138"))?"":(String)params.get("occ_138"));
    	vo.setOcc_139(!StringUtils.isNotBlank((String)params.get("occ_139"))?"":(String)params.get("occ_139"));
    	vo.setOcc_140(!StringUtils.isNotBlank((String)params.get("occ_140"))?"":(String)params.get("occ_140"));
    	vo.setOcc_141(!StringUtils.isNotBlank((String)params.get("occ_141"))?"":(String)params.get("occ_141"));
    	vo.setOcc_142(!StringUtils.isNotBlank((String)params.get("occ_142"))?blankPerato:(String)params.get("occ_142"));
     	vo.setOcc_143(!StringUtils.isNotBlank((String)params.get("occ_143"))?blankStatus:(String)params.get("occ_143"));
    	vo.setOcc_148(!StringUtils.isNotBlank((String)params.get("occ_148"))?"":(String)params.get("occ_148"));
    	vo.setOcc_149(!StringUtils.isNotBlank((String)params.get("occ_149"))?"":(String)params.get("occ_149"));
    	vo.setOcc_150(!StringUtils.isNotBlank((String)params.get("occ_150"))?"":(String)params.get("occ_150"));
    	vo.setOcc_151(!StringUtils.isNotBlank((String)params.get("occ_151"))?"":(String)params.get("occ_151"));
    	vo.setOcc_152(!StringUtils.isNotBlank((String)params.get("occ_152"))?"":(String)params.get("occ_152"));
    
    	vo.setOcc_153(!StringUtils.isNotBlank((String)params.get("occ_153"))?"":(String)params.get("occ_153"));
    	vo.setOcc_154(!StringUtils.isNotBlank((String)params.get("occ_154"))?"":(String)params.get("occ_154"));
    	vo.setOcc_155(!StringUtils.isNotBlank((String)params.get("occ_155"))?"":(String)params.get("occ_155"));
    	vo.setOcc_156(!StringUtils.isNotBlank((String)params.get("occ_156"))?"":(String)params.get("occ_156"));
    	vo.setOcc_157(!StringUtils.isNotBlank((String)params.get("occ_157"))?"":(String)params.get("occ_157"));
    	vo.setOcc_158(!StringUtils.isNotBlank((String)params.get("occ_158"))?"":(String)params.get("occ_158"));
    	vo.setOcc_159(!StringUtils.isNotBlank((String)params.get("occ_159"))?blankPerato:(String)params.get("occ_159"));
     	vo.setOcc_160(!StringUtils.isNotBlank((String)params.get("occ_160"))?blankStatus:(String)params.get("occ_160"));
    	vo.setOcc_165(!StringUtils.isNotBlank((String)params.get("occ_165"))?"":(String)params.get("occ_165"));
    	vo.setOcc_166(!StringUtils.isNotBlank((String)params.get("occ_166"))?"":(String)params.get("occ_166"));
    	vo.setOcc_167(!StringUtils.isNotBlank((String)params.get("occ_167"))?"":(String)params.get("occ_167"));
    	vo.setOcc_168(!StringUtils.isNotBlank((String)params.get("occ_168"))?"":(String)params.get("occ_168"));
    	vo.setOcc_169(!StringUtils.isNotBlank((String)params.get("occ_169"))?"":(String)params.get("occ_169"));
    	
    	vo.setT10OccOld(!StringUtils.isNotBlank((String)params.get("t10OccOld"))?"":(String)params.get("t10OccOld"));
    	vo.setOccOldTol(!StringUtils.isNotBlank((String)params.get("occOldTol"))?"":(String)params.get("occOldTol"));
    	vo.setT10OccNew(!StringUtils.isNotBlank((String)params.get("t10OccNew"))?"":(String)params.get("t10OccNew"));
    	vo.setOccNewTol(!StringUtils.isNotBlank((String)params.get("occNewTol"))?"":(String)params.get("occNewTol"));
    	
    	voList.add(vo);
    	if(voList.size()>0) {
    		return voList;
    	}
    	
		return null;
    	
    }
    	
	
}
