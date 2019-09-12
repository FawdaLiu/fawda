/*
 * cssdoc v1.0 Copyright(c) 2014 by Css Team
 * $Id: cssdoc.js,v 1.0 2014/07/29 XiaoLi$
 * 
	boolean init();//5.1.1	插件初始化
    boolean createDocument(string type);//5.2.1	创建空文件
    boolean openDocument(string fileName, boolean readOnly);//5.2.2	打开本地文档
    boolean openDocumentRemote(string URL, boolean readOnly);//5.2.3	打开远程文档
    boolean saveAs(string fileName);//5.2.4	保存到本地5.2.5	通过对话框保存到本地
    boolean saveURL(string URL, string fileName);//5.2.6	保存到远程
    boolean close();//5.2.9	关闭
    boolean insertDocumentField(string id);//5.6.1	公文域增加
    boolean setDocumentField(string id, string value);//5.6.2	公文域内容修改
    boolean enableProtect(boolean flag) ; //设置true-->只读
    
*/  
C = cssDoc = (function( $, window, undefined ){
//	var appWpsjs;
//	var docWpsjs;
	var  MODE = 'liushi',
	COMPANY = 'NEO',
	objId = 'objId',
	objW = '100%',
	objH = '610',
	obj,
	app,
	state = 0,
	_webRoot,
	_args;
	_getWebRoot = function() {
		if (_webRoot != null) {
			return _webRoot;
		}
		$("script").each(function(){
			var src = $(this).attr("src");
			if(src && src.indexOf("cssdoc")>-1){
				index = src.indexOf("doc");
				_webRoot = src.substring(0, index);
				_args = src.split('?')[1];
				return _webRoot;
			}
		});
	};
	_getArgs = function( name )
	{
	    if( _args )
		{
		    var p = _args.split('&'), i = 0, l = p.length, a;
			for( ; i < l; i++ )
			{
			    a = p[i].split('=');
				if( name === a[0] ) return a[1];
			}
		}
		return null;
	};
	_loadArgs = function(){
		MODE = _getArgs('m');
		COMPANY = _getArgs('c');
		objId = _getArgs('id');
	};
	_initH = function(){
		if(!! navigator.userAgent.match(/MSIE/)) {
			objH = document.documentElement.clientHeight - '40';
		} else {
			objH = window.innerHeight - '40';
		}
	};
	_skLoad = function(){
		try{
			obj = suwell.ofdReaderInit(objId, objW, objH);
			if (obj) 
			{
				//obj.setCompsiteVisible([ "menu" ], false);
				obj.setCompositeVisible("saveas",true);
				state = 1;
			} else {
				alert("请确认是否正确安装了数科阅读器，并检查浏览器设置是否正确!");
			}
		}catch(e){
			alert(e);
		}
	};
	
	
	//wps全屏显示
	showFullSrc=function(){
		//obj.ActiveWindow.ActivePanel.View.FullScreen=true;
	};
	
	//新建文档
	onNew=function(){
		obj.createDocument("uot");
	};
	//通过对话框保存到本地
	saveLocal=function(){
		obj.saveAs("D:\\ccc.wps");
	};
	//通过对话框打开本地文件
	openLocal=function(){
		//obj.openDocument();
	};
	
	//显示誊清稿
	showTqg=function(){
		//obj.showRevision(2);
		if(navigator.userAgent.indexOf("MSIE") > 0) {
			obj.showRevision(2);
		}else{
			app.showRevision(2);
			app.Refresh();//linux需要刷新一下
		}
	};
	//显示留痕稿
	showLh=function(){
		if(navigator.userAgent.indexOf("MSIE") > 0) {
			obj.showRevision(0);
		}else{
			app.showRevision(0);
			app.Refresh();//linux需要刷新一下
		}
	};
	
	_fxLoad = function(){
		if(navigator.userAgent.indexOf("MSIE") > 0) {
			try{
				obj = OFD.OCX.init(objId, objW, objH);
				obj.ready(obj._id);
				state = 1;
			}catch(e){
				alert(e);
			}
		} else {createDocument
			obj = foxit.ofdReaderInit(objId, objW, objH);
			if (obj) 
			{
				//ocx.setCompsiteVisible([ "menu" ], false);
				state = 1;
			}
		}
	};
	_wpsLoad = function(){
		if(navigator.userAgent.indexOf("MSIE") > 0) {
			function init(tagID, width, height) {
				var iframe;
				var obj;
				iframe = document.getElementById(tagID);
				var codes=[];   
				codes.push('<object id=DocFrame1 height=' + height + ' width=' + width + ' ');
				codes.push('data=data:application/x-oleobject;base64,7Kd9juwHQ0OBQYiirbY6XwEABAA7DwMAAgAEAB0AAAADAAQAgICAAAQABAD///8ABQBcAFgAAABLAGkAbgBnAHMAbwBmAHQAIABBAGMAdABpAHYAZQBYACAARABvAGMAdQBtAGUAbgB0ACAARgByAGEAbQBlACAAQwBvAG4AdAByAG8AbAAgADEALgAwAAAA ');
				codes.push('classid=clsid:8E7DA7EC-07EC-4343-8141-88A2ADB63A5F viewastext=VIEWASTEXT></object> ');
				iframe.innerHTML = codes.join("");
				obj = document.getElementById("DocFrame1");
				return obj;
			}
			obj = init(objId, objW, '698');
//			setTimeout(function() {
//				if(workitemid==''||isLiuhen==0){
//					//如果是起草第一个环节不需要留痕，显示誊清稿
//					obj.showRevision(2);
//				}
//				if((activityName=='SEC_2')||(activityName=='FINISH_DOC')||(activityName=='dengJiTaoHongTou')||(activityName=='DIANZIJIAODUI')){
//					//如果是登记套红头环节或者电子校对，默认显示誊清稿
//					obj.showRevision(2);
//				}
//			},1000);
			state = 1;
		} else {
			function init2(tagID) {
				var iframe;
				var obj;
				iframe = document.getElementById(tagID);
				var codes=[];   
				codes.push("<object wpsextpara='-w' id=DocFrame1 type='application/x-wps'  data='../Normal.wpt'  width='950px;'  height='698px;'> <param name='Enabled' value='1' /> </object>");
				iframe.innerHTML = codes.join("");
				obj = document.getElementById("DocFrame1");
				return obj;
			}
			obj = init2(objId);
//			setTimeout(function() {
//				if(workitemid==''||isLiuhen==0){
//					//如果是起草第一个环节不需要留痕，显示誊清稿
//					app.showRevision(2);
//				}
//				if((activityName=='SEC_2')||(activityName=='FINISH_DOC')||(activityName=='dengJiTaoHongTou')||(activityName=='DIANZIJIAODUI')){
//					//如果是登记套红头环节或者电子校对，默认显示誊清稿
//					app.showRevision(2);
//				}
//			},1000);
			state = 1;
		}
	};
	_yozoLoad = function(){
		try{
			obj = init(objId, objW, objH);
		}catch(e){
		}
		state = 1;
	};
	_neoLoad = function(){
		try{
			obj = init(objId, objW, objH);
		}catch(e){
		}
		state = 1;
	};
	return {
		init:function(mode, company){
			_getWebRoot();
			_loadArgs();
			if(MODE == 'liushi') {
				if(COMPANY == 'NEO') {
					$("head").append('<link href="'+_getWebRoot()+'/doc/NEO/style1.css" rel="stylesheet" type="text/css">');
					$.getScript(_getWebRoot()+"/doc/NEO/office.js", function(){
						setTimeout(function(){
							_initH();
							_neoLoad();
						},200)
					});
				} else if(COMPANY == 'Yozo') {
					$.getScript(_getWebRoot()+"/doc/Yozo/js/yozooffice.js", function(){
						setTimeout(function(){
							_initH();
							_yozoLoad();
						},200)
					});
				} else if(COMPANY == 'WPS') {
					setTimeout(function(){
						_initH();
						_wpsLoad();
					},200)
				}
			} else if(MODE == 'banshi') {
				if(COMPANY == 'FX') {
					if(navigator.userAgent.indexOf("MSIE") > 0) {
						$.getScript(_getWebRoot()+"/doc/FX/foxit.ofd.ocx1.js", function(){
							_initH();
							_fxLoad();
						});
					} else {
						$.getScript(_getWebRoot()+"/doc/FX/Foxit_npapi.js", function(){
							setTimeout(function(){
								_initH();
								_fxLoad();
							},200)
						});
					}
				} else if(COMPANY == 'SK') {
					if(navigator.userAgent.indexOf("MSIE") > 0) {
						$.getScript(_getWebRoot()+"/doc/SK/suwell_activex.js", function(){
							_initH();
							_skLoad();
						});
					} else {
						$.getScript(_getWebRoot()+"/doc/SK/suwell_npapi.js", function(){
							setTimeout(function(){
								_initH();
								_skLoad();
							},200)
						});
					}
				}
			}
		},
		createDocument:function(userName){
			var check = setInterval(function(){
				if(state == 1){
					clearInterval(check);
					if(MODE == 'liushi') {
						if(COMPANY == 'NEO') {
							 obj.createDocument('');
						} else if(COMPANY == 'Yozo') {
							try{
								obj = obj.createDocument("uot");
							}catch(e){
							}
						} else if(COMPANY == 'WPS') {
							if(navigator.userAgent.indexOf("MSIE") > 0) {
							    obj.createDocument("uot");
							    obj.setUserName(userName) ;
							    obj.enableRevision(false);
//							    obj.showRevision(2);
							} else {
								var Interval_control = setInterval(
									function(){
										app = obj.Application;
										if(app && app.IsLoad()){
											clearInterval(Interval_control);
											app.createDocument("uot");
											app.setUserName(userName) ;
											app.enableRevision(false);
//												app.enableRevision(true);
//												app.Refresh();
//												app.showRevision(2);
										}
								},200);

							}
						}
					} else if(MODE == 'banshi') {
						if(COMPANY == 'FX') {
						}
					}
				}
			},200);
		},
		openDocument:function(){
			if(navigator.userAgent.indexOf("MSIE") > 0){
			}else{
				app.openDocument("", false);
			}
		},
		insertDocument:function(mm,url){
			if(navigator.userAgent.indexOf("MSIE") > 0){
				obj.cursorToDocumentField(mm,1);
				obj.DocObj.Application.Selection.InsertBreak(3);
				obj.insertDocument(mm,url);
			}else{
				app.cursorToDocumentField(mm,1);
				app.Selection.InsertBreak(3);
				app.insertDocument(mm,url);
			}
		},
		openDocumentRemote:function(url, readOnly, userName){
			var check = setInterval(function(){
				if(state == 1){
					clearInterval(check);
					if(obj == null)
						return;
					if(MODE == 'liushi') {
						if(COMPANY == 'NEO') {
							obj.openDocument(url,readOnly);
						} else if(COMPANY == 'Yozo') {
							try{
								ret = obj.openDocumentRemote(url,readOnly);
							}catch(e){
							}
						} else if(COMPANY == 'WPS') {
							url += '&rnd='+Math.random();
							//arm环境下和window环境下  >0(window环境下)
							if(navigator.userAgent.indexOf("MSIE") > 0) {
								obj.openDocumentRemote(url, readOnly);
								
//								objWpsjs = new Wps(obj);
								
								if(readOnly){
									obj.setToolbarAllVisible(false);
								}
								if(userName=='tht'){
									obj.enableRevision(false);
									obj.showRevision(2);
								}else{
									if((activityName=='SEC_2')||(activityName=='dengJiTaoHongTou')||(activityName=='dengJiTaoHongTou1')){
										obj.enableRevision(false);
										obj.showRevision(2);
									}else{
										obj.enableRevision(true);
									}
									if((activityName=='FORMULATE_DOC')||(activityName=='GAIZHANGFAWEN')||(activityName=='GAIZHANGFAWEN1')||(activityName=='FINISH_DOC')||(activityName=='DIANZIJIAODUI')||(activityName=='DIANZIJIAODUI1')|| (activityName =='JBQZ') || (activityName =='XJQZ')  || (activityName =='Check_dzgz') ||(activityName =='Check_gzhang')){
										//如果是登记套红头环节或者电子校对，默认显示誊清稿
										//lhxj Check_gzhang
										//xj  XJQZ
										//jb JBQZ
										//sw Check_dzgz
										obj.showRevision(2);
										obj.ActiveDocument.AcceptAllRevisions();
										if(activityName!='DIANZIJIAODUI1'&&activityName!='DIANZIJIAODUI'&&activityName!='FINISH_DOC'){
											obj.enableProtect(true);
											obj.setToolbarAllVisible(false);
										}
									}
								}
								obj.setUserName(userName) ;
								var value = obj.ActiveDocument.Application.OfdExportOptions;
								value.SelectServiceProvider = 1;
							} else {
								var Interval_control = setInterval(
										function(){
											app = obj.Application;
											if(app && app.IsLoad()){
												clearInterval(Interval_control);
												app.openDocumentRemote(url, readOnly);
												
//												appWpsjs = new Wps(app);
												
												if(readOnly){
													app.setToolbarAllVisible(false);
												}
												if(userName=='tht'){
													app.enableRevision(false);
													app.showRevision(2);
												}else{
													if(!readOnly){
														app.enableProtect(false);
													}
													if((activityName=='SEC_2')||(activityName=='dengJiTaoHongTou')||(activityName=='dengJiTaoHongTou1')){
														app.enableRevision(false);
														app.showRevision(2);
													}else{
														app.enableRevision(true);
													}
													if((activityName=='FORMULATE_DOC')||(activityName=='GAIZHANGFAWEN')||(activityName=='GAIZHANGFAWEN1')||(activityName=='FINISH_DOC')||(activityName=='DIANZIJIAODUI')||(activityName=='DIANZIJIAODUI1')|| (activityName =='JBQZ') || (activityName =='XJQZ')  || (activityName =='Check_dzgz') ||(activityName =='Check_gzhang')){
														//如果是登记套红头环节或者电子校对，默认显示誊清稿
														app.showRevision(2);
														app.ActiveDocument.AcceptAllRevisions();
														if(activityName!='DIANZIJIAODUI1'&&activityName!='DIANZIJIAODUI'&&activityName!='FINISH_DOC'){
															app.enableProtect(true);
															//问题：国产上只读，右侧出现停止保护
															app.setToolbarAllVisible(false);
														}
													}
												}
												app.setUserName(userName) ;
											}
											var a = app.get_OfdExportOptions();
											a.put_SelectServiceProvider(1);
								},200);

							}
						}
					} else if(MODE == 'banshi') {
						if(COMPANY == 'FX') {
							if(url.indexOf('?')!=-1)
								url += '&rnd='+Math.random();
							obj.openFile(url);
						} else if(COMPANY == 'SK') {
							if(url.indexOf('?')!=-1)
								url += '&rnd='+Math.random();
							obj.openFile(url);
						}
					}
				}
			},200);
		},
		enableProtect:function(){//设置只读
			if(obj != null){
				obj.enableProtect(true) ;
			}
		},
		saveAs:function(){
		},
		saveURL:function(url, fileName){
			debugger;
			if(obj == null)
				return;
			if(MODE == 'liushi') {
				if(COMPANY == 'NEO') {
					obj.saveURL(url, fileName);
				} else if(COMPANY == 'Yozo') {
					try{
						ret = obj.saveURL(url, fileName);
					}catch(e){
					}
				} else if(COMPANY == 'WPS') {
					var result;
					if(navigator.userAgent.indexOf("MSIE") > 0) {
						result=obj.saveURL(url, fileName, false);
					} else {
						result=app.saveURL(url, fileName);
					}
					return result;
				}
			} else if(MODE == 'banshi') {
				if(COMPANY == 'FX') {
					obj.saveFile(url);
				} else if(COMPANY == 'SK') {
					obj.saveFile(url);
				}
			}
		},
		insertDocumentField:function(prom1){
			var check = setInterval(function(){
				if(state == 1){
					clearInterval(check);
					if(obj == null)
						return;
					if(MODE == 'liushi') {
						if(COMPANY == 'NEO') {
							obj.insertDocumentField( prom1, false );
						} else if(COMPANY == 'Yozo') {
							obj.insertDocumentField( prom1);
						} else if(COMPANY == 'WPS') {
							if(navigator.userAgent.indexOf("MSIE") > 0) {
								obj.insertDocumentField( prom1);
							} else {
								app.insertDocumentField( prom1);
							}
						}
					}
				}
			},200);
		},

		deleteDocumentField:function(fieldName){//删除公文域（不保留公文域内容）
			if(navigator.userAgent.indexOf("MSIE") > 0) {
				obj.deleteDocumentField(fieldName);
			}else{
				app.deleteDocumentField(fieldName);
			}
		},
		getAllDocumentField:function(fieldName){//判断公文域名称是否存在（true为存在）
			var flag = false;
			if(navigator.userAgent.indexOf("MSIE") > 0) {
				var list = obj.getAllDocumentField();//获取全部公文域名称
				if(list.indexOf(fieldName)>-1){
					flag=true
				}
			}else{
				var list = app.getAllDocumentField();
				if(list.indexOf(fieldName)>-1){
					flag=true
				}
			}
			return flag;
		},
		getLengthDocumentField:function(fieldName){//统计公文域名称中包含传的参数的公文域个数
			var result=0;
			if(navigator.userAgent.indexOf("MSIE") > 0) {
				var list = obj.getAllDocumentField();
				if(list.indexOf(fieldName)>-1){
					result=list.split(fieldName).length-1;
				}
			}else{
				var list = app.getAllDocumentField();
				if(list.indexOf(fieldName)>-1){
					result=list.split(fieldName).length-1;
				}
			}
			return result;
		},
		
		//附件说明表格的公文域操作
		setFJSM:function(fieldName,string){//string[包含附件说明内容的数组]；fieldName用于定位，传的值为固定值"附件说明"
			if(navigator.userAgent.indexOf("MSIE") > 0) {//默认模板中存在附件说明的情况下，附件说明的表格是全篇中第二张表格
				obj.ActiveDocument.Tables(2).Cell(2,2).Range.Text = "";//清空表格中第二行第二列的文字内容
				obj.cursorToDocumentField(fieldName,3);//光标定位（定位在附件说明公文域的最左端）
				var selection=obj.ActiveDocument.Application.Selection;//selection可当做当前光标对象
//				selection.MoveRight("wdCharacter",4,"wdMove");//光标右移4位
				for(var i = 0; i < string.length; i++){
					if(i+1 == string.length){
						obj.insertText(string[i]);//插入最后一条附件说明
					}else{//插入附件说明，右移，回车
						obj.insertText(string[i]);
//						selection.MoveRight;
						selection.TypeParagraph;
					}
				}
			}else{//代码执行效果同x86
				app.ActiveDocument.Tables.Item(2).Cell(2,2).Range.put_Text("");
				app.cursorToDocumentField(fieldName,3);
//				app.Selection.MoveRight(1,4,0);
				for(var i = 0; i < string.length; i++){
					if(i+1 == string.length){
						app.insertText(string[i]);
					}else{
						app.insertText(string[i]);
//						app.Selection.MoveRight();
						app.Selection.TypeParagraph();
					}
				}
			}
		},
		//附件正文公文域操作
		setFJZW:function setFJZW(count){//count[公文中的附件个数]
			if(navigator.userAgent.indexOf("MSIE") > 0){//下为WPS提供的附件的格式代码，若[模板中没有附件公文域]或[公文中只有一个附件]则不会进入循环内
				var doctable = null;
				obj.ActiveDocument.DocumentFields.InsertionMode = "False";
				for(var i = 1;i <= count - 1; i++){
					if(i == 1){
						obj.ActiveDocument.DocumentFields("附件").Select;
						obj.ActiveDocument.ActiveWindow.Selection.MoveLeft("wdCharacter",1,"wdMove");
						
						obj.ActiveDocument.ActiveWindow.Selection.Collapse("wdCollapseStart");
						obj.ActiveDocument.ActiveWindow.Selection.InsertBreak(2);
						obj.ActiveDocument.ActiveWindow.Selection.MoveUp("wdLine",1,"wdMove");
					}else{
						obj.ActiveDocument.DocumentFields("附件_" + (i-1)).Select;
						obj.ActiveDocument.ActiveWindow.Selection.MoveLeft("wdCharacter",1,"wdMove");
						
						obj.ActiveDocument.ActiveWindow.Selection.Collapse("wdCollapseStart");
						obj.ActiveDocument.ActiveWindow.Selection.InsertBreak(2);
						obj.ActiveDocument.ActiveWindow.Selection.MoveUp("wdLine",1,"wdMove");
					}
					obj.ActiveDocument.ActiveWindow.Selection.Font.Size= 16;
					obj.ActiveDocument.ActiveWindow.Selection.Font.Name= "黑体";
					obj.ActiveDocument.ActiveWindow.Selection.Font.Position = 0;
					obj.insertDocumentField("附件_"+i);
					
					obj.ActiveDocument.DocumentFields.InsertionMode = "False";
					obj.setDocumentField("附件_"+i, "附件"+i);
					obj.ActiveDocument.ActiveWindow.Selection.MoveRight("wdCharacter",3,"wdMove");
					obj.ActiveDocument.ActiveWindow.Selection.TypeParagraph;
					doctable = obj.ActiveDocument.Tables.add(obj.ActiveDocument.ActiveWindow.Selection.Range,2,1);
					doctable.Borders(-1).LineStyle = "0";
					doctable.Borders(-2).LineStyle = "0";
					doctable.Borders(-3).LineStyle = "0";
					doctable.Borders(-4).LineStyle = "0";
					doctable.Borders(-5).LineStyle = "0";
					doctable.Borders(-6).LineStyle = "0";
					doctable.Borders(-7).LineStyle = "0";
					doctable.Borders(-8).LineStyle = "0";
					doctable.PreferredWidth = 442.204437;   //413.858002
					doctable.Rows.Alignment = 1;
					doctable.Rows(1).Height = 31.464546;
					obj.ActiveDocument.ActiveWindow.Selection.MoveDown("wdLine",1,"wdMove");
					doctable.Rows(2).Height = 52.724377;
					obj.ActiveDocument.ActiveWindow.Selection.ParagraphFormat.Alignment = 1;
					obj.ActiveDocument.ActiveWindow.Selection.Font.Size= 24;
					obj.ActiveDocument.ActiveWindow.Selection.Font.Name= "方正小标宋_GBK";
					obj.ActiveDocument.ActiveWindow.Selection.Font.Position = 9.5;
					obj.insertDocumentField("FJ_标题_"+i);
					
					obj.ActiveDocument.DocumentFields.InsertionMode = "False";
					obj.setDocumentField("FJ_标题_"+i, "ＸＸＸＸＸＸＸＸ");
					obj.ActiveDocument.ActiveWindow.Selection.MoveDown("wdLine",1,"wdMove");
					obj.ActiveDocument.ActiveWindow.Selection.Font.Size= 16;
					obj.ActiveDocument.ActiveWindow.Selection.Font.Name= "仿宋_GB2312";
					obj.ActiveDocument.ActiveWindow.Selection.Font.Position = -23;
					obj.ActiveDocument.ActiveWindow.Selection.TypeText("    ");
					obj.insertDocumentField("FJ_正文_"+i);
					
					obj.ActiveDocument.DocumentFields.InsertionMode = "False";
					obj.setDocumentField("FJ_正文_"+i, "ＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸ");
				}
				for(var i = 1;i <= count; i++){
					if(i == 1){
						obj.cursorToDocumentField("附件", 5);
					}else{
						obj.cursorToDocumentField("附件_"+(i-1), 5);
					}
					obj.ActiveDocument.ActiveWindow.Selection.Font.Name= "黑体";
				}
			}else{//代码执行效果同x86
				var doctable = null;
				for(var i = 1;i <= count - 1; i++){
					if(i == 1){
						app.cursorToDocumentField("附件",5);
						app.ActiveDocument.DocumentFields.InsertionMode = "False";
						app.ActiveDocument.ActiveWindow.Selection.MoveLeft(1,1,0);
						
						app.ActiveDocument.ActiveWindow.Selection.Collapse(1);
						app.ActiveDocument.ActiveWindow.Selection.InsertBreak(2);
						app.ActiveDocument.ActiveWindow.Selection.MoveUp(5,1,0);
					}else{
						app.cursorToDocumentField("附件_" + (i-1),5);
						app.ActiveDocument.DocumentFields.InsertionMode = "False";
						app.ActiveDocument.ActiveWindow.Selection.MoveLeft(1,1,0);
						
						app.ActiveDocument.ActiveWindow.Selection.Collapse(1);
						app.ActiveDocument.ActiveWindow.Selection.InsertBreak(2);
						app.ActiveDocument.ActiveWindow.Selection.MoveUp(5,1,0);
					}
					app.ActiveDocument.ActiveWindow.Selection.Font.Size= 16;
					app.ActiveDocument.ActiveWindow.Selection.Font.Name= "黑体";
					app.ActiveDocument.ActiveWindow.Selection.Font.Position = 0;
					app.insertDocumentField("附件_"+i);

					app.ActiveDocument.DocumentFields.InsertionMode = "False";
					app.setDocumentField("附件_"+i, "附件"+i);
					app.ActiveDocument.ActiveWindow.Selection.MoveRight(1,3,0);
					app.ActiveDocument.ActiveWindow.Selection.TypeParagraph();
					doctable = app.ActiveDocument.Tables.Add(app.ActiveDocument.ActiveWindow.Selection.Range,2,1);
					doctable.Borders.put_InsideLineStyle(0);
					doctable.Borders.put_OutsideLineStyle(0);
					
					doctable.put_PreferredWidth(442.204437);   //413.858002
					doctable.Rows.put_Alignment(1);
					doctable.Rows.Item(1).put_Height(31.464546);
					app.ActiveDocument.ActiveWindow.Selection.MoveDown(5,1,0);
					doctable.Rows.Item(2).put_Height(52.724377);
					app.ActiveDocument.ActiveWindow.Selection.ParagraphFormat.put_Alignment(1);
					app.ActiveDocument.ActiveWindow.Selection.Font.put_Size(24);
					app.ActiveDocument.ActiveWindow.Selection.Font.put_Name("方正小标宋_GBK");
					app.ActiveDocument.ActiveWindow.Selection.Font.put_Position(9);//问题：此值应为9.5，但国产代码中参数为小数时会报错
					app.insertDocumentField("FJ_标题_"+i);

					app.ActiveDocument.DocumentFields.InsertionMode = "False";
					app.setDocumentField("FJ_标题_"+i, "ＸＸＸＸＸＸＸＸ");
					app.ActiveDocument.ActiveWindow.Selection.MoveDown(5,1,0);
					app.ActiveDocument.ActiveWindow.Selection.Font.put_Size(16);
					app.ActiveDocument.ActiveWindow.Selection.Font.put_Name("仿宋_GB2312");
					app.ActiveDocument.ActiveWindow.Selection.Font.put_Position(-23);
					app.ActiveDocument.ActiveWindow.Selection.TypeText("    ");
					app.insertDocumentField("FJ_正文_"+i);
					
					app.ActiveDocument.DocumentFields.InsertionMode = "False";
					app.setDocumentField("FJ_正文_"+i, "ＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸＸ");
				}
				for(var i = 1;i <= count; i++){
					if(i == 1){
						app.cursorToDocumentField("附件", 5);
					}else{
						app.cursorToDocumentField("附件_"+(i-1), 5);
					}
					app.ActiveDocument.ActiveWindow.Selection.Font.Name= "黑体";
				}
			}
		},
		//删除附件
		deleteFJ:function(x){//chooseHongtouMb.jsp中判断[公文中没有附件]并且[模板中有附件的公文域]，则执行此部分代码
			if(navigator.userAgent.indexOf("MSIE") > 0) {
				obj.deleteDocumentField("附件说明");//删除附件说明公文域
				obj.ActiveDocument.Tables(2).Cell(2,2).Range.ListFormat.RemoveNumbers("wdNumberParagraph");//删除附件说明表格中第二行第二列的数字标号（编号不在公文域中，故需要单独删除）
				obj.deleteDocumentField("附件");
				obj.deleteDocumentField("FJ_标题");
				obj.deleteDocumentField("FJ_正文");
				if(x==1){
					obj.ActiveDocument.tables(3).Delete();//删除附件正文上方的表格（默认有附件的模板中，第三个表格是正文标题的表格）
				}
			}else{//代码执行效果同x86
				app.deleteDocumentField("附件说明");
				app.ActiveDocument.Tables.Item(2).Cell(2,2).Range.ListFormat.RemoveNumbers(1);
				app.deleteDocumentField("附件");
				app.deleteDocumentField("FJ_标题");
				app.deleteDocumentField("FJ_正文");
				if(x==1){
					app.ActiveDocument.Tables.Item(3).Delete();
				}
			}
		},
		//删除单元格（行）[伪]
		deleteCellRow:function(m){//[公文中红字表头个数]少于[模板中对应位置公文域个数]时使用，m[表格中要删除的行]
			if(navigator.userAgent.indexOf("MSIE") > 0) {
//				obj.ActiveDocument.Tables(1).Cell(m,2).Range.Rows.Delete;//光标定位在第一个表格中第m行第2列，并删除行
				for(var i=5; i>m; i--){
					obj.cursorToDocumentField("发文机关名称_"+i, 1);
					obj.backspace()
					obj.deleteDocumentField("发文机关名称_"+i);
				}
			}else{
//				app.ActiveDocument.Tables.Item(1).Cell(m,2).Range.Rows.Delete();
				for(var i=5; i>m; i--){
					app.cursorToDocumentField("发文机关名称_"+i, 1);
					app.backspace()
					app.deleteDocumentField("发文机关名称_"+i);
				}
			}
		},
		//添加公文域(横向)【抄送已修改不用此方法】
		insertDocumentField2:function(fieldName,fieldName2,String){//[模板中签发人 主送 抄送的公文域包含"_"]且[公文中对应内容个数大于对应公文域个数]时使用
			if(navigator.userAgent.indexOf("MSIE") > 0) {//x86代码
				obj.cursorToDocumentField(fieldName2, 2);
				obj.insertText(String);//String[签发人为两个空格，主送 抄送为中文格式的逗号]
				var selection=obj.ActiveDocument.Application.Selection;
//				selection.MoveRight("wdCell",1,"wdMove");//右移1位
				obj.insertDocumentField(fieldName);
			}else{//国产代码（代码实现方式与x86存在差异）
//				if(String == "，" || String == "。"){//String为逗号或句号时与x86代码效果相同
				if(String == "，"){//String为逗号时与x86代码效果相同
					app.cursorToDocumentField(fieldName2, 2);
					app.insertText(String);
					app.Selection.MoveRight(1,1,0);
				}else{
					app.cursorToDocumentField(fieldName2, 5);
					app.Selection.MoveRight(1,1,0);
					app.ActiveDocument.DocumentFields.InsertionMode = "False";
					//问题：国产中insertText无法直接插入2个空格（首位不能是空格，末尾不能是空格，不能有连续空格）
					app.insertText("两 空 格");//插入3个文字中间两两之间各一个空格，下边代码用于删掉3个中文字，并把光标移动到最右端
					app.backspace();
					app.Selection.MoveLeft(1,1,0);
					app.backspace();
					app.Selection.MoveLeft(1,1,0);
					app.backspace();
					app.Selection.MoveRight(1,1,0);
					app.Selection.MoveRight(1,1,0);
				}
				app.insertDocumentField(fieldName);
				app.setDocumentField(fieldName, "ＸＸＸ");//国产代码中cursorToDocumentField(fieldName2, 5)无法选中没有内容的公文域，因此添加公文域后用XXX填值以便之后代码对公文域进行选中操作
			}
		},
		//添加公文域（抄送）
		setDocumentFieldCs:function(str,length,cs,flag){
			if(navigator.userAgent.indexOf("MSIE") > 0) {
				if(str == "、"){
					obj.cursorToDocumentField("抄送机关_"+length, 4);
					obj.ActiveDocument.DocumentFields.InsertionMode = "False";
					obj.insertText(str);
//					var selection=obj.ActiveDocument.Application.Selection;
//					selection.MoveRight("wdCell",1,"wdMove");
					obj.insertDocumentField("抄送机关_"+(length+1));
					obj.setDocumentField("抄送机关_"+(length+1),cs);
				}else if(str == "_"){
					obj.cursorToDocumentField("抄送机关_"+length, 4);
					obj.ActiveDocument.DocumentFields.InsertionMode = "False";
					var selection=obj.ActiveDocument.Application.Selection;
					if(flag){
						selection.MoveDown(5,1,0);
						selection.MoveLeft(1,1,0);
					}else{
						selection.MoveRight("wdCell",1,"wdMove");
					}
					obj.insertDocumentField("抄送机关_"+(length+1));
					obj.setDocumentField("抄送机关_"+(length+1),cs);
				}else{
					obj.cursorToDocumentField("抄送机关_"+length, 4);
					obj.ActiveDocument.DocumentFields.InsertionMode = "False";
					obj.insertText(str);
					var selection=obj.ActiveDocument.Application.Selection;
					selection.MoveRight("wdCell",1,"wdMove");
					if(str == "。"){
						selection.TypeParagraph;
					}
				}
			}else{
				if(str == "、"){
					app.cursorToDocumentField("抄送机关_"+length, 4);
					app.ActiveDocument.DocumentFields.InsertionMode = "False";
					app.insertText(str);
//					app.Selection.MoveRight(1,1,0);
					app.insertDocumentField("抄送机关_"+(length+1));
					app.setDocumentField("抄送机关_"+(length+1),cs);
				}else if(str == "_"){
					app.cursorToDocumentField("抄送机关_"+length, 4);
					app.ActiveDocument.DocumentFields.InsertionMode = "False";
					if(flag){
						app.Selection.MoveDown(5,1,0);
						app.Selection.MoveLeft(1,1,0);
					}else{
						app.Selection.MoveRight(1,1,0);
					}
					app.insertDocumentField("抄送机关_"+(length+1));
					app.setDocumentField("抄送机关_"+(length+1),cs);
				}else{
					app.cursorToDocumentField("抄送机关_"+length, 4);
					app.ActiveDocument.DocumentFields.InsertionMode = "False";
					app.insertText(str);
					app.Selection.MoveRight(1,1,0);
					if(str == "。"){
						app.Selection.TypeParagraph();
					}
				}
			}
		},
		//添加公文域（纵向）
		insertTaiTou:function(i,n){//用于增加发文机关名称（红字表头）i[待插入公文域编号]；n[总共发文机关名称个数]
			if(navigator.userAgent.indexOf("MSIE") > 0) {//注意：模板原型为每表头在单独的单元格内，增加表头后，最后一个单元格会有多个表头（左右两端有合并的单元格，在单元格内增加行方法不容易实现，所以用换行方式实现，最后一个单元格会有多个表头）
				obj.cursorToDocumentField("发文机关名称_"+(i-1), 4);//定位模板当前最后一个发文机关名称的公文域
				obj.ActiveDocument.DocumentFields.InsertionMode = "False";
				var selection=obj.ActiveDocument.ActiveWindow.Selection;
				selection.TypeParagraph;//回车换行
				obj.insertDocumentField("发文机关名称_"+i);
				obj.setDocumentField("发文机关名称_"+i, "发文机关名称_"+i);
//				if(i==n){
//					obj.cursorToDocumentField("发文机关名称_"+i, 3);
//					obj.ActiveDocument.DocumentFields.InsertionMode = "False";
//				}
			}else{//代码执行效果同x86
				app.cursorToDocumentField("发文机关名称_"+(i-1), 4);
				app.ActiveDocument.DocumentFields.InsertionMode = "False";
				app.Selection.TypeParagraph();
				app.insertDocumentField("发文机关名称_"+i);
				app.setDocumentField("发文机关名称_"+i, "发文机关名称_"+i);
			}
		},
		backspace:function(fieldName){//定位指定公文域，并进行退格（backspace）操作
			if(navigator.userAgent.indexOf("MSIE") > 0) {
				obj.cursorToDocumentField(fieldName, 3);
				obj.backspace();
			}else{//代码执行效果同x86
				app.cursorToDocumentField(fieldName, 3);
				app.backspace();
			}
		},
		
		setGaizhang1:function(countGw,countMb,fwjgsm){//非表格类型发文机关署名公文域的添加/删除操作【模板中发文机关署名公文域个数如果是5个，不会进入此方法！】
			if(navigator.userAgent.indexOf("MSIE") > 0) {
				if(countGw > countMb){//[公文中发文机关署名个数]>[模板中发文机关署名公文域的个数]
					for(var i = countMb+1; i < countGw+1; i++){
						var n = i - 1;
						obj.cursorToDocumentField("发文机关署名_"+n, 4);//定位
						obj.ActiveDocument.DocumentFields.InsertionMode = "False";
						var selection=obj.ActiveDocument.Application.Selection;
						selection.TypeParagraph;//回车
						obj.insertDocumentField("发文机关署名_"+i);//插入新的公文域
					}
				}else{//[模板中发文机关署名公文域的个数]>[公文中发文机关署名个数]
					for(var m=countGw+1; m<countMb+1;m++){//m[公文中发文机关署名个数+1]；+1的目的是方便下方使用；循环条件[m<模板中对应公文域个数+1]
						obj.cursorToDocumentField("发文机关署名_"+m, 1);
						obj.backspace();
						obj.deleteDocumentField("发文机关署名_"+m);
					}
				}
				for(var m=0;m<countGw;m++){//公文域赋值
					obj.ActiveDocument.DocumentFields.InsertionMode = "False";
					obj.setDocumentField("发文机关署名_"+(m+1),fwjgsm[m]);
				}
			}else{//代码执行效果同x86
				if(countGw > countMb){
					for(var i = countMb+1; i < countGw+1; i++){
						var n = i - 1;
						app.cursorToDocumentField("发文机关署名_"+n, 4);
						app.ActiveDocument.DocumentFields.InsertionMode = "False";
						app.Selection.TypeParagraph();
						app.insertDocumentField("发文机关署名_"+i);
					}
				}else{
					for(var m=countGw+1; m<countMb+1;m++){
						app.cursorToDocumentField("发文机关署名_"+m, 1);
						app.backspace();
						app.deleteDocumentField("发文机关署名_"+m);
					}
				}
				for(var m=0;m<countGw;m++){
					app.ActiveDocument.DocumentFields.InsertionMode = "False";
					app.setDocumentField("发文机关署名_"+(m+1),fwjgsm[m]);
				}
			}
		},
		setGaizhang2:function(countGw,fwjgsm){//表格类型发文机关署名公文域格式控制操作（最多14个章）【当且仅当模板中发文机关署名的公文域个数等于5时才进入此方法！】
			if(navigator.userAgent.indexOf("MSIE") > 0) {
				switch(countGw){
					case 2://[公文中有2个发文机关署名（2个单位盖章）]
						//默认有这种格式的发文机关署名时，发文机关署名所在表格为全篇中第二张表格
						obj.ActiveDocument.Tables(2).Rows(1).Delete;//删除第二表格第一行
						for(var i = 0; i < countGw; i++){
							obj.setDocumentField("发文机关署名_"+(i+4), fwjgsm[i]);
						}
						break;
					case 3://[公文中有3个发文机关署名（3个单位盖章）]
						obj.ActiveDocument.Tables(2).Rows(2).Delete;//删除第二表格第二行
						obj.cursorToDocumentField("发文机关署名_1", 4);//定位
						var selection=obj.ActiveDocument.Application.Selection;
						obj.ActiveDocument.DocumentFields.InsertionMode = "False";
						selection.TypeParagraph;//控制格式用的回车
						obj.cursorToDocumentField("发文机关署名_2", 4);
						obj.ActiveDocument.DocumentFields.InsertionMode = "False";
						selection.TypeParagraph;//控制格式用的回车
						obj.cursorToDocumentField("发文机关署名_3", 4);
						obj.ActiveDocument.DocumentFields.InsertionMode = "False";
						selection.TypeParagraph;//控制格式用的回车
						obj.insertDocumentField("成文日期");
						for(var i = 0; i < countGw; i++){
							obj.setDocumentField("发文机关署名_"+(i+1), fwjgsm[i]);
						}
						break;
					case 4://修改格式：22
						obj.ActiveDocument.Tables(2).Rows(1).Delete;//删除第一行
						obj.deleteDocumentField("成文日期");//删除第一行（原为第二行）的成文日期公文域
						obj.cursorToDocumentField("发文机关署名_5", 4);//定位
						var selection=obj.ActiveDocument.Application.Selection;
						selection.MoveRight("wdCell",4,"wdMove");//控制光标位置
						selection.TypeParagraph;
						obj.ActiveDocument.Tables(2).Cell(2,2).Range.Text="\r\n\r\n\r\n\r\n\r\n\r\n";//控制格式用的回车换行（将粘贴部分第一个单元格内容变为6行空白格）
						obj.cursorToDocumentField("发文机关署名_4", 4);
						selection.MoveDown("wdCell",7,"wdMove");//移动光标位置
						obj.insertDocumentField("发文机关署名_6");//添加公文域
						
						obj.ActiveDocument.Tables(2).Cell(2,3).Range.Text="\r\n\r\n\r\n\r\n\r\n\r\n";//控制格式用的回车换行（将粘贴部分第二个单元格内容变为6行空白格）
						obj.cursorToDocumentField("发文机关署名_5", 4);
						selection.MoveDown("wdCell",7,"wdMove");
						obj.insertDocumentField("发文机关署名_7");
						
						selection.MoveDown("wdCell",1,"wdMove");
						obj.insertDocumentField("成文日期");
						
						for(var i = 0; i < countGw; i++){
							obj.setDocumentField("发文机关署名_"+(i+4), fwjgsm[i]);
						}
						break;
					case 5://模板默认格式：32
						for(var i = 0; i < countGw; i++){
							obj.setDocumentField("发文机关署名_"+(i+1), fwjgsm[i]);
						}
						break;
					case 6://修改格式：33
						obj.ActiveDocument.Tables(2).Rows(2).Delete;
						obj.cursorToDocumentField("发文机关署名_3", 4);
						var selection=obj.ActiveDocument.Application.Selection;
						selection.MoveRight("wdCell",2,"wdMove");
						selection.TypeParagraph;
						obj.ActiveDocument.Tables(2).Cell(2,1).Range.Text="\r\n\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_1", 4);
						selection.MoveDown("wdCell",6,"wdMove");
						obj.insertDocumentField("发文机关署名_4");
						
						obj.ActiveDocument.Tables(2).Cell(2,2).Range.Text="\r\n\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_2", 4);
						selection.MoveDown("wdCell",6,"wdMove");
						obj.insertDocumentField("发文机关署名_5");
						
						obj.ActiveDocument.Tables(2).Cell(2,3).Range.Text="\r\n\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_3", 4);
						selection.MoveDown("wdCell",6,"wdMove");
						obj.insertDocumentField("发文机关署名_6");
						
						selection.MoveDown("wdCell",1,"wdMove");
						obj.insertDocumentField("成文日期");
						
						for(var i = 0; i < countGw; i++){
							obj.setDocumentField("发文机关署名_"+(i+1), fwjgsm[i]);
						}
						break;
					case 7://修改格式：322
						obj.deleteDocumentField("成文日期");
						obj.cursorToDocumentField("发文机关署名_5", 4);
						var selection=obj.ActiveDocument.Application.Selection;
						selection.MoveRight("wdCell",4,"wdMove");
						selection.TypeParagraph;
						obj.ActiveDocument.Tables(2).Cell(3,2).Range.Text="\r\n\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_4", 4);
						selection.MoveDown("wdCell",7,"wdMove");
						obj.insertDocumentField("发文机关署名_6");
						
						obj.ActiveDocument.Tables(2).Cell(3,3).Range.Text="\r\n\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_5", 4);
						selection.MoveDown("wdCell",7,"wdMove");
						obj.insertDocumentField("发文机关署名_7");
						
						selection.MoveDown("wdCell",1,"wdMove");
						obj.insertDocumentField("成文日期");
						
						for(var i = 0; i < countGw; i++){
							obj.setDocumentField("发文机关署名_"+(i+1), fwjgsm[i]);
						}
						break;
					case 8://修改格式：332
						obj.cursorToDocumentField("发文机关署名_3", 4);
						var selection=obj.ActiveDocument.Application.Selection;
						selection.MoveRight("wdCell",2,"wdMove");
						selection.TypeParagraph;
						obj.ActiveDocument.Tables(2).Cell(2,1).Range.Text="\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_1", 4);
						selection.MoveDown("wdCell",6,"wdMove");
						obj.insertDocumentField("发文机关署名_6");
						
						obj.ActiveDocument.Tables(2).Cell(2,2).Range.Text="\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_2", 4);
						selection.MoveDown("wdCell",6,"wdMove");
						obj.insertDocumentField("发文机关署名_7");
						
						obj.ActiveDocument.Tables(2).Cell(2,3).Range.Text="\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_3", 4);
						selection.MoveDown("wdCell",6,"wdMove");
						obj.insertDocumentField("发文机关署名_8");
						
						for(var i = 0; i < countGw; i++){
							obj.setDocumentField("发文机关署名_"+(i+1), fwjgsm[i]);
						}
						break;
					case 9://修改格式：333
						obj.ActiveDocument.Tables(2).Rows(2).Delete;
						obj.cursorToDocumentField("发文机关署名_3", 4);
						var selection=obj.ActiveDocument.Application.Selection;
						selection.MoveRight("wdCell",2,"wdMove");
						selection.TypeParagraph;
						obj.ActiveDocument.Tables(2).Cell(2,1).Range.Text="\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_1", 4);
						selection.MoveDown("wdCell",6,"wdMove");
						obj.insertDocumentField("发文机关署名_4");
						
						obj.ActiveDocument.Tables(2).Cell(2,2).Range.Text="\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_2", 4);
						selection.MoveDown("wdCell",6,"wdMove");
						obj.insertDocumentField("发文机关署名_5");
						
						obj.ActiveDocument.Tables(2).Cell(2,3).Range.Text="\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_3", 4);
						selection.MoveDown("wdCell",6,"wdMove");
						obj.insertDocumentField("发文机关署名_6");
						
						
						obj.cursorToDocumentField("发文机关署名_6", 4);
						selection.MoveRight("wdCell",2,"wdMove");
						selection.TypeParagraph;
						obj.ActiveDocument.Tables(2).Cell(3,1).Range.Text="\r\n\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_4", 4);
						selection.MoveDown("wdCell",6,"wdMove");
						obj.insertDocumentField("发文机关署名_7");
						
						obj.ActiveDocument.Tables(2).Cell(3,2).Range.Text="\r\n\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_5", 4);
						selection.MoveDown("wdCell",6,"wdMove");
						obj.insertDocumentField("发文机关署名_8");
						
						obj.ActiveDocument.Tables(2).Cell(3,3).Range.Text="\r\n\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_6", 4);
						selection.MoveDown("wdCell",6,"wdMove");
						obj.insertDocumentField("发文机关署名_9");
						
						selection.MoveDown("wdCell",1,"wdMove");
						obj.insertDocumentField("成文日期");
						
						for(var i = 0; i < countGw; i++){
							obj.setDocumentField("发文机关署名_"+(i+1), fwjgsm[i]);
						}
						break;
					case 10://修改格式：3322
						var selection=obj.ActiveDocument.Application.Selection;
						obj.deleteDocumentField("成文日期");
						obj.cursorToDocumentField("发文机关署名_5", 4);
						selection.MoveRight("wdCell",4,"wdMove");
						selection.TypeParagraph;
						obj.ActiveDocument.Tables(2).Cell(3,2).Range.Text="\r\n\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_4", 4);
						selection.MoveDown("wdCell",7,"wdMove");
						obj.insertDocumentField("发文机关署名_9");
						obj.ActiveDocument.Tables(2).Cell(3,3).Range.Text="\r\n\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_5", 4);
						selection.MoveDown("wdCell",7,"wdMove");
						obj.insertDocumentField("发文机关署名_10");
						
						selection.MoveDown("wdCell",1,"wdMove");
						obj.insertDocumentField("成文日期");
						
						obj.cursorToDocumentField("发文机关署名_3", 4);
						selection.MoveRight("wdCell",2,"wdMove");
						selection.TypeParagraph;
						obj.ActiveDocument.Tables(2).Cell(2,1).Range.Text="\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_1", 4);
						selection.MoveDown("wdCell",6,"wdMove");
						obj.insertDocumentField("发文机关署名_6");
						
						obj.ActiveDocument.Tables(2).Cell(2,2).Range.Text="\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_2", 4);
						selection.MoveDown("wdCell",6,"wdMove");
						obj.insertDocumentField("发文机关署名_7");
						
						obj.ActiveDocument.Tables(2).Cell(2,3).Range.Text="\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_3", 4);
						selection.MoveDown("wdCell",6,"wdMove");
						obj.insertDocumentField("发文机关署名_8");
						
						for(var i = 0; i < countGw; i++){
							obj.setDocumentField("发文机关署名_"+(i+1), fwjgsm[i]);
						}
						break;
					case 11://修改格式：3332
						obj.cursorToDocumentField("发文机关署名_3", 4);
						var selection=obj.ActiveDocument.Application.Selection;
						selection.MoveRight("wdCell",2,"wdMove");
						selection.TypeParagraph;
						obj.ActiveDocument.Tables(2).Cell(2,1).Range.Text="\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_1", 4);
						selection.MoveDown("wdCell",6,"wdMove");
						obj.insertDocumentField("发文机关署名_6");
						
						obj.ActiveDocument.Tables(2).Cell(2,2).Range.Text="\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_2", 4);
						selection.MoveDown("wdCell",6,"wdMove");
						obj.insertDocumentField("发文机关署名_7");
						
						obj.ActiveDocument.Tables(2).Cell(2,3).Range.Text="\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_3", 4);
						selection.MoveDown("wdCell",6,"wdMove");
						obj.insertDocumentField("发文机关署名_8");
						
						obj.cursorToDocumentField("发文机关署名_8", 4);
						selection.MoveRight("wdCell",2,"wdMove");
						selection.TypeParagraph;
						obj.ActiveDocument.Tables(2).Cell(3,1).Range.Text="\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_6", 4);
						selection.MoveDown("wdCell",6,"wdMove");
						obj.insertDocumentField("发文机关署名_9");
						
						obj.ActiveDocument.Tables(2).Cell(3,2).Range.Text="\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_7", 4);
						selection.MoveDown("wdCell",6,"wdMove");
						obj.insertDocumentField("发文机关署名_10");
						
						obj.ActiveDocument.Tables(2).Cell(3,3).Range.Text="\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_8", 4);
						selection.MoveDown("wdCell",6,"wdMove");
						obj.insertDocumentField("发文机关署名_11");
						
						for(var i = 0; i < countGw; i++){
							obj.setDocumentField("发文机关署名_"+(i+1), fwjgsm[i]);
						}
						break;
					case 12://修改格式：3333
						obj.ActiveDocument.Tables(2).Rows(2).Delete;
						obj.cursorToDocumentField("发文机关署名_3", 4);
						var selection=obj.ActiveDocument.Application.Selection;
						selection.MoveRight("wdCell",2,"wdMove");
						selection.TypeParagraph;
						obj.ActiveDocument.Tables(2).Cell(2,1).Range.Text="\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_1", 4);
						selection.MoveDown("wdCell",6,"wdMove");
						obj.insertDocumentField("发文机关署名_4");
						
						obj.ActiveDocument.Tables(2).Cell(2,2).Range.Text="\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_2", 4);
						selection.MoveDown("wdCell",6,"wdMove");
						obj.insertDocumentField("发文机关署名_5");
						
						obj.ActiveDocument.Tables(2).Cell(2,3).Range.Text="\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_3", 4);
						selection.MoveDown("wdCell",6,"wdMove");
						obj.insertDocumentField("发文机关署名_6");
						
						obj.cursorToDocumentField("发文机关署名_6", 4);
						selection.MoveRight("wdCell",2,"wdMove");
						selection.TypeParagraph;
						obj.ActiveDocument.Tables(2).Cell(3,1).Range.Text="\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_4", 4);
						selection.MoveDown("wdCell",6,"wdMove");
						obj.insertDocumentField("发文机关署名_7");
						
						obj.ActiveDocument.Tables(2).Cell(3,2).Range.Text="\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_5", 4);
						selection.MoveDown("wdCell",6,"wdMove");
						obj.insertDocumentField("发文机关署名_8");
						
						obj.ActiveDocument.Tables(2).Cell(3,3).Range.Text="\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_6", 4);
						selection.MoveDown("wdCell",6,"wdMove");
						obj.insertDocumentField("发文机关署名_9");
						
						obj.cursorToDocumentField("发文机关署名_9", 4);
						selection.MoveRight("wdCell",2,"wdMove");
						selection.TypeParagraph;
						obj.ActiveDocument.Tables(2).Cell(4,1).Range.Text="\r\n\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_7", 4);
						selection.MoveDown("wdCell",6,"wdMove");
						obj.insertDocumentField("发文机关署名_10");
						
						obj.ActiveDocument.Tables(2).Cell(4,2).Range.Text="\r\n\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_8", 4);
						selection.MoveDown("wdCell",6,"wdMove");
						obj.insertDocumentField("发文机关署名_11");
						
						obj.ActiveDocument.Tables(2).Cell(4,3).Range.Text="\r\n\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_9", 4);
						selection.MoveDown("wdCell",6,"wdMove");
						obj.insertDocumentField("发文机关署名_12");
						
						selection.MoveDown("wdCell",1,"wdMove");
						obj.insertDocumentField("成文日期");
						
						for(var i = 0; i < countGw; i++){
							obj.setDocumentField("发文机关署名_"+(i+1), fwjgsm[i]);
						}
						break;
					case 13://修改格式：33322
						var selection=obj.ActiveDocument.Application.Selection;
						obj.deleteDocumentField("成文日期");
						obj.cursorToDocumentField("发文机关署名_5", 4);
						selection.MoveRight("wdCell",4,"wdMove");
						selection.TypeParagraph;
						obj.ActiveDocument.Tables(2).Cell(3,2).Range.Text="\r\n\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_4", 4);
						selection.MoveDown("wdCell",7,"wdMove");
						obj.insertDocumentField("发文机关署名_12");
						
						obj.ActiveDocument.Tables(2).Cell(3,3).Range.Text="\r\n\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_5", 4);
						selection.MoveDown("wdCell",7,"wdMove");
						obj.insertDocumentField("发文机关署名_13");
						
						selection.MoveDown("wdCell",1,"wdMove");
						obj.insertDocumentField("成文日期");
						
						obj.cursorToDocumentField("发文机关署名_3", 4);
						selection.MoveRight("wdCell",2,"wdMove");
						selection.TypeParagraph;
						obj.ActiveDocument.Tables(2).Cell(2,1).Range.Text="\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_1", 4);
						selection.MoveDown("wdCell",6,"wdMove");
						obj.insertDocumentField("发文机关署名_6");
						
						obj.ActiveDocument.Tables(2).Cell(2,2).Range.Text="\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_2", 4);
						selection.MoveDown("wdCell",6,"wdMove");
						obj.insertDocumentField("发文机关署名_7");
						
						obj.ActiveDocument.Tables(2).Cell(2,3).Range.Text="\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_3", 4);
						selection.MoveDown("wdCell",6,"wdMove");
						obj.insertDocumentField("发文机关署名_8");
						
						obj.cursorToDocumentField("发文机关署名_8", 4);
						selection.MoveRight("wdCell",2,"wdMove");
						selection.TypeParagraph;
						obj.ActiveDocument.Tables(2).Cell(3,1).Range.Text="\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_6", 4);
						selection.MoveDown("wdCell",6,"wdMove");
						obj.insertDocumentField("发文机关署名_9");
						
						obj.ActiveDocument.Tables(2).Cell(3,2).Range.Text="\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_7", 4);
						selection.MoveDown("wdCell",6,"wdMove");
						obj.insertDocumentField("发文机关署名_10");
						
						obj.ActiveDocument.Tables(2).Cell(3,3).Range.Text="\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_8", 4);
						selection.MoveDown("wdCell",6,"wdMove");
						obj.insertDocumentField("发文机关署名_11");
						
						for(var i = 0; i < countGw; i++){
							obj.setDocumentField("发文机关署名_"+(i+1), fwjgsm[i]);
						}
						break;
					case 14://修改格式：33332
						obj.cursorToDocumentField("发文机关署名_3", 4);
						var selection=obj.ActiveDocument.Application.Selection;
						selection.MoveRight("wdCell",2,"wdMove");
						selection.TypeParagraph;
						obj.ActiveDocument.Tables(2).Cell(2,1).Range.Text="\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_1", 4);
						selection.MoveDown("wdCell",6,"wdMove");
						obj.insertDocumentField("发文机关署名_6");
						
						obj.ActiveDocument.Tables(2).Cell(2,2).Range.Text="\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_2", 4);
						selection.MoveDown("wdCell",6,"wdMove");
						obj.insertDocumentField("发文机关署名_7");
						
						obj.ActiveDocument.Tables(2).Cell(2,3).Range.Text="\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_3", 4);
						selection.MoveDown("wdCell",6,"wdMove");
						obj.insertDocumentField("发文机关署名_8");
						
						
						obj.cursorToDocumentField("发文机关署名_8", 4);
						selection.MoveRight("wdCell",2,"wdMove");
						selection.TypeParagraph;
						obj.ActiveDocument.Tables(2).Cell(3,1).Range.Text="\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_6", 4);
						selection.MoveDown("wdCell",6,"wdMove");
						obj.insertDocumentField("发文机关署名_9");
						
						obj.ActiveDocument.Tables(2).Cell(3,2).Range.Text="\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_7", 4);
						selection.MoveDown("wdCell",6,"wdMove");
						obj.insertDocumentField("发文机关署名_10");
						
						obj.ActiveDocument.Tables(2).Cell(3,3).Range.Text="\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_8", 4);
						selection.MoveDown("wdCell",6,"wdMove");
						obj.insertDocumentField("发文机关署名_11");
						
						
						obj.cursorToDocumentField("发文机关署名_11", 4);
						selection.MoveRight("wdCell",2,"wdMove");
						selection.TypeParagraph;
						obj.ActiveDocument.Tables(2).Cell(4,1).Range.Text="\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_9", 4);
						selection.MoveDown("wdCell",6,"wdMove");
						obj.insertDocumentField("发文机关署名_12");
						
						obj.ActiveDocument.Tables(2).Cell(4,2).Range.Text="\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_10", 4);
						selection.MoveDown("wdCell",6,"wdMove");
						obj.insertDocumentField("发文机关署名_13");
						
						obj.ActiveDocument.Tables(2).Cell(4,3).Range.Text="\r\n\r\n\r\n\r\n\r\n";
						obj.cursorToDocumentField("发文机关署名_11", 4);
						selection.MoveDown("wdCell",6,"wdMove");
						obj.insertDocumentField("发文机关署名_14");
						
						for(var i = 0; i < countGw; i++){
							obj.setDocumentField("发文机关署名_"+(i+1), fwjgsm[i]);
						}
						break;
				}
			}else{//国产代码（代码实现方式与x86存在差异）国产无法使用"\r\n\r\n\r\n\r\n\r\n"
				switch(countGw){
					case 2:
						app.cursorToDocumentField("发文机关署名_1", 4);
						app.Selection.Cells.Delete(2);
						for(var i = 0; i < countGw; i++){
							app.setDocumentField("发文机关署名_"+(i+4), fwjgsm[i]);
						}
						break;
					case 3:
						app.cursorToDocumentField("发文机关署名_4", 4);
						app.Selection.Cells.Delete(2);
						
						app.cursorToDocumentField("发文机关署名_1", 4);
						app.ActiveDocument.DocumentFields.InsertionMode = "False";
						app.Selection.TypeParagraph();
						app.cursorToDocumentField("发文机关署名_2", 4);
						app.ActiveDocument.DocumentFields.InsertionMode = "False";
						app.Selection.TypeParagraph();
						app.cursorToDocumentField("发文机关署名_3", 4);
						app.ActiveDocument.DocumentFields.InsertionMode = "False";
						app.Selection.TypeParagraph();
						
						app.insertDocumentField("成文日期");
						for(var i = 0; i < countGw; i++){
							app.setDocumentField("发文机关署名_"+(i+1), fwjgsm[i]);
						}
						break;
					case 4://22
						app.cursorToDocumentField("发文机关署名_1", 4);
						app.Selection.Cells.Delete(2);
						app.deleteDocumentField("成文日期");

						var selection=app.ActiveDocument.ActiveWindow.Selection;
						app.ActiveDocument.Tables.Item(2).Rows.Item(1).Select();
						selection.MoveRight(1,1,0);
						selection.MoveLeft(1,1,0);
						selection.TypeParagraph();
						
						app.cursorToDocumentField("发文机关署名_4", 4);
						selection.MoveDown(5,3,0);
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,2,0);
						app.insertDocumentField("发文机关署名_6");//添加公文域
						
						app.cursorToDocumentField("发文机关署名_5", 4);
						selection.MoveDown(5,3,0);
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,2,0);
						app.insertDocumentField("发文机关署名_7");
						
						selection.MoveDown(5,1,0);
						app.insertDocumentField("成文日期");
						
						for(var i = 0; i < countGw; i++){
							app.setDocumentField("发文机关署名_"+(i+4), fwjgsm[i]);
						}
						break;
					case 5:
						for(var i = 0; i < countGw; i++){
							app.setDocumentField("发文机关署名_"+(i+1), fwjgsm[i]);
						}
						break;
					case 6://33
						app.cursorToDocumentField("发文机关署名_4", 4);
						app.Selection.Cells.Delete(2);
						
						app.cursorToDocumentField("发文机关署名_3", 4);
						var selection=app.ActiveDocument.ActiveWindow.Selection;
						selection.MoveRight(1,2,0);
						selection.TypeParagraph();
						app.ActiveDocument.Tables.Item(2).Rows.Item(2).Select();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,2,0);
						app.insertDocumentField("发文机关署名_4");
						
						app.cursorToDocumentField("发文机关署名_2", 4);
						selection.MoveDown(5,2,0);
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,2,0);
						app.insertDocumentField("发文机关署名_5");
						
						app.cursorToDocumentField("发文机关署名_3", 4);
						selection.MoveDown(5,2,0);
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,2,0);
						app.insertDocumentField("发文机关署名_6");
						
						selection.MoveDown(5,1,0);
						app.insertDocumentField("成文日期");
						
						for(var i = 0; i < countGw; i++){
							app.setDocumentField("发文机关署名_"+(i+1), fwjgsm[i]);
						}
						break;
					case 7://322
						app.deleteDocumentField("成文日期");
						var selection=app.ActiveDocument.ActiveWindow.Selection;
						app.ActiveDocument.Tables.Item(2).Rows.Item(2).Select();
						selection.MoveRight(1,1,0);
						selection.MoveLeft(1,1,0);
						selection.TypeParagraph();
						
						app.cursorToDocumentField("发文机关署名_4", 4);
						selection.MoveDown(5,3,0);
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,2,0);
						app.insertDocumentField("发文机关署名_6");//添加公文域
						
						app.cursorToDocumentField("发文机关署名_5", 4);
						selection.MoveDown(5,3,0);
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,2,0);
						app.insertDocumentField("发文机关署名_7");
						
						selection.MoveDown("wdCell",1,"wdMove");
						app.insertDocumentField("成文日期");
						
						for(var i = 0; i < countGw; i++){
							app.setDocumentField("发文机关署名_"+(i+1), fwjgsm[i]);
						}
						break;
					case 8://332
						app.cursorToDocumentField("发文机关署名_3", 4);
						var selection=app.ActiveDocument.ActiveWindow.Selection;
						selection.MoveRight(1,2,0);
						selection.TypeParagraph();
						app.ActiveDocument.Tables.Item(2).Rows.Item(2).Select();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,1,0);
						app.insertDocumentField("发文机关署名_6");

						app.cursorToDocumentField("发文机关署名_2", 4);
						selection.MoveDown(5,2,0);
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,1,0);
						app.insertDocumentField("发文机关署名_7");

						app.cursorToDocumentField("发文机关署名_3", 4);
						selection.MoveDown(5,1,0);
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,1,0);
						app.insertDocumentField("发文机关署名_8");
						
						for(var i = 0; i < countGw; i++){
							app.setDocumentField("发文机关署名_"+(i+1), fwjgsm[i]);
						}
						break;
					case 9://333
						app.cursorToDocumentField("发文机关署名_4", 4);
						app.Selection.Cells.Delete(2);
						
						app.cursorToDocumentField("发文机关署名_3", 4);
						var selection=app.ActiveDocument.ActiveWindow.Selection;
						selection.MoveRight(1,2,0);
						selection.TypeParagraph();
						app.ActiveDocument.Tables.Item(2).Rows.Item(2).Select();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,1,0);
						app.insertDocumentField("发文机关署名_4");

						app.cursorToDocumentField("发文机关署名_2", 4);
						selection.MoveDown(5,2,0);
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,1,0);
						app.insertDocumentField("发文机关署名_5");

						app.cursorToDocumentField("发文机关署名_3", 4);
						selection.MoveDown(5,2,0);
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,1,0);
						app.insertDocumentField("发文机关署名_6");

						app.cursorToDocumentField("发文机关署名_6", 4);
						selection.MoveRight(1,2,0);
						selection.TypeParagraph();
						app.ActiveDocument.Tables.Item(2).Rows.Item(3).Select();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,2,0);
						app.insertDocumentField("发文机关署名_7");

						app.cursorToDocumentField("发文机关署名_5", 4);
						selection.MoveDown(5,2,0);
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,2,0);
						app.insertDocumentField("发文机关署名_8");

						app.cursorToDocumentField("发文机关署名_6", 4);
						selection.MoveDown(5,2,0);
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,2,0);
						app.insertDocumentField("发文机关署名_9");
						
						selection.MoveDown(5,1,0);
						app.insertDocumentField("成文日期");
						
						for(var i = 0; i < countGw; i++){
							app.setDocumentField("发文机关署名_"+(i+1), fwjgsm[i]);
						}
						break;
					case 10://3322
						app.cursorToDocumentField("发文机关署名_3", 4);
						var selection=app.ActiveDocument.ActiveWindow.Selection;
						selection.MoveRight(1,2,0);
						selection.TypeParagraph();
						app.ActiveDocument.Tables.Item(2).Rows.Item(2).Select();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,1,0);
						app.insertDocumentField("发文机关署名_6");

						app.cursorToDocumentField("发文机关署名_2", 4);
						selection.MoveDown(5,2,0);
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,1,0);
						app.insertDocumentField("发文机关署名_7");

						app.cursorToDocumentField("发文机关署名_3", 4);
						selection.MoveDown(5,2,0);
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,1,0);
						app.insertDocumentField("发文机关署名_8");
						
						app.deleteDocumentField("成文日期");

						app.ActiveDocument.Tables.Item(2).Rows.Item(3).Select();
						selection.MoveRight(1,1,0);
						selection.MoveLeft(1,1,0);
						selection.TypeParagraph();
						
						app.cursorToDocumentField("发文机关署名_4", 4);
						selection.MoveDown(5,3,0);
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,2,0);
						app.insertDocumentField("发文机关署名_9");//添加公文域
						
						app.cursorToDocumentField("发文机关署名_5", 4);
						selection.MoveDown(5,3,0);
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,2,0);
						app.insertDocumentField("发文机关署名_10");
						
						selection.MoveDown(5,1,0);
						app.insertDocumentField("成文日期");
						
						for(var i = 0; i < countGw; i++){
							app.setDocumentField("发文机关署名_"+(i+1), fwjgsm[i]);
						}
						break;
					case 11://3332
						app.cursorToDocumentField("发文机关署名_3", 4);
						var selection=app.ActiveDocument.ActiveWindow.Selection;
						selection.MoveRight(1,2,0);
						selection.TypeParagraph();
						app.ActiveDocument.Tables.Item(2).Rows.Item(2).Select();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,1,0);
						app.insertDocumentField("发文机关署名_6");

						app.cursorToDocumentField("发文机关署名_2", 4);
						selection.MoveDown(5,2,0);
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,1,0);
						app.insertDocumentField("发文机关署名_7");

						app.cursorToDocumentField("发文机关署名_3", 4);
						selection.MoveDown(5,2,0);
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,1,0);
						app.insertDocumentField("发文机关署名_8");

						app.cursorToDocumentField("发文机关署名_8", 4);
						selection.MoveRight(1,2,0);
						selection.TypeParagraph();
						app.ActiveDocument.Tables.Item(2).Rows.Item(3).Select();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,1,0);
						app.insertDocumentField("发文机关署名_9");

						app.cursorToDocumentField("发文机关署名_7", 4);
						selection.MoveDown(5,2,0);
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,1,0);
						app.insertDocumentField("发文机关署名_10");

						app.cursorToDocumentField("发文机关署名_8", 4);
						selection.MoveDown(5,2,0);
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,1,0);
						app.insertDocumentField("发文机关署名_11");
						
						for(var i = 0; i < countGw; i++){
							app.setDocumentField("发文机关署名_"+(i+1), fwjgsm[i]);
						}
						break;
					case 12://3333
						app.cursorToDocumentField("发文机关署名_4", 4);
						app.Selection.Cells.Delete(2);
						
						app.cursorToDocumentField("发文机关署名_3", 4);
						var selection=app.ActiveDocument.ActiveWindow.Selection;
						selection.MoveRight(1,2,0);
						selection.TypeParagraph();
						app.ActiveDocument.Tables.Item(2).Rows.Item(2).Select();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,1,0);
						app.insertDocumentField("发文机关署名_4");

						app.cursorToDocumentField("发文机关署名_2", 4);
						selection.MoveDown(5,2,0);
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,1,0);
						app.insertDocumentField("发文机关署名_5");

						app.cursorToDocumentField("发文机关署名_3", 4);
						selection.MoveDown(5,2,0);
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,1,0);
						app.insertDocumentField("发文机关署名_6");

						app.cursorToDocumentField("发文机关署名_6", 4);
						selection.MoveRight(1,2,0);
						selection.TypeParagraph();
						app.ActiveDocument.Tables.Item(2).Rows.Item(3).Select();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,1,0);
						app.insertDocumentField("发文机关署名_7");

						app.cursorToDocumentField("发文机关署名_5", 4);
						selection.MoveDown(5,2,0);
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,1,0);
						app.insertDocumentField("发文机关署名_8");

						app.cursorToDocumentField("发文机关署名_6", 4);
						selection.MoveDown(5,2,0);
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,1,0);
						app.insertDocumentField("发文机关署名_9");
						
						app.cursorToDocumentField("发文机关署名_9", 4);
						selection.MoveRight(1,2,0);
						selection.TypeParagraph();
						app.ActiveDocument.Tables.Item(2).Rows.Item(4).Select();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,2,0);
						app.insertDocumentField("发文机关署名_10");

						app.cursorToDocumentField("发文机关署名_8", 4);
						selection.MoveDown(5,2,0);
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,2,0);
						app.insertDocumentField("发文机关署名_11");

						app.cursorToDocumentField("发文机关署名_9", 4);
						selection.MoveDown(5,2,0);
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,2,0);
						app.insertDocumentField("发文机关署名_12");
						
						selection.MoveDown("wdCell",1,"wdMove");
						app.insertDocumentField("成文日期");
						
						for(var i = 0; i < countGw; i++){
							app.setDocumentField("发文机关署名_"+(i+1), fwjgsm[i]);
						}
						break;
					case 13://33322
						app.cursorToDocumentField("发文机关署名_3", 4);
						var selection=app.ActiveDocument.ActiveWindow.Selection;
						selection.MoveRight(1,2,0);
						selection.TypeParagraph();
						app.ActiveDocument.Tables.Item(2).Rows.Item(2).Select();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,1,0);
						app.insertDocumentField("发文机关署名_6");

						app.cursorToDocumentField("发文机关署名_2", 4);
						selection.MoveDown(5,2,0);
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,1,0);
						app.insertDocumentField("发文机关署名_7");

						app.cursorToDocumentField("发文机关署名_3", 4);
						selection.MoveDown(5,2,0);
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,1,0);
						app.insertDocumentField("发文机关署名_8");

						app.cursorToDocumentField("发文机关署名_8", 4);
						selection.MoveRight(1,2,0);
						selection.TypeParagraph();
						app.ActiveDocument.Tables.Item(2).Rows.Item(3).Select();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,1,0);
						app.insertDocumentField("发文机关署名_9");

						app.cursorToDocumentField("发文机关署名_7", 4);
						selection.MoveDown(5,2,0);
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,1,0);
						app.insertDocumentField("发文机关署名_10");

						app.cursorToDocumentField("发文机关署名_8", 4);
						selection.MoveDown(5,2,0);
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,1,0);
						app.insertDocumentField("发文机关署名_11");
						
						app.deleteDocumentField("成文日期");

						app.ActiveDocument.Tables.Item(2).Rows.Item(4).Select();
						selection.MoveRight(1,1,0);
						selection.MoveLeft(1,1,0);
						selection.TypeParagraph();
						
						app.cursorToDocumentField("发文机关署名_4", 4);
						selection.MoveDown(5,3,0);
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,2,0);
						app.insertDocumentField("发文机关署名_12");//添加公文域
						
						app.cursorToDocumentField("发文机关署名_5", 4);
						selection.MoveDown(5,3,0);
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,2,0);
						app.insertDocumentField("发文机关署名_13");
						
						selection.MoveDown("wdCell",1,"wdMove");
						app.insertDocumentField("成文日期");
						
						for(var i = 0; i < countGw; i++){
							app.setDocumentField("发文机关署名_"+(i+1), fwjgsm[i]);
						}
						break;
					case 14:
						app.cursorToDocumentField("发文机关署名_3", 4);
						var selection=app.ActiveDocument.ActiveWindow.Selection;
						selection.MoveRight(1,2,0);
						selection.TypeParagraph();
						app.ActiveDocument.Tables.Item(2).Rows.Item(2).Select();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,1,0);
						app.insertDocumentField("发文机关署名_6");

						app.cursorToDocumentField("发文机关署名_2", 4);
						selection.MoveDown(5,2,0);
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,1,0);
						app.insertDocumentField("发文机关署名_7");
						
						app.cursorToDocumentField("发文机关署名_3", 4);
						selection.MoveDown(5,2,0);
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,1,0);
						app.insertDocumentField("发文机关署名_8");
						
						app.cursorToDocumentField("发文机关署名_8", 4);
						selection.MoveRight(1,2,0);
						selection.TypeParagraph();
						app.ActiveDocument.Tables.Item(2).Rows.Item(3).Select();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,1,0);
						app.insertDocumentField("发文机关署名_9");
						
						app.cursorToDocumentField("发文机关署名_7", 4);
						selection.MoveDown(5,2,0);
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,1,0);
						app.insertDocumentField("发文机关署名_10");
						
						app.cursorToDocumentField("发文机关署名_8", 4);
						selection.MoveDown(5,2,0);
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,1,0);
						app.insertDocumentField("发文机关署名_11");
						
						app.cursorToDocumentField("发文机关署名_11", 4);
						selection.MoveRight(1,2,0);
						selection.TypeParagraph();
						app.ActiveDocument.Tables.Item(2).Rows.Item(4).Select();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,1,0);
						app.insertDocumentField("发文机关署名_12");
						
						app.cursorToDocumentField("发文机关署名_10", 4);
						selection.MoveDown(5,2,0);
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,1,0);
						app.insertDocumentField("发文机关署名_13");
						
						app.cursorToDocumentField("发文机关署名_11", 4);
						selection.MoveDown(5,2,0);
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.TypeParagraph();
						selection.MoveUp(5,1,0);
						app.insertDocumentField("发文机关署名_14");
						
						for(var i = 0; i < countGw; i++){
							app.setDocumentField("发文机关署名_"+(i+1), fwjgsm[i]);
						}
						break;
				}
			}
		},
		get_Saved:function(){
			var isSaved = obj.get_Saved();
			return isSaved;
		},
		put_Saved:function(){
			obj.put_Saved(true);
		},
		
		setDocumentField:function(prom1, prom2){
			var check = setInterval(function(){
				if(state == 1){
					clearInterval(check);
					if(obj == null)
						return;
					if(MODE == 'liushi') {
						if(COMPANY == 'NEO') {
							obj.setDocumentField( prom1, prom2 );
						} else if(COMPANY == 'Yozo') {
							obj.setDocumentField( prom1, prom2 );
						} else if(COMPANY == 'WPS') {
							if(navigator.userAgent.indexOf("MSIE") > 0) {
								obj.setDocumentField( prom1, prom2 );
							} else {
								app.setDocumentField( prom1, prom2 );
							}
						}
					}
				}
			},200);
		},
		close:function(url, fileName){
			if(obj == null)
				return;
			if(MODE == 'liushi') {
				if(COMPANY == 'NEO') {
					if(obj.IsModified()){
						return false;   
					}
				} else if(COMPANY == 'Yozo') {
				} else if(COMPANY == 'WPS') {
					if(navigator.userAgent.indexOf("MSIE") > 0) {
					}else{
						app.close();
					}
				}
			} else if(MODE == 'banshi') {
				if(COMPANY == 'FX') {
					obj.closeFile();
				} else if(COMPANY == 'SK') {
				}
			}
			return true;
		},
		get_Count:function(){
			if(MODE == 'liushi'){
				if(COMPANY == 'WPS') {
					if(navigator.userAgent.indexOf("MSIE") > 0) {
						return 1;
					}else{
						return app.Documents.get_Count();
					}
				}
			}
		},
		getFileSize:function(saveformat){
			try{
				if(navigator.userAgent.indexOf("MSIE") > 0) {
					var size;
					size = obj.getFileSize("",saveformat);
					return size;
				}else{
					var size;
					size = app.getFileSize("",saveformat);
					return size;
//					return 0;
				}
			}catch(e){
				return "shengji";
			}
		},
		getQuanping:function(){
			if(navigator.userAgent.indexOf("MSIE") > 0) {
				obj.FullScreen();
			}else{
				app.FullScreen();
			}
		},
		setYeMa:function(){
			if(navigator.userAgent.indexOf("MSIE") > 0) {
				obj.DocObj.Application.Selection.GoTo(1,1,3);
				obj.DocObj.Application.ActiveWindow.ActivePane.View.SeekView = 9;
				obj.DocObj.Application.Selection.PageSetup.OddAndEvenPagesHeaderFooter = 1;
				obj.DocObj.Application.Selection.PageSetup.DifferentFirstPageHeaderFooter = 0;
				obj.DocObj.Application.Selection.Sections.Item(1).Footers.Item(1).LinkToPrevious = true;
				obj.DocObj.Application.Selection.Sections.Item(1).Headers.Item(1).LinkToPrevious = true;
				obj.DocObj.Application.ActiveWindow.ActivePane.View.SeekView = 10;
				obj.DocObj.Application.ActiveWindow.ActivePane.View.SeekView = 0;
			}else{
				app.Selection.GoTo(1,1,3)
				app.ActiveWindow.ActivePane.View.SeekView = 9;    
				app.Selection.PageSetup.OddAndEvenPagesHeaderFooter = 1;
				app.Selection.PageSetup.DifferentFirstPageHeaderFooter = 0;              
				app.Selection.Sections.Item(1).Footers.Item(1).LinkToPrevious = true; 
				app.Selection.Sections.Item(1).Headers.Item(1).LinkToPrevious = true;    
				app.ActiveWindow.ActivePane.View.SeekView = 10;
				app.ActiveWindow.ActivePane.View.SeekView = 0;
			}
		},
		setBiaoshi:function(){
			var Identification = $("#Identification").val();
			if(navigator.userAgent.indexOf("MSIE") > 0) {
				if(!isnull(Identification)){
					obj.setDocumentId(Identification);
				}
			}else{
				if(!isnull(Identification)){
					app.setDocumentId(Identification);
				}
			}
		},
		getBiaoshi:function(){
			if(navigator.userAgent.indexOf("MSIE") > 0) {
				return obj.getDocumentId();
			}else{
				return app.getDocumentId();
			}
		},
		deleteFuzhu:function(){
			if(navigator.userAgent.indexOf("MSIE") > 0) {
				obj.cursorToDocumentField("附注", 2);
				var selection=obj.ActiveDocument.Application.Selection;
				selection.MoveRight("wdCell",1,"wdMove");
				obj.backspace();
				obj.deleteDocumentField("附注");
				obj.backspace();
			}else{
				app.cursorToDocumentField("附注", 2);
				var selection=app.ActiveDocument.ActiveWindow.Selection;
				selection.MoveRight(1,1,0);
				app.backspace();
				app.deleteDocumentField("附注");
				app.backspace();
			}
		},
		guidangYema:function(){
			var num = "";
			if(navigator.userAgent.indexOf("MSIE") > 0) {
				num = obj.ActiveDocument.BuiltInDocumentProperties(14);
				return num+"";
			}else{
				num = app.ActiveDocument.BuiltInDocumentProperties.get_Item(14).Value;
				return num;
			}
		}
	}
}( this.jQuery, this ));
