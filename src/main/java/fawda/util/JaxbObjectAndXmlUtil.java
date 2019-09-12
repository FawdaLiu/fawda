package fawda.util;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import fawda.java.webservice.WhAppinfo;
import fawda.java.webservice.WhDept;
import fawda.java.webservice.WhEdoc;
import fawda.java.webservice.WhForminfo;
import fawda.java.webservice.WhTodeptinfo;
import com.risen.hp.sfw.util.xml.Base64Utils;

public class JaxbObjectAndXmlUtil {

    /**
     * @param xmlStr 字符串
     * @param objcet 对象Class类型
     * @return 对象实例
     */
    @SuppressWarnings({ "unchecked", "finally" })
    public static <T> T xml2Object(String xmlStr, Class<T> objcet) {
        T t = null;
        try {
            JAXBContext context = JAXBContext.newInstance(objcet);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            t = (T) unmarshaller.unmarshal(new StringReader(xmlStr));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return t;
        }
    }

    /**
     * @param object 对象
     * @return 返回xmlStr
     */
    public static String object2Xml(Object object) {
        try {
            StringWriter writer = new StringWriter();
            JAXBContext context = JAXBContext.newInstance(object.getClass());
            Marshaller marshal = context.createMarshaller();

            // 决定是否在转换成xml时同时进行格式化（即按标签自动换行，否则即是一行的xml）
            marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
            // xml编码格式,默认为utf-8
            marshal.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            // 是否省略xml头信息
            marshal.setProperty(Marshaller.JAXB_FRAGMENT, true);
            // 设置编码格式 utf-8
            marshal.setProperty("jaxb.encoding", "UTF-8");
            marshal.marshal(object, writer);

            return new String(writer.getBuffer());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    String xmlStr = "<edoc>" +
            "    <appinfo>" +
            "        <appid>30000000012@zhejiang.zg</appid>" +
            "        <appname>公文处理系统</appname>" +
            "    </appinfo>" +
            "    <forminfo>" +
            "        <bookdeptname>张浩</bookdeptname>" +
            "        <bookdepturi>20000010902@zhejiang.zg</bookdepturi>" +
            "        <bookindex></bookindex>" +
            "        <booktype></booktype>" +
            "        <data></data>" +
            "        <docformat></docformat>" +
            "        <docnum></docnum>" +
            "        <doctype></doctype>" +
            "        <exchareatag></exchareatag>" +
            "        <exchmode>EM01</exchmode>" +
            "        <extdata></extdata>" +
            "        <filedate></filedate>" +
            "        <formid>7E69E791D2AE4C2C80A374EB53F65E77</formid>" +
            "        <fromdeptname>中共浙江省委办公厅信息化管理中心</fromdeptname>" +
            "        <fromdepturi>10000011400@zhejiang.zg</fromdepturi>" +
            "        <instancy>IL04</instancy>" +
            "        <keywords></keywords>" +
            "        <opername>张浩</opername>" +
            "        <operuri>20000010902@zhejiang.zg</operuri>" +
            "        <pagesum></pagesum>" +
            "        <remark></remark>" +
            "        <rep_type></rep_type>" +
            "        <seclevel>MJ02</seclevel>" +
            "        <sendername>公文处理系统</sendername>" +
            "        <sendertype>TY03</sendertype>" +
            "        <senderuri>30000000012@zhejiang.zg</senderuri>" +
            "        <summary></summary>" +
            "        <task_id></task_id>" +
            "        <title>测试数据2018年5月30日14:49:19</title>" +
            "        <todeptinfo>" +
            "            <list>" +
            "                <dept recvtype=\"TY12\">" +
            "                    <appname></appname>" +
            "                    <appuri></appuri>" +
            "                    <deptname>浙江省府办公厅秘书一处</deptname>" +
            "                    <depturi>10000020400@zhejiang.zg</depturi>" +
            "                    <isbarcode>0</isbarcode>" +
            "                    <sendnumber>1</sendnumber>" +
            "                    <sendstartnum>0</sendstartnum>" +
            "                    <sort>0</sort>" +
            "                </dept>" +
            "                <dept recvtype=\"TY12\">" +
            "                    <appname></appname>" +
            "                    <appuri></appuri>" +
            "                    <deptname>中共浙江省委机要局</deptname>" +
            "                    <depturi>10000010400@zhejiang.zg</depturi>" +
            "                    <isbarcode>0</isbarcode>" +
            "                    <sendnumber>1</sendnumber>" +
            "                    <sendstartnum>0</sendstartnum>" +
            "                    <sort>0</sort>" +
            "                </dept>" +
            "                <dept recvtype=\"TY12\">" +
            "                    <appname></appname>" +
            "                    <appuri></appuri>" +
            "                    <deptname>中共浙江省委办公厅秘书一处</deptname>" +
            "                    <depturi>10000010900@zhejiang.zg</depturi>" +
            "                    <isbarcode>0</isbarcode>" +
            "                    <sendnumber>1</sendnumber>" +
            "                    <sendstartnum>0</sendstartnum>" +
            "                    <sort>0</sort>" +
            "                </dept>" +
            "                <dept recvtype=\"TY12\">" +
            "                    <appname></appname>" +
            "                    <appuri></appuri>" +
            "                    <deptname>浙江省经信委</deptname>" +
            "                    <depturi>10000022400@zhejiang.zg</depturi>" +
            "                    <isbarcode>0</isbarcode>" +
            "                    <sendnumber>1</sendnumber>" +
            "                    <sendstartnum>0</sendstartnum>" +
            "                    <sort>0</sort>" +
            "                </dept>" +
            "            </list>" +
            "        </todeptinfo>" +
            "        <topic></topic>" +
            "    </forminfo>" +
            "</edoc>";
    
    public static void main(String[] args) {
        /** 构造测试报文头对象 */
        WhDept dept = new WhDept();
        dept.setRecvtype("TY12");
        dept.setDepturi("10000010400@zhejiang.zg");
        dept.setDeptname("中共浙江省委机要局");
        dept.setSendnumber("0");
        dept.setSendstartnum("1");
        dept.setSort("0");
        dept.setIsbarcode("0");
        
        WhDept dept1 = new WhDept();
        dept1.setRecvtype("TY12");
        dept1.setDepturi("10000020200@zhejiang.zg");
        dept1.setDeptname("浙江省府办公厅");
        dept1.setSendnumber("0");
        dept1.setSendstartnum("1");
        dept1.setSort("0");
        dept1.setIsbarcode("0");
        
        WhDept dept2 = new WhDept();
        dept2.setRecvtype("TY12");
        dept2.setDepturi("10000022400@zhejiang.zg");
        dept2.setDeptname("浙江省经信委");
        dept2.setSendnumber("0");
        dept2.setSendstartnum("1");
        dept2.setSort("0");
        dept2.setIsbarcode("0");
        
        
        WhTodeptinfo todeptinfo = new WhTodeptinfo();
        todeptinfo.setDept(Arrays.asList(dept));
        
        WhForminfo forminfo = new WhForminfo();
        forminfo.setFormid("085C362B7BC34A82BAD979AD2072A9FF");
        forminfo.setTitle("测试数据2018年5月30日14:11:36");
        forminfo.setExchmode("EM01");
        forminfo.setInstancy("IL04");
        forminfo.setSendertype("TY03");
        forminfo.setSeclevel("MJ02");
        forminfo.setFromdepturi("10000011400@zhejiang.zg");
        forminfo.setFromdeptname("中共浙江省委办公厅信息化管理中心");
        forminfo.setBookdepturi("20000010902@zhejiang.zg");
        forminfo.setBookdeptname("张浩");
        forminfo.setSenderuri("30000000012@zhejiang.zg");
        forminfo.setSendername("公文处理系统");
        forminfo.setOperuri("20000010902@zhejiang.zg");
        forminfo.setOpername("张浩");
        forminfo.setTodeptinfo(todeptinfo);
        
        WhAppinfo appinfo = new WhAppinfo();
        appinfo.setAppid("30000000012@zhejiang.zg");
        appinfo.setAppname("公文处理系统");
        
        WhEdoc edoc = new WhEdoc();
        edoc.setAppinfo(appinfo);
        edoc.setForminfo(forminfo);

        // 构造报文 XML
        String xmlStr = JaxbObjectAndXmlUtil.object2Xml(edoc);
//        System.out.println(xmlStr);
//        Base64.Encoder encoder = Base64.getEncoder();
//        String encodeToString = encoder.encodeToString(xmlStr.getBytes());
        String encodeToString = "";
//        encodeToString = Base64Utils.encodeToString(xmlStr.getBytes());
//        try {
//            encodeToString = Base64Utils.encodeToString((new String((xmlStr.getBytes()), "GBK")).getBytes());
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        EdocWebService factory = new EdocWebService();
//        EdocWebServicePortType edocWebServiceHttpPort = factory.getEdocWebServiceHttpPort();
        
//        String formInfoSend1 = edocWebServiceHttpPort.formInfoSend(encodeToString, "", "", "");
//        System.out.println(formInfoSend1);
//        String commonExchObj = edocWebServiceHttpPort.getCommonExchObj("1", "root", "20000010902@zhejiang.zg", "张浩", "2", "");
//        WhResult whResult = JaxbObjectAndXmlUtil.xml2Object(commonExchObj, WhResult.class);
//        System.out.println(whResult.getMsg());
//        byte[] decodeFromString = Base64Utils.decodeFromString(whResult.getMsg());
        encodeToString = "PGxhYnM+PGd1aWQ+MjAxODA2MDUyMTM4MjE2REU2MjlFQzQzODg5OEY0RTA1MEE4QzA2RjAxNDBEOEB6aGVqaWFuZy56ZzwvZ3VpZD48cXJ5aWQ+MjAxODA2MDUyMTM4MjE2REU2MjlFQzQzODk5OEY0RTA1MEE4QzA2RjAxNDBEOEB6aGVqaWFuZy56ZzwvcXJ5aWQ+PC9sYWJzPg==";
        byte[] decodeFromString;
        decodeFromString = Base64Utils.decodeFromString(encodeToString);
        
        System.out.println(new String(decodeFromString));
        
        
    }
}
