package com.senior.cyber.frmk.repository;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Repository {

    public static List<StringBuffer> installJdbc(DataSource dataSource) throws SQLException, IOException {
        List<StringBuffer> scripts = new ArrayList<>();
        {
            String table = Jdbc.INSTALL;
            String pk = "install_id";
            StringBuffer tableScript = new StringBuffer();
            tableScript.append("CREATE TABLE IF NOT EXISTS " + table + " (").append("\n");
            tableScript.append("  " + pk + " VARCHAR(100) NOT NULL").append(",").append("\n");
            tableScript.append("  `table` VARCHAR(100) NOT NULL").append(",").append("\n");
            tableScript.append("  pk VARCHAR(100) NOT NULL").append(",").append("\n");
            tableScript.append("  level INT(1) NOT NULL").append(",").append("\n");
            tableScript.append("  history BIT(1) NOT NULL").append(",").append("\n");
            tableScript.append("  draft_yes INT(2)").append(",").append("\n");
            tableScript.append("  draft_no INT(2)").append(",").append("\n");
            tableScript.append("  review_yes INT(2)").append(",").append("\n");
            tableScript.append("  review_no INT(2)").append(",").append("\n");
            tableScript.append("  PRIMARY KEY (" + pk + ")").append("\n");
            tableScript.append(");");

            scripts.add(tableScript);
            scripts.add(uniqueIndex(table, "`table`"));
            scripts.add(index(table, "pk"));
        }

        {
            String table = Jdbc.TASK;
            String pk = "task_id";
            StringBuffer tableScript = new StringBuffer();
            tableScript.append("CREATE TABLE IF NOT EXISTS " + table + " (").append("\n");
            tableScript.append("  " + pk + " VARCHAR(100) NOT NULL").append(",").append("\n");
            tableScript.append("  `table` VARCHAR(100) NOT NULL").append(",").append("\n");
            tableScript.append("  dt_when DATETIME NOT NULL").append(",").append("\n");
            tableScript.append("  action VARCHAR(10) NOT NULL").append(",").append("\n");
            tableScript.append("  op VARCHAR(10) NOT NULL").append(",").append("\n");
            tableScript.append("  draft_yes INT(2) NOT NULL").append(",").append("\n");
            tableScript.append("  draft_no INT(2) NOT NULL").append(",").append("\n");
            tableScript.append("  review_yes INT(2) NOT NULL").append(",").append("\n");
            tableScript.append("  review_no INT(2) NOT NULL").append(",").append("\n");
            tableScript.append("  PRIMARY KEY (" + pk + ")").append("\n");
            tableScript.append(");");

            scripts.add(tableScript);
            scripts.add(index(table, "`table`"));
            scripts.add(index(table, "op"));
            scripts.add(index(table, "action"));
            scripts.add(index(table, "dt_when"));
        }

        return scripts;
    }

    public static List<StringBuffer> installTable(DataSource dataSource, String userTable, String userPk, boolean history, int level, int draftYes, int draftNo, int reviewYes, int reviewNo, Map<String, String> tables) throws IOException, SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        List<StringBuffer> scripts = new ArrayList<>();

        for (Map.Entry<String, String> table : tables.entrySet()) {
            scripts.addAll(configureTable(jdbcTemplate, userTable, userPk, table.getKey(), table.getValue(), history, level, draftYes, draftNo, reviewYes, reviewNo));
        }

        return scripts;
    }

    private static List<StringBuffer> configureTable(JdbcTemplate jdbcTemplate, String userTable, String userPk, String table, String pk, boolean history, int level, int draftYes, int draftNo, int reviewYes, int reviewNo) throws SQLException, IOException {

        List<StringBuffer> scripts = new ArrayList<>();

        {
            StringBuffer script = new StringBuffer();
            script.append("DELETE FROM " + Jdbc.INSTALL + " WHERE `table` = '").append(table).append("';");
            scripts.add(script);
        }
        {
            StringBuffer script = new StringBuffer();
            script.append("INSERT INTO " + Jdbc.INSTALL + "(install_id, `table`, pk, level, history, draft_yes, draft_no, review_yes, review_no) values (uuid(), '").append(table).append(", ").append(pk).append("', ").append(level).append(", ").append(history).append(", ").append(draftYes).append(", ").append(draftNo).append(", ").append(reviewYes).append(", ").append(reviewNo).append(");");
            scripts.add(script);
        }

        scripts.addAll(configureHistoryTable(jdbcTemplate, table, pk, userTable, userPk));
        scripts.addAll(configureHistorySummaryTable(jdbcTemplate, table, pk, userTable, userPk));

        scripts.addAll(configureLevelTable(jdbcTemplate, table, pk, "draft", userTable, userPk));
        scripts.addAll(configureLevelSummaryTable(jdbcTemplate, table, pk, "draft", userTable, userPk));

        scripts.addAll(configureLevelTable(jdbcTemplate, table, pk, "review", userTable, userPk));
        scripts.addAll(configureLevelSummaryTable(jdbcTemplate, table, pk, "review", userTable, userPk));

        return scripts;
    }

    private static List<StringBuffer> configureLevelSummaryTable(JdbcTemplate jdbcTemplate, String table, String pk, String level, String userTable, String userPk) throws SQLException, IOException {
        List<StringBuffer> scripts = new ArrayList<>();
        String levelTable = table + "_" + level.substring(0, 3);
        String levelSummaryTable = levelTable + "_" + Jdbc.SUMMARY_TABLE;
        String levelSummaryPk = levelSummaryTable + "_id";
        String levelPk = levelTable + "_id";
        StringBuffer tableScript = new StringBuffer();
        tableScript.append("CREATE TABLE " + levelSummaryTable + " (").append("\n");
        tableScript.append("  " + levelSummaryPk + " VARCHAR(100) NOT NULL").append(",").append("\n");
        tableScript.append("  " + levelPk + " " + cloneFieldType(jdbcTemplate, table, pk) + " NOT NULL").append(",").append("\n");
        tableScript.append("  " + userPk + " " + cloneFieldType(jdbcTemplate, userTable, userPk) + " NOT NULL").append(",").append("\n");
        tableScript.append("  action" + " VARCHAR(200) NOT NULL").append(",").append("\n");
        tableScript.append("  reason" + " VARCHAR(1000)").append(",").append("\n");
        tableScript.append("  PRIMARY KEY (" + levelSummaryPk + ")").append("\n");
        tableScript.append(");");

        scripts.add(dropTable(levelSummaryTable));
        scripts.add(tableScript);
        scripts.add(uniqueIndex(levelSummaryTable, levelPk, userPk));
        scripts.add(index(levelSummaryTable, "action"));
        scripts.add(index(levelSummaryTable, "reason"));
        return scripts;
    }

    private static List<StringBuffer> configureHistoryTable(JdbcTemplate jdbcTemplate, String table, String pk, String userTable, String userPk) throws SQLException, IOException {
        List<StringBuffer> scripts = new ArrayList<>();
        String historyTable = StringUtils.lowerCase(table + "_" + Jdbc.HISTORY_TABLE);
        String historyPk = StringUtils.lowerCase(historyTable + "_id");
        List<String> fields = lookupField(jdbcTemplate, table);
        StringBuffer tableScript = new StringBuffer();
        tableScript.append("CREATE TABLE " + historyTable + " (").append("\n");
        tableScript.append("  " + historyPk + " VARCHAR(100) NOT NULL").append(",").append("\n");
        tableScript.append("  " + pk + " " + cloneFieldType(jdbcTemplate, table, pk) + " NOT NULL").append(",").append("\n");
        tableScript.append("  dt_when DATETIME NOT NULL DEFAULT NOW()").append(",").append("\n");
        tableScript.append("  level INT(2) NOT NULL").append(",").append("\n");
        tableScript.append("  " + userPk + " " + cloneFieldType(jdbcTemplate, userTable, userPk) + " NOT NULL").append(",").append("\n");
        tableScript.append("  op VARCHAR(6)").append(" NOT NULL,").append("\n");
        // tableScript.append("  " + Engine.SYSTEM_TRANSACTION_ID + " VARCHAR(100)").append(" NOT NULL,").append("\n");
        for (String field : fields) {
            String type = cloneFieldType(jdbcTemplate, table, field);
            tableScript.append("  " + Jdbc.BEFORE + "_" + field + " " + type).append(",").append("\n");
            tableScript.append("  " + Jdbc.AFTER + "_" + field + " " + type).append(",").append("\n");
        }
        tableScript.append("  PRIMARY KEY (" + historyPk + ")").append("\n");
        tableScript.append(");");

        scripts.add(dropTable(historyTable));
        scripts.add(tableScript);
        scripts.add(index(historyTable, pk));
        scripts.add(index(historyTable, "op"));
        scripts.add(index(historyTable, "level"));
        // scripts.add(index(historyTable, Engine.SYSTEM_TRANSACTION_ID));
        scripts.add(index(historyTable, "dt_when"));
        scripts.add(index(historyTable, userPk));
        return scripts;
    }

    private static List<StringBuffer> configureLevelTable(JdbcTemplate jdbcTemplate, String table, String pk, String level, String userTable, String userPk) throws SQLException, IOException {
        List<StringBuffer> scripts = new ArrayList<>();
        String levelTable = StringUtils.lowerCase(table + "_" + level.substring(0, 3));
        String levelPk = StringUtils.lowerCase(levelTable + "_id");
        List<String> fields = lookupField(jdbcTemplate, table);
        StringBuffer tableScript = new StringBuffer();
        tableScript.append("CREATE TABLE " + levelTable + " (").append("\n");
        tableScript.append("  " + levelPk + " VARCHAR(100) NOT NULL").append(",").append("\n");
        tableScript.append("  " + pk + " " + cloneFieldType(jdbcTemplate, table, pk) + " NOT NULL").append(",").append("\n");
        tableScript.append("  yes INT(2)").append(",").append("\n");
        tableScript.append("  no INT(2)").append(",").append("\n");
        tableScript.append("  dt_when DATETIME NOT NULL DEFAULT NOW()").append(",").append("\n");
        tableScript.append("  " + userPk + " " + cloneFieldType(jdbcTemplate, userTable, userPk) + " NOT NULL").append(",").append("\n");
        tableScript.append("  op VARCHAR(6)").append(" NOT NULL,").append("\n");
        // tableScript.append("  " + Engine.SYSTEM_TRANSACTION_ID + " VARCHAR(100)").append(" NOT NULL,").append("\n");
        for (String field : fields) {
            String type = cloneFieldType(jdbcTemplate, table, field);
            tableScript.append("  " + Jdbc.BEFORE + "_" + field + " " + type).append(",").append("\n");
            tableScript.append("  " + Jdbc.AFTER + "_" + field + " " + type).append(",").append("\n");
        }
        tableScript.append("  PRIMARY KEY (" + levelPk + ")").append("\n");
        tableScript.append(");");

        scripts.add(dropTable(levelTable));
        scripts.add(tableScript);

        scripts.add(index(levelTable, "yes"));
        scripts.add(index(levelTable, "no"));
        scripts.add(index(levelTable, "op"));
        // scripts.add(index(levelTable, Engine.SYSTEM_TRANSACTION_ID));
        scripts.add(index(levelTable, "dt_when"));
        scripts.add(index(levelTable, pk));
        scripts.add(index(levelTable, userPk));
        return scripts;
    }

    private static StringBuffer index(String tableName, String fieldName) {
        return index(tableName, new String[]{fieldName});
    }

    private static StringBuffer index(String tableName, String... fieldNames) {
        List<String> temp = new ArrayList<>();
        for (String fieldName : fieldNames) {
            temp.add(fieldName + " ASC");
        }
        StringBuffer script = new StringBuffer();
        script.append("CREATE INDEX ").append(tableName).append("_").append(randomSuffix()).append("\n");
        script.append("  ON ").append(tableName).append("(").append(StringUtils.join(temp, ", ")).append(");");
        return script;
    }

    private static StringBuffer uniqueIndex(String tableName, String fieldName) {
        return uniqueIndex(tableName, new String[]{fieldName});
    }

    private static StringBuffer uniqueIndex(String tableName, String... fieldNames) {
        List<String> temp = new ArrayList<>();
        for (String fieldName : fieldNames) {
            temp.add(fieldName + " ASC");
        }
        StringBuffer script = new StringBuffer();
        script.append("CREATE UNIQUE INDEX ").append(tableName).append("_").append(randomSuffix()).append("\n");
        script.append("  ON ").append(tableName).append("(").append(StringUtils.join(temp, ", ")).append(");");
        return script;
    }

    private static String randomSuffix() {
        return StringUtils.lowerCase(RandomStringUtils.randomAlphabetic(5));
    }

    private static StringBuffer dropTable(String tableName) {
        StringBuffer script = new StringBuffer();
        script.append("DROP TABLE IF EXISTS ").append(tableName).append(";");
        return script;
    }

    private static List<StringBuffer> configureHistorySummaryTable(JdbcTemplate jdbcTemplate, String table, String pk, String userTable, String userPk) throws SQLException, IOException {
        List<StringBuffer> scripts = new ArrayList<>();
        String historyTable = table + "_" + Jdbc.HISTORY_TABLE;
        String historySummaryTable = historyTable + "_" + Jdbc.SUMMARY_TABLE;
        String historySummaryPk = historySummaryTable + "_id";
        String historyPk = historyTable + "_id";
        StringBuffer tableScript = new StringBuffer();
        tableScript.append("CREATE TABLE " + historySummaryTable + " (").append("\n");
        tableScript.append("  " + historySummaryPk + " VARCHAR(100) NOT NULL").append(",").append("\n");
        tableScript.append("  " + historyPk + " VARCHAR(100) NOT NULL").append(",").append("\n");
        tableScript.append("  " + userPk + " " + cloneFieldType(jdbcTemplate, userTable, userPk) + " NOT NULL").append(",").append("\n");
        tableScript.append("  level" + " VARCHAR(10) NOT NULL").append(",").append("\n");
        tableScript.append("  action" + " VARCHAR(200) NOT NULL").append(",").append("\n");
        tableScript.append("  reason" + " VARCHAR(1000)").append(",").append("\n");
        tableScript.append("  PRIMARY KEY (" + historySummaryPk + ")").append("\n");
        tableScript.append(");");

        scripts.add(dropTable(historySummaryTable));
        scripts.add(tableScript);
        scripts.add(index(historySummaryTable, userPk));
        scripts.add(index(historySummaryTable, historyPk));
        scripts.add(index(historySummaryTable, "level"));
        scripts.add(index(historySummaryTable, "action"));
        scripts.add(index(historySummaryTable, "reason"));
        return scripts;
    }

    private static String cloneFieldType(JdbcTemplate jdbcTemplate, String table, String field) throws SQLException {
        List<Column> columns = metaDataTable(jdbcTemplate, table);
        for (Column column : columns) {
            String columnName = column.getColumnName();
            if (columnName.equalsIgnoreCase(field)) {
                String columnTypeName = column.getColumnTypeName();
                return lookupDataType(column, columnTypeName);
            }
        }
        throw new RuntimeException("unknown " + field);
    }

    private static List<String> lookupField(JdbcTemplate jdbcTemplate, String table, String prefix) throws SQLException {
        return lookupField(jdbcTemplate, table, false, prefix);
    }

    private static List<String> lookupField(JdbcTemplate jdbcTemplate, String table, boolean includedPk) throws SQLException {
        return lookupField(jdbcTemplate, table, includedPk, "");
    }

    protected static List<String> lookupField(JdbcTemplate jdbcTemplate, String table) throws SQLException {
        return lookupField(jdbcTemplate, table, false, "");
    }

    private static List<Column> metaDataTable(JdbcTemplate jdbcTemplate, String table) throws SQLException {
        List<Column> columns = new ArrayList<>();
        jdbcTemplate.query("SELECT * FROM " + table + " LIMIT 1", rs -> {
            ResultSetMetaData metaData = rs.getMetaData();
            for (int index = 1; index <= metaData.getColumnCount(); index++) {
                String columnClassName = metaData.getColumnClassName(index);
                String columnName = metaData.getColumnName(index);
                int columnType = metaData.getColumnType(index);
                String columnTypeName = metaData.getColumnTypeName(index);
                int precision = metaData.getPrecision(index);
                int scale = metaData.getScale(index);
                columns.add(new Column(columnName, scale, precision, columnTypeName, columnClassName, columnType));
            }
            return null;
        });
        return columns;
    }

    private static List<String> lookupField(JdbcTemplate jdbcTemplate, String table, boolean includedPk, String prefix) throws SQLException {
        List<String> fields = new ArrayList<>();
        List<Column> columns = metaDataTable(jdbcTemplate, table);
        String pk = StringUtils.lowerCase(table + "_id");
        for (Column column : columns) {
            String columnName = column.getColumnName();
            if (includedPk) {
                if (prefix == null || "".equals(prefix)) {
                    fields.add(columnName);
                } else {
                    fields.add(prefix + columnName);
                }
            } else {
                if (!pk.equals(columnName)) {
                    if (prefix == null || "".equals(prefix)) {
                        fields.add(columnName);
                    } else {
                        fields.add(prefix + columnName);
                    }
                }
            }
        }
        return fields;
    }


    private static String lookupDataType(Column column, String columnTypeName) {
        if ("TIMESTAMP WITH LOCAL TIME ZONE".equals(columnTypeName)
                || "TIMESTAMP WITH TIME ZONE".equals(columnTypeName)) {
            return "TIMESTAMP WITH TIME ZONE";
        } else if ("NUMBER".equals(columnTypeName)) {
            int precision = column.getPrecision();
            int scale = column.getScale();
            return "DECIMAL(" + precision + "," + scale + ")";
        } else if ("VARCHAR2".equals(columnTypeName)) {
            int precision = column.getPrecision();
            return "VARCHAR2(" + precision + ")";
        } else if ("VARCHAR".equals(columnTypeName)) {
            int precision = column.getPrecision();
            return "VARCHAR(" + precision + ")";
        } else if ("TIME".equals(columnTypeName)) {
            return "TIME";
        } else if ("DATETIME".equals(columnTypeName)
                || "TIMESTAMP".equals(columnTypeName)) {
            return "DATETIME";
        } else if ("DATE".equals(columnTypeName)) {
            return "DATE";
        } else if ("BIGINT".equals(columnTypeName)) {
            int precision = column.getPrecision();
            return "BIGINT(" + precision + ")";
        } else if ("INT".equals(columnTypeName)) {
            int precision = column.getPrecision();
            return "INT(" + precision + ")";
        } else if ("BIT".equals(columnTypeName)) {
            int precision = column.getPrecision();
            return "BIT(" + precision + ")";
        } else if ("TINYINT".equals(columnTypeName)) {
            int precision = column.getPrecision();
            return "TINYINT(" + precision + ")";
        } else {
            throw new RuntimeException("unknown " + columnTypeName);
        }
    }

    protected static boolean isMySQL(DataSource dataSource) {
        try {
            return "MySQL".equals(dataSource.getConnection().getMetaData().getDatabaseProductName());
        } catch (SQLException e) {
            return false;
        }
    }

    protected static boolean isOracle(DataSource dataSource) {
        try {
            return "Oracle".equals(dataSource.getConnection().getMetaData().getDatabaseProductName());
        } catch (SQLException e) {
            return false;
        }
    }
}