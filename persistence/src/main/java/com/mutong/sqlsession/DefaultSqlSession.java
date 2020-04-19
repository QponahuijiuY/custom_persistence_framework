package com.mutong.sqlsession;

import com.mutong.pojo.Configuration;

import java.util.List;

/**
 * @description:
 * @Author: Mutong
 * @Date: 2020-04-19 15:58
 * @time_complexity: O()
 */
public class DefaultSqlSession implements SqlSession {
    private Configuration configuration;
    public DefaultSqlSession(Configuration configuration){
        this.configuration = configuration;
    }

    public <E> List<E> selectList(String statementid, Object... params) {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        List<Object> list = null;
        try {
            list = simpleExecutor.query(configuration, configuration.getMappedStatementMap().get(statementid), params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (List<E>) list;
    }

    public <T> T selectOne(String statementid, Object... params) {
        List<Object> objects = selectList(statementid, params);
        if (objects.size() == 1){
            return (T) objects.get(0);
        }else {
            throw new RuntimeException("查询结果为空或者返回结果过多");
        }
    }
}
