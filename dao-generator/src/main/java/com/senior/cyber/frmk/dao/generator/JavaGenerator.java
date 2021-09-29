package com.senior.cyber.frmk.dao.generator;

public class JavaGenerator extends org.jooq.codegen.JavaGenerator {

    public JavaGenerator() {
        super();
    }

    @Override
    public boolean generateGlobalSchemaReferences() {
        return false;
    }

    @Override
    public boolean generateGlobalCatalogReferences() {
        return false;
    }

    @Override
    public boolean generateGlobalTableReferences() {
        return false;
    }

    @Override
    public boolean generateGlobalKeyReferences() {
        return false;
    }

}
