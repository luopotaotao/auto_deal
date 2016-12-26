<%--
  Created by IntelliJ IDEA.
  User: tt
  Date: 2016/11/28
  Time: 11:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Resumable.js - Multiple simultaneous, stable and resumable uploads via the HTML5 File API</title>
    <meta charset="utf-8" />

    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/style.css"/>" />
</head>
<body>
<div id="frame">

    <h1>Resumable.js</h1>
    <p>It's a JavaScript library providing multiple simultaneous, stable and resumable uploads via the HTML5 File API.</p>

    <p>The library is designed to introduce fault-tolerance into the upload of large files through HTTP. This is done by splitting each files into small chunks; whenever the upload of a chunk fails, uploading is retried until the procedure completes. This allows uploads to automatically resume uploading after a network connection is lost either locally or to the server. Additionally, it allows for users to pause and resume uploads without loosing state.</p>

    <p>Resumable.js relies on the HTML5 File API and the ability to chunks files into smaller pieces. Currently, this means that support is limited to Firefox 4+ and Chrome 11+.</p>

    <hr/>

    <h3>Demo</h3>
    <script src="<c:url value="/resources/jquery-1.11.3.js"/>"></script>
    <script src="<c:url value="/resources/resumable.js"/>"></script>
    <script src="<c:url value="/resources/my_resumble.js"/>"></script>
    <script>

    </script>
    <div class="resumable-error">
        Your browser, unfortunately, is not supported by Resumable.js. The library requires support for <a href="http://www.w3.org/TR/FileAPI/">the HTML5 File API</a> along with <a href="http://www.w3.org/TR/FileAPI/#normalization-of-params">file slicing</a>.
    </div>

    <div class="resumable-drop" ondragenter="jQuery(this).addClass('resumable-dragover');" ondragend="jQuery(this).removeClass('resumable-dragover');" ondrop="jQuery(this).removeClass('resumable-dragover');">
        Drop video files here to upload or <a class="resumable-browse"><u>select from your computer</u></a>
    </div>

    <div class="resumable-progress">
        <table>
            <tr>
                <td width="100%"><div class="progress-container"><div class="progress-bar"></div></div></td>
                <td class="progress-text" nowrap="nowrap"></td>
                <td class="progress-pause" nowrap="nowrap">
                    <a href="#" class="progress-resume-link"><img src="<c:url value="/resources/resume.png"/>" title="Resume upload" /></a>
                    <a href="#" class="progress-pause-link"><img src="<c:url value="/resources/pause.png"/>" title="Pause upload" /></a>
                </td>
            </tr>
        </table>
    </div>

    <ul class="resumable-list"></ul>

    <script>
        $(function () {
           var resumble = $.getResumble({
               url:'<c:url value="/file/upload"/>',
               fileType:['png'],
               successHandler:function (uuid) {
                   alert('成功保存文件:'+uuid);
               },
               fileTypeErrorHandler:function () {
                   alert("文件类型错误...");
               }
           }) ;
        });
    </script>

</div>
</body>
</html>




