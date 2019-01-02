(function($) {  
	$.extend({    
		getURLParam: function(m) {
			var sValue = location.search.match(new RegExp("[\?\&]" + m + "=([^\&]*)(\&?)", "i"));      
			return sValue ? sValue[1] : sValue;    
		},
	    saveURLParam: function(url, name, value) { 
		debugger   
			var r = url;      
			if(r != null && r != 'undefined' && r != "") {        
				value = encodeURIComponent(value);        
				var reg = new RegExp("(^|)" + name + "=([^&]*)(|$)");        
				var tmp = name + "=" + value;        
				if(url.match(reg) != null) {          
					r = url.replace(eval(reg), tmp);        
				}        
				else {          
					if(url.match("[\?]")) {            
						r = url + "&" + tmp;          
					} else {            
						r = url + "?" + tmp;          
					}        
				}      
			}      
			return r;    
		},
		isEmpty:function(v){
			switch (typeof v) {
		    case 'undefined':
		        return true;
		    case 'string':
		        if (v.replace(/(^[ \t\n\r]*)|([ \t\n\r]*$)/g, '').length == 0) return true;
		        break;
		    case 'boolean':
		        if (!v) return true;
		        break;
		    case 'number':
		        if (0 === v || isNaN(v)) return true;
		        break;
		    case 'object':
		        if (null === v || v.length === 0) return true;
		        for (var i in v) {
		            return false;
		        }
		        return true;
		    }
		    return false;
		}
	});
})(jQuery);