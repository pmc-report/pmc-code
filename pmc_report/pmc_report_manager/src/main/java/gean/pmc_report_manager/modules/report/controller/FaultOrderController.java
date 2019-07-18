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
import gean.pmc_report_manager.modules.report.service.FaultOrderService;
import gean.pmc_report_manager.modules.report.vo.FaultOrderVo;

@RestController
@RequestMapping("report/order")
public class FaultOrderController {

	@Autowired
	private FaultOrderService orderServcie;
	
	@RequestMapping("/currDurList")
	public R findCurrShiftTop10FaultDur(@RequestParam Map<String,Object> param) {
		int flag = 0;
		List<FaultOrderVo> list = orderServcie.queryCurrTop10Fualt(param,flag);
		return R.ok().put("list", list);
		
	}
	
	@RequestMapping("/currOccList")
	public R findCurrShiftTop10FaultOcc(@RequestParam Map<String,Object> param) {
		int flag = 1;
		List<FaultOrderVo> list = orderServcie.queryCurrTop10Fualt(param,flag);
		return R.ok().put("list", list);
		
	}
	
	@RequestMapping("/preDurList")
	public R findPreShiftTop10FaultDur(@RequestParam Map<String,Object> param) {
		int flag = 0;
		List<FaultOrderVo> list = orderServcie.queryPreTop10Fualt(param,flag);
		return R.ok().put("list", list);
		
	}
	
	@RequestMapping("/preOccList")
	public R findPreShiftTop10FaultOcc(@RequestParam Map<String,Object> param) {
		int flag = 1;
		List<FaultOrderVo> list = orderServcie.queryPreTop10Fualt(param,flag);
		return R.ok().put("list", list);
		
	}
	
	  
    /**
     * 导出报表
     * @param request
     * @param response
     * @param params
     * @throws Exception
     */
    @RequestMapping("/export")
    public void export(HttpServletRequest request,HttpServletResponse response,
			@RequestParam Map<String, Object> params) throws Exception {

    	String fileType = (String)params.get("type");
		String tagertFile = "src/main/java/gean/pmc_report_manager/modules/report/controller/exportModel/Top10_Fault."+fileType;
		Configuration configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
		configuration.setClassForTemplateLoading(this.getClass(), "exportModel"); // 获取需要加载文件的相对路径 只到上级包
		configuration.setClassicCompatible(true);
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("export/Top10_Fault."+fileType);
		File f = new File(tagertFile);
		FileUtils.copyInputStreamToFile(is, f);
		Writer writer = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
		Template template =null;
		if("xls".equals(fileType)) {
			template = configuration.getTemplate("Top10_Fault_excel.xml");
		}else if("doc".equals(fileType)) {
			template = configuration.getTemplate("Top10_Fault_word.xml");
		}
		
		Map<String, String> map = new HashMap<>();
		//组装数据
		for (String str : params.keySet()) {
			map.put(str, params.get(str) == null ? " " : (String)params.get(str));
		}
		map.put("createTime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
		template.process(map, writer);
		writer.close();

		File file = new File(tagertFile);
		if (file.exists()) {
			response.setContentType("application/force-download");// 设置强制下载不打开            
			response.addHeader("Content-Disposition", "attachment;fileName= Top10_Fault."+fileType);
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

	
}
