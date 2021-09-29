package com.senior.cyber.frmk.metamodel;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.*;

import java.util.Collection;
import java.util.List;

public abstract class AbstractTable implements Table {

    private final Table INSTANCE;

    protected AbstractTable(DataContext dataContext, String tableName) {
        this.INSTANCE = dataContext.getDefaultSchema().getTableByName(tableName);
    }

    protected AbstractTable(DataContext dataContext, String schemaName, String tableName) {
        this.INSTANCE = dataContext.getSchemaByName(schemaName).getTableByName(tableName);
    }

    protected Column getInternalColumnByName(String columnName) {
        Column column = this.INSTANCE.getColumnByName(columnName);
        if (column == null) {
            throw new IllegalArgumentException(columnName + " is not found");
        }
        return column;
    }

    @Override
    public final String getName() {
        return INSTANCE.getName();
    }

    @Override
    public final int getColumnCount() {
        return INSTANCE.getColumnCount();
    }

    @Override
    public final List<Column> getColumns() {
        return INSTANCE.getColumns();
    }

    @Override
    public final Column getColumnByName(String columnName) {
        return INSTANCE.getColumnByName(columnName);
    }

    @Override
    public final Column getColumn(int index) throws IndexOutOfBoundsException {
        return INSTANCE.getColumn(index);
    }

    @Override
    public final Schema getSchema() {
        return INSTANCE.getSchema();
    }

    @Override
    public final TableType getType() {
        return INSTANCE.getType();
    }

    @Override
    public final Collection<Relationship> getRelationships() {
        return INSTANCE.getRelationships();
    }

    @Override
    public final Collection<Relationship> getRelationships(Table otherTable) {
        return INSTANCE.getRelationships(otherTable);
    }

    @Override
    public final int getRelationshipCount() {
        return INSTANCE.getRelationshipCount();
    }

    @Override
    public final String getRemarks() {
        return INSTANCE.getRemarks();
    }

    @Override
    public final List<Column> getNumberColumns() {
        return INSTANCE.getNumberColumns();
    }

    @Override
    public final List<Column> getLiteralColumns() {
        return INSTANCE.getLiteralColumns();
    }

    @Override
    public final List<Column> getTimeBasedColumns() {
        return INSTANCE.getTimeBasedColumns();
    }

    @Override
    public final List<Column> getBooleanColumns() {
        return INSTANCE.getBooleanColumns();
    }

    @Override
    public final List<Column> getIndexedColumns() {
        return INSTANCE.getIndexedColumns();
    }

    @Override
    public final Collection<Relationship> getForeignKeyRelationships() {
        return INSTANCE.getForeignKeyRelationships();
    }

    @Override
    public final Collection<Relationship> getPrimaryKeyRelationships() {
        return INSTANCE.getPrimaryKeyRelationships();
    }

    @Override
    public final List<Column> getForeignKeys() {
        return INSTANCE.getForeignKeys();
    }

    @Override
    public final List<Column> getPrimaryKeys() {
        return INSTANCE.getPrimaryKeys();
    }

    @Override
    public final List<String> getColumnNames() {
        return INSTANCE.getColumnNames();
    }

    @Override
    public final List<Column> getColumnsOfType(ColumnType columnType) {
        return INSTANCE.getColumnsOfType(columnType);
    }

    @Override
    public final List<Column> getColumnsOfSuperType(SuperColumnType superColumnType) {
        return INSTANCE.getColumnsOfSuperType(superColumnType);
    }

    protected final Table getDelegate() {
        return INSTANCE;
    }

    @Override
    public final int compareTo(Table o) {
        return INSTANCE.compareTo(o);
    }

    @Override
    public final String getQuote() {
        return INSTANCE.getQuote();
    }

    @Override
    public final String getQuotedName() {
        return INSTANCE.getQuotedName();
    }

    @Override
    public final String getQualifiedLabel() {
        return INSTANCE.getQualifiedLabel();
    }

}