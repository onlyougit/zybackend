<%@ page language="java" contentType="text/html;charset=utf-8" %>
<%@ include file="../../include/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>角色修改</title>
    <script type="text/javascript" src="<c:url value='/scripts/boot.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/Portal.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/js/jquery.form.js'/>"></script>
    <link rel="stylesheet" href="<c:url value='/scripts/portal.css'/>"/>

    <style type="text/css">
        body {
            margin: 0;
            padding: 0;
            border: 0;
            width: 100%;
            height: 100%;
            overflow: hidden;
        }
    </style>
</head>
<body>
<input type="hidden" name="roleId" id="roleId" class="mini-hidden">
<ul id="pmtree" class="mini-tree" url="" style="width:350px;height:480px;padding-left: 25px;"
    showTreeIcon="true" textField="text" idField="id" parentField="pid" resultAsTree="false"
    allowSelect="false" enableHotTrack="false" expandOnLoad="true"
    showCheckBox="true" checkRecursive="false" autoCheckParent="true">
</ul>
<div style="text-align:center;padding:10px;">
    <a class="mini-button" onclick="checkAll" style="width:60px;margin-right:20px;">全选</a>
    <a class="mini-button" onclick="uncheckAll" style="width:60px;margin-right:20px;">全不选</a>
    <a class="mini-button" onclick="onOk" style="width:60px;margin-right:20px;">确定</a>
    <a class="mini-button" onclick="onCancel" style="width:60px;">取消</a>
</div>
<script type="text/javascript">
    mini.parse();
    var tree = mini.get("pmtree");
    //标准方法接口定义
    function SetData(data) {
        var roleId = data.roleId;
        mini.get("roleId").setValue(roleId);
        tree.load("/role/getMenuPermissionTree.action?roleId="+roleId);
    }
    function SaveData() {
        var roleId = mini.get("roleId").value;
        var nodes = tree.getCheckedNodes();
        var permissionIds = new Array();
        for(var i = 0; i < nodes.length; i++){
            var hasParent = tree.getParentNode(nodes[i])._id;
            //if(hasParent!=-1){
                permissionIds.push(nodes[i].id);
            //}
        }
        var json = JSON.stringify(permissionIds);
        var msgid = mini.loading("数据处理中，请稍后......", "处理数据");
        $.ajax({
            url: "<c:url value='/role/saveAuthority.action'/>",
            type: "post",
            data: {roleId:roleId,permissionIds:json},
            cache: false,
            success: function (text) {
                var o = mini.decode(text);
                mini.hideMessageBox(msgid);
                CloseWindow("save");
            }
        });
    }

    function GetData() {
        var o = form.getData();
        return o;
    }
    function CloseWindow(action) {
        if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
        else window.close();
    }
    function onOk(e) {
        SaveData();
    }
    function onCancel(e) {
        CloseWindow("close");
    }

    function checkAll(){
        var nodes = tree.getAllChildNodes(tree.getRootNode());
        tree.checkNodes(nodes);
    }
    function uncheckAll(){
        var nodes = tree.getAllChildNodes(tree.getRootNode());
        tree.uncheckNodes(nodes);
    }
</script>

</body>
</html>