package fawda.java.security;
 
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
 
import javax.net.ssl.*;
import java.io.FileInputStream;
import java.net.SocketTimeoutException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
 
/**
 * <b>修订原因:</b> 初始化创建.详细说明:<br>
 *       互相认证,p12证书<br>
 *对接银联的支付业务时，由于银联进行的https请求，把https的原理和java中如何使用进行了一个总结，希望对后面进行https请求的开发有所帮助.
 * 首先需要了解https的原理，是一种基于SSL/TLS的Http，所有的http数据都是在SSL/TLS协议封装之上传输的。Https协议在Http协议的基础上，添加了SSL/TLS握手以及数据加密传输，也属于应用层协议。所以，研究Https协议原理，最终其实是研究SSL/TLS协议。具体了解SSL/TLS可以参照文档：http://www.fenesky.com/blog/2014/07/19/how-https-works.html。
 * SSL/TLS也是双方需要通过握手来验证请求双方都是安全的，为了验证双方的合法性，就需要交换双方的公钥证书，其实原理有点像非对称加密，所以首先你需要提供给银联一个公钥证书，银联也会提供一个公钥证书给你，但是java里面需要用keystore来保存公钥和私钥，下面是具体的步骤：
 * 1.用工具keytool来生成本地的keystore：
 *     keytool -genkey -alias wjx -keypass wjx -keyalg RSA -keysize 1024 -validity 36500 -keystore  e:/wjx.keystore -storepass 12345678 -keypass 123456789 -dname "CN=www.wjx.cn, OU=wjx, O=wjx, L=cq, ST=cq, C=cn"
 *     其中：
 *            -storepass为指定密钥库的密码(获取keystore信息所需的密码)
 *            -keypass为指定别名条目的密码(私钥的密码)
 *       但是可能银联会直接给你提供私钥和公钥的密钥对，而不是你生成（这个有点坑），这个时候就需要用公钥和私钥来生成keystore，具体步骤为：
 *            a.用公钥和私钥生成p12证书：
 *              openssl pkcs12 -export -in client.crt -inkey client.key -out client.p12 -name wjx -CAfile ca.crt -caname wjx
 *                 会要求你输入私钥的密码，和p12文件的密码1234567890(后面生成keystore的时候会用到)
 *              其中client.key为私钥，client.crt为公钥，client.p12生成的p12格式的证书
 *            b.再用p12文件生成对应的keystore（wjx.keystore）：
 *             keytool -importkeystore -srckeystore client.p12 -srcstoretype pkcs12 -destkeystore e:/wjx.keystore -deststoretype jks -srcstorepass 1234567890
 *           其中1234567890是keystore的密码
 *
 * 2.如果需要和银联交换公钥，就需要将本地生成的keystore导出公钥证书：
 *        keytool -export -alias wjx -keystore e:/wjx.keystore -file e:/wjx.crt -storepass 12345678
 *
 * 3.然后需要和他们交换公钥证书，他会给你提供公钥证书ca.crt；
 *
 * 4.生成的公钥证书需要转换为keystore才能java中使用，具体命令为：
 *        keytool -import -alias unionpay_https -keystore e:/unionpay_https.keystore -file ca.crt
 *
 * 生成好keystore过后就需要和银联进行握手的操作(主要是用KeyManagerFactory来管理自己的秘钥，TrustManagerFactory来管理对方的公钥证书)：
 * <b>时间:</b> <i>2019年08月02日 下午6:14</i>
 *
 * @author Fawda: liuyl
 */
public class HttpsSignUtil {
    public static final String DEFAULT_CHARSET = "UTF-8";           //设置默认通信报文编码为UTF-8
    private static final int DEFAULT_CONNECTION_TIMEOUT = 1000 * 2; //设置默认连接超时为2s
    private static final int DEFAULT_SO_TIMEOUT = 1000 * 60;        //设置默认读取超时为60s
    public static String postWithSign(String reqURL, String reqData ,String charset){
        String respData = "";
        HttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, DEFAULT_CONNECTION_TIMEOUT);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, DEFAULT_SO_TIMEOUT);

        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            KeyStore trustKeyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            FileInputStream inputStream = new FileInputStream("/home/fawda/mykeystore.keystore");//加载自己的keystore
            FileInputStream trustInputStream = new FileInputStream("/home/fawda/ca.keystore");//加载银联提供的keystore
            try {
                keyStore.load(inputStream, "123456789".toCharArray());//123456789为生成keystore时的密码storepass
                trustKeyStore.load(trustInputStream, "1234567890".toCharArray());//1234567890为生成keystore时的密码storepass
            } catch (CertificateException e) {
                e.printStackTrace();
                inputStream.close();
                throw new CertificateException();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                inputStream.close();
                throw new NoSuchAlgorithmException();
            }
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");//管理对方的公钥证书
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            tmf.init(trustKeyStore);
            keyManagerFactory.init(keyStore, "123456789".toCharArray());  // 123456789为keypass为指定别名条目的密码(私钥的密码)
            TrustManager tms [] = tmf.getTrustManagers();
            KeyManager keyManagers [] = keyManagerFactory.getKeyManagers();
            SSLContext sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init(keyManagers, tms, new SecureRandom());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[]{"TLSv1"},
                    null,
                    SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);//如果这个不写ALLOW_ALL_HOSTNAME_VERIFIER 就证书中的CN对应域名必须和请求的https地址一样
            httpClient = HttpClients.custom()
                    .setSSLSocketFactory(sslsf)
                    .build();
            HttpPost httpPost = new HttpPost(reqURL);
            httpPost.setHeader(HTTP.CONTENT_TYPE, "text/xml; charset=" + DEFAULT_CHARSET);
            httpPost.setEntity(new StringEntity(null==reqData?"":reqData, DEFAULT_CHARSET));
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            if(null != entity){
                respData = EntityUtils.toString(entity, charset);
            }
            return respData;
        }catch(ConnectTimeoutException cte){
            throw new RuntimeException("请求通信[" + reqURL + "]时连接超时", cte);
        }catch(SocketTimeoutException ste){
            throw new RuntimeException("请求通信[" + reqURL + "]时读取超时", ste);
        }catch(Exception e){
            throw new RuntimeException("请求通信[" + reqURL + "]时遇到异常", e);
        }finally{
            httpClient.getConnectionManager().shutdown();
        }
    }
}