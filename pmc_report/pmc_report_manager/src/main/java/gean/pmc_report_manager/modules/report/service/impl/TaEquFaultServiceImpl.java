package gean.pmc_report_manager.modules.report.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_common.common.utils.StringUtils;
import gean.pmc_report_manager.common.utils.Query;
import gean.pmc_report_manager.modules.report.dao.TaEquFaultDao;
import gean.pmc_report_manager.modules.report.entity.TaEquFaultEntity;
import gean.pmc_report_manager.modules.report.service.TaEquFaultService;
import gean.pmc_report_manager.modules.report.vo.FaultVo;
import gean.pmc_report_manager.modules.report.vo.PageParamVo;


@Service("taEquFaultService")
public class TaEquFaultServiceImpl extends ServiceImpl<TaEquFaultDao, TaEquFaultEntity> implements TaEquFaultService {
	
	@Override
	 public TaEquFaultEntity queryTotalMins(Map<String, Object> params){
		
				PageParamVo vo = new PageParamVo(params);
						
						String str = vo.getEquipment();
						str = str.replaceAll("&amp;", "&");
						vo.setEquipment(str);
			 return baseMapper.queryTotalMins(vo); 
	 }
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TaEquFaultEntity> page = this.page(
                new Query<TaEquFaultEntity>().getPage(params),
                new QueryWrapper<TaEquFaultEntity>()
        );

        return new PageUtils(page);
    }

	@Override
	public PageUtils queryEquFaultByParam(Map<String, Object> params) {
		// TODO Auto-generated method stub
		//后续封装方法简化代码
		PageParamVo vo = new PageParamVo(params);
		
		String str = vo.getEquipment();
		str = str.replaceAll("&amp;", "&");
		vo.setEquipment(str);
		
		/*List<TaEquFaultEntity> faultList = baseMapper.qureyFualtList(vo);
		
		int pageSize = Integer.parseInt(vo.getLimit()==null?"0":vo.getLimit());
		int currPage = Integer.parseInt(vo.getPage()==null?"0":vo.getPage());
		
		PageUtils page = new PageUtils(faultList, faultList.size(), pageSize, currPage);*/
		
 		Timestamp sTime=null;
		Timestamp eTime=null;
		if(vo.getStartTime()!=null) {
			sTime = new Timestamp(vo.getStartTime().getTime());
		}
		if(vo.getEndTime()!=null) {
			eTime = new Timestamp(vo.getEndTime().getTime());
		}
		IPage<TaEquFaultEntity> page = 
				this.page( new Query<TaEquFaultEntity>().getPage(params),
						new QueryWrapper<TaEquFaultEntity>().eq(StringUtils.isNotBlank(vo.getShop()), "shop", vo.getShop())
															//.eq(StringUtils.isNotBlank(vo.getArea()), "area", vo.getArea())
															.eq(StringUtils.isNotBlank(vo.getArea()), "line", vo.getArea())
															.eq(StringUtils.isNotBlank(vo.getZone()), "zone", vo.getZone())
															.eq(StringUtils.isNotBlank(vo.getStation()), "station", vo.getStation())
															.eq(StringUtils.isNotBlank(vo.getEquipment()), "equipment", vo.getEquipment())
															.eq(StringUtils.isNotBlank(vo.getShift()), "shift", vo.getShift())
															.eq(StringUtils.isNotBlank(vo.getJobId()), "job_id", vo.getJobId())
															.ge(StringUtils.isNotNull(sTime), "start_Time", sTime)
															.le(StringUtils.isNotNull(eTime), "end_Time", eTime)
															.ne("fault_word3", 0)
															.isNotNull("duration")
															//.isNotNull("end_Time")
															.orderByAsc("facility_id ").orderByDesc("start_Time"));
		
		return new PageUtils(page);
	}

	@Override
	public List<FaultVo> queryExportFault(Map<String, Object> params) {
		
		PageParamVo vo = new PageParamVo(params);

		String str = vo.getEquipment();
		str = str.replaceAll("&amp;", "&");
		vo.setEquipment(str);

		List<FaultVo> faultList = baseMapper.qureyFualtList(vo);
		return faultList;
	}
	
	
	
	@Override
	public List<FaultVo> queryFaultsList(Map<String, Object> params) {
		PageParamVo vo = new PageParamVo(params);
		List<FaultVo> faultsList= baseMapper.queryFaultsList(vo);
		List<FaultVo> list = new ArrayList<>();
		FaultVo _new = null;
		for (FaultVo faultsVo : faultsList) {
			_new = new FaultVo();
			_new.setFacilityId(faultsVo.getFacilityId());
			_new.setOccurence(faultsVo.getOccurence());
			_new.setMinutes(faultsVo.getMinutes());
			_new.setStation(faultsVo.getStation());
			_new.setFaultDescription(faultsVo.getFaultDescription());
			list.add(_new);
		}
		return list;
	}
}
