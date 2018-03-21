package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.OrderBill;
import cn.wolfcode.wms.query.OrderBillQueryObject;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.service.OrderBillService;
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
@RequestMapping("orderBill")
public class OrderBillController {

    @Autowired
    private OrderBillService orderBillService;

    @Autowired
    private SupplierService supplierService;

    @RequiredPermission("采购订单列表")
    @RequiresPermissions("orderBill:list")
    @RequestMapping("list")
    public String query(Model model, @ModelAttribute("qo")OrderBillQueryObject qo) throws Exception {
        model.addAttribute("suppliers", supplierService.list());
        PageResult result = orderBillService.query(qo);
        model.addAttribute("result", result);
        return "orderBill/list";
    }

    @RequiredPermission("编辑采购订单")
    @RequiresPermissions("orderBill:input")
    @RequestMapping("input")
    public String input(Long id, Model model) throws Exception {
        model.addAttribute("suppliers", supplierService.list());
        if (id != null) {
            OrderBill  orderBill= orderBillService.get(id);
            model.addAttribute("entity", orderBill);
        }
        return "/orderBill/input";
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(OrderBill entity) throws Exception {
        orderBillService.saveOrUpdate(entity);
        return new JSONResult();
    }

    @RequiredPermission("删除采购订单")
    @RequiresPermissions("orderBill:delete")
    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id) throws Exception {
        orderBillService.delete(id);
        return new JSONResult();
    }

    @RequestMapping("audit")
    @ResponseBody
    public Object audit(Long id) {
        orderBillService.audit(id);
        return new JSONResult();
    }

    @RequestMapping("showOrder")
    public String showOrder(Long id, Model model) throws Exception {
        if (id != null) {
            OrderBill  orderBill= orderBillService.get(id);
            model.addAttribute("entity", orderBill);
        }
        return "/orderBill/showOrder";
    }

}
