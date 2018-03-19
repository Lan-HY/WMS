package cn.wolfcode.wms.service;

import cn.wolfcode.wms.domain.Brand;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.QueryObject;

import java.util.List;

public interface BrandService {

    void saveOrUpdate(Brand entity);

    void delete(Long id);

    Brand get(Long id);

    List<Brand> list();

    PageResult query(QueryObject qo);

}
