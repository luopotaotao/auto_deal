<%--<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>--%>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%--<!DOCTYPE html>--%>
<%--<html>--%>
<%--<head>--%>
    <%--<title>智能无线静荷载试验检测云平台</title>--%>
    <%--<jsp:include page="../layout/common.jsp"></jsp:include>--%>

<%--</head>--%>
<%--<body>--%>

<%--<div class="easyui-panel" style="width:30%">--%>
    <%--<input class="easyui-searchbox"--%>
           <%--data-options="prompt:'请输入机构名称',searcher:function(val){$('#dg').datagrid('load',{name:encodeURIComponent(val)});}"--%>
           <%--style="width:100%">--%>
<%--</div>--%>

<%--<table id="dg" style="width:100%"></table>--%>
<%--<div id="dlg_edit" style="width:100%;max-width:400px;padding:30px 60px;">--%>
    <%--<form id="ff" class="easyui-form" method="post" data-options="novalidate:true" action="${baseUrl}/post">--%>
        <%--<div style="margin-bottom:20px;display: none">--%>
            <%--<input class="easyui-textbox" name="id" style="width:100%" data-options="label:'记录编号:',required:true">--%>
        <%--</div>--%>
        <%--<div style="margin-bottom:20px">--%>
            <%--<input class="easyui-textbox" name="code" style="width:100%"--%>
                   <%--data-options="label:'机构编号:',required:true">--%>
        <%--</div>--%>
        <%--<div style="margin-bottom:20px">--%>
            <%--<input class="easyui-textbox" name="name" style="width:100%"--%>
                   <%--data-options="label:'机构名称:'">--%>
        <%--</div>--%>
        <%--<div style="margin-bottom:20px">--%>
            <%--<select class="easyui-combobox" data-options="editable:false" name="economy_typ" label="经济性质:"--%>
                    <%--style="width:100%">--%>
                <%--<option value="1">经济性质1</option>--%>
                <%--<option value="2">经济性质2</option>--%>
                <%--<option value="3">经济性质3</option>--%>
            <%--</select>--%>
        <%--</div>--%>
        <%--<div style="margin-bottom:20px">--%>
            <%--<input class="easyui-textbox" name="certificate_code" style="width:100%"--%>
                   <%--data-options="label:'电话号码:'">--%>
        <%--</div>--%>
        <%--<div style="margin-bottom:20px">--%>
            <%--<select class="easyui-combobox" data-options="editable:false" name="register_type" label="注册类型:"--%>
                    <%--style="width:100%">--%>
                <%--<option value="1">建设单位</option>--%>
                <%--<option value="2">施工单位</option>--%>
                <%--<option value="3">监理单位</option>--%>
            <%--</select>--%>
        <%--</div>--%>
    <%--</form>--%>
<%--</div>--%>
<%--<div id="details"></div>--%>

<%--<script type="text/javascript">--%>
    <%--$(function () {--%>
        <%--var baseUrl = '/';--%>
        <%--$('#dg').datagrid({--%>
            <%--url: '../${baseUrl}/query',--%>
            <%--method: 'get',--%>
            <%--iconCls: 'icon-save',--%>
<%--//            width: 700,--%>
            <%--height: $('body').height(),--%>
            <%--fitColumns: true,--%>
            <%--singleSelect: false,--%>
            <%--pagination: true,--%>
            <%--pageSize: 10,--%>
            <%--toolbar: [{--%>
                <%--text: '添加',--%>
                <%--iconCls: 'icon-add',--%>
                <%--handler: function () {--%>
                    <%--showEditDialog();--%>
                <%--}--%>
            <%--}, {--%>
                <%--text: '编辑',--%>
                <%--iconCls: 'icon-edit',--%>
                <%--handler: function () {--%>
                    <%--var rows = $('#dg').datagrid('getChecked');--%>
                    <%--if (!rows || !rows.length) {--%>
                        <%--$.messager.alert('提示', '请选择要编辑的行!');--%>
                        <%--return;--%>
                    <%--}--%>
                    <%--if (rows.length > 1) {--%>
                        <%--$.messager.alert('提示', '只能选择一行进行编辑!');--%>
                        <%--return;--%>
                    <%--} else {--%>
                        <%--showEditDialog(rows[0]);--%>
                    <%--}--%>
                <%--}--%>
            <%--}, {--%>
                <%--text: '删除',--%>
                <%--iconCls: 'icon-remove',--%>
                <%--handler: function () {--%>
                    <%--var rows = $('#dg').datagrid('getChecked');--%>
                    <%--if (!rows || !rows.length) {--%>
                        <%--$.messager.alert('提示', '请选择要删除的行!');--%>
                        <%--return;--%>
                    <%--}--%>
                    <%--$.messager.confirm('提示', '是否确定删除这' + rows.length + '条数据?', function (r) {--%>
                        <%--if (r) {--%>
                            <%--remove($.map(rows, function (row) {--%>
                                <%--return row.id;--%>
                            <%--}), function () {--%>
                                <%--$('#dg').datagrid('reload');--%>
                            <%--});--%>
                        <%--}--%>
                    <%--});--%>
                <%--}--%>
            <%--}],--%>
            <%--columns: [[--%>
                <%--{field: 'ck', checkbox: true},--%>
                <%--{field: 'id', title: 'ID', hidden: true},--%>
                <%--{field: 'code', title: '机构编号', width: 80},--%>
                <%--{field: 'name', title: '机构名称', width: 80, align: 'right'},--%>
                <%--{--%>
                    <%--field: 'economy_typ', title: '经济性质', width: 80, align: 'right',--%>
                    <%--formatter: function (val, row) {--%>
                        <%--return {1: '性质1', 2: '性质2', 3: '性质3'}[val];--%>
                    <%--}--%>
                <%--},--%>
                <%--{field: 'certificate_code', title: '资质证书编号', width: 80, align: 'right'},--%>
                <%--{--%>
                    <%--field: 'register_type', title: '注册类型', width: 80, align: 'right',--%>
                    <%--formatter: function (val, row) {--%>
                        <%--return {1: '建设单位', 2: '施工单位', 3: '监理单位'}[val];--%>
                    <%--}--%>
                <%--},--%>
                <%--{--%>
                    <%--field: 'null', title: '操作', width: 80, align: 'right',--%>
                    <%--formatter: function (val, row) {--%>
                        <%--var str_arr = [--%>
                            <%--'<a href="javascript:manageUsers(',--%>
                            <%--row['id'],--%>
                            <%--');">人员</a>&nbsp;',--%>
                            <%--'<a href="javascript:manageEquipments(',--%>
                            <%--row['id'],--%>
                            <%--');">设备</a>'--%>
                        <%--];--%>
                        <%--return str_arr.join('');--%>
                    <%--}--%>
                <%--}--%>
            <%--]],--%>
            <%--onHeaderContextMenu: function (e, field) {--%>
                <%--e.preventDefault();--%>
                <%--if (!$.cmenu) {--%>
                    <%--createColumnMenu();--%>
                <%--}--%>
                <%--$.cmenu.menu('show', {--%>
                    <%--left: e.pageX,--%>
                    <%--top: e.pageY--%>
                <%--});--%>
            <%--}--%>
        <%--});--%>

        <%--function createColumnMenu() {--%>
            <%--$.cmenu = $('<div/>').appendTo('body');--%>
            <%--$.cmenu.menu({--%>
                <%--onClick: function (item) {--%>
                    <%--if (item.iconCls == 'icon-ok') {--%>
                        <%--$('#dg').datagrid('hideColumn', item.name);--%>
                        <%--$.cmenu.menu('setIcon', {--%>
                            <%--target: item.target,--%>
                            <%--iconCls: 'icon-empty'--%>
                        <%--});--%>
                    <%--} else {--%>
                        <%--$('#dg').datagrid('showColumn', item.name);--%>
                        <%--$.cmenu.menu('setIcon', {--%>
                            <%--target: item.target,--%>
                            <%--iconCls: 'icon-ok'--%>
                        <%--});--%>
                    <%--}--%>
                <%--}--%>
            <%--});--%>
            <%--var fields = $('#dg').datagrid('getColumnFields');--%>
            <%--for (var i = 0; i < fields.length; i++) {--%>
                <%--var field = fields[i];--%>
                <%--var col = $('#dg').datagrid('getColumnOption', field);--%>
                <%--$.cmenu.menu('appendItem', {--%>
                    <%--text: col.title,--%>
                    <%--name: field,--%>
                    <%--iconCls: 'icon-ok'--%>
                <%--});--%>
            <%--}--%>
        <%--}--%>

        <%--$('#dlg_edit').dialog({--%>
            <%--title: "添加机构",--%>
            <%--closed: true,--%>
            <%--modal: true,--%>
            <%--draggable: false,--%>
            <%--iconCls: 'icon-add',--%>
            <%--buttons: [{--%>
                <%--text: '保存',--%>
                <%--iconCls: 'icon-ok',--%>
                <%--handler: submitForm--%>
            <%--}, {--%>
                <%--text: '取消',--%>
                <%--iconCls: 'icon-cancel',--%>
                <%--handler: closeEditDialog--%>
            <%--}]--%>
        <%--})--%>

        <%--function showEditDialog(data) {--%>
            <%--var $ff = $('#ff');--%>
            <%--if (data) {--%>
                <%--$ff.form('load', data);--%>
                <%--$ff.form({url: 'put'});--%>
            <%--} else {--%>
                <%--$ff.form({url: 'post'});--%>
            <%--}--%>
            <%--$('#dlg_edit').dialog('open');--%>
        <%--}--%>

        <%--function submitForm() {--%>
            <%--$.messager.progress();	// display the progress bar--%>
            <%--$('#ff').form('submit', {--%>
                <%--onSubmit: function () {--%>
                    <%--var isValid = $(this).form('validate');--%>
                    <%--if (!isValid) {--%>
                        <%--$.messager.progress('close');	// hide progress bar while the form is invalid--%>
                    <%--}--%>
                    <%--return isValid;	// return false will stop the form submission--%>
                <%--},--%>
                <%--success: function (data) {--%>
                    <%--$.messager.progress('close');	// hide progress bar while submit successfully--%>
<%--//                    {--%>
<%--//                        "success": true,--%>
<%--//                            "message": "Message sent successfully."--%>
<%--//                    }--%>
                    <%--data = $.parseJSON(data);--%>
                    <%--if (data.flag) {--%>
                        <%--$.messager.alert('提示', '保存成功!');--%>
                        <%--closeEditDialog(true);--%>
                    <%--} else {--%>
                        <%--$.messager.alert('提示', data.message || '保存失败,请检查网络连接或者权限!');--%>
                    <%--}--%>
                <%--}--%>
            <%--});--%>
        <%--}--%>

        <%--function closeEditDialog(needRefresh) {--%>
            <%--if (needRefresh) {--%>
                <%--$('#dg').datagrid('reload');--%>
            <%--}--%>
            <%--$('#ff').form('clear');--%>
            <%--$('#dlg_edit').dialog('close');--%>
        <%--}--%>

        <%--function remove(ids) {--%>
            <%--$.ajax({--%>
                <%--url: '../${baseUrl}/delete',--%>
                <%--data: {ids: ids},--%>
                <%--type: 'post',--%>
                <%--dataType: 'json'--%>
            <%--}).done(function (ret) {--%>
                <%--if (ret && ret.flag) {--%>
                    <%--$.messager.alert('提示', '删除成功!');--%>
                    <%--$('#dg').datagrid('reload');--%>
                <%--} else {--%>
                    <%--$.messager.alert('提示', ret, msg || '删除失败!');--%>
                <%--}--%>
            <%--}).fail(function () {--%>
                <%--$.messager.alert('提示', '删除失败!');--%>
            <%--});--%>
        <%--}--%>



    <%--});--%>
    <%--function openDialog(title,href){--%>
        <%--$('#details').dialog({--%>
            <%--title: title,--%>
            <%--width: $('body').width()*0.8,--%>
            <%--height: $(document).height(),--%>
            <%--closed: false,--%>
            <%--cache: false,--%>
            <%--href: href,--%>
            <%--modal: true--%>
        <%--});--%>
    <%--}--%>


    <%--function manageUsers(id) {--%>
        <%--var href = '../basic/inspector/index/'+id;--%>
        <%--openDialog('人员管理',href);--%>
    <%--}--%>
    <%--function manageEquipments(id){--%>
        <%--var href = '../basic/equipment/index/'+id;--%>
        <%--openDialog('设备管理',href);--%>
    <%--}--%>

<%--</script>--%>
<%--</body>--%>
<%--</html>--%>