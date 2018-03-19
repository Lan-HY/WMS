package cn.wolfcode.wms.service;

import cn.wolfcode.wms.domain.Supplier;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.QueryObject;

import java.util.List;

public interface SupplierService {

    void saveOrUpdate(Supplier entity);

    void delete(Long id);

    Supplier get(Long id);

    List<Supplier> list();

    PageResult query(QueryObject qo);

}
