function tif(data,arr){
	
	for(var i=0;i<arr.length;i++){
		if(data==i){
			return arr[i];
		}
	}
	return null;
}

function getdata(){
	var id = GetRequest();

	$.ajax({
		url:'SearchServlet?action=getdata',
		data:{action:"getdata",params: id.id,cond: id.cond},
		dataType:"json",
		success:function(data,textStatus){
			var status=new Array("在存","损毁","预约","借出");
			if(data.success){
				var a = "";
				if(data.result[0].tEdocument_id != null){
					a = '有';
				}else{
					a = '没有';
				}
				var data1 = '<div><a href="#">'+data.result[0].tCatalog_id.name+'</a><span>&gt;</span><span>'+data.result[0].resource_no+'&nbsp&nbsp&nbsp&nbsp</span></div>';
				data1+='<div class="col-xs-12"><h2>'+data.result[0].name+'</h2><span>作者：'+data.result[0].author+'&nbsp&nbsp&nbsp&nbsp</span><span>电子文档：'+a+'&nbsp&nbsp&nbsp&nbsp</span><span>状态：'+status[data.result[0].status]+'&nbsp&nbsp&nbsp&nbsp</span><span>形成时间：'+data.result[0].create_time+'&nbsp&nbsp&nbsp&nbsp</span><h3>附注</h3><p><span>'+data.result[0].notions+'&nbsp&nbsp&nbsp&nbsp</span></p></div>';
				data1+='<div class="excessinfo col-xs-10"><br><span>标签主题&nbsp&nbsp&nbsp&nbsp</span><span>标签项：</span><span class="label label-primary">'+data.result[0].tag_theme+'</span>&nbsp&nbsp&nbsp&nbsp<span class="label label-primary">'+data.result[0].tag_item+'</span><a href="javascript:void(0)" class="btn btn-primary pull-right" onclick="addrd()">添加到预约箱</a></div>';
				var main = $(data1);
				$('#main').append(main);
			}else{
				layer.msg(data.message,{icon: 2});
			}
		},
		error:function(XMLHttpRequest, textStatus){
			console.log("请求SearchServlet出错");
		}
	});
};

function addrd() {
	var id = GetRequest();

	$.ajax({
		url:'ReserveServlet',
		data:{action:"addData",params: id.id,cond: id.cond},
		dataType:"json",
		success:function(data,textStatus){
			if(data.success){
				layer.msg(data.message, {
					  icon: 1
					  ,shade: 0.05
					});
				getleftnav();
			}else{
				layer.msg(data.message,{icon: 2});
				getleftnav();
				window.location = "login.html";
			}
		},
		error:function(XMLHttpRequest, textStatus){
			console.log("请求SearchServlet出错");
		}
	});
}

//获取id
function GetRequest() {
    var url = location.search; //获取url中"?"符后的字串
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        if (str.indexOf("&") != -1) {
            strs = str.split("&");
            for (var i = 0; i < strs.length; i++) {
                theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
            }
        } else {
            theRequest[str.split("=")[0]] = unescape(str.split("=")[1]);
        }
    }
    return theRequest;
}

jQuery(document).ready(function(){
	getleftnav();
	
	rightnav();
	getdata();
});
