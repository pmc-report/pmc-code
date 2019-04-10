package gean.pmc_report_api.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import gean.pmc_report_api.entity.TokenEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Token
 *
 */
@Mapper
public interface TokenDao extends BaseMapper<TokenEntity> {
	
}
