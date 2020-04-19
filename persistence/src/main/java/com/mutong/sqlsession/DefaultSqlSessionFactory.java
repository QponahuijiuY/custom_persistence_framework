package com.mutong.sqlsession;

import com.mutong.pojo.Configuration;

/**
 * @description:
 * @Author: Mutong
 * @Date: 2020-04-19 15:55
 * @time_complexity: O()
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory{
    private Configuration configuration;
    public DefaultSqlSessionFactory(Configuration configuration){
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
