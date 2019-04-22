package gean.pmc_report_manager.modules.base.service.impl;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_common.common.utils.StringUtils;
import gean.pmc_report_manager.common.utils.Query;
import gean.pmc_report_manager.modules.base.dao.AtPmcMasterdataConfigDao;
import gean.pmc_report_manager.modules.base.entity.AtPmcMasterdataConfigEntity;
import gean.pmc_report_manager.modules.base.service.AtPmcMasterdataConfigService;
import gean.pmc_report_manager.modules.report.vo.MasterDataVo;


@Service("atPmcMasterdataConfigService")
public class AtPmcMasterdataConfigServiceImpl extends ServiceImpl<AtPmcMasterdataConfigDao, AtPmcMasterdataConfigEntity> implements AtPmcMasterdataConfigService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AtPmcMasterdataConfigEntity> page = this.page(
                new Query<AtPmcMasterdataConfigEntity>().getPage(params),
                new QueryWrapper<AtPmcMasterdataConfigEntity>()
        );

        return new PageUtils(page);
    }

	@Override
	public List<MasterDataVo> queryMasterDataShop() {
		// TODO Auto-generated method stub
		List<MasterDataVo> shopList = baseMapper.queryAllShop();
		return shopList;
	}

	@Override
	public List<MasterDataVo> queryMasterDataLine(String shopNo) {
		// TODO Auto-generated method stub
		List<MasterDataVo> lineList = baseMapper.queryAllLineForShop(shopNo);
		return lineList;
	}

	@Override
	public List<MasterDataVo> queryMasterDataZone(Map<String, Object> params) {
		// TODO Auto-generated method stub
		List<MasterDataVo> zoneList = baseMapper.queryAllZoneForLine(params);
		return zoneList;
	}

	@Override
	public List<MasterDataVo> queryMasterDataStation(Map<String, Object> params) {
		// TODO Auto-generated method stub
		List<MasterDataVo> stationList = baseMapper.queryAllStationForZone(params);
		return stationList;
	}

	@Override
	public List<MasterDataVo> queryMasterDataEquipment(Map<String, Object> params) {
		// TODO Auto-generated method stub
		List<MasterDataVo> equList = baseMapper.queryEquipmentForStation(params);
		return equList;
	}

	@Override
	public List<String> queryDates(String params) {
		// TODO Auto-generated method stub
		if(StringUtils.isNotEmpty(params)) {
			List<String> dateList = baseMapper.queryDates(params);
			return dateList;
		}
		return null;
	}

}
