			$(function(){
				$(":radio").iCheck({
					radioClass: 'iradio_flat-blue',
					increaseArea: '20%'
				});
				$(":checkbox").iCheck({
					checkboxClass: 'icheckbox_flat-blue',
					increaseArea: '20%'

				});
				var stage=$(".findstage").children();
				console.log(stage.length);
				var filter=$(".filter");
				filter.hide();
				$(".findstage").find("input").on('ifChecked',function(event){
					var index=$(".findstage").find("input").index(this);
					filter[index].style.display="block";
					if(index == 0){
						var textval = document.getElementById("tran");
						textval.value = "file";
						console.log(textval.value);
					}else if(index == 1){
						var textval = document.getElementById("tran");
						textval.value = "material";
						console.log(textval.value);
					}else if(index == 2){
						var textval = document.getElementById("tran");
						textval.value = "multimedia";
						console.log(textval.value);
					}
				});
				 $(".findstage").find("input").on('ifUnchecked',function(event){
				 	var index=$(".findstage").find("input").index(this);
					filter[index].style.display="none";
				 });
				$('.form_date').datetimepicker({
			        language:  'fr',
			        weekStart: 1,
			        todayBtn:  1,
					autoclose: 1,
					todayHighlight: 1,
					startView: 2,
					minView: 2,
					forceParse: 0
			    });

			});

			$(function () { $('#collapseFour').collapse({
				toggle: false
			});});
			$(function () { $('#collapseFive').collapse({
				toggle: false
			});});
			$(function () { $('#collapseTwo').collapse('hide');});
			$(function () { $('#collapseThree').collapse('hide');});
			$(function () { $('#collapseOne').collapse('show');});
			$(function() {
				$(document).scroll(function() {
					if($(window).scrollTop() == 0) {
						$("#amdee").addClass("hide");
					} else if($(window).scrollTop() > 100) {
						$("#amdee").removeClass("hide");
					}
				});
			});
			
			jQuery(document).ready(function(){
				var thisURL = document.URL;    
				var  a =thisURL.split('?')[1]; 
				if(a != null&& a != ""){
					var  b =a.split('&')[0];  
					var  c =a.split('&')[1];  
					var type = b.split("=")[1];
					var key = decodeURI(c.split("=")[1]);
					$(".keywork").val(key);
					var tran =  document.getElementById("tran");
					var keywork =  document.getElementById("keywork");
				
					tran.value = type;
					if(type == "file"){
						$("input[type='radio']:first").iCheck('check');
						
					}else if(type == "material"){
						$("#material").iCheck('check');
						
					}else if(type == "multimedia"){
						$("#multimedia").iCheck('check');
						
					}
					
					keywork.value = key;
				}
				
				
				getleftnav();
				getall();
				getcatalog();
				rightnav();
			});
