<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="easyui-panel" style="width:100%">

    <div class="easyui-panel" style="width:30%">
        <input class="easyui-searchbox"
               data-options="prompt:'请输入设备名称',searcher:function(val,typ){$('#dg_equipment').datagrid('load',{typ:typ,name:encodeURIComponent(val)});}"
               style="width:100%">
    </div>

    <table id="dg_equipment" style="width:100%"></table>
    <div id="dlg_equipment_edit" class="easyui-dialog destroy" style="width:100%;max-width:400px;padding:30px 60px;"
            data-options="
            title: '添加设备',
            closed: true,
            modal: true,
            draggable: false,
            iconCls: 'icon-add',
            buttons: [{
            text: '保存',
            iconCls: 'icon-ok',
            handler: $.submitEquipmentForm
            }, {
            text: '取消',
            iconCls: 'icon-cancel',
            handler: $.closeEquipmentEditDialog
            }]
    ">
        <form id="ff_equipment" class="easyui-form" method="post" data-options="novalidate:true" action="<c:url value="/basic/equipment/post"/>">
            <div style="margin-bottom:20px;display: none">
                <input class="easyui-textbox" name="id" style="width:100%" data-options="label:'id:'">
            </div>
            <div style="margin-bottom:20px">
                <input class="easyui-textbox" name="code" style="width:100%"
                       data-options="label:'设备编号:',required:true">
            </div>
            <div style="margin-bottom:20px">
                <input class="easyui-textbox" name="name" style="width:100%"
                       data-options="label:'设备名称:',required:true">
            </div>
            <div style="margin-bottom:20px">
                <input class="easyui-datebox" name="expiredDate" style="width:100%"
                       data-options="label:'有效日期:',editable:false,required:true">
            </div>
            <div style="margin-bottom:20px">
                <input class="easyui-textbox" name="note" style="width:100%"
                       data-options="label:'备注:',multiline:true,height:120">
            </div>
        </form>
    </div>
    <script type="text/javascript">
        $(function () {
            var dept_id = $('#details').data("data").id;
            $('#dg_equipment').datagrid({
                url: '<c:url value="/basic/equipment/query/"/>' + dept_id,
                method: 'get',
                height: $('body').height(),
                fitColumns: true,
                singleSelect: false,
                pagination: false,
                fitColumns: true,
                toolbar: [{
                    text: '添加',
                    iconCls: 'icon-add',
                    handler: function () {
                        showEditDialog();
                    }
                }, {
                    text: '编辑',
                    iconCls: 'icon-edit',
                    handler: function () {
                        var rows = $('#dg_equipment').datagrid('getChecked');
                        if (!rows || !rows.length) {
                            $.messager.alert('提示', '请选择要编辑的行!');
                            return;
                        }
                        if (rows.length > 1) {
                            $.messager.alert('提示', '只能选择一行进行编辑!');
                            return;
                        } else {
                            showEditDialog(rows[0]);
                        }
                    }
                }, {
                    text: '删除',
                    iconCls: 'icon-remove',
                    handler: function () {
                        var rows = $('#dg_equipment').datagrid('getChecked');
                        if (!rows || !rows.length) {
                            $.messager.alert('提示', '请选择要删除的行!');
                            return;
                        }
                        $.messager.confirm('提示', '是否确定删除这' + rows.length + '条数据?', function (r) {
                            if (r) {
                                remove($.map(rows, function (row) {
                                    return row.id;
                                }), function () {
                                    $('#dg_equipment').datagrid('reload');
                                });
                            }
                        });
                    }
                }],
                columns: [[
                    {field: 'ck', checkbox: true},
                    {field: 'id', title: 'ID', hidden:true},
                    {field: 'code', title: '设备编号'},
                    {field: 'name', title: '设备名称'},
                    {field: 'expiredDate', title: '有效日期'},
                    {field: 'note', title: '备注'}
                ]],
                onHeaderContextMenu: function (e, field) {
                    e.preventDefault();
                    if (!$.cmenu) {
                        createColumnMenu();
                    }
                    $.cmenu.menu('show', {
                        left: e.pageX,
                        top: e.pageY
                    });
                }
            });

            function createColumnMenu() {
                $.cmenu = $('<div/>').appendTo('body');
                $.cmenu.menu({
                    onClick: function (item) {
                        if (item.iconCls == 'icon-ok') {
                            $('#dg_equipment').datagrid('hideColumn', item.name);
                            $.cmenu.menu('setIcon', {
                                target: item.target,
                                iconCls: 'icon-empty'
                            });
                        } else {
                            $('#dg_equipment').datagrid('showColumn', item.name);
                            $.cmenu.menu('setIcon', {
                                target: item.target,
                                iconCls: 'icon-ok'
                            });
                        }
                    }
                });
                var fields = $('#dg_equipment').datagrid('getColumnFields');
                for (var i = 0; i < fields.length; i++) {
                    var field = fields[i];
                    var col = $('#dg_equipment').datagrid('getColumnOption', field);
                    $.cmenu.menu('appendItem', {
                        text: col.title,
                        name: field,
                        iconCls: 'icon-ok'
                    });
                }
            }

            function showEditDialog(data) {
                var $ff = $('#ff_equipment');
                if (data) {
                    console.log(data);
                    $ff.form('load', data);
                    $ff.form({url: '<c:url value="/basic/equipment/put"/>'});
                } else {
                    $ff.form({url: '<c:url value="/basic/equipment/post"/>'});
                }
                $('#dlg_equipment_edit').dialog('open');
            }


            function submitForm() {
                $.messager.progress();	// display the progress bar
                $('#ff_equipment').form('submit', {
                    onSubmit: function (param) {
                        var isValid = $(this).form('enableValidation').form('validate');
                        if (!isValid) {
                            $.messager.progress('close');	// hide progress bar while the form is invalid
                        }
                        if(dept_id){
                            param.dept_id =  $('#details').data("data").id;
                        }
                        return isValid;	// return false will stop the form submission
                    },
                    success: function (data) {
                        $.messager.progress('close');	// hide progress bar while submit successfully
                        data = $.parseJSON(data);
                        if (data.flag) {
                            $.messager.alert('提示', '保存成功!');
                            closeEditDialog(true);
                        } else {
                            $.messager.alert('提示', data.msg || '保存失败,请检查网络连接或者权限!');
                        }
                    }
                });
            }

            function closeEditDialog(needRefresh) {
                if (needRefresh) {
                    $('#dg_equipment').datagrid('reload');
                }
                $('#ff_equipment').form('clear');
                $('#dlg_equipment_edit').dialog('close');
            }
            $.submitEquipmentForm = submitForm;
            $.closeEquipmentEditDialog = closeEditDialog;
            function remove(ids) {
                $.ajax({
                    url: '<c:url value="/basic/equipment/delete"/>',
                    data: {ids: ids},
                    type: 'post',
                    dataType: 'json'
                }).done(function (ret) {
                    if (ret && ret.flag) {
                        $.messager.alert('提示', '删除成功!');
                        $('#dg_equipment').datagrid('reload');
                    } else {
                        $.messager.alert('提示', ret, msg || '删除失败!');
                    }
                }).fail(function () {
                    $.messager.alert('提示', '删除失败!');
                });
            }
        });

    </script>
</div>
