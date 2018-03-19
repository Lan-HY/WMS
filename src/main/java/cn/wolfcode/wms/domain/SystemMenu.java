package cn.wolfcode.wms.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter@Getter@ToString
public class SystemMenu extends BaseDomain {

    private String name;

    private String url;

    private String sn;

    private SystemMenu parent;

}