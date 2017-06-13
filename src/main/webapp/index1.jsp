<%--
  Created by IntelliJ IDEA.
  User: zhans-pc
  Date: 2017/6/8
  Time: PM6:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>批量上传白名单</title>
    <link href="/static/css/jquery-ui.css" rel="stylesheet" media="screen">
    <link href="/static/css/jquery-ui.theme.css" rel="stylesheet" media="screen">
    <script type="text/javascript" src="/static/js/jquery.min.js" charset="UTF-8"></script>
    <script type="text/javascript" src="/static/js/jquery-ui.js" charset="UTF-8"></script>
    <script type="text/javascript" src="/static/js/jquery-form.js" charset="UTF-8"></script>
</head>

<script type="application/javascript">

    $(function () {
        $("#dialog").dialog({
            autoOpen: false,
            modal: true,
            height: 300,
            width: 600,
            buttons: {
                "关闭": function () {
                    $(this).dialog('close');
                    window.location.reload();
                }
            }
        });

        $("#upload").click(function () {
            var file = $("#file").val();
            if (file.length == 0) {
                $("#comment").html("请选择要上传的文件")
                $("#dialog").dialog("open")
                return false
            }
            var options={
                dataType:  'json',
                success:function(data)
                {
                    if(data.msg){
                        $("#comment").html("上传成功")
                        $("#dialog").dialog("open")

                    }else {
                        console.log(data.length)
                        var show="上传失败：<br><br>"
                        for(var i=0;i<data.length;i++){
                            show+="第"+data[i].row+"行的第"+data[i].column+"列"+data[i].msg+"<br> <br>"
                        }
                        $("#comment").html(show)
                        $("#dialog").dialog("open")
                    }
                }
            }
            $("#form1").ajaxSubmit(options)
        })


    })
</script>
<body>
<form id="form1" action="/upload" method="post" enctype="multipart/form-data">
    <h2>文件上传</h2>
    文件:<input id="file" type="file" name="uploadFile"/><br/><br/>
    <input id="upload" type="button" value="上传"/>
</form>

<div id="dialog" title="结果通知">
    <p id="comment"></p>
</div>
<button id="add">add</button>

</body>
</html>
