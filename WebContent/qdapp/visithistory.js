/**
 *浏览历史
 */
	
function visithistory(){

	var currentPage = document.getElementById("currentPage");
	
	$.ajax({
		url:'UserServlet',
		data:{action:"visithistory", currentPage:currentPage.value},
		dataType:"json",
		success:function(data,textStatus){
				if(data.success){
					$('#history').empty();
					for(var i = 0 ;i<data.result.length;i++){
						var da = $('<li class="slist"><a href="./detail.html?id='+data.result[i].archive_id+'&cond='+data.result[i].archive_type+'"><div class="con"><h1>'+data.resultdata[i].name+'</h1><h2>'+data.result[i].time+'</h2></div></a></li>');
						$('#history').append(da);
						
					}
					
					var p = data.page[0].currentPage;
					var t = data.page[0].totalPages;
					fenye(p,t);
				}else{
					if(data.message.indexOf(":")==-1){
						console.log("信息：" + data.message);
					}else{
						var msg = data.message.split(":");
						console.log("出错信息:" + msg[1]);
					} 
					
				}					
			},
		error:function(XMLHttpRequest, textStatus){
			console.log("请求UserServlet出错");
		}
	});
	
}


function pagination(action) {
	
	var currentPage = $("#currentPage").val();
	var totalPages = $("#totalPages").val();
	if (action == "first") {
		$('#currentPage').val(1);
		console.log("当前页码："+currentPage);
		console.log("总页码："+totalPages);
	}else if (action == "prev") {
		console.log("当前页码："+currentPage);
		console.log("总页码："+totalPages);
		currentPage = parseInt(currentPage) - 1;
		if (currentPage == 0)
			$("#currentPage").val(1);
		else
			$("#currentPage").val(currentPage);
	}else if (action == "next") {
		console.log("当前页码："+currentPage);
		console.log("总页码："+totalPages);
		currentPage = parseInt(currentPage) + 1;
		if (currentPage == parseInt(totalPages) + 1)
			$("#currentPage").val(totalPages);
		else
			$("#currentPage").val(currentPage);
	}else if (action == "last") {
		console.log("当前页码："+currentPage);
		console.log("总页码："+totalPages);
		$("#currentPage").val(totalPages);
	}else{
		$("#currentPage").val(action); 
	}
	visithistory();
}
		