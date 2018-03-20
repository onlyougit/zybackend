<%@ page import="com.rttmall.shopbackend.utils.Constants"%>
<%@ page import="com.rttmall.shopbackend.httpModel.SessionInfo"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/jsp/include/taglibs.jsp"%>
<%
	String userNm = null;
	//String roleNm = "";
	boolean isAdmin = true;
	boolean isDefaultPwd = false;
	Integer userId = 0;
	if (session.getAttribute(Constants.SESSION_BEAN) != null) {
		SessionInfo sessionInfo = (SessionInfo) session
				.getAttribute(Constants.SESSION_BEAN);
		userNm = sessionInfo.getUser().getUserName();
		userId = sessionInfo.getUser().getUserId();
		//roleNm=sessionInfo.getRole().getRole_nm();
		//String roleCd=sessionInfo.getRole().getRole_cd();
		//        if (!roleCd.equals("80b4413d429f4533bb62bc0694c80119")){
		//            isAdmin = false;
		//        }
		isDefaultPwd = sessionInfo.isDefaultPwd();
	} else {
		userNm = "";
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>期货后台管理系统</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<script src="/scripts/boot.js" type="text/javascript"></script>
<!--<link rel="shortcut icon" href="/images/favicon.png" type="image/png" />-->

<style type="text/css">
html, body {
	margin: 0;
	padding: 0;
	border: 0;
	width: 100%;
	height: 100%;
	overflow: hidden;
}

.header {
	background: url(/images/header.gif) repeat-x 0 -1px;
}

.Note {
	background: url(/images/Notes_Large.png) no-repeat;
	width: 32px;
	height: 32px;
}

.Reports {
	background: url(/images/Reports_Large.png) no-repeat;
	width: 32px;
	height: 32px;
}
</style>
</head>
<body>
	<div id="layout1" class="mini-layout" splitSize="5"
		style="width: 100%; height: 100%;">
		<div class="header" region="north" height="70" showSplitIcon="true"
			showSplit="false" showHeader="false">
			<!--<img style="border: none; width: 18%;" id="mainTitle"
				src="/images/logo.JPG" alt="云电商后台系统" />-->
				<h1 style="margin:0;padding:15px;cursor:default;font-family:'Trebuchet MS',Arial,sans-serif;">后台管理系统</h1>

			<div style="position: absolute; top: 6px; right: 10px;">
				<div
					style="height: 30px; float: left; text-align: center;margin-right:30px;">
					<p style="margin-bottom: 5px;">
						<h5>尊敬的<%=userNm %>用户，欢迎来到后台管理系统</h5>
					</p>
				</div>
				<a class="mini-button mini-button-iconTop" iconCls="icon-edit"
					onclick="javascript:changePassword()" plain="true">修改密码</a> <a
					class="mini-button mini-button-iconTop" iconCls="icon-close"
					onclick="javascript:window.location.href='/login/logout.action'"
					plain="true" style="margin-right:30px;">退出</a>
			</div>
		</div>
		<div title="south" region="south" showSplitIcon="true"
			showSplit="false" showHeader="false" height="30">
			<div style="line-height: 28px; text-align: center; cursor: default">Copyright
				© 起点科技有限公司版权所有</div>
		</div>
		<div showHeader="false" region="west" width="180" maxWidth="250" minWidth="100" >
        <!--OutlookMenu-->
	        <div id="leftTree" class="mini-outlookmenu"
						url="/login/showLeftTab.action" onitemclick="onItemSelect"
						idField="id" parentField="pid" textField="text"
						borderStyle="border:0"></div>
		</div>
		
		<div title="center" region="center" style="border: 0;">
				<div showCollapseButton="false" style="border: 0;height: 100%;">
					<!--Tabs-->
					<div id="mainTabs" class="mini-tabs" activeIndex="0"
						style="width: 100%; height: 100%;" plain="false">
						<div title="首页" url="/center.jsp"></div>
					</div>
				</div>
		</div>
	</div>
	<div id="chpwWindow" class="mini-window" title="修改密码"
		style="width: 270px;" showModal="true" allowResize="true"
		allowDrag="true">
		<div id="chpwform" class="form">
			<input class="mini-hidden" name="userId" value="<%=userId%>" />
			<table style="width: 100%;">
				<tr>
					<td style="width: 80px;">原密码：</td>
					<td style="width: 150px;"><input id="userPw" name="userPw"
						vtype="minLength:5" class="mini-password" required="true"
						style="width: 150px;" /></td>
				</tr>
				<tr>
					<td>新密码：</td>
					<td><input id="userNewPw" name="userNewPw" vtype="minLength:5"
						class="mini-password" required="true" style="width: 150px;" /></td>
				</tr>
				<tr>
					<td>确认密码：</td>
					<td><input id="userNewPwConfirm" name="userNewPwConfirm"
						vtype="minLength:5" class="mini-password" required="true"
						style="width: 150px;" /></td>
				</tr>
				<tr>
					<td
						style="text-align: right; padding-top: 5px; padding-right: 20px;"
						colspan="2"><a class="Update_Button"
						href="javascript:updatePw()">确定</a> <a class="Cancel_Button"
						href="javascript:cancelRow()">取消</a></td>
				</tr>

			</table>
		</div>
	</div>
	<script type="text/javascript">
    mini.parse();
    var tree = mini.get("leftTree");
    var chpwWindow = mini.get("chpwWindow");
    if (top != window){
        top.location.href = window.location.href;    
    }
    function onItemSelect(e) {
        var item = e.item;
        showTab(item);
    }
    function showTab(item) {
        var tabs = mini.get("mainTabs");

        var id = "tab$" + item.id;
        var tab = tabs.getTab(id);
        if (!tab) {
            tab = {};
            tab._nodeid = item.id;
            tab.name = id;
            tab.title = item.text;
            tab.showCloseButton = true;

            //这里拼接了url，实际项目，应该从后台直接获得完整的url地址
            tab.url = item.url;

            tabs.addTab(tab);
        }
        tabs.reloadTab(tab);
        tabs.activeTab(tab);
    }

    $(function() {
        var isDefaultPwd= "<%=isDefaultPwd%>";
			if (isDefaultPwd == "true") {
				mini.showMessageBox({
					showModal : false,
					width : 250,
					title : "提示",
					iconCls : "mini-messagebox-warning",
					message : "您的密码为初始密码，请尽快修改！",
					timeout : 3000,
					x : "right",
					y : "bottom"
				});
			}
		});
		function changePassword() {
			chpwWindow.show();

		}
		function cancelRow() {
			chpwWindow.hide();
		}
		function updatePw() {
			//判断两次密码是否一致
			var userNewPw = mini.get("#userNewPw").value;
			var userNewPwConfirm = mini.get("#userNewPwConfirm").value;
			if (userNewPw != userNewPwConfirm) {
				alert("密码不一致");
				return;
			}
			var form = new mini.Form("#chpwform");
			form.validate();
			if (form.isValid() == false)
				return;
			var o = form.getData();

			var json = mini.encode(o);//这里有一个坑：[o]得到的是一个数组对象，o得到的是一个对象
			$.ajax({
				url : "<c:url value='/user/changePassword.action'/>",
				data : {
					json : json
				},
				success : function() {
					chpwWindow.hide();
					mini.showTips({
						content : "<b>成功</b> <br/>密码修改成功",
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

			chpwWindow.hide();
		}
	</script>

</body>
</html>
