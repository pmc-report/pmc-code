package gean.pmc_report_manager.modules.base.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_manager.common.utils.Query;
import gean.pmc_report_manager.modules.base.dao.TmBasModelDao;
import gean.pmc_report_manager.modules.base.entity.TmBasModelEntity;
import gean.pmc_report_manager.modules.base.service.TmBasModelService;


@Service("tmBasModelService")
public class TmBasModelServiceImpl extends ServiceImpl<TmBasModelDao, TmBasModelEntity> implements TmBasModelService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TmBasModelEntity> page = this.page(
                new Query<TmBasModelEntity>().getPage(params),
                new QueryWrapper<TmBasModelEntity>()
        );

        return new PageUtils(page);
    }

	@Override
	public List<TmBasModelEntity> queryJobId(Map<String, Object> params) {
		List<TmBasModelEntity>jobId=baseMapper.queryJobId(params);
		return jobId;
	}

}
