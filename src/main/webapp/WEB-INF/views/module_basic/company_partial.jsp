<%--
  Created by IntelliJ IDEA.
  User: taotao
  Date: 2016/9/30
  Time: 15:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="easyui-panel" style="width:30%">
    <input class="easyui-searchbox"
           data-options="prompt:'请输入公司名称',searcher:function(val){$('#grid').datagrid('load',{typ:${typ},name:encodeURIComponent(val)});}"
           style="width:100%">
</div>
<table id="grid" class="easyui-datagrid" style="width:100%;height:100%"
       data-options="singleSelect:false,url:'<c:url value="/basic/company/query"/>?typ=${typ}',method:'get'">
    <thead>
    <tr>
        <th data-options="field: 'ck', checkbox: true"></th>
        <th data-options="field: 'id', title: 'ID', hidden:true"></th>
        <th data-options="field: 'name', title: '公司名称', width: 120"></th>
        <th data-options="field: 'contacts', title: '联系人', width: 80, align: 'right'"></th>
        <th data-options="field: 'tel', title: '电话号码', width: 80, align: 'right'"></th>
       <th data-options="field: 'typ', title: '公司类型', width: 80, align: 'right',
           formatter: function (val, row) {
            return {1: '建设单位',2:'施工单位',3:'监理单位'}[val];
            }
        "></th>
    </tr>
    </thead>
</table>
