package com.senior.cyber.frmk.repository;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public class Column implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private final String columnName;

    private final int scale;

    private final int precision;

    private final String columnTypeName;

    private final String columnClassName;

    private final int columnType;

    public Column(String columnName, int scale, int precision, String columnTypeName, String columnClassName, int columnType) {
        super();
        this.columnName = StringUtils.lowerCase(columnName);
        this.scale = scale;
        this.precision = precision;
        this.columnTypeName = columnTypeName;
        this.columnClassName = columnClassName;
        this.columnType = columnType;
    }

    public int getColumnType() {
        return columnType;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getColumnName() {
        return columnName;
    }

    public int getScale() {
        return scale;
    }

    public int getPrecision() {
        return precision;
    }

    public String getColumnTypeName() {
        return columnTypeName;
    }

    public String getColumnClassName() {
        return columnClassName;
    }

}