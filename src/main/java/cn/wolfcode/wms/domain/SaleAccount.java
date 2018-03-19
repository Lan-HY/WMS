package cn.wolfcode.wms.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Setter@Getter
public class SaleAccount extends BaseDomain {

    private Date vdate;

    private BigDecimal number;

    private BigDecimal costPrice;

    private BigDecimal costAmount;

    private BigDecimal salePrice;

    private BigDecimal saleAmount;

    private Long product_id;

    private Long saleman_id;

    private Long client_id;

}