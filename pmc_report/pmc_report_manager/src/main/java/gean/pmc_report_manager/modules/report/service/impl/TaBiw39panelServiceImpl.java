package gean.pmc_report_manager.modules.report.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import gean.pmc_report_common.common.utils.PageUtils;
import gean.pmc_report_manager.common.utils.Query;
import gean.pmc_report_manager.modules.report.dao.TaBiw39panelDao;
import gean.pmc_report_manager.modules.report.entity.TaBiw39panelEntity;
import gean.pmc_report_manager.modules.report.service.TaBiw39panelService;
import gean.pmc_report_manager.modules.report.vo.PageParamVo;
import gean.pmc_report_manager.modules.report.vo.PanelVo;


@Service("taBiw39panelService")
public class TaBiw39panelServiceImpl extends ServiceImpl<TaBiw39panelDao, TaBiw39panelEntity> implements TaBiw39panelService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TaBiw39panelEntity> page = this.page(
                new Query<TaBiw39panelEntity>().getPage(params),
                new QueryWrapper<TaBiw39panelEntity>()
        );

        return new PageUtils(page);
    }

	@Override
	public List<PanelVo> queryEchart(Map<String, Object> params) {
		// TODO Auto-generated method stub
		PageParamVo vo = new PageParamVo(params);
		List<PanelVo> list = baseMapper.queryEchart(vo);
		
		DecimalFormat df = new DecimalFormat("##0.00");
		
		PanelVo _new = null;
		
		List<PanelVo> resultList = new ArrayList<>();
		
		//组装数组
		for(PanelVo panel : list) {
			if(panel!=null) {
				_new = new PanelVo();
				if(panel.getMonday()!=null) {
					_new.setMonday(panel.getMonday());
				}
				if(panel.getTav()!=0) {
					_new.setTargetTav(panel.getTargetTav());
					_new.setTav(Float.parseFloat(df.format(panel.getTav())));
				}
				
				if(panel.getMtbf()!=0) {
					_new.setTargetMtbf(Float.parseFloat(df.format(panel.getTargetMtbf())));
					_new.setMtbf(panel.getMtbf());
				}
				_new.setStartTime(panel.getStartTime());
				_new.setWeek(panel.getWeek());
				_new.setWeekRange(panel.getWeekRange());
				_new.setWeekNo(panel.getWeekNo());
				resultList.add(_new);
			}
		}
		
		Collections.sort(resultList, new Comparator<PanelVo>() {
			@Override
			public int compare(PanelVo o1, PanelVo o2) {
				// TODO Auto-generated method stub
				int i = o1.getWeekRange() - o2.getWeekRange();
				return i;
			}
		});
		return resultList;
	}



	@Override
	public List<PanelVo> queryTop10DownTime(Map<String, Object> params) {
		// TODO Auto-generated method stub
		PageParamVo vo = new PageParamVo(params);
		List<PanelVo> list = baseMapper.queryTop10DownTime(vo);
		
		return null;
	}
	
}
