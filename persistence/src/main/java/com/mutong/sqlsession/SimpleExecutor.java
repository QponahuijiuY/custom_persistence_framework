package com.mutong.sqlsession;

import com.mutong.config.BoundSql;
import com.mutong.pojo.Configuration;
import com.mutong.pojo.MappedStatement;
import com.mutong.utils.GenericTokenParser;
import com.mutong.utils.ParameterMapping;
import com.mutong.utils.ParameterMappingTokenHandler;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @Author: Mutong
 * @Date: 2020-04-19 16:23
 * @time_complexity: O()
 */
public class SimpleExecutor implements Executor {
    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object[] param) throws Exception {
        //1. 注册驱动,获取连接
        Connection connection = configuration.getDataSource().getConnection();
        //2. 获取sql语句
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);
        //3. 获取预处理对象preparedStatement
        PreparedStatement preparedStatement = connection.prepareStatement(boundSql.getSqlText());
        //4. 设置参数
        String paramterType = mappedStatement.getParamterType();
        Class<?> classType = getClassType(paramterType);

        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        for (int i = 0; i < parameterMappingList.size(); i++) {
            ParameterMapping parameterMapping = parameterMappingList.get(i);
            String content = parameterMapping.getContent();
            //使用反射
            Field declaredFields = classType.getDeclaredField(content);
            declaredFields.setAccessible(true);
            Object o = declaredFields.get(param[0]);
            preparedStatement.setObject(i+1,o);
        }
        //5. 执行sql
        ResultSet resultSet = preparedStatement.executeQuery();
        String resultType = mappedStatement.getResultType();
        Class<?> resultTypeClass = getClassType(resultType);
        Object o = resultTypeClass.newInstance();
        ArrayList<Object> objects = new ArrayList<>();
        //6. 封装返回结果集
        while (resultSet.next()){
            ResultSetMetaData metaData = resultSet.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                //字段名
                String columnName = metaData.getColumnName(i);
                //字段值
                Object value = resultSet.getObject(columnName);
                //使用反射,根据数据库表和实体的对应关系,完成封装
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, resultTypeClass);
                Method writeMethod = propertyDescriptor.getWriteMethod();
                writeMethod.invoke(o,value);
            }
            objects.add(o);
        }
        return (List<E>) objects;
    }

    private Class<?> getClassType(String paramterType) throws ClassNotFoundException {
        if (paramterType != null){
            Class<?> aClass = Class.forName(paramterType);
            return aClass;
        }else{
            return null;
        }
    }

    private BoundSql getBoundSql(String sql) {

        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        String parseSql = genericTokenParser.parse(sql);
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();
        BoundSql boundSql = new BoundSql(parseSql,parameterMappings);
        return boundSql;
    }

    public void close() throws SQLException {

    }
}
