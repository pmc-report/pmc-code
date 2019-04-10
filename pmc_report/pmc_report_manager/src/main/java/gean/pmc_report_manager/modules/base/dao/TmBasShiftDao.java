package gean.pmc_report_manager.modules.base.dao;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import gean.pmc_report_manager.modules.base.entity.TmBasShiftEntity;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

/**
 * ${comments}
 * 
 * @author Jason
 * @email xxxxx@gmail.com
 * @date 2019-04-04 11:17:03
 */
@Mapper
public interface TmBasShiftDao extends BaseMapper<TmBasShiftEntity> {
	List<TmBasShiftEntity> queryShift();
}
