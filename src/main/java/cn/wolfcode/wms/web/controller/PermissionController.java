package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.PermissionService;
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
@RequestMapping("permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    public PermissionController() {
    }

    @RequiredPermission("权限列表")
//    @RequiresPermissions("permission:list")
    @RequestMapping("list")
    public String list(@ModelAttribute("qo") QueryObject qo, Model model) {
        model.addAttribute("result", permissionService.query(qo));
        return "permission/list";
    }

    @RequiredPermission("删除权限")
//    @RequiresPermissions("permission:delete")
    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id) {
        permissionService.delete(id);
        return new JSONResult();
    }

    @RequiredPermission("加载权限")
//    @RequiresPermissions("permission:reload")
    @RequestMapping("reload")
    @ResponseBody
    public Object reload() {
        permissionService.reload();
        return new JSONResult();
    }

}
