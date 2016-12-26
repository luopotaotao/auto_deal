
<%--
  Created by IntelliJ IDEA.
  Inspector: tt
  Date: 2016/10/7
  Time: 23:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript" charset="utf-8">

    try{
        top.openModule('<c:url value="/basic/index/index?selectDept=true"/>');
        top.$.messager.alert('操作提示',"请先设置公司再查看数据!");
    } catch(e){}

</script>