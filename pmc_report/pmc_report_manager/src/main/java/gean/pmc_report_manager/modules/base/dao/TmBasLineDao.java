package gean.pmc_report_manager.modules.base.dao;

import gean.pmc_report_datasource.annotation.DataSource;
import gean.pmc_report_manager.modules.base.entity.TmBasLineEntity;
import gean.pmc_report_manager.modules.base.entity.TmBasUlocEntity;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

/**
 * 产线基础信息表
 * 
 * @author Jason
 * @email xxxxx@gmail.com
 * @date 2019-03-22 09:53:33
 */
@Mapper
public interface TmBasLineDao extends BaseMapper<TmBasLineEntity> {
	
	/**
	 * 根据产线查询所有
	 * @return 集合
	 */
	@Transactional
	@DataSource("slave1")
	List<TmBasLineEntity> queryLineByShop(String shopNo);
}
