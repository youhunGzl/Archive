// JavaScript Document
function rightnav(){
					$.ajax({
								url : "LoginServlet?action=checkuser",
								dataType : "json",
								type : "post",
								success : function(data, textStatus) {
									if (data.success) {
										var td1 = $("<li><a href='./user.html'><span class='glyphicon glyphicon-user'></span>&nbsp;"
												+ data.result
												+ "&nbsp;<span class='badge pull-right'>"+data.message+"</span></a></li><li class='active'><a href='javascript:;' onclick='loginout()'><span class='glyphicon glyphicon-log-in'></span>&nbsp;&nbsp;退出登录</a></li>");
										$('#checkname').append(td1);
									} else {
										var td1 = $("<li><a href='register.html'><span class='glyphicon glyphicon-user'></span>&nbsp;注册</a></li><li class='active'><a href='login.html'><span class='glyphicon glyphicon-log-in'></span>&nbsp;&nbsp;登录</a></li>");
										$('#checkname').append(td1);
									}
								},
								error : function(XMLHttpRequest, textStatus) {
									console.log("请求LoginServlet出错");
								}
							});
				}

function loginout() {
	$.ajax({
		url : 'LoginServlet?action=loginout',
		dataType : "json",
		type : "post",
		success : function(data, textStatus) {
			layer.load(2);
			setTimeout('delayer()', 500);
		},
		error : function(XMLHttpRequest, textStatus) {
			console.log("请求LoginServlet出错");
		}
	});
}

function delayer() {
	window.location = "login.html";
}


/**分页
 * 
 * @param p    当前页  data.page[0].currentPage
 * @param t    总页数  data.page[0].totalPages
 * @param sum  显示的页码    默认是5页
 */
function fenye(p,t,sum){
	if(sum == null){
		var sum = 5;
	}
	
	var arr = new Array();
	
	var page= $('<ul class="pagination" id="page"></ul>');
	var page1 = $('<li><a href="#" onclick=pagination("first")>&laquo; &laquo;</a></li>');
	var page2 = $('<li><a href="#" onclick=pagination("prev")>&laquo;</a></li>');
	page.append(page1);
	page.append(page2);
	
	if(p <= sum/2){
		var k = 0;
		for(var i = 1 ;i<=sum; i++){
			if(i<=t){
				arr[k] = $('<li id="'+i+'"><a href="#" onclick=pagination('+i+')>'+i+'</a></li>');
				k++;
			}
		}
	}else if((sum+1)/2+p >= t){
		var k = 0;
		for(var i = p-(sum-(t-p+1))  ;i<p; i++){
			if(i>0){
				arr[k] = $('<li id="'+i+'"><a href="#" onclick=pagination('+i+')>'+i+'</a></li>');
				k++;
			}
		}
		for(var i = p ;i<=t ;i++){
			arr[k] = $('<li id="'+(i)+'"><a href="#" onclick=pagination('+(i)+')>'+(i)+'</a></li>');
			k++;
		}
	}else{
		var k = 0;
		for(var i = p-(parseInt(sum/2))  ;i<=p; i++){
			if(i>0){
				arr[k] = $('<li id="'+i+'"><a href="#" onclick=pagination('+i+')>'+i+'</a></li>');
				k++;
			}
		}
		for(var i = p+1 ;i< p+3 ;i++){
			if(i < t){
				arr[i] = $('<li id="'+i+'"><a href="#" onclick=pagination('+i+')>'+i+'</a></li>');
			}
		}
	}
	
	for(var i = 0;i<arr.length;i++){
		page.append(arr[i]);
	}
	
	var page3 = $('<li><a href="#" onclick=pagination("next")>&raquo;</a></li>');
	var page4 = $('<li><a href="#" onclick=pagination("last")>&raquo; &raquo;</a></li>');
	page.append(page3);
	page.append(page4);
	$('#page').empty();
	$('#page').append(page);
	$('#'+p+'').addClass("active");
	
	console.log("currentPage:" + p);
	console.log("totalPages:" + t);
	$("#currentPage").val(p);
	$("#totalPages").val(t);
}

