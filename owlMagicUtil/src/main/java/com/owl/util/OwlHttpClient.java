package com.owl.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * http请求工具
 * @author engwen
 * email xiachanzou@outlook.com
 * 2019/7/25.
 */
public class OwlHttpClient {
    public enum HttpTypeEM {
        GET, POST
    }

    public enum DataTypeEM {
        JSON, FORM
    }

    public enum HttpHeadEM {
        //        connection.setRequestProperty("Content-Type", "application/xml;");
        XML("application/xml;text/html;application/xhtml+xml"), JSON("application/json;"), FORM("multipart/form-data");

        private String content;

        HttpHeadEM(String content) {
            this.content = content;
        }
    }

    private String url;
    private HttpTypeEM httpType;
    private HttpHeadEM httpHead;
    private DataTypeEM dataType;
    private Map<String, String> data;

    public HttpHeadEM getHttpHead() {
        return httpHead;
    }

    public void setHttpHead(HttpHeadEM httpHead) {
        this.httpHead = httpHead;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public String getHttpType() {
        return this.httpType.name();
    }

    public void setHttpType(HttpTypeEM httpType) {
        this.httpType = httpType;
    }

    public DataTypeEM getDataType() {
        return dataType;
    }

    public void setDataType(DataTypeEM dataType) {
        this.dataType = dataType;
    }

    public String send() {
        StringBuilder result = new StringBuilder();
        if (RegexUtil.isParamsHaveEmpty(url, httpType)) {
            return "bad url, httpType";
        }
        if (httpType.equals(HttpTypeEM.POST) && RegexUtil.isEmpty(dataType)) {
            return "bad dataType by post";
        }
        if (RegexUtil.isEmpty(httpHead)) {
            return "bad httpHead";
        }
        try {
            URL url = new URL(this.url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod(httpType.name());
            connection.setDoOutput(true);
            connection.setRequestMethod(getHttpType());
            connection.setRequestProperty("Content-Type", httpHead.content + "charset:UTF-8;");
//            connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(60000);
            OutputStreamWriter pw = null;
            switch (httpType) {
                case GET:
//                    PrintWriter out = new PrintWriter(connection.getOutputStream());
//                    data.forEach((key, value) -> out.print("&" + key + "=" + value));
//                    out.flush();
                    break;
                case POST:
                    connection.setDoInput(true);
                    pw = new OutputStreamWriter(connection.getOutputStream());
                    if (DataTypeEM.FORM.equals(dataType)) {
                        pw.write(buildDataStr(data));
                    } else {
//                        ObjectMapper objectMapper = new ObjectMapper();
//                        pw.write(objectMapper.writeValueAsString(data));
                    }
                    pw.flush();
                    pw.close();
                    break;
            }

            connection.connect();
//            connection.getResponseCode();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = br.readLine()) != null) { // 读取数据
                result.append(line);
            }
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    private static String buildDataStr(Map<String, String> data) {
        StringBuilder stringBuilder = new StringBuilder("?");
        if (null != data) {
            data.forEach((key, value) -> stringBuilder.append(key).append("=").append(value).append("&"));
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }

}
