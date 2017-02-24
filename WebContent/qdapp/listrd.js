// JavaScript Document
jQuery(document).ready(function(){
	rightnav();
	getleftnav();
			getrd();
});

function list(){
	var id = document.getElementsByName("checkid");
	var checkedIds=""; 
    for(var i=0;i< id.length;i++){ 
    if(id.item(i).type != "checkbox")continue; 
    var oEl = id.item(i); 
        if(oEl.checked) { 
            checkedIds = checkedIds + (oEl.value + ","); 
        } 
    }
	if(checkedIds==""){
		layer.msg('未选择',{icon: 2});
	}else{
		$.ajax({
		url:"ReserveServlet",
		dataType:"json",
		data:{action:"addcard" , ids:checkedIds},
		success:function(data, textStatus){
			if(data.success){
				window.location = "bespeak.html";
			}else{
				alert('未选择');
				layer.msg('未选择',{icon: 2});
			}
		},
		error:function(XMLHttpRequest, textStatus){
			console.log("请求ReserveServlet出错");
		}
	});
	}
	
}

function getrd(){
	$.ajax({
		url:"ReserveServlet",
		dataType:"json",
		data:{action:"getrd"},
		success:function(data, textStatus){
			if(data.success){
				for(var i = 0; i <data.result.length;i++){
					var td = $('<tr><td><input type="checkbox" name="checkid" value="'+data.rdresult[i]+'"></td><td>'+data.result[i].resource_no+'</td><td>'+data.result[i].attach_name+'</td><td>'+data.result[i].author+'</td><td>'+data.result[i].unit_code+'</td></tr>');
					$('#table').append(td);
					$(":checkbox").iCheck({
						checkboxClass: 'icheckbox_flat-blue',
						increaseArea: '20%'

					});
					$("#choice").on('ifChecked', function(event){
						  $(":checkbox").iCheck('check');
						});
						$("#choice").on('ifUnchecked', function(event){
						  $(":checkbox").iCheck('uncheck');
						});
				}
				
			}
		},
		error:function(XMLHttpRequest, textStatus){
			console.log("请求ReserveServlet出错");
		}
	});
}
