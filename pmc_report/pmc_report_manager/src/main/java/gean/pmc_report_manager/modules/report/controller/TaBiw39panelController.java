package gean.pmc_report_manager.modules.report.controller;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gean.pmc_report_common.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import freemarker.template.Configuration;
import freemarker.template.Template;
import gean.pmc_report_manager.modules.report.entity.TaBiw39panelEntity;
import gean.pmc_report_manager.modules.report.service.TaBiw39panelService;
import gean.pmc_report_manager.modules.report.vo.PanelVo;
import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_common.common.utils.R;



/**
 * ${comments}
 *
 * @author Jason
 * @email xxxxx@gmail.com
 * @date 2019-04-13 14:15:33
 */
@RestController
@RequestMapping("modules/report/panel")
public class TaBiw39panelController {
    @Autowired
    private TaBiw39panelService taBiw39panelService;
    
    
    
    @RequestMapping("/listPrePanel")
    public Object listPrePanel(Map<String,Object> form) {
    	//假数据
        int totalCount = 10;
        if(totalCount > 0){
            List<PanelVo> user = new ArrayList<>();
            for(int i=1; i<11; i++) {
            	PanelVo vo = new PanelVo();
            	vo.set_new((int)(1+Math.random()*(10-1+1)));
            	vo.setOld(i);
            	vo.setDescription("* "+(int)(1+Math.random()*(10-1+1))+"S512 UNIT103 PART PRESENT");
            	vo.setMins((float)(3+Math.random()*(45-1+1)));
            	vo.setOcc((int)(1+Math.random()*(100-1+1)));
            	vo.setStn((int)(100+Math.random()*(2000-1+1)));
            	vo.setPareto((float)(1+Math.random()*(100-1+1)));
            	vo.setStatus((int)(0+Math.random()*(5-1+1)));
            	user.add(vo);
            }
            /////////////
            return new PageUtils(user, totalCount, totalCount, 0);
        }
        return null;
    }
    
    @RequestMapping("/listCurrPanel")
    public Object listCurrPanel(Map<String,Object> form) {
    	//假数据 
        int totalCount = 10;
        if(totalCount > 0){
            List<PanelVo> user = new ArrayList<>();
            for(int i=1; i<11; i++) {
            	PanelVo vo = new PanelVo();
            	vo.set_new(i);
            	vo.setDescription("* "+(int)(1+Math.random()*(10-1+1))+"S512 UNIT103 PART PRESENT");
            	vo.setMins((float)(3+Math.random()*(45-1+1)));
            	vo.setOcc((int)(1+Math.random()*(100-1+1)));
            	vo.setStn((int)(100+Math.random()*(2000-1+1)));
            	user.add(vo);
            }
            ///////////
            return new PageUtils(user, totalCount, totalCount, 0);
        }
        return null;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("report:tabiw39panel:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = taBiw39panelService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{taBiw39panelId}")
    @RequiresPermissions("report:tabiw39panel:info")
    public R info(@PathVariable("taBiw39panelId") Integer taBiw39panelId){
        TaBiw39panelEntity taBiw39panel = taBiw39panelService.getById(taBiw39panelId);

        return R.ok().put("taBiw39panel", taBiw39panel);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("report:tabiw39panel:save")
    public R save(@RequestBody TaBiw39panelEntity taBiw39panel){
        taBiw39panelService.save(taBiw39panel);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("report:tabiw39panel:update")
    public R update(@RequestBody TaBiw39panelEntity taBiw39panel){
        ValidatorUtils.validateEntity(taBiw39panel);
        taBiw39panelService.updateById(taBiw39panel);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("report:tabiw39panel:delete")
    public R delete(@RequestBody Integer[] taBiw39panelIds){
        taBiw39panelService.removeByIds(Arrays.asList(taBiw39panelIds));

        return R.ok();
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

		Writer writer = new OutputStreamWriter(new FileOutputStream(new File(tagertFile)), "utf-8");
		Template template = configuration.getTemplate("word.xml");
		Map<String, String> map = new HashMap<>();
		for (String str : params.keySet()) {
			map.put(str, params.get(str) == null ? null : params.get(str).toString());
		}
		// map.put("data", data);
		template.process(map, writer);
		writer.close();

		File file = new File(tagertFile);
		if (file.exists()) {
			response.setContentType("application/force-download");// 设置强制下载不打开            
			response.addHeader("Content-Disposition", "attachment;fileName=" + "word.doc");
			byte[] buffer = new byte[102400];
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
