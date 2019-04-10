package gean.pmc_report_manager.modules.base.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_manager.common.utils.Query;
import gean.pmc_report_manager.modules.base.dao.TmBasLineDao;
import gean.pmc_report_manager.modules.base.entity.TmBasLineEntity;
import gean.pmc_report_manager.modules.base.service.TmBasLineService;


@Service("tmBasLineService")
public class TmBasLineServiceImpl extends ServiceImpl<TmBasLineDao, TmBasLineEntity> implements TmBasLineService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TmBasLineEntity> page = this.page(
                new Query<TmBasLineEntity>().getPage(params),
                new QueryWrapper<TmBasLineEntity>()
        );

        return new PageUtils(page);
    }

	@Override
	public List<TmBasLineEntity> queryLine(String shop) {
		// TODO Auto-generated method stub
		return baseMapper.queryLineByShop(shop);
	}

}
