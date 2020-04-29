package com.lanjy.blog;


import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.font.KumoFont;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.nlp.tokenizers.ChineseWordTokenizer;
import com.kennycason.kumo.palette.LinearGradientColorPalette;
import com.lanjy.blog.exception.PageNotFoundException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.security.jca.GetInstance;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.IOException;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @项目名称：lanjyblog
 * @包名： com.lanjy.blog
 * @类描述：
 * @创建人：lanjy
 * @创建时间：2020/1/11
 */

@RestController
@RequestMapping("blog/")
public class DemoApplication {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) throws IOException {
//        System.out.println(LocalDateTime.now());
//        System.out.println(new Date());
        jsoupTest1();
    }



    public static void jsoupTest1() throws IOException {
        String url = "http://www.cnit5.com";
        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.getElementsByClass("main-z-n");
        Elements aElements = elements.get(0).getElementsByTag("a");
        for (Element element : aElements) {
            System.out.println(element);
            System.out.println("=========================================");
        }
    }


    public static List<String> jsoupTest() throws IOException {
        String s1 = "https://movie.douban.com/top250?start=";
        String s2 = "&filter=";
        String link = null;   // 电影的链接
        String title = null;  // 电影名称
        String score = null;  // 电影评分
        String num = null ;   // 获取评价人数
        String inqStr = null ;   // 获取评价人数
        // 存储待爬取的网址url链接
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i <= 50; i += 25) {
            list.add(s1+i+s2);
        }
        // 遍历url集合 爬取网页数据
        List<String> inqList = new ArrayList<>();
        for (String string : list) {
//            Document doc = Jsoup.connect(string).userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.12; rv:60.0) Gecko/20100101 Firefox/60.0").timeout(6000).get();
            Document doc = Jsoup.connect(string).timeout(6000).get();
            Element content = doc.getElementById("content");
            Elements infos = content.getElementsByClass("info");
            for (Element element : infos) {
                /*Element inq = element.getElementsByClass("inq").get(0);
                inqStr = inq.html();
                inqList.add(inqStr);
                Element links = element.getElementsByTag("a").get(0);
                title = links.child(0).html();    // 获取电影名称
                inqList.add(title);*/

                Element links = element.getElementsByTag("a").get(0);
                Element star = element.getElementsByClass("star").get(0);
                Element inq = element.getElementsByClass("inq").get(0);
                link = links.attr("href");        // 获取电影的链接
                title = links.child(0).html();    // 获取电影名称
                score = star.child(1).html();     // 获取电影评分
                num = star.child(3).html();       // 获取评价人数
                System.out.println(link + "\t" + title + "\t评分" + score + "\t" + num + "\t"+inq.html());
            }
        }
        //createWordCloud(inqList);
        return inqList;
    }



    //"E:\\爬虫/wy.png"
    public static void createWordCloud(List<String> books) throws IOException {

        //建立词频分析器，设置词频，以及词语最短长度，此处的参数配置视情况而定即可
        FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(600);
        frequencyAnalyzer.setMinWordLength(2);

        //引入中文解析器
        frequencyAnalyzer.setWordTokenizer( new ChineseWordTokenizer());
        //指定文本文件路径，生成词频集合
        final List<WordFrequency> wordFrequencyList = new ArrayList<WordFrequency>();

        for (String book : books){
            wordFrequencyList.add(new WordFrequency(book,new Random().nextInt(books.size())));
        }
        //设置图片分辨率
        Dimension dimension = new Dimension(1920,1080);
        //此处的设置采用内置常量即可，生成词云对象
        WordCloud wordCloud = new WordCloud(dimension,CollisionMode.PIXEL_PERFECT);
        //设置边界及字体
        wordCloud.setPadding(1);
        java.awt.Font font = new java.awt.Font("STSong-Light", 1, 8);
        //设置词云显示的三种颜色，越靠前设置表示词频越高的词语的颜色
        wordCloud.setColorPalette(new LinearGradientColorPalette(Color.RED, Color.BLUE, Color.GREEN, 30, 30));
        wordCloud.setKumoFont(new KumoFont(font));
        //设置背景色
        wordCloud.setBackgroundColor(new Color(255,255,255));
        //设置背景图片
        //wordCloud.setBackground(new PixelBoundryBackground("E:\\爬虫/google.jpg"));
        //设置背景图层为圆形
//        wordCloud.setBackground(new CircleBackground(255));
        wordCloud.setFontScalar(new SqrtFontScalar(12, 40));
        //生成词云
        wordCloud.build(wordFrequencyList);
        wordCloud.writeToFile("E:\\爬虫/wy.png");
    }

}
