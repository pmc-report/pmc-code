package gean.pmc_report_manager.modules.sys.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import gean.pmc_report_common.common.utils.RedisUtils;
import gean.pmc_report_manager.common.utils.RedisKeys;
import gean.pmc_report_manager.modules.sys.entity.SysConfigEntity;

/**
 * 系统配置Redis
 *
 */
@Component
public class SysConfigRedis {
	
	@Autowired
    private RedisUtils redisUtils;

    public void saveOrUpdate(SysConfigEntity config) {
        if(config == null){
            return ;
        }
        String key = RedisKeys.getSysConfigKey(config.getParamKey());
        redisUtils.set(key, config);
    }

    public void delete(String configKey) {
        String key = RedisKeys.getSysConfigKey(configKey);
        redisUtils.delete(key);
    }

    public SysConfigEntity get(String configKey){
        String key = RedisKeys.getSysConfigKey(configKey);
        return redisUtils.get(key, SysConfigEntity.class);
    }
}
