package gean.pmc_report_api.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import gean.pmc_report_api.entity.UserEntity;

import org.apache.ibatis.annotations.Mapper;

/**
 * 用户
 *
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {

}
