package com.mutong.sqlsession;

import java.util.List;

/**
 * @description:
 * @Author: Mutong
 * @Date: 2020-04-19 15:58
 * @time_complexity: O()
 */
public interface SqlSession {
    public <E> List<E> selectList(String statementid,Object...params);

    public <T> T selectOne(String statementid,Object...params);
}
