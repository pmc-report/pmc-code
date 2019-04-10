package gean.pmc_report_manager.modules.report.service.impl;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import gean.pmc_report_common.common.utils.DateUtils;
import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_common.common.utils.StringUtils;
import gean.pmc_report_manager.common.utils.Query;
import gean.pmc_report_manager.modules.report.dao.TaEquFaultDao;
import gean.pmc_report_manager.modules.report.entity.TaEquFaultEntity;
import gean.pmc_report_manager.modules.report.service.EquFaultService;
import gean.pmc_report_manager.modules.report.vo.PageParamVo;


@Service("taEquFaultService")
public class EquFaultServiceImpl extends ServiceImpl<TaEquFaultDao, TaEquFaultEntity> implements EquFaultService {

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
		//DateUtils.format(vo.getStartTime(),DateUtils.DATE_TIME_PATTERN );
		Timestamp tp = new Timestamp(vo.getStartTime().getTime());
		System.out.println(tp);
		IPage<TaEquFaultEntity> page = 
				this.page( new Query<TaEquFaultEntity>().getPage(params),
						new QueryWrapper<TaEquFaultEntity>().eq(StringUtils.isNotBlank(vo.getArea()), "area", vo.getArea())
															.eq(StringUtils.isNotBlank(vo.getLine()), "line", vo.getLine())
															.eq(StringUtils.isNotBlank(vo.getZone()), "zone", vo.getZone())
															.eq(StringUtils.isNotBlank(vo.getStation()), "station", vo.getStation())
															.eq(StringUtils.isNotBlank(vo.getJobId()), "jobId", vo.getJobId())
															.ge(StringUtils.isNotNull(vo.getStartTime()), "start_Time", vo.getStartTime())
															.le(StringUtils.isNotNull(vo.getEndTime()), "end_Time", vo.getEndTime()));
		
		return new PageUtils(page);
	}

}
