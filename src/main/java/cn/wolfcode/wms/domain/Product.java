package cn.wolfcode.wms.domain;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@ToString
public class Product extends BaseDomain{

    private String name;

    private String sn;

    private BigDecimal costPrice;

    private BigDecimal salePrice;

    private String imagePath;

    private String intro;

    private Long brandId;

    private String brandName;

    public String getSmallImagePath() {
        if (imagePath == null) {
            return "";
        }
        return imagePath.substring(0, imagePath.indexOf(".")) + "_small"
                + imagePath.substring(imagePath.indexOf("."));
    }

    public String getProductInfo() {
        Map<String, Object> map = new HashMap<>();
        map.put("id",getId());
        map.put("brandName",brandName);
        map.put("name", name);
        map.put("costPrice", costPrice);
        map.put("salePrice", salePrice);
        return JSON.toJSONString(map);
    }

}