<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../include/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>角色列表</title>
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
                    <a class="mini-button" iconCls="icon-add" onclick="add()">增加</a>
                    <a class="mini-button" iconCls="icon-edit" onclick="edit()">编辑</a>
                    <a class="mini-button" iconCls="icon-remove" onclick="remove()">删除</a>
                    <a class="mini-button" iconCls="icon-addnew" onclick="authority()">授权</a>
                </td>
            </tr>
        </table>
    </div>
</div>
<div id="datagrid1" class="mini-datagrid" style="width:99%;height:80%;" multiSelect="true"
     url="/role/queryRole.action" idField="roleId" allowResize="true"
>
    <div property="columns">
        <div type="checkcolumn"></div>
        <div field="roleName" width="120" align="center" headerAlign="center">角色名称</div>
        <div field="description" width="200" align="center" headerAlign="center">描述</div>
    </div>
</div>

<script type="text/javascript">

    mini.parse();
    var grid = mini.get("datagrid1");
    grid.load();
    //查询
    function search() {
        grid.load({grid: grid});
    }
    function add() {
        mini.open({
            url: "<c:url value='/role/addRolePage.action'/>",
            title: "角色添加", width: 333, height: 222,
            onload: function () {
                var iframe = this.getIFrameEl();
                var data = {action: "new"};
                iframe.contentWindow.SetData(data);
            },
            ondestroy: function (action) {
                if (action == "save") {
                    grid.reload();
                    mini.showTips({
                        content: "<b>成功</b> <br/>角色添加成功",
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
                url: "<c:url value='/role/addRolePage.action'/>",
                title: "角色修改", width: 333, height: 222,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var data = {action: "edit", roleId: row.roleId};
                    iframe.contentWindow.SetData(data);
                },
                ondestroy: function (action) {
                    if (action == "save") {
                        grid.reload();
                        mini.showTips({
                            content: "<b>成功</b> <br/>角色修改成功",
                            state: "success",
                            x: "center",
                            y: "center",
                            timeout: 3000
                        });
                    }
                }
            });
        }
    }
    function remove() {
        var rows = grid.getSelecteds();
        if (rows.length > 0) {
            if (confirm("确定删除选中记录？")) {
                var ids = [];
                for (var i = 0, l = rows.length; i < l; i++) {
                    var r = rows[i];
                    ids.push(r.roleId);
                }
                var roleId = ids.join(',');
                grid.loading("操作中，请稍后......");
                $.ajax({
                    url: "/role/batchDeleteRole.action?roleId=" + roleId,
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
    function authority() {
        var row = grid.getSelected();
        if (row) {
            mini.open({
                url: "<c:url value='/role/addAuthority.action'/>",
                title: "授权", width: 365, height: 550,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var data = {roleId: row.roleId};
                    iframe.contentWindow.SetData(data);
                },
                ondestroy: function (action) {
                    if (action == "save") {
                        grid.reload();
                        mini.showTips({
                            content: "<b>成功</b> <br/>授权成功",
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
</script>
</body>
</html>
