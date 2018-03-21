package cn.wolfcode.wms.web.controller;

import cn.wolfcode.wms.query.ChartOrderQueryObject;
import cn.wolfcode.wms.query.ChartSaleQueryObject;
import cn.wolfcode.wms.service.BrandService;
import cn.wolfcode.wms.service.ChartService;
import cn.wolfcode.wms.service.ClientService;
import cn.wolfcode.wms.service.SupplierService;
import cn.wolfcode.wms.util.Dictionary;
import cn.wolfcode.wms.util.RequiredPermission;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("chart")
public class ChartController {

    @Autowired
    private SupplierService supplierService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private ChartService chartService;
    @Autowired
    private ClientService clientService;

    @RequiredPermission("订货报表")
    @RequiresPermissions("chart:order")
    @RequestMapping("order")
    public String order(Model model, @ModelAttribute("qo") ChartOrderQueryObject qo) throws  Exception{

        model.addAttribute("suppliers",supplierService.list());
        model.addAttribute("brands",brandService.list());
        model.addAttribute("list",chartService.queryOrderChart(qo));
        model.addAttribute("groupByTypeMap", Dictionary.orderGroupByTypeMap);
        return "/chart/order";
    }

    @RequiredPermission("销售报表")
    @RequiresPermissions("chart:sale")
    @RequestMapping("sale")
    public String sale(Model model, @ModelAttribute("qo") ChartSaleQueryObject qo) throws  Exception{

        model.addAttribute("clients",clientService.list());
        model.addAttribute("brands",brandService.list());
        model.addAttribute("list",chartService.querySaleChart(qo));
        model.addAttribute("groupTypeMap", Dictionary.saleGroupByTypeMap);
        return "/chart/sale";
    }

    @RequestMapping("saleByBar")
    public String saleByBar(Model model, @ModelAttribute("qo") ChartSaleQueryObject qo) throws  Exception{
        List<Map<String, Object>> list = chartService.querySaleChart(qo);
        List<Object> x = new ArrayList<>();
        List<Object> y = new ArrayList<>();
        for (Map<String, Object> entity : list) {
            x.add(entity.get("groupType"));
            y.add(entity.get("totalAmount"));
        }
        model.addAttribute("groupType",Dictionary.saleGroupByTypeMap.get(qo.getGroupType()));
        model.addAttribute("x", JSON.toJSONString(x));
        model.addAttribute("y", JSON.toJSONString(y));
        return "/chart/saleByBar";
    }

    @RequestMapping("saleByPie")
    public String saleByPie(Model model, @ModelAttribute("qo") ChartSaleQueryObject qo) throws  Exception{
        List<Map<String, Object>> list = chartService.querySaleChart(qo);
        List<Object> left = new ArrayList<>();
        List<Object> datas = new ArrayList<>();
        for (Map<String, Object> entity : list) {
            left.add(entity.get("groupType"));
            Map<String, Object> data = new HashMap<>();
            data.put("name", entity.get("groupType"));
            data.put("value", entity.get("totalAmount"));
            datas.add(data);
        }

        model.addAttribute("groupType",Dictionary.saleGroupByTypeMap.get(qo.getGroupType()));
        model.addAttribute("left", JSON.toJSONString(left));
        model.addAttribute("datas", JSON.toJSONString(datas));
        return "/chart/saleByPie";
    }

}
