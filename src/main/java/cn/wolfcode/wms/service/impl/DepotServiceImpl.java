package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.Depot;
import cn.wolfcode.wms.mapper.DepotMapper;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.DepotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepotServiceImpl implements DepotService {

    @Autowired
    private DepotMapper depotMapper;

    public void saveOrUpdate(Depot entity) {
        if (entity.getId() == null) {
            depotMapper.insert(entity);
        } else {
            depotMapper.updateByPrimaryKey(entity);
        }
    }

    public void delete(Long id) {
        if (id != null) {
            depotMapper.deleteByPrimaryKey(id);
        }
    }

    public Depot get(Long id) {
        return depotMapper.selectByPrimaryKey(id);
    }

    public List<Depot> list() {
        return depotMapper.selectAll();
    }

    public PageResult query(QueryObject qo) {
        int totalCount = depotMapper.queryForCount(qo);
        if (totalCount == 0) {
            return PageResult.EMPTY_PAGE;
        }
        List<Depot> data = depotMapper.queryForList(qo);
        return new PageResult(data, totalCount, qo.getCurrentPage(), qo.getPageSize());
    }
}
