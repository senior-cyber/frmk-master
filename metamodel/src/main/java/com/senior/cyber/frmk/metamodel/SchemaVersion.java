package com.senior.cyber.frmk.metamodel;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.schema.Column;

public class SchemaVersion extends AbstractTable {

    public final Column INSTALLED_RANK;

    public final Column VERSION;

    public final Column DESCRIPTION;

    public final Column TYPE;

    public final Column SCRIPT;

    public final Column CHECKSUM;

    public final Column INSTALLED_BY;

    public final Column INSTALLED_ON;

    public final Column EXECUTION_TIME;

    public final Column SUCCESS;

    public static SchemaVersion staticInitialize(DataContext dataContext) {
        return new SchemaVersion(dataContext);
    }

    private SchemaVersion(DataContext dataContext) {
        super(dataContext, "schema_version");
        this.INSTALLED_RANK = getDelegate().getColumnByName("installed_rank");
        this.VERSION = getDelegate().getColumnByName("version");
        this.DESCRIPTION = getDelegate().getColumnByName("description");
        this.TYPE = getDelegate().getColumnByName("type");
        this.SCRIPT = getDelegate().getColumnByName("script");
        this.CHECKSUM = getDelegate().getColumnByName("checksum");
        this.INSTALLED_BY = getDelegate().getColumnByName("installed_by");
        this.INSTALLED_ON = getDelegate().getColumnByName("installed_on");
        this.EXECUTION_TIME = getDelegate().getColumnByName("execution_time");
        this.SUCCESS = getDelegate().getColumnByName("success");
    }

}
