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
		$(function () {
			$(".btn_reload").click(function () {
				var url = $(this).data("url");
			    //弹出加载权限的对话框
				showDialog("加载权限需要耗费较长时间,确实要重新加载吗?",function () {
					//发送ajax请求访问加载权限的方法
					$.get(url,function (data) {
						if (data.success) {
						    showDialog("加载成功",function () {
								location.reload();
                            });
						}
                    }, "json");
                },true);
            });
        });
	</script>
<title>PSS-权限管理</title>
<style>
	.alt td{ background:black !important;}
</style>
</head>
<body>
	<form id="searchForm" action="/permission/list.do" method="post">
		<div id="container">
			<div class="ui_content">
				<div class="ui_text_indent">
					<div id="box_border">
						<div id="box_top">搜索</div>
						<div id="box_bottom">
							<input type="button" value="加载权限" data-url="/permission/reload.do"
								   class="ui_input_btn01 btn_reload"/>
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
							<th>权限名称</th>
							<th>权限表达式</th>
							<th></th>
						</tr>
						<c:forEach var="entity" items="${result.data}" varStatus="num">
							<tr>
								<td><input type="checkbox" class="acb" /></td>
								<td>${num.count}</td>
								<td>${entity.name}</td>
								<td>${entity.expression}</td>
								<td>
									<a href="javascript:" class="btn_delete"
									   data-url="/permission/delete.do?id=${entity.id}"
									   data-reload="/permission/list.do"
									>删除</a>
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

