package cn.wolfcode.wms.service;

import cn.wolfcode.wms.query.QueryObject;

import java.util.List;
import java.util.Map;


public interface ChartService {

    List<Map<String, Object>> queryOrderChart(QueryObject qo);

    List<Map<String, Object>> querySaleChart(QueryObject qo);

}
