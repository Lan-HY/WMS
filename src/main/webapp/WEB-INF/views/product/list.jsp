<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
    <link href="/style/common_style.css" rel="stylesheet" type="text/css">
    <link href="/js/plugins/fancyBox/jquery.fancybox.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <script src="/js/plugins/fancyBox/jquery.fancybox.js"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <script>
        $(function () {
            $(".fancybox").fancybox();
        });
    </script>
    <title>PSS-商品管理</title>
    <style>
        .alt td{ background:black !important;}
    </style>
</head>
<body>
<form id="searchForm" action="/product/list.do" method="post">
    <div id="container">
        <div class="ui_content">
            <div class="ui_text_indent">
                <div id="box_border">
                    <div id="box_top">搜索</div>
                    <div id="box_center">
                        商品名称/编码
                        <input type="text" class="ui_input_txt02" name="keyword" value="${qo.keyword}"/>
                        商品品牌
                        <select id="brandId" class="ui_select01" name="brandId">
                            <option value="-1">全部品牌</option>
                            <c:forEach items="${brands}"  var="brand">
                                <option value="${brand.id}">${brand.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <script>
                        $("#brandId option[value='${qo.brandId}']").prop("selected",true);
                    </script>
                    <div id="box_bottom">
                        <input type="button" value="查询" class="ui_input_btn01 btn_page" data-page="1"/>
                        <input type="button" value="新增" data-url="/product/input.do"
                               class="ui_input_btn01 btn_input"/>
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
                        <th>商品图片</th>
                        <th>商品名称</th>
                        <th>商品编码</th>
                        <th>商品品牌</th>
                        <th>成本价</th>
                        <th>零售价</th>
                        <th></th>
                    </tr>
                    <c:forEach var="entity" items="${result.data}" varStatus="num">
                        <tr>
                            <td><input type="checkbox" class="acb" /></td>
                            <td>${num.count}</td>
                            <td>
                                <a class="fancybox" href="${entity.imagePath}" data-fancybox-group="gallery" title="${entity.name}">
                                    <img src="${entity.smallImagePath}" width="70px">
                                </a>
                            </td>
                            <td>${entity.name}</td>
                            <td>${entity.sn}</td>
                            <td>${entity.brandName}</td>
                            <td>${entity.costPrice}</td>
                            <td>${entity.salePrice}</td>
                            <td>
                                <a href="/product/input.do?id=${entity.id}">编辑</a>
                                <a href="javascript:" class="btn_delete"
                                   data-url="/product/delete.do?id=${entity.id}&imagePath=${entity.imagePath}"
                                   data-reload="/product/list.do">删除</a>
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

