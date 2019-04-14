package gean.pmc_report_manager.modules.report.dao;

import gean.pmc_report_manager.modules.report.entity.TaBiw3StateEntity;
import gean.pmc_report_manager.modules.report.vo.AreaOprVo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

/**
 * ${comments}
 * 
 * @author ''
 * @email xxxxx@gmail.com
 * @date 2019-04-11 10:56:37
 */
@Mapper
public interface TaBiw3StateDao extends BaseMapper<TaBiw3StateEntity> {
	
	List<TaBiw3StateEntity> queryZoneOpr(Map<String, Object> params);
}
