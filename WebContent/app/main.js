/**
 * 菜单选项处理
 */
$(function(){
	//页面刷新时，返回首页	
	window.onunload = function()  ////document.body.onunload = function ()
    {	 	   
       $.home.load();       
    };
    //页面前进或后退时，跳转到不同页面
	var oldhref = location.hash;
	$(window).hashchange(function(){	    //插件base.js
		if(oldhref==location.hash) return;  //退回操作时location.hash不变，则不再刷新
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