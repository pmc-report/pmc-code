package gean.pmc_report_manager.modules.report.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gean.pmc_report_common.common.utils.DateUtils;
import gean.pmc_report_common.common.utils.StringUtils;
import gean.pmc_report_manager.modules.report.dao.TaBiw39panelDao;
import gean.pmc_report_manager.modules.report.service.TaSummaryService;
import gean.pmc_report_manager.modules.report.vo.PageParamVo;
import gean.pmc_report_manager.modules.report.vo.PanelVo;
import gean.pmc_report_manager.modules.report.vo.TaSummaryVo;

@Service("taSummaryService")
public class TaSummaryServiceImpl implements TaSummaryService{

	@Autowired
	private TaBiw39panelDao panelDao;
	
	@Override
	public List<TaSummaryVo> queryTaSummary(Map<String, Object> param) {
		/*
		 * 1.获取输入日期的周一和周日时间
		 * 2.组装参数查询数据
		 * 3.获取上周和当前周的TA数据，对比差异，组装数据
		 */
		List<TaSummaryVo> resultList = new ArrayList<>();
		DecimalFormat df = new DecimalFormat("##0.00");
		String preDate = (String)param.get("sTime"); 
		Date date = DateUtils.stringToDate(preDate, "yyyy-MM-dd");
		Date preStartTime = DateUtils.getFirstDayOfWeek(date);
		Date preEndTime = DateUtils.getLastDayOfWeek(date);
		
		PageParamVo paramVo = new PageParamVo(param);
		paramVo.setStartTime(preStartTime);
		paramVo.setEndTime(preEndTime);
		Map<String,Object> preTaMap = panelDao.queryTaSummary(paramVo);
		Map<String,Object> preVolMap = panelDao.queryVol(paramVo);
		
		String curDate = (String)param.get("eTime"); 
		Date _date = DateUtils.stringToDate(curDate, "yyyy-MM-dd");
		Date curStartTime = DateUtils.getFirstDayOfWeek(_date);
		Date curEndTime = DateUtils.getLastDayOfWeek(_date);
		
		paramVo.setStartTime(curStartTime);
		paramVo.setEndTime(curEndTime);
		Map<String,Object> curTaMap = panelDao.queryTaSummary(paramVo);
		Map<String,Object> curVolMap = panelDao.queryVol(paramVo);
		
		TaSummaryVo vo = new TaSummaryVo();
		
		String area = (String)param.get("area")==null?"":(String)param.get("area");
		String zone = (String)param.get("zone")==null?"":(String)param.get("zone");
		String jobId = (String)param.get("jobId")==null?"":(String)param.get("jobId");
		Integer prePordVol = 0;
		Integer curPordVol = 0;
		Float targetTa = 0f;
		Float preActTa = 0f;
		Float preGap = 0f;
		Float curActTa = 0f;
		Float curGap = 0f;
		if(preVolMap!=null&&!preVolMap.isEmpty()
				&&preVolMap.get("prod_vol")!=null) {
			prePordVol = Integer.valueOf(preVolMap.get("prod_vol").toString());
		}
		if(curVolMap!=null&&!curVolMap.isEmpty()
				&&curVolMap.get("prod_vol")!=null) {
			curPordVol = Integer.valueOf(curVolMap.get("prod_vol").toString());
		}
		if(!StringUtils.isEmpty(preTaMap)) {
			targetTa = Float.valueOf(preTaMap.get("tar_tech_avail")==null?"0.00":preTaMap.get("tar_tech_avail").toString());
			preActTa = Float.valueOf(preTaMap.get("tech_avail")==null?"0.00":preTaMap.get("tech_avail").toString());
			preGap = Float.valueOf(preTaMap.get("Gap")==null?"0.00":preTaMap.get("Gap").toString());
		}
		if(!StringUtils.isEmpty(curTaMap)) {
			curActTa = Float.valueOf(curTaMap.get("tech_avail")==null?"0.00":curTaMap.get("tech_avail").toString());
			curGap = Float.valueOf(curTaMap.get("Gap")==null?"0.00":curTaMap.get("Gap").toString());
			
		}
		
		Float improve = 0.00f;
		if(curActTa>-1
				&&preActTa>-1) {
			improve = curActTa - preActTa;
		}
		String trend ="";
		if(improve>0) {
			trend = "1";//上升
		}else if(improve<0) {
			trend = "2";//下降
		}else {
			trend = "3";//持平
		}
		
		vo.setArea(area);
		vo.setZone(zone);
		vo.setJobId(jobId);
		vo.setTargetTa(Float.parseFloat(df.format(targetTa)));
		vo.setPreActualTa(Float.parseFloat(df.format(preActTa*100)));
		vo.setPreGap(Float.parseFloat(df.format(preGap)));
		vo.setPreVol(prePordVol);
		vo.setCurActualTa(Float.parseFloat(df.format(curActTa*100)));
		vo.setCurGap(Float.parseFloat(df.format(curGap)));
		vo.setCurVol(curPordVol);
		vo.setImprove(Float.parseFloat(df.format(improve*100)));
		vo.setTrend(trend);
		resultList.add(vo);
		
		if(resultList.size()>0) {
			return resultList;
		}
		return null;
	}

	@Override
	public List<TaSummaryVo> queryTrendCahrt(Map<String, Object> param) {
		// TODO Auto-generated method stub
		//初始化查询参数
		PageParamVo paramVo = new PageParamVo(param);
		//返回结果list
		List<TaSummaryVo> resultList = new ArrayList<>();
		//查询结果集
		List<PanelVo> taInfoList = panelDao.queryTrendChart(paramVo);
		//查目标TA
		PanelVo panelVo = panelDao.queryTarTavMtbf(paramVo);
			
		//业务验证
		if(!StringUtils.isEmpty(taInfoList)) {
			//转换
			DecimalFormat df = new DecimalFormat("##0.00");
			//遍历取出数据
			for(PanelVo panel: taInfoList) {
				//每个实例
				TaSummaryVo vo = new TaSummaryVo();
				//组装数据
				vo.setWeek(panel.getWeek());
				vo.setCurActualTa(Float.parseFloat(df.format(panel.getTav())));
				vo.setTargetTa(Float.parseFloat(df.format(panelVo.getTargetTav())));
				//装填
				resultList.add(vo);
			}
		}
		if(resultList.size()>0) {
			return resultList;
		}
		return null;
	}

}
