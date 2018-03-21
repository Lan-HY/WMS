package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.Brand;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.BrandService;
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
@RequestMapping("brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @RequiredPermission("品牌列表")
    @RequiresPermissions("brand:list")
    @RequestMapping("list")
    public String query(Model model, @ModelAttribute("qo")QueryObject qo) throws Exception {
        PageResult result = brandService.query(qo);
        model.addAttribute("result", result);
        return "brand/list";
    }

    @RequiredPermission("编辑品牌")
    @RequiresPermissions("brand:input")
    @RequestMapping("input")
    public String input(Long id, Model model) throws Exception {
        if (id != null) {
            model.addAttribute("entity", brandService.get(id));
        }
        return "/brand/input";
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(Brand entity) throws Exception {
        brandService.saveOrUpdate(entity);
        return new JSONResult();
    }

    @RequiredPermission("删除品牌")
    @RequiresPermissions("brand:delete")
    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id) throws Exception {
        brandService.delete(id);
        return new JSONResult();
    }

}
