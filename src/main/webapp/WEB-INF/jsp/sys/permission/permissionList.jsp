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
<div class="mini-splitter" style="width:100%;height:100%;">
    <div size="20%" showCollapseButton="true" style="padding:0px;">
        <ul id="tree1" class="mini-tree" url="/permission/getMenuTree.action" style="width:200px;padding:5px;"
            showTreeIcon="true" textField="menuName" idField="menuId" expandOnLoad="true" onnodeselect="search">
        </ul>
    </div>
    <div showCollapseButton="true">
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
             url="/permission/queryPermission.action" idField="permissionId" allowResize="true"
        >
            <div property="columns">
                <div type="checkcolumn"></div>
                <div field="permissionName" width="100" headerAlign="center" align="center">权限名称</div>
                <div field="permissionUrl" width="120" headerAlign="center" align="center">资源</div>
                <div field="menuName" width="80" headerAlign="center" align="center">所属菜单</div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">

    mini.parse();
    var grid = mini.get("datagrid1");
    grid.load();
    //查询
    function search(e) {
        var node = e.node;
        var isLeaf = e.isLeaf;
        if (isLeaf) {
            var menuId = node.menuId;
            grid.load({menuId: menuId});
        }else{
            grid.load();
        }
    }

    function clean() {
        form.reset();
    }
    function add() {
        var tree = mini.get("tree1");
        var selectNode = tree.getSelectedNode();
        if(selectNode==undefined){
            mini.showTips({
                content: "<b>警告</b> <br/>请选中左边菜单",
                state: "warning",
                x: "center",
                y: "center",
                timeout: 3000
            });
        }else{
            mini.open({
                url: "<c:url value='/permission/addPermissionPage.action'/>",
                title: "权限添加", width: 358, height: 200,
                onload: function () {
                    var menuId = selectNode.menuId;
                    var iframe = this.getIFrameEl();
                    var data = {action: "new",menuId:menuId};
                    iframe.contentWindow.SetData(data);
                },
                ondestroy: function (action) {
                    if (action == "save") {
                        grid.reload();
                        mini.showTips({
                            content: "<b>成功</b> <br/>权限添加成功",
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
    function edit(id) {
        var row = grid.getSelected();
        if (row) {
            mini.open({
                url: "<c:url value='/permission/addPermissionPage.action'/>",
                title: "权限修改", width: 358, height: 200,
                onload: function () {
                    var iframe = this.getIFrameEl();
                    var data = {action: "edit", permissionId: row.permissionId};
                    iframe.contentWindow.SetData(data);
                },
                ondestroy: function (action) {
                    if (action == "save") {
                        grid.reload();
                        mini.showTips({
                            content: "<b>成功</b> <br/>权限修改成功",
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
                    ids.push(r.permissionId);
                }
                var permissionId = ids.join(',');
                grid.loading("操作中，请稍后......");
                $.ajax({
                    url: "/permission/batchDeletePermission.action?permissionId=" + permissionId,
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
