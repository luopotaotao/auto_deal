<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>智能无线静荷载试验检测云平台</title>
    <link id="easyuiTheme" rel="stylesheet"
          href="<c:url value="/resources/jslib/easyui/themes/default/easyui.css"/>" type="text/css">
    <link rel="stylesheet" type="text/css"
          href="<c:url value="/resources/jslib/easyui/themes/icon.css"/>">
    <script type="text/javascript" src="<c:url value="/resources/jslib/jquery-1.11.3.js"/>" type="text/javascript"
            charset="utf-8"></script>
    <script type="text/javascript"
            src="<c:url value="/resources/jslib/easyui/jquery.easyui.min.js"/>"
            charset="utf-8"></script>
    <script type="text/javascript"
            src="<c:url value="/resources/jslib/easyui/locale/easyui-lang-zh_CN.js"/>"
            charset="utf-8"></script>

</head>
<body class="easyui-layout">

<div data-options="region:'north'"
     style="height: 59px; overflow: hidden;background:#b6c6f5 url('<c:url
             value="/resources/style/themes/default.jpg"/>') repeat-y"
     id="bannerbg">
    <jsp:include page="../layout/north.jsp"></jsp:include>
</div>

<div data-options="region:'center'" style="overflow: hidden">
    <iframe id="panel_main" style="height: 100%;width: 100%;" scrolling="no"></iframe>
</div>


<div data-options="region:'south',border:false"
     style="height: 15px; overflow: hidden;">
    <div class="panel-header panel-title" style="text-align: center;">
        版权所有：广州市建设科学研究院&nbsp;&nbsp;&nbsp;技术支持：北京联睿科科技有限公司&nbsp;&nbsp;&nbsp;电话：010-88888888&nbsp;&nbsp;&nbsp;版本号：V1.001.000-20161204
    </div>
</div>


<%--<jsp:include page="inc.jsp"></jsp:include>--%>
</body>
</html>