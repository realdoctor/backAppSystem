package com.kanglian.healthcare.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HTTPUtil {

    private final static Logger                       logger          = LoggerFactory.getLogger(HTTPUtil.class);

    private static final int                          REQUEST_TIMEOUT = 10 * 1000; // 设置请求超时10秒钟
    private static final int                          TIMEOUT         = 20 * 1000; // 连接超时时间
    private static final int                          SO_TIMEOUT      = 20 * 1000; // 数据传输超时
    private static final String                       CHARSET         = "UTF-8";

    /**
     * 指定参数名GET方式请求数据
     * 
     * @param url
     * @param paramsMap
     * @return
     */
    public static String doGet(String url, Map<String, String> paramsMap) {
        return doGet(invokeUrl(url, paramsMap));
    }

    /**
     * GET方式请求数据
     * 
     * @param url
     */
    public static String doGet(String url) {
        CloseableHttpClient httpClient = HTTPUtil.createSSLClientDefault();

        HttpGet httpGet = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SO_TIMEOUT).setConnectTimeout(TIMEOUT).setConnectionRequestTimeout(REQUEST_TIMEOUT).build();
        httpGet.setConfig(requestConfig);

        long responseLength = 0; // 响应长度
        String responseContent = null; // 响应内容
        String strRep = null;
        try {
            // 执行get请求
            HttpResponse httpResponse = httpClient.execute(httpGet);

            // 头信息
            printHeaders(httpResponse);

            // 获取响应消息实体
            HttpEntity entityRep = httpResponse.getEntity();
            // 判断响应实体是否为空
            if (entityRep != null) {
                responseLength = entityRep.getContentLength();
                responseContent = EntityUtils.toString(entityRep, "UTF-8");// 不能重复调用此方法，IO流已关闭。

                System.err.println("请求地址: " + httpGet.getURI());
                System.err.println("响应状态: " + httpResponse.getStatusLine());
                System.err.println("响应长度: " + responseLength);
                System.err.println("内容编码: " + entityRep.getContentEncoding());
                System.err.println("响应内容: \r\n" + responseContent);

                // 获取HTTP响应的状态码
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                if (statusCode != HttpStatus.SC_OK) {
                    logger.error("httpGet Method failed: " + httpResponse.getStatusLine());
                } else {
                    strRep = responseContent; // EntityUtils.toString(httpResponse.getEntity());
                }

                // Consume response content
                EntityUtils.consume(entityRep);
                // Do not need the rest 终止操作
                httpGet.abort();
            }
        } catch (Exception e) {
            logger.error("httpGet Exception", e);
        } finally {
            httpGet.releaseConnection();
        }

        return strRep;
    }

    /**
     * 不指定参数名的方式来POST数据
     * 
     * @param url
     * @param stringJsonXml
     * @return
     */
    public static String doPost(String url, String stringJsonXml) {
        return doPost(url, null, stringJsonXml);
    }

    /**
     * 指定参数名POST方式请求数据
     * 
     * @param url
     * @param paramsMap
     */
    public static String doPost(String url, Map<String, String> paramsMap) {
        return doPost(url, paramsMap, null);
    }

    private static String doPost(String url, Map<String, String> paramsMap, String postContent) {
        CloseableHttpClient httpClient = HTTPUtil.createSSLClientDefault();

        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-type", "application/json; charset=utf-8");

        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SO_TIMEOUT).setConnectTimeout(TIMEOUT).setConnectionRequestTimeout(REQUEST_TIMEOUT).setExpectContinueEnabled(false).build();
        httpPost.setConfig(requestConfig);// RequestConfig.DEFAULT

        long responseLength = 0; // 响应长度
        String responseContent = null; // 响应内容
        String strRep = null;
        try {
            if (paramsMap != null && postContent == null) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(getParamsList(paramsMap), CHARSET);
                httpPost.setEntity(entity);
            } else {
                httpPost.setEntity(new StringEntity(postContent, CHARSET)); // POST方式传JSONString
            }

            // 执行post请求
            HttpResponse httpResponse = httpClient.execute(httpPost);

            // 头信息
            printHeaders(httpResponse);

            // 获取响应消息实体
            HttpEntity entityRep = httpResponse.getEntity();
            if (entityRep != null) {
                responseLength = entityRep.getContentLength();
                responseContent = EntityUtils.toString(httpResponse.getEntity());

                System.err.println("请求地址: " + httpPost.getURI());
                System.err.println("响应状态: " + httpResponse.getStatusLine());
                System.err.println("响应长度: " + responseLength);
                System.err.println("内容编码: " + entityRep.getContentEncoding());
                System.err.println("响应内容: \r\n" + responseContent);

                // 获取HTTP响应的状态码
                int statusCode = httpResponse.getStatusLine().getStatusCode();
                if (statusCode == HttpStatus.SC_OK) {
                    strRep = responseContent; // EntityUtils.toString(httpResponse.getEntity());
                } else if ((statusCode == HttpStatus.SC_MOVED_TEMPORARILY)
                           || (statusCode == HttpStatus.SC_MOVED_PERMANENTLY) || (statusCode == HttpStatus.SC_SEE_OTHER)
                           || (statusCode == HttpStatus.SC_TEMPORARY_REDIRECT)) {
                    // 页面重定向代码
                } else {
                    logger.error("httpPost Method failed: " + httpResponse.getStatusLine());
                }

                EntityUtils.consume(entityRep);
                httpPost.abort();
            }
        } catch (Exception e) {
            logger.error("httpPost Exception", e);
            e.printStackTrace();
        } finally {
            httpPost.releaseConnection();
        }

        return strRep;
    }

    /**
     * post请求传输String参数 例如：name=Jack&sex=1&type=2 
     * 
     * @Content-type:application/x-www-form-urlencoded
     * @param url url地址
     * @param strParam 参数
     * @return
     */
    public static String httpPost(String url, String strParam) {
        // post请求返回结果
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        // 设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
        httpPost.setConfig(requestConfig);
        String strRep = null;
        try {
            if (null != strParam) {
                // 解决中文乱码问题
                StringEntity entity = new StringEntity(strParam, CHARSET);
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/x-www-form-urlencoded");
                httpPost.setEntity(entity);
            }
            HttpResponse result = httpClient.execute(httpPost);
            // 请求发送成功，并得到响应
            if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                try {
                    // 读取服务器返回过来的json字符串数据
                    strRep = EntityUtils.toString(result.getEntity(), CHARSET);
                } catch (Exception e) {
                    logger.error("post返回数据异常:" + url, e);
                }
            }
        } catch (IOException e) {
            logger.error("post请求提交失败:" + url, e);
        } finally {
            httpPost.releaseConnection();
        }
        return strRep;
    }
    
    // 打印相应头信息
    private static void printHeaders(HttpResponse httpResponse) {
        System.out.println("------------------------------");
        // 头信息
        HeaderIterator it = httpResponse.headerIterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
        System.out.println("------------------------------");
    }

    // 读取内容
    public static String readContent(InputStream in) throws Exception {
        BufferedInputStream buffer = new BufferedInputStream(in);
        StringBuilder builder = new StringBuilder();
        byte[] bytes = new byte[1024];
        int line = 0;
        while ((line = buffer.read(bytes)) != -1) {
            builder.append(new String(bytes, 0, line, CHARSET));
        }

        return builder.toString();
    }

    /**
     * GET方式传参
     * 
     * @param url
     * @param paramsMap
     * @return
     */
    public static String invokeUrl(String url, Map<String, String> paramsMap) {
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        int i = 0;
        if (paramsMap != null && paramsMap.size() > 0) {
            for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
                if (i == 0 && !url.contains("?")) {
                    sb.append("?");
                } else {
                    sb.append("&");
                }
                sb.append(entry.getKey());
                sb.append("=");
                String value = entry.getValue();
                try {
                    sb.append(URLEncoder.encode(value, CHARSET));
                } catch (UnsupportedEncodingException e) {
                    logger.warn("encode http get params error, value is " + value, e);
                    try {
                        sb.append(URLEncoder.encode(value, null));
                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                    }
                }

                i++;
            }
        }

        return sb.toString();
    }

    /**
     * 将传入的键/值对参数转换为NameValuePair参数集
     * 
     * @param paramsMap 参数集, 键/值对
     * @return NameValuePair参数集
     */
    private static List<NameValuePair> getParamsList(Map<String, String> paramsMap) {
        if (paramsMap == null || paramsMap.size() == 0) {
            return null;
        }

        // 创建参数队列
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> map : paramsMap.entrySet()) {
            params.add(new BasicNameValuePair(map.getKey(), map.getValue()));
        }

        return params;
    }

    /**
     * 不需要导入证书，SSL信任所有证书
     * 
     * @return
     */
    public static CloseableHttpClient createSSLClientDefault() {

        SSLContext sslContext;
        try {
            sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {

                // 信任所有证书
                @Override
                public boolean isTrusted(X509Certificate[] xcs, String string) {
                    return true;
                }
            }).build();

            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);

            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (KeyStoreException ex) {
            ex.printStackTrace();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        } catch (KeyManagementException ex) {
            ex.printStackTrace();
        }

        return HttpClients.createDefault();
    }
    
    /**
     * 需要导入信任的SSL证书
     * 
     * @param keyStorePath
     * @param password
     * @return
     */
    public static CloseableHttpClient createSSLClient(String keyStorePath, String password) {
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            FileInputStream instream = new FileInputStream(new File(keyStorePath));
            try {
                // 加载keyStore
                trustStore.load(instream, password.toCharArray());
            } catch (CertificateException e) {
                e.printStackTrace();
            } finally {
                try {
                    instream.close();
                } catch (Exception ignore) {
                }
            }
            SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(trustStore,
                                                                           new TrustSelfSignedStrategy()).build();
            // 只允许使用TLSv1协议
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1", "TLSv2", "TLSv3" },
                                                                              null,
                                                                              SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    /**
     * 上传文件
     */
    public static void upload(String url, String filePath) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(url);
            FileBody fileBody = new FileBody(new File(filePath));
            // StringBody name = new StringBody("这个一测试", ContentType.TEXT_PLAIN);
            HttpEntity reqEntity = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE).addPart("uploadFile",
                                                                                                                         fileBody).setCharset(CharsetUtils.get("UTF-8")).build();
            httpPost.setEntity(reqEntity);
            System.out.println("execute request: " + httpPost.getRequestLine());
            HttpResponse response = httpClient.execute(httpPost);

            // httpPost = new HttpPost(response.getLastHeader("location").getValue());
            // response = httpClient.execute(httpPost);
            System.out.println("----------------------------------------");
            System.out.println(response.getStatusLine());
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                // 响应长度
                System.out.println("Response content length: " + resEntity.getContentLength());
                // 打印响应内容
                System.out.println("Response content: " + EntityUtils.toString(resEntity));
            }
            EntityUtils.consume(resEntity);
            httpPost.abort();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 文件下载
     */
    public static void download(String url) {
        // 生成一个httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                // 响应长度
                System.out.println("Response content length: " + resEntity.getContentLength());
                InputStream in = resEntity.getContent();
                String fileName = url.substring(url.lastIndexOf("/"));
                File file = new File("C:\\TDDownload" + fileName);
                try {
                    FileOutputStream fout = new FileOutputStream(file);
                    int l = -1;
                    byte[] tmp = new byte[1024];
                    while ((l = in.read(tmp)) != -1) {
                        fout.write(tmp, 0, l);
                    }
                    fout.flush();
                    fout.close();
                } finally {
                    in.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        String reqURL = "https://10.46.4.119:8182/cxf/POSTOnlineEnquiryService?wsdl";

        try {
            // System.clearProperty("javax.net.ssl.trustStore");
            // System.clearProperty("javax.net.ssl.keyStoreType");
            // System.clearProperty("javax.net.ssl.keyStorePassword");
            // System.setProperty("javax.net.ssl.keyStore", "c:/OctopusStoreKey/certs/esb.jks" );
            // System.setProperty("javax.net.ssl.keyStorePassword", "password");
            // System.setProperty("javax.net.ssl.keyStoreType", "JKS");
            // System.setProperty("javax.net.ssl.trustStore", "c:/OctopusStoreKey/certs/esb.jks/esb.jks");
            // System.setProperty("javax.net.ssl.trustStorePassword", "password" );
            // System.setProperty("javax.net.ssl.trustStoreType", "JKS");

            reqURL = "http://192.168.2.144:8080/SptInfoManagement/ajaxGetAllTreeNodes.action";
            reqURL = "http://127.0.0.1:8080/FlowMovie/views/short_phone_ad/list?showCount=10&currentPage=2";
            reqURL = "http://localhost:8080/Spring4Test/notice";
            
            Map<String, String> paramsMap = new HashMap<String, String>();
            paramsMap.put("title", "公告");
            paramsMap.put("type", "2");
//            doGet(reqURL, null);
            doPost(reqURL, paramsMap);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

}
