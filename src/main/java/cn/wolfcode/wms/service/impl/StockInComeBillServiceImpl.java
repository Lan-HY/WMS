package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.ProductStock;
import cn.wolfcode.wms.domain.StockInComeBill;
import cn.wolfcode.wms.domain.StockInComeBillItem;
import cn.wolfcode.wms.mapper.ProductStockMapper;
import cn.wolfcode.wms.mapper.StockInComeBillItemMapper;
import cn.wolfcode.wms.mapper.StockInComeBillMapper;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.StockInComeBillQueryObject;
import cn.wolfcode.wms.service.StockInComeBillService;
import cn.wolfcode.wms.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class StockInComeBillServiceImpl implements StockInComeBillService {

    @Autowired
    private StockInComeBillMapper stockInComeBillMapper;

    @Autowired
    private StockInComeBillItemMapper stockInComeBillItemMapper;

    @Autowired
    private ProductStockMapper productStockMapper;

    public void saveOrUpdate(StockInComeBill entity) {
        entity.setInputUser(UserContext.getCurrentUser());
        entity.setInputTime(new Date());
        BigDecimal totalNumber = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<StockInComeBillItem> items = entity.getItems();
        for (StockInComeBillItem item : items) {
            totalNumber = totalNumber.add(item.getNumber());
            totalAmount = totalAmount.add(item.getCostPrice().multiply(item.getNumber())).setScale(2,BigDecimal.ROUND_HALF_UP);
        }
        entity.setTotalNumber(totalNumber);
        entity.setTotalAmount(totalAmount);
        if (entity.getId() == null) {
            stockInComeBillMapper.insert(entity);
        } else {
            stockInComeBillMapper.updateByPrimaryKey(entity);
            stockInComeBillItemMapper.deleteByBillId(entity.getId());
        }
        for (StockInComeBillItem item : items) {
            item.setAmount(item.getCostPrice().multiply(item.getNumber()).setScale(2, BigDecimal.ROUND_HALF_UP));
            item.setBillId(entity.getId());
            stockInComeBillItemMapper.insert(item);
        }
    }

    public void delete(Long id) {
        if (id != null) {
            stockInComeBillMapper.deleteByPrimaryKey(id);
        }
    }

    public StockInComeBill get(Long id) {
        return stockInComeBillMapper.selectByPrimaryKey(id);
    }

    public List<StockInComeBill> list() {
        return stockInComeBillMapper.selectAll();
    }

    public PageResult query(StockInComeBillQueryObject qo) {
        int totalCount = stockInComeBillMapper.queryForCount(qo);
        if (totalCount == 0) {
            return PageResult.EMPTY_PAGE;
        }
        List<StockInComeBill> data = stockInComeBillMapper.queryForList(qo);
        return new PageResult(data, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }

    public void audit(Long id) {
        StockInComeBill stockInComeBill = stockInComeBillMapper.selectByPrimaryKey(id);
        if(stockInComeBill.getStatus() == StockInComeBill.STATUS_NOMAL) {
            stockInComeBill.setStatus(StockInComeBill.STATUS_AUDIT);
            stockInComeBill.setAuditor(UserContext.getCurrentUser());
            stockInComeBill.setAuditTime(new Date());
            stockInComeBillMapper.audit(stockInComeBill);
        }

        List<StockInComeBillItem> items = stockInComeBill.getItems();
        for (StockInComeBillItem item : items) {
            ProductStock productStock = productStockMapper.selectByProductIdAndDepotId(item.getProduct().getId(),
                    stockInComeBill.getDepot().getId());
            if(productStock == null) {
                productStock = new ProductStock();
                productStock.setPrice(item.getCostPrice());
                productStock.setStoreNumber(item.getNumber());
                productStock.setAmount(item.getAmount());
                productStock.setProduct(item.getProduct());
                productStock.setDepot(stockInComeBill.getDepot());
                productStockMapper.insert(productStock);
            } else {
                productStock.setStoreNumber(productStock.getStoreNumber().add(item.getNumber()));
                productStock.setPrice(item.getAmount().add(productStock.getAmount()).divide(productStock.getStoreNumber(), 2, BigDecimal.ROUND_HALF_UP));
                productStock.setAmount(productStock.getPrice().multiply(productStock.getStoreNumber()).setScale(2,BigDecimal.ROUND_HALF_UP));
                productStockMapper.updateByPrimaryKey(productStock);
            }
        }

    }
}
