package gean.pmc_report_manager.modules.report.service.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_common.common.utils.StringUtils;
import gean.pmc_report_manager.common.utils.Query;
import gean.pmc_report_manager.modules.report.dao.TaEquFaultDao;
import gean.pmc_report_manager.modules.report.entity.PmcBiwFaultEntity;
import gean.pmc_report_manager.modules.report.service.EquFaultService;
import gean.pmc_report_manager.modules.report.vo.PageParamVo;


@Service("taEquFaultService")
public class EquFaultServiceImpl extends ServiceImpl<TaEquFaultDao, PmcBiwFaultEntity> implements EquFaultService {

	@Override
	 public PmcBiwFaultEntity queryTotalMins(Map<String, Object> params){

			 Map<String, String> faultMap = new HashMap<String, String>();
				for(String str : params.keySet()) {
					faultMap.put(str, params.get(str) != null ? params.get(str).toString() : null);
				}
			 return baseMapper.queryTotalMins(faultMap); 
	 }
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PmcBiwFaultEntity> page = this.page(
                new Query<PmcBiwFaultEntity>().getPage(params),
                new QueryWrapper<PmcBiwFaultEntity>()
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
		//List<PmcBiwFaultEntity> list = baseMapper.qureyFualtList(vo);
 		Timestamp sTime=null;
		Timestamp eTime=null;
		if(vo.getStartTime()!=null) {
			sTime = new Timestamp(vo.getStartTime().getTime());
			eTime = new Timestamp(vo.getEndTime().getTime());
		}
		IPage<PmcBiwFaultEntity> page = 
				this.page( new Query<PmcBiwFaultEntity>().getPage(params),
						new QueryWrapper<PmcBiwFaultEntity>().eq(StringUtils.isNotBlank(vo.getShop()), "shop", vo.getShop())
															//.eq(StringUtils.isNotBlank(vo.getArea()), "area", vo.getArea())
															.eq(StringUtils.isNotBlank(vo.getArea()), "line", vo.getArea())
															.eq(StringUtils.isNotBlank(vo.getZone()), "zone", vo.getZone())
															.eq(StringUtils.isNotBlank(vo.getStation()), "station", vo.getStation())
															.eq(StringUtils.isNotBlank(vo.getEquipment()), "equipment", vo.getEquipment())
															.eq(StringUtils.isNotBlank(vo.getShift()), "shift", vo.getShift())
															.eq(StringUtils.isNotBlank(vo.getJobId()), "job_id", vo.getJobId())
															.ge(StringUtils.isNotNull(sTime), "start_Time", sTime)
															.le(StringUtils.isNotNull(eTime), "end_Time", eTime));
		if(page!=null) {
			return new PageUtils(page);
		}
		return null;
	}

}
