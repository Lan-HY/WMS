package cn.wolfcode.wms.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Setter@Getter
public class OrderBill extends BaseDomain {

    public static final int STATUS_NOMAL = 0;

    public static final int STATUS_AUDIT = 1;

    private String sn;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date vdate;

    private int status = STATUS_NOMAL;

    private BigDecimal totalAmount;

    private BigDecimal totalNumber;

    private Date auditTime;

    private Date inputTime;

    private Employee inputUser;

    private Employee auditor;

    private Supplier supplier;

    private List<OrderBillItem> items;

}