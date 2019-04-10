package gean.pmc_report_manager.modules.report.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_manager.common.utils.Query;
import gean.pmc_report_manager.modules.report.dao.FinalBiw3OprDao;
import gean.pmc_report_manager.modules.report.entity.FinalBiw3OprEntity;
import gean.pmc_report_manager.modules.report.service.FinalBiw3OprService;


@Service("finalBiw3OprService")
public class FinalBiw3OprServiceImpl extends 
	ServiceImpl<FinalBiw3OprDao, FinalBiw3OprEntity> implements FinalBiw3OprService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<FinalBiw3OprEntity> page = this.page(
                new Query<FinalBiw3OprEntity>().getPage(params),
                new QueryWrapper<FinalBiw3OprEntity>()
        );

        return new PageUtils(page);
    }

}
