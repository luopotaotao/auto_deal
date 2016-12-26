<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

    <title>智能无线静荷载试验检测云平台</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="Pragma" content="no-cache"/>
    <meta http-equiv="Cache-Control" content="no-cache"/>
    <script type="text/javascript"
            src="<c:url value="/resources/jslib/jquery-1.11.3.js"/>"></script>
    <script type="text/javascript"
            src="<c:url value="/resources/jslib/artDialog/artDialog.js"/>"></script>
    <script type="text/javascript" charset="utf-8">
        $(function () {
            bindHandlers();
            function doLogin() {
                var name = $("#name").val();
                var pwd = $("#pwd").val();
                var kaptcha = $("#kaptcha").val();
                if (true) {//validate(name,pwd,kaptcha)) {
                    pwd = $("#pwd").val();
                    $.ajax({
                        url: "<c:url value="/login"/>",
                        dataType: 'json',
                        type: 'post',
                        data: {username: name, password: pwd, kaptcha: kaptcha,'${_csrf.parameterName}':'${_csrf.token}'}
                    }).success(function (r) {

                        try {
                            if (r.flag) {
                                //登录成功跳转
                                window.location.href = "<c:url value="/index"/>";
                            } else {
                                //显示错误信息
                                art.dialog({
                                    time: 1,
                                    icon: 'warning',
                                    content: r.msg
                                });
                            }
                        } catch (e) {
                            $.messager.alert('操作提示', result);
                        }
                    }).error(function (err) {
                        art.dialog({
                            time: 1,
                            icon: 'warning',
                            content: '登陆错误!'
                        });
                    }).complete(function () {
                        $("#pwd").val("");
                        $("#img").attr("src", "authimg?now=" + new Date());
                    });
                }
            }

            function validate(name, pwd, kaptcha) {
                $("#btn_login").focus();

                if (!name) {
                    art.dialog({
                        time: 1,
                        icon: 'warning',
                        content: '账号不能为空！'
                    });
                    $("#name").focus();
                    return false;
                }
                if (!pwd) {
                    art.dialog({
                        time: 1,
                        icon: 'warning',
                        content: '密码不能为空！'
                    });
                    $("#pwd").focus();
                    return false;
                } else {
                    $("#pwd").val(sha256_digest(pwd));
                }
                if (!kaptcha) {
                    art.dialog({
                        time: 1,
                        icon: 'warning',
                        content: '验证码不能为空！'
                    });
                    $("#kaptcha").focus();
                    return false;
                }
                return true;
            }

            function bindHandlers() {
                $('#btn_login').bind('click', doLogin);
            }
        });
    </script>
    <link rel="stylesheet" href="<c:url value="/resources/jslib/artDialog/skins/blue.css"/>"/>
    <style type="text/css">
        html, body {
            overflow: hidden;
            height: 100%;
            margin: 0px;
            padding: 0px;
            min-width: 1200px;
            font-size: 13px;
            font-family: microsoft yahei;
        }

        img {
            border: 0px;
        }

        .txt {
            border: 1px solid #fff;
            font-size: 13px;
            height: 20px;
            line-height: 19px
        }

        .btn input {
            background: url(<c:url value="/resources/style/images/d9_03.png"/>) no-repeat;
            border: none;
            width: 213px;
            height: 26px;
            cursor: pointer;
            float: left;
            margin: 15px 0px 0px 60px;
        }

        /*1b1b1b*/
        a {
            color: #f9f9f9;
            text-decoration: none;
        }

        .gdul {
            list-style-type: none;
            margin: 0;
            padding: 0;
            overflow: hidden;
        }

        .gdul li {
            display: inline;
        }
    </style>
</head>

<body>

<div id="bgTop"
     style="position: absolute; z-index: 1; width: 100%; height: 408px; background-color: rgb(38, 150, 203);">
    &nbsp;</div>
<div style="position:absolute;z-index:2;width: 100%">
    <img src="<c:url value="/resources/style/images/logo.png"/>" style="height: 56px;margin-left: 5px;">
    <div id="title" style="text-align: center; height: 55px; margin-top: 206px">
        <h1 style="color: white;font-size: 40px;font-family: initial;">智能无线静荷载试验检测云平台</h1>
    </div>

    <div style="height: 200px;width: 636px;background: url(<c:url value="/resources/style/images/031.gif"/>) no-repeat;margin: 0px auto">

        <div style="float: left;width: 30%;margin-top: 30px;margin-left: 30px;"><img src="<c:url value="/resources/style/images/logo_login.png"/>"></div>
        <div style="float: right;width: 60%;margin-top: 30px;">
            <table cellpadding="0" cellspacing="10" style="width: 300px;color: white;"
                   onkeydown="if(event.keyCode==13) {getLogin(); return false;}">
                <tbody>
                <tr>
                    <td width="80px" align="right">用户名：</td>
                    <td><input style="width: 180px" name="name" id="name" class="txt" tabindex="1"></td>
                </tr>
                <tr>
                    <td align="right">密&nbsp;&nbsp;&nbsp;码：</td>
                    <td><input style="width: 180px" type="password" name="pwd" id="pwd" class="txt" tabindex="2"></td>
                </tr>
                <tr>
                    <td align="right">验证码：</td>
                    <td><input name="kaptcha" id="kaptcha" style="width: 80px" maxlength="4" class="txt"
                               tabindex="3">
                        <%--<img id="code"--%>
                                                 <%--src="authimg?now=new Date()"--%>
                                                 <%--alt="点击更换"--%>
                                                 <%--width="65"--%>
                                                 <%--height="25"--%>
                                                 <%--align="absMiddle"--%>
                                                 <%--onClick="this.src='authimg?now='+new Date()">--%>
                        <img src="<c:url value="/kaptcha"/>" width="95" height="25" align="absMiddle" onClick="this.src='<c:url value="/kaptcha"/>?now='+new Date()">
                    </td>
                </tr>
                </tbody>
            </table>

            <div class="btn"><input type="button" name="btn_login" id="btn_login"></div>
        </div>
    </div>
</div>

</body>
</html>
