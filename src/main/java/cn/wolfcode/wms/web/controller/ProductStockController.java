package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.ProductStockQueryObject;
import cn.wolfcode.wms.service.BrandService;
import cn.wolfcode.wms.service.DepotService;
import cn.wolfcode.wms.service.ProductStockService;
import cn.wolfcode.wms.util.RequiredPermission;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("productStock")
public class ProductStockController {

    @Autowired
    private ProductStockService productStockService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private DepotService depotService;


    @RequiredPermission("商品列表")
    @RequiresPermissions("productStock:list")
    @RequestMapping("list")
    public String query(Model model, @ModelAttribute("qo")ProductStockQueryObject qo) throws Exception {
        PageResult result = productStockService.query(qo);
        model.addAttribute("brands", brandService.list());
        model.addAttribute("depots", depotService.list());
        model.addAttribute("result", result);
        return "productStock/list";
    }

}
