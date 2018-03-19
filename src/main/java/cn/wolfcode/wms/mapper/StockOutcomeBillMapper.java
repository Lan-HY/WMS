package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.domain.StockOutcomeBill;
import cn.wolfcode.wms.query.StockOutcomeBillQueryObject;

import java.util.List;

public interface StockOutcomeBillMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StockOutcomeBill entity);

    StockOutcomeBill selectByPrimaryKey(Long id);

    List<StockOutcomeBill> selectAll();

    int updateByPrimaryKey(StockOutcomeBill entity);

    List<StockOutcomeBill> queryForList(StockOutcomeBillQueryObject qo);

    int queryForCount(StockOutcomeBillQueryObject qo);

    void audit(StockOutcomeBill stockOutcomeBill);
}