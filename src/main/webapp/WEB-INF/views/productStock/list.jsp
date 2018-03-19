<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <title>PSS-即时库存报表管理</title>
    <style>
        .alt td{ background:black !important;}
    </style>
</head>
<body>
<form id="searchForm" action="/productStock/list.do" method="post">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                    <div id="box_center">
                        商品名称/编码
                        <input type="text" class="ui_input_txt01" name="keyword" value="${qo.keyword}"/>
                        仓库
                        <select id="depotId" class="ui_select01" name="depotId">
                            <option value="-1">全部仓库</option>
                            <c:forEach items="${depots}" var="depot">
                                <option value="${depot.id}">${depot.name}</option>
                            </c:forEach>
                        </select>
                        品牌
                        <select id="brandId" class="ui_select01" name="brandId">
                            <option value="-1">全部品牌</option>
                            <c:forEach items="${brands}" var="brand">
                                <option value="${brand.id}">${brand.name}</option>
                            </c:forEach>
                        </select>
                        库存阈值
                        <input type="number" class="ui_input_txt01" name="warnNum" value="${qo.warnNum}"/>
                        <script>
                            $("#depotId option[value='${qo.depotId}']").prop("selected",true);
                            $("#brandId option[value='${qo.brandId}']").prop("selected",true);
                        </script>
                    </div>
                    <div id="box_bottom">
                        <input type="button" value="查询" class="ui_input_btn01 btn_page"/>
                    </div>
                </div>
            </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb">
                <table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
                    <tr>
                        <th width="30"><input type="checkbox" id="all" /></th>
                        <th>仓库</th>
                        <th>货品编码</th>
                        <th>货品名称</th>
                        <th>货品品牌</th>
                        <th>库存价格</th>
                        <th>库存数量</th>
                        <th>总价值</th>
                    </tr>
                    <c:forEach var="entity" items="${result.data}" varStatus="num">
                        <tr>
                            <td><input type="checkbox" class="acb" /></td>
                            <td>${entity.depot.name}</td>
                            <td>${entity.product.sn}</td>
                            <td>${entity.product.name}</td>
                            <td>${entity.product.brandName}</td>
                            <td>${entity.price}</td>
                            <td>${entity.storeNumber}</td>
                            <td>${entity.amount}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <%-- 引入分页条 --%>
            <%@ include file="/WEB-INF/views/common/page.jsp"%>
        </div>
    </div>
</form>
</body>
</html>

