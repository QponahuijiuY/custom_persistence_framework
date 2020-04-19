package com.mutong.sqlsession;

import com.mutong.config.XMLConfigBuilder;
import com.mutong.pojo.Configuration;
import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

/**
 * @description:
 * @Author: Mutong
 * @Date: 2020-04-19 15:14
 * @time_complexity: O()
 */
public class SqlSessionFactoryBuilder {
    public SqlSessionFactory build(InputStream inputStream) throws DocumentException, PropertyVetoException {
        //1. 使用dom4j解析配置文件,将解析出来的内容封装到Configuration中
        XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
        //解析Configuration,同时在parseConfig()方法里面,也解析了mapper.xml
        Configuration configuration = xmlConfigBuilder.parseConfig(inputStream);
        //2. 创建sqlSessionFactory对象, 工厂类 : 生产sqlSession:会话对象
        DefaultSqlSessionFactory defaultSqlSessionFactory = new DefaultSqlSessionFactory(configuration);
        return defaultSqlSessionFactory;
    }


}
