<%--
  Created by IntelliJ IDEA.
  User: tt
  Date: 2016/12/26
  Time: 16:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page trimDirectiveWhitespaces="true" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="<c:url value="/resources/jslib/jquery-1.11.3.js"/>" type="text/javascript"
            charset="utf-8"></script>
    <script>
        $(function () {

            initSocket();
            initEventHanlders();

            function initSocket() {
                var url = 'ws://' + window.location.host + '/schedule';
                var socket = new WebSocket(url);
                socket.onopen = function () {
                    $('#status').text('数据读取中...');
                    socket.send("getStatus");
                    setInterval(function () {
                        socket.send("getStatus");
                        socket.send("getEvent");
                    }, 5000);
                }
                socket.onmessage = function (e) {
                    var msg_str = e.data;
                    var msg = msg_str.split(':::');
                    switch (msg[0]) {
                        case 'status':
                            showStatus(msg[1]);
                            break;
                        case 'event':
                            showEvent(msg[1]);
                            break;
                    }
                }
                socket.onclose = function () {
                    showStatus('连接断开,请刷新页面重试!');
//                    window.location.reload();
                }
            }
            function initEventHanlders() {
                $('#btn_login').bind('click',login);
                $('#btn_setnum').bind('click',setNum);
                $('#btn_start').bind('click',start);
                $('#btn_stop').bind('click',stop);
            }

            function showEvent(msg) {
                if (!msg) {
                    return;
                }
                $ul = $('#event_list');
                if ($ul.length > 50) {
                    $li = $ul.children('li').first();
                    $li.text(msg);
                    $li.appendTo($ul);
                } else {
                    $('<li/>').text(msg).appendTo($ul);
                }
            }

            function showStatus(status) {
                $('#status').text(status);
            }
            function login() {
                var $account = $('#account'),
                        $password = $('#password');
                var account = $account.val(),
                        password = $password.val();
                if(!account||!password){
                    alert("请输入用户名和密码!");
                    return;
                }
                $.ajax({
                    url:'<c:url value="/schedule/login"/>',
                    type:'post',
                    data:{
                        account:account,
                        password:password
                    },
                    dataType:'json'
                }).success(function (ret) {
                    showStatus(ret);
                    $('#login_form_div').hide();
                }).fail(function () {
                    alert('登录失败!');
                });
            }
            function setNum() {
                var $num = $('#num');
                var num = $num.val();
                if(!num){
                    alert("请填写期号!");
                    return;
                }
                $.ajax({
                    url:'<c:url value="/schedule/setnum/"/>'+num,
                    type:'post',
                    dataType:'json'
                }).success(function (ret) {
                    showStatus(ret);
                }).fail(function () {
                    alert('设置失败!');
                });
            }
            function start() {
                $.get('<c:url value="/schedule/start"/>',function (ret) {
                    showStatus(ret);
                });
            }
            function stop() {
                $.get('<c:url value="/schedule/stop"/>',function (ret) {
                    showStatus(ret);
                });
            }
        });
    </script>
</head>
<body>
<div id="login_form_div">
    <form action="">
        <label for="account">账号:</label><input type="text" name="account" id="account"><br>
        <label for="password">密码:</label><input type="password" name="password" id="password"><br>
        <input id="btn_login" type="button" value="登录">
    </form>
</div>
<div>
    <input type="text" id="num">
    <button id="btn_setnum">设置期号</button>
</div>
<div>
    <button id="btn_start">开始</button>
    <button id="btn_stop">暂停</button>
</div>
<p id="status"></p>
<div>
    <ul id="event_list">

    </ul>
</div>
</body>
</html>
