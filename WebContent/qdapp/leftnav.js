// JavaScript Document
/*jQuery(document).ready(function(){
			getleftnav();
});*/

function getleftnav(){
	
	$.ajax({
		url:"ReserveServlet",
		dataType:"json",
		data:{action:"getnum"},
		success:function(data, textStatus){
			var td1 = $('<li class="active"><a href="./index.html">主页</a></li><li><a href="./user.html">用户中心</a></li><li><a href="./bespeaklist.html">预约借阅&nbsp;<span class="badge pull-right">'+data.message+'</span></a></li><li><a href="./aboutme.html">关于我们</a></li>');
			$('#nav').empty();
			$('#nav').append(td1);
			
		},
		error:function(XMLHttpRequest, textStatus){
			console.log("请求ReserveServlet出错");
		}
	});
}
