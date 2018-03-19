package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.domain.StockOutcomeBillItem;
import java.util.List;

public interface StockOutcomeBillItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StockOutcomeBillItem entity);

    void deleteByBillId(Long billId);
}