// JavaScript Document
var current_fs, next_fs, previous_fs;
var left, opacity, scale;
var animating;
jQuery(document).ready(function() {
	getleftnav();
	rightnav();

});
$('.next').click(function() {
	if (animating)
                return false;
            animating = true;
            current_fs = $(this).parents("fieldset");
            next_fs = $(this).parents("fieldset").next();
            $('#progress li').eq($('fieldset').index(next_fs)).addClass('active');
            next_fs.show();
            current_fs.animate({ opacity: 0 }, {
                step: function (now, mx) {
                    scale = 1 - (1 - now) * 0.2;
                    opacity = 1 - now;
                    current_fs.css({ 'transform': 'scale(' + scale + ')' });
                    next_fs.css({
                        'left': left,
                        'opacity': opacity
                    });
                },
                duration: 500,
                complete: function () {
                    current_fs.hide();
                    animating = false;
                },
                easing: 'easeInExpo'
            });
});
$('.previous').click(function(){
		 if (animating)
                return false;
            animating = true;
            current_fs = $(this).parents("fieldset");
            previous_fs = $(this).parents("fieldset").prev();
            $('#progress li').eq($('fieldset').index(current_fs)).removeClass('active');
            previous_fs.show();
            current_fs.animate({ opacity: 0 }, {
                step: function (now, mx) {
                    scale = 0.8 + (1 - now) * 0.2;
                    opacity = 1 - now;
                    current_fs.css({ 'left': left });
                    previous_fs.css({
                        'transform': 'scale(' + scale + ')',
                        'opacity': opacity
                    });
                },
                duration: 500,
                complete: function () {
                    current_fs.hide();
                    animating = false;
                },
                easing: 'easeOutCubic'
            });
		});

$("#submit").click(function() {

	console.info("11");
	var tuser = {
		user_name : ($('#username').val()),
		user_pwd : ($('#password').val()),
		real_name : encodeURIComponent($('#name').val()),
		profession : encodeURIComponent($('#profession').val()),
		cert_type : encodeURIComponent($('#cert_type').val()),
		cert_no : ($('#cid').val()),
		phone : ($('#tel').val()),
		email : ($('#email').val()),
		sex : encodeURIComponent($('#sex').val()),
		address : encodeURIComponent($('#address').val()),
		selflabel : encodeURIComponent($('#selflabel').val())
	};

	if (!checkFormat(tuser)) {
		//console.info(tuser);
		layer.msg('请输入正确格式',{
			  icon: 2
			  ,shade: 0.01
			});
		return;
	}

	var cond = JSON.stringify(tuser);
	$.ajax({
		url : 'RegisterServlet',
		data : {
			action : "getAll",
			params : cond
		},
		dataType : "json",
		success : function(data, textStatus) {
			if (data.success) {
				layer.msg(data.message, {
					  icon: 1
					  ,shade: 0.05
					});
				
				setTimeout('delayer()', 800);
			}
		},
		error : function(XMLHttpRequest, textStatus) {
			console.log("请求RegiserServlet出错");
		}
	});
});

// 检查输入框合法性
function checkFormat(user) {
	var username_reg = /^[a-zA-Z][a-zA-Z0-9]{2,15}$/;
	var password_reg = /[a-zA-Z0-9]{6,20}$/;
	var email_reg = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
	// var zipcode_reg = /[1-9]{1}(\d+){5}/;
	var phone_reg = /[0-9]*[1-9][0-9]*$/;// 正整数

	var flag = true;
	if (user.user_name == "" && user.user_name == undefined
			&& user.user_pwd == "" && user.user_pwd == undefined
			&& user.real_name == "" && user.real_name == undefined
			// && user.profession == "" && user.profession == undefined
			&& user.cert_type == "" && user.cert_type == undefined
			&& user.cert_no == "" && user.cert_no == undefined
			&& user.phone == "" && user.phone == undefined && user.email == ""
			&& user.email == undefined && user.sex == ""
			&& user.sex == undefined
	// && user.address == "" && user.address == undefined
	// && user.selflabel == "" && user.selflabel == undefined
	) {
		flag = false;
	} else if (username_reg.test(user.user_name)
			&& password_reg.test(user.user_pwd) && email_reg.test(user.email)
			&& phone_reg.test(user.phone)

			&& user.real_name != "" && user.real_name != undefined
			&& user.cert_type != "" && user.cert_type != undefined
			&& user.cert_no != "" && user.cert_no != undefined
			&& user.sex != "" && user.sex != undefined) {
		flag = true;
	} else {
		flag = false;
	}
	return flag;
}

function delayer(){
	 window.location = "user.html";
	}


$(function() {
	$(":checkbox").iCheck({
					checkboxClass: 'icheckbox_flat-blue',
					increaseArea: '20%'

				});
	var username = $("#username").val();
	var password = $("#password").val();
	var cpa = $("#cpa").val();
	var username_reg = /^[a-zA-Z][a-zA-Z0-9]{2,15}$/;
	var password_reg = /[a-zA-Z0-9]{6,20}$/;

	var flag = true;
	if (username == "" && username == undefined && password == ""
			&& password == undefined && cpa == "" && cpa == undefined) {
		flag = false;
	} else if (username_reg.test(username)
			&& password_reg.test(password) && password_reg.test(cpa)
			&& cpa == password) {
		flag = true;
	} else {
		$("#next_one").addClass("disabled");
		$("#next_one").prop("disabled", true);
		flag = false;
	}

	if (flag) {
		$("#next_one").removeClass("disabled");
		$("#next_one").removeProp("disabled", true);
	} else {
		$("#next_one").addClass("disabled");
		$("#next_one").prop("disabled", true);
	}

	var cid = $("#cid").val();

	var flag2 = false;
	if (cid != "" && cid != undefined) {
		flag2 = true;
	} else {
		flag2 = false;
	}

	if (flag2) {
		/* $("#previous_one").removeClass(
			"disabled");
		$("#previous_one").removeProp(
			"disabled", true); */
		$("#next_two").removeClass("disabled");
		$("#next_two").removeProp("disabled", true);
	} else {
		/* $("#previous_one").removeClass(
		"disabled");
		$("#previous_one").removeProp(
		"disabled", true); */
		$("#next_two").addClass("disabled");
		$("#next_two").prop("disabled", true);
	}

	$("[data-toggle='tooltip']").tooltip();
	$('form :input')
			.blur(
					function() {
						var str = "#" + $(this).attr("id");
						var $parent = $(this).parent();
						if ($(this).is(str)) {
							if (this.value == "") {
								/* $parent.siblings(":button").addClass(
										"disabled");
								$parent.siblings(":button").prop(
										"disabled", true); */
								//$parent.addClass("has-error");
							} else {

								var username = $("#username").val();
								var password = $("#password").val();
								var cpa = $("#cpa").val();
								var username_reg = /^[a-zA-Z][a-zA-Z0-9]{2,15}$/;
								var password_reg = /[a-zA-Z0-9]{6,20}$/;

								var flag = true;
								if (username == ""
										&& username == undefined
										&& password == ""
										&& password == undefined
										&& cpa == ""
										&& cpa == undefined) {
									flag = false;
								} else if (username_reg.test(username)
										&& password_reg.test(password)
										&& password_reg.test(cpa)
										&& cpa == password) {
									flag = true;
								} else {
									$("#next_one").addClass("disabled");
									$("#next_one").prop("disabled",
											true);
									flag = false;
								}

								if (flag) {
									$("#next_one").removeClass(
											"disabled");
									$("#next_one").removeProp(
											"disabled", true);
								} else {
									$("#next_one").addClass("disabled");
									$("#next_one").prop("disabled",
											true);
								}
								var cid = $("#cid").val();

								var flag2 = false;
								if (cid != "" && cid != undefined) {
									$("#next_two").removeClass(
											"disabled");
									$("#next_two").removeProp(
											"disabled", true);
									flag2 = true;
								} else {
									flag2 = false;
									$("#next_two").addClass("disabled");
									$("#next_two").prop("disabled",
											true);
								}

								if (flag2) {
									$("#next_two").removeClass(
											"disabled");
									$("#next_two").removeProp(
											"disabled", true);
								} else {
									$("#next_two").addClass("disabled");
									$("#next_two").prop("disabled",
											true);
								}

							}
						}

						//var username = $("#username").val();
						//var username_reg = /^[a-zA-Z][a-zA-Z0-9]{2,15}$/;
						var password = $("#password").val();
						var cpa = $("#cpa").val();
						var password_reg = /[a-zA-Z0-9]{6,20}$/;

						/* if (username_reg.test(username)) {
							$("#username").parent().removeClass(
									"has-error");
							$("#username").parent().addClass(
									"has-success");
							;
						} else {
							$("#username").parent().removeClass(
									"has-success");
							$("#username").parent().addClass(
									"has-error");
						} */
						if (password != "" && password != undefined) {
							if (password_reg.test(password)) {
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
						if (cpa != "" && cpa != undefined) {
							if (password_reg.test(cpa)) {
								$("#cpa").parent().removeClass(
										"has-error");
								$("#cpa").parent().addClass(
										"has-success");
							} else {
								$("#cpa").parent().removeClass(
										"has-success");
								$("#cpa").parent()
										.addClass("has-error");
							}

							if (password == cpa) {
								$("#password").parent().removeClass(
										"has-error");
								$("#password").parent().addClass(
										"has-success");

								$("#cpa").parent().removeClass(
										"has-error");
								$("#cpa").parent().addClass(
										"has-success");
							}
							if (cpa != password) {
								$("#password").parent().removeClass(
										"has-success");
								$("#password").parent().addClass(
										"has-error");
								$("#cpa").parent().removeClass(
										"has-success");
								$("#cpa").parent()
										.addClass("has-error");
							}
						}
						$("#next_one")
								.click(
										function() {
											var username = $(
													"#username").val();
											var password = $(
													"#password").val();
											var cpa = $("#cpa").val();
											var username_reg = /^[a-zA-Z][a-zA-Z0-9]{2,15}$/;
											var password_reg = /[a-zA-Z0-9]{6,20}$/;

											var flag = true;
											if (username == ""
													&& username == undefined
													&& password == ""
													&& password == undefined
													&& cpa == ""
													&& cpa == undefined) {
												flag = false;
											} else if (username_reg
													.test(username)
													&& password_reg
															.test(password)
													&& password_reg
															.test(cpa)
													&& cpa == password) {
												flag = true;
											} else {
												$("#next_one")
														.addClass(
																"disabled");
												$("#next_one").prop(
														"disabled",
														true);
												flag = false;
											}

											if (flag) {
												$("#next_one")
														.removeClass(
																"disabled");
												$("#next_one")
														.removeProp(
																"disabled",
																true);
											} else {
												$("#next_one")
														.addClass(
																"disabled");
												$("#next_one").prop(
														"disabled",
														true);

												$("#username").focus();
											}
										});

						/*
						  $("#next_two").click(
								function() {
									var cid = $("#cid").val();

									var flag = false;
									if (cid == "" && cid == undefined) {
										flag = false;
									} else{
										flag = true;
									}

									if (flag) {
										$("#next_two").removeClass(
												"disabled");
										$("#next_two").removeProp(
												"disabled", true);
									} else {
										$("#next_two").addClass(
												"disabled");
										$("#next_two").prop("disabled",
												true);
									}
								}); */

					});

});

$("#username").blur(function() {
	var username = $("#username").val();
	var username_reg = /^[a-zA-Z][a-zA-Z0-9]{2,15}$/;

	if (username_reg.test(username)) {
		$("#username").parent().removeClass("has-error");
		$("#username").parent().addClass("has-success");
		;
	} else {
		$("#username").parent().removeClass("has-success");
		$("#username").parent().addClass("has-error");
	}
	$.ajax({
		url : 'RegisterServlet',
		data : {
			action : "chickname",
			params : username
		},
		dataType : "json",
		success : function(data, textStatus) {
			if (data.success) {
				layer.msg(data.message,{
					  icon: 1
					  ,shade: 0.01
					});
				console.log(data.message);
			} else {
				layer.msg(data.message,{
					  icon: 2
					  ,shade: 0.01
					});

				$("#username").parent().removeClass("has-success");
				$("#username").parent().addClass("has-error");

				$("#next_one").addClass("disabled");
				$("#next_one").prop("disabled", true);
				$("#username").focus();
			}
		},
		error : function(XMLHttpRequest, textStatus) {
			console.log("请求RegiserServlet出错");
		}
	});

});
$("#cid").blur(function() {
	var cid = $("#cid").val();

	if (cid != "" && cid != undefined) {
		$("#cid").parent().removeClass("has-error");
		$("#cid").parent().addClass("has-success");
	} else {
		$("#cid").parent().removeClass("has-success");
		$("#cid").parent().addClass("has-error");
	}

});
//var work = $("#work").val();
//var sex = $("#sex").val();
//var selflabel = $("#selflabel").val();
$("#name").blur(function() {
	var name=$("#name").val();
	if (name != "" && name != undefined) {
		$("#name").parent().removeClass("has-error");
		$("#name").parent().addClass("has-success");
	} else {
		$("#name").parent().removeClass("has-success");
		$("#name").parent().addClass("has-error");
	}
});

$("#tel").blur(function() {
	var phone_reg = /[0-9]*[1-9][0-9]*$/;//正整数
	var tel=$("#tel").val();
	if (phone_reg.test(tel)) {
		$("#tel").parent().removeClass("has-error");
		$("#tel").parent().addClass("has-success");
		;
	} else {
		$("#tel").parent().removeClass("has-success");
		$("#tel").parent().addClass("has-error");
	}

});

$("#address").blur(function() {
	var address = $("#address").val();
	if (address != "" && address != undefined) {
		$("#address").parent().removeClass("has-error");
		$("#address").parent().addClass("has-success");
	} else {
		$("#address").parent().removeClass("has-success");
		$("#address").parent().addClass("has-error");
	}

});
$("#email").blur(function() {
	var email_reg = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
	var email = $("#email").val();
	
	if (email_reg.test(email)) {
		$("#email").parent().removeClass("has-error");
		$("#email").parent().addClass("has-success");
		;
	} else {
		$("#email").parent().removeClass("has-success");
		$("#email").parent().addClass("has-error");
	}
});
/* if (work != "" && work != undefined) {
	$("#work").parent()
			.removeClass("has-error");
	$("#work").parent().addClass("has-success");
} else {
	$("#work").parent().removeClass(
			"has-success");
	$("#work").parent().addClass("has-error");
} */


