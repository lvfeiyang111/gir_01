<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
		<title></title>
		
	</head>

	<body>
		<div id="login">
			<div id="top">
				<img src="/images/cloud.jpg" /><span>REGIST</span>
			</div>
			<div id="bottom">
				<form action="/admin/regist.action" method="post" id="myform1">
					<table border="0px" id="table">
						<tr>
							<td class="td1">用户名：</td>
							<td><input type="text" placeholder="Username" class="td2" name="myname" id="username"></td>
						</tr>
						<tr>
							<td></td>
							<td><span id="nameerr"></span></td>
						</tr>
						<tr>
							<td class="td1">密码：</td>
							<td><input type="password" placeholder="Password" class="td2" name="mypwd" id="userpasswd"></td>
						</tr>
						<tr>
							<td></td>
							<td><span id="pwderr"></span></td>
						</tr>
						<tr>
							<td></td>
							<td><input type="submit" value="注册" class="td3">
								<input type="button" value="取消" class="td3	" id="go">
							</td>
						</tr>
					</table>
				</form>
			</div>
			${registmsg}
		</div>
	</body>
<script>
	$("#go").click(function () {
		location.href="/admin/login.jsp"
	})
/*账号简单校验*/
	function user() {
		var j =/^\w{3,10}$/;
		let b = j.test($("#username").val());
		if (!b){
			$("#nameerr").html("请输入3到10位的账号")
			$("#nameerr").css("color","red")
			return false
		}else {
			return true;
		}
	}
	/*密码简单校验*/
	function pwd () {
		var j =/^\w{3,10}$/;
		let b = j.test($("#userpasswd").val());
		if (!b){
			$("#pwderr").html("请输入3到10位的密码")
			$("#pwderr").css("color","red")
			return false
		}else {
			return true;
		}
	}
	/*校验不成功阻止提交表单*/
		$(function () {
			$("#myform1").submit(function () {
				return user()&&pwd();
			})
			/*离焦调用校验方法*/
			$("#username").blur(user);
			$("#userpasswd").blur(pwd)
		})
</script>
</html>