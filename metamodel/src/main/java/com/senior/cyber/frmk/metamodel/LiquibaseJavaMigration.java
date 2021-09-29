package com.senior.cyber.frmk.metamodel;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public abstract class LiquibaseJavaMigration extends BaseJavaMigration {

    private Database database;

    private String base;

    protected LiquibaseJavaMigration(String base) {
        this.base = base;
    }

    @Override
    public final void migrate(Context context) throws Exception {
        SingleConnectionDataSource dataSource = new SingleConnectionDataSource(context.getConnection(), true);
        dataSource.setAutoCommit(true);

        this.database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(dataSource.getConnection()));

        NamedParameterJdbcTemplate named = new NamedParameterJdbcTemplate(dataSource);

        try {
            doMigrate(named);
            dataSource.getConnection().commit();
        } finally {
            this.database.close();
        }
    }

    protected abstract void doMigrate(NamedParameterJdbcTemplate named) throws Exception;

    @Override
    public final Integer getChecksum() {
        List<String> xml = getXmlChecksum();
        if (xml == null) {
            return getClassChecksum();
        } else {
            return getClassChecksum() + getInternalChecksum(xml);
        }
    }

    private Integer getClassChecksum() {
        String className = getClass().getName();
        String path = StringUtils.replaceChars(className, '.', '/') + ".class";
        try (InputStream stream = getClass().getResourceAsStream("/" + path)) {
            byte[] temp = IOUtils.toByteArray(stream);
            HashCode hashCode = Hashing.sha256().hashBytes(temp);
            return hashCode.asInt();
        } catch (IOException e) {
            return -1;
        }
    }

    protected List<String> getXmlChecksum() {
        return null;
    }

    private Integer getInternalChecksum(List<String> names) {
        int checksum = 0;
        for (String name : names) {
            checksum = checksum + getInternalChecksum(name);
        }
        return checksum;
    }

    private Integer getInternalChecksum(String name) {
        try (InputStream stream = getClass().getResourceAsStream(this.base + name)) {
            byte[] temp = IOUtils.toByteArray(stream);
            HashCode hashCode = Hashing.sha256().hashBytes(temp);
            return hashCode.asInt();
        } catch (NullPointerException | IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    protected void updateLiquibase(String changeset) throws LiquibaseException {
        Liquibase liquibase = new Liquibase(this.base + changeset, new ClassLoaderResourceAccessor(), this.database);
        try {
            liquibase.update(new Contexts(), new LabelExpression());
        } catch (Exception e) {
            Throwable throwable = e;
            while (throwable != null) {
                if (throwable instanceof DatabaseException) {
                    break;
                } else {
                    throwable = throwable.getCause();
                }
            }
            if (throwable != null) {
                System.out.println(throwable.getMessage());
            }
            throw e;
        }
    }

}