package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.SystemMenu;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.query.SystemMenuQueryObject;
import cn.wolfcode.wms.service.SystemMenuService;
import cn.wolfcode.wms.util.JSONResult;
import cn.wolfcode.wms.util.RequiredPermission;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("systemMenu")
public class SystemMenuController {

    @Autowired
    private SystemMenuService systemMenuService;

    @RequiredPermission("菜单列表")
    @RequiresPermissions("systemMenu:list")
    @RequestMapping("list")
    public String list(Model model, @ModelAttribute("qo")SystemMenuQueryObject qo) throws Exception {
        if (qo.getParentId() != null) {
            SystemMenu menu = systemMenuService.get(qo.getParentId());
            model.addAttribute("menus", systemMenuService.getParentMenus(menu));
        }
        PageResult result = systemMenuService.query(qo);
        model.addAttribute("result", result);
        return "systemMenu/list";
    }

    @RequiredPermission("编辑菜单")
    @RequiresPermissions("systemMenu:input")
    @RequestMapping("input")
    public String input(Long id, Long parentId, Model model) throws Exception {
        if (parentId != null) {
            model.addAttribute("parent", systemMenuService.get(parentId));
        }
        if (id != null) {
            model.addAttribute("entity", systemMenuService.get(id));
        }
        return "/systemMenu/input";
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(SystemMenu entity) throws Exception {
        systemMenuService.saveOrUpdate(entity);
        return new JSONResult();
    }

    @RequiredPermission("删除菜单")
    @RequiresPermissions("systemMenu:delete")
    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id) throws Exception {
        systemMenuService.delete(id);
        return new JSONResult();
    }

    @RequestMapping("loadMenusBySn")
    @ResponseBody
    public Object loadMenusBySn(String parentSn) {
        List<Map<String, Object>> maps = this.systemMenuService.loadMenusBySn(parentSn);
        return maps;
    }

}
