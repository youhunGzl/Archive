/**
 * archive base library
 */
$.home = {
	load : function(){
		window.open("http://localhost:8080//Archive/index.html","_parent");  
	}
  };
 //对象级插件：DOM对象的hashchange事件处理
$.fn.extend({
	hashchange: function(callback) {
        this.bind('hashchange', callback);
        if(location.hash) //if location.hash is not empty,fire the event when load,make ajax easy
        {
            callback();
        }
    }


});
	
	


