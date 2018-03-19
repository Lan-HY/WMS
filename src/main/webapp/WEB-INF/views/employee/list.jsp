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
    <script>


    </script>
    <title>PSS-账户管理</title>
    <style>
        .alt td{ background:black !important;}
    </style>
</head>
<body>
<form id="searchForm" action="/employee/list.do" method="post">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                    <div id="box_center">
                        姓名/邮箱
                        <input type="text" class="ui_input_txt02" name="keyword" value="${qo.keyword}"/>
                        所属部门
                        <select id="deptId" class="ui_select01" name="deptId">
                            <option value="-1">全部部门</option>
                            <c:forEach var="ds" items="${depts}">
                                <option value="${ds.id}">${ds.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <script>
                        $("#deptId option[value='${qo.deptId}']").prop("selected", true);
                    </script>
                    <div id="box_bottom">
                        <input type="button" value="查询" class="ui_input_btn01 btn_page" data-page="1"/>
                        <input type="button" value="新增" data-url="/employee/input.do"
                               class="ui_input_btn01 btn_input"/>
                        <input type="button" value="批量删除" data-url="/employee/batchDelete.do"
                               class="ui_input_btn01 btn_batchDelete"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="ui_content">
            <div class="ui_tb">
                <table class="table" cellspacing="0" cellpadding="0" width="100%" align="center" border="0">
                    <tr>
                        <th width="30"><input type="checkbox" id="all" /></th>
                        <th>编号</th>
                        <th>用户名</th>
                        <th>EMAIL</th>
                        <th>年龄</th>
                        <th>所属部门</th>
                        <th></th>
                    </tr>
                    <c:forEach var="entity" items="${result.data}" varStatus="num">
                        <tr>
                            <td><input type="checkbox" class="acb" data-eid="${entity.id}"/></td>
                            <td>${num.count}</td>
                            <td>${entity.name}</td>
                            <td>${entity.email}</td>
                            <td>${entity.age}</td>
                            <td>${entity.dept.name}</td>
                            <td>
                                <a href="/employee/input.do?id=${entity.id}">编辑</a>
                                <a href="javascript:" class="btn_delete" data-reload="/employee/list.do"
                                   data-url="/employee/delete.do?id=${entity.id}">删除</a>
                            </td>
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
