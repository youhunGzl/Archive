/**
 * �˵�ѡ���
 */
$(function(){
	//ҳ��ˢ��ʱ��������ҳ	
	window.onunload = function()  ////document.body.onunload = function ()
    {	 	   
       $.home.load();       
    };
    //ҳ��ǰ�������ʱ����ת����ͬҳ��
	var oldhref = location.hash;
	$(window).hashchange(function(){	    //���base.js
		if(oldhref==location.hash) return;  //�˻ز���ʱlocation.hash���䣬����ˢ��
		oldhref = location.hash;
		var hashUrl = location.hash.replace('#','');					
		if(typeof pageContent==undefined){
			console('pageContent is not exist');
			return;
		}
		pageContent.init(hashUrl);			
	});	
	
	var pageContent = {
			init:function(options){
				if(options=='sSearch'){
					this.enterSearch();
				}else if(options=='sModify'){
					this.enterModify();
				}
			},
			enterSearch:function(){
				if(typeof staff==undefined){
					console("staff is not exist");					
				}
				new Staff().init();
			},
			enterModify:function(){
				
			}
		};	

	
	
	
	
});