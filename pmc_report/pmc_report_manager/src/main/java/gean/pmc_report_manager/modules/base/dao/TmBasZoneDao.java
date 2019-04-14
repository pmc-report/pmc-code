package gean.pmc_report_manager.modules.base.dao;

import gean.pmc_report_manager.modules.base.entity.TmBasZoneEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * ${comments}
 * 
 * @author ''
 * @email xxxxx@gmail.com
 * @date 2019-04-13 23:24:54
 */
@Mapper
public interface TmBasZoneDao extends BaseMapper<TmBasZoneEntity> {
	
	/**
	 * 根据产线查询所有
	 * @return 集合
	 */
	List<TmBasZoneEntity> queryZoneByLine(String areaNo);
}
