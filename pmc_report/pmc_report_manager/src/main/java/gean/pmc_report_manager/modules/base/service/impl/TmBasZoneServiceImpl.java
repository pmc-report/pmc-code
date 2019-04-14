package gean.pmc_report_manager.modules.base.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_manager.common.utils.Query;
import gean.pmc_report_manager.modules.base.dao.TmBasZoneDao;
import gean.pmc_report_manager.modules.base.entity.TmBasZoneEntity;
import gean.pmc_report_manager.modules.base.service.TmBasZoneService;


@Service("tmBasZoneService")
public class TmBasZoneServiceImpl extends ServiceImpl<TmBasZoneDao, TmBasZoneEntity> implements TmBasZoneService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TmBasZoneEntity> page = this.page(
                new Query<TmBasZoneEntity>().getPage(params),
                new QueryWrapper<TmBasZoneEntity>()
        );

        return new PageUtils(page);
    }

	@Override
	public List<TmBasZoneEntity> queryZone(String lineNo) {
		// TODO Auto-generated method stub
		List<TmBasZoneEntity> list = baseMapper.queryZoneByLine(lineNo);
		return list;
	}
}
