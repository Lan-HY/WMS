<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shabiyang" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>叩丁狼PSS</title>
    <link href="/style/login_css.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="/js/plugins/jQueryForm/jQueryForm.js"></script>
    <script>
        $(function () {
            $("#login_sub").click(function () {
                //使用ajax提交表单
                /*$("#submitForm").ajaxSubmit(function (data) {
                    if (data.success) { //登录成功
                        //跳转到/main.do中
                        location.href = "/main.do";
                    } else { //登录失败
                        $("#login_err").html(data.msg);
                    }
                });*/
                $("#submitForm").submit();
            });
        });
    </script>
</head>
<body>
<div id="login_center">
    <div id="login_area">
        <div id="login_box">
            <div id="login_form">
                <form id="submitForm" action="/login.do" method="post">
                    <div id="login_tip">
                        <span id="login_err" class="sty_txt2">

                        </span>
                    </div>
                    <div>
                        账号:<input type="text" name="username" class="username" id="name" value="admin"/>
                    </div>
                    <div>
                        密码:
                        <input type="password" name="password" class="pwd" id="pwd" value="1"/>
                    </div>
                    <div id="btn_area">
                        <input type="button" class="login_btn" id="login_sub"  value="登  录"/>
                        <input type="reset" class="login_btn" id="login_ret" value="重 置"/>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
