<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>住所情報の検索</title>
</head>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script type="text/javascript" src="/selfjsp/dwr/interface/Address.js"></script>
<script type="text/javascript" src="/selfjsp/dwr/engin.js"></script>
<script type="text/javascript" src="/selfjsp/dwr/util.js "></script>
<script type="text/javascript">
function init() {
	dwr.util.useLoagingMessage();
}
function getResult() {
	Address.getInfoByName($('#name').val(),
		function(result) {
			var msg = '<table border="1">';
			msg += '<tr><th>名前</th><th>住所</th><th>TEL</th><th>E-Mail</th></tr>';
			for (i = 0; i < result.length; i++) {
				msg += '<tr>';
				msg += '<td>' + result[i].name + '</td>';
				msg += '<td>' + result[i].address + '</td>';
				msg += '<td>' + result[i].tel + '</td>';
				msg += '<td>' + result[i].email + '</td>';
				msg += '</tr>';
			}
			msg += '</table>';
			$('result').html = (msg);
		}
	);
}
</script>
<body onload="init()">
<form>
名前：
<input type="text" name="name" size="20" />
<input type="button" value="検索" onclick="getResult()" />
</form>
<div id="result"></div>
</body>
</html>