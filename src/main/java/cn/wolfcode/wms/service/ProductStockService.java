package cn.wolfcode.wms.service;

import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.QueryObject;


public interface ProductStockService {

    PageResult query(QueryObject qo);

}
