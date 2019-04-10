package gean.pmc_report_manager.modules.oss.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import gean.pmc_report_manager.modules.oss.entity.SysOssEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文件上传
 *
 */
@Mapper
public interface SysOssDao extends BaseMapper<SysOssEntity> {
	
}
