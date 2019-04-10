package gean.pmc_report_manager.modules.base.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_manager.common.utils.Query;
import gean.pmc_report_manager.modules.base.dao.TmBasUlocDao;
import gean.pmc_report_manager.modules.base.entity.TmBasUlocEntity;
import gean.pmc_report_manager.modules.base.service.TmBasUlocService;


@Service("tmBasUlocService")
public class TmBasUlocServiceImpl extends ServiceImpl<TmBasUlocDao, TmBasUlocEntity> implements TmBasUlocService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TmBasUlocEntity> page = this.page(
                new Query<TmBasUlocEntity>().getPage(params),
                new QueryWrapper<TmBasUlocEntity>()
        );

        return new PageUtils(page);
    }

	@Override
	public List<TmBasUlocEntity> queryUloc(String lineNo) {
		// TODO Auto-generated method stub
		List<TmBasUlocEntity> list = baseMapper.queryUlocByLine(lineNo);
		return list;
	}

}
