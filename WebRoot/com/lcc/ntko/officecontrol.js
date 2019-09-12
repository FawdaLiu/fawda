;(function($, window, undefined){
	if(typeof window.RS$ == 'undefined'){
		window.RS$ = {};
	}
	var RS$ = window.RS$;
	
	var _DEFAULT_WINDOW_OPTIONS = {
		id : "TANGER_OCX"
	}
	

	var remoteRoot;
	if (typeof location.origin === 'undefined') {// 兼容ie8
		remoteRoot = location.protocol + '//' + location.host;
	} else {
		remoteRoot = window.location.origin;
	}
	var sessionUrl = "?ERROR.TYPE=jsn_msg&CMD=", ufUrl = sessionUrl + "UF", udfUrl = sessionUrl
			+ "UDF", dfUrl = sessionUrl + "DF";
	var TANGER_OCX_OBJ = document.getElementById("TANGER_OCX");
	TANGER_OCX_OBJ.IsUseUTF8Data = true;
	$.extend(RS$, {
		_initUrl : function(url){
			if(url){
				var newUrl = url;
				if(url.indexOf("?") > -1){
					newUrl = remoteRoot + url.substring(0, url.indexOf("?"));
					newUrl += ";jsessionid=" + sessionId;
					newUrl += url.substring(url.indexOf("?"), url.length);
					
				} else {
					newUrl += ";jsessionid=" + sessionId;
				}
				return newUrl;
			}
		},
		isExist : function () {
			if(TANGER_OCX_OBJ){
				return true;
			}
			return false;
		},
		TANGER_OCX_OBJ : TANGER_OCX_OBJ,
		loadFile : function(URL, readOnly){
			URL = this._initUrl(URL);
			readOnly = readOnly ? true : false;
			this.TANGER_OCX_OBJ.OpenFromURL(URL, false, false);//参数：URL,是否显示进程,是否只读
			if(readOnly){
				this.TANGER_OCX_OBJ.SetReadOnly(true,"");
				this.TANGER_OCX_OBJ.Menubar = false;
				this.TANGER_OCX_OBJ.ToolBars = false;
			}
		},
		saveFile : function(URL, FileFieldName, CPARA, FileName, HTMLForm){
			return this.TANGER_OCX_OBJ.SaveToURL(this._initUrl(URL), FileFieldName, CPARA, FileName, HTMLForm);
		}
	});
	
})(jQuery, window);

