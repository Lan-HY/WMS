package cn.wolfcode.wms.service;

import cn.wolfcode.wms.domain.OrderBill;
import cn.wolfcode.wms.query.OrderBillQueryObject;
import cn.wolfcode.wms.query.PageResult;

import java.util.List;

public interface OrderBillService {

    void saveOrUpdate(OrderBill entity);

    void delete(Long id);

    OrderBill get(Long id);

    List<OrderBill> list();

    PageResult query(OrderBillQueryObject qo);

    void audit(Long id);
}
