<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../include/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>总部结算列表</title>
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
				<td>营业部名称：</td>
				<td>
					<input name="business.businessName" emptyText="请输入营业部名称" class="mini-textbox" onenter="search"/>
				</td>
				<td>
					代理商名称：
				</td>
				<td>
					<input name="agent.agentName" emptyText="请输入代理商名称" class="mini-textbox" onenter="search"/>
				</td>
				<td>代理商电话：</td>
				<td>
					<input name="agent.agentPhone" emptyText="请输入代理商电话" class="mini-textbox" onenter="search"/>
				</td>
			</tr>
		</table>
		<table>
			</tr>
			<td>
				结算时间：
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
                    <form action="/agentSettlement/exportAgentSettlement.action" method="post" id="form2">
                        <input type="hidden" name="queryParam" id="queryParam" class="mini-hidden">
                        <a class="mini-button" iconCls="icon-download" onclick="exportAgentSettlement()">导出</a>
                    </form>
                </td>
            </tr>
        </table>
    </div>
</div>
<div id="datagrid1" class="mini-datagrid" style="width:99%;height:75%;" sizeList="[20,30,50,100]" pageSize="20"
     url="/agentSettlement/queryAgentSettlement.action" idField="id" allowResize="true" allowCellWrap="true"
	 allowCellSelect="true" onshowrowdetail="onShowRowDetail">
    <div property="columns">
        <div type="expandcolumn" >#</div>
		<div field="business.businessName" width="100" headerAlign="center" align="center">营业部名称</div>
		<div field="agent.agentName" width="100" headerAlign="center" align="center">代理商名称</div>
		<div field="agent.agentPhone" width="120" headerAlign="center" align="center">代理商电话</div>
        <div field="sumDailyServiceCharge" width="120" headerAlign="center" align="center">当日总手续费</div>
        <div field="sumDailyServiceChargeCost" width="120" headerAlign="center" align="center">当日总手续费成本</div>
        <div field="dailyRebate" width="120" headerAlign="center" align="center">当日返佣</div>
		<div field="createTime" width="150" align="center" headerAlign="center" dateFormat="yyyy-MM-dd HH:mm:ss"
			 allowSort="true">结算时间</div>
    </div>
</div>
<div id="detailGrid_Form" style="display:none;">
    <div id="settlement_grid" class="mini-datagrid" style="width:100%;height:255px;"
         url="/agentSettlement/querySettlement.action" sizeList="[10,20,30]" pageSize="5"
    >
        <div property="columns">
        	<div width="80" type="indexcolumn"></div>
            <div field="customer.customerRealName" width="150" headerAlign="center" align="center">客户名称</div>
            <div field="customer.customerPhone" width="150" headerAlign="center" align="center">客户电话</div>
            <div field="customer.customerName" width="150" headerAlign="center" align="center">交易账号</div>
            <div field="sumDailyServiceCharge" width="150" headerAlign="center" align="center">当日总手续费</div>
            <div field="sumDailyServiceChargeCost" width="150" headerAlign="center" align="center">当日总手续费成本</div>
            <div field="dailyRebate" width="150" headerAlign="center" align="center">当日返佣</div>
            <div field="settlementStatusEnum.text" width="150" headerAlign="center" align="center">状态</div>
		<div field="createTime" width="150" align="center" headerAlign="center" dateFormat="yyyy-MM-dd HH:mm:ss"
			 allowSort="true">结算时间</div>
        </div>
    </div>
</div>
<script type="text/javascript">

    mini.parse();
    var grid = mini.get("datagrid1");
    var form = new mini.Form("#form1"); //获取表单对象
    var settlement_grid = mini.get("settlement_grid");
    var detailGrid_Form = document.getElementById("detailGrid_Form");
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
    function onShowRowDetail(e) {
        var grid = e.sender;
        var row = e.record;
        var td = grid.getRowDetailCellEl(row);
        td.appendChild(detailGrid_Form);
        detailGrid_Form.style.display = "block";
        settlement_grid.load({ agentId: row.agentId });
    }
    function exportAgentSettlement(){
        var data = form.getData();
        var json = mini.encode(data);
        mini.get("queryParam").setValue(json);
        $("#form2").submit();
    }
</script>
</body>
</html>
