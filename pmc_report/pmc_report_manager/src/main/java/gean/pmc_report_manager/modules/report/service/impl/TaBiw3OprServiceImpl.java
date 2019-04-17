package gean.pmc_report_manager.modules.report.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import gean.pmc_report_common.common.utils.DateUtils;
import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_common.common.utils.StringUtils;
import gean.pmc_report_manager.common.utils.Query;
import gean.pmc_report_manager.modules.report.dao.TaBiw3OprDao;
import gean.pmc_report_manager.modules.report.entity.TaBiw3OprEntity;
import gean.pmc_report_manager.modules.report.service.TaBiw3OprService;
import gean.pmc_report_manager.modules.report.vo.AreaOprVo;
import gean.pmc_report_manager.modules.report.vo.PageParamVo;


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
		Timestamp sTime = null;
		if(vo.getStartTime()!=null) {
			sTime = new Timestamp(vo.getStartTime().getTime());
		}
		QueryWrapper<TaBiw3OprEntity> wrapper = new QueryWrapper<>();
		wrapper.eq(StringUtils.isNotBlank(vo.getShop()),"shop", vo.getShop())
				.eq(StringUtils.isNotBlank(vo.getArea()), "area",vo.getArea())
				.eq(StringUtils.isNotBlank(vo.getShift()),"shift", vo.getShift())
				.eq("work_day", sTime);
		List<TaBiw3OprEntity> areaOprList = this.list(wrapper);
		
		List<AreaOprVo> aOprList = new ArrayList<>();
		
		if(!StringUtils.isEmpty(areaOprList)) {
			for(TaBiw3OprEntity en : areaOprList) {
				AreaOprVo avo = new AreaOprVo();
				avo.setActual(196);
				avo.setArea(en.getArea());
				avo.setEquipmentOpr(en.getActualEquipmentOpr());
				avo.setProductionOpr(en.getTargetProductionOpr()/en.getActualProductionOpr());
				avo.setShiftPlan(en.getTargetProductionOpr());
				avo.setVariation(0);
				avo.setZone(en.getZone());
				aOprList.add(avo);
			}
			return aOprList;
		}
		return null;
	}
}
