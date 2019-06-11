package gean.pmc_report_manager.modules.report.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_common.common.utils.StringUtils;
import gean.pmc_report_manager.common.utils.Query;
import gean.pmc_report_manager.modules.report.dao.TaBiw39panelDao;
import gean.pmc_report_manager.modules.report.entity.TaBiw39panelEntity;
import gean.pmc_report_manager.modules.report.service.TaBiw39panelService;
import gean.pmc_report_manager.modules.report.vo.PageParamVo;
import gean.pmc_report_manager.modules.report.vo.PanelVo;


@Service("taBiw39panelService")
public class TaBiw39panelServiceImpl extends ServiceImpl<TaBiw39panelDao, TaBiw39panelEntity> implements TaBiw39panelService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TaBiw39panelEntity> page = this.page(
                new Query<TaBiw39panelEntity>().getPage(params),
                new QueryWrapper<TaBiw39panelEntity>()
        );

        return new PageUtils(page);
    }

	@Override
	public List<PanelVo> queryEchart(Map<String, Object> params) {
		// TODO Auto-generated method stub
		PageParamVo vo = new PageParamVo(params);
		if(vo.getZone()!=null
				&&!"".equals(vo.getZone())) {
			vo.setLevel("zone");
		}
		if("".equals(vo.getArea())) {
			vo.setLevel("shop");
		}
		if(!"".equals(vo.getArea())
				&&"".equals(vo.getZone())) {
			vo.setLevel("line");
		}
		if(vo.getShift()==null
				||"".equals(vo.getShift())) {
			vo.setShift("ALL");
		}
		
		List<PanelVo> list = baseMapper.queryEchart(vo);
		
		DecimalFormat df = new DecimalFormat("##0.00");
		
		PanelVo _new = null;
		
		List<PanelVo> resultList = new ArrayList<>();
		
		//组装数组
		for(PanelVo panel : list) {
				_new = new PanelVo();
				if(panel.getMonday()!=null) {
					_new.setMonday(panel.getMonday());
				}
				if(panel.getTav()!=0) {
					_new.setTargetTav(panel.getTargetTav());
					_new.setTav(Float.parseFloat(df.format(panel.getTav())));
				}
				
				if(panel.getMtbf()!=0) {
					_new.setTargetMtbf(Float.parseFloat(df.format(panel.getTargetMtbf())));
					_new.setMtbf(panel.getMtbf());
				}
				_new.setStartTime(panel.getStartTime());
				_new.setWeek(panel.getWeek());
				_new.setWeekRange(panel.getWeekRange());
				_new.setWeekNo(panel.getWeekNo());
				resultList.add(_new);
		}
		
		Collections.sort(resultList, new Comparator<PanelVo>() {
			@Override
			public int compare(PanelVo o1, PanelVo o2) {
				// TODO Auto-generated method stub
				int i = o1.getWeekRange() - o2.getWeekRange();
				return i;
			}
		});
		return resultList;
	}

	


	@Override
	public List<PanelVo> queryTop10DownTime(Map<String, Object> params) {
		// TODO Auto-generated method stub
		PageParamVo vo = new PageParamVo(params);
		
		//5
		List<PanelVo> resultList = new ArrayList<>();
		/*
		 * 1.查询开始日期内排名前十的故障按照停机时长排名
		 * 2.查询开始日期内总的停机持续时间
		 * 3.查询结束日期内排名前十的故障按照停机时长排名
		 * 4.查询结束日期内总的停机持续时间
		 * 5.组装数据返回前端(5.1：组装开始周的表格数据-旧，5.2：匹配排名变化、生成编号，5.3：组装结束周的表格数据-新)
		 */
		
		//1
		List<PanelVo> top10DownTimeOldList = baseMapper.queryTop10DownTimeOld(vo);
		
		//2
		List<PanelVo> totalDownTimeForFrom = baseMapper.queryTotalDurationTimeOld(vo);
		
		//3
		List<PanelVo> top10DownTimeNewList = baseMapper.queryTop10DownTimeNew(vo);
		
		//4
		List<PanelVo> totalDownTimeForTo = baseMapper.queryTotalDurationTimeNew(vo);
		
		Map<String,PanelVo> groupMap = new HashMap<>();
		
		DecimalFormat df = new DecimalFormat("##0.00");
		//5
		if(!StringUtils.isEmpty(top10DownTimeOldList)&&!StringUtils.isEmpty(top10DownTimeNewList)) {
			
			//5.1
			for(int i=0;i<top10DownTimeOldList.size();i++) {
				PanelVo resultVo = new PanelVo();
				if(top10DownTimeOldList.get(i)!=null) {
					PanelVo oldVo = top10DownTimeOldList.get(i);
					if(i==0) {
						float totalDur1 = totalDownTimeForFrom.get(0).getTotalDuration1();
						resultVo.setTotalDuration1(Float.parseFloat(df.format(totalDur1)));
					}
					Integer oldOrder = i + 1;
					resultVo.setOld(oldOrder);
					resultVo.setOcc1(oldVo.getOcc1());
					resultVo.setMins1(Float.parseFloat(df.format(oldVo.getMins1())));
					resultVo.setStn1(oldVo.getStn1());
					resultVo.setDescription1(oldVo.getDescription1());
					String oldKey = oldVo.getStn1()+"-"+oldVo.getDescription1();
					groupMap.put(oldKey, resultVo);
					resultList.add(resultVo);
				}
			}
		
			//5.2
			for(int i=0;i<top10DownTimeNewList.size();i++) {
				if(top10DownTimeNewList.get(i)!=null) {
					PanelVo newVo = top10DownTimeNewList.get(i);
					String newKey = newVo.getStn2()+"-"+newVo.getDescription2();
					if(groupMap.containsKey(newKey)) {
						PanelVo oldVo = groupMap.get(newKey);
						Integer newOrder = newVo.get_new();
						Integer oldOrder = oldVo.getOld();
						oldVo.setOldThenNew(newOrder);
						if(newOrder>10) {
							oldVo.setStatus(4);//退出前十
						}else if(newOrder < oldOrder) {
							oldVo.setStatus(0);//排名上升
						}else if(newOrder > oldOrder) {
							oldVo.setStatus(3);//排名降低
						}else if(newOrder == oldOrder) {
							oldVo.setStatus(2);//保持不变
						}
					}
				}
			}
		
			//5.3
			for(int i=0;i<=9;i++) {
				int count = top10DownTimeNewList.size();
				if(i<=count-1) {
					PanelVo newVo = top10DownTimeNewList.get(i);
					PanelVo oldVo = resultList.get(i);
					if(i==0) {
						float totalDur2 = totalDownTimeForTo.get(0).getTotalDuration2();
						oldVo.setTotalDuration2(Float.parseFloat(df.format(totalDur2)));
					}
					Integer newOrder = newVo.get_new();
					oldVo.set_new(newOrder);
					oldVo.setOcc2(newVo.getOcc2());
					oldVo.setMins2(Float.parseFloat(df.format(newVo.getMins2())));
					oldVo.setStn2(newVo.getStn2());
					oldVo.setDescription2(newVo.getDescription2());
					if(oldVo.getStatus()==null) {
						oldVo.setStatus(1);//故障消失
					}
				}
			}
			
			if(!StringUtils.isEmpty(resultList)) {
				return resultList;
			}
		}
		return null;
	}
	
	

	@Override
	public List<PanelVo> queryTop10Occurrence(Map<String, Object> params) {
		// TODO Auto-generated method stub
		PageParamVo vo = new PageParamVo(params);
		
		//5
		List<PanelVo> resultList = new ArrayList<>();
		/*
		 * 1.查询开始日期内排名前十的故障按照停机次数排名
		 * 2.查询开始日期内总的停机次数
		 * 3.查询结束日期内排名前十的故障按照停机次数排名
		 * 4.查询结束日期内总的停机次数
		 * 5.组装数据返回前端(5.1：组装开始周的表格数据-旧，5.2：匹配排名变化、生成编号，5.3：组装结束周的表格数据-新)
		 */
		
		//1
		List<PanelVo> top10OccurrenceOldList = baseMapper.queryTop10OccurrenceOld(vo);
		
		//2
		List<PanelVo> totalOccurrenceForFrom = baseMapper.queryTotalDurationTimeOld(vo);
		
		//3
		List<PanelVo> top10OccurrenceNewList = baseMapper.queryTop10OccurrenceNew(vo);
		
		//4
		List<PanelVo> totalOccurrenceForTo = baseMapper.queryTotalDurationTimeNew(vo);
		
		Map<String,PanelVo> groupMap = new HashMap<>();
		
		DecimalFormat df = new DecimalFormat("##0.00");
		
		if(!StringUtils.isEmpty(top10OccurrenceOldList)&&!StringUtils.isEmpty(top10OccurrenceNewList)) {
			//5.1
			for(int i=0;i<top10OccurrenceOldList.size();i++) {
				PanelVo resultVo = new PanelVo();
				if(top10OccurrenceOldList.get(i)!=null) {
					PanelVo oldVo = top10OccurrenceOldList.get(i);
					if(i==0) {
						int totalOcc1 = totalOccurrenceForFrom.get(0).getTotalOcc1();
						resultVo.setTotalOcc1(totalOcc1);
					}
					Integer oldOrder = i + 1;
					resultVo.setOld(oldOrder);
					resultVo.setOcc1(oldVo.getOcc1());
					resultVo.setMins1(Float.parseFloat(df.format(oldVo.getMins1())));
					resultVo.setStn1(oldVo.getStn1());
					resultVo.setDescription1(oldVo.getDescription1());
					String oldKey = oldVo.getStn1()+"-"+oldVo.getDescription1();
					groupMap.put(oldKey, resultVo);
					resultList.add(resultVo);
				}
			}
			
			//5.2
			for(int i=0;i<top10OccurrenceNewList.size();i++) {
				if(top10OccurrenceNewList.get(i)!=null) {
					PanelVo newVo = top10OccurrenceNewList.get(i);
					String newKey = newVo.getStn2()+"-"+newVo.getDescription2();
					if(groupMap.containsKey(newKey)) {
						PanelVo oldVo = groupMap.get(newKey);
						Integer newOrder = newVo.get_new();
						Integer oldOrder = oldVo.getOld();
						oldVo.setOldThenNew(newOrder);
						if(newOrder>10) {
							oldVo.setStatus(4);//退出前十
						}else if(newOrder < oldOrder) {
							oldVo.setStatus(0);//排名上升
						}else if(newOrder > oldOrder) {
							oldVo.setStatus(3);//排名降低
						}else if(newOrder == oldOrder) {
							oldVo.setStatus(2);//保持不变
						}
					}
				}
			}
			
			//5.3
			for(int i=0;i<=9;i++) {
				int count = top10OccurrenceNewList.size();
				if(i <= count -1) {
					PanelVo newVo = top10OccurrenceNewList.get(i);
					PanelVo oldVo = resultList.get(i);
					if(i==0) {
						int totalOcc2 = totalOccurrenceForTo.get(0).getTotalOcc2();
						oldVo.setTotalOcc2(totalOcc2);
					}
					Integer newOrder = newVo.get_new();
					oldVo.set_new(newOrder);
					oldVo.setOcc2(newVo.getOcc2());
					oldVo.setMins2(Float.parseFloat(df.format(newVo.getMins2())));
					oldVo.setStn2(newVo.getStn2());
					oldVo.setDescription2(newVo.getDescription2());
					if(oldVo.getStatus()==null) {
						oldVo.setStatus(1);//故障消失
					}
				}
			}
			
			if(!StringUtils.isEmpty(resultList)) {
				return resultList;
			}
		}
		return null;
	}

	
}
