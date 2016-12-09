package com.phycholee.blog.test;

import com.phycholee.blog.utils.ParseHTMLUtil;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * Created by PhychoLee on 2016/11/28 19:41.
 * Description: 测试解析xml
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestParseHTML {

    /**
     * 解析html，获取图片链接
     */
    @SuppressWarnings("Duplicates")
    @Test
    public void parseHtml(){
        String html = "<div><h3 id=\"h3-u6D4Bu8BD5\"><a name=\"测试\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>测试</h3><ul>\n" +
                "<li>aa</li><li>bb</li><li>cc</li></ul>\n" +
                "<p>图片<img src=\"/post/201611/4a1d76d6-309c-4157-870a-521f8015b0a6.png\" alt=\"\"></img></p></div>";

        try {
            Parser parser = new Parser(html);
            parser.setEncoding("UTF-8");
            TagNameFilter filter = new TagNameFilter("img");
            NodeList nodeList = parser.extractAllNodesThatMatch(filter);
            if (nodeList != null){
                for (int i=0; i< nodeList.size(); i++){
                    Node node = nodeList.elementAt(i);
                    System.out.println(node.getText());

                    if (node instanceof ImageTag){
                        ImageTag imageTag = (ImageTag) node;
                        String src = imageTag.getAttribute("src");
                        System.out.println(src);
                    }


                }
            }

        } catch (ParserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testParseUtil(){
        String html = "<h3 id=\"h3-u6D4Bu8BD5\"><a name=\"测试\" class=\"reference-link\"></a><span class=\"header-link octicon octicon-link\"></span>测试</h3><ul>\n" +
                "<li>aa</li><li>bb</li><li>cc</li></ul>\n" +
                "<p>图片<img src=\"/post/201611/4a1d76d6-309c-4157-870a-521f8015b0a6.png\" alt=\"\"></p>" +
                "<p>图片<img src=\"/post/201611/4a1d76d6-309c-4157-870a-521f8015b0a6.png\" alt=\"\"></p>";

        String imgSrc = ParseHTMLUtil.getImgSrc(html);
        System.out.println(imgSrc);

    }

    @Test
    public void testParseText(){
        String html = "<h1 id=\"springbootmybatis\">SpringBoot+Mybatis整合</h1>" +
                "<p>前段时间去了解了SpringBoot，感觉这东西太方便，不去要部署到Tomcat，" +
                "也不需要配置文件，直接写一句代码就跑起来一个web项目，直接打包成jar运行就ok了。" +
                "大型项目会不会使用SpringBoot我不太清除，但对于我这种入门程序员想做一些小项目简直太方便。</p>" +
                "<p>入门SpringBoot非常简单，Spring官网有教程，如果学过Spring和SpringMVC，那么简单5分钟即可入门。" +
                "当然，如果没有学习过Spring和SpringMVC也不推荐去学习SpringBoot，建议先将前边两个至少入了门，毕竟这俩才是核心。</p>";
        System.out.println("before length:" + html.length());
        String text = ParseHTMLUtil.getText(html);
        System.out.println(text+" after length:"+text.length());
    }
}