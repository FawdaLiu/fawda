package fawda.java.webservice;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

public class LccClient {
    public static void main(String[] args) {

        try {
            String endpoint = "http://192.168.1.111:8080/edocument/service/EdocWebService?wsdl";
            // 直接引用远程的wsdl文件
            // 以下都是套路
            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(endpoint);
            call.setOperationName("formInfoSend");// WSDL里面描述的接口名称
            call.addParameter("in0", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);// 接口的参数
            call.addParameter("in1", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);// 接口的参数
            call.addParameter("in2", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);// 接口的参数
            call.addParameter("in3", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);// 接口的参数
            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);// 设置返回类型
            String temp = "测试人员";
            String result = (String) call.invoke(new Object[] { temp, "", "", "" });
            // 给方法传递参数，并且调用方法
            System.out.println("result is " + result);
        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }
}
