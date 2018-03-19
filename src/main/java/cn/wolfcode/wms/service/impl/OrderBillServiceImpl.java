package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.OrderBill;
import cn.wolfcode.wms.domain.OrderBillItem;
import cn.wolfcode.wms.mapper.OrderBillItemMapper;
import cn.wolfcode.wms.mapper.OrderBillMapper;
import cn.wolfcode.wms.query.OrderBillQueryObject;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.OrderBillService;
import cn.wolfcode.wms.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class OrderBillServiceImpl implements OrderBillService {

    @Autowired
    private OrderBillMapper orderBillMapper;

    @Autowired
    private OrderBillItemMapper orderBillItemMapper;

    public void saveOrUpdate(OrderBill entity) {
        entity.setInputUser(UserContext.getCurrentUser());
        entity.setInputTime(new Date());
        BigDecimal totalNumber = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderBillItem> items = entity.getItems();
        for (OrderBillItem item : items) {
            totalNumber = totalNumber.add(item.getNumber());
            totalAmount = totalAmount.add(item.getCostPrice().multiply(item.getNumber()));
        }
        entity.setTotalNumber(totalNumber);
        entity.setTotalAmount(totalAmount);
        if (entity.getId() == null) {

            orderBillMapper.insert(entity);
        } else {
            orderBillMapper.updateByPrimaryKey(entity);
            orderBillItemMapper.deleteByBillId(entity.getId());
        }
        for (OrderBillItem item : items) {
            item.setAmount(item.getCostPrice().multiply(item.getNumber()));
            item.setBillId(entity.getId());
            orderBillItemMapper.insert(item);
        }
    }

    public void delete(Long id) {
        if (id != null) {
            orderBillMapper.deleteByPrimaryKey(id);
        }
    }

    public OrderBill get(Long id) {
        return orderBillMapper.selectByPrimaryKey(id);
    }

    public List<OrderBill> list() {
        return orderBillMapper.selectAll();
    }

    public PageResult query(OrderBillQueryObject qo) {
        int totalCount = orderBillMapper.queryForCount(qo);
        if (totalCount == 0) {
            return PageResult.EMPTY_PAGE;
        }
        List<OrderBill> data = orderBillMapper.queryForList(qo);
        return new PageResult(data, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }

    public void audit(Long id) {
        OrderBill orderBill = orderBillMapper.selectByPrimaryKey(id);
        if(orderBill.getStatus() == OrderBill.STATUS_NOMAL) {
            orderBill.setStatus(OrderBill.STATUS_AUDIT);
            orderBill.setAuditor(UserContext.getCurrentUser());
            orderBill.setAuditTime(new Date());
            orderBillMapper.audit(orderBill);
        }
    }
}
