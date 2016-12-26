<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<div id="dialog_content" style="width:100%">

    <div class="easyui-panel" style="width:30%">
        <input class="easyui-searchbox"
               data-options="prompt:'请输入姓名',searcher:function(val,typ){$('#dg_user').datagrid('load',{typ:typ,name:encodeURIComponent(val)});}"
               style="width:100%">
    </div>

    <table id="dg_user" style="width:100%"></table>
    <div id="dlg_user_edit" class="easyui-dialog destroy" style="width:100%;max-width:400px;padding:30px 60px;"
         data-options="
            title: '编辑',
            closed: true,
            modal: true,
            draggable: false,
            //iconCls: 'icon-add',
            buttons: [{
            text: '保存',
            iconCls: 'icon-ok',
            handler: $.submitUserForm
            }, {
            text: '取消',
            iconCls: 'icon-cancel',
            handler: $.closeUserEditDialog
            }]
        ">
        <form id="ff_user" class="easyui-form" method="post" data-options="novalidate:false"
              action="<c:url value="/basic/user/post"/>">
            <div style="margin-bottom:20px;display: none">
                <input class="easyui-textbox" name="id" style="width:100%" data-options="label:'用户编号:'">
            </div>
            <div style="margin-bottom:20px">
                <input class="easyui-textbox" name="username" style="width:100%"
                       data-options="label:'账号:',required:true">
            </div>
            <div style="margin-bottom:20px">
                <input class="easyui-textbox" name="realName" style="width:100%"
                       data-options="label:'姓名:',required:true">
            </div>
            <div style="margin-bottom:20px">
                <input class="easyui-textbox" name="email" style="width:100%"
                       data-options="label:'Email:',required:true">
            </div>
            <div style="margin-bottom:20px">
                <input class="easyui-textbox" name="password" style="width:100%"
                       data-options="label:'密码:',required:true">
            </div>

            <div style="margin-bottom:20px">
                <select id="user_authority_id" class="easyui-combobox" data-options="editable:false,multiple:false"
                        name="authority.id"
                        label="角色:" style="width:100%">
                    <sec:authorize access="hasRole('SUPER')">
                        <option value="1">超级管理员</option>
                        <option value="2">管理员</option>
                    </sec:authorize>
                    <option value="3">普通用户</option>
                </select>
            </div>
        </form>
    </div>
    <script type="text/javascript">
        $(function () {
//            alert(JSON.stringify($('#details').data("data")));
            var dept_id = $('#details').data("data").id;
            $('#dg_user').datagrid({
                url: '<c:url value="/basic/user/queryAll/"/>' + dept_id+'?123',
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
                        var rows = $('#dg_user').datagrid('getChecked');
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
                        var rows = $('#dg_user').datagrid('getChecked');
                        if (!rows || !rows.length) {
                            $.messager.alert('提示', '请选择要删除的行!');
                            return;
                        }
                        $.messager.confirm('提示', '是否确定删除这' + rows.length + '条数据?', function (r) {
                            if (r) {
                                remove($.map(rows, function (row) {
                                    return row.id;
                                }), function () {
                                    $('#dg_user').datagrid('reload');
                                });
                            }
                        });
                    }
                }],
                columns: [[
                    {field: 'ck', checkbox: true},
                    {field: 'id', title: 'ID', hidden: true},
                    {field: 'username', title: '账号'},
                    {field: 'realName', title: '姓名'},
                    {field: 'email', title: '邮箱'},
                    {
                        field: 'authority', title: '角色', formatter: function (val, row) {
                        return {
                                    'ROLE_SUPER': '超级管理员',
                                    'ROLE_ADMIN': '管理员',
                                    'ROLE_CUSTOM': '普通用户'
                                }[val.authority] || '普通用户';
                    }
                    },
                    {field: 'note', title: '备注'}
                ]]
            });


            function showEditDialog(data) {
                var $ff = $('#ff_user');
                if (data) {
                    $ff.form('load', data);
                    $ff.form({url: '<c:url value="/basic/user/put"/>'});
                } else {
                    $ff.form({url: '<c:url value="/basic/user/post"/>'});
                }
                if (data && data.authority) {
                    $('#user_authority_id').combobox('setValue', data.authority.id);
                }

                $('#dlg_user_edit').dialog('open');
            }


            function submitForm() {
                $.messager.progress();
                $('#ff_user').form('submit', {
                    onSubmit: function (param) {
                        var isValid = $(this).form('enableValidation').form('validate');
                        if (!isValid) {
                            $.messager.progress('close');
                        }
                        console.log( $('#details').data("data").id);
                        if(dept_id){
                            param.dept_id =  $('#details').data("data").id;
                        }
                        return isValid;
                    },
                    success: function (data) {
                        debugger;
                        $.messager.progress('close');
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
                    $('#dg_user').datagrid('reload');
                }
                $('#ff_user').form('reset');
                $('#dlg_user_edit').dialog('close');
            }

            $.submitUserForm = submitForm;
            $.closeUserEditDialog = closeEditDialog;
            function remove(ids) {
                $.ajax({
                    url: '<c:url value="/basic/user/delete"/>',
                    data: {ids: ids},
                    type: 'post',
                    dataType: 'json'
                }).done(function (ret) {
                    if (ret && ret.flag) {
                        $.messager.alert('提示', '删除成功!');
                        $('#dg_user').datagrid('reload');
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
