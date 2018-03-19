package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.Department;
import cn.wolfcode.wms.domain.Employee;
import cn.wolfcode.wms.query.EmployeeQueryObject;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.service.DepartmentService;
import cn.wolfcode.wms.service.EmployeeService;
import cn.wolfcode.wms.service.RoleService;
import cn.wolfcode.wms.util.JSONResult;
import cn.wolfcode.wms.util.RequiredPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private RoleService roleService;

    @RequiredPermission("员工列表")
    @RequestMapping("list")
    public String query(Model model, @ModelAttribute("qo")EmployeeQueryObject qo) throws Exception {
        PageResult result = employeeService.query(qo);
        model.addAttribute("result", result);
        List<Department> list = departmentService.list();
        model.addAttribute("depts", list);
        return "employee/list";
    }

    @RequiredPermission("编辑员工")
    @RequestMapping("input")
    public String input(Long id, Model model) throws Exception {
        if (id != null) {
            model.addAttribute("entity", employeeService.get(id));
        }
        model.addAttribute("depts", departmentService.list());
        model.addAttribute("roles", roleService.list());
        return "/employee/input";
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(Employee entity, Long[] ids) throws Exception {
        employeeService.saveOrUpdate(entity, ids);
        return new JSONResult();
    }

    @RequiredPermission("删除员工")
    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id) throws Exception {
        employeeService.delete(id);
        return new JSONResult();
    }

    @RequestMapping("batchDelete")
    @ResponseBody
    public Object batchDelete(Long[] ids) throws Exception {
        employeeService.batchDelete(ids);
        return new JSONResult();
    }

}
