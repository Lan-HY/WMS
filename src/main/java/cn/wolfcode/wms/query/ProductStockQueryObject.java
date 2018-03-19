package cn.wolfcode.wms.query;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductStockQueryObject extends QueryObject {

    private String keyword;

    private Long BrandId;

    private Long DepotId;

    private Integer warnNum;

}
