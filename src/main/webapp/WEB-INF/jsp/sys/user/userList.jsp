<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../include/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>用户列表</title>
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

<div style="padding-bottom:5px;padding-left: 0px;">

    <div id="form1">

        <table border="0" cellpadding="1" cellspacing="2">
            <tr>
                <td>
                                                用户账号：
                </td>
                <td>
                    <input id="userName" emptyText="请输入登录账号" name="userName" class="mini-textbox"/>
                </td>
                <td>用户姓名：</td>
                <td>
                    <input id="realName" emptyText="请输入真名" name="realName" class="mini-textbox"/>
                </td>
                <td colspan="2" align="center"><a class="mini-button" iconCls="icon-search" onClick="search()">搜索</a>&nbsp;<a
                        class="mini-button" iconCls="icon-reload" onClick="clean()">重置</a>
                </td>
            </tr>
        </table>
    </div>
</div>
<div style="width:99%;">
    <div class="mini-toolbar" style="border-bottom:0;padding:0px;">
        <table style="width:100%;">
            <tr>
                <td style="width:100%;">
                    <a class="mini-button" iconCls="icon-add" onclick="add()">增加</a>
                    <a class="mini-button" iconCls="icon-edit" onclick="edit()">编辑</a>
                    <a class="mini-button" iconCls="icon-remove" onclick="remove()">删除</a>
                </td>
            </tr>
        </table>
    </div>
</div>
<div id="datagrid1" class="mini-datagrid" style="width:99%;height:80%;" multiSelect="true"
     url="/user/queryUser.action" idField="userId" allowResize="true"
>
    <div property="columns">
        <div type="checkcolumn"></div>
        <div field="userName" width="120" align="center" headerAlign="center">用户账号</div>
        <div field="realName" width="100" align="center" headerAlign="center">用户姓名</div>
        <div field="userEmail" width="100" align="center" headerAlign="center">邮箱</div>
        <div field="roleName" width="120" align="center" headerAlign="center">所属角色</div>
        <div field="loginCount" width="120" align="center" headerAlign="center">登录次数</div>
        <div field="createTime" width="120" align="center" headerAlign="center" dateFormat="yyyy-MM-dd HH:mm:ss"
             allowSort="true">
            创建时间
        </div>
        <div field="status.text" width="100" align="center" headerAlign="center">状态</div>
    </div>
</div>

<script type="text/javascript">

    mini.parse();
    var grid = mini.get("datagrid1");
    var form = new mini.Form("#form1"); //获取表单对象
    grid.load();
    //查询
    function search() {
        var data = form.getData();      //获取表单多个控件的数据
        var json = mini.encode(data);   //序列化成JSON
        grid.load({json: json});
    }

    function clean() {
        form.reset();
    }
    function add() {
        mini.open({
            url: "<c:url value='/user/addUserPage.action'/>",
            title: "用户添加", width: 558, height: 256,
            onload: function () {
                var iframe = this.getIFrameEl();
                var data = {action: "new"};
                iframe.contentWindow.SetData(data);
            },
            ondestroy: function (action) {
                if (action == "save") {
                    grid.reload();
                    mini.showTips({
                        content: "<b>成功</b> <br/>用户添加成功",
                        state: "success",
                        x: "center",
                        y: "center",
                        timeout: 3000
                    });
                }
            }
        });
    }
    function edit(id) {
        var row = grid.getSelected();
        if (row) {
            mini.open({
                url: "<c:url value='/user/addUserPage.action'/>",
                title: "用户修改", width: 558, height: 256,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var data = {action: "edit", userId: row.userId};
                    iframe.contentWindow.SetData(data);
                },
                ondestroy: function (action) {
                    if (action == "save") {
                        grid.reload();
                        mini.showTips({
                            content: "<b>成功</b> <br/>用户修改成功",
                            state: "success",
                            x: "center",
                            y: "center",
                            timeout: 3000
                        });
                    }
                }
            });
        } else {
            alert("请选中一条记录");
        }
    }
    function remove() {
        var rows = grid.getSelecteds();
        if (rows.length > 0) {
            if (confirm("确定删除选中记录？")) {
                var ids = [];
                for (var i = 0, l = rows.length; i < l; i++) {
                    var r = rows[i];
                    ids.push(r.userId);
                }
                var userId = ids.join(',');
                grid.loading("操作中，请稍后......");
                $.ajax({
                    url: "/user/batchDeleteUser.action?userId=" + userId,
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
