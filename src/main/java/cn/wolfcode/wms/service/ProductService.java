package cn.wolfcode.wms.service;

import cn.wolfcode.wms.domain.Product;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.QueryObject;

import java.util.List;

public interface ProductService {

    void saveOrUpdate(Product entity);

    void delete(Long id);

    Product get(Long id);

    List<Product> list();

    PageResult query(QueryObject qo);

}
