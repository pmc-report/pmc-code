package gean.pmc_report_manager.modules.report.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import gean.pmc_report_manager.modules.report.entity.PmcOprEntity;

@Mapper
public interface PmcOprDao extends BaseMapper<PmcOprEntity>{

	List<PmcOprEntity> queryBiw1Opr(Map<String, Object> params);
	
	List<PmcOprEntity> queryOprByParam(Map<String,String> params);

}
