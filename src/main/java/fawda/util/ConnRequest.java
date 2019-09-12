/*
package com.lcc.liu.util;

import com.lcc.liu.util.MyUtils;
//import com.risen.hp.cmm.io.FileUtils;
//import com.risen.hp.cmm.io.IOUtils;
//import com.risen.hp.cmm.lang3.builder.ReflectionToStringBuilder;
//import com.risen.hp.cmm.lang3.builder.ToStringStyle;
//import com.risen.hp.sfw.util.StringUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.Cookie;

public class ConnRequest {
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String FORM_CONTENT_TYPE = "application/x-www-form-urlencoded";
    private String targetUrl;
    private String method = "GET";

    private String requestCharset = "UTF-8";

    private int connTimeout = 10000;

    private int readTimeout = 10000;

    private Map<String, String> requestHeaders = new LinkedHashMap();
    private String requestBody;
    private String responseCharset = "UTF-8";
    private int responseCode;
    private Map<String, String> responseHeaders = new LinkedHashMap();
    private String responseBody;
    private File responseFile;
    private List<Cookie> cookies = new ArrayList();

    private Map<String, Cookie> mapCookie = new LinkedHashMap();
    private Cookie sessionCookie;
    static final Pattern cookiePattern = Pattern.compile("\\w+\\=[^\\s,\\=]*;?");

    public ConnRequest() {
    }

    public ConnRequest(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public ConnRequest(String targetUrl, String method) {
        this.targetUrl = targetUrl;
        this.method = method;
    }

    public String getTargetUrl() {
        return this.targetUrl;
    }

    public ConnRequest targetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
        return this;
    }

    public String getMethod() {
        return this.method;
    }

    public ConnRequest method(String method) {
        this.method = method;
        return this;
    }

    public int getConnTimeout() {
        return this.connTimeout;
    }

    public ConnRequest connTimeout(int connTimeout) {
        this.connTimeout = connTimeout;
        return this;
    }

    public int getReadTimeout() {
        return this.readTimeout;
    }

    public ConnRequest readTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
        return this;
    }

    public String getRequestCharset() {
        return this.requestCharset;
    }

    public ConnRequest requestCharset(String requestCharset) {
        this.requestCharset = requestCharset;
        return this;
    }

    public Map<String, String> getRequestHeaders() {
        return this.requestHeaders;
    }

    public ConnRequest requestHeaders(Map<String, String> requestHeaders) {
        this.requestHeaders = requestHeaders;
        return this;
    }

    public String getRequestBody() {
        return this.requestBody;
    }

    public ConnRequest requestBody(String requestBody) {
        this.requestBody = requestBody;
        return this;
    }

    public String getResponseCharset() {
        return this.responseCharset;
    }

    public ConnRequest responseCharset(String responseCharset) {
        this.responseCharset = responseCharset;
        return this;
    }

    public Map<String, String> getResponseHeaders() {
        return this.responseHeaders;
    }

    public ConnRequest responseHeaders(Map<String, String> responseHeaders) {
        this.responseHeaders = responseHeaders;
        return this;
    }

    public int getResponseCode() {
        return this.responseCode;
    }

    public ConnRequest responseCode(int responseCode) {
        this.responseCode = responseCode;
        return this;
    }

    public String getResponseBody() {
        return this.responseBody;
    }

    public ConnRequest responseBody(String responseBody) {
        this.responseBody = responseBody;
        return this;
    }

    public File getResponseFile() {
        return this.responseFile;
    }

    public ConnRequest responseFile(File responseFile) {
        this.responseFile = responseFile;
        return this;
    }

    public List<Cookie> getCookies() {
        return this.cookies;
    }

    public ConnRequest cookies(List<Cookie> cookies) {
        this.cookies = cookies;
        for (Cookie cookie : cookies) {
            this.mapCookie.put(cookie.getName(), cookie);
        }
        return this;
    }

    public Map<String, Cookie> getMapCookie() {
        return this.mapCookie;
    }

    public void mapCookie(Map<String, Cookie> mapCookie) {
        this.mapCookie = mapCookie;
        this.cookies.addAll(mapCookie.values());
    }

    public ConnRequest addCookie(Cookie cookie) {
        this.cookies.add(cookie);
        this.mapCookie.put(cookie.getName(), cookie);
        return this;
    }

    public ConnRequest addCookie(String name, String value) {
        addCookie(new Cookie(name, value));
        return this;
    }

    public ConnRequest addCookie(String nameValue) {
        String name = nameValue.substring(0, nameValue.indexOf("="));
        String value = nameValue.substring(nameValue.indexOf("=") + 1, nameValue.indexOf(";"));
        Cookie cookie = new Cookie(name, value);
        addCookie(cookie);

        nameValue = nameValue.substring(nameValue.indexOf(";"));
        StringTokenizer st = new StringTokenizer(nameValue, ", ");
        while (st.hasMoreElements()) {
            String token = (String) st.nextElement();
            String[] nv = token.split("=");
            if (nv.length == 2) {
                if (nv[0].equalsIgnoreCase("Path"))
                    cookie.setPath(nv[1]);
                else if (nv[0].equalsIgnoreCase("Domain")) {
                    cookie.setDomain(nv[1]);
                }
            }
        }
        if ("JSESSIONID".equals(name)) {
            this.sessionCookie = cookie;
        }

        return this;
    }

    public Cookie getSessionCookie() {
        return this.sessionCookie;
    }

    public ConnRequest clear() {
        this.requestBody = null;
        this.requestHeaders.clear();
        this.responseBody = null;
        this.responseHeaders.clear();

        this.cookies.clear();
        this.mapCookie.clear();
        this.sessionCookie = null;

        return this;
    }

    public ConnRequest defaultHeaders() {
        this.requestHeaders.put("connection", "Keep-Alive");
        this.requestHeaders.put("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
        this.requestHeaders.put("Charset", "UTF-8");
        return this;
    }

    public ConnRequest request(String targetURL, String method) {
        HttpURLConnection conn = null;
        InputStream inputStream = null;
        OutputStream os = null;
        try {
            targetURL = (String) MyUtils.getValue(targetURL, getTargetUrl());
            URL url = new URL(targetURL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod((String) MyUtils.getValue(method, getMethod()));
            conn.setInstanceFollowRedirects(false);

            conn.setConnectTimeout(getConnTimeout());
            conn.setReadTimeout(getReadTimeout());

            Map header = getRequestHeaders();
            for (Iterator it = header.entrySet().iterator(); it.hasNext();) {
                Map.Entry entry = (Map.Entry) it.next();
                conn.setRequestProperty((String) entry.getKey(), (String) entry.getValue());
            }
            if (StringUtils.hasText(getRequestBody())) {
                os = conn.getOutputStream();
                os.write(getRequestBody().getBytes(getRequestCharset()));
            }

            inputStream = conn.getInputStream();
            if (this.responseFile == null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, getResponseCharset()));
                responseBody(IOUtils.toString(reader));
            } else {
                FileUtils.copyInputStreamToFile(inputStream, this.responseFile);
            }

            Map responseHeaders = conn.getHeaderFields();
            for (Iterator it = responseHeaders.entrySet().iterator(); it.hasNext();) {
                Map.Entry entry = (Map.Entry) it.next();
                this.responseHeaders.put((String) entry.getKey(), StringUtils.collectionToCommaDelimitedString((Collection) entry.getValue()));
                if ("Set-Cookie".equalsIgnoreCase((String) entry.getKey())) {
                    List<String> cookies = (List<String>) entry.getValue();
                    for (String cookie : cookies) {
                        addCookie(cookie);
                    }
                }
            }
            responseCode(conn.getResponseCode());
        } catch (MalformedURLException e) {
            throw new RuntimeException("URL异常", e);
        } catch (ProtocolException e) {
            throw new RuntimeException("协议异常", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("编码异常", e);
        } catch (IOException e) {
            throw new RuntimeException("IO异常", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(os);
            conn.disconnect();
        }
        return this;
    }

    public ConnRequest request() {
        return request(null, null);
    }

    public ConnRequest request(String targetUrl) {
        return request(targetUrl, null);
    }

    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    static SSLSocketFactory createSSLSocketFactory() {
        SSLContext sslContext = null;
        try {
            TrustManager[] tm = { new TrustAnyTrustManager() };
            sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new SecureRandom());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sslContext.getSocketFactory();
    }

    static {
        HttpsURLConnection.setDefaultSSLSocketFactory(createSSLSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier(new MyHostnameVerifier());
    }

    static class MyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    static class TrustAnyTrustManager implements X509TrustManager {
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }
}*/
