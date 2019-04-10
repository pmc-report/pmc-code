package gean.pmc_report_manager.modules.sys.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;

import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_manager.modules.sys.entity.SysDictEntity;

/**
 * 数据字典
 *
 */
public interface SysDictService extends IService<SysDictEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
    List<SysDictEntity> getType(String param);
    
    String getLabel(String dictType, String dictValue);
}

