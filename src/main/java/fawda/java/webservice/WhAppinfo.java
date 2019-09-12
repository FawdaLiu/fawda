package fawda.java.webservice;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "appinfo")
public class WhAppinfo {

    private String appid = "";

    private String appname = "";

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    @Override
    public String toString() {
        return "WhAppinfo [appid=" + appid + ", appname=" + appname + "]";
    }

}
