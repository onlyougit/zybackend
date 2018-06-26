<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../include/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>代理客户列表</title>
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
					客户电话：
				</td>
				<td>
					<input name="customerPhone" emptyText="请输入电话号码" class="mini-textbox" onenter="search"/>
				</td>
				<td>
					客户姓名：
				</td>
				<td>
					<input name="customerRealName" emptyText="请输入姓名" class="mini-textbox" onenter="search"/>
				</td>
			</tr>
		</table>
		<table>
			</tr>
			<td>
				注册时间：
			</td>
			<td>
				<input emptyText="请输入开始时间" allowInput="false" style="width: 200px;" name="startDateTime" class="mini-datepicker" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" showOkButton="true" showClearButton="false" />
			</td>
			<td>
				<span class="separator"></span>
			</td>
			<td>
				<input emptyText="请输入结束时间" allowInput="false"  style="width: 200px;" name="endDateTime" class="mini-datepicker" format="yyyy-MM-dd HH:mm:ss" timeFormat="HH:mm:ss" showTime="true" showOkButton="true" showClearButton="false" />
			</td>
			<td align="center">
				<a class="mini-button" iconCls="icon-search" onClick="search()">搜索</a>&nbsp;
				<a class="mini-button" iconCls="icon-reload" onClick="clean()">重置</a>
			</td>
			<tr>
		</table>
    </div>
</div>
<div style="width:99%;">
	<div class="mini-toolbar" style="border-bottom:0;padding:0px;">
		<table style="width:100%;">
			<tr>
				<td style="width:100%;">
					<form action="/customer/exportCustomerAgent.action" method="post" name="form2" id="form2">
						<input type="hidden" name="queryParam" id="queryParam" class="mini-hidden">
						<a class="mini-button" iconCls="icon-download" onclick="exportCustomerAgent()">导出</a>
					</form>
				</td>
			</tr>
		</table>
	</div>
</div>
<div id="datagrid1" class="mini-datagrid" style="width:99%;height:80%;" sizeList="[20,30,50,100]" pageSize="20"
     url="/customer/queryAgentCustomer.action" idField="id" allowResize="true" allowCellWrap="true"
	 allowCellSelect="true">
    <div property="columns">
        <div type="indexcolumn"></div>
        <div field="customerName" width="120" headerAlign="center" align="center">交易账号</div>
        <div field="customerPhone" width="120" headerAlign="center" align="center">客户电话</div>
        <div field="customerRealName" width="120" headerAlign="center" align="center">客户姓名</div>
        <div field="customerCardId" width="120" headerAlign="center" align="center">身份证</div>
		<div field="registTime" width="150" align="center" headerAlign="center" dateFormat="yyyy-MM-dd HH:mm:ss"
			 allowSort="true">注册时间</div>
		<div name="action" width="100" headerAlign="center" align="center"
				renderer="onActionRenderer" cellStyle="padding:0;">操作</div>
    </div>
</div>
<div id="editWindow" class="mini-window" title="客户信息修改"
		style="width: 300px;" showModal="true" allowResize="true"
		allowDrag="true">
		<div id="editForm">
		<input type="hidden" id="customerId" class="mini-hidden">
			<table border="0" cellpadding="1" cellspacing="2">
				<tr>
					<td>客户名称：</td>
					<td><input style="width: 200px;" id="customerRealName"
						required="true" emptyText="请输入客户名称" class="mini-textbox" /></td>
				</tr>
				<tr>
					<td>客户身份证：</td>
					<td><input style="width: 200px;" id="customerCardId"
						required="true" emptyText="请输入客户身份证" class="mini-textbox" /></td>
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
		var s = ' <a class="Edit_Button" href="javascript:edit(\''
				+ record.id+'\',\''+record.customerRealName+'\',\''+record.customerCardId+'\')">编辑</a>';
		return s;
	}
function edit(id,customerRealName,customerCardId){
    	if(customerRealName == null || customerRealName == "null"){
    		customerRealName = "";
    	}
    	if(customerCardId == null || customerCardId == "null"){
    		customerCardId = "";
    	}
    	mini.get("customerRealName").setValue(customerRealName);
    	mini.get("customerCardId").setValue(customerCardId);
    	mini.get("customerId").setValue(id);
    	editWindow.show();
    }
    function editClose(){
    	editWindow.hide();
    }
    function editSave() {
        var editForm = new mini.Form("#editForm"); //获取表单对象
		editForm.validate();
		if (editForm.isValid() == false) return;
		var customerRealName = mini.get("customerRealName").getValue();
		var customerCardId = mini.get("customerCardId").getValue();
		var customerId = mini.get("customerId").getValue();
		if (confirm("确定更改客户信息？")) {
			$.ajax({
				url : "/customer/editCustomer.action",
				data : {
					customerRealName : customerRealName,
					customerCardId : customerCardId,
					customerId : customerId
				},
				success : function(text) {
					editWindow.hide();
					mini.showTips({
						content : "<b>成功</b> <br/>更改成功",
						state : "success",
						x : "center",
						y : "center",
						timeout : 3000
					});
					grid.reload();
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
	function exportCustomerAgent(){
		var data = form.getData();
		var json = mini.encode(data);
		mini.get("queryParam").setValue(json);
		$("#form2").submit();
	}
</script>
</body>
</html>
