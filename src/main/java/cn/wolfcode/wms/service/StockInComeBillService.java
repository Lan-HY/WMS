package cn.wolfcode.wms.service;

import cn.wolfcode.wms.domain.StockInComeBill;
import cn.wolfcode.wms.query.StockInComeBillQueryObject;
import cn.wolfcode.wms.query.PageResult;

import java.util.List;

public interface StockInComeBillService {

    void saveOrUpdate(StockInComeBill entity);

    void delete(Long id);

    StockInComeBill get(Long id);

    List<StockInComeBill> list();

    PageResult query(StockInComeBillQueryObject qo);

    void audit(Long id);
}
