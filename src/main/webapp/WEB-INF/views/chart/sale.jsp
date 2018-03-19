<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/iframeTools.js"></script>
    <script type="text/javascript" src="/js/plugins/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <title>WMS-销售报表管理</title>
    <style>
        .alt td{ background:black !important;}
    </style>
    <script type="text/javascript" >
        $(function () {
            $(".Wdate").click(function () {
                WdatePicker({
                    minDate : $("input[name=beginDate]").val(),
                    maxDate : new Date(),
                    readOnly : true
                });
            });
            $(".chart").click(function () {

                var url = $(this).data("url") + "?" + $("#searchForm").serialize();
                $.artDialog.open(url, {
                    title : "销售报表",
                    width : "90%",
                    height : 550,
                    lock : true,
                    opacity : 0.5
                });
            });
        });
    </script>
</head>
<body>
<form id="searchForm" action="/chart/sale.do" method="post">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_bsale">
                    <div id="box_top">搜索</div>
                    <div id="box_center">
                        业务时间
                        <fmt:formatDate value="${qo.beginDate}" pattern="yyyy-MM-dd" var="beginDate" />
                        <input type="text" class="ui_input_txt02 Wdate" name="beginDate" value="${beginDate}"/> ~
                        <fmt:formatDate value="${qo.endDate}" pattern="yyyy-MM-dd" var="endDate" />
                        <input type="text" class="ui_input_txt02 Wdate" name="endDate" value="${endDate}"/>
                        商品名称/编码
                        <input type="text" class="ui_input_txt02" name="keyword" value="${qo.keyword}"/>
                        客户
                        <select id="clientId" class="ui_select01 select_change" name="clientId">
                            <option value="-1">全部客户</option>
                            <c:forEach items="${clients}" var="client">
                                <option value="${client.id}">${client.name}</option>
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
                            <c:forEach items="${groupTypeMap}" var="groupTpye">
                                <option value="${groupTpye.key}" ${groupTpye.key == qo.groupType ? "selected" : ""}>${groupTpye.value}</option>
                            </c:forEach>

                        </select>
                        <script type="text/javascript" >
                            $("#clientId option[value='${qo.clientId}']").prop("selected",true);
                            $("#brandId option[value='${qo.brandId}']").prop("selected",true);
                        </script>

                    </div>
                    <div id="box_bottom">
                        <input type="button" value="查询" class="ui_input_btn01 btn_page"/>
                        <input type="button" value="柱状图" class="left2right chart"
                               data-url="/chart/saleByBar.do"/>
                        <input type="button" value="饼状图" class="left2right chart"
                               data-url="/chart/saleByPie.do"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb">
                <table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" bsale="0">
                    <tr>
                        <th>分组类型</th>
                        <th>销售总数</th>
                        <th>销售总额</th>
                        <th>利润</th>
                    </tr>
                    <c:forEach items="${list}" var="entity" varStatus="vs">
                        <tr>
                            <td>${entity.groupType}</td>
                            <td>${entity.totalNumber}</td>
                            <td>${entity.totalAmount}</td>
                            <td>${entity.profit}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
</form>
</body>
</html>
