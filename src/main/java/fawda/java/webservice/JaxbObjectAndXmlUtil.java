package fawda.java.webservice;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class JaxbObjectAndXmlUtil {

    /**
     * @param xmlStr
     *            字符串
     * @param objcet 对象Class类型
     * @return 对象实例
     */
    @SuppressWarnings("unchecked")
    public static <T> T xml2Object(String xmlStr, Class<T> objcet) {
        try {
            JAXBContext context = JAXBContext.newInstance(objcet);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            T t = (T) unmarshaller.unmarshal(new StringReader(xmlStr));
            return t;
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param object
     *            对象
     * @return 返回xmlStr
     */
    public static String object2Xml(Object object) {
        try {
            StringWriter writer = new StringWriter();
            JAXBContext context = JAXBContext.newInstance(object.getClass());
            Marshaller marshal = context.createMarshaller();

            // 决定是否在转换成xml时同时进行格式化（即按标签自动换行，否则即是一行的xml）
            marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            // xml编码格式,默认为utf-8
            marshal.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            // 是否省略xml头信息
            marshal.setProperty(Marshaller.JAXB_FRAGMENT, true);
            // 设置编码格式 utf-8
            marshal.setProperty("jaxb.encoding", "utf-8");
            marshal.marshal(object, writer);

            return new String(writer.getBuffer());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static void main(String[] args) {
        /** 构造测试报文头对象 */
        WhDept dept = new WhDept();
        dept.setAppuri("");
        dept.setAppname("");
        dept.setDepturi("40001010@jm.gd.cp");
        dept.setDeptname("江门市委办综合科");
        dept.setSendnumber("");
        dept.setSendstartnum("");
        dept.setSort("0");
        dept.setIsbarcode("0");
        
        WhTodeptinfo todeptinfo = new WhTodeptinfo();
        todeptinfo.setDept(Arrays.asList(dept));
        
        WhForminfo forminfo = new WhForminfo();
        forminfo.setFormid("5d6c42e83bf745029b497634dabccac7");
        forminfo.setTitle("（用章系统）安全性测试bug5d6c42e83bf745029b497634dabccac7");
        forminfo.setExchmode("EM01");
        forminfo.setInstancy("IL04");
        forminfo.setSendertype("TY03");
        forminfo.setSeclevel("MJ02");
        forminfo.setFromdepturi("40001010@jm.gd.cp");
        forminfo.setFromdeptname("江门市委办综合科");
        forminfo.setBookdepturi("40001010@jm.gd.cp");
        forminfo.setBookdeptname("江门市委办综合科");
        forminfo.setSenderuri("900000065@jm.gd.cp");
        forminfo.setSendername("维豪接口演示系统");
        forminfo.setOperuri("21800168@jm.gd.cp");
        forminfo.setOpername("黄春燕");
        forminfo.setTodeptinfo(todeptinfo);
        
        WhAppinfo appinfo = new WhAppinfo();
        appinfo.setAppid("900000065@jm.gd.cp");
        appinfo.setAppname("900000065@jm.gd.cp");
        
        WhEdoc edoc = new WhEdoc();
        edoc.setAppinfo(appinfo);
        edoc.setForminfo(forminfo);

        // 构造报文 XML
        String xmlStr = JaxbObjectAndXmlUtil.object2Xml(edoc);
        // 格式的字符串
        System.out.println("对象转xml报文： \n" + xmlStr);
        
        EdocWebService factory = new EdocWebService();
        EdocWebServicePortType edocWebServiceHttpPort = factory.getEdocWebServiceHttpPort();
        
        String formInfoSend1 = edocWebServiceHttpPort.formInfoSend(xmlStr, "", "", "");
        System.out.println(formInfoSend1);

//        WhEdoc edoc2 = JaxbObjectAndXmlUtil.xml2Object(xmlStr, WhEdoc.class);
//        System.out.println("报文转xml转： \n" + JSON.toJSONString(edoc2));
    }
}
