package gean.pmc_report_manager.modules.base.dao;

import gean.pmc_report_datasource.annotation.DataSource;
import gean.pmc_report_manager.modules.base.entity.TmBasUlocEntity;
import gean.pmc_report_manager.modules.base.entity.TmBasWorkshopEntity;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

/**
 * 工位信息
 * 
 * @author Jason
 * @email xxxxx@gmail.com
 * @date 2019-03-22 09:54:06
 */
@Mapper
public interface TmBasUlocDao extends BaseMapper<TmBasUlocEntity> {
	
	/**
	 * 根据产线查询所有
	 * @return 集合
	 */
	@Transactional
	@DataSource("slave1")
	List<TmBasUlocEntity> queryUlocByLine(String areaNo);
}
