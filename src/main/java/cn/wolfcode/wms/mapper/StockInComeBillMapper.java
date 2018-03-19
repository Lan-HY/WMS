package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.domain.StockInComeBill;
import cn.wolfcode.wms.query.StockInComeBillQueryObject;

import java.util.List;

public interface StockInComeBillMapper {
    int deleteByPrimaryKey(Long id);

    int insert(StockInComeBill entity);

    StockInComeBill selectByPrimaryKey(Long id);

    List<StockInComeBill> selectAll();

    int updateByPrimaryKey(StockInComeBill entity);

    List<StockInComeBill> queryForList(StockInComeBillQueryObject qo);

    int queryForCount(StockInComeBillQueryObject qo);

    void audit(StockInComeBill stockInComeBill);
}