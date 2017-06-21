package com.guonima.wxapp.util;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

/**
 * xml格式数据处理
 *
 * @author : guonima
 * @create : 2017-05-20-14:30
 */
public class XmlParserUtil {

    private static XPath oXpath;

    /**
     * xpath解析xml获取值
     *
     * @param obj xml格式文件file或者xml文件流
     * @return
     */
    private static Document xPathParseXml(Object obj) throws Exception {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(false);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = null;

        if (obj instanceof InputStream) {
            doc = db.parse((InputStream) obj);
        }
        if (obj instanceof File) {
            doc = db.parse((File) obj);
        }

        // 创建XPath对象
        XPathFactory factory = XPathFactory.newInstance();
        oXpath = factory.newXPath();
        return doc;
    }

    /**
     * 构造xml文档对应的document对象
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static Document parseFile(File file) throws Exception {
        if (null == file) {
            return null;
        }
        return xPathParseXml(file);
    }

    /**
     * 构造输入流对应的document对象
     *
     * @param in
     * @return
     * @throws Exception
     */
    public static Document parseStream(InputStream in) throws Exception {
        if (null == in) {
            return null;
        }
        return xPathParseXml(in);
    }

    /**
     * 构造xml字符串对应的document对象
     *
     * @param xml xml字符串
     * @return
     * @throws Exception
     */
    public static Document parseString(String xml) throws Exception {
        if (StringUtils.isEmpty(xml)) {
            return null;
        }
        InputStream in = new ByteArrayInputStream(xml.getBytes("utf-8"));
        return xPathParseXml(in);
    }

    /**
     * 获取结点值
     *
     * @param node xml节点对象
     * @return
     */
    public static String getNodeValue(Node node) {
        if (null == node) {
            return null;
        }
        return node.getTextContent();
    }

    /**
     * 获取结点List
     *
     * @param node  xml节点对象
     * @param xpath xpath表达式
     * @return
     * @throws XPathExpressionException
     */
    public static NodeList getNodeList(Node node, String xpath) throws XPathExpressionException {
        if (null == node || StringUtils.isEmpty(xpath)) {
            return null;
        }
        NodeList nodeList = (NodeList) oXpath.evaluate(xpath, node, XPathConstants.NODESET);
        return nodeList;
    }

    /**
     * 获取单个结点
     *
     * @param node  xml节点对象
     * @param xpath xpath表达式
     * @return
     * @throws XPathExpressionException
     */
    public static Node getNode(Node node, String xpath) throws XPathExpressionException {
        if (null == node || StringUtils.isEmpty(xpath)) {
            return null;
        }
        Node nodeRet = (Node) oXpath.evaluate(xpath, node, XPathConstants.NODE);
        return nodeRet;
    }

    /**
     * 获取单个结点的值
     *
     * @param node  xml节点对象
     * @param xpath xpath表达式
     * @return
     * @throws XPathExpressionException
     */
    public static String getNodeValue(Node node, String xpath) throws XPathExpressionException {
        if (null == node || StringUtils.isEmpty(xpath)) {
            return null;
        }
        Node nodeRet = (Node) oXpath.evaluate(xpath, node, XPathConstants.NODE);
        return nodeRet.getTextContent();
    }

    /**
     * 获取单个结点的值
     *
     * @param xml   xml字符串
     * @param xpath xpath表达式
     * @return
     * @throws XPathExpressionException
     */
    public static String getNodeValue(String xml, String xpath) {
        if (StringUtils.isEmpty(xml) || StringUtils.isEmpty(xpath)) {
            return null;
        }
        try {
            Document doc = parseString(xml);
            if (null == doc) {
                return null;
            }
            Node nodeRet = (Node) oXpath.evaluate(xpath, doc.getFirstChild(), XPathConstants.NODE);
            return nodeRet.getTextContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {

        String xml = "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[商户号mch_id或sub_mch_id不存在]]></return_msg></xml>";
//        String xml = "<xml><appid>123</appid><openid>openid</openid></xml>";
        Document doc = XmlParserUtil.parseString(xml);
        System.out.println(
                XmlParserUtil.getNodeValue(doc.getFirstChild(), "//xml/return_code"));
        System.out.println(
                XmlParserUtil.getNodeValue(doc.getFirstChild(), "//xml/return_code"));

        System.out.println(XmlParserUtil.getNodeValue(xml, "//xml/return_msg"));

    }


}
