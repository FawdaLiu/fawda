package fawda.java.webservice;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Administrator
 *
 */
@XmlRootElement(name = "forminfo")
public class WhForminfo {

    /** 同文件发送接口中的参数docID */
    private String formid = "";

    private String title = "";

    /** 交换模式：直接发送，默认EM01 */
    private String exchmode = "";

    /** 紧急程度：IL02-加急 IL03-急件 IL04-平件 */
    private String instancy = "";

    /** 默认值：TY03 */
    private String sendertype = "";

    /** 保密等级：MJ01-机密 MJ02-秘密 MJ03-平件 内部 MJ04 其他MJ09 */
    private String seclevel = "";

    /** 交换发送的单位标识 */
    private String fromdepturi = "";

    /** 交换发送的单位名称 */
    private String fromdeptname = "";

    /** 个人发文时为个人用户URI,单位发文时为操作人URI,应用发文时为应用系统URI，这里默认是应用UR */
    private String bookdepturi = "";

    private String bookdeptname = "";

    /** 交换发送的应用系统标识 */
    private String senderuri = "";

    /** 交换发送的应用系统名称 */
    private String sendername = "";

    /** 交换发送的用户标识 */
    private String operuri = "";

    /** 交换发送的用户名称 */
    private String opername = "";

    private String booktype = "";

    private String docformat = "";

    private String exchareatag = "";

    private String doctype = "";

    private String pagesum = "";

    private String filedate = "";

    private String topic = "";

    private String keywords = "";

    private String summary = "";

    /** 附加信息 */
    private String remark = "";

    private String bookindex = "";

    private String docnum = "";

    private String rep_type = "";

    private String task_id = "";

    /** base64编码 */
    private String data = "";

    /** base64编码 */
    private String extdata = "";

    private WhTodeptinfo todeptinfo = new WhTodeptinfo();

    public String getFormid() {
        return formid;
    }

    public void setFormid(String formid) {
        this.formid = formid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExchmode() {
        return exchmode;
    }

    public void setExchmode(String exchmode) {
        this.exchmode = exchmode;
    }

    public String getInstancy() {
        return instancy;
    }

    public void setInstancy(String instancy) {
        this.instancy = instancy;
    }

    public String getSendertype() {
        return sendertype;
    }

    public void setSendertype(String sendertype) {
        this.sendertype = sendertype;
    }

    public String getSeclevel() {
        return seclevel;
    }

    public void setSeclevel(String seclevel) {
        this.seclevel = seclevel;
    }

    public String getFromdepturi() {
        return fromdepturi;
    }

    public void setFromdepturi(String fromdepturi) {
        this.fromdepturi = fromdepturi;
    }

    public String getFromdeptname() {
        return fromdeptname;
    }

    public void setFromdeptname(String fromdeptname) {
        this.fromdeptname = fromdeptname;
    }

    public String getBookdepturi() {
        return bookdepturi;
    }

    public void setBookdepturi(String bookdepturi) {
        this.bookdepturi = bookdepturi;
    }

    public String getBookdeptname() {
        return bookdeptname;
    }

    public void setBookdeptname(String bookdeptname) {
        this.bookdeptname = bookdeptname;
    }

    public String getSenderuri() {
        return senderuri;
    }

    public void setSenderuri(String senderuri) {
        this.senderuri = senderuri;
    }

    public String getSendername() {
        return sendername;
    }

    public void setSendername(String sendername) {
        this.sendername = sendername;
    }

    public String getOperuri() {
        return operuri;
    }

    public void setOperuri(String operuri) {
        this.operuri = operuri;
    }

    public String getOpername() {
        return opername;
    }

    public void setOpername(String opername) {
        this.opername = opername;
    }

    public String getBooktype() {
        return booktype;
    }

    public void setBooktype(String booktype) {
        this.booktype = booktype;
    }

    public String getDocformat() {
        return docformat;
    }

    public void setDocformat(String docformat) {
        this.docformat = docformat;
    }

    public String getExchareatag() {
        return exchareatag;
    }

    public void setExchareatag(String exchareatag) {
        this.exchareatag = exchareatag;
    }

    public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    public String getPagesum() {
        return pagesum;
    }

    public void setPagesum(String pagesum) {
        this.pagesum = pagesum;
    }

    public String getFiledate() {
        return filedate;
    }

    public void setFiledate(String filedate) {
        this.filedate = filedate;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBookindex() {
        return bookindex;
    }

    public void setBookindex(String bookindex) {
        this.bookindex = bookindex;
    }

    public String getDocnum() {
        return docnum;
    }

    public void setDocnum(String docnum) {
        this.docnum = docnum;
    }

    public String getRep_type() {
        return rep_type;
    }

    public void setRep_type(String rep_type) {
        this.rep_type = rep_type;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getExtdata() {
        return extdata;
    }

    public void setExtdata(String extdata) {
        this.extdata = extdata;
    }

    public WhTodeptinfo getTodeptinfo() {
        return todeptinfo;
    }

    public void setTodeptinfo(WhTodeptinfo todeptinfo) {
        this.todeptinfo = todeptinfo;
    }

    @Override
    public String toString() {
        return "WhForminfo [formid=" + formid + ", title=" + title + ", exchmode=" + exchmode + ", instancy=" + instancy + ", sendertype="
                + sendertype + ", seclevel=" + seclevel + ", fromdepturi=" + fromdepturi + ", fromdeptname=" + fromdeptname + ", bookdepturi="
                + bookdepturi + ", bookdeptname=" + bookdeptname + ", senderuri=" + senderuri + ", sendername=" + sendername + ", operuri=" + operuri
                + ", opername=" + opername + ", booktype=" + booktype + ", docformat=" + docformat + ", exchareatag=" + exchareatag + ", doctype="
                + doctype + ", pagesum=" + pagesum + ", filedate=" + filedate + ", topic=" + topic + ", keywords=" + keywords + ", summary=" + summary
                + ", remark=" + remark + ", bookindex=" + bookindex + ", docnum=" + docnum + ", rep_type=" + rep_type + ", task_id=" + task_id
                + ", data=" + data + ", extdata=" + extdata + ", todeptinfo=" + todeptinfo + "]";
    }

}
