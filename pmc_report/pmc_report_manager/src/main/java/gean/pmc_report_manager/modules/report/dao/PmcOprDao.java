package gean.pmc_report_manager.modules.report.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import gean.pmc_report_manager.modules.report.entity.PmcOprEntity;
import gean.pmc_report_manager.modules.report.vo.OprReportVo;

/**
 * ${comments}
 * 
 * @author Jason
 * @email xxxxx@gmail.com
 * @date 2019-03-14 13:22:17
 */
@Mapper
public interface PmcOprDao extends BaseMapper<PmcOprEntity> {
	
	/**
	 * 根据页面参数代理类查询OPR报表
	 * @param 页面参数代理类
	 * @retuen 根据参数查询到的结果集
	 */
	List<PmcOprEntity> queryOprByParam(Map<String,Object> param);
}
