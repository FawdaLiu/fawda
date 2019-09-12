package fawda.java.webservice.test;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpclientTest {
    public void test() {
        CloseableHttpClient closeableHttpClient = null;
        CloseableHttpResponse closeableHttpResponse = null;
        String result = null;
        try {

            String requestStr = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" "
                    + "xmlns:web=\"http://mycompany.com/hr/webservice\">" + "<soapenv:Header/>" + "<soapenv:Body>" + "<web:holidayRequest>"
                    + "<web:name>旺财</web:name>" + "</web:holidayRequest>" + "</soapenv:Body>" + "</soapenv:Envelope>";

            HttpPost httpPost = new HttpPost("http://www.ningyongli.site:8080/webservicelearn/holidayService");
            httpPost.setHeader("Content-Type", "text/xml;charset=utf-8");
            // httpPost.setHeader("SOAPAction", "hollidayService");

            System.out.println(requestStr);
            StringEntity entity = new StringEntity(requestStr, "UTF-8");
            httpPost.setEntity(entity);

            closeableHttpClient = HttpClients.createDefault();
            closeableHttpResponse = closeableHttpClient.execute(httpPost);
            result = EntityUtils.toString(closeableHttpResponse.getEntity());
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                closeableHttpResponse.close();
                closeableHttpClient.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
