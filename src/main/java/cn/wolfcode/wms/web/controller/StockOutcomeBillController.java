package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.StockOutcomeBill;
import cn.wolfcode.wms.exception.LogicException;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.StockOutcomeBillQueryObject;
import cn.wolfcode.wms.service.ClientService;
import cn.wolfcode.wms.service.DepotService;
import cn.wolfcode.wms.service.StockOutcomeBillService;
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
@RequestMapping("stockOutcomeBill")
public class StockOutcomeBillController {

    @Autowired
    private StockOutcomeBillService stockOutcomeBillService;

    @Autowired
    private DepotService depotService;

    @Autowired
    private ClientService clientService;

    @RequiredPermission("销售出库单列表")
    @RequiresPermissions("stockOutcomeBill:list")
    @RequestMapping("list")
    public String query(Model model, @ModelAttribute("qo")StockOutcomeBillQueryObject qo) throws Exception {
        model.addAttribute("depots", depotService.list());
        model.addAttribute("clients", clientService.list());
        PageResult result = stockOutcomeBillService.query(qo);
        model.addAttribute("result", result);
        return "/stockOutcomeBill/list";
    }

    @RequiredPermission("编辑销售出库单")
    @RequiresPermissions("stockOutcomeBill:input")
    @RequestMapping("input")
    public String input(Long id, Model model) throws Exception {
        model.addAttribute("depots", depotService.list());
        model.addAttribute("clients", clientService.list());
        if (id != null) {
            StockOutcomeBill  stockOutcomeBill= stockOutcomeBillService.get(id);
            model.addAttribute("entity", stockOutcomeBill);
        }
        return "/stockOutcomeBill/input";
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(StockOutcomeBill entity) throws Exception {
        stockOutcomeBillService.saveOrUpdate(entity);
        return new JSONResult();
    }

    @RequiredPermission("删除销售出库单")
    @RequiresPermissions("stockOutcomeBill:delete")
    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id) throws Exception {
        stockOutcomeBillService.delete(id);
        return new JSONResult();
    }

    @RequestMapping("audit")
    @ResponseBody
    public Object audit(Long id) {
        JSONResult result = new JSONResult();
        try {
            stockOutcomeBillService.audit(id);
        } catch (LogicException e) {
            result.mark(e.getMessage());
        }
        return result;
    }

    @RequestMapping("showStockOutcome")
    public String showStockOutcome(Long id, Model model) throws Exception {
        if (id != null) {
            StockOutcomeBill  stockOutcomeBill= stockOutcomeBillService.get(id);
            model.addAttribute("entity", stockOutcomeBill);
        }
        return "/stockOutcomeBill/showStockOutcome";
    }

}
