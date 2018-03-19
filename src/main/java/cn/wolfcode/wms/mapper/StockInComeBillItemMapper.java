package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.domain.StockInComeBillItem;

public interface StockInComeBillItemMapper {

    int deleteByPrimaryKey(Long id);

    int insert(StockInComeBillItem entity);

    void deleteByBillId(Long billId);
}