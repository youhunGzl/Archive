// JavaScript Document
jQuery(document).ready(function() {
	getleftnav();
	rightnav();

});

$('#submit').click(function() {

	
	var tuser = {
		user_name : ($('#username').val()),
		user_pwd : ($('#password').val())
	};	
	if(!checkFormat(tuser)){
		//console.info(checkFormat);
		layer.msg('请输入正确格式',{icon:2});
		return;
	}
	var cond = JSON.stringify(tuser);
	$.ajax({
		url : "LoginServlet?action=login",
		data : {
			params : cond
		},
		contentType : "application/json",
		dataType : "json",
		success : function(data, textStatus) {
			if (data.success) {
				layer.msg('登录中', {
					  icon: 1
					  ,shade: 0.05
					});
				
				setTimeout('suc()', 500);
			} else {
				layer.msg('登录错误',{icon: 2});
			}
		},
		error : function(XMLHttpRequest, textStatus) {
			console.log("请求LoginServlet出错");
		}
	});
});

// 检查输入框合法性
function checkFormat(user) {
	var reg1 = /[a-zA-Z][a-zA-Z0-9]{2,15}$/;
	var reg2 = /[a-zA-Z0-9]{6,20}$/;
	
	var flag = true;
	if (user.user_name == "" && user.user_name == undefined && user.user_pwd == "" && user.user_pwd == undefined) {
		flag = false;
	}else if (reg1.test(user.user_name) && reg2.test(user.user_pwd)) {
		flag = true;
	}else{
		flag=false;
	}
	return flag;
}

function suc(){
	 window.location = "index.html";
	}

$(function() {
	$("#drag").drag();
	$(":checkbox").iCheck({
		checkboxClass : 'icheckbox_flat-blue',
		increaseArea : '20%'
	});
	$("[data-toggle='tooltip']").tooltip();
	$("form :input")
			.blur(
					function() {
						var username = $("#username").val();

						var reg1 = /^[a-zA-Z][a-zA-Z0-9]{2,15}$/;
						var password = $("#password").val();
						var reg2 = /[a-zA-Z0-9]{6,20}$/;
						//console.info(username + "  " + password);

						var str = "#" + $(this).attr("id");
						var $parent = $(this).parent();
						if ($(this).is(str)) {
							if (this.value == "") {
								$parent.nextAll("div").children(
										"button").addClass("disabled");
								$parent.nextAll("div").children(
										"button")
										.prop("disabled", true);
								$parent.addClass("has-error");
							} else {
								$parent.nextAll("div").children(
										"button").removeClass(
										"disabled");
								$parent.nextAll("div").children(
										"button").removeProp(
										"disabled", true);
								$parent.removeClass("has-error");
								$parent.addClass("has-success");
							}
						}

						if (reg1.test(username)) {
							$("#username").parent().removeClass(
									"has-error");
							$("#username").parent().addClass(
									"has-success");
							$("#drag").css("display","block");
						} else {
							$("#username").focus();
							$("#username").parent().removeClass(
									"has-success");
							$("#username").parent().addClass(
									"has-error");
						}
						if (password != "" && password != undefined) {
							if (reg2.test(password)) {
								$("#password").parent().removeClass(
										"has-error");
								$("#password").parent().addClass(
										"has-success");
								
								
							} else {
								$("#password").parent().removeClass(
										"has-success");
								$("#password").parent().addClass(
										"has-error");
							}
							
						}
					});
});
