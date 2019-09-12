package fawda.java.webservice;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author liuyl@risen.com 2018年5月29日下午12:53:04
 *
 */
@XmlType(propOrder = { "appuri", "appname", "depturi", "deptname", "sendnumber", "sendstartnum", "sort", "isbarcode"})
@XmlRootElement(name = "dept")
public class WhDept {
    
    /** TY11，TY12 ，TY13  发送对象为个人、单位、应用 */
    private String recvtype = "";

    /** TY13时一定要填写 */
    private String appuri = "";

    /** TY13时一定要填写 */
    private String appname = "";

    /** 交换件的目的单位标识 */
    private String depturi = "";

    /** 交换件的目的单位名称 */
    private String deptname = "";

    /** 发送份数 */
    private String sendnumber = "";

    /** 起始流水号，没有流水号就为0 */
    private String sendstartnum = "";

    private String sort = "";

    /** 文件是否有二维码 */
    private String isbarcode = "";
    
    @XmlAttribute(name = "recvtype")
    public String getRecvtype() {
        return recvtype;
    }

    public void setRecvtype(String recvtype) {
        this.recvtype = recvtype;
    }

    public String getAppuri() {
        return appuri;
    }

    public void setAppuri(String appuri) {
        this.appuri = appuri;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getDepturi() {
        return depturi;
    }

    public void setDepturi(String depturi) {
        this.depturi = depturi;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getSendnumber() {
        return sendnumber;
    }

    public void setSendnumber(String sendnumber) {
        this.sendnumber = sendnumber;
    }

    public String getSendstartnum() {
        return sendstartnum;
    }

    public void setSendstartnum(String sendstartnum) {
        this.sendstartnum = sendstartnum;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getIsbarcode() {
        return isbarcode;
    }

    public void setIsbarcode(String isbarcode) {
        this.isbarcode = isbarcode;
    }

    @Override
    public String toString() {
        return "WhDept [appuri=" + appuri + ", appname=" + appname + ", depturi=" + depturi + ", deptname=" + deptname + ", sendnumber=" + sendnumber
                + ", sendstartnum=" + sendstartnum + ", sort=" + sort + ", isbarcode=" + isbarcode + "]";
    }

}
