package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.Department;
import cn.wolfcode.wms.domain.Role;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.DepartmentService;
import cn.wolfcode.wms.service.PermissionService;
import cn.wolfcode.wms.service.RoleService;
import cn.wolfcode.wms.service.SystemMenuService;
import cn.wolfcode.wms.util.JSONResult;
import cn.wolfcode.wms.util.RequiredPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("role")
public class RoleController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private SystemMenuService systemMenuService;

    public RoleController() {
    }

    @RequiredPermission("角色列表")
    @RequestMapping("list")
    public String list(@ModelAttribute("qo") QueryObject qo, Model model) {
        model.addAttribute("result", roleService.query(qo));
        return "role/list";
    }

    @RequiredPermission("编辑角色")
    @RequestMapping("input")
    public String input(Long id, Model model) {
        if (id != null) {
            model.addAttribute("entity", roleService.get(id));
        }
        model.addAttribute("permissions", permissionService.list());
        model.addAttribute("menus", systemMenuService.list());
        return "role/input";
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(Role entity, Long[] ids, Long[] menuIds) {
        roleService.saveOrUpdate(entity, ids, menuIds);
        return new JSONResult();
    }

    @RequiredPermission("删除角色")
    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id) {
        roleService.delete(id);
        return new JSONResult();
    }

}
