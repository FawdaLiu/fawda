package fawda.java.webservice;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "edoc")
public class WhEdoc {
    
    private WhAppinfo appinfo = new WhAppinfo();
    
    private WhForminfo forminfo = new WhForminfo();

    public WhAppinfo getAppinfo() {
        return appinfo;
    }

    @XmlElement(name = "appinfo")
    public void setAppinfo(WhAppinfo appinfo) {
        this.appinfo = appinfo;
    }

    public WhForminfo getForminfo() {
        return forminfo;
    }

    @XmlElement(name = "forminfo")
    public void setForminfo(WhForminfo forminfo) {
        this.forminfo = forminfo;
    }

    @Override
    public String toString() {
        return "WhEdoc [appinfo=" + appinfo + ", forminfo=" + forminfo + "]";
    }
    
}
