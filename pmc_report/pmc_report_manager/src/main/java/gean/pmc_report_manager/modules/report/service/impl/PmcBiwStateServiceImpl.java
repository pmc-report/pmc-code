package gean.pmc_report_manager.modules.report.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_manager.common.utils.Query;
import gean.pmc_report_manager.modules.report.dao.PmcBiwStateDao;
import gean.pmc_report_manager.modules.report.entity.PmcBiwStateEntity;
import gean.pmc_report_manager.modules.report.service.PmcBiwStateService;



@Service("pmcBiwStateService")
public class PmcBiwStateServiceImpl extends ServiceImpl<PmcBiwStateDao, PmcBiwStateEntity> implements PmcBiwStateService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PmcBiwStateEntity> page = this.page(
                new Query<PmcBiwStateEntity>().getPage(params),
                new QueryWrapper<PmcBiwStateEntity>()
        );

        return new PageUtils(page);
    }

}
