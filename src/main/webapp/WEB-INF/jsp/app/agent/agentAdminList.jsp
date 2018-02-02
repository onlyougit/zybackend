<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../include/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>总部代理列表</title>
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
    <div id="form1">
        <table style="width:100%;">
            <tr>
	            <td style="width:100%;">
	            </td>
	            <td style="white-space:nowrap;">
	                <input name="business.businessName" class="mini-textbox" emptyText="请输入营业部名称" style="width:150px;" onenter="search"/>
	            </td>
            </tr>
        </table>
        </div>
    </div>
</div>
<div id="datagrid1" class="mini-datagrid" style="width:99%;height:80%;" sizeList="[20,30,50,100]" pageSize="20"
     url="/agent/queryHeadAgent.action" idField="id" allowResize="true" allowCellWrap="true" 
     allowCellSelect="true">
    <div property="columns">
        <div type="indexcolumn"></div>
        <div field="business.businessName" width="120" headerAlign="center" align="center">营业部名称</div>
        <div field="agentName" width="120" headerAlign="center" align="center">代理商名称</div>
		<div field="agentExtensionLink" width="150" headerAlign="center" align="center">
		代理推广链接</div>
        <div field="agentPhone" width="120" headerAlign="center" align="center">代理电话</div>
		<div field="userName" width="100" headerAlign="center" align="center">登录账号</div>
        <div field="templateAccount" width="100" headerAlign="center" align="center">模板账户</div>
        <div field="serviceChargeStandard" width="100" headerAlign="center" align="center">手续费标准</div>
        <div field="serviceChargeCost" width="100" headerAlign="center" align="center">手续费成本</div>
		<div field="bondStandard" width="100" headerAlign="center" align="center">保证金标准</div>
		<div field="agentStatusEnum.text" width="80" headerAlign="center" align="center">状态</div>
		<div name="action" width="250" headerAlign="center" align="center"
				renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
    </div>
</div>
<div id="addWindow" class="mini-window" title="添加模板账号"
		style="width: 300px;" showModal="true" allowResize="true"
		allowDrag="true">
		<div id="addForm">
		<input type="hidden" id="agentId" class="mini-hidden">
			<table border="0" cellpadding="1" cellspacing="2">
				<tr>
					<td>模板账号：</td>
					<td><input style="width: 200px;" id="templateAccount"
						required="true" emptyText="请输入模板账号" class="mini-textbox" /></td>
				</tr>
				<tr>
					<td colspan="4" align="center">
						<a class="mini-button" iconCls="icon-save" onClick="addSave()">保存</a> 
						<a class="mini-button" iconCls="icon-close" onClick="addClose()">取消</a>
					</td>
				</tr>
			</table>
		</div>
	</div>
<div id="editWindow" class="mini-window" title="修改模板账号"
	 style="width: 300px;" showModal="true" allowResize="true"
	 allowDrag="true">
	<div id="editForm">
		<input type="hidden" id="agentId2" class="mini-hidden">
		<table border="0" cellpadding="1" cellspacing="2">
			<tr>
				<td>模板账号：</td>
				<td><input style="width: 200px;" id="templateAccount2"
						   required="true" emptyText="请输入模板账号" class="mini-textbox" /></td>
			</tr>
			<tr>
				<td colspan="4" align="center">
					<a class="mini-button" iconCls="icon-save" onClick="editSave()">保存</a>
					<a class="mini-button" iconCls="icon-close" onClick="editClose()">取消</a>
				</td>
			</tr>
		</table>
	</div>
</div>
<script type="text/javascript">

    mini.parse();
    var grid = mini.get("datagrid1");
    var form = new mini.Form("#form1"); //获取表单对象
	var addWindow = mini.get("addWindow");
	var editWindow = mini.get("editWindow");
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
    function onActionRenderer(e) {
		var record = e.record;
		var uid = record.id;
		var rowIndex = e.rowIndex;
		var s;
		if(record.agentStatusEnum.code==0){
			s = '<a class="Edit_Button" href="javascript:edit(\''+ record.id+'\')">编辑</a>&nbsp;'
			+'<a class="Add_Button" href="javascript:addTemplateAccount(\''+ record.id+'\')">添加模板账号</a>&nbsp;'
					+'<a class="Add_Button" href="javascript:updateTemplateAccount(\''+ record.id+'\',\''+record.templateAccount+'\')">修改模板账号</a>&nbsp;'
			+'<a class="Edit_Button" href="javascript:valid(\''+ record.id+'\')">启用</a>';
		}else{
			s = '<a class="Edit_Button" href="javascript:edit(\''+ record.id+'\')">编辑</a>&nbsp;'
			+'<a class="Add_Button" href="javascript:addTemplateAccount(\''+ record.id+'\')">添加模板账号</a>&nbsp;'
					+'<a class="Add_Button" href="javascript:updateTemplateAccount(\''+ record.id+'\',\''+record.templateAccount+'\')">修改模板账号</a>&nbsp;'
			+'<a class="Edit_Button" href="javascript:invalid(\''+ record.id+'\')">不启用</a>';
		}
		return s;
	}
    function edit(id) {
        mini.open({
            url: "<c:url value='/agent/editAgentPage.action'/>",
            title: "代理商编辑", width: 350, height: 200,
            onload: function () {
                var iframe = this.getIFrameEl();
                var data = {action: "edit", agentId: id};
                iframe.contentWindow.SetData(data);
            },
            ondestroy: function (action) {
                if (action == "save") {
                    grid.reload();
                    mini.showTips({
                        content: "<b>成功</b> <br/>代理商编辑成功",
                        state: "success",
                        x: "center",
                        y: "center",
                        timeout: 3000
                    });
                }
            }
        });
    }
    function addTemplateAccount(id){
    	mini.get("agentId").setValue(id);
    	addWindow.show();
    }
	function updateTemplateAccount(id,templateAccount){
		mini.get("agentId2").setValue(id);
		if(templateAccount == null || templateAccount == "null"){
			templateAccount = "";
		}
		mini.get("templateAccount2").setValue(templateAccount);
		editWindow.show();
	}
    function addClose(){
    	addWindow.hide();
    }
	function editClose(){
		editWindow.hide();
	}
	function editSave() {
		var editForm = new mini.Form("#addForm"); //获取表单对象
		editForm.validate();
		if (editForm.isValid() == false) return;
		var agentId = mini.get("agentId2").getValue();
		var templateAccount = mini.get("templateAccount2").getValue();
		if (confirm("确定添加模板账号？")) {
			$.ajax({
				url : "/agent/updateTemplateAccount.action",
				data : {
					templateAccount : templateAccount,
					agentId : agentId
				},
				success : function(text) {
					addWindow.hide();
					grid.reload();
					mini.showTips({
						content : "<b>成功</b> <br/>修改成功",
						state : "success",
						x : "center",
						y : "center",
						timeout : 3000
					});
				},
				error : function(jqXHR, textStatus, errorThrown) {
					mini.showMessageBox({
						showModal : false,
						width : 250,
						title : "提示",
						iconCls : "mini-messagebox-warning",
						message : jqXHR.responseText,
						timeout : 3000,
						x : "right",
						y : "bottom"
					});
				}
			});
		}
	}
    function addSave() {
        var addForm = new mini.Form("#addForm"); //获取表单对象
        addForm.validate();
		if (addForm.isValid() == false) return;
		var agentId = mini.get("agentId").getValue();
		var templateAccount = mini.get("templateAccount").getValue();
		if (confirm("确定添加模板账号？")) {
			$.ajax({
				url : "/agent/addTemplateAccount.action",
				data : {
					templateAccount : templateAccount,
					agentId : agentId
				},
				success : function(text) {
					addWindow.hide();
					grid.reload();
					mini.showTips({
						content : "<b>成功</b> <br/>添加成功",
						state : "success",
						x : "center",
						y : "center",
						timeout : 3000
					});
				},
				error : function(jqXHR, textStatus, errorThrown) {
					mini.showMessageBox({
						showModal : false,
						width : 250,
						title : "提示",
						iconCls : "mini-messagebox-warning",
						message : jqXHR.responseText,
						timeout : 3000,
						x : "right",
						y : "bottom"
					});
				}
			});
		}
	}
	function valid(id){
		if (confirm("确定启用该代理商？")) {
			grid.loading("操作中，请稍后......");
			$.ajax({
				url : "/agent/updateAgentStatus.action",
				data : {
					id : id,
					flag : 1
				},
				success : function() {
					grid.reload();
					mini.showTips({
						content : "<b>成功</b> <br/>启用成功",
						state : "success",
						x : "center",
						y : "center",
						timeout : 3000
					});
				},
				error : function(jqXHR, textStatus, errorThrown) {
					grid.reload();
					mini.showMessageBox({
						showModal : false,
						width : 250,
						title : "提示",
						iconCls : "mini-messagebox-warning",
						message : jqXHR.responseText,
						timeout : 3000,
						x : "right",
						y : "bottom"
					});
				}
			});
		}
	}
	function invalid(id){
		if (confirm("确定不启用该代理商？")) {
			grid.loading("操作中，请稍后......");
			$.ajax({
				url : "/agent/updateAgentStatus.action",
				data : {
					id : id,
					flag : 0
				},
				success : function() {
					grid.reload();
					mini.showTips({
						content : "<b>成功</b> <br/>不启用成功",
						state : "success",
						x : "center",
						y : "center",
						timeout : 3000
					});
				},
				error : function(jqXHR, textStatus, errorThrown) {
					grid.reload();
					mini.showMessageBox({
						showModal : false,
						width : 250,
						title : "提示",
						iconCls : "mini-messagebox-warning",
						message : jqXHR.responseText,
						timeout : 3000,
						x : "right",
						y : "bottom"
					});
				}
			});
		}
	}
</script>
</body>
</html>
