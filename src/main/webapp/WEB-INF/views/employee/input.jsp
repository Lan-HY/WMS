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
    <script type="text/javascript" src="/js/plugins/validation/jquery.validate.min.js"></script>
    <script type="text/javascript" src="/js/plugins/validation/messages_cn.js"></script>
    <script type="text/javascript" src="/js/plugins/artDialog/jquery.artDialog.js?skin=blue"></script>
    <script type="text/javascript" src="/js/plugins/jQueryForm/jQueryForm.js"></script>
    <script type="text/javascript" src="/js/commonAll.js"></script>
    <script>
        $(function () {
            //把表单做成JQ对象,调用插件的校验方法
            $("#editForm").validate({
                rules: {
                    name: {
                        required:true,
                        rangelength:[2,6]
                    },
                    password: {
                        required:true
                    },
                    repassword: {
                        equalTo:"#password"
                    },
                    email: {
                        required:true,
                        email:true
                    },age: {
                        digits:true,
                        range:[16,65]
                    }
                }
            });

            //点击超级管理员按钮删除分配角色,再次点击显示分配角色
            $("#admin").click(function () {
                if (this.checked) {
                    //保留事件删除分配角色
                    role = $("#role").detach();
                } else {
                    //找到离当前元素最近的tr祖先元素
                    $(this).closest("tr").after(role);
                }
            });

            //如果当前用户默认就是超级管理器,不应该显示分配角色
            if (${empty entity ? 0 : entity.admin}) {
                role = $("#role").detach();
            }

            //左移 右移
            $("#selectAll").click(function () {
                $(".all_roles option").appendTo(".selected_roles");
            });

            $("#deselectAll").click(function () {
                $(".selected_roles option").appendTo(".all_roles");
            });

            $("#select").click(function () {
                $(".all_roles option:selected").appendTo(".selected_roles");
            });

            $("#deselect").click(function () {
                $(".selected_roles option:selected").appendTo(".all_roles");
            });

            //删除重复的角色选项
            var ids = $.map($(".selected_roles option"),function (item) {
                return item.value;
            });

            $.each($(".all_roles option"),function (index,item) {
                if ($.inArray(item.value,ids) != -1) {
                    $(item).remove();
                }
            });

        });
    </script>
</head>
<body>
<form id="editForm" action="/employee/saveOrUpdate.do" method="post">
    <input type="hidden" name="id" value="${entity.id}">
    <div id="container">
        <div id="nav_links">
            <span style="color: #1A5CC6;">用户编辑</span>
            <div id="page_close">
                <a>
                    <img src="/images/common/page_close.png" width="20" height="20" style="vertical-align: text-top;"/>
                </a>
            </div>
        </div>
        <div class="ui_content">
            <table cellspacing="0" cellpadding="0" width="100%" align="left" border="0">
                <tr>
                    <td class="ui_text_rt" width="140">用户名</td>
                    <td class="ui_text_lt">
                        <input name="name" value="${entity.name}" class="ui_input_txt02"/>
                    </td>
                </tr>
                <c:if test="${entity.id == null}">
                    <tr>
                        <td class="ui_text_rt" width="140">密码</td>
                        <td class="ui_text_lt">
                            <input type="password" name="password" id="password" class="ui_input_txt02"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="ui_text_rt" width="140">验证密码</td>
                        <td class="ui_text_lt">
                            <input name="repassword" type="password" class="ui_input_txt02" />
                        </td>
                    </tr>
                </c:if>
                <tr>
                    <td class="ui_text_rt" width="140">Email</td>
                    <td class="ui_text_lt">
                        <input name="email" value="${entity.email}" class="ui_input_txt02"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">年龄</td>
                    <td class="ui_text_lt">
                        <input name="age" value="${entity.age}" class="ui_input_txt02"/>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">所属部门</td>
                    <td class="ui_text_lt">
                        <select id="dept" name="dept.id" class="ui_select03">
                            <c:forEach var="ds" items="${depts}">
                                <option value="${ds.id}">${ds.name}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="ui_text_rt" width="140">超级管理员</td>
                    <td class="ui_text_lt">
                        <input id="admin" type="checkbox" name="admin" class="ui_checkbox01"/>
                    </td>
                </tr>
                <script>
                    $("#dept option[value='${entity.dept.id}']").prop("selected",true);
                    $("#admin").prop("checked",${empty entity ? 0 : entity.admin});
                </script>
                <tr id="role">
                    <td class="ui_text_rt" width="140">分配角色</td>
                    <td class="ui_text_lt">
                        <table>
                            <tr>
                                <td>
                                    <select multiple="true" class="ui_multiselect01 all_roles">
                                        <!--显示系统中所有的角色-->
                                        <c:forEach var="obj" items="${roles}">
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
                                    <select name="ids" multiple="true" class="ui_multiselect01 selected_roles">
                                        <!--当前用户所拥有的角色-->
                                        <c:forEach var="obj" items="${entity.roles}">
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
                        &nbsp;<input type="button" value="确定保存" data-url="/employee/list.do" class="ui_input_btn01 btn_submit"/>
                        &nbsp;<input id="cancelbutton" type="button" value="重置" class="ui_input_btn01"/>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</form>
</body>
</html>