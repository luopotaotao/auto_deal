<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>智能无线静荷载试验检测云平台</title>
    <jsp:include page="../layout/common.jsp"></jsp:include>
</head>

<body>
<div class="easyui-panel" style="width:30%">
    <input class="easyui-searchbox"
           data-options="prompt:'请输入公司名称',searcher:function(val){$('#dg').datagrid('load',{name:encodeURIComponent(val)});}"
           style="width:100%">
</div>

<table id="dg" style="width:95%"></table>
<div id="dlg_edit" style="width:100%;max-width:800px;padding:30px 60px;">
    <form id="ff" class="easyui-form" method="post" data-options="novalidate:true"
          action="<c:url value="/basic/dept/post"/>">
        <div style="margin-bottom:20px;display: none">
            <input class="easyui-textbox" name="id" style="width:45%" data-options="label:'记录编号:',required:true">

        </div>
        <div style="margin-bottom:20px">
            <input class="easyui-textbox" name="code" style="width:45%"
                   data-options="label:'机构编号:',required:true,labelAlign:'right'">
            <input class="easyui-textbox" name="name" style="width:45%"
                   data-options="label:'机构名称:',required:true,labelAlign:'right'">

        </div>
        <div style="margin-bottom:20px">

            <select class="easyui-combobox" data-options="editable:false,labelAlign:'right'" name="economy_typ"
                    label="经济性质:"
                    style="width:45%">
                <option value="1">经济性质1</option>
                <option value="2">经济性质2</option>
                <option value="3">经济性质3</option>
            </select>
            <select class="easyui-combobox" data-options="editable:false,labelAlign:'right'" name="register_type"
                    label="注册类型:"
                    style="width:45%">
                <option value="1">注册类型1</option>
                <option value="2">注册类型2</option>
                <option value="3">注册类型3</option>
            </select>
        </div>
        <div style="margin-bottom:20px">

            <input class="easyui-textbox" name="certificate_code" style="width:45%"
                   data-options="label:'电话号码:',labelAlign:'right'">
        </div>
        <div style="margin-bottom:20px;display: none">
            <input class="easyui-textbox" id="dept_logo" name="logo" style="width:100%"
                   data-options="label:'logo:',labelAlign:'right'">
        </div>
        <div id="img_div" style="margin-bottom:20px;margin-left:87px;overflow: hidden" class="resumable-browse">
            <img id="img" style="width: 300px;height: 59px"/>
        </div>
        <div class="resumable-progress">
            <table>
                <tr>
                    <td width="100%">
                        <div class="progress-container">
                            <div class="progress-bar"></div>
                        </div>
                    </td>
                    <td class="progress-text" nowrap="nowrap"></td>
                </tr>
            </table>
        </div>
        <div style="margin-bottom:20px">
            <input class="easyui-textbox" name="note" style="width:90%"
                   data-options="label:'备注信息:',labelAlign:'right',multiline:true,height:120">
        </div>
    </form>
</div>
<div id="details"></div>

<script type="text/javascript">
    $(function () {
        $('#dg').datagrid({
            url: '<c:url value="/basic/dept/query"/>',
            method: 'get',
            iconCls: 'icon-save',
//            width: 700,
            height: $('body').height(),
            fitColumns: true,
            singleSelect: false,
            pagination: true,
            pageSize: 10,
            <sec:authorize access="hasAnyRole('SUPER','ADMIN')">
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
                    var rows = $('#dg').datagrid('getChecked');
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
                    var rows = $('#dg').datagrid('getChecked');
                    if (!rows || !rows.length) {
                        $.messager.alert('提示', '请选择要删除的行!');
                        return;
                    }
                    $.messager.confirm('提示', '是否确定删除这' + rows.length + '条数据?', function (r) {
                        if (r) {
                            remove($.map(rows, function (row) {
                                return row.id;
                            }), function () {
                                $('#dg').datagrid('reload');
                            });
                        }
                    });
                }
            }],
            </sec:authorize>
            columns: [[
                {field: 'ck', checkbox: true},
                {field: 'id', title: 'ID', hidden: true},
                {field: 'code', title: '机构编号', width: 80},
                {field: 'name', title: '机构名称', width: 80, align: 'right'},
                {
                    field: 'economy_typ', title: '经济性质', width: 80, align: 'right',
                    formatter: function (val, row) {
                        return {1: '性质1', 2: '性质2', 3: '性质3'}[val];
                    }
                },
                {field: 'certificate_code', title: '资质证书编号', width: 80, align: 'right'},
                {
                    field: 'register_type', title: '注册类型', width: 80, align: 'right',
                    formatter: function (val, row) {
                        return {1: '建设单位', 2: '施工单位', 3: '监理单位'}[val];
                    }
                },
                {
                    field: 'logo', title: '公司Logo', width: 80, align: 'right',
                    formatter: function (val, row) {
                        return '<img style="width:300px;height:59px;" src="<c:url value="/resources/upload/"/>' + (val || 'blank.png') + '">';
                    }
                },
                {field: 'note', title: '备注'},
                {
                    field: 'null', title: '操作', width: 110, align: 'right',
                    formatter: function (val, row) {
                        var str_arr = [
                            '<button onclick="javascript:manageTUsers(',
                            row['id'],
                            ');">人员</button>&nbsp;',
                            '<button onclick="javascript:manageEquipments(',
                            row['id'],
                            ');">设备</button>&nbsp;',
                            '<button onclick="javascript:top.switchDept(',
                            row['id'],
                            ');">查看数据</button>'
                        ];
                        return str_arr.join('');
                    }
                }
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
                        $('#dg').datagrid('hideColumn', item.name);
                        $.cmenu.menu('setIcon', {
                            target: item.target,
                            iconCls: 'icon-empty'
                        });
                    } else {
                        $('#dg').datagrid('showColumn', item.name);
                        $.cmenu.menu('setIcon', {
                            target: item.target,
                            iconCls: 'icon-ok'
                        });
                    }
                }
            });
            var fields = $('#dg').datagrid('getColumnFields');
            for (var i = 0; i < fields.length; i++) {
                var field = fields[i];
                var col = $('#dg').datagrid('getColumnOption', field);
                $.cmenu.menu('appendItem', {
                    text: col.title,
                    name: field,
                    iconCls: 'icon-ok'
                });
            }
        }

        $('#dlg_edit').dialog({
            title: "编辑",
            closed: true,
            modal: true,
            draggable: false,
            iconCls: 'icon-add',
            buttons: [{
                text: '保存',
                iconCls: 'icon-ok',
                handler: submitForm
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: closeEditDialog
            }],
            onClose: function () {
                r.cancel();
                $('#ff').form('clear');
            },
            onBeforeOpen: function () {
                initResumable();
                return true;
            }
        });

        function showEditDialog(data) {
            var $ff = $('#ff'),
                    $img = $('#img'),
                    name = 'blank.png';
            if (data) {
                $ff.form('load', data);
                $ff.form({url: 'put'});
                if (data.logo) {
                    name = data.logo;
                }
            } else {
                $ff.form({url: 'post'});
            }
            $img.attr('src', '<c:url value="/resources/upload/"/>' + name);

            $('#dlg_edit').dialog('open');
        }

        function submitForm() {
            $.messager.progress();
            $('#ff').form('submit', {
                onSubmit: function () {
                    var isValid = $(this).form('validate');
                    if (!isValid) {
                        $.messager.progress('close');
                    }
                    return isValid;
                },
                success: function (data) {
                    $.messager.progress('close');
                    data = $.parseJSON(data);
                    if (data.id) {
//                            $.messager.alert('提示', '保存成功!');
                        closeEditDialog(true);
                        confirmAddUser(data.id);
                    } else {
                        $.messager.alert('提示', data.message || '保存失败,请检查网络连接或者权限!');
                    }
                }
            });
        }

        function confirmAddUser(dept_id) {
            $.messager.confirm('请确认', '保存成功!是否添加名为' + dept_id + '的管理员账户?', function (r) {
                if (r) {
                    $.ajax({
                        url: '<c:url value="/basic/user/createDefault"/>',
                        type: 'post',
                        dataType: 'json',
                        data: {
                            dept_id: dept_id
                        }
                    }).done(function (ret) {
                        $.messager.alert('系统提示', ret.msg || '添加成功!');
                    }).fail(function () {
                        $.messager.alert('系统提示', '添加失败,请在人员管理页面手动添加!');
                    })
                }
            });
        }

        function closeEditDialog(needRefresh) {
            if (needRefresh) {
                $('#dg').datagrid('reload');
            }
            $('#ff').form('clear');
            $('#dlg_edit').dialog('close');
        }

        function remove(ids) {
            $.ajax({
                url: '<c:url value="/basic/dept/delete"/>',
                data: {ids: ids},
                type: 'post',
                dataType: 'json'
            }).done(function (ret) {
                if (ret && ret.flag) {
                    $.messager.alert('提示', '删除成功!');
                    $('#dg').datagrid('reload');
                } else {
                    $.messager.alert('提示', ret, msg || '删除失败!');
                }
            }).fail(function () {
                $.messager.alert('提示', '删除失败!');
            });
        }
    });
    function openDialog(title, href, data) {
        //由于dialog通过url方式多次加载,会生成重复的dom节点,导致form表单重复提交,需要将重复的节点销毁
        $('.destroy').panel('destroy');
        if (data) {
            $('#details').data("data", data);
        }

        $('#details').dialog({
            title: title,
            width: $('body').width() * 0.8,
            height: $(document).height() * 0.8,
            closed: false,
            cache: false,
            href: href,
            modal: true
        });
    }


    function manageTUsers(id) {
        var href = '<c:url value="/basic/inspector/index"/>';
        openDialog('账号管理', href, {id: id});
    }
    function manageEquipments(id) {
        var href = '<c:url value="/basic/equipment/index"/>';
        openDialog('设备管理', href, {id: id});
        $('<div/>').html()
    }
    var r = null;
    function initResumable() {
        r = $.getResumble({
            url: '<c:url value="/file/upload"/>',
            fileType: ['png'],
            successHandler: function (ret) {
                alert('成功保存文件:' + ret.uuid);
                $('#dept_logo').textbox('setValue', ret.uuid);
                $('#img').attr('src', '<c:url value="/resources/upload/"/>' + ret.uuid);
            },
            fileTypeErrorHandler: function () {
                alert("文件类型错误...");
            }
        });
    }
    $('#btn_save').on('click', function () {
        $.ajax({
            url: '../fileController/save',
            type: 'post',
            dataType: 'json',
            data: {fileName: $('#fileName').val()}
        }).done(function (ret) {
            if (ret.flag) {
                console.log('保存成功');
            } else {
                console.log("保存失败");
            }
        }).fail(function () {
            console.log("保存失败");
        });
    })

</script>
<script type="text/javascript" src="<c:url value="/resources/jslib/resumable.js"/>" type="text/javascript"
        charset="utf-8"></script>
<script type="text/javascript" src="<c:url value="/resources/jslib/my_resumble.js"/>" type="text/javascript"
        charset="utf-8"></script>

</body>

</html>