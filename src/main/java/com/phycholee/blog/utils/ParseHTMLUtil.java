package com.phycholee.blog.utils;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.util.NodeList;

/**
 * Created by PhychoLee on 2016/11/28 21:00.
 * Description: 解析HTML
 */
public class ParseHTMLUtil {

    /**
     * 获得img标签的src属性值，即图片链接
     * @param html
     * @return
     */
    @SuppressWarnings("Duplicates")
    public static String getImgSrc(String html){
        StringBuilder sb = new StringBuilder();

        try {
            Parser parser = new Parser(html);
            parser.setEncoding("UTF-8");
            TagNameFilter filter = new TagNameFilter("img");
            NodeList nodeList = parser.extractAllNodesThatMatch(filter);
            if (nodeList != null){
                for (int i=0; i< nodeList.size(); i++){
                    Node node = nodeList.elementAt(i);

                    if (node instanceof ImageTag){
                        ImageTag imageTag = (ImageTag) node;
                        String src = imageTag.getAttribute("src");

                        if (src!=null && !"".equals(src)){
                            sb.append(src+",");
                        }
                    }

                }
            }

            if (!"".equals(sb.toString())){
                sb.delete(sb.length()-1, sb.length());
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        return sb.toString();
    }

    /**
     * 获得正文内容的前180个字符
     * @param html
     * @return
     */
    public static String getText(String html){
        StringBuilder sb = new StringBuilder();
        String substring;

        try {
            Parser parser = new Parser(html);
            parser.setEncoding("UTF-8");
            NodeList nodeList = parser.parse(null);
            if (nodeList != null){
                for (int i=0; i< nodeList.size(); i++){
                    Node node = nodeList.elementAt(i);
                    String text = node.toPlainTextString();

                    sb.append(text+" ");
                    if (sb.length() > 180){
                        break;
                    }
                }
            }

            if (!"".equals(sb.toString()) && sb.length() > 180){
                substring = sb.substring(0, 180);
            }else{
                substring = sb.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        return substring+"...";
    }
}
