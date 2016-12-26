<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div style="width:97%">

    <div class="easyui-panel" style="width:30%">
        <input class="easyui-searchbox"
               data-options="prompt:'请输入检测方法名称',searcher:function(val,typ){$('#dg_method').datagrid('load',{typ:typ,name:encodeURIComponent(val)});}"
               style="width:100%">
    </div>

    <table id="dg_method" style="width:100%"></table>
    <div id="dlg_method_edit" class="easyui-dialog" style="width:100%;max-width:400px;padding:30px 60px;"
         data-options="
            title: '添加检测方法',
            closed: true,
            modal: true,
            draggable: false,
            iconCls: 'icon-add',
            buttons: [{
            text: '保存',
            iconCls: 'icon-ok',
            handler: $.submitMethodForm
            }, {
            text: '取消',
            iconCls: 'icon-cancel',
            handler: $.closeMethodEditDialog
            }]
        ">
        <form id="ff_method" class="easyui-form" method="post" data-options="novalidate:true" action="<c:url value="/basic/inspectMethod/post"/>">
            <div style="margin-bottom:20px;display: none">
                <input class="easyui-textbox" name="id" style="width:100%" data-options="label:'id:',required:true">
            </div>
            <div style="margin-bottom:20px">
                <input class="easyui-textbox" name="name" style="width:100%"
                       data-options="label:'方法名称:'">
            </div>
            <div style="margin-bottom:20px;display: none;">
                <input id="inspect_item_id" class="easyui-textbox" name="inspect_item_id" style="width:100%"
                       data-options="label:'检测项目id:',required:true">
            </div>
        </form>
    </div>
    <script type="text/javascript">
        $(function () {
            var inspect_item_id = '${inspect_item_id}';
            $('#dg_method').datagrid({
                url: '<c:url value="/basic/inspectMethod/${inspect_item_id}/query"/>',
                method: 'get',
//                title: '设备管理',
//                iconCls: 'icon-save',
//            width: 700,
                height: $('body').height()*0.65,
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
                        var rows = $('#dg_method').datagrid('getChecked');
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
                        var rows = $('#dg_method').datagrid('getChecked');
                        if (!rows || !rows.length) {
                            $.messager.alert('提示', '请选择要删除的行!');
                            return;
                        }
                        $.messager.confirm('提示', '是否确定删除这' + rows.length + '条数据?', function (r) {
                            if (r) {
                                remove($.map(rows, function (row) {
                                    return row.id;
                                }), function () {
                                    $('#dg_method').datagrid('reload');
                                });
                            }
                        });
                    }
                }],
                columns: [[
                    {field: 'ck', checkbox: true},
                    {field: 'id', title: 'ID', hidden:true},
                    {field: 'name', title: '方法名称'},
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
                            $('#dg_method').datagrid('hideColumn', item.name);
                            $.cmenu.menu('setIcon', {
                                target: item.target,
                                iconCls: 'icon-empty'
                            });
                        } else {
                            $('#dg_method').datagrid('showColumn', item.name);
                            $.cmenu.menu('setIcon', {
                                target: item.target,
                                iconCls: 'icon-ok'
                            });
                        }
                    }
                });
                var fields = $('#dg_method').datagrid('getColumnFields');
                for (var i = 0; i < fields.length; i++) {
                    var field = fields[i];
                    var col = $('#dg_method').datagrid('getColumnOption', field);
                    $.cmenu.menu('appendItem', {
                        text: col.title,
                        name: field,
                        iconCls: 'icon-ok'
                    });
                }
            }

//            $('#dlg_method_edit').dialog({
//                title: "添加人员",
//                closed: true,
//                modal: true,
//                draggable: false,
//                iconCls: 'icon-add',
//                buttons: [{
//                    text: '保存',
//                    iconCls: 'icon-ok',
//                    handler: submitForm
//                }, {
//                    text: '取消',
//                    iconCls: 'icon-cancel',
//                    handler: closeEditDialog
//                }]
//            })

            function showEditDialog(data) {
                var $ff = $('#ff_method');
                if (data) {
                    $ff.form('load', data);
                    $ff.form({url: '<c:url value="/basic/inspectMethod/put"/>'});
                } else {
                    $ff.form({url: '<c:url value="/basic/inspectMethod/post"/>'});
                }
                $('#inspect_item_id').textbox('setValue', inspect_item_id);
                $('#dlg_method_edit').dialog('open');
            }


            function submitForm() {
                $.messager.progress();	// display the progress bar
                $('#ff_method').form('submit', {
                    onSubmit: function () {
                        var isValid = $(this).form('validate');
                        if (!isValid) {
                            $.messager.progress('close');	// hide progress bar while the form is invalid
                        }
                        return isValid;	// return false will stop the form submission
                    },
                    success: function (data) {
                        $.messager.progress('close');	// hide progress bar while submit successfully
//                    {
//                        "success": true,
//                            "message": "Message sent successfully."
//                    }
                        data = $.parseJSON(data);
                        if (data.flag) {
                            $.messager.alert('提示', '保存成功!');
                            closeEditDialog(true);
                        } else {
                            $.messager.alert('提示', data.message || '保存失败,请检查网络连接或者权限!');
                        }
                    }
                });
            }

            function closeEditDialog(needRefresh) {
                if (needRefresh) {
                    $('#dg_method').datagrid('reload');
                }
                $('#ff_method').form('clear');
                $('#dlg_method_edit').dialog('close');
            }
            $.submitMethodForm = submitForm;
            $.closeMethodEditDialog = closeEditDialog;
            function remove(ids) {
                $.ajax({
                    url: '<c:url value="/basic/inspectMethod/delete"/>',
                    data: {ids: ids},
                    type: 'post',
                    dataType: 'json'
                }).done(function (ret) {
                    if (ret && ret.flag) {
                        $.messager.alert('提示', '删除成功!');
                        $('#dg_method').datagrid('reload');
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
