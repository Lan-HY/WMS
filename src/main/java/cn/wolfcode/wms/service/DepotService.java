package cn.wolfcode.wms.service;

import cn.wolfcode.wms.domain.Depot;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.QueryObject;

import java.util.List;

public interface DepotService {

    void saveOrUpdate(Depot entity);

    void delete(Long id);

    Depot get(Long id);

    List<Depot> list();

    PageResult query(QueryObject qo);

}
