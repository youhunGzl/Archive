	$(function() {
				
				$("[data-toggle='tooltip']").tooltip();
				$("form :input").blur(function() {
					var str = "#" + $(this).attr("id");
					var $parent = $(this).parent();
					if($(this).is(str)) {
						if(this.value == "") {
							$parent.nextAll("div").children("button").addClass("disabled");
							$parent.nextAll("div").children("button").prop("disabled", true);
							$parent.addClass("has-error");
						} else {
							$parent.nextAll("div").children("button").removeClass("disabled");
							$parent.nextAll("div").children("button").removeProp("disabled", true);
							$parent.removeClass("has-error");
							$parent.addClass("has-success");
						}
					}
				});
				function getNowFormatDate() {
				        var date = new Date();
				        var seperator1 = "-";
				        var year = date.getFullYear();
				        var month = date.getMonth() + 1;
				        parseInt(month,10);
				        var strDate = date.getDate()+3;
				       var term1=year%4==0;
				       var term2=year%100!=0;
				       var term3=year%400==0;
				       var term=term1&&term2||term3;
				        
				       
				        if(month>12){
				        	month="1";year+=1;
				        }
				        if(month == 1 || month ==3 || month ==5 || month ==7 ||month == 8 || month==10 && strDate>=32){
				        	month += 1;strDate=strDate-31;
				        }else if(month==12 && strDate>=32){
				        	year+=1;month="1";strDate=strDate-31;
				        }else if( month == 2 && term && strDate >= 28){
				        	month="3";strDate=strDate-28;
				        }else if(month == 2 && !term&& strDate >= 27){
				        	month="3";strDate=strDate-27;
				        }else if(month ==4 || month == 6 ||month == 9 ||month ==11 && strDate >=31){
				        	month+=1;strDate=strDate-30;
				        }
				         if (strDate >= 0 && strDate <= 9) {
				            strDate = "0" + strDate;
				        }
				       	if (month >= 1 && month <= 9) {
				            month = "0" + month;
				        }
				        var currentdate = year + seperator1 + month + seperator1 + strDate;
				        var t = currentdate.toString();
				        return t;
				    }
				console.log(getNowFormatDate());

				$('.form_date').datetimepicker({
					minView: 'month',
					format:"yyyy-mm-dd",
			        language:  'fr',
			        weekStart: 1,
			        todayBtn:  1,
					autoclose: true,
					todayHighlight: 1,
					startView: 2,
					forceParse:true,
					forceParse: 0,
					startDate:getNowFormatDate()
			    });
			jQuery(document).ready(function() {
				getleftnav();
				rightnav();
			});
		})