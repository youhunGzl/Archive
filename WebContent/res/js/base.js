/**
 * archive base library
 */
$.home = {
	load : function(){
		window.open("http://localhost:8080//Archive/index.html","_parent");  
	}
  };
 //���󼶲����DOM�����hashchange�¼�����
$.fn.extend({
	hashchange: function(callback) {
        this.bind('hashchange', callback);
        if(location.hash) //if location.hash is not empty,fire the event when load,make ajax easy
        {
            callback();
        }
    }


});
	
	


