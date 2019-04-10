package gean.pmc_report_api.controller;



import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import gean.pmc_report_api.annotation.Login;
import gean.pmc_report_api.form.LoginForm;
import gean.pmc_report_api.service.TokenService;
import gean.pmc_report_api.service.UserService;
import gean.pmc_report_common.common.utils.R;
import gean.pmc_report_common.common.validator.ValidatorUtils;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * 登录接口
 */
@RestController
@RequestMapping("/api")
@Api(tags="登录接口")
public class ApiLoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;


    @PostMapping("login")
    @ApiOperation("登录")
    public R login(@RequestBody LoginForm form){
        //表单校验
        ValidatorUtils.validateEntity(form);

        //用户登录
        Map<String, Object> map = userService.login(form);

        return R.ok(map);
    }

    @Login
    @PostMapping("logout")
    @ApiOperation("退出")
    public R logout(@ApiIgnore @RequestAttribute("userId") long userId){
        tokenService.expireToken(userId);
        return R.ok();
    }

}
