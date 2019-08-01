package gean.pmc_report_manager.modules.report.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gean.pmc_report_common.common.utils.R;
import gean.pmc_report_common.common.utils.StringUtils;
import gean.pmc_report_manager.common.utils.JasperExportUtils;
import gean.pmc_report_manager.modules.report.service.TaSummaryService;
import gean.pmc_report_manager.modules.report.vo.TaSummaryVo;

@RestController
@RequestMapping("report/summary")
public class TaSummaryContoller {

	@Autowired
	private TaSummaryService taService;
	
	@RequestMapping("/info")
	public R findTaSummary(@RequestParam Map<String,Object> param) {
		
		List<TaSummaryVo> resultList = taService.queryTaSummary(param);
		if(resultList!=null) {
			return R.ok().put("list", resultList);
		}
		return R.error("无数据！");
	}
	
	@RequestMapping("/chart")
	public R findTrendChart(@RequestParam Map<String,Object> param) {
		
		List<TaSummaryVo> resultList = taService.queryTrendCahrt(param);
		if(resultList!=null) {
			return R.ok().put("list", resultList);
		}
		return R.error("无数据！");
	}
	
	@RequestMapping("/export")
	public void exportSummary(HttpServletRequest request,HttpServletResponse response,
			@RequestParam Map<String, Object> params) {
		//获取数据
		List<TaSummaryVo> resultList = taService.queryTaSummary(params);
		List<TaSummaryVo> exportList = new ArrayList<TaSummaryVo>();
		if(resultList!= null && !resultList.isEmpty()) {
        	for (TaSummaryVo ta : resultList) {
        		TaSummaryVo exportVo = new TaSummaryVo();
        		exportVo.setShop(!StringUtils.isNotBlank((String)params.get("shop")) ? "" : (String)params.get("shop"));
        		exportVo.setArea(!StringUtils.isNotBlank((String)params.get("area")) ? "ALL" : (String)params.get("area"));
        		exportVo.setZone(!StringUtils.isNotBlank((String)params.get("zone")) ? "ALL" : (String)params.get("zone"));
        		exportVo.setShift(!StringUtils.isNotBlank((String)params.get("shift")) ? "ALL" : (String)params.get("shift"));
        		exportVo.setJobId(!StringUtils.isNotBlank((String)params.get("jobId")) ? "ALL" : (String)params.get("jobId"));
        		exportVo.setZoneTaEchart(!StringUtils.isNotBlank((String)params.get("echarepxport")) ? "" : (String)params.get("echarepxport"));
        		exportVo.setPreWeekNo(!StringUtils.isNotBlank((String)params.get("preWeekNo")) ? "" : (String)params.get("preWeekNo"));
        		exportVo.setCurWeekNo(!StringUtils.isNotBlank((String)params.get("curWeekNo")) ? "" : (String)params.get("curWeekNo"));
        		Object objstart = params.get("sTime");
        		if(objstart != null && !"".equals(objstart)) {
        			exportVo.setStartTime((String)params.get("sTime"));
        		}else {
        			exportVo.setStartTime("");
        		}
        	
        		Object objend = params.get("eTime");
        		if(objend != null && !"".equals(objend)) {
        			exportVo.setEndTime((String)params.get("eTime"));
        		}else {
        			exportVo.setEndTime("");
        		}
        		exportVo.setImprove(ta.getImprove());
        		exportVo.setTargetTa(ta.getTargetTa());
        		exportVo.setPreActualTa(ta.getPreActualTa());
        		exportVo.setPreGap(ta.getPreGap());
        		exportVo.setPreVol(ta.getPreVol());
        		exportVo.setCurActualTa(ta.getCurActualTa());
        		exportVo.setCurGap(ta.getCurGap());
        		exportVo.setCurVol(ta.getCurVol());
        		String trend = "iVBORw0KGgoAAAANSUhEUgAAADwAAAA6CAYAAADspTpvAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAAEnQAABJ0Ad5mH3gAAABUSURBVGhD7c8BAQAwDMOg";
        		if("1".equals(ta.getTrend())) {
        			trend = "iVBORw0KGgoAAAANSUhEUgAAAIgAAAAcCAYAAAC6TfcHAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAAEnQAABJ0Ad5mH3gAAAKHSURBVGhD7ZrLTlpRFEDPDH7Atx+gn+DHSJgRA0h5ROqD1pYWJa21VK29aTsiYUJ8hMeYGE2AQElph50wcYLxRQwJOtjdZ3to1NabzvdeyZrc3HNGKzmPexUIgg0SiGCLBILc3NxAo9Egu92ueSpoJBBEAnkc9oHoOCqVCjR/NcmjoyOJ5A5sA7m+viZ1HPs/98H51UladQsODg7g6urKvMkbloH0w9Du/dgD5xcnKEv9cau8Bfl8HjqdDskZdoH049ht7pKOzw5QnzCMvh/RTQXrpXXIZDLkxcWFGc0PVoH049j5vgMOy0GqLQxiA/1gfI++Q98qWC2uktvb23B2dmZm4QWLQPr7jXK5DNlvWXBsYhgpjEC7jq6hb4xJdBVNoK9uXc4uQzKZhJOTEzMjHyQQCcQWFoHU63WyWCxCLpeDbDYLhUKBHN8YB7WCIbw2xtEXipaVQCBAejwecLvdEI1GzYx8YLUH6fV6cH5+DsfHx5BOp8mxtTFQyxjFM2MMXVQQj8ehVCqReoz28vLSzMQHVoH00UdXy7LI0ZVRUAsYxVPjHBpRMD8/T5dmWs6wDEQfW1OpFDnyfARUGKMI3TGoIBKJSCAIy0D0cpFIJMjhpWEK4p5PFIRCIQkEYRmIvtOIxWLk0MIQBXHPgIJgMAiHh4ckZyQQCcQWloGcnp5COBwmB6ODFMRD9fFWAmEaiL7w8vl85GOBzM7OSiAI20C8Xi85MDfwz0D8fr8EgrAMpN1uw8zMDDn1cgomlyZhYnHinhLILSwD0cfc6elpUu819HLyUJfLRd9utJxhGYim1WqRtVoNqtXqX+r/U/XVvJYzEogEYgvbQIT/QwIRbJFABBsAfgPMp6zGnYCRjwAAAABJRU5ErkJggg==";
        		}else if("2".equals(ta.getTrend())) {
        			trend = "iVBORw0KGgoAAAANSUhEUgAAAIgAAAAcCAYAAAC6TfcHAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAAEnQAABJ0Ad5mH3gAAAK8SURBVGhD7dhJS1tRGMZx53nAAXUhCoK4EEVwJW7di+DGjX4Dl1k4ZKh1QIMDGKMYVApakDQ2GoQaCIUaDAmGtE3TUIUa2lLaSoyZFoI89byYVIvcL/CeB/7c1d39uOfemwE5OYVJIHKKYwnk5uYGDoeDstlsODg4gNVqpfb29mA2m+kaj8cpzpNAJBDFsQSSTCZht9up2NISYhMTiOh0VFijwaVajX2LBT6fj+I8lkASiQQODw+pRGcnknV1iNfWUtGaGkSqq/FqZwder5fiPJZAxLEhjhBRtKMjjUIUrqrCn8pKvNzawunpKcV5LIHEYjHs7u5S4fZ2QnF5h0L0q6ICP8vL8WJ9HR6Ph+I8tkC2t7ep362taRSiH2Vl+FZaCpPBALfbTXEeSyDRaBSbm5vU95aWNApRqLgYX4uKYFxchMvlojhPApFAFMcSyPX1NdbW1qhQczNCJSWEQnReWIgvBQVYmp3FyckJxXksgUQiESwvL1PnTU1pFKJgXh4CubnQT07C6XRSnMcWyPz8PPW5sRHB/HxCIfqUk4OP2dmY0mhwfHxMcR4LIKknwcbGBkwmE4xGI2ZmZih/fX0aheh9VhZ8mZnQjYxAq9VSc3NzmJ6epnvFb3oRl7EAcnZ2Run1eryemsJRTw/edndTH+6OlxQKkTcjgzpqa4Olq4va6e2FSqXC6uoqbm9vKS5jdcQEg0GsrKzg3eAgoUjBSKH4P2dDAzWpVtN9nGCkJoFIIIpj95IaCASwsLCANwMD1FMwRALG8/FxiisOMXZAxPx+P3Q6HWXr738E4yEOAYMzDjGWQMRCoRA1OjqK/b6+RziejY3BYDAQDM44xNgCSe3i4gIjd5+0lqEh6iEOOQmEJpAMDw9T4ve7xPFvEsj9rq6uKLnHk0DuJ4E8PQlETnESiJziJBA5hQF/AV8X2alYKa8NAAAAAElFTkSuQmCC";
        		}else if("3".equals(ta.getTrend())){
        			trend = "iVBORw0KGgoAAAANSUhEUgAAAIgAAAAcCAYAAAC6TfcHAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAAEnQAABJ0Ad5mH3gAAALgSURBVGhD7ZlJS1thFIa7aB3qWGutY/9Sf0AXha66qQ00ilKopEiF0pKAVRGnChXrohGHmFAREQx1HoJjNJpoTDTGMc5R3373mCx6F3fR5f3OAw/kJiHZPDnnS3IPDKMBB8JowoEwmnAgjCYciIpIJIJwOBy7YjgQFVNTU2hubkYoFIrdIzcciIqJiQnYbFa0t7djZ2eHlBkORMXY2BgcDjPcbiNaWlrIYDAYe1Q+dB1IIBDAyMjIPzqdTgwNDWFgYIB0OBzo7e1FZ2cnqQRht38RUWTA5Soma2trsbW1FXtVudB1IC6XC3Nzf8Sa+EYGg9Xw+y3w+b7C4/lMut2fxHNMmJn5QE5Ovsf6+nMRRDo2NlLJ8fHXMJvN2NzcjL2yPHAgHIgmug/E4/ktbj0jb26KcH1diKurApyd5ZGRyFMcHj7B3t5jMhR6JFZThoghDV5vCrm6mozh4VeoqqoSwWyQsiBFILe3RaQSRzRagIuLfJyc5JJHRznY38/G7m4Wub2dSdPD50vF2loy6XYnYnHxgTizvEBFRQXp9Xpj76JvpAhEmRyK0WghLi/zxeTIxfFxDnlwkI1wOEusoEwyEEgX0yNVrJmHYnIkkcvLCZifv4+urjcoKSkhZfmdRNeBzM7Oik+/nSZHfLWcn+eJyXG3VuKrRVkryrcWRb8/TUyPFBHW3eSITw8lDqPRKK7dpCzoOpDp6WlxwBwWU6CbXFj4JaL5KQ6iPzA6+p10OhsxOFiH/v5q0m43i/tfYmUlCUtLCWR3dzFKS0ulCiOOrgNRDpM2mw1Wq5Xs6OhAW1sbWltb0dTURNbX16OmpgYWi4U0mUxoaHgn1koienrekrLGocCBcCCa6DqQ/0H5ZbWuziDCMqCsrIyUNQ4FDkRFX18fKis/ory8XJxDVkiZ4UBUKP/LGAwG6cOIw4GoOD09FV9zfbErhgNhNOFAGE04EEYTDoTRhANhNOFAGA2Av6K1Xogkcq9kAAAAAElFTkSuQmCC";
        		}
        		exportVo.setTrendChart(trend);
        		exportList.add(exportVo);
        	}
	     }
	    String exoprt = params.get("type") == null ? "word" : (String)params.get("type");
    	try {
    		InputStream is = this.getClass().getResourceAsStream("exportModel/ta_summary.jasper");//获取同包下模版文件
        	String exportName = "TA_Detail_Summary_报表";
    		JasperExportUtils.export(exportList, exoprt, is, request, response,exportName);
    		params.clear();
		} catch (Exception e) {
			params.clear();
			e.getMessage();
		}
	}
}
