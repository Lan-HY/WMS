package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.domain.ProductStock;
import cn.wolfcode.wms.query.QueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductStockMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProductStock entity);

    ProductStock selectByPrimaryKey(Long id);

    List<ProductStock> selectAll();

    int updateByPrimaryKey(ProductStock entity);

    List<ProductStock> queryForList(QueryObject qo);

    int queryForCount(QueryObject qo);

    ProductStock selectByProductIdAndDepotId(@Param("productId") Long productId, @Param("depotId") Long depotId);
}