package fawda.java.webservice.test;

import java.net.URL;

import org.codehaus.xfire.client.Client;
import org.json.JSONObject;

public class Test {
    public void sendSmsToWebservice(){
        try{
            //调用WEBESERVICE获取所有待接收文件
            //String sb="{username:'F88F9DDBD93D440B9343242E610F832E',password:'',phoneName:'[15397092140]',content:'test',plantime:'',organId:'',sendType:'0',isForward:'1',forwardNos:'',forwardMark:'内容',isReplace:'0',suffixName:'内容',isDel:'0',smsType:'1',isThird:'0'}";
            String sb="{in0:'1',in1:'root',in2:'20000010902@zhejiang.zg',in3:'张浩',in4:'2',in5:''}";
            Client client = new Client(new URL("http://192.168.1.111:8080/edocument/service/EdocWebService?wsdl"));
            Object[] res = client.invoke("getCommonExchObj", new Object[] {sb.toString()});
            JSONObject json = new JSONObject(res[0].toString());
            String message = json.getString("msg");
            System.out.println(message);
            client.close();//关闭WEBESERVICE连接
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
//        Test pub=new Test();
//         pub.sendSmsToWebservice();
         System.out.println("KKKKKKKKKKKKKKKK");
         System.out.println( System.getProperty("java.library.path"));
    }
}
