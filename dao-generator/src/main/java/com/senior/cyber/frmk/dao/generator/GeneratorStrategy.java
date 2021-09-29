package com.senior.cyber.frmk.dao.generator;

import org.jooq.codegen.DefaultGeneratorStrategy;
import org.jooq.meta.Definition;
import org.jooq.meta.TableDefinition;

public class GeneratorStrategy extends DefaultGeneratorStrategy {

    @Override
    public String getJavaClassName(Definition definition, Mode mode) {
        String name = super.getJavaClassName(definition, mode);
        if (mode == Mode.DAO) {
            return name.substring(0, name.length() - "DAO".length()) + "DAO";
        } else if (mode == Mode.POJO) {
            return name + "Object";
        } else if (mode == Mode.DEFAULT) {
            if (definition instanceof TableDefinition) {
                return name + "Table";
            }
        }
        return name;
    }

    /**
     * <configuration>
     * <generator>
     * <generate>
     * <globalTableReferences>false</globalTableReferences>
     * </generate>
     * <generator>
     * </configuration>
     *
     * @param definition
     * @return
     */
    @Override
    public String getJavaIdentifier(Definition definition) {
        String name = super.getJavaIdentifier(definition);
        if (definition instanceof TableDefinition) {
            return "INSTANCE";
        }
        return name;
    }

}
