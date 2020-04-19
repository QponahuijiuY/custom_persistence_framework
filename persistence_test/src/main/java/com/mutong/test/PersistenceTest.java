package com.mutong.test;

import com.mutong.io.Resources;
import com.mutong.pojo.User;
import com.mutong.sqlsession.SqlSession;
import com.mutong.sqlsession.SqlSessionFactory;
import com.mutong.sqlsession.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.io.InputStream;

/**
 * @description:
 * @Author: Mutong
 * @Date: 2020-04-19 14:51
 * @time_complexity: O()
 */
public class PersistenceTest {
    @Test
    public void test() throws PropertyVetoException, DocumentException {
        InputStream resourcesAsStream = Resources.getResourcesAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourcesAsStream);
        SqlSession openSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setId(1);
        user.setUsername("Zhangsan");
        User user1 = openSession.selectOne("user.selectOne", user);
        System.out.println(user1);
    }
}
