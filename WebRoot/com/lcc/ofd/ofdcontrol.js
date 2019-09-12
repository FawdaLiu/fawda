var obj;
function fx_init() {
    // alert(11)
    obj = OFD.OCX.init("objectdiv", 778, 700);
    obj.ready(obj._id);
    // fx_openFile();
    // fx_read();
}
function fx_openFile(strPath) {
    // alert(strPath)
    if (strPath == "") {
        return false;
    } else {
        var res = "";
        // if (strPath.indexOf("http://") != -1) {
        // res = obj.openFile(encodeURI(strPath));
        // } else {
        // alert(1)
        res = obj.openFile(strPath);
        // }
        // fx_read();
        // fx_signature()
    }
}

function getRootPath() {
    return "http://" + window.location.host + "" + root;
    // return "http://" + window.location.host + "";
}
function fx_openFiledown(strData, ofdName, isread) {
    isread = isread || 0;
    var strURL = getRootPath() + "/FXUploadPage";

    strURL = strURL + "?dataSource=" + strData + "&OFDName=" + ofdName + "&R="
            + Math.random();
    var res = obj.openFile(strURL);
    if (isread == 1) {
        fx_read(false);
    } else {
        fx_read(true);
    }
    // fx_read();
    // fx_signature()
}

// 保存上传ofd
function fx_saveURLFile(strurl, uuid, name, path) {

    if (strurl == "") {
        strurl = getRootPath() + "/FXUploadPage?uuid=" + uuid;
    }
    if (name == "") {
        name = uuid + ".ofd";
    }
    var url_ = strurl + "&ofdname=" + name + "&path=" + path + "&R="
            + Math.random();
    // obj.saveFile("http://localhost:8080/FXUploadPage?uuid=3&ofdname=44444444.ofd");
    url_ = encodeURI(url_);
    var res = obj.saveFile(url_);
    return res;
    // var res =
    // obj.saveFile("http://demo17.foxitsoftware.com/foxit-upload-ofd/fileupload.do?fileid=3fa915da4864455db6f429f9226dc635&userid=03d1ac9b44f94fbab79003ab009508a1&uploadpage=fileupload.do&updatename=&flag=1");

}

// ����Ϊָ�����ļ�
function fx_saveFile1() {
    // alert(2222);
    // alert(obj)
    var res = obj.saveFile("D:\\TempFiles\\1.ofd");

}
// ����Ϊָ�����ļ�
function fx_saveFile2(strPath) {
    // alert(2222);
    // alert(obj)
    var res = obj.saveFile(strPath);

}

// �رյ�ǰ���ĵ�
function fx_closeFile() {
    // alert(1)
    // var res = obj.closeFile();

}
// 设置将要应用的印章名称
function fx_setSealName() {
    var sealName = document.getElementById("sealName").value;
    if (sealName == "") {
        alert("请输入将要应用的印章名称");
        return;
    }
    obj.setSealName(sealName);
}
// 设置将要应用的印章标识
function fx_setSealId() {
    var sealId = document.getElementById("sealId").value;
    if (sealId == "") {
        alert("请输入将要应用的印章标识");
        return;
    }
    obj.setSealId(sealId);
}
// 设置将要应用的签名算法
function fx_setSealSignMethod() {
    var signMethod = document.getElementById("signMethod").value;
    if (signMethod == "") {
        alert("请输入将要应用的签名算法");
        return;
    }
    obj.setSealSignMethod(signMethod);
}
function fx_setCompositeVisible() {
    var selobj = document.getElementById("btnname");
    var index = selobj.selectedIndex;
    var value = selobj.options[index].value;
    var chkObjs = document.getElementsByName("btncom");
    var chkvaule = "";
    for (var i = 0; i < chkObjs.length; i++) {
        if (chkObjs[i].checked) {
            chkvaule = chkObjs[i].value;
        }
    }
    obj.setCompositeVisible(value, chkvaule);
}

function fx_read(bl) {
    obj.setCompositeVisible("saveas", bl);
    // obj.setCompositeVisible("upload", 1);
    obj.setCompositeVisible("sign", bl);
    // obj.setCompositeVisible("verify", true);
    // alert(1)
}

function fx_signature() {
    // alert(11)
    // obj.setCompositeVisible("signature",1);
}
// 0 hong 1hei
function fx_print(str) {
    var fs = "";
    try {
        fs = document.all.FileCount.value
    } catch (e) {
        fs = "";
    }
    var pro = prompt("请输入打印的份数", fs);
    if (pro != null && pro != "") {
        obj.setPrintInfo(pro);
        obj.PrintFile('objectdiv', str);
    }
}