package gean.pmc_report_manager.modules.report.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_manager.modules.report.dao.PmcOprDao;
import gean.pmc_report_manager.modules.report.entity.PmcOprEntity;
import gean.pmc_report_manager.modules.report.service.PmcOprService;

@Service("pmcOprService")
public class PmcOprServiceImpl extends ServiceImpl<PmcOprDao, PmcOprEntity> implements PmcOprService{

	@Override
	public List<String> findBiw1Oprimg(Map<String, Object> params) {
		return null;
	}
	
	@Override
	 public List<PmcOprEntity> queryBiw1Opr(Map<String, Object> params){
			 return baseMapper.queryBiw1Opr(params); 
	 }

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		return null;
	}

}
