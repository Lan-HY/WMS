package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.domain.Product;
import cn.wolfcode.wms.query.QueryObject;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Product entity);

    Product selectByPrimaryKey(Long id);

    List<Product> selectAll();

    int updateByPrimaryKey(Product entity);

    List<Product> queryForList(QueryObject qo);

    int queryForCount(QueryObject qo);
}