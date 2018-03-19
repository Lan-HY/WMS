package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.ProductStock;
import cn.wolfcode.wms.domain.SaleAccount;
import cn.wolfcode.wms.domain.StockOutcomeBill;
import cn.wolfcode.wms.domain.StockOutcomeBillItem;
import cn.wolfcode.wms.exception.LogicException;
import cn.wolfcode.wms.mapper.ProductStockMapper;
import cn.wolfcode.wms.mapper.SaleAccountMapper;
import cn.wolfcode.wms.mapper.StockOutcomeBillItemMapper;
import cn.wolfcode.wms.mapper.StockOutcomeBillMapper;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.StockOutcomeBillQueryObject;
import cn.wolfcode.wms.service.StockOutcomeBillService;
import cn.wolfcode.wms.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class StockOutcomeBillServiceImpl implements StockOutcomeBillService {

    @Autowired
    private StockOutcomeBillMapper stockOutcomeBillMapper;

    @Autowired
    private StockOutcomeBillItemMapper stockOutcomeBillItemMapper;

    @Autowired
    private ProductStockMapper productStockMapper;

    @Autowired
    private SaleAccountMapper saleAccountMapper;

    public void saveOrUpdate(StockOutcomeBill entity) {
        entity.setInputUser(UserContext.getCurrentUser());
        entity.setInputTime(new Date());
        BigDecimal totalNumber = BigDecimal.ZERO;
        BigDecimal totalAmount = BigDecimal.ZERO;
        List<StockOutcomeBillItem> items = entity.getItems();
        for (StockOutcomeBillItem item : items) {
            totalNumber = totalNumber.add(item.getNumber());
            totalAmount = totalAmount.add(item.getSalePrice().multiply(item.getNumber())).setScale(2,BigDecimal.ROUND_HALF_UP);
        }
        entity.setTotalNumber(totalNumber);
        entity.setTotalAmount(totalAmount);
        if (entity.getId() == null) {
            stockOutcomeBillMapper.insert(entity);
        } else {
            stockOutcomeBillMapper.updateByPrimaryKey(entity);
            stockOutcomeBillItemMapper.deleteByBillId(entity.getId());
        }
        for (StockOutcomeBillItem item : items) {
            item.setAmount(item.getSalePrice().multiply(item.getNumber()).setScale(2, BigDecimal.ROUND_HALF_UP));
            item.setBillId(entity.getId());
            stockOutcomeBillItemMapper.insert(item);
        }
    }

    public void delete(Long id) {
        if (id != null) {
            stockOutcomeBillMapper.deleteByPrimaryKey(id);
        }
    }

    public StockOutcomeBill get(Long id) {
        return stockOutcomeBillMapper.selectByPrimaryKey(id);
    }

    public List<StockOutcomeBill> list() {
        return stockOutcomeBillMapper.selectAll();
    }

    public PageResult query(StockOutcomeBillQueryObject qo) {
        int totalCount = stockOutcomeBillMapper.queryForCount(qo);
        if (totalCount == 0) {
            return PageResult.EMPTY_PAGE;
        }
        List<StockOutcomeBill> data = stockOutcomeBillMapper.queryForList(qo);
        return new PageResult(data, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }

    public void audit(Long id) {
        StockOutcomeBill stockOutcomeBill = stockOutcomeBillMapper.selectByPrimaryKey(id);
        if(stockOutcomeBill.getStatus() == StockOutcomeBill.STATUS_NOMAL) {
            stockOutcomeBill.setStatus(StockOutcomeBill.STATUS_AUDIT);
            stockOutcomeBill.setAuditor(UserContext.getCurrentUser());
            stockOutcomeBill.setAuditTime(new Date());
            stockOutcomeBillMapper.audit(stockOutcomeBill);
        }

        List<StockOutcomeBillItem> items = stockOutcomeBill.getItems();
        for (StockOutcomeBillItem item : items) {
            ProductStock productStock = productStockMapper.selectByProductIdAndDepotId(item.getProduct().getId(),
                    stockOutcomeBill.getDepot().getId());
            if(productStock == null) {
                throw new LogicException("商品[" + item.getProduct().getName() + "]在仓库[" +
                        stockOutcomeBill.getDepot().getName() + "]中不存在");
            }
            if (item.getNumber().compareTo(productStock.getStoreNumber()) > 0) {
                throw new LogicException("商品[" + item.getProduct().getName() + "]在仓库[" +
                        stockOutcomeBill.getDepot().getName() + "]中的数量[" + productStock.getStoreNumber() +
                        "]不足[" + item.getNumber() + "]");
            }
            productStock.setStoreNumber(productStock.getStoreNumber().subtract(item.getNumber()));
            productStock.setAmount(productStock.getStoreNumber().multiply(productStock.getPrice()));
            productStockMapper.updateByPrimaryKey(productStock);

            SaleAccount sa = new SaleAccount();
            sa.setProduct_id(item.getProduct().getId());
            Long id1 = stockOutcomeBill.getClient().getId();
            sa.setClient_id(id1);
            sa.setSaleman_id(stockOutcomeBill.getInputUser().getId());
            sa.setNumber(item.getNumber());
            sa.setCostPrice(productStock.getPrice());
            sa.setCostAmount(sa.getCostPrice().multiply(sa.getNumber().setScale(2, BigDecimal.ROUND_HALF_UP)));
            sa.setSalePrice(item.getSalePrice());
            sa.setSaleAmount(sa.getSalePrice().multiply(sa.getNumber().setScale(2, BigDecimal.ROUND_HALF_UP)));
            sa.setVdate(new Date());
            saleAccountMapper.insert(sa);
        }



    }
}
