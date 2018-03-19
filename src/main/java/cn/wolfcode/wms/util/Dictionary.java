package cn.wolfcode.wms.util;

import org.springframework.web.servlet.DispatcherServlet;

import java.util.LinkedHashMap;
import java.util.Map;

public class Dictionary {

    private Dictionary(){}

  public static Map<String,String> orderGroupByTypeMap = new LinkedHashMap<>();
  public static Map<String,String> saleGroupByTypeMap = new LinkedHashMap<>();


    static {
        //订货报表
        orderGroupByTypeMap.put("e.name","订货员");
        orderGroupByTypeMap.put("p.name","商品名称");
        orderGroupByTypeMap.put("p.brand_name","商品品牌");
        orderGroupByTypeMap.put("s.name","供应商");
        orderGroupByTypeMap.put("DATE_FORMAT(bill.vdate,'%Y-%m')","订货月份");
        orderGroupByTypeMap.put("DATE_FORMAT(bill.vdate,'%Y-%m-%d')","订货日期");

        //售获报表
        saleGroupByTypeMap.put("saleMan.name","销售员");
        saleGroupByTypeMap.put("p.name","商品名称");
        saleGroupByTypeMap.put("p.brand_name","商品品牌");
        saleGroupByTypeMap.put("c.name","客户");
        saleGroupByTypeMap.put("DATE_FORMAT(sa.vdate,'%Y-%m')","销售月份");
        saleGroupByTypeMap.put("DATE_FORMAT(sa.vdate,'%Y-%m-%d')","销售日期");


    }
}
