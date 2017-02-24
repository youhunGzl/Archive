var srange=$(".srange");
var showe=$(".showr span");
srange.hide();
 $(".showr").click(function(){
 	showe.toggleClass("abc");
 	srange.toggleClass("show");
	
 });
// $(".showr").mouseenter(function(){
// 	showe.addClass("abc");
// 	srange.addClass("show");
// });$(".showr").mouseleave(function(){
// 	showe.removeClass("abc");
// 	srange.removeClass("show");
// });
$(".srange").children().click(function(){
	var customary=$("#choice li").first().text();
	var newc=$(this).text();
	$("#choice li").first().html(newc);
	$(this).html(customary);
});
var srangel=$(".srange-lg");
var showel=$(".showr-lg span");
srangel.hide();
 $(".showr-lg").click(function(){
 	showel.toggleClass("abc");
 	srangel.toggleClass("show");
	
 });
// $(".showr").mouseenter(function(){
// 	showe.addClass("abc");
// 	srange.addClass("show");
// });$(".showr").mouseleave(function(){
// 	showe.removeClass("abc");
// 	srange.removeClass("show");
// });
$(".srange-lg").children().click(function(){
	var customary=$("#choice-lg li").first().text();
	var newc=$(this).text();
	$("#choice-lg li").first().html(newc);
	$(this).html(customary);
});
function getall(catalog , type){
	$('.main').remove();
	if(type == null){
		var f = document.getElementById("tran").value;
	}else{
		var f = type.value;
		$('#tran').val(f);
	}
	
	
	var currentPage = document.getElementById("currentPage");
	var data = new Array();
	var firstcreate_time = "";
	var endcreate_time = "";
	if(catalog == null){
		$('#catalogvalue').val("");
		var catalog = $('#catalogvalue').val();
		
	}else{
		var catalog = encodeURIComponent(catalog);
		$('#catalogvalue').val(catalog);
	}
	
	if(f=="file"){
		data = {
				 notions: encodeURIComponent($('#keywork').val()),
				 
				 unit_code: encodeURIComponent($('#unit_code1').val()),
				 name: encodeURIComponent($('#name1').val()),
				 author: encodeURIComponent($('#author1').val()),
				 file_no: encodeURIComponent($('#file_no1').val()),
				 piece_no: encodeURIComponent($('#piece_no1').val()),
				 resource_no: encodeURIComponent($('#resource_no1').val()),
				 
				};
		firstcreate_time= $('#firstcreate_time1').val();
		endcreate_time= $('#endcreate_time1').val();
	}
	if(f=="material"){
		data = {
				notions: encodeURIComponent($('#keywork').val()),
				 
				 unit_code: encodeURIComponent($('#unit_code2').val()),
				 name: encodeURIComponent($('#name2').val()),
				 author: encodeURIComponent($('#author2').val()),
				 publisher: encodeURIComponent($('#publisher2').val()),
				 resource_no: encodeURIComponent($('#resource_no2').val()),
				};
		firstcreate_time= $('#firstcreate_time2').val();
		endcreate_time= $('#endcreate_time2').val();
	}
	if(f=="multimedia"){
		data = {
				notions: encodeURIComponent($('#keywork').val()),
				 
				 unit_code: encodeURIComponent($('#unit_code3').val()),
				 name: encodeURIComponent($('#name3').val()),
				 author: encodeURIComponent($('#author3').val()),
				 resource_no: encodeURIComponent($('#resource_no3').val()),
				 media_type: encodeURIComponent($('#media_type').val()),
		 			
				};
		firstcreate_time= $('#firstcreate_time3').val();
		endcreate_time= $('#endcreate_time3').val();
	}
	if(encodeURIComponent($('#name3').val()) == "undefined"){
		data = {
				illustrate: encodeURIComponent($('#keywork').val()),
				 
		 			
				};
		firstcreate_time= "";
		endcreate_time= "";
	}
	 var cond = JSON.stringify(data);
	 
	 
	 if(f == ""||f == null){
		 
	 }else{
		$.ajax({
			url:'SearchServlet',
			data:{action:"find", params: f, term: cond ,firstcreate_time: firstcreate_time,endcreate_time: endcreate_time,currentPage: currentPage.value,catalog:catalog},
			dataType:"json",
			success:function(data,textStatus){
				if(data.success){
					//冒泡排序
					for(var i=0; i<data.result.length; i++) {    
					    for(var j=i+1; j<=data.result.length-1; j++) {
					        if(data.result[i].create_time<data.result[j].create_time) {
					            var temp = data.result[i];
					            data.result[i] = data.result[j];
					            data.result[j] = temp;
					        }
					    }
					}
					for(var i=0;i<data.result.length;i++){
						//console.log("msg:" + data.result[i].susername + " " +data.result[i].staffNo);
						var td1 = $("<h3><a href='./detail.html?id="+data.result[i].id+"&cond="+data.cond+"'>"+data.result[i].name +"</a></h3><span class='pull-right'>--"+data.result[i].create_time+"</span><br/> ");
						var td2 = $("<p>"+data.result[i].subject_abstract+"<span class='glyphicon glyphicon-folder-open'></span></p>");
						var td3 = $("<span class='boldf'>出版人</span><span>"+data.result[i].author+"<span></span>");
						var td4 = $("<span class='pull-right'>档案资料编号：&nbsp;&nbsp;"+data.result[i].resource_no+"</span>");
						var li = $("<li class='list-group-item main'></li>");
						li.append(td1);
						li.append(td2);
						li.append(td3);
						li.append(td4);
						$('#tblStaffInfo').append(li);
					}
					
					var p = data.page[0].currentPage;
					var t = data.page[0].totalPages;
					fenye(p,t);
					
				}else{
					if(data.message.indexOf(":")==-1){
						layer.msg('查询不到数据',{icon: 2});
						console.log("信息：" + data.message);
					}else{
						var msg = data.message.split(":");
						console.log("出错信息:" + msg[1]);
					} 
					
				}					
			},
			error:function(XMLHttpRequest, textStatus){
				console.log("请求SearchServlet出错");
			}
		});
	 }
};

function getcatalog(){
	$.ajax({
		url:'SearchServlet',
		data:{action:"getcatalog"},
		dataType:"json",
		success:function(data,textStatus){
			if(data.success){
				for(var i = 0 ;i<data.result.length ;i++){
					if(data.result[i].parent_id == null){
						var main = '<div class="panel panel-primary"><div class="panel-heading"><h4 class="panel-title"><a data-toggle="collapse" data-parent="#catalog" href="#'+data.result[i].catalog_no+'">'+data.result[i].name+'</a></h4></div><div id="'+data.result[i].catalog_no+'" class="panel-collapse collapse ">';
						for(var j = 0 ;j<data.result.length ;j++){
							if(data.result[j].parent_id != null){
								if(data.result[j].parent_id.id == data.result[i].id){
									main = main +'<div class="panel-body">'+data.result[j].name;
									for(var k = 0 ;k<data.count.length ;k++){
										if(data.count[k].catalog_no == data.result[j].catalog_no){
											
											main = main + '<span class="badge pull-right">'+data.count[k].count+'</span>';
											main = main + '<ul class="detail_stage"><a href="javascirpt:void(0);" onclick="javascirpt:getall('+data.result[j].id+',file)"><li>文档&nbsp;&nbsp;<span class="badge">'+data.count[k].file+'</span></li></a>';
											main = main + '<a href="javascirpt:void(0);" onclick="javascirpt:getall('+data.result[j].id+',material)"><li>图像&nbsp;&nbsp;<span class="badge">'+data.count[k].material+'</span></li></a>';
											main = main + '<a href="javascirpt:void(0);" onclick="javascirpt:getall('+data.result[j].id+',multimedia)"><li>视频&nbsp;&nbsp;<span class="badge">'+data.count[k].multimedia+'</span></li></a></ul></div>';
										}
									}
								}
							}
						}
						main = main + '</div></div>';
						var li = $(main);
						$('#catalog').append(li);
					}
					
				}
				
				
			}
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
	getall();
}
