package gean.pmc_report_api.service;

import com.baomidou.mybatisplus.extension.service.IService;

import gean.pmc_report_api.entity.UserEntity;
import gean.pmc_report_api.form.LoginForm;

import java.util.Map;

/**
 * 用户
 *
 */
public interface UserService extends IService<UserEntity> {

	UserEntity queryByMobile(String mobile);

	/**
	 * 用户登录
	 * @param form    登录表单
	 * @return        返回登录信息
	 */
	Map<String, Object> login(LoginForm form);
}
