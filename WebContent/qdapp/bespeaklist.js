$(function(){
				$(":checkbox").iCheck({
					checkboxClass: 'icheckbox_flat-blue',
					increaseArea: '20%'

				});
				$("#choice").on('ifChecked', function(event){
				  $(":checkbox").iCheck('check');
				});
				$("#choice").on('ifUnchecked', function(event){
				  $(":checkbox").iCheck('uncheck');
				});
			});