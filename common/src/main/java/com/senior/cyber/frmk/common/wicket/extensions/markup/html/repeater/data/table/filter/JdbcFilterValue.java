package com.senior.cyber.frmk.common.wicket.extensions.markup.html.repeater.data.table.filter;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class JdbcFilterValue implements Serializable {

    private String clause;

    private Map<String, Object> params;

    public JdbcFilterValue(String clause, Map<String, Object> params) {
        this.clause = clause;
        if (params == null) {
            this.params = new LinkedHashMap<>();
        } else {
            this.params = params;
        }
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public String getClause() {
        return clause;
    }
}