<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../include/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>公告列表</title>
    <style type="text/css">
        html, body {
            margin: 0;
            padding: 0;
            border: 0;
            width: 100%;
            height: 100%;
            overflow: hidden;
        }

    </style>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <link href="/css/demo.css" rel="stylesheet" type="text/css"/>
    <script src="/scripts/boot.js" type="text/javascript"></script>
</head>
<body>

<div style="width:99%;">
        <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
            <table style="width:100%;">
                <tr>
                    <td style="width:100%;">
                        <a class="mini-button" iconCls="icon-add" onclick="add()">添加公告</a>
                        <a class="mini-button" iconCls="icon-add" onclick="remove()">批量删除</a>
                    </td>
                    <td style="white-space:nowrap;">
                        <input id="title" name="title" emptyText="请输入公告标题" class="mini-textbox" style="width:150px;" onenter="search"/>
                        <a class="mini-button" onclick="search()">查询</a>
                    </td>
                </tr>
            </table>
        </div>
</div>
<div id="datagrid1" class="mini-datagrid" style="width:99%;height:90%;" sizeList="[20,30,50,100]" pageSize="20" multiSelect="true"
     url="/notice/queryNotice.action" idField="id" allowResize="true" allowCellWrap="true" allowCellSelect="true">
    <div property="columns">
        <div type="checkcolumn"></div>
        <div field="id" width="100" headerAlign="center" align="center">ID</div>
        <div field="title" width="200" headerAlign="center" align="center">标题</div>
        <div field="content" width="250" headerAlign="center" align="center">内容</div>
		<div field="createTime" width="120" align="center" headerAlign="center" dateFormat="yyyy-MM-dd HH:mm:ss">创建时间</div>
        <div field="user.userName" width="100" headerAlign="center" align="center">发布人</div>
        <div field="noticeStatusEnum.text" width="80" headerAlign="center" align="center">状态</div>
    </div>
</div>
<script type="text/javascript">

    mini.parse();
    var grid = mini.get("datagrid1");
    grid.load();
    //查询
    function search() {
        var key = mini.get("title").getValue();
        grid.load({ title: key });
    }
    function clean() {
        form.reset();
    }
    function add() {
        mini.open({
            url: "<c:url value='/notice/noticeAddPage.action'/>",
            title: "添加公告", width: 450, height: 300,
            onload: function () {
                var iframe = this.getIFrameEl();
            },
            ondestroy: function (action) {
                if (action == "save") {
                    grid.reload();
                    mini.showTips({
                        content: "<b>成功</b> <br/>添加成功",
                        state: "success",
                        x: "center",
                        y: "center",
                        timeout: 3000
                    });
                }
            }
        });
    }

    function remove() {
        var rows = grid.getSelecteds();
        if (rows.length > 0) {
            if (confirm("确定删除选中记录？")) {
                var ids = [];
                for (var i = 0, l = rows.length; i < l; i++) {
                    var r = rows[i];
                    ids.push(r.id);
                }
                var ids = ids.join(',');
                grid.loading("操作中，请稍后......");
                $.ajax({
                    url: "/notice/batchDeleteNotice.action?ids="+ids,
                    success: function () {
                        grid.reload();
                        mini.showTips({
                            content: "<b>成功</b> <br/>删除成功",
                            state: "success",
                            x: "center",
                            y: "center",
                            timeout: 3000
                        });
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        grid.reload();
                        mini.showMessageBox({
                            showModal: false,
                            width: 250,
                            title: "提示",
                            iconCls: "mini-messagebox-warning",
                            message: jqXHR.responseText,
                            timeout: 3000,
                            x: "right",
                            y: "bottom"
                        });
                    }
                });
            }
        } else {
            alert("请选中一条记录");
        }
    }
</script>
</body>
</html>
