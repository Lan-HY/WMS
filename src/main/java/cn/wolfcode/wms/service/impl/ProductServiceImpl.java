package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.Brand;
import cn.wolfcode.wms.domain.Product;
import cn.wolfcode.wms.mapper.BrandMapper;
import cn.wolfcode.wms.mapper.ProductMapper;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {


    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private BrandMapper brandMapper;

    public PageResult query(QueryObject qo) {
        int totalCount = productMapper.queryForCount(qo);
        if (totalCount == 0) {
            return PageResult.EMPTY_PAGE;
        }
        List<Product> data = productMapper.queryForList(qo);
        return new PageResult(data, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }

    public void saveOrUpdate(Product entity) {
        Brand brand = brandMapper.selectByPrimaryKey(entity.getBrandId());
        entity.setBrandName(brand.getName());
        if (entity.getId() == null) {
            productMapper.insert(entity);
        } else {
            productMapper.updateByPrimaryKey(entity);
        }
    }

    public void delete(Long id) {
        if (id != null) {
            productMapper.deleteByPrimaryKey(id);
        }
    }

    public Product get(Long id) {
        return productMapper.selectByPrimaryKey(id);
    }

    public List<Product> list() {
        return productMapper.selectAll();
    }

}
