package gean.pmc_report_manager.modules.report.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_manager.common.utils.Query;
import gean.pmc_report_manager.modules.report.dao.TaBiw39panelDao;
import gean.pmc_report_manager.modules.report.entity.TaBiw39panelEntity;
import gean.pmc_report_manager.modules.report.service.TaBiw39panelService;


@Service("taBiw39panelService")
public class TaBiw39panelServiceImpl extends ServiceImpl<TaBiw39panelDao, TaBiw39panelEntity> implements TaBiw39panelService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TaBiw39panelEntity> page = this.page(
                new Query<TaBiw39panelEntity>().getPage(params),
                new QueryWrapper<TaBiw39panelEntity>()
        );

        return new PageUtils(page);
    }

}
