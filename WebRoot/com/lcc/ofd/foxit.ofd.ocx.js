var OFD = {
    _OCX_Array: [],
    find: function (id) {
        var ocx;
        this._each(this._OCX_Array, function (i, e) {
            if (e._id == id) {
                ocx = e;
                return false;
            }
        });
        return ocx;
    },
    _extend: function (defs, target) {
        if (!target) {
            return defs;
        }
        this._each(defs, function (n, v) {
            if (!(n in target)) {
                target[n] = v;
            }
        });
        return target;
    },
    // 判断参数是否是数组
    _isArray: function (v) {
        return Object.prototype.toString.call(v) === "[object Array]";
    },
    // 判断参数是否是undefined或null
    _isNull: function (v) {
        return typeof v == "undefined" || (v != 0 && !v);
    },
    // 判断参数是有有效值
    _isValid: function (v) {
        return !this._isNull(v);
    },
    // getElementById
    _$: function (id) {
        return document.getElementById(id);
    },
    // createElement
    _new: function (tag) {
        return document.createElement(tag);
    },
    // for each like jquery
    _each: function (o, fn) {
        if (this._isArray(o)) {
            for (var i = 0, ol = o.length, val = o[0]; i < ol
					&& fn.call(val, i, val) !== false; val = o[++i]) {
            }
        } else {
            for (var i in o) {
                if (fn.call(o[i], i, o[i]) === false) {
                    break;
                }
            }
        }
        return o;
    },
    // 一些页面方法
    Page: {
        // 获取窗口宽度
        width: function () {
            var w = 0;
            if (window.innerWidth) {
                w = window.innerWidth;
            } else if ((document.body) && (document.body.clientWidth)) {
                w = document.body.clientWidth;
            }
            // 通过深入Document内部对body进行检测，获取窗口大小
            if (document.documentElement
					&& document.documentElement.clientHeight
					&& document.documentElement.clientWidth) {
                w = document.documentElement.clientWidth;
            }
            return w;
        },
        // 获取窗口高度
        height: function () {
            var h = 0;
            if (window.innerHeight) {
                h = window.innerHeight;
            } else if ((document.body) && (document.body.clientHeight)) {
                h = document.body.clientHeight;
            }
            // 通过深入Document内部对body进行检测，获取窗口大小
            if (document.documentElement
					&& document.documentElement.clientHeight
					&& document.documentElement.clientWidth) {
                h = document.documentElement.clientHeight;
            }
            return h;
        },
        // 兼容FF和IE的事件，当e未定义的时候返回window.event
        dEvent: function (e) {
            if (!e) {
                return window.event;
            } else {
                return e;
            }
        }
    }
};
// 定义提示框对象
OFD.MsgBox = {
    // 创建一个元素，并赋予其各种属性
    _new: function (tag, id, css, type, value, style) {
        var e = OFD._new(tag);
        if (OFD._isValid(id)) {
            e.id = id;
        }
        this._css(e, css);
        if (OFD._isValid(type)) {
            e.type = type;
        }
        if (OFD._isValid(value)) {
            e.value = value;
        }
        if (OFD._isValid(style)) {
            OFD._each(style, function (n, v) {
                e.style[n] = v;
            });
        }
        return e;
    },
    // 对象转为字符串,用;号分割
    _o2s: function (obj) {
        var r = [];
        OFD._each(obj, function (n, v) {
            r.push(n + ":" + v);
        });
        if (r.length > 0) {
            return r.join(";");
        }
        return "";
    },
    // 设置元素的css
    _css: function (e, css) {
        if (OFD._isValid(css)) {
            if (typeof css == "string") {
                e.style.cssText = css;
            } else {
                var text = this._o2s(css);
                e.style.cssText = text;
            }
        }
    },
    // 初始化对话框的DOM对象，并设置相关的样式
    init: function (msg, title) {
        // 覆盖背景，将当前页面其他内容覆盖
        this.background = this._new("div", "maskDiv", {
            "position": "absolute",
            "top": "0",
            "left": "0",
            "filter": "alpha(opacity = 30)",
            "-moz-opacity": "0.3",
            "opacity": "0.3",
            "background": "#000",
            "z-index": "9990"
        }, null, null, {
            "width": OFD.Page.width() + "px",
            "height": OFD.Page.height() + "px"
        });
        // 标题，对话框标题容器
        this.titleContainer = this._new("div", "messageBoxTitle", {
            "height": "20px",
            "padding": "4px",
            "cursor": "move"
        });
        // 对话框标题内容，如果为空则自动显示“消息”
        var titleText;
        if (!title) {
            titleText = "提示";
        } else {
            titleText = title;
        }
        this.titleObj = document.createTextNode(titleText);
        this.titleSpan = this._new("span", null, {
            "float": "left"
        });
        this.titleSpan.appendChild(this.titleObj);
        this.titleContainer.appendChild(this.titleSpan);

        var closeBtnCSS = {
            "float": "right",
            "background": "#e3f1ff",
            "border": "none",
            "color": "#96caff",
            "font-size": "12px"
        };
        var closeBtnHover = OFD._extend(closeBtnCSS, {
            "background": "#f00",
            "color": "#fff",
            "-moz-border-radius": "3px",
            "-webkit-border-radius": "3px"
        });
        this.closeBtn = this._new("input", "closeBtn", closeBtnCSS, "button",
				"X");
        var cb = this.closeBtn;
        this.closeBtn.onmouseover = function () {
            OFD.MsgBox._css(cb, closeBtnHover);
        };
        this.closeBtn.onmouseout = function () {
            OFD.MsgBox._css(cb, closeBtnCSS);
        };
        this.titleContainer.appendChild(this.closeBtn);

        // 消息内容
        this.messageContainer = this._new("div", "messageContainer", {
            "padding": "12px 4px",
            /* "padding-left":"90px", */
            "background": "#eef7ff",
            "margin": "0 4px",
            "border": "1px solid #fff",
            "-moz-border-radius": "2px",
            "-webkit-border-radius": "2px"
        });
        this.messageStr = msg;
        // this.messageObj = document.createTextNode(msg);
        this.messageObj = OFD._new("div");
        this.messageObj.innerHTML = this.messageStr;
        this.messageContainer.appendChild(this.messageObj);
        this.footContainer = this._new("div", "messageBoxFoot", {
            "position": "relative",
            "height": "20px",
            "padding": "4px"
        });
        // 确定按钮
        this.confirmBtn = this._new("input", "okBtn", {
            "float": "right",
            "border": "1px solid #fff",
            "-moz-border-radius": "3px",
            "-webkit-border-radius": "3px",
            "background": "#c0ddfb",
            "line-height": "24px"
        }, "button", "确定");
        this.footContainer.appendChild(this.confirmBtn);
        // 消息框的整体
        this.messageBox = this._new("div", "messageBox", {
            "width": "308px",
            "position": "absolute",
            "background": "#e3f1ff",
            "border": "solid 1px #b3d7ff",
            "opacity": "1",
            "z-index": "9999",
            "-moz-border-radius": "3px",
            "-webkit-border-radius": "3px",
            "font-size": "12px",
            "line-height": "24px"
        }, null, null, {// 对话框的定位，根据宽度和高度自动生成定位
            "top": parseInt(OFD.Page.height()) / 2 - 100 + "px",
            "left": parseInt(OFD.Page.width()) / 2 - 150 + "px"
        });
        // 将元素添加到对话框中
        this.messageBox.appendChild(this.titleContainer);
        this.messageBox.appendChild(this.messageContainer);
        this.messageBox.appendChild(this.footContainer);
    },
    // 对话框释放函数
    dispose: function () {
        var background = OFD._$("maskDiv");
        var messageBox = OFD._$("messageBox");
        document.body.removeChild(background);
        document.body.removeChild(messageBox);
    },
    // 对话框显示的主方法
    show: function (msg, title) {
        // 初始化对话框基本元素
        this.init(msg, title);
        // 将对话框以及背景加入到body中
        document.body.appendChild(this.background);
        document.body.appendChild(this.messageBox);
        // 定义按钮，当点击按钮的时候关闭对话框
        this.confirmBtn.onclick = this.dispose;
        this.closeBtn.onclick = this.dispose;
        // 定义对话框的拖动事件
        var nowTitleContainer = this.titleContainer;
        // 当在标题上点击的时候开始实现拖动
        this.titleContainer.onmousedown = function (e) {
            var messageBox = OFD._$("messageBox");
            var startX = OFD.Page.dEvent(e).clientX;
            var startY = OFD.Page.dEvent(e).clientY;
            var offsetLeft = messageBox.offsetLeft;
            var offsetTop = messageBox.offsetTop;
            // 当鼠标移动到时候对话框跟随鼠标移动，为了防止作用的区域过小对话框移动出问题，这个地方选择了对body对象注册mousemove事件
            document.body.onmousemove = function (e) {
                // 设置对话框的具体坐标
                messageBox.style.top = offsetTop + OFD.Page.dEvent(e).clientY
						- startY + "px";
                messageBox.style.left = offsetLeft + OFD.Page.dEvent(e).clientX
						- startX + "px";
            };
            // 当鼠标释放即mouseup事件触发后注销body的mousemove事件同时注销body的mouseup事件自身，以防止事件污染
            document.body.onmouseup = function () {
                document.body.onmousemove = null;
                document.body.onmouseup = null;
            };
        };
    }
};

// OCX类型及方法
OFD.OCX = function (options) {
    // OCX的CLSID
    this.clsid = "9A9F603B-51A8-4630-AE99-4BBF01675575";
    // OCX的Object ID
    this._id;
    // 配置
    this.opts = OFD._extend({
        width: "100%",
        height: OFD.Page.height() + "px",
        compsite: {
            "toolstate handtool": false,
            "toolstate selecttext": false,
            "vnavigator outline": false,
            "ExportStream": false,
            "open": false,
            "print": false
        },
        loadMsg: "<span>正在加载控件，请稍候....</span>",
        // 控件安装程序的下载路径
        downURL: null
    }, options);
    // 控件对象
    this.ax;
    // 缓存用户操作.因为某些情况下,用户操作时,控件还没有初始化完毕
    this._optCache = {
        compsite: [],
        callback: [],
        open: []
    };

    // 加载控件
    this.load = function () {
        if (!("div" in this.opts)) {
            OFD.MsgBox.show("请指定一个div,以便写入ActiveX!","错误信息");
            // return;
            // 新建一个div放置控件,并追加到body的最后
            var newDiv = OFD._new("div");
            newDiv.id = "ofd_div_" + this._randomString(5);
            document.body.appendChild(newDiv);
            this.opts.div = newDiv.id;
        }
        var div = OFD._$(this.opts.div);
        if (this._hasActiveX()) {
            div.innerHTML = this.opts.loadMsg;
            this._id = "ofd_ocx_" + this._randomString(10);
            OFD._OCX_Array.push(this); // 放入队列,以方便查找使用
            this._writeOCX(div);
        } else {
            div.innerHTML = "<span style='color:red;'>加载ActiveX控件失败!</span>";
        }
        return this;
    };

    // 加载配置,完成准备工作,只执行一次
    this.ready = function () {
        if (this.ax) {// 已经初始化
            return this;
        }
        var o = OFD._$(this._id);
        if (!o || !("openFile" in o)) {
            OFD.MsgBox.show("控件没有正确初始化!");
            div.innerHTML = "<span>ActiveX控件未正确初始化!</span>";
            return;
        }
        this.ax = o; // 赋值,很重要	
        return this;
    };

    // 生成随机串
    this._randomString = function (l) {
        var x = "0123456789qwertyuioplkjhgfdsazxcvbnm";
        var tmp = "";
        for (var i = 0; i < l; i++) {
            tmp += x.charAt(Math.ceil(Math.random() * 100000000) % x.length);
        }
        return tmp;
    };

    // 判断是否安装了OFD控件
    this._hasActiveX = function () {
        if ("ActiveXObject" in window) {// 判断是否IE
            try {// 判断是否安装OFD阅读器
                return new ActiveXObject(
						"FoxitReader.FoxitReaderCtl");
            } catch (e) {
                var html = "OFD阅读控件没有正确安装，请下载安装！";
                if (OFD._isValid(this.opts.downURL)) {
                    html += "<br><a href='"// 
							+ this.opts.downURL //
							+ "' target='_blank'>下载</a>";
                }
                html += "<br>由于安装程序会更改IE的安全设置并注册dll文件，一些安全软件（如360安全卫士）可能会弹出安全警告，允许本软件继续即可。<br>建议使用管理员权限运行本软件。";
                OFD.MsgBox.show(html);
            }
        } else {
            OFD.MsgBox.show("无法显示ActiveX控件,请使用IE访问");
        }
        return false;
    };

    // 输出OCX的Object HTML
    this._writeOCX = function (div) {
        OFD._$(this.opts.div).innerHTML = "<object id='" + this._id //
				+ "' width='"//
				+ this.opts.width//
				+ "' height='"//
				+ this.opts.height//
				+ "' classid='CLSID:" + this.clsid + "'>"
				+ "<param name='object_id' value = '" + this._id + "'> "//				
				+ "</object>";
    };

    // 检查组件是否准备完毕
    this._check = function () {
        return OFD._isValid(this.ax);
    };

    // 显示和隐藏组件
    this.setCompositeVisible = function (name, visible) {
        if (this._check()) {
            if (OFD._isArray(name)) {
                for (var i in name) {
                    var n = name[i];
                    this.ax.setCompositeVisible(n, visible);
                }
            } else {
                this.ax.setCompositeVisible(name, visible);
            }
        } else {
            this._optCache.compsite.push({
                n: name,
                v: visible
            });
        }
        return this;
    };

    // 设置回调
    this.setCallback = function (name, func, after) {
        if (this._check()) {
            this.ax.setCallback(name, func, after);
        } else {
            this._optCache.callback.push({
                n: name,
                f: func,
                a: after
            });
        }
        return this;
    };

    // 打开文件
    this._open = function (path,isread) {
        if (this._check()) {
            try {
                if (this.ax.openFile(path,isread) == 1)
                    return false;
                else
                    return true;
            } catch (e) {

            }
          
        } else {
            this._optCache.open.push({
                p: path
            });
            return false;
        }

    };
    this._save = function (path) {
        if (this._check()) {
            if (this.ax.saveFile(path) == 1)
                return false;
            else
                return true;
        }
        else
            return false;
    }
    this._close = function () {
        if (this._check()) {
            if (this.ax.closeFile() == 1)
                return false;
            else
                return true;
        }
        else
            return false;
    }
    this._setSealName = function (strSealName) {
        if (this._check()) {
            this.ax.setSealName(strSealName);
        }
    }
    this._setSealId = function (strSealId) {
        if (this._check()) {
            this.ax.setSealId(strSealId);
        }
    }
    this._setSealSignMethod = function (strSignMethod) {
        if (this._check()) {
            this.ax.setSealSignMethod(strSignMethod);
        }
    }
    this._setCompositeVisible = function (strCompName, bVisible) {
        if (this._check()) {
            this.ax.setCompositeVisible(strCompName, bVisible);
        }
    }
    this._setViewPreference=function(key,value){
    	if(this._check()){
    		this.ax.setViewPreference(key,value);
    	}
    }
    this._setLogURL=function(logurl,oid){
    	if(this._check()){
    		this.ax.setLogURL(logurl,oid);
    	}
    }
    this._addTrackInfo=function(xmlParam){
    	if(this._check()){
    		this.ax.addTrackInfo(xmlParam);
    	}
    }
    this._setPrintInfo=function(num){
    	if(this._check()){
    		this.ax.setPrintInfo(num);
    	}
    }
    this._PrintFile = function (title, printcolor) {
    	if(this._check()){
    	    this.ax.PrintFile(title, printcolor);
    	}
    }
    this._getlogFilePath=function(){
    	if(this._check()){
    		return this.ax.getLogFilePath();		
    	}
    }
    // 打开文件
    this.openFile = function (url,isread) {
        return this._open(url,isread);
    };
    //保存文件
    this.saveFile = function (url) {
        return this._save(url);
    }
    //关闭文件
    this.closeFile = function () {
        return this._close();
    }
    //设置将要应用的印章名称
    this.setSealName = function (strSealName) {
        return this._setSealName(strSealName);
    }
    //设置将要应用的印章标识
    this.setSealId = function (strSealId) {
        return this._setSealId(strSealId);
    }
    //设置将要应用的签名算法
    this.setSealSignMethod = function (strSignMethod) {
        return this._setSealSignMethod(strSignMethod);
    }
    //设置Reader界面按钮或组件是否可见
    this.setCompositeVisible = function (strCompName, bVisible) {
        return this._setCompositeVisible(strCompName, bVisible);
    }
    //导航栏
    this.setViewPreference=function(key,value){
    	return this._setViewPreference(key, value);
    }
    //日志上传
    this.setLogURL=function(logurl,oid){
    	return this._setLogURL(logurl, oid);
    }
    //二维码
    this.addTrackInfo=function(xmlParam){
    	return this._addTrackInfo(xmlParam);
    }
    //打印控制
    this.setPrintInfo=function(num){
    	return this._setPrintInfo(num);
    }
    //打印
    //title文档标题，
    this.PrintFile=function(title,printcolor){
        return this._PrintFile(title, printcolor);
    }
    //获取打印日志内容
    this.getlogContent=function(){
    	return this._getlogFilePath();
    }
};

// 控件加载完毕后的回调方法
var onOFDLoaded = function (objID) {
    if (OFD._isValid(objID)) {
        var ocx = OFD.find(objID);
        if (ocx) {
            ocx.ready();
        }
    }
};

// 快速加载并初始化OCX控件
OFD.OCX.init = function (divID, width, height) {
    var config = {};
    if (OFD._isValid(divID)) {
        config.div = divID;
    }
    if (OFD._isValid(width)) {
        config.width = width;
    }
    if (OFD._isValid(height)) {
        config.height = height;
    }
    return new OFD.OCX(config).load();
};
