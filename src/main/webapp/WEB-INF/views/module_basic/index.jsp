<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="../layout/common.jsp"></jsp:include>

</head>
<body class="easyui-layout">
<div data-options="region:'west',split:true" title="功能导航" style="width: 200px; overflow: hidden;">
    <div class="easyui-panel" style="padding:5px">
        <ul id="tree_menu"></ul>
    </div>
</div>
<div id="tt" class="easyui-tabs" data-options="region:'center'"></div>

<script>
    $(function () {
        var tree_data = [{
            "id": 11,
            "text": "单位管理",
            "url": "<c:url value="/basic/company/index"/>"
        }, {
            "id": 13,
            "text": "检测项目",
            "url": "<c:url value="/basic/inspectItem/index"/>"
        }, {
            "id": 14,
            "text": "行政区划",
            "url": "<c:url value="/basic/area/index"/>"
        }
            <sec:authorize access="hasAnyRole('SUPER','ADMIN')">
            , {
                "id": 15,
                "text": "检测机构",
                "url": "<c:url value="/basic/dept/index"/>"
            }
            </sec:authorize>
            <sec:authorize access="hasAnyRole('SUPER')">
            , {
                "id": 16,
                "text": "关于平台",
                "url": "<c:url value="/about/edit"/>"
            }
            </sec:authorize>
        ];
        var selectDept = "${selectDept}";
        initUI();
        function initUI() {
            $('#tree_menu').tree({
                data: tree_data,
                animate: true,
                onClick: function (node) {
                    if (node.text && node.url) {
                        openTab(node.text, node.url);
                    }
                },onLoadSuccess: function (node, data) {
                    if (selectDept=='true') {
                        var node = $('#tree_menu').tree('find', 15);
                        $(node.target).click();
                    }
                }
            });

        }

        function openTab(title, url) {
            var $tt = $('#tt');
            if (!$tt.tabs('exists', title)) {
                var $iframe = $('<iframe/>', {src: url, style: 'width:100%;height:100%', scrolling: 'no'});
                $tt.tabs('add', {
                    title: title,
                    content: $iframe,
                    closable: true,
                    fit: true,
                    plain: true
                });
            } else {
                $tt.tabs('select', title)
            }

        }
    });
</script>

</body>
</html>