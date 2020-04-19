package com.mutong.sqlsession;

import com.mutong.pojo.Configuration;
import com.mutong.pojo.MappedStatement;

import java.sql.SQLException;
import java.util.List;

/**
 * @description:
 * @Author: Mutong
 * @Date: 2020-04-19 16:22
 * @time_complexity: O()
 */
public interface Executor {
    <E> List<E> query(Configuration configuration, MappedStatement
            mappedStatement, Object[] param) throws Exception;
    void close() throws SQLException;
}
