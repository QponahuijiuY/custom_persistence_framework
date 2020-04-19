package com.mutong.config;

import com.mutong.pojo.Configuration;
import com.mutong.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * @description:
 * @Author: Mutong
 * @Date: 2020-04-19 15:42
 * @time_complexity: O()
 */
public class XMLMapperBuilder {
    private Configuration configuration;
    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = new Configuration();
    }
    public void parse(InputStream inputStream) throws DocumentException {
        Document document = new SAXReader().read(inputStream);
        Element rootElement = document.getRootElement();
        List<Element> list = rootElement.selectNodes("//select");
        for (Element element : list) {
            String id = element.attributeValue("id");
            String paramterType = element.attributeValue("paramterType");
            String resultType = element.attributeValue("resultType");
            String sqlText = element.getTextTrim();
            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setSql(sqlText);
            mappedStatement.setParamterType(paramterType);
            mappedStatement.setResultType(resultType);
            configuration.getMappedStatementMap().put(rootElement.selectNodes("namespace")+id ,mappedStatement);
        }

    }
}
