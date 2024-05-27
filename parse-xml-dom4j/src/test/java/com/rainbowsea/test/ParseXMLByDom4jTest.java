package com.rainbowsea.test;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import java.io.InputStream;
import java.io.Reader;
import java.util.List;
import java.util.Properties;

public class ParseXMLByDom4jTest {
    @Test
    public void testParseMyBatisConfigXML() throws DocumentException {
        // 创建SAReader 对象
        SAXReader saxReader = new SAXReader();   // org.dom4j.io.SAXReader;

        // 获取输入流
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("mybatis-config.xml"); //
        // 默认从类路径下获取
        // 读XML文件，返回document对象，document对象是文档对象，代表了整个XML文件。
        Document document = saxReader.read(is);
        //System.out.println(document);

        // 获取文件当中的根标签
        Element rootElement = document.getRootElement();
        String rootElementName = rootElement.getName();

        //System.out.println("根节点的名字" + rootElementName);

        // 获取default默认的环境id
        // xpath 是做标签路径匹配的，能够让我们快速定位xml文件中的元素
        // 以下的xpath代表了，从根下开始找 configuration标签，然后我configuration标签下的子标签 enviroments
        String xpath = "/configuration/environments";
        Element environments = (Element) document.selectSingleNode(xpath); // Element 是Node类的子类，方法更多，使用更便捷
        //System.out.println(environments);

        // 获取属性的值
        String defaultEnvironmentId = environments.attributeValue("default");
        //System.out.println("默认环境的id = " + defaultEnvironmentId);

        // 获取具体的环境environment
        xpath = "/configuration/environments/environment[@id='" + defaultEnvironmentId + "']";

        //System.out.println(xpath);
        Element environment = (Element) document.selectSingleNode(xpath);
        // 获取environment 节点下的 transactionManager节点
        Element transactionManager = (Element) environment.element("transactionManager");
        String transactionType = transactionManager.attributeValue("type");
        //System.out.println("事务管理器的类型" + transactionType);


        // 获取dataSource节点
        Element dataSource = (Element) environment.element("dataSource");
        String dataSourceType = dataSource.attributeValue("type");
        System.out.println("数据源的类型：" + dataSourceType);

        // 获取dataSource节点下的所有子节点
        List<Element> propertyElts = dataSource.elements();

        // 遍历
        propertyElts.forEach(propertyElt->{
            String name = propertyElt.attributeValue("name");
            String value = propertyElt.attributeValue("value");
            System.out.println(name + "=" + value);

        });


        // 获取所有的mapper标签
        // 不想从根下开始获取，你想从任意位置开始，获取所有的某个标签，xpath该这样写
        xpath ="//mapper";
        List<Node> mappers = document.selectNodes(xpath);

        // 遍历
        mappers.forEach(mapper->{
            Element mapperElt = (Element) mapper;
            String resource = mapperElt.attributeValue("resource");
            System.out.println(resource);

        });

    }


    @Test
    public void testParseSqlMapperXML() throws DocumentException {
        SAXReader saxReader = new SAXReader(); // org.dom4j.io.SAXReader;
        InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("CarMapper.xml"); // 获取到从根路径下
        Document document = saxReader.read(inputStream);

        // 获取namespace
        String xpath = "/mapper";
        Element mapper = (Element) document.selectSingleNode(xpath);
        String namespace = mapper.attributeValue("namespace");
        //System.out.println(namespace);

        // 获取mapper节点下所有的子节点
        List<Element> elements = mapper.elements();

        // 遍历
        elements.forEach(element ->{
            // 获取sqlId
            String id = element.attributeValue("id");
            System.out.println(id);
            String resultType = element.attributeValue("resultType");
            System.out.println(resultType);

            // 获取标签中sql语句（表示获取标签中的文本内容，而且去除前后空白）
            String sql = element.getTextTrim();
            System.out.println(sql);

            // insert into t_car(id,car_num,brand,guide_price,produce_time,car_type) values (null,#{carNum},#{brand},#{guiderPrice},#{produceTime},#{carType})
            // insert into t_car(id,car_num,brand,guide_price,produce_time,car_type) values (null,?,?,?,?,?)
            // mybatis封装了jdbc，早晚要执行带？的sql语句
            // 转换，替换
            String newSql = sql.replaceAll("#\\{[0-9a-zA-Z_$]*}", "?");
            System.out.println(newSql);
        });

    }
}
