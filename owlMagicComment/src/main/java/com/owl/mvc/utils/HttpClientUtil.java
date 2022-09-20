package com.owl.mvc.utils;

import com.owl.util.ObjectUtil;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2022/9/14.
 */
public class HttpClientUtil {
    private static String charsetName = "UTF-8";

    public static String sendPost(String url, String param, String headerMap) {
        Map<String, Object> stringObjectMap = ObjectUtil.StringToMap(headerMap);
        Map<String, String> reulst = new HashMap<>();
        if (null != stringObjectMap) {
            stringObjectMap.forEach((key, value) -> reulst.put(key, value.toString()));
        }
        return HttpClientUtil.sendPost(url, param, reulst);
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * @param url       发送请求的 URL
     * @param param     请求参数，Json格式。
     * @param headerMap 请求参数，Json格式。
     * @return 响应结果
     */
    public static String sendPost(String url, String param, Map<String, String> headerMap) {
        System.out.println("-------url=" + url);
        System.out.println("-------param=" + param);
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            URLConnection conn = getUrlConnection(url, headerMap);

            HttpURLConnection httpUrlConnection = (HttpURLConnection) conn;
            // 发送POST请求必须设置如下两行
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.setDoInput(true);
            out = new PrintWriter(new OutputStreamWriter(httpUrlConnection.getOutputStream(), charsetName));
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 按返回码判断获取哪个输出流，避免java.io.FileNotFoundException:的错误
            int responseCode = httpUrlConnection.getResponseCode();
            InputStream inputStream = null;
            if (responseCode == 200) {
                inputStream = new BufferedInputStream(httpUrlConnection.getInputStream());
            } else {
                inputStream = new BufferedInputStream(httpUrlConnection.getErrorStream());
            }
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(inputStream, charsetName));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        System.out.println("-------result=" + result);
        return result.toString();
    }

    /**
     * 设置heder并得到连接
     */
    private static URLConnection getUrlConnection(String url, Map<String, String> headerMap) throws IOException {
        URL realUrl = new URL(url);
        // 打开和URL之间的连接
        URLConnection conn = realUrl.openConnection();

        // 设置通用的请求属性
        conn.setRequestProperty("Content-Type", "application/json;charset=" + charsetName);
        //conn.setRequestProperty("Accept", "*/*");
        //conn.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
        conn.setRequestProperty("Connection", "keep-alive");
        //conn.setRequestProperty("Charset", "UTF-8");
        //conn.setRequestProperty("Accept-Language", "zh-CN");
        //设置浏览器类型和版本、操作系统，使用语言等信息
        headerMap.forEach(conn::setRequestProperty);
//        if (headerMap != null && headerMap.size() > 0) {
//            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
//                if (entry.getKey() != null && entry.getValue() != null) {
//                    conn.setRequestProperty(entry.getKey(), entry.getValue());
//                }
//            }
//        }
        return conn;
    }


    public static String sendGet(String url, Map<String, String> param) {
        AtomicReference<String> params = new AtomicReference<>("");
        param.forEach((key, value) -> {
            params.set(params.get() + "&" + key + "=" + value);
        });
        return sendGet(url, params.get());
    }


    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static String sendPost(String url, Map<String, String> param) {
        AtomicReference<String> params = new AtomicReference<>("");
        param.forEach((key, value) -> {
            params.set(params.get() + "&" + key + "=" + value);
        });
        return sendPost(url, params.get());
    }

    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}
