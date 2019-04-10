package gean.pmc_report_manager.modules.sys.service;


import com.baomidou.mybatisplus.extension.service.IService;

import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_manager.modules.sys.entity.SysRoleEntity;

import java.util.Map;


/**
 * 角色
 *
 */
public interface SysRoleService extends IService<SysRoleEntity> {

	PageUtils queryPage(Map<String, Object> params);

	void saveRole(SysRoleEntity role);

	void update(SysRoleEntity role);
	
	void deleteBatch(Long[] roleIds);

}
