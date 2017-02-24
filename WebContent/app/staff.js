/**
 * 工作人员信息处理
 */
function Staff(){	
	this.init = function(){
		var titles = ['用户名','密码','员工编号','真实姓名','权限','所在档案馆'];
		this.setMaintence('#oModify');
		this.setQuery('#oSearch');		
		this.setTable('#oTable',titles);
		this.bindEvent($('#sBtnSearch'),"click",this.sSearchHandler);				
	};	
	//操作按钮
	this.setMaintence = function(element){
		var sButton = $("<button type='button' id='sbtn_modify' class='btn btn-sm btn-info'>" +
		"<span style='color:3C71FC;' class='glyphicon glyphicon-edit'></span> 修改</button> ");
		$(element).empty();
		$(element).append(sButton);	
	};
	//查询文本框
	this.setQuery = function(element){
		var sQuery = $("<input  class='span3' id='sTxtCondition' type='text'  placeholder='请输入用 户姓名'>"+
					"<button class='add-on' type='button' id='sBtnSearch'>搜索</button>");
		$(element).empty();
		$(element).append(sQuery);
	};
	//信息列表
	this.setTable = function(element,titles){
		var sTable = $("<table class='table table-hover table-bordered'>");		
		sTable.attr('id','tblStaffInfo');		
		var sTr = $("<tr></tr>");
		for(var i=0;i<titles.length;i++){
			var td = $("<th>"+titles[i]+"</th>");
			td.appendTo(sTr);
		}		
		sTr.appendTo(sTable);		
		$("</table>").appendTo(sTable);
		$(element).empty();
		$(element).append(sTable);		
	};
	//绑定事件
	this.bindEvent = function(element, eventName, func){
		element.bind(eventName,func);			
	};
	//事件处理程序
	this.sSearchHandler = function(){		
		var tstaff = {
					realName:encodeURIComponent($('#sTxtCondition').val())
				};
		var cond = JSON.stringify(tstaff);		
		$.ajax({
			url:'SearchServlet',
			data:{action:"getByCond",params: cond},
			dataType:"json",
			success:function(data,textStatus){
				if(data.success){
					$('#tblStaffInfo tr:gt(0)').remove();
					for(var i=0;i<data.result.length;i++){
						var td1 = $("<td>"+data.result[i].suserName +"</td>");
						var td2 = $("<td>"+data.result[i].staffPwd+"</td>");
						var td3 = $("<td>"+data.result[i].staffNo+"</td>");
						var td4 = $("<td>"+data.result[i].realName+"</td>");
						var td5 = $("<td>"+data.result[i].staffGrant+"</td>");
						var td6 = $("<td>"+data.result[i].unitCode+"</td>");						
						var tr = $("<tr></tr");
						tr.append(td1);
						tr.append(td2);
						tr.append(td3);
						tr.append(td4);
						tr.append(td5);
						tr.append(td6);
						$('#tblStaffInfo').append(tr);						
					}
				}else{
					console.log("信息：" + data.message);					
				}
			},
			error:function(XMLHttpRequest, textStatus){
				console.log("请求SearchServlet出错");
			}
		});
	};
	
}