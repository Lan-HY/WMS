package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.Department;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.DepartmentService;
import cn.wolfcode.wms.util.JSONResult;
import cn.wolfcode.wms.util.RequiredPermission;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
@RequestMapping("department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @RequiredPermission("部门列表")
    @RequiresPermissions("department:list")
    @RequestMapping("list")
    public String query(Model model, @ModelAttribute("qo")QueryObject qo) throws Exception {
        PageResult result = departmentService.query(qo);
        model.addAttribute("result", result);
        return "department/list";
    }

    @RequiredPermission("编辑部门")
    @RequiresPermissions("department:input")
    @RequestMapping("input")
    public String input(Long id, Model model) throws Exception {
        if (id != null) {
            model.addAttribute("entity", departmentService.get(id));
        }
        return "/department/input";
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(Department entity) throws Exception {
        departmentService.saveOrUpdate(entity);
        return new JSONResult();
    }

    @RequiredPermission("删除部门")
    @RequiresPermissions("department:delete")
    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id) throws Exception {
        departmentService.delete(id);
        return new JSONResult();
    }

}
