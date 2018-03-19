package cn.wolfcode.wms.service;

import cn.wolfcode.wms.domain.Client;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.QueryObject;

import java.util.List;

public interface ClientService {

    void saveOrUpdate(Client entity);

    void delete(Long id);

    Client get(Long id);

    List<Client> list();

    PageResult query(QueryObject qo);

}
