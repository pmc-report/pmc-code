package gean.pmc_report_manager.modules.base.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import gean.pmc_report_manager.modules.base.entity.TmBasModelEntity;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * ${comments}
 * 
 * @author Jason
 * @email xxxxx@gmail.com
 * @date 2019-04-03 14:00:04
 */
@Mapper
public interface TmBasModelDao extends BaseMapper<TmBasModelEntity> {
	List<TmBasModelEntity>queryJobId(Map<String, Object> params);
}
