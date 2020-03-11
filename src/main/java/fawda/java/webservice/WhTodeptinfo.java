package fawda.java.webservice;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "todeptinfo")
public class WhTodeptinfo {
    
    /** TY11，TY12 ，TY13  发送对象为个人、单位、应用 */
    private List<WhDept> dept = new ArrayList<WhDept>();

    @XmlElementWrapper(name = "list")
    @XmlElement(name = "dept")
    public List<WhDept> getDept() {
        return dept;
    }

    public void setDept(List<WhDept> dept) {
        this.dept = dept;
    }
}
