package cn.wolfcode.wms.query;


import cn.wolfcode.wms.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter@Setter
public class ChartOrderQueryObject extends QueryObject {

    private String keyword;

    @DateTimeFormat(
            pattern = "yyyy-MM-dd"
    )
    private Date beginDate;
    @DateTimeFormat(
            pattern = "yyyy-MM-dd"
    )
    private Date endDate;

    private Long supplierId;

    private Long brandId;

    private String groupType = "e.name";

    public void setEndDate(Date endDate){
        this.endDate = DateUtil.getEndDate(endDate);
    }

}
