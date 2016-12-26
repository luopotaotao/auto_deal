<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page trimDirectiveWhitespaces="true" %>

<script type="text/javascript" charset="utf-8">
    $.extend($.fn.validatebox.defaults.rules, {
        /*必须和某个字段相等*/
        equalTo: {
            validator: function (value, param) {
                return $(param[0]).val() == value;
            },
            message: '两次输入不一致!'
        }
    });
    $(function () {

        openModule(
                <sec:authorize access="hasAnyRole('ADMIN','CUSTOM')">
                '<c:url value="/overview/main/index"/>'
        </sec:authorize>
        <sec:authorize access="hasRole('SUPER')">
        '<c:url value="/basic/index/index?selectDept=true"/>'
        </sec:authorize>
        )
        ;
    });

    function logoutFun() {
        $.messager
                .confirm(
                        '操作提醒',
                        "您确定要退出本系统？",
                        function (c) {
                            if (c) {
                                $.ajax({
                                    url: '<c:url value="/logout"/>',
                                    type: 'GET',
                                    timeout: 1000
                                }).always(function () {
                                    location.href = '<c:url value="/login"/>';
                                });
                            }
                        });
    }
    function showUserInfo() {
        $.ajax({
            url: '<c:url value="/basic/user/currentUser"/>',
            type: 'get',
            dataType: 'json'
        }).done(function (ret) {
            $('#ff_user_info').form('load', ret);
            $('#dlg_user_info').dialog('open');
        }).fail(function () {
            $.messager.alert('提示', '获取数据失败,请重新尝试或联系管理员!');
        });
    }

    function openModule(url) {
        var $iframe = $('#panel_main');
        var old_url = $iframe.attr('src');
        if (old_url != url) {
            $iframe.attr('src', url);
        }
    }
    function switchDept(id) {
        var base_url = '<c:url value="/resources/upload/"/>';
        var href = '<c:url value="/switchDept/"/>' + id;
        $.ajax({
            url: href,
            type: 'get',
            dataType: 'json'
        }).done(function (ret) {
            if (ret.id) {
                var logo = null;
                if (ret.logo) {
                    logo = '<img src="' + base_url + ret.logo + '">'
                } else {
                    logo = '<h2 style="color:white;margin: 5px;">智能无线静荷载试验检测云平台</h2>';
                }
                $('#logo_div').empty().append(logo);
                $('#current_dept_name').text(ret.name);
                openModule('<c:url value="/overview/main/index"/>');
            }
        }).fail(function () {
            $.messager.alert('提示', '操作失败，请重新尝试或联系管理员！');
        });
    }
</script>

<style type="text/css">
    .module_icon {
        width: 19px;
        height: 19px;
        vertical-align: -5px;
    }

    html, body {
        overflow: hidden;
    }

    body {
        margin: 0px;
        overflow: hidden;
        background-color: #2696cb;

    }

    .topul {
        list-style-type: none;
        margin: 0;
        padding: 0;
        margin-top: 20px;
        /*padding-top:3px;*/
        padding-bottom: 8px;
        font-size: 13px;
    }

    .topul li {
        display: inline;
    }

    .topul li a {
        color: white;
        text-align: center;
        text-decoration: none;
        padding: 8px 14px 8px 10px;
    }

    .topul li a #img {
        vertical-align: middle;
    }

    .topul li a.onnav {
        /*background: url('/public/images/newweb_06.png') no-repeat;*/
    }
</style>
<div style="background: rgb(38,150,203); height: 59px">
    <div id="logo_div" style="float: left;width: 36%;height: 59px;max-width: 460px;">
        <sec:authentication property="principal.dept" var="dept"></sec:authentication>
        <c:choose>
            <c:when test="${empty dept.logo}">
                <h2 style="color:white;margin: 5px;">智能无线静荷载试验检测云平台</h2>
            </c:when>
            <c:otherwise><img src="<c:url value="/resources/upload/${dept.logo}"/>"></c:otherwise>
        </c:choose>
    </div>
    <div style="float: left;width: 48%;height: 59px;min-width: 500px;">

        <ul class="topul">
            <li>
                <a href="javascript:openModule('<c:url value="/overview/main/index"/>');"
                   id="16" class="menus"><img
                        src="<c:url value="/resources/style/images/icons/icon_monitor.png"/>"
                        class="module_icon">工程概览</a>
            </li>

            <li>
                <a href="javascript:openModule('<c:url value="/project/manage/index"/>');"
                   id="19" class="menus"><img
                        src="<c:url value="/resources/style/images/icons/icon_report.png"/>"
                        class="module_icon">工程管理</a>
            </li>
            <li><a href="javascript:openModule('<c:url value="/basic/index/index"/>')"
                   id="18"
                   class="menus"><img src="<c:url value="/resources/style/images/icons/icon_config.png"/>"
                                      class="module_icon">平台设置</a>
            </li>
            <li><a href="javascript:openModule('<c:url value="/about/index"/>')"
                   id="20"
                   class="menus"><img src="<c:url value="/resources/style/images/icons/icon_about.png"/>"
                                      class="module_icon">关于平台</a>
            </li>
        </ul>

    </div>
    <div style="float: right;width: 15%;height: 59px;">
        <div style="margin-top: 10px;text-align: right;margin-right: 5px;">

            <div style="margin-right: 5px">当前用户：
                <sec:authentication property="principal.realName" var="realName"/>
                ${realName}
            </div>


            <div style="margin-top: 10px">
                <sec:authorize access="hasRole('SUPER')">
                    <sec:authentication property="principal.dept" var="dept"/>
                    <a id="current_dept_name"
                       href="javascript:openModule('<c:url value="/basic/index/index?selectDept=true"/>')">
                        <c:if test="${not empty dept}">
                            ${dept.name}
                        </c:if>
                        <c:if test="${empty dept}">
                            切换公司
                        </c:if>
                    </a>
                </sec:authorize>
                <a href="javascript:;" onclick="showUserInfo()"
                   style="color: white;"><img
                        src="<c:url value="/resources/style/images/icons/icon_user_sm.png"/>"
                        style="width: 18px;height: 18px;vertical-align: -5px;">账号信息</a>&nbsp;
                <a href="javascript:;" onclick="logoutFun();" style="color: white;"><img
                        src="<c:url value="/resources/style/images/icons/icon_logout.png"/>"
                        style="width: 18px;height: 18px;vertical-align: -5px;">退出系统</a>
            </div>
        </div>
    </div>


</div>

<div id="dlg_user_info" class="easyui-dialog" style="width:100%;max-width:400px;padding:30px 60px;"
     data-options="
            title: '账号信息',
            closed: true,
            modal: true,
            draggable: false,
            //iconCls: 'icon-add',
            buttons: [{
            text: '修改密码',
            handler: function(){
                if($('#ff_user_info').form('enableValidation').form('validate')){
                    $.ajax({
                        url:'<c:url value="/basic/user/changePwd"/>',
                        type:'post',
                        dataType:'json',
                        data:{
                            old:$('#user_pwd_old').textbox('getValue'),
                            cur:$('#user_pwd_new').textbox('getValue')
                        }
                    })
                    .success(function(ret){
                        if(ret.flag){
                            $.messager.alert('系统提示','修改密码成功!重新登录生效!');
                            return;
                        }
                        $.messager.alert('系统提示',ret.msg||'修改密码失败!');
                    })
                    .fail(function(){
                        $.messager.alert('系统提示',ret.msg||'修改密码失败!');
                    });
                }

            }
            },{
            text: '确定',
            handler: function(){
                $('#ff_user_info').form('clear');
                $('#dlg_user_info').dialog('close');

            }
            }]
        ">
    <form id="ff_user_info" class="easyui-form" method="post" data-options="novalidate:true"
          action="<c:url value="/basic/user/put"/>"/>
    <div style="margin-bottom:20px">
        <input class="easyui-textbox" name="username" style="width:100%"
               data-options="label:'账号:',editable:false">
    </div>
    <div style="margin-bottom:20px">
        <input class="easyui-textbox" name="realName" style="width:100%"
               data-options="label:'账号:',editable:false">
    </div>
    <div style="margin-bottom:20px">
        <input class="easyui-textbox" name="email" style="width:100%"
               data-options="label:'Email:',editable:true">
    </div>

    <div style="margin-bottom:20px">
        <input id="user_pwd_old" class="easyui-textbox" name="password" style="width:100%"
               data-options="label:'原始密码:',required:true" type="password">
    </div>
    <div style="margin-bottom:20px">
        <input id="user_pwd_new" class="easyui-textbox" name="new_password" style="width:100%"
               data-options="label:'新密码:',required:true" validType="length[4,32]" id="new_password" type="password">
    </div>
    <div style="margin-bottom:20px">
        <input id="user_pwd_repeat" class="easyui-textbox" name="role" style="width:100%"
               data-options="label:'确认密码:',required:true" type="password"
               validType="equalTo['#user_pwd_new']" invalidMessage="两次输入密码不匹配">
    </div>
    </form>
</div>

