package com.senior.cyber.frmk.common.jpa;

import jakarta.persistence.*;
import jakarta.persistence.metamodel.Attribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

public class Sql {

    public static String table(Class<?> tableDsl) {
        String name;
        Class<?> clazz = tableDsl;
        StaticMetamodel staticMetamodel = tableDsl.getAnnotation(StaticMetamodel.class);
        if (staticMetamodel != null) {
            clazz = staticMetamodel.value();
        }
        Table table = clazz.getAnnotation(Table.class);
        if (table != null && StringUtils.isNotEmpty(table.name())) {
            name = table.name();
        } else {
            name = clazz.getSimpleName();
        }
        return name;
    }

    public static String column(String aliasTableName, Attribute<?, ?> columnDsl) {
        String tableName = aliasTableName;
        Field f = (Field) columnDsl.getJavaMember();
        OneToMany oneToMany = f.getAnnotation(OneToMany.class);
        OneToOne oneToOne = f.getAnnotation(OneToOne.class);
        ManyToOne manyToOne = f.getAnnotation(ManyToOne.class);
        ManyToMany manyToMany = f.getAnnotation(ManyToMany.class);
        if (oneToMany == null && oneToOne == null && manyToOne == null && manyToMany == null) {
            Column column = f.getAnnotation(Column.class);
            if (column != null && StringUtils.isNotEmpty(column.name())) {
                return tableName + "." + column.name();
            } else {
                return tableName + "." + columnDsl.getName();
            }
        } else {
            if (manyToMany != null) {
                JoinTable joinTable = f.getAnnotation(JoinTable.class);
                JoinColumn joinColumn = joinTable.joinColumns()[0];
                return tableName + "." + joinColumn.name();
            } else {
                JoinColumn joinColumn = f.getAnnotation(JoinColumn.class);
                if (joinColumn != null && StringUtils.isNotEmpty(joinColumn.name())) {
                    return tableName + "." + joinColumn.name();
                } else {
                    return tableName + "." + columnDsl.getName();
                }
            }
        }
    }

    public static String column(Attribute<?, ?> columnDsl) {
        String tableName = table(columnDsl.getJavaMember().getDeclaringClass());
        Field f = (Field) columnDsl.getJavaMember();
        OneToMany oneToMany = f.getAnnotation(OneToMany.class);
        OneToOne oneToOne = f.getAnnotation(OneToOne.class);
        ManyToOne manyToOne = f.getAnnotation(ManyToOne.class);
        ManyToMany manyToMany = f.getAnnotation(ManyToMany.class);
        if (oneToMany == null && oneToOne == null && manyToOne == null && manyToMany == null) {
            Column column = f.getAnnotation(Column.class);
            if (column != null && StringUtils.isNotEmpty(column.name())) {
                return tableName + "." + column.name();
            } else {
                return tableName + "." + columnDsl.getName();
            }
        } else {
            if (manyToMany != null) {
                JoinTable joinTable = f.getAnnotation(JoinTable.class);
                JoinColumn joinColumn = joinTable.joinColumns()[0];
                return tableName + "." + joinColumn.name();
            } else {
                JoinColumn joinColumn = f.getAnnotation(JoinColumn.class);
                if (joinColumn != null && StringUtils.isNotEmpty(joinColumn.name())) {
                    return tableName + "." + joinColumn.name();
                } else {
                    return tableName + "." + columnDsl.getName();
                }
            }
        }
    }

}
