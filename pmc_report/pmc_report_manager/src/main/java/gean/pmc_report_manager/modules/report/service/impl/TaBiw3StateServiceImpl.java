package gean.pmc_report_manager.modules.report.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import gean.pmc_report_common.common.utils.DateUtils;
import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_common.common.utils.StringUtils;
import gean.pmc_report_manager.common.utils.Query;
import gean.pmc_report_manager.modules.report.dao.TaBiw3StateDao;
import gean.pmc_report_manager.modules.report.entity.TaBiw3StateEntity;
import gean.pmc_report_manager.modules.report.service.TaBiw3StateService;
import gean.pmc_report_manager.modules.report.vo.AreaOprVo;
import gean.pmc_report_manager.modules.report.vo.ZoneOprVo;


@Service("taBiw3StateService")
public class TaBiw3StateServiceImpl extends ServiceImpl<TaBiw3StateDao, TaBiw3StateEntity> implements TaBiw3StateService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TaBiw3StateEntity> page = this.page(
                new Query<TaBiw3StateEntity>().getPage(params),
                new QueryWrapper<TaBiw3StateEntity>()
        );

        return new PageUtils(page);
    }

    @Override
	public List<ZoneOprVo> queryOprForZone(Map<String, Object> params) {
		// TODO Auto-generated method stub
    	
    	Long workDay = 0l;
    	if(params.get("sTime")!=null) {
    		Date date = DateUtils.stringToDate(params.get("sTime").toString(),DateUtils.DATE_TIME_PATTERN);
    		String s_date = DateUtils.format(date, "yyyyMMdd");
    		workDay = Long.parseLong(s_date);
    	}
		
		params.put("sTime", workDay);
		List<TaBiw3StateEntity> zoneOprList = baseMapper.queryZoneOpr(params);
		
		List<ZoneOprVo> zOprList = new ArrayList<>();
		if(!StringUtils.isEmpty(zoneOprList)) {
			Integer cycleTime = 0;
			Integer downTime = 0;
			Integer equipAvail = 0;
			Float equipmentOpr = 0f;
			Integer goodPartCount = 0;
			Float productionOpr =0f;
			String starved = "";
			String blocked = "";
			String zone = "";
			long block = 0l;
			long starve = 0l;
			Map<String,List<TaBiw3StateEntity>> groupMap = new HashMap<>();
			List<TaBiw3StateEntity> list = new ArrayList<>();
			ZoneOprVo vo = null;
			for(TaBiw3StateEntity en : zoneOprList) {
				String zoneKey = en.getArea()+"-"+en.getZone();
				if(groupMap.containsKey(zoneKey)) {
					list.add(en);
				}else {
					list.add(en);
					groupMap.put(zoneKey, list);
				}
				groupMap.put(zoneKey, list);
			}
			
			for(TaBiw3StateEntity entity : list) {
				vo = new ZoneOprVo();
				downTime += entity.getDuration();
				cycleTime += entity.getDesignCycleTime();
				
				if(StringUtils.isNotBlank(entity.getState())
						&&"blocked".equals(entity.getState())) {
					block += Long.parseLong(entity.getDuration()==null?"":entity.getDuration().toString());
					
				}
				if(StringUtils.isNotBlank(entity.getState())
						&&"starved".equals(entity.getState())) {
					starve += Long.parseLong(entity.getDuration()==null?"":entity.getDuration().toString());
					
				}
				vo.setZone(entity.getZone());
				
			}
			equipAvail += (cycleTime-downTime);
			goodPartCount = (int)(1+Math.random()*(10-1+1));
			equipmentOpr = (float)(1+Math.random()*(100-1+1));
			productionOpr = (float)(1+Math.random()*(100-1+1));
			Date sd = new Date(starve);
			Date bd = new Date(block);
			starved = DateUtils.format(sd, "HH:mm:ss");
			blocked = DateUtils.format(bd, "HH:mm:ss");
			
			vo.setBlocked(blocked);
			vo.setCycleTime(cycleTime);
			vo.setDownTime(downTime);
			vo.setEquipAvail(equipAvail);
			vo.setEquipmentOpr(equipmentOpr);
			vo.setGoodPartCount(goodPartCount);
			vo.setProductionOpr(productionOpr);
			vo.setStarved(starved);
			zOprList.add(vo);
			return zOprList;
		}
		 return null;
	}
}
