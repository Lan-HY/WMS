<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>信息管理系统</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="/style/basic_layout.css" rel="stylesheet" type="text/css">
<link href="/style/common_style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/js/jquery/jquery.js"></script>
<script src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
<script type="text/javascript" src="/js/plugins/jQueryForm/jQueryForm.js"></script>
<script type="text/javascript" src="/js/commonAll.js"></script>
<script>
	$(function () {
		// 把当前页面中的表单变成ajax提交,有submit按钮,直接使用ajaxForm比较好
		$(".role_submit").click(function () {
		    //选中右边所有的option
            $(".selected_permissions option").prop("selected",true);
            $(".selected_menus option").prop("selected",true);
            //ajax方式提交表单
            $("#editForm").ajaxSubmit(function (data) {
                if (data.success) {
                    showDialog("操作成功",function () {
                        location.href = "/role/list.do";
                    });
                }
            });
        });

		//左移 右移
		$("#selectAll").click(function () {
			$(".all_permissions option").appendTo(".selected_permissions");
		});

		$("#deselectAll").click(function () {
			$(".selected_permissions option").appendTo(".all_permissions");
		});

		$("#select").click(function () {
			$(".all_permissions option:selected").appendTo(".selected_permissions");
		});

		$("#deselect").click(function () {
			$(".selected_permissions option:selected").appendTo(".all_permissions");
		});

        $("#selectMenuAll").click(function () {
            $(".all_menus option").appendTo(".selected_menus");
        });

        $("#deselectMenuAll").click(function () {
            $(".selected_menus option").appendTo(".all_menus");
        });

        $("#selectMenu").click(function () {
            $(".all_menus option:selected").appendTo(".selected_menus");
        });

        $("#deselectMenu").click(function () {
            $(".selected_menus option:selected").appendTo(".all_menus");
        });
		//--------------------------------------------
		//在左边删除右边所拥有的权限option
		//1:获取到右边权限列表中所有的option的value
		var ids = $.map($(".selected_permissions option"),function (item) {
            //item就是原数组中迭代出来的每一个对象
			return item.value;
        });
		//2:获取到左边权限列表中所有的option
        $.each($(".all_permissions option"),function (index,item) {
        	//如果该option的value在ids数组中,则删除
			if ($.inArray(item.value,ids) != -1) {
				$(item).remove();
			}
        });
        //2:获取到左边权限列表中所有的option
        ids = $.map($(".selected_menus option"),function (item) {
            //item就是原数组中迭代出来的每一个对象
            return item.value;
        });
        //2:获取到左边权限列表中所有的option
        $.each($(".all_menus option"),function (index,item) {
            //如果该option的value在ids数组中,则删除
            if ($.inArray(item.value,ids) != -1) {
                $(item).remove();
            }
        });
	});
</script>
</head>
<body>
<form id="editForm" action="/role/saveOrUpdate.do" method="post">
	<input type="hidden" name="id" value="${entity.id}"/>
	<div id="container">
		<div id="nav_links">
			<span style="color: #1A5CC6;">角色编辑</span>
			<div id="page_close">
				<a>
					<img src="/images/common/page_close.png" width="20" height="20" style="vertical-align: text-top;"/>
				</a>
			</div>
		</div>
		<div class="ui_content">
			<table cellspacing="0" cellpadding="0" width="100%" align="left" border="0">
				<tr>
					<td class="ui_text_rt" width="140">角色名称</td>
					<td class="ui_text_lt">
						<input name="name" value="${entity.name}" class="ui_input_txt02"/>
					</td>
				</tr>
				<tr>
					<td class="ui_text_rt" width="140">角色编码</td>
					<td class="ui_text_lt">
						<input name="sn" value="${entity.sn}" class="ui_input_txt02"/>
					</td>
				</tr>
				<tr>
					<td class="ui_text_rt" width="140">分配权限</td>
					<td class="ui_text_lt">
						<table>
							<tr>
								<td>
									<select multiple="true" class="ui_multiselect01 all_permissions">
										<!--系统中所有的权限-->
										<c:forEach var="obj" items="${permissions}">
											<option value="${obj.id}">${obj.name}</option>
										</c:forEach>
									</select>
								</td>
								<td align="center">
									<input type="button" id="select" value="-->" class="left2right"/><br/>
									<input type="button" id="selectAll" value="==>" class="left2right"/><br/>
									<input type="button" id="deselect" value="<--" class="left2right"/><br/>
									<input type="button" id="deselectAll" value="<==" class="left2right"/>
								</td>
								<td>
									<select name="ids" multiple="true" class="ui_multiselect01 selected_permissions">
										<!--当前角色选中的权限-->
										<c:forEach var="obj" items="${entity.permissions}">
											<option value="${obj.id}">${obj.name}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="ui_text_rt" width="140">分配菜单</td>
					<td class="ui_text_lt">
						<table>
							<tr>
								<td>
									<select multiple="true" class="ui_multiselect01 all_menus">
										<!--系统中所有的菜单-->
										<c:forEach var="obj" items="${menus}">
											<option value="${obj.id}">${obj.name}</option>
										</c:forEach>
									</select>
								</td>
								<td align="center">
									<input type="button" id="selectMenu" value="-->" class="left2right"/><br/>
									<input type="button" id="selectMenuAll" value="==>" class="left2right"/><br/>
									<input type="button" id="deselectMenu" value="<--" class="left2right"/><br/>
									<input type="button" id="deselectMenuAll" value="<==" class="left2right"/>
								</td>
								<td>
									<select name="menuIds" multiple="true" class="ui_multiselect01 selected_menus">
										<!--当前角色选中的菜单-->
										<c:forEach var="obj" items="${entity.menus}">
											<option value="${obj.id}">${obj.name}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td class="ui_text_lt">
						&nbsp;<input type="button" value="确定保存" class="ui_input_btn01 role_submit"/>
						&nbsp;<input id="cancelbutton" type="button" value="重置" class="ui_input_btn01"/>
					</td>
				</tr>
			</table>
		</div>
	</div>
</form>
</body>
</html>