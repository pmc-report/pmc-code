package gean.pmc_report_manager.modules.report.service.impl;

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
import gean.pmc_report_manager.modules.report.dao.TaBiw3OprDao;
import gean.pmc_report_manager.modules.report.entity.TaBiw3OprEntity;
import gean.pmc_report_manager.modules.report.service.TaBiw3OprService;
import gean.pmc_report_manager.modules.report.vo.AreaOprVo;
import gean.pmc_report_manager.modules.report.vo.PageParamVo;
import gean.pmc_report_manager.modules.report.vo.ZoneOprVo;


@Service("taBiw3OprService")
public class TaBiw3OprServiceImpl extends ServiceImpl<TaBiw3OprDao, TaBiw3OprEntity> implements TaBiw3OprService {
	
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TaBiw3OprEntity> page = this.page(
                new Query<TaBiw3OprEntity>().getPage(params),
                new QueryWrapper<TaBiw3OprEntity>()
        );

        return new PageUtils(page);
    }

	@Override
	public List<AreaOprVo> queryOprForArea(Map<String, Object> params) {
		// TODO Auto-generated method stub
		PageParamVo vo = new PageParamVo(params);
		
		List<TaBiw3OprEntity> areaOprList = baseMapper.qureyOPRList(vo);
		
		List<AreaOprVo> aOprList = new ArrayList<>();
		List<ZoneOprVo> zOprList = new ArrayList<>();
		if(!StringUtils.isEmpty(areaOprList)) {
			for(TaBiw3OprEntity en : areaOprList) {
				if("zone".equals(en.getOprLevel())) {
					ZoneOprVo zvo = new ZoneOprVo();
					zvo.setZone(en.getZone());
					zvo.setEquipmentOpr(en.getActualEquipmentOpr());
					zvo.setProductionOpr(en.getActualProductionOpr());
					zOprList.add(zvo);
				}
				AreaOprVo avo = new AreaOprVo();
				avo.setActual(196);
				avo.setArea(en.getArea());
				avo.setEquipmentOpr(en.getActualEquipmentOpr());
				avo.setProductionOpr(en.getTargetProductionOpr()/en.getActualProductionOpr());
				avo.setShiftPlan(en.getTargetProductionOpr());
				avo.setVariation(0);
				avo.setZone(en.getZone());
				avo.setZoneList(zOprList);
				aOprList.add(avo);
			}
			return aOprList;
		}
		return null;
	}
}
