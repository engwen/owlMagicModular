package com.owl.pokemon.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2023/6/27.
 */
public class GetPokemonForHttpsUtils {
    public static void main(String[] args) {
        getSkillInfoURL("https://wiki.52poke.com/wiki/%E6%8B%9B%E5%BC%8F%E5%88%97%E8%A1%A8");
    }

    private static void getSkillInfoURL(String Url) {

    }

    private static void getURL(String baseUrl) {
//        创建一个map集合，传入要爬取的页面的url，并定义一个Boole型值，用于判断当前传入的url是否被遍历过；
        Map<String, Boolean> oldMap = new LinkedHashMap<>();
//        如果采集下来的路径是相对路径的话，要将相对路径进行
        String oldLinkHost = "";
//        使用正则表达式，对路径进行占位符替换
        Pattern p = Pattern.compile("(http?://)?[^/\\s]*");
        Matcher m = p.matcher(baseUrl);
        if (m.find()) {
            oldLinkHost = m.group();
//            取到正则之后的值
            //因为baseUrl，也是需要去爬取的，所以要将baseUrl的值，放入到这个集合中,因为未采集，所以定值为false
            oldMap.put(baseUrl, false);

            oldMap = crawlLinks(oldLinkHost, oldMap);//遍历这个url，告诉他传入的值经过正则处理过，如果取到一个相对路径则进行拼接
            //返回的也是一个oldMap；（是个引用类型）
            for (Map.Entry<String, Boolean> mapping : oldMap.entrySet()) {
                System.out.println("链接" + mapping.getKey());
            }
        }
    }

    /**
     * @Description: 实现递归    对传入的url进行处理
     * @Param: [oldLinkList, oldMap]
     * @return: void
     * @Author: 孙伟俊
     * @Date: 2021/3/14
     */


    private static Map<String, Boolean> crawlLinks(String oldLinkHost, Map<String, Boolean> oldMap) {
        Map<String, Boolean> newMap = new LinkedHashMap<String, Boolean>();//创建一个新的容器
//        如果url被遍历过，就不做遍历，
        String oldLink = "";
        for (Map.Entry<String, Boolean> mapping : oldMap.entrySet()) {
            System.out.println("链接" + mapping.getKey() + "----check" + mapping.getValue());
            if (!mapping.getValue()) {
            }
            oldLink = mapping.getKey();
            try {
                URL url = new URL(oldLink);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                //如果拿到的响应值为responseCode=200
                if (connection.getResponseCode() == 200) {
                    //读取所有的a标签   使用BufferedReader 缓冲区来接收,这样读取速度快一点,
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
                    //定义 a标签的正则表达式

                    Pattern p = Pattern.compile("a.*?href=[\\\"']?((https?://)?/?[^\"']+)[\\\"']?.*?>(.+)</a>");
                    Matcher matcher = null;
                    String line = "";
                    //开始流操作
                    while ((line = reader.readLine()) != null) {
                        matcher = p.matcher(line);

                        if (matcher.find()) {
                            String newLink = matcher.group(1).trim();
                            //判断newLink是否为合法的,如果newLink不以http开头，此时的newLink就是一个相对路径
                            if (!newLink.startsWith("http")) {
                                if (newLink.startsWith("/")) {
                                    newLink = oldLinkHost + newLink;
                                } else {
                                    newLink = oldLinkHost + "/" + newLink;
                                }
                            }
                            if (newLink.endsWith("/")) {
                                newLink = newLink.substring(0, newLink.length() - 1);
                            }
                            //判断url有没有重复
                            if (!oldMap.containsKey(newLink)
                                    && !newMap.containsKey(newLink)
                                    && !newLink.startsWith(oldLinkHost)) {
                                newMap.put(newLink, false);
                            }
                        }
                    }
                }
            } catch (Exception e) {

            } finally {

            }
            oldMap.replace(oldLink, false, true);
        }
        if (!newMap.isEmpty()) {
            //将新的url集合和oldMap进行合并
            oldMap.putAll(newMap);
            oldMap.putAll(crawlLinks(oldLinkHost, oldMap));
        }
        return oldMap;
    }

}
