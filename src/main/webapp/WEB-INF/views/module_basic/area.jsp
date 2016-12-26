<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>智能无线静荷载试验检测云平台</title>
    <jsp:include page="../layout/common.jsp"></jsp:include>

</head>
<body style="width:100%">

<div class="easyui-panel" style="padding:5px;width: 100%;height: 500px">
    <ul id="tree_area"></ul>
</div>
<div id="mm" class="easyui-menu" style="width:120px;">
    <div class="menu_item" onclick="add('append')" data-options="iconCls:'icon-add'">添加</div>
    <%--<div class="menu_item" onclick="add('insert')" data-options="iconCls:'icon-insert'">插入</div>--%>
    <div class="menu_item_remove" onclick="remove()" data-options="iconCls:'icon-remove'">删除</div>
    <div class="menu-sep"></div>
    <div class="menu_item" onclick="operate('expand')">展开</div>
    <div class="menu_item" onclick="operate('collapse')">收起</div>
</div>
<script type="text/javascript">
    initUI();
    function initUI() {

        $('#tree_area').tree({
            url: '<c:url value="/basic/area/root"/>',
            method: 'get',
            animate: true,
            dnd: false,
            onDblClick: function (node) {
                $.messager.prompt('请输入', '请输入节点名称:', function (txt) {
                    if (txt) {
//                        $(this).tree('beginEdit', node.target);
                        $.ajax({
                            url: '<c:url value="/basic/area/put"/>',
                            type: 'post',
                            dataType: 'json',
                            data: {
                                id: node.id,
                                text: txt,
                                level: node.level
                            }
                        }).done(function (ret) {
                            if (ret.flag) {
                                $('#tree_area').tree('update', {
                                    target: node.target,
                                    text: txt
                                });
                            } else {
                                $.messager.alert('提示', '保存数据失败,请重新尝试或联系管理员!');
                            }
                        }).fail(function () {
                            $.messager.alert('提示', '保存数据失败,请重新尝试或联系管理员!');
                        });
                    }
                })

            },
            onContextMenu: function (e, node) {
                e.preventDefault();
                $(this).tree('select', node.target);

                var $mm = $('#mm');
                //如果为城市节点,为叶子节点,只能删除,禁用其他菜单,否则启用全部菜单
                var operate = node.level<2?'enableItem':'disableItem';
                $mm.find('.menu_item').each(function (i,div) {
                    $mm.menu(operate, div);
                });
                //全国为根节点,不可删除
                $mm.menu(node.level!=0?'enableItem':'disableItem',$mm.find('.menu_item_remove').first())
                $mm.menu('show', {
                    left: e.pageX,
                    top: e.pageY
                });
            }
        });
    }

    function add(operation) {
        $.messager.prompt('请输入', '请输入节点名称:', function (txt) {
            if (txt) {
                var $tree = $('#tree_area');
                var node = $tree.tree('getSelected');
                $.ajax({
                    url: '<c:url value="/basic/area/post"/>',
                    type: 'post',
                    dataType: 'json',
                    data: {'parent.id': node ? node.id : null, text: txt}
                }).done(function (ret) {
                    if (ret.id) {
                        var child = {
                            data: [ret]
                        };
                        var type = (operation == 'insert') ? 'before' : 'parent';
                        child[type] = (node ? node.target : null);
                        $tree.tree(operation, child);
                    } else {
                        $.messager.alert('提示', '保存数据失败,请重新尝试或联系管理员!');
                    }
                }).fail(function () {
                    $.messager.alert('提示', '保存数据失败,请重新尝试或联系管理员!');
                });
            }
        })

    }

    /**
     *
     * @param action 可选[remove,collapse,expand]之一
     */
    function operate(action) {
        var node = $('#tree_area').tree('getSelected');
        $('#tree_area').tree(action, node.target);
    }

    function remove() {
        var node = $('#tree_area').tree('getSelected');

        $.ajax({
            url: '<c:url value="/basic/area/delete/"/>' + node.id,
            type: 'post',
            dateType: 'json'
        }).done(function () {
            $('#tree_area').tree('remove', node.target);
        }).fail(function () {
            $.messager.alert('提示', '删除失败!')
        })
    }
</script>
</body>
</html>