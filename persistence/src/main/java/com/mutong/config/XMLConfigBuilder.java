package com.mutong.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mutong.io.Resources;
import com.mutong.pojo.Configuration;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @description:
 * @Author: Mutong
 * @Date: 2020-04-19 15:22
 * @time_complexity: O()
 */
public class XMLConfigBuilder {
    private Configuration configuration;
    public XMLConfigBuilder(){
        this.configuration = new Configuration();
    }
    /**
     * 该方法就是使用dom4j对配置文件进行解析,封装成Configuration
     * @param inputStream
     * @return
     */
    public Configuration parseConfig(InputStream inputStream) throws DocumentException, PropertyVetoException {
        Document document = new SAXReader().read(inputStream);
        //得到根对象<configuration>
        Element rootElement = document.getRootElement();
        List<Element> list = rootElement.selectNodes("//property");
        Properties properties = new Properties();
        for (Element element : list) {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            properties.setProperty(name,value);
        }

        //使用数据库连接池获取连接
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setDriverClass(properties.getProperty("driverClass"));
        comboPooledDataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
        comboPooledDataSource.setUser(properties.getProperty("user"));
        comboPooledDataSource.setPassword(properties.getProperty("password"));
        configuration.setDataSource(comboPooledDataSource);

        //mapper.xml解析:拿到路径
        List<Element> mapperList = rootElement.selectNodes("//mapper");
        for (Element element : mapperList) {
            String mapperPath = element.attributeValue("resource");
            InputStream resourcesAsStream = Resources.getResourcesAsStream(mapperPath);
            XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);
            xmlMapperBuilder.parse(resourcesAsStream);
        }
        return null;
    }
}
