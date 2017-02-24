$(function() {
				$(document).scroll(function() {
					if($(window).scrollTop() == 0) {
						$("#amdee").addClass("hide");
					} else if($(window).scrollTop() > 100) {
						$("#amdee").removeClass("hide");
					}
				});
				$(".section").hide();
				$(".section:first").show();
				$(".navigationTwo").children().click(function(){
					var index=$(".navigationTwo").children().index(this);console.log(index);
					index+=2;
					$(".section").hide();
					$(".section:nth-of-type("+index+")").show();
				});
				$(".answer").hide();
				$(".answer:first").show();
				$(".question_navigation li:first").css({"border-bottom":"3px solid rgb(34,167,240)","color":"rgb(34,167,240)"});
				$(".question_navigation").children().click(function(){
					var index=$(".question_navigation").children().index(this);
					$(".answer").hide();
					$(".question_navigation li").css({"border":"0px","color":"#555"});
					$(this).css({"border-bottom":"3px solid #69f","color":"rgb(34,167,240)"});
					index += 2;
					console.log(index);
					$(".answer:nth-of-type("+index+")").show();
				});
				
				jQuery(document).ready(function() {
					getleftnav();
					rightnav();
				});
			})
