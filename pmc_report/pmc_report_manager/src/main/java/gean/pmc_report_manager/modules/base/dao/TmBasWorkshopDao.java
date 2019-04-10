package gean.pmc_report_manager.modules.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import gean.pmc_report_datasource.annotation.DataSource;
import gean.pmc_report_manager.modules.base.entity.TmBasWorkshopEntity;

/**
 * 车间基本信息
 * 
 * @author Jason
 * @email xxxxx@gmail.com
 * @date 2019-03-19 16:45:21
 */
@Mapper
public interface TmBasWorkshopDao extends BaseMapper<TmBasWorkshopEntity> {
	
	/**
	 * 查询所有
	 * @return 集合
	 */
	@Transactional
	@DataSource("slave1")
	List<TmBasWorkshopEntity> queryAll();
}
