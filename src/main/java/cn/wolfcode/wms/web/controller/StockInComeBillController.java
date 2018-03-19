package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.domain.StockInComeBill;
import cn.wolfcode.wms.query.StockInComeBillQueryObject;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.service.StockInComeBillService;
import cn.wolfcode.wms.service.DepotService;
import cn.wolfcode.wms.util.JSONResult;
import cn.wolfcode.wms.util.RequiredPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("stockIncomeBill")
public class StockInComeBillController {

    @Autowired
    private StockInComeBillService stockInComeBillService;

    @Autowired
    private DepotService depotService;

    @RequiredPermission("采购入库单列表")
    @RequestMapping("list")
    public String query(Model model, @ModelAttribute("qo")StockInComeBillQueryObject qo) throws Exception {
        model.addAttribute("depots", depotService.list());
        PageResult result = stockInComeBillService.query(qo);
        model.addAttribute("result", result);
        return "/stockInComeBill/list";
    }

    @RequiredPermission("编辑采购入库单")
    @RequestMapping("input")
    public String input(Long id, Model model) throws Exception {
        model.addAttribute("depots", depotService.list());
        if (id != null) {
            StockInComeBill  stockInComeBill= stockInComeBillService.get(id);
            model.addAttribute("entity", stockInComeBill);
        }
        return "/stockInComeBill/input";
    }

    @RequestMapping("saveOrUpdate")
    @ResponseBody
    public Object saveOrUpdate(StockInComeBill entity) throws Exception {
        stockInComeBillService.saveOrUpdate(entity);
        return new JSONResult();
    }

    @RequiredPermission("删除采购入库单")
    @RequestMapping("delete")
    @ResponseBody
    public Object delete(Long id) throws Exception {
        stockInComeBillService.delete(id);
        return new JSONResult();
    }

    @RequestMapping("audit")
    @ResponseBody
    public Object audit(Long id) {
        stockInComeBillService.audit(id);
        return new JSONResult();
    }

    @RequestMapping("showStockInCome")
    public String showStockInCome(Long id, Model model) throws Exception {
        if (id != null) {
            StockInComeBill  stockInComeBill= stockInComeBillService.get(id);
            model.addAttribute("entity", stockInComeBill);
        }
        return "/stockInComeBill/showStockInCome";
    }

}
