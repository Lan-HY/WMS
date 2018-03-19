package cn.wolfcode.wms.service;

import cn.wolfcode.wms.domain.StockOutcomeBill;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.StockOutcomeBillQueryObject;

import java.util.List;

public interface StockOutcomeBillService {

    void saveOrUpdate(StockOutcomeBill entity);

    void delete(Long id);

    StockOutcomeBill get(Long id);

    List<StockOutcomeBill> list();

    PageResult query(StockOutcomeBillQueryObject qo);

    void audit(Long id);
}
