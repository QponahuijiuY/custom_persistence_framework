package com.mutong.sqlsession;

/**
 * @description:
 * @Author: Mutong
 * @Date: 2020-04-19 15:15
 * @time_complexity: O()
 */
public interface SqlSessionFactory {
    SqlSession openSession();
}
