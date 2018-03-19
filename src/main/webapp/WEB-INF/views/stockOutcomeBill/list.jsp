<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <script src="/js/plugins/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <script>
        $(function () {
            $(".btn_audit").click(function () {
                var url = $(this).data("url");
                var reload = $(this).data("reload");
                //弹出对话框
                showDialog("你确定要审核吗?",function () {
                    //发送ajax请求
                    $.get(url,function (data) {
                        //如果操作成功,自动刷新页面
                        if (data.success) {
                            showDialog("审核成功",function () {
                                location.href=reload;
                            });
                        } else {
                            showDialog("审核失败", data.msg);
                        }
                    },"json");
                },true);
            });

            $(".Wdate").click(function () {
                WdatePicker({
                    minDate : $("input[name=beginDate]").val(),
                    maxDate : new Date(),
                    readOnly : true
                })
            });
        });
    </script>
    <title>PSS-采购出库单管理</title>
    <style>
        .alt td{ background:black !important;}
    </style>
</head>
<body>
<form id="searchForm" action="/stockOutcomeBill/list.do" method="post">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_bstockOutcome">
                    <div id="box_top">搜索</div>
                    <div id="box_center">
                        业务时间
                        <fmt:formatDate value="${qo.beginDate}" pattern="yyyy-MM-dd" var="beginDate"/>
                        <input type="text" class="ui_input_txt02 Wdate" name="beginDate" value="${beginDate}"/> ~
                        <fmt:formatDate value="${qo.endDate}" pattern="yyyy-MM-dd" var="endDate"/>
                        <input type="text" class="ui_input_txt02 Wdate" name="endDate" value="${endDate}"/>
                        仓库
                        <select id="depotId" class="ui_select01" name="depotId">
                            <option value="-1">全部仓库</option>
                            <c:forEach items="${depots}" var="depot">
                                <option value="${depot.id}">${depot.name}</option>
                            </c:forEach>
                        </select>
                        客户
                        <select id="clientId" class="ui_select01" name="clientId">
                            <option value="-1">全部客户</option>
                            <c:forEach items="${clients}" var="client">
                                <option value="${client.id}">${client.name}</option>
                            </c:forEach>
                        </select>
                        状态
                        <select id="status" class="ui_select01" name="status">
                            <option value="-1">全部</option>
                            <option value="0">未审核</option>
                            <option value="1">已审核</option>
                        </select>
                        <script>
                            $("#depotId option[value='${qo.depotId}']").prop("selected",true);
                            $("#clientId option[value='${qo.clientId}']").prop("selected",true);
                            $("#status option[value='${qo.status}']").prop("selected",true);
                        </script>
                    </div>
                    <div id="box_bottom">
                        <input type="button" value="查询" class="ui_input_btn01 btn_page" data-page="1"/>
                        <input type="button" value="新增" data-url="/stockOutcomeBill/input.do"
                               class="ui_input_btn01 btn_input"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb">
                <table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" bstockOutcome="0">
                    <tr>
                        <th width="30"><input type="checkbox" id="all" /></th>
                        <th>采购单号</th>
                        <th>业务时间</th>
                        <th>仓库</th>
                        <th>客户</th>
                        <th>总数量</th>
                        <th>总金额</th>
                        <th>录出人</th>
                        <th>审核人</th>
                        <th>状态</th>
                        <th></th>
                    </tr>
                    <c:forEach var="entity" items="${result.data}" varStatus="num">
                        <tr>
                            <td><input type="checkbox" class="acb" /></td>
                            <td>${entity.sn}</td>
                            <td>
                                <fmt:formatDate value="${entity.vdate}" pattern="yyyy-MM-dd"/>
                            </td>
                            <td>${entity.depot.name}</td>
                            <td>${entity.client.name}</td>
                            <td>${entity.totalNumber}</td>
                            <td>${entity.totalAmount}</td>
                            <td>${entity.inputUser.name}</td>
                            <td>${entity.auditor.name}</td>
                            <td>
                                <c:if test="${entity.status == 0}">
                                    <span style="color: #ab1e1e">未审核</span>
                                </c:if>
                                <c:if test="${entity.status == 1}">
                                    <span style="color: #00B83F">已审核</span>
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${entity.status == 0}">
                                    <a href="/stockOutcomeBill/input.do?id=${entity.id}">编辑</a>
                                    <a href="javascript:" class="btn_delete"
                                       data-url="/stockOutcomeBill/delete.do?id=${entity.id}"
                                       data-reload="/stockOutcomeBill/list.do">删除</a>
                                    <a href="javascript:" class="btn_audit"
                                       data-url="/stockOutcomeBill/audit.do?id=${entity.id}"
                                       data-reload="/stockOutcomeBill/list.do">审核</a>
                                </c:if>
                                <c:if test="${entity.status == 1}">
                                    <a href="/stockOutcomeBill/showStockOutcome.do?id=${entity.id}">查看</a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            <%-- 引出分页条 --%>
            <%@ include file="/WEB-INF/views/common/page.jsp"%>
        </div>
    </div>
</form>
</body>
</html>

