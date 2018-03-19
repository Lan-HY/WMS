package cn.wolfcode.wms.query;

import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public class PageResult {

    public static final PageResult EMPTY_PAGE = new PageResult(Collections.EMPTY_LIST, 0, 1, 3);

    private int currentPage;

    private int pageSize;

    private int totalCount;

    private List<?> data;

    private int prevPage;

    private int nextPage;

    private int totalPage;

    public PageResult(List<?> data, int totalCount, int currentPage, int pageSize) {
        this.data = data;
        this.totalCount = totalCount;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        if (totalCount <= pageSize) {
            this.prevPage = 1;
            this.nextPage = 1;
            this.totalPage = 1;
            return;
        }
        this.totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        this.prevPage = currentPage > 1 ? currentPage - 1 : 1;
        this.nextPage = currentPage < totalPage ? currentPage + 1 : totalPage;
    }

}
