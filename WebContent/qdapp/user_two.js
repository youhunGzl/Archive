$(function() {
		$('#collapseFour').collapse({
			toggle : false
		});
		$('#collapseFive').collapse({
			toggle : false
		});
		$('#collapseTwo').collapse('hide');
		$('#collapseThree').collapse('hide');
		$('#collapseOne').collapse('show');
		$(document).scroll(function() {
					if ($(window).scrollTop() == 0) {
						$("#amdee").addClass("hide");
					} else if ($(window).scrollTop() > 500) {
						$("#amdee").removeClass("hide");
					}
				});
		var d=new Date();
		var year = d.getFullYear()+"-";
		var month= d.getMonth()+1 +"-";
		var day=d.getDate()+"";
		var logintime=year+month+day;
		$(".logintime").text("登陆时间"+logintime);
		var lis = $(".personal").children();
		var changes = $(".changes");
		function hide() {
			for (var i = 0; i < changes.length; i++) {
				changes[i].className = "hide";
			}
		}
		function actives() {
			for (var i = 0; i < lis.length; i++) {
				lis[i].className = "";
			}
		}
		hide();
		changes[0].className = "show changes";
		actives();
		lis[0].className = "active";
		$(".personal").children().click(function() {
			var index = $(".personal").children().index(this);
			console.log(index);
			actives();
			lis[index].className = "active";
			hide();
			if(index>=3){
				index=index-3;
				changes[index].className = "show changes";
			}else{
				changes[index].className = "show changes";
			}
		});
	});

	$("#email")
			.blur(
					function() {
						var email = $("#email").val();
						var email_reg = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;

						if (!email_reg.test(email)) {
							$("#email").parent().removeClass("has-success");
							$("#email").parent().addClass("has-error");
						} else {
							$("#email").parent().removeClass("has-error");
							$("#email").parent().addClass("has-success");
						}
					});
	$("#phone").blur(function() {
		var phone = $("#phone").val();
		var phone_reg = /[0-9]*[1-9][0-9]*$/;//正整数

		if (!phone_reg.test(phone)) {
			//console.info("err y" + ypassword);
			$("#phone").parent().removeClass("has-success");
			$("#phone").parent().addClass("has-error");
		} else {
			//console.info("suc y" + ypassword);
			$("#phone").parent().removeClass("has-error");
			$("#phone").parent().addClass("has-success");
		}
	});
	$("#zipcode").blur(function() {
		var zipcode = $("#zipcode").val();
		var zipcode_reg = /[1-9]{1}(\d+){5}/;
		if (zipcode != "" && zipcode != undefined) {
			if (!zipcode_reg.test(zipcode)) {
				$("#zipcode").parent().removeClass("has-success");
				$("#zipcode").parent().addClass("has-error");
			} else {
				$("#zipcode").parent().removeClass("has-error");
				$("#zipcode").parent().addClass("has-success");
			}

		}
	});
	$("#profession").blur(function() {
		if (profession != "" && profession != undefined) {
			//console.info("1");
			//console.info("err y" + ypassword);
			$("#profession").parent().removeClass("has-error");
			$("#profession").parent().addClass("has-success");
		} else {
			//console.info("suc y" + ypassword);
			$("#profession").parent().removeClass("has-success");
			$("#profession").parent().addClass("has-error");
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
	$("#selflabel").blur(function() {
		var selflabel = $("#selflabel").val();
		if (selflabel != "" && address != undefined) {
			$("#selflabel").parent().removeClass("has-error");
			$("#selflabel").parent().addClass("has-success");
		} else {
			$("#selflabel").parent().removeClass("has-success");
			$("#selflabel").parent().addClass("has-error");
		}
	});

	$("#ypassword").blur(function() {
		var ypassword = $("#ypassword").val();
		//var username_reg = /[a-zA-Z][a-zA-Z0-9]{2,15}$/;
		var password_reg = /[a-zA-Z0-9]{6,20}$/;
		//console.info("doing");

		if (ypassword != "" && ypassword != undefined) {
			//console.info("1");
			if (!password_reg.test(ypassword)) {
				//console.info("err y" + ypassword);
				$("#ypassword").parent().removeClass("has-success");
				$("#ypassword").parent().addClass("has-error");
			} else {
				//console.info("suc y" + ypassword);
				$("#ypassword").parent().removeClass("has-error");
				$("#ypassword").parent().addClass("has-success");
			}

		}
	});
	$("#npassword").blur(function() {
		var ypassword = $("#ypassword").val();
		//var username_reg = /[a-zA-Z][a-zA-Z0-9]{2,15}$/;
		var npassword = $("#npassword").val();
		var password_reg = /[a-zA-Z0-9]{6,20}$/;
		if (npassword != "" && npassword != undefined) {
			if (!password_reg.test(npassword)) {
				//console.info("err n" + npassword);
				$("#npassword").parent().removeClass("has-success");
				$("#npassword").parent().addClass("has-error");
			} else {
				//console.info("suc n" + npassword);
				$("#npassword").parent().removeClass("has-error");
				$("#npassword").parent().addClass("has-success");
			}
			if (npassword == ypassword) {
				$("#ypassword").parent().removeClass("has-error");
				$("#ypassword").parent().addClass("has-success");

				$("#npassword").parent().removeClass("has-");
				$("#npassworderror").parent().addClass("has-success");
			}
		}
	});
	$("#cpa").blur(function() {
		var npassword = $("#npassword").val();
		var cpa = $("#cpa").val();
		var password_reg = /[a-zA-Z0-9]{6,20}$/;

		if (cpa != "" && cpa != undefined) {
			if (!password_reg.test(cpa)) {
				//console.info("err cpa" + cpa);
				$("#cpa").parent().removeClass("has-success");
				$("#cpa").parent().addClass("has-error");
			} else {
				//console.info("suc cpa" + cpa);
				$("#cpa").parent().removeClass("has-error");
				$("#cpa").parent().addClass("has-success");
			}
			if (cpa != npassword) {
				$("#npassword").parent().removeClass("has-success");
				$("#npassword").parent().addClass("has-error");
				$("#cpa").parent().removeClass("has-success");
				$("#cpa").parent().addClass("has-error");
			}
		}
	});
	$("#ycert_no").blur(function() {
		//var ycert_type=$("#ycert_type").val();
		var ycert_no = $("#ycert_no").val();

		if (ycert_no != "" && ycert_no != undefined) {
			//console.info("1");
			//console.info("err y" + ypassword);
			$("#ycert_no").parent().removeClass("has-error");
			$("#ycert_no").parent().addClass("has-success");
		} else {
			//console.info("suc y" + ypassword);
			$("#ycert_no").parent().removeClass("has-success");
			$("#ycert_no").parent().addClass("has-error");
		}

	});
	$("#ncert_no").blur(function() {
		//var ncert_type=$("#ncert_type").val();
		var ncert_no = $("#ncert_no").val();
		if (ncert_no != "" && ncert_no != undefined) {
			//console.info("1");
			//console.info("err y" + ypassword);
			$("#ncert_no").parent().removeClass("has-error");
			$("#ncert_no").parent().addClass("has-success");
		} else {
			//console.info("suc y" + ypassword);
			$("#ncert_no").parent().removeClass("has-success");
			$("#ncert_no").parent().addClass("has-error");
		}
		
	});
	
	if (navigator.userAgent.match(/IEMobile\/10\.0/)) {
		  var msViewportStyle = document.createElement('style');
		  msViewportStyle.appendChild(
		    document.createTextNode(
		      '@-ms-viewport{width:auto!important}'
		    )
		  );
		  document.querySelector('head').appendChild(msViewportStyle);
		}
		$(function () {
		  var nua = navigator.userAgent;
		  var isAndroid = (nua.indexOf('Mozilla/5.0') > -1 && nua.indexOf('Android ') > -1 && nua.indexOf('AppleWebKit') > -1 && nua.indexOf('Chrome') === -1);
		  if (isAndroid) {
		    $('select.form-control').removeClass('form-control').css('width', '100%');
		  }
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
					});
					$(function(){
					$(".leftdao").children().mouseenter(function(){
						$(this).addClass("adds");
					});
					$(".leftdao").children().mouseleave(function(){
						$(this).removeClass("adds");
					});	
				});
			});