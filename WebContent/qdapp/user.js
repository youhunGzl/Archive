// JavaScript Document
jQuery(document).ready(function() {
	getleftnav();
	rightnav();
	getuser();
	user();

});
$(function() {
				$(".rlist").children().addClass("slist");
				$(document).scroll(function() {
					if($(window).scrollTop() == 0) {
						$("#amdee").addClass("hide");
					} else if($(window).scrollTop() > 100) {
						$("#amdee").removeClass("hide");
					}
				});
				var changec=document.getElementById("changec");
				var changel=document.getElementById("changel");

				$("#changel").click(function(){
					changel.className="btn-list-open";
					changec.className="btn-card-off";
					$(".rlist").children().removeClass("scard");
					$(".rlist").children().addClass("slist");
				});
				$("#changec").click(function(){
					changec.className="btn-card-open";
					changel.className="btn-list-off";

					$(".rlist").children().removeClass("slist");
					$(".rlist").children().addClass("scard");
				})
				$(function(){
				$(".leftdao").children().mouseenter(function(){
					$(this).addClass("adds");
				});
				$(".leftdao").children().mouseleave(function(){
					$(this).removeClass("adds");
				});	
				
					var toggle = $('#ss_toggle');
					var menu = $('#ss_menu');
					var rot;
					$('#ss_toggle').on('click', function (ev) {
						rot = parseInt($(this).data('rot')) - 180;
						menu.css('transform', 'rotate(' + rot + 'deg)');
						menu.css('webkitTransform', 'rotate(' + rot + 'deg)');
						if (rot / 180 % 2 == 0) {
							toggle.parent().addClass('ss_active');
							toggle.addClass('close');
						} else {
							toggle.parent().removeClass('ss_active');
							toggle.removeClass('close');
						}
						$(this).data('rot', rot);
					});
					menu.on('transitionend webkitTransitionEnd oTransitionEnd', function () {
						if (rot / 180 % 2 == 0) {
							$('#ss_menu div i').addClass('ss_animate');
						} else {
							$('#ss_menu div i').removeClass('ss_animate');
						}
					});
			})

			})
function getuser() {
	// console.info("getuser 1");
	
	$.ajax({
		url : 'UserServlet',
		data : {
			action : "getUser"
		},
		dataType : "json",
		success : function(data, textStatus) {
			if (data.success) {
				var result = JSON.parse(data.result);

				// 邮编
				var zipcode_reg = /[1-9]{1}(\d+){5}/;

				$("#email").attr("value", result.email);
				$('input[id=email]').attr("readonly", "readonly");
				$("#profession").attr("value", result.profession);
				$('input[id=profession]').attr("readonly", "readonly");
				$("#phone").attr("value", result.phone);
				$('input[id=phone]').attr("readonly", "readonly");
				$("#address").attr("value", result.address);
				$('input[id=address]').attr("readonly", "readonly");

				if (zipcode_reg.test(result.zipcode)) {
					$("#zipcode").attr("value", result.zipcode);
				} else {
					$("#zipcode").attr("value", "100000");
				}

				$('input[id=zipcode]').attr("readonly", "readonly");
				$("#selflabel").attr("value", result.selflabel);
				$('input[id=selflabel]').attr("readonly", "readonly");
				$('#update').attr("type", "button");
				$('#updateuser').attr("type", "hidden");
				$('#back').attr("type", "hidden");
				if (result.sex == 0) {
					$("#img").attr("src", './image/male.png');
					$("#sex option[value='0']").attr("selected", "selected");
				} else if (result.sex == 1) {
					$("#img").attr("src", './image/female.png');
					$("#sex option[value='1']").attr("selected", "selected");
				}

			} else {
				if (data.message.indexOf(":") == -1) {
					console.log("信息：" + data.message);
				} else {
					var msg = data.message.split(":");
					console.log("出错信息:" + msg[1]);
				}

			}
		},
		error : function(XMLHttpRequest, textStatus) {
			console.log("请求UserServlet出错");
		}
	});
}

$('#updatepwd').click(function() {
	var ypassword = $('#ypassword').val();
	var npassword = $('#npassword').val();
	var cpa = $('#cpa').val();

	if (ypassword != npassword) {
		console.info("ypassword != npassword");
	}

	if (!checkupdatepwd(ypassword, npassword, cpa)) {
		// console.info(checkupdatepwd);
		layer.msg('请输入正确格式',{
			  icon: 2
			  ,shade: 0.01
			});
		return;
	}

	if (npassword == cpa && ypassword != npassword) {
		$.ajax({
			url : 'UserServlet?action=updatepwd',
			data : {
				ypwd : ypassword,
				npwd : npassword
			},
			dataType : "json",
			type : "post",
			success : function(data, textStatus) {
				if (data.success) {
					layer.msg('修改成功',{
						  icon: 1
						  ,shade: 0.01
						});
					$("input[type=reset]").trigger("click");
				} else {
					layer.msg('原始密码错误',{
						  icon: 2
						  ,shade: 0.01
						});
					$("input[type=reset]").trigger("click");
				}
			},
			error : function(XMLHttpRequest, textStatus) {
				console.log("请求UserServlet出错");
			}
		});
	} else {
		return;
	}

});

function checkupdatepwd(ypassword, npassword, cpa) {
	// console.info("0");
	var password_reg = /[a-zA-Z0-9]{6,20}$/;
	var flag = true;
	if (ypassword == "" && ypassword == undefined && npassword == ""
			&& npassword == undefined && cpa == "" && cpa == undefined) {
		// console.info("1");
		flag = false;
	} else if (password_reg.test(ypassword) && password_reg.test(npassword)
			&& password_reg.test(cpa) && cpa == npassword
			&& ypassword != npassword) {
		// console.info("2");
		flag = true;
	} else {
		// console.info("3");
		flag = false;
	}
	return flag;
}

$('#updatecert').click(function() {
	var ycert_type = $('#ycert_type').val();
	var ycert_no = $('#ycert_no').val();
	var ncert_type = $('#ncert_type').val();
	var ncert_no = $('#ncert_no').val();
	$.ajax({
		url : 'UserServlet?action=updatecert',
		data : {
			ycert_type : ycert_type,
			ycert_no : ycert_no,
			ncert_type : ncert_type,
			ncert_no : ncert_no
		},
		dataType : "json",
		type : "post",
		success : function(data, textStatus) {
			if (data.success) {
				layer.msg('修改成功',{
					  icon: 1
					  ,shade: 0.01
					});
				$("input[type=reset]").trigger("click");
			} else {
				layer.msg(data.result,{
					  icon: 2
					  ,shade: 0.01
					});
				$("input[type=reset]").trigger("click");
			}
		},
		error : function(XMLHttpRequest, textStatus) {
			console.log("请求UserServlet出错");
		}
	});

});

$('#updateuser').click(
		function() {
			var email = $('#email').val();
			var sex = $('#sex').val();
			var profession = $('#profession').val();
			var phone = $('#phone').val();
			var address = $('#address').val();
			var zipcode = $('#zipcode').val();
			var selflabel = $('#selflabel').val();
			
			if (!checkupdateuser(email, profession, phone, address, zipcode,
					selflabel)) {
				layer.msg('请输入正确格式',{
					  icon: 2
					  ,shade: 0.01
					});
				return;
			}

			$.ajax({
				url : 'UserServlet?action=updateuser',
				data : {
					email : email,
					sex : sex,
					profession : profession,
					phone : phone,
					address : address,
					zipcode : zipcode,
					selflabel : selflabel,
				},
				dataType : "json",
				type : "post",
				success : function(data, textStatus) {
					if (data.success) {
						layer.msg('修改成功',{
							  icon: 1
							  ,shade: 0.01
							});
						getuser();
					} else {
						layer.msg('出错',{
							  icon: 2
							  ,shade: 0.01
							});
						getuser();
					}
				},
				error : function(XMLHttpRequest, textStatus) {
					console.log("请求UserServlet出错");
				}
			});
		});

function checkupdateuser(email, profession, phone, address, zipcode, selflabel) {
	var email_reg = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
	var zipcode_reg = /[1-9]{1}(\d+){5}/;
	var phone_reg = /[0-9]*[1-9][0-9]*$/;// 正整数

	var flag = true;
	if (email == "" && email == undefined && profession == ""
			&& profession == undefined && phone == "" && phone == undefined
			&& address == "" && address == undefined && zipcode == ""
			&& zipcodee == undefined && selflabel == ""
			&& selflabel == undefined) {
		flag = false;
	} else if (email_reg.test(email) && zipcode_reg.test(zipcode)
			&& phone_reg.test(phone) && address != "" && address != undefined
			&& selflabel != "" && selflabel != undefined && profession != ""
			&& profession != undefined) {
		flag = true;
	} else {
		flag = false;
	}
	return flag;
}

$('#update').click(function() {

	$('input[id=email]').removeAttr("readonly");
	$('input[id=profession]').removeAttr("readonly");
	$('input[id=phone]').removeAttr("readonly");
	$('input[id=address]').removeAttr("readonly");
	$('input[id=zipcode]').removeAttr("readonly");
	$('input[id=selflabel]').removeAttr("readonly");
	$('#update').attr("type", "hidden");
	$('#updateuser').attr("type", "button");
	$('#back').attr("type", "button");
	console.log($("#email").parent());
	$("#email").parent().removeClass("has-success");
	$("#email").parent().removeClass("has-error");
});
$('#back').click(function() {

	// getuser();
	$('input[id=email]').attr("readonly", "readonly");
	$('input[id=profession]').attr("readonly", "readonly");
	$('input[id=phone]').attr("readonly", "readonly");
	$('input[id=address]').attr("readonly", "readonly");
	$('input[id=zipcode]').attr("readonly", "readonly");
	$('input[id=selflabel]').attr("readonly", "readonly");
	$('#update').attr("type", "button");
	$('#updateuser').attr("type", "hidden");
	$('#back').attr("type", "hidden");
	
	var index = layer.load(1, {
		  shade: [0.1,'#fff'] //0.1透明度的白色背景
		});
	setTimeout('delayer()', 200);
});
function delayer(){
	window.location.reload();
}

function listreserve(){
	
	$.ajax({
		url : 'UserServlet',
		data : {
			action : "reservehistory",
		},
		dataType : "json",
		success : function(data, textStatus) {
			if (data.success) {
				$('#accordion').empty();
				console.log(JSON.stringify(data));
				for(var i = 0;i<data.result.length;i++){
					var status ;
					if(data.result[i].audiu_status == "0"){//0==未审核
						status = '未审核';
					}else if(data.result[i].audiu_status == "1"){//1==同意
						status = data.result[i].reserve_no;
					}else if(data.result[i].audiu_status == "2"){//2==拒绝
						status = '拒绝';
					}
					var d = '<div class="panel panel-primary"><div class="panel-heading"><h4 class="panel-title"><span>单号：'+status+'</span><a class="pull-right" data-toggle="collapse" data-parent="#accordion" href="#'+i+'">详情</a></h4></div><div id="'+i+'" class="panel-collapse collapse">';
					
					for(var j = 0;j<data.resultdata.length;j++){
						if(data.rddata[j].tTReserve_id.id == data.result[i].id){
							d = d + '<div class="panel-body"><a href="./detail.html?id='+data.rddata[j].archive_id+'&cond='+data.rddata[j].archive_type+'"><span>'+data.resultdata[j].name+'</span></a></div>';

						}
					}
					d=d+'</div>';
					$('#accordion').append(d);
				}
			}
				
		},
		error : function(XMLHttpRequest, textStatus) {
			console.log("请求UserServlet出错");
		}
	});

}

function news(){
	
	$.ajax({
		url : 'UserServlet',
		data : {
			action : "systemnews",
		},
		dataType : "json",
		success : function(data, textStatus) {
			if (data.success) {
				$('#systemnews').empty();
				for(var i = 0;i<data.result.length;i++){
					var a ="";
					if(data.result[i].audiu_status == "1"){
						a = $('<li><h4>预约单审核信息</h4><p>&nbsp;&nbsp;&nbsp;<span><img src="image/go.png" alt="查看详情"/></span>您所预约的文档已被管理员审核，请在'+data.result[i].reserve_time+'凭借预约号：'+data.result[i].reserve_no+'进行预约借阅。</p><span>'+data.result[i].audiu_time+'</span></li>');
					}else if(data.result[i].audiu_status == "2"){
						a = $('<li><h4>预约单审核信息</h4><p>&nbsp;&nbsp;&nbsp;<span><img src="image/go.png" alt="查看详情"/></span>您所预约的文档已被管理员审核，审核结果被拒绝。</p><span>'+data.result[i].audiu_time+'</span></li>');
					}
					
					
					
					$('#systemnews').append(a);
				}
			}
				
		},
		error : function(XMLHttpRequest, textStatus) {
			console.log("请求UserServlet出错");
		}
	});

}

function user(){
	$("#userbody").show();
	$("#historybody").hide();
	$("#recordhistorybody").hide();
	$("#systemnewsbody").hide();

}
function history(){
	$("#userbody").hide();
	$("#historybody").show();
	$("#recordhistorybody").hide();
	$("#systemnewsbody").hide();
	visithistory();

}
function rdhistory(){
	$("#userbody").hide();
	$("#historybody").hide();
	$("#recordhistorybody").show();
	$("#systemnewsbody").hide();
	listreserve();

}

function systemnews(){
	$("#userbody").hide();
	$("#historybody").hide();
	$("#recordhistorybody").hide();
	$("#systemnewsbody").show();
	news();
}