package gean.pmc_report_manager.modules.base.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_manager.common.utils.Query;
import gean.pmc_report_manager.modules.base.dao.TmBasWorkshopDao;
import gean.pmc_report_manager.modules.base.entity.TmBasWorkshopEntity;
import gean.pmc_report_manager.modules.base.service.TmBasWorkshopService;

@Service("tmBasWorkshopService")
public class TmBasWorkshopServiceImpl extends ServiceImpl<TmBasWorkshopDao, TmBasWorkshopEntity> implements TmBasWorkshopService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TmBasWorkshopEntity> page = this.page(
                new Query<TmBasWorkshopEntity>().getPage(params),
                new QueryWrapper<TmBasWorkshopEntity>()
        );

        return new PageUtils(page);
    }

	@Override
	public List<TmBasWorkshopEntity> queryAllShop() {
		return baseMapper.queryAll();
	}

}
