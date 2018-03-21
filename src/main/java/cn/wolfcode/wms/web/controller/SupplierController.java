package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.Supplier;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.SupplierService;
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
@RequestMapping("supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @RequiredPermission("供应商列表")
    @RequiresPermissions("supplier:list")
    @RequestMapping("list")
    public String query(Model model, @ModelAttribute("qo")QueryObject qo) throws Exception {
        PageResult result = supplierService.query(qo);
        model.addAttribute("result", result);
        return "supplier/list";
    }

    @RequiredPermission("编辑供应商")
    @RequiresPermissions("supplier:input")
    @RequestMapping("input")
    public String input(Long id, Model model) throws Exception {
        if (id != null) {
            model.addAttribute("entity", supplierService.get(id));
        }
        return "/supplier/input";
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(Supplier entity) throws Exception {
        supplierService.saveOrUpdate(entity);
        return new JSONResult();
    }

    @RequiredPermission("删除供应商")
    @RequiresPermissions("supplier:delete")
    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id) throws Exception {
        supplierService.delete(id);
        return new JSONResult();
    }

}
