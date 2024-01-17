package com.owl.util;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * author engwen
 * email xiachanzou@outlook.com
 * 2022/9/29.
 */
public class XMLUtils {

    public static void readXml(String filePath, String tagName) {
        try {
            // 创建解析器工厂
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = factory.newDocumentBuilder();
            // 创建一个Document对象
            Document doc = db.parse(filePath);
            NodeList tagList = doc.getElementsByTagName(tagName);
            // 获取节点个数
            System.out.println("一共有" + tagList.getLength() + "个节点");
            // 遍历每个节点
            for (int i = 0; i < tagList.getLength(); i++) {
                Node node = tagList.item(i);
                // 获取指定节点所有属性集合
                NamedNodeMap attrs = node.getAttributes();
                System.out.println("第" + (i + 1) + "个节点，共有" + attrs.getLength() + "属性");
                // 遍历属性，不知道节点属性和属性名情况
                Map<String, Object> temp = new HashMap<>();
                for (int j = 0; j < attrs.getLength(); j++) {
                    // 获取某一个属性
                    Node attr = attrs.item(j);
                    temp.put(attr.getNodeName(), attr.getNodeValue());
                }
                NodeList childNodes = node.getChildNodes();
                for (int k = 0; k < childNodes.getLength(); k++) {
                    // 区分,去掉空格和换行符
                    if (childNodes.item(k).getNodeType() == Node.ELEMENT_NODE) {
                        // 获取element类型的节点和节点值
                        System.out.print("节点名：" + childNodes.item(k).getNodeName());
                        System.out.print(" --- 节点值：" + childNodes.item(k).getFirstChild().getNodeValue());
                        System.out.println(" --- 节点值：" + childNodes.item(k).getTextContent());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
