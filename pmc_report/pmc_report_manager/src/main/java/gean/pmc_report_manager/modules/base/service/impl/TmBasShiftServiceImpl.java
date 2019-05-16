package gean.pmc_report_manager.modules.base.service.impl;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_manager.common.utils.Query;
import gean.pmc_report_manager.modules.base.dao.TmBasShiftDao;
import gean.pmc_report_manager.modules.base.entity.TmBasShiftEntity;
import gean.pmc_report_manager.modules.base.service.TmBasShiftService;



@Service("tmBasShiftService")
public class TmBasShiftServiceImpl extends ServiceImpl<TmBasShiftDao, TmBasShiftEntity> implements TmBasShiftService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TmBasShiftEntity> page = this.page(
                new Query<TmBasShiftEntity>().getPage(params),
                new QueryWrapper<TmBasShiftEntity>()
        );

        return new PageUtils(page);
    }
    
    /**
     * 查询所有班次信息
     */
    public List<String> queryShift() {
    	
    	List<String> shiftList=baseMapper.queryShift();
    	
    	if(shiftList!=null) {
    		return shiftList;
    	}
    	return null;
	
    }
    
    
    
    

}
