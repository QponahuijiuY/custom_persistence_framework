package com.mutong.config;

import com.mutong.utils.ParameterMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @Author: Mutong
 * @Date: 2020-04-19 16:41
 * @time_complexity: O()
 */
public class BoundSql {
    private String sqlText;
    private List<ParameterMapping> parameterMappingList = new ArrayList<ParameterMapping>();

    public BoundSql(String sqlText, List<ParameterMapping> parameterMappingList) {
        this.sqlText = sqlText;
        this.parameterMappingList = parameterMappingList;
    }

    public String getSqlText() {
        return sqlText;
    }

    public void setSqlText(String sqlText) {
        this.sqlText = sqlText;
    }

    public List<ParameterMapping> getParameterMappingList() {
        return parameterMappingList;
    }

    public void setParameterMappingList(List<ParameterMapping> parameterMappingList) {
        this.parameterMappingList = parameterMappingList;
    }
}
