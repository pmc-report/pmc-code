package gean.pmc_report_manager.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import gean.pmc_report_manager.modules.sys.entity.SysDictEntity;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * 数据字典
 *
 */
@Mapper
public interface SysDictDao extends BaseMapper<SysDictEntity> {
	
	List<SysDictEntity> selectDictDataByType(String param);
    
    String selectDictName(String dictType, String dictValue);
}
