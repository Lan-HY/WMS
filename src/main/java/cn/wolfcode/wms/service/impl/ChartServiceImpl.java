package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.mapper.ChartMapper;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ChartServiceImpl implements ChartService {

    @Autowired
    private ChartMapper chartMapper;

    public List<Map<String, Object>> queryOrderChart(QueryObject qo) {
        return chartMapper.queryOrderChart(qo);
    }

    public List<Map<String, Object>> querySaleChart(QueryObject qo) {
        return chartMapper.querySaleChart(qo);
    }
}
