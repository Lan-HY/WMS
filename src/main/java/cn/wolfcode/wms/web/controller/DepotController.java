package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.Depot;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.DepotService;
import cn.wolfcode.wms.util.JSONResult;
import cn.wolfcode.wms.util.RequiredPermission;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("depot")
public class DepotController {

    @Autowired
    private DepotService depotService;

    @RequiredPermission("仓库列表")
    @RequiresPermissions("depot:list")
    @RequestMapping("list")
    public String query(Model model, @ModelAttribute("qo")QueryObject qo) throws Exception {
        PageResult result = depotService.query(qo);
        model.addAttribute("result", result);
        return "depot/list";
    }

    @RequiredPermission("编辑仓库")
    @RequiresPermissions("depot:input")
    @RequestMapping("input")
    public String input(Long id, Model model) throws Exception {
        if (id != null) {
            model.addAttribute("entity", depotService.get(id));
        }
        return "/depot/input";
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(Depot entity) throws Exception {
        depotService.saveOrUpdate(entity);
        return new JSONResult();
    }

    @RequiredPermission("删除仓库")
    @RequiresPermissions("depot:delete")
    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id) throws Exception {
        depotService.delete(id);
        return new JSONResult();
    }

}
