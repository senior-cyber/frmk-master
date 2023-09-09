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

    public static String column(String tableName, Attribute<?, ?> columnDsl) {
        Field field = (Field) columnDsl.getJavaMember();
        return column(tableName, field);
    }

    public static String column(Attribute<?, ?> columnDsl) {
        Field field = (Field) columnDsl.getJavaMember();
        return column(field);
    }

    public static String column(Field field) {
        String tableName = table(field.getDeclaringClass());
        return column(tableName, field);
    }

    private static String column(String tableName, Field field) {
        OneToMany oneToMany = field.getAnnotation(OneToMany.class);
        OneToOne oneToOne = field.getAnnotation(OneToOne.class);
        ManyToOne manyToOne = field.getAnnotation(ManyToOne.class);
        ManyToMany manyToMany = field.getAnnotation(ManyToMany.class);
        if (oneToMany == null && oneToOne == null && manyToOne == null && manyToMany == null) {
            Column column = field.getAnnotation(Column.class);
            if (column != null && StringUtils.isNotEmpty(column.name())) {
                return tableName + "." + column.name();
            } else {
                return tableName + "." + field.getName();
            }
        } else {
            if (manyToMany != null) {
                JoinTable joinTable = field.getAnnotation(JoinTable.class);
                JoinColumn joinColumn = joinTable.joinColumns()[0];
                return tableName + "." + joinColumn.name();
            } else {
                JoinColumn joinColumn = field.getAnnotation(JoinColumn.class);
                if (joinColumn != null && StringUtils.isNotEmpty(joinColumn.name())) {
                    return tableName + "." + joinColumn.name();
                } else {
                    return tableName + "." + field.getName();
                }
            }
        }
    }

}
