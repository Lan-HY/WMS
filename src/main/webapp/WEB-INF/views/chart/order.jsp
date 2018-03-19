<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="/js/jquery/jQueryForm.js"></script>
    <script type="text/javascript" src="/js/plugins/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <title>WMS-订货报表管理</title>
    <style>
        .alt td{ background:black !important;}
    </style>
    <script type="text/javascript" >

    </script>
</head>
<body>
<form id="searchForm" action="/chart/order.do" method="post">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                    <div id="box_center">
                        业务时间
                        <fmt:formatDate value="${qo.beginDate}" pattern="yyyy-MM-dd" var="beginDate" />
                        <input type="text" readonly="readonly" class="ui_input_txt02 Wdate" name="beginDate" value="${beginDate}" onClick="WdatePicker()" /> ~
                        <fmt:formatDate value="${qo.endDate}" pattern="yyyy-MM-dd" var="endDate" />
                        <input type="text" readonly="readonly" class="ui_input_txt02 Wdate" name="endDate" value="${endDate}" onClick="WdatePicker()" />
                        商品名称/编码
                        <input type="text" class="ui_input_txt02" name="keyword" value="${qo.keyword}"/>
                        供应商
                        <select id="supplierId" class="ui_select01 select_change" name="supplierId">
                            <option value="-1">全部供应商</option>
                            <c:forEach items="${suppliers}" var="supplier">
                                <option value="${supplier.id}">${supplier.name}</option>
                            </c:forEach>
                        </select>
                        品牌
                        <select id="brandId" class="ui_select01 select_change" name="brandId">
                            <option value="-1">全部品牌</option>
                            <c:forEach items="${brands}" var="brand">
                                <option value="${brand.id}">${brand.name}</option>
                            </c:forEach>
                        </select>
                        类型
                        <select id="groupType" class="ui_select01 select_change" name="groupType">
                            <c:forEach items="${groupByTypeMap}" var="proupTpye">
                                <option value="${proupTpye.key}">${proupTpye.value}</option>
                            </c:forEach>
                           <%-- <option value="e.name">订货员</option>
                            <option value="p.name">商品名称</option>
                            <option value="p.brandName">商品品牌</option>
                            <option value="s.name">供应商</option>
                            <option value="DATE_FORMAT(bill.vdate,'%Y-%m')">订货月份</option>
                            <option value="DATE_FORMAT(bill.vdate,'%Y-%m-%d')">订货日期</option>--%>
                        </select>
                        <script type="text/javascript" >
                            $("#supplierId option[value='${qo.supplierId}']").prop("selected",true);
                            $("#brandId option[value='${qo.brandId}']").prop("selected",true);
                            $("#groupType option[value='${qo.groupType}']").prop("selected",true);
                        </script>

                    </div>
                    <div id="box_bottom">
                        <input type="button" value="查询" class="ui_input_btn01 btn_page"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb">
                <table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
                    <tr>
                        <th>分组类型</th>
                        <th>订货总数</th>
                        <th>订货总额</th>
                    </tr>
                    <c:forEach items="${list}" var="entity" varStatus="vs">
                        <tr>
                            <td>${entity.groupByType}</td>
                            <td>${entity.totalNumber}</td>
                            <td>${entity.totalAmount}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
</form>
</body>
</html>
