<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>信息管理系统</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script>
        $(function () {
            //设置所有的文本框为只读状态
            $("input").prop("readonly",true);
            //点击返回按钮回到list.do的界面
            $(".back").click(function () {
                location.href = "/stockOutcomeBill/list.do";
            });
        });
    </script>
</head>
<body>
<input type="hidden" name="id" value="1"/>
<div id="container">
    <div id="nav_links">
        <span style="color: #1A5CC6;">采购单查看</span>
        <div id="page_close">
            <a>
                <img src="/images/common/page_close.png" width="20" height="20" style="vertical-align: text-top;"/>
            </a>
        </div>
    </div>
    <div class="ui_content">
        <table cellspacing="0" cellpadding="0" width="100%" align="left" bstockOutcome="0">
            <tr>
                <td class="ui_text_rt" width="140">采购单编码</td>
                <td class="ui_text_lt">
                    <input  value="${entity.sn}" class="ui_input_txt02"/>
                </td>
            </tr>
            <tr>
                <td class="ui_text_rt" width="140">地址</td>
                <td class="ui_text_lt">
                    <input value="${entity.depot.name}" class="ui_input_txt02"/>
                </td>
            </tr>
            <tr>
                <td class="ui_text_rt" width="140">业务时间</td>
                <td class="ui_text_lt">
                    <fmt:formatDate value="${entity.vdate}" var="vdate" pattern="yyyy-MM-dd"/>
                    <input value="${vdate}" class="ui_input_txt02"/>
                </td>
            </tr>
            <tr>
                <td class="ui_text_rt" width="140">明细</td>
            </tr>
            <tr>
                <td></td>
                <td>
                    <table class="edit_table" cellspacing="0" cellpadding="0" bstockOutcome="0">
                        <thead>
                        <tr>
                            <th width="170">货品</th>
                            <th width="100">品牌</th>
                            <th width="80">价格</th>
                            <th width="80">数量</th>
                            <th width="100">金额小计</th>
                            <th width="180">备注</th>
                        </tr>
                        </thead>
                        <tbody id="edit_table_body">
                            <c:forEach items="${entity.items}" var="item">
                                <tr>
                                    <td>
                                        <input class="ui_input_txt01" tag="name" value="${item.product.name}"/>
                                        <input type="hidden" value="${item.product.id}" tag="pid"/>
                                    </td>
                                    <td><span tag="brand">${item.product.brandName}</span></td>
                                    <td><input type="number" tag="salePrice" value="${item.salePrice}" class="ui_input_txt01"/></td>
                                    <td><input type="number" tag="number" value="${item.number}" class="ui_input_txt01"/></td>
                                    <td><span tag="amount">${item.amount}</span></td>
                                    <td><input tag="remark" value="${item.remark}" class="ui_input_txt01"/></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </td>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td class="ui_text_lt">
                    &nbsp;<input id="back" type="button" value="返回" class="ui_input_btn01 back"/>
                </td>
            </tr>
        </table>
    </div>
</div>
</body>
</html>