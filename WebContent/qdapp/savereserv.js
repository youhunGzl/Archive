// JavaScript Document
jQuery(document).ready(function(){
			list();
});

function list(){
	
		$.ajax({
		url:"ReserveServlet",
		dataType:"json",
		data:{action:"listrd"},
		success:function(data, textStatus){
			if(data.success){
				for(var i = 0; i <data.result.length;i++){
					var td = $('<tr style="height:30px;line-height:30px;"><input type="hidden" name="rid" id="rid" value="'+data.rdresult[i]+'"><td>'+data.result[i].resource_no+'</td><td>'+data.result[i].name+'</td><td>'+data.result[i].author+'</td><td>'+data.result[i].unit_code+'</td></tr>');
					$('#table').append(td);
				}
				
			}else{
				layer.msg('未选择',{icon:2});
				window.location = "bespeaklist.html";
			}
		},
		error:function(XMLHttpRequest, textStatus){
			console.log("请求ReserveServlet出错");
		}
	});
		
}

function savereserv(){
	
	var id = document.getElementsByName("rid");
	var ids=""; 
    for(var i=0;i< id.length;i++){ 
    	ids=ids+ (id.item(i).value + ",");
    }
	$.ajax({
	url:"ReserveServlet",
	dataType:"json",
	data:{action:"savereserv", reserve_time : $('#reserve_time').val() , borrow_objects : encodeURIComponent($('#borrow_objects').val()) , ids : ids},
	success:function(data, textStatus){
		if(data.success){
			layer.msg('预约成功', {
				  icon: 1
				  ,shade: 0.05
				});
			window.location = "user.html";
		}else{
			layer.msg('未选择',{icon:2});
			window.location = "bespeaklist.html";
		}
	},
	error:function(XMLHttpRequest, textStatus){
		console.log("请求ReserveServlet出错");
	}
});
}
