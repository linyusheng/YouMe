<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
<head>
	<title></title>
	<link rel="stylesheet"	href="${pageContext.request.contextPath}/css/bootstrap.min.css"	type="text/css"></link>
	<script type="text/javascript"	src="${pageContext.request.contextPath}/js/jquery-1.7.2.min.js"></script>
	<script type="text/javascript"	src="${pageContext.request.contextPath}/js/json2.js"></script>
	<script type="text/javascript">
		function sendData() {
			var url = $('#url').val();
			var form = $('form');
			if (url == "" || url == null) {
				alert("资源路径不能为空！");
				return;
			}
			$.post('${pageContext.request.contextPath}' + url, form.serialize(),
					function(result) {
						$('#result').val(JSON.stringify(result));
					}, "JSON");
		}
	</script>
</head>
<body>
	<div style="width:960px;margin:0 auto;">
		<h1 style="text-align: center;">JSON测试工具</h1>
		<br />
		<form class="form-horizontal" role="form">
			<div class="form-group">
				<label for="url" class="col-sm-2 control-label">访问资源：</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="url"
						placeholder="如：/activities/add">
				</div>
			</div>
			<div class="form-group">
				<label for="param" class="col-sm-2 control-label">访问参数：</label>
				<div class="col-sm-10">
					<textarea name="jsonString" class="form-control" id="param"
						placeholder='{"userId": 1,"userName": "lys"}' rows="5"></textarea>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<input type="button" class="btn btn-default" onclick="sendData();"
						value="发送测试">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">回传参数：</label>
				<div class="col-sm-10">
					<textarea class="form-control" id="result" placeholder="回传的JSON字符串"
						rows="13"></textarea>
				</div>
			</div>
		</form>
	</div>
</body>
</html>
