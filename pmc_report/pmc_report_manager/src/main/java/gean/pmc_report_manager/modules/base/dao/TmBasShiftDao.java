package gean.pmc_report_manager.modules.base.dao;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import gean.pmc_report_datasource.annotation.DataSource;
import gean.pmc_report_manager.modules.base.entity.TmBasShiftEntity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

/**
 * ${comments}
 * 
 * @author Jason
 * @email xxxxx@gmail.com
 * @date 2019-04-04 11:17:03
 */
@Mapper
public interface TmBasShiftDao extends BaseMapper<TmBasShiftEntity> {
	
	@Transactional
    @DataSource("slave1")
	List<String> queryShift();
}
