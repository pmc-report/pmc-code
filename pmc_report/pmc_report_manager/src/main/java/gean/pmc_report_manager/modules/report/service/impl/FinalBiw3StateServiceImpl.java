package gean.pmc_report_manager.modules.report.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_manager.common.utils.Query;
import gean.pmc_report_manager.modules.report.dao.FinalBiw3StateDao;
import gean.pmc_report_manager.modules.report.entity.FinalBiw3StateEntity;
import gean.pmc_report_manager.modules.report.service.FinalBiw3StateService;


@Service("finalBiw3StateService")
public class FinalBiw3StateServiceImpl extends ServiceImpl<FinalBiw3StateDao, FinalBiw3StateEntity> implements FinalBiw3StateService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<FinalBiw3StateEntity> page = this.page(
                new Query<FinalBiw3StateEntity>().getPage(params),
                new QueryWrapper<FinalBiw3StateEntity>()
        );

        return new PageUtils(page);
    }

}
