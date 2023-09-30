package com.senior.cyber.frmk.repository;

import com.senior.cyber.frmk.jdbc.query.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

public class Jdbc {

    public static final String SUMMARY_TABLE = "sum";
    public static final String HISTORY_TABLE = "his";
    public static final String BEFORE = "bef";
    public static final String AFTER = "aft";
    // public static final String SYSTEM_TRANSACTION_ID = "sys_tid";
    public static final String INSTALL = "sys_install";
    public static final String TASK = "sys_task";
    private static final int LEVEL_NONE = 0;
    private static final int LEVEL_DRAFT = 1;
    private static final int LEVEL_REVIEW = 2;
    private static final String DRAFT_TABLE = "dra";
    private static final String REVIEW_TABLE = "rev";
    private final DataSource dataSource;

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate named;

    private final String userTable;

    private final String userPk;

    public Jdbc(DataSource dataSource, String userTable, String userPk) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.named = new NamedParameterJdbcTemplate(this.dataSource);
        this.userTable = userTable;
        this.userPk = userPk;
    }

    public String insert(String table, Map<String, Object> values) throws SQLException {
        return insert(table, values, new HashMap<>());
    }

    public String insert(String table, Map<String, Object> values, Map<String, Object> systems) throws SQLException {
        SelectQuery selectQuery = new MySqlSelectQuery(INSTALL);
        selectQuery.addWhere("`table` = :table", Map.of("table", table));
        Install install = this.named.queryForObject(selectQuery.toSQL(), selectQuery.toParam(), Install.class);
        if (install == null) {
            throw new SQLException(table + " is not configured");
        }
        String pk = install.getPk();
        Object id = values.remove(pk);
        String historyTable = table + "_" + HISTORY_TABLE;
        String draftTable = table + "_" + DRAFT_TABLE;
        String reviewTable = table + "_" + REVIEW_TABLE;
        String reviewPk = reviewTable + "_id";
        String draftPk = draftTable + "_id";
        String historyPk = historyTable + "_id";

        if (install.getLevel() == LEVEL_NONE) {
            if (install.isHistory()) {
                if (systems.get(this.userPk) == null || "".equals(systems.get(this.userPk))) {
                    throw new SQLException(this.userPk + " is required");
                }
//                if (systems.get(SYSTEM_TRANSACTION_ID) == null || "".equals(systems.get(SYSTEM_TRANSACTION_ID))) {
//                    throw new SQLException(SYSTEM_TRANSACTION_ID + " is required");
//                }
                InsertQuery insertQuery = new InsertQuery(historyTable);
                for (Map.Entry<String, Object> attr : values.entrySet()) {
                    insertQuery.addValue(AFTER + "_" + attr.getKey(), ":" + AFTER + "_" + attr.getKey(), Map.of(AFTER + "_" + attr.getKey(), attr.getValue()));
                }
                insertQuery.addValue(this.userPk, ":" + this.userPk, Map.of(this.userPk, systems.get(this.userPk)));
                insertQuery.addValue("dt_when", ":dt_when", Map.of("dt_when", new Date()));
                insertQuery.addValue("op = :op", "INSERT");
                insertQuery.addValue(pk, ":" + pk, Map.of(pk, id));
                insertQuery.addValue("level", ":level", Map.of("level", install.getLevel()));
                insertQuery.addValue(historyPk, "uuid()");
                // insertQuery.addValue(SYSTEM_TRANSACTION_ID + " = :" + SYSTEM_TRANSACTION_ID, systems.get(SYSTEM_TRANSACTION_ID));
                this.named.update(insertQuery.toSQL(), insertQuery.toParam());
            }
            InsertQuery insertQuery = new InsertQuery(table);
            for (Map.Entry<String, Object> attr : values.entrySet()) {
                insertQuery.addValue(attr.getKey(), ":" + attr.getKey(), Map.of(attr.getKey(), attr.getValue()));
            }
            insertQuery.addValue(pk, ":" + pk, Map.of(pk, id));
            this.named.update(insertQuery.toSQL(), insertQuery.toParam());
            return null;
        } else if (install.getLevel() == LEVEL_DRAFT) {
            if (systems.get(this.userPk) == null || "".equals(systems.get(this.userPk))) {
                throw new SQLException(this.userPk + " is required");
            }
//            if (systems.get(SYSTEM_TRANSACTION_ID) == null || "".equals(systems.get(SYSTEM_TRANSACTION_ID))) {
//                throw new SQLException(SYSTEM_TRANSACTION_ID + " is required");
//            }

            String taskId = this.jdbcTemplate.queryForObject("SELECT uuid() FROM DUAL", String.class);
            InsertQuery insertQuery = new InsertQuery(Jdbc.TASK);
            insertQuery.addValue("task_id = :task_id", taskId);
            insertQuery.addValue("`table` = :table", table);
            insertQuery.addValue("action = :action", "REVIEW");
            insertQuery.addValue("op = :op", "INSERT");
            insertQuery.addValue("draft_yes", ":draft_yes", Map.of("draft_yes", 0));
            insertQuery.addValue("draft_no", ":draft_no", Map.of("draft_no", 0));
            insertQuery.addValue("review_yes", ":review_yes", Map.of("review_yes", 0));
            insertQuery.addValue("review_no", ":review_no", Map.of("review_no", 0));
            insertQuery.addValue("dt_when", ":dt_when", Map.of("dt_when", new Date()));
            this.named.update(insertQuery.toSQL(), insertQuery.toParam());

            insertQuery = new InsertQuery(draftTable);
            for (Map.Entry<String, Object> attr : values.entrySet()) {
                insertQuery.addValue(AFTER + "_" + attr.getKey(), ":" + AFTER + "_" + attr.getKey(), Map.of(AFTER + "_" + attr.getKey(), attr.getValue()));
            }
            insertQuery.addValue(this.userPk, ":" + this.userPk, Map.of(this.userPk, systems.get(this.userPk)));
            insertQuery.addValue("dt_when", ":dt_when", Map.of("dt_when", new Date()));
            insertQuery.addValue("yes", ":yes", Map.of("yes", install.getDraftYes()));
            insertQuery.addValue("no", ":no", Map.of("no", install.getDraftNo()));
            insertQuery.addValue("op", ":op", Map.of("op", "INSERT"));
            insertQuery.addValue(pk, ":" + pk, Map.of(pk, id));
            insertQuery.addValue(draftPk, ":" + draftPk, Map.of(draftPk, taskId));
//            insertQuery.addValue(SYSTEM_TRANSACTION_ID + " = :" + SYSTEM_TRANSACTION_ID, systems.get(SYSTEM_TRANSACTION_ID));
            this.named.update(insertQuery.toSQL(), insertQuery.toParam());
            return taskId;
        } else if (install.getLevel() == LEVEL_REVIEW) {
            if (systems.get(this.userPk) == null || "".equals(systems.get(this.userPk))) {
                throw new SQLException(this.userPk + " is required");
            }
//            if (systems.get(SYSTEM_TRANSACTION_ID) == null || "".equals(systems.get(SYSTEM_TRANSACTION_ID))) {
//                throw new SQLException(SYSTEM_TRANSACTION_ID + " is required");
//            }

            String taskId = this.jdbcTemplate.queryForObject("SELECT uuid() FROM DUAL", String.class);
            InsertQuery insertQuery = new InsertQuery(Jdbc.TASK);
            insertQuery.addValue("task_id", ":task_id", Map.of("task_id", taskId));
            insertQuery.addValue("`table`", ":table", Map.of("table", table));
            insertQuery.addValue("action", ":action", Map.of("action", "APPROVE"));
            insertQuery.addValue("op", ":op", Map.of("op", "INSERT"));
            insertQuery.addValue("draft_yes", ":draft_yes", Map.of("draft_yes", 0));
            insertQuery.addValue("draft_no", ":draft_no", Map.of("draft_no", 0));
            insertQuery.addValue("review_yes", ":review_yes", Map.of("review_yes", 0));
            insertQuery.addValue("review_no", ":review_no", Map.of("review_no", 0));
            insertQuery.addValue("dt_when", ":dt_when", Map.of("dt_when", new Date()));
            this.named.update(insertQuery.toSQL(), insertQuery.toParam());

            insertQuery = new InsertQuery(reviewTable);
            for (Map.Entry<String, Object> attr : values.entrySet()) {
                insertQuery.addValue(AFTER + "_" + attr.getKey(), ":" + AFTER + "_" + attr.getKey(), Map.of(AFTER + "_" + attr.getKey(), attr.getValue()));
            }
            insertQuery.addValue(this.userPk, ":" + this.userPk, Map.of(this.userPk, systems.get(this.userPk)));
            insertQuery.addValue("dt_when", ":dt_when", Map.of("dt_when", new Date()));
            insertQuery.addValue("yes", ":yes", Map.of("yes", install.getReviewYes()));
            insertQuery.addValue("no", ":no", Map.of("no", install.getReviewNo()));
            insertQuery.addValue("op", ":op", Map.of("op", "INSERT"));
            insertQuery.addValue(pk, ":" + pk, Map.of(pk, id));
            insertQuery.addValue(reviewPk, ":" + reviewPk, Map.of(reviewPk, taskId));
            // insertQuery.addValue(SYSTEM_TRANSACTION_ID + " = :" + SYSTEM_TRANSACTION_ID, systems.get(SYSTEM_TRANSACTION_ID));
            this.named.update(insertQuery.toSQL(), insertQuery.toParam());
            return taskId;
        } else {
            return null;
        }
    }

    public String delete(String table, Map<String, Object> wheres) throws SQLException {
        return delete(table, wheres, new HashMap<>());
    }

    public String delete(String table, Map<String, Object> wheres, Map<String, Object> systems) throws SQLException {
        SelectQuery selectQuery = new MySqlSelectQuery(INSTALL);
        selectQuery.addWhere("`table` = :table", Map.of("table", table));
        Install install = this.named.queryForObject(selectQuery.toSQL(), selectQuery.toParam(), Install.class);
        if (install == null) {
            throw new SQLException(table + " is not configured");
        }
        String historyTable = table + "_" + HISTORY_TABLE;
        String historyPk = historyTable + "_id";
        String pk = install.getPk();
        String draftTable = table + "_" + DRAFT_TABLE;
        String reviewTable = table + "_" + REVIEW_TABLE;
        String reviewPk = reviewTable + "_id";
        String draftPk = draftTable + "_id";
        if (install.getLevel() == LEVEL_NONE) {
            if (install.isHistory()) {
                if (systems.get(this.userPk) == null || "".equals(systems.get(this.userPk))) {
                    throw new SQLException(this.userPk + " is required");
                }
//                if (systems.get(SYSTEM_TRANSACTION_ID) == null || "".equals(systems.get(SYSTEM_TRANSACTION_ID))) {
//                    throw new SQLException(SYSTEM_TRANSACTION_ID + " is required");
//                }

                List<String> where = new ArrayList<>();
                Map<String, Object> param = new HashMap<>();
                for (Map.Entry<String, Object> w : wheres.entrySet()) {
                    where.add(w.getKey() + " = :" + w.getKey());
                    param.put(w.getKey(), w.getValue());
                }
                List<String> fromFields = Repository.lookupField(this.jdbcTemplate, table);
                List<String> intoFields = new ArrayList<>();
                for (String fromField : fromFields) {
                    intoFields.add(BEFORE + "_" + fromField);
                }

                intoFields.add(table + "_id");
                fromFields.add(table + "_id");

                intoFields.add(historyPk);
                fromFields.add("uuid()");

                intoFields.add("op");
                fromFields.add("'DELETE'");

                intoFields.add("dt_when");
                fromFields.add(":dt_when");
                param.put("dt_when", new Date());

                intoFields.add(this.userPk);
                fromFields.add(":" + this.userPk);
                param.put(this.userPk, systems.get(this.userPk));

                intoFields.add("level");
                fromFields.add(":level");
                param.put("level", install.getLevel());

//                intoFields.add(Engine.SYSTEM_TRANSACTION_ID);
//                fromFields.add(":" + Engine.SYSTEM_TRANSACTION_ID);
//                param.put(Engine.SYSTEM_TRANSACTION_ID, systems.get(Engine.SYSTEM_TRANSACTION_ID));

                String query = "INSERT INTO " + historyTable + "(" + StringUtils.join(intoFields, ", ") + ") SELECT " + StringUtils.join(fromFields, ", ") + " FROM " + table + " WHERE " + StringUtils.join(where, " AND ");
                this.named.update(query, param);
            }
            DeleteQuery deleteQuery = new DeleteQuery(table);
            for (Map.Entry<String, Object> attr : wheres.entrySet()) {
                deleteQuery.addWhere(attr.getKey() + " = :" + attr.getKey(), Map.of(attr.getKey(), attr.getValue()));
            }
            this.named.update(deleteQuery.toSQL(), deleteQuery.toParam());
            return null;
        } else if (install.getLevel() == LEVEL_DRAFT) {
            if (systems.get(this.userPk) == null || "".equals(systems.get(this.userPk))) {
                throw new SQLException(this.userPk + " is required");
            }
//            if (systems.get(SYSTEM_TRANSACTION_ID) == null || "".equals(systems.get(SYSTEM_TRANSACTION_ID))) {
//                throw new SQLException(SYSTEM_TRANSACTION_ID + " is required");
//            }

            String taskId = this.jdbcTemplate.queryForObject("SELECT uuid() FROM DUAL", String.class);
            InsertQuery insertQuery = new InsertQuery(Jdbc.TASK);
            insertQuery.addValue("task_id", ":task_id", Map.of("task_id", taskId));
            insertQuery.addValue("`table`", ":table", Map.of("table", table));
            insertQuery.addValue("action", ":action", Map.of("action", "REVIEW"));
            insertQuery.addValue("op", ":op", Map.of("op", "DELETE"));
            insertQuery.addValue("draft_yes", ":draft_yes", Map.of("draft_yes", 0));
            insertQuery.addValue("draft_no", ":draft_no", Map.of("draft_no", 0));
            insertQuery.addValue("review_yes", ":review_yes", Map.of("review_yes", 0));
            insertQuery.addValue("review_no", ":review_no", Map.of("review_no", 0));
            insertQuery.addValue("dt_when", ":dt_when", Map.of("dt_when", new Date()));
            this.named.update(insertQuery.toSQL(), insertQuery.toParam());

            List<String> where = new ArrayList<>();
            Map<String, Object> param = new HashMap<>();
            for (Map.Entry<String, Object> w : wheres.entrySet()) {
                where.add(w.getKey() + " = :" + w.getKey());
                param.put(w.getKey(), w.getValue());
            }

            List<String> fromFields = Repository.lookupField(this.jdbcTemplate, table);
            List<String> intoFields = new ArrayList<>();
            for (String fromField : fromFields) {
                intoFields.add(BEFORE + "_" + fromField);
            }

            intoFields.add("yes");
            fromFields.add(":yes");
            param.put("yes", install.getDraftYes());

            intoFields.add("no");
            fromFields.add(":no");
            param.put("no", install.getDraftNo());

            intoFields.add("op");
            fromFields.add(":op");
            param.put("op", "DELETE");

            intoFields.add("dt_when");
            fromFields.add(":dt_when");
            param.put("dt_when", new Date());

//            intoFields.add(SYSTEM_TRANSACTION_ID);
//            fromFields.add(":" + SYSTEM_TRANSACTION_ID);
//            param.put(SYSTEM_TRANSACTION_ID, systems.get(SYSTEM_TRANSACTION_ID));

            intoFields.add(this.userPk);
            fromFields.add(":" + this.userPk);
            param.put(this.userPk, systems.get(this.userPk));

            intoFields.add(draftPk);
            fromFields.add(":" + draftPk);
            param.put(draftPk, taskId);

            intoFields.add(pk);
            fromFields.add(pk);

            String query = "INSERT INTO " + draftTable + "(" + StringUtils.join(intoFields, ", ") + ") SELECT " + StringUtils.join(fromFields, ", ") + " FROM " + table + " WHERE " + StringUtils.join(where, " AND ");
            this.named.update(query, param);

            return taskId;
        } else if (install.getLevel() == LEVEL_REVIEW) {
            if (systems.get(this.userPk) == null || "".equals(systems.get(this.userPk))) {
                throw new SQLException(this.userPk + " is required");
            }
//            if (systems.get(SYSTEM_TRANSACTION_ID) == null || "".equals(systems.get(SYSTEM_TRANSACTION_ID))) {
//                throw new SQLException(SYSTEM_TRANSACTION_ID + " is required");
//            }

            String taskId = this.jdbcTemplate.queryForObject("SELECT uuid() FROM DUAL", String.class);
            InsertQuery insertQuery = new InsertQuery(Jdbc.TASK);
            insertQuery.addValue("task_id", ":task_id", Map.of("task_id", taskId));
            insertQuery.addValue("`table`", ":table", Map.of("table", table));
            insertQuery.addValue("action", ":action", Map.of("action", "APPROVE"));
            insertQuery.addValue("op", ":op", Map.of("op", "DELETE"));
            insertQuery.addValue("draft_yes", ":draft_yes", Map.of("draft_yes", 0));
            insertQuery.addValue("draft_no", ":draft_no", Map.of("draft_no", 0));
            insertQuery.addValue("review_yes", ":review_yes", Map.of("review_yes", 0));
            insertQuery.addValue("review_no", ":review_no", Map.of("review_no", 0));
            insertQuery.addValue("dt_when", ":dt_when", Map.of("dt_when", new Date()));
            this.named.update(insertQuery.toSQL(), insertQuery.toParam());

            List<String> where = new ArrayList<>();
            Map<String, Object> param = new HashMap<>();
            for (Map.Entry<String, Object> w : wheres.entrySet()) {
                where.add(w.getKey() + " = :" + w.getKey());
                param.put(w.getKey(), w.getValue());
            }

            List<String> fromFields = Repository.lookupField(this.jdbcTemplate, table);
            List<String> intoFields = new ArrayList<>();
            for (String fromField : fromFields) {
                intoFields.add(BEFORE + "_" + fromField);
            }

            intoFields.add("yes");
            fromFields.add(":yes");
            param.put("yes", install.getDraftYes());

            intoFields.add("no");
            fromFields.add(":no");
            param.put("no", install.getDraftNo());

            intoFields.add("op");
            fromFields.add(":op");
            param.put("op", "DELETE");

            intoFields.add("dt_when");
            fromFields.add(":dt_when");
            param.put("dt_when", new Date());

//            intoFields.add(SYSTEM_TRANSACTION_ID);
//            fromFields.add(":" + SYSTEM_TRANSACTION_ID);
//            param.put(SYSTEM_TRANSACTION_ID, systems.get(SYSTEM_TRANSACTION_ID));

            intoFields.add(this.userPk);
            fromFields.add(":" + this.userPk);
            param.put(this.userPk, systems.get(this.userPk));

            intoFields.add(reviewPk);
            fromFields.add(":" + reviewPk);
            param.put(reviewPk, taskId);

            intoFields.add(pk);
            fromFields.add(pk);

            String query = "INSERT INTO " + reviewTable + "(" + StringUtils.join(intoFields, ", ") + ") SELECT " + StringUtils.join(fromFields, ", ") + " FROM " + table + " WHERE " + StringUtils.join(where, " AND ");
            this.named.update(query, param);
            return taskId;
        } else {
            return null;
        }
    }

    public String update(String table, Map<String, Object> values, Map<String, Object> wheres) throws SQLException {
        return update(table, values, wheres, new HashMap<>());
    }

    public String update(String table, Map<String, Object> values, Map<String, Object> wheres, Map<String, Object> systems) throws SQLException {
        SelectQuery selectQuery = new MySqlSelectQuery(INSTALL);
        selectQuery.addWhere("`table` = :table", Map.of("table", table));
        Install install = this.named.queryForObject(selectQuery.toSQL(), selectQuery.toParam(), Install.class);
        if (install == null) {
            throw new SQLException(table + " is not configured");
        }
        String pk = install.getPk();
        if (values.containsKey(pk) || values.get(pk) != null) {
            throw new SQLException(pk + " is not allow to update");
        }
        String historyTable = table + "_" + HISTORY_TABLE;
        String historyPk = historyTable + "_id";
        String draftTable = table + "_" + DRAFT_TABLE;
        String reviewTable = table + "_" + REVIEW_TABLE;
        String reviewPk = reviewTable + "_id";
        String draftPk = draftTable + "_id";
        if (install.getLevel() == LEVEL_NONE) {
            if (install.isHistory()) {
                if (systems.get(this.userPk) == null || "".equals(systems.get(this.userPk))) {
                    throw new SQLException(this.userPk + " is required");
                }
//                if (systems.get(SYSTEM_TRANSACTION_ID) == null || "".equals(systems.get(SYSTEM_TRANSACTION_ID))) {
//                    throw new SQLException(SYSTEM_TRANSACTION_ID + " is required");
//                }

                List<String> where = new ArrayList<>();
                Map<String, Object> param = new HashMap<>();
                for (Map.Entry<String, Object> w : wheres.entrySet()) {
                    where.add(w.getKey() + " = :" + w.getKey());
                    param.put(w.getKey(), w.getValue());
                }

                List<String> fromFields = Repository.lookupField(this.jdbcTemplate, table);
                List<String> intoFields = new ArrayList<>();
                for (String fromField : fromFields) {
                    intoFields.add(BEFORE + "_" + fromField);
                }

                for (Map.Entry<String, Object> attr : values.entrySet()) {
                    intoFields.add(AFTER + "_" + attr.getKey());
                    fromFields.add(":" + attr.getKey());
                    param.put(attr.getKey(), attr.getValue());
                }

                for (String f : Repository.lookupField(this.jdbcTemplate, table)) {
                    if (!intoFields.contains(AFTER + "_" + f)) {
                        intoFields.add(AFTER + "_" + f);
                        fromFields.add(f);
                    }
                }

                intoFields.add(table + "_id");
                fromFields.add(table + "_id");

                intoFields.add(historyPk);
                fromFields.add("uuid()");

                intoFields.add("op");
                fromFields.add("'UPDATE'");

                intoFields.add("dt_when");
                fromFields.add(":dt_when");
                param.put("dt_when", new Date());

                intoFields.add("level");
                fromFields.add(":level");
                param.put("level", install.getLevel());

                intoFields.add(this.userPk);
                fromFields.add(":" + this.userPk);
                param.put(this.userPk, systems.get(this.userPk));

//                intoFields.add(Engine.SYSTEM_TRANSACTION_ID);
//                fromFields.add(":" + Engine.SYSTEM_TRANSACTION_ID);
//                param.put(Engine.SYSTEM_TRANSACTION_ID, systems.get(Engine.SYSTEM_TRANSACTION_ID));

                String query = "INSERT INTO " + historyTable + "(" + StringUtils.join(intoFields, ", ") + ") SELECT " + StringUtils.join(fromFields, ", ") + " FROM " + table + " WHERE " + StringUtils.join(where, " AND ");
                this.named.update(query, param);
            }
            UpdateQuery updateQuery = new UpdateQuery(table);
            for (Map.Entry<String, Object> attr : values.entrySet()) {
                updateQuery.addSet(attr.getKey() + " = :" + attr.getKey(), Map.of(attr.getKey(), attr.getValue()));
            }
            for (Map.Entry<String, Object> attr : wheres.entrySet()) {
                updateQuery.addSet(attr.getKey() + " = :" + attr.getKey(), Map.of(attr.getKey(), attr.getValue()));
            }
            this.named.update(updateQuery.toSQL(), updateQuery.toParam());

            return null;
        } else if (install.getLevel() == LEVEL_DRAFT) {
            if (systems.get(this.userPk) == null || "".equals(systems.get(this.userPk))) {
                throw new SQLException(this.userPk + " is required");
            }
//            if (systems.get(SYSTEM_TRANSACTION_ID) == null || "".equals(systems.get(SYSTEM_TRANSACTION_ID))) {
//                throw new SQLException(SYSTEM_TRANSACTION_ID + " is required");
//            }

            String taskId = this.jdbcTemplate.queryForObject("SELECT uuid() FROM DUAL", String.class);
            InsertQuery insertQuery = new InsertQuery(Jdbc.TASK);
            insertQuery.addValue("task_id", ":task_id", Map.of("task_id", taskId));
            insertQuery.addValue("`table`", ":table", Map.of("table", table));
            insertQuery.addValue("action", ":action", Map.of("action", "REVIEW"));
            insertQuery.addValue("op", ":op", Map.of("op", "UPDATE"));
            insertQuery.addValue("draft_yes", ":draft_yes", Map.of("draft_yes", 0));
            insertQuery.addValue("draft_no", ":draft_no", Map.of("draft_no", 0));
            insertQuery.addValue("review_yes", ":review_yes", Map.of("review_yes", 0));
            insertQuery.addValue("review_no", ":review_no", Map.of("review_no", 0));
            insertQuery.addValue("dt_when", ":dt_when", Map.of("dt_when", new Date()));
            this.named.update(insertQuery.toSQL(), insertQuery.toParam());

            List<String> where = new ArrayList<>();
            Map<String, Object> param = new HashMap<>();
            for (Map.Entry<String, Object> w : wheres.entrySet()) {
                where.add(w.getKey() + " = :" + w.getKey());
                param.put(w.getKey(), w.getValue());
            }

            List<String> fromFields = Repository.lookupField(this.jdbcTemplate, table);
            List<String> intoFields = new ArrayList<>();

            for (String fromField : fromFields) {
                intoFields.add(BEFORE + "_" + fromField);
            }

            for (Map.Entry<String, Object> attr : values.entrySet()) {
                intoFields.add(AFTER + "_" + attr.getKey());
                fromFields.add(":" + attr.getKey());
                param.put(attr.getKey(), attr.getValue());
            }

            for (String f : Repository.lookupField(this.jdbcTemplate, table)) {
                if (!intoFields.contains(AFTER + "_" + f)) {
                    intoFields.add(AFTER + "_" + f);
                    fromFields.add(f);
                }
            }

            intoFields.add("yes");
            fromFields.add(":yes");
            param.put("yes", install.getDraftYes());

            intoFields.add("no");
            fromFields.add(":no");
            param.put("no", install.getDraftNo());

            intoFields.add("op");
            fromFields.add(":op");
            param.put("op", "UPDATE");

            intoFields.add("dt_when");
            fromFields.add(":dt_when");
            param.put("dt_when", new Date());

//            intoFields.add(SYSTEM_TRANSACTION_ID);
//            fromFields.add(":" + SYSTEM_TRANSACTION_ID);
//            param.put(SYSTEM_TRANSACTION_ID, systems.get(SYSTEM_TRANSACTION_ID));

            intoFields.add(this.userPk);
            fromFields.add(":" + this.userPk);
            param.put(this.userPk, systems.get(this.userPk));

            intoFields.add(draftPk);
            fromFields.add(":" + draftPk);
            param.put(draftPk, taskId);

            intoFields.add(pk);
            fromFields.add(pk);

            String query = "INSERT INTO " + draftTable + "(" + StringUtils.join(intoFields, ", ") + ") SELECT " + StringUtils.join(fromFields, ", ") + " FROM " + table + " WHERE " + StringUtils.join(where, " AND ");
            this.named.update(query, param);
            return taskId;
        } else if (install.getLevel() == LEVEL_REVIEW) {
            if (systems.get(this.userPk) == null || "".equals(systems.get(this.userPk))) {
                throw new SQLException(this.userPk + " is required");
            }
//            if (systems.get(SYSTEM_TRANSACTION_ID) == null || "".equals(systems.get(SYSTEM_TRANSACTION_ID))) {
//                throw new SQLException(SYSTEM_TRANSACTION_ID + " is required");
//            }

            String taskId = this.jdbcTemplate.queryForObject("SELECT uuid() FROM DUAL", String.class);
            InsertQuery insertQuery = new InsertQuery(Jdbc.TASK);
            insertQuery.addValue("task_id", ":task_id", Map.of("task_id", taskId));
            insertQuery.addValue("`table`", ":table", Map.of("table", table));
            insertQuery.addValue("action", ":action", Map.of("action", "APPROVE"));
            insertQuery.addValue("op", ":op", Map.of("op", "UPDATE"));
            insertQuery.addValue("draft_yes", ":draft_yes", Map.of("draft_yes", 0));
            insertQuery.addValue("draft_no", ":draft_no", Map.of("draft_no", 0));
            insertQuery.addValue("review_yes", ":review_yes", Map.of("review_yes", 0));
            insertQuery.addValue("review_no", ":review_no", Map.of("review_no", 0));
            insertQuery.addValue("dt_when", ":dt_when", Map.of("dt_when", new Date()));
            this.named.update(insertQuery.toSQL(), insertQuery.toParam());

            List<String> where = new ArrayList<>();
            Map<String, Object> param = new HashMap<>();
            for (Map.Entry<String, Object> w : wheres.entrySet()) {
                where.add(w.getKey() + " = :" + w.getKey());
                param.put(w.getKey(), w.getValue());
            }

            List<String> fromFields = Repository.lookupField(this.jdbcTemplate, table);
            List<String> intoFields = new ArrayList<>();

            for (String fromField : fromFields) {
                intoFields.add(BEFORE + "_" + fromField);
            }

            for (Map.Entry<String, Object> attr : values.entrySet()) {
                intoFields.add(AFTER + "_" + attr.getKey());
                fromFields.add(":" + attr.getKey());
                param.put(attr.getKey(), attr.getValue());
            }

            for (String f : Repository.lookupField(this.jdbcTemplate, table)) {
                if (!intoFields.contains(AFTER + "_" + f)) {
                    intoFields.add(AFTER + "_" + f);
                    fromFields.add(f);
                }
            }

            intoFields.add("yes");
            fromFields.add(":yes");
            param.put("yes", install.getDraftYes());

            intoFields.add("no");
            fromFields.add(":no");
            param.put("no", install.getDraftNo());

            intoFields.add("op");
            fromFields.add(":op");
            param.put("op", "UPDATE");

            intoFields.add("dt_when");
            fromFields.add(":dt_when");
            param.put("dt_when", new Date());

//            intoFields.add(SYSTEM_TRANSACTION_ID);
//            fromFields.add(":" + SYSTEM_TRANSACTION_ID);
//            param.put(SYSTEM_TRANSACTION_ID, systems.get(SYSTEM_TRANSACTION_ID));

            intoFields.add(this.userPk);
            fromFields.add(":" + this.userPk);
            param.put(this.userPk, systems.get(this.userPk));

            intoFields.add(reviewPk);
            fromFields.add(":" + reviewPk);
            param.put(reviewPk, taskId);

            intoFields.add(pk);
            fromFields.add(pk);

            String query = "INSERT INTO " + reviewTable + "(" + StringUtils.join(intoFields, ", ") + ") SELECT " + StringUtils.join(fromFields, ", ") + " FROM " + table + " WHERE " + StringUtils.join(where, " AND ");
            this.named.update(query, param);
            return taskId;
        } else {
            return null;
        }
    }

    public void draftReject(String taskId, String reason, Map<String, Object> systems) throws SQLException {
        SelectQuery selectQuery = new MySqlSelectQuery(TASK);
        selectQuery.addWhere("task_id = :task_id", Map.of("task_id", taskId));
        Task task = this.named.queryForObject(selectQuery.toSQL(), selectQuery.toParam(), Task.class);
        if (task == null) {
            throw new SQLException("task " + taskId + " is not found");
        }
        selectQuery = new MySqlSelectQuery(INSTALL);
        selectQuery.addWhere("`table` = :table", Map.of("table", task.getTable()));
        Install install = this.named.queryForObject(selectQuery.toSQL(), selectQuery.toParam(), Install.class);

        if (systems.get(this.userPk) == null || "".equals(systems.get(this.userPk))) {
            throw new SQLException(this.userPk + " is required");
        }
        String table = install.getTable();
        String draftTable = table + "_" + DRAFT_TABLE;
        String draftPk = draftTable + "_id";
        String draftSummaryTable = draftTable + "_" + SUMMARY_TABLE;
        String draftSummaryPk = draftSummaryTable + "_id";
        selectQuery = new MySqlSelectQuery(draftTable);
        selectQuery.addWhere(draftPk + " = :" + draftPk, Map.of(draftPk, task.getTaskId()));
        Map<String, Object> record = null;
        try {
            record = this.named.queryForMap(selectQuery.toSQL(), selectQuery.toParam());
        } catch (EmptyResultDataAccessException e) {
        }
        if (record == null) {
            throw new SQLException("task " + taskId + " is not found");
        }
        int draftNo = (int) record.get("no");

        InsertQuery insertQuery = new InsertQuery(draftSummaryTable);
        insertQuery.addValue(draftSummaryPk, "uuid()");
        insertQuery.addValue(draftPk, ":" + draftPk, Map.of(draftPk, task.getTaskId()));
        insertQuery.addValue(this.userPk, ":" + this.userPk, Map.of(this.userPk, systems.get(this.userPk)));
        insertQuery.addValue("reason", ":reason", Map.of("reason", reason));
        insertQuery.addValue("action", ":action", Map.of("action", "NO"));
        this.named.update(insertQuery.toSQL(), insertQuery.toParam());

        selectQuery = new MySqlSelectQuery(draftSummaryTable);
        selectQuery.addField("COUNT(*)");
        selectQuery.addWhere("action = :action", Map.of("action", "NO"));
        selectQuery.addWhere(draftPk + " = :" + draftPk, Map.of(draftPk, task.getTaskId()));
        Integer runDraftNo = this.named.queryForObject(selectQuery.toSQL(), selectQuery.toParam(), int.class);

        UpdateQuery updateQuery = new UpdateQuery(TASK);
        updateQuery.addSet("draft_no = :draft_no", Map.of("draft_no", runDraftNo));
        updateQuery.addWhere("task_id = :task_id", Map.of("task_id", taskId));
        this.named.update(updateQuery.toSQL(), updateQuery.toParam());

        if (runDraftNo != null && runDraftNo >= draftNo) {
            taskClean(draftTable, draftPk, draftSummaryTable, task.getTaskId());
        }
    }

    private void taskClean(String table, String pk, String summaryTable, String id) {
        DeleteQuery deleteQuery = new DeleteQuery(table);
        deleteQuery.addWhere(pk + " = :" + pk, Map.of(pk, id));
        this.named.update(deleteQuery.toSQL(), deleteQuery.toParam());
        deleteQuery = new DeleteQuery(summaryTable);
        deleteQuery.addWhere(pk + " = :" + pk, Map.of(pk, id));
        this.named.update(deleteQuery.toSQL(), deleteQuery.toParam());
        deleteQuery = new DeleteQuery(TASK);
        deleteQuery.addWhere("task_id = :task_id", Map.of("task_id", id));
        this.named.update(deleteQuery.toSQL(), deleteQuery.toParam());
    }

    public void draftApprove(String taskId, Map<String, Object> systems) throws SQLException {
        SelectQuery selectQuery = new MySqlSelectQuery(TASK);
        selectQuery.addWhere("task_id = :task_id", Map.of("task_id", taskId));
        Task task = this.named.queryForObject(selectQuery.toSQL(), selectQuery.toParam(), Task.class);
        if (task == null) {
            throw new SQLException("task " + taskId + " is not found");
        }

        if (systems.get(this.userPk) == null || "".equals(systems.get(this.userPk))) {
            throw new SQLException(this.userPk + " is required");
        }
        String table = task.getTable();
        String draftTable = table + "_" + DRAFT_TABLE;
        String reviewTable = table + "_" + REVIEW_TABLE;
        String reviewPk = reviewTable + "_id";
        String draftPk = draftTable + "_id";
        String draftSummaryTable = draftTable + "_" + SUMMARY_TABLE;
        String draftSummaryPk = draftSummaryTable + "_id";
        selectQuery = new MySqlSelectQuery(draftTable);
        selectQuery.addWhere(draftPk + " = :" + draftPk, Map.of(draftPk, task.getTaskId()));
        Map<String, Object> record = null;
        try {
            record = this.named.queryForMap(selectQuery.toSQL(), selectQuery.toParam());
        } catch (EmptyResultDataAccessException e) {
        }
        if (record == null) {
            throw new SQLException("task " + taskId + " is not found");
        }
        int draftYes = (int) record.get("yes");

        InsertQuery insertQuery = new InsertQuery(draftSummaryTable);
        insertQuery.addValue(draftSummaryPk, "uuid()");
        insertQuery.addValue(draftPk, ":" + draftPk, Map.of(draftPk, task.getTaskId()));
        insertQuery.addValue(this.userPk, ":" + this.userPk, Map.of(this.userPk, systems.get(this.userPk)));
        insertQuery.addValue("action", ":action", Map.of("action", "YES"));
        this.named.update(insertQuery.toSQL(), insertQuery.toParam());

        selectQuery = new MySqlSelectQuery(draftSummaryTable);
        selectQuery.addField("COUNT(*)");
        selectQuery.addWhere("action = :action", Map.of("action", "YES"));
        selectQuery.addWhere(draftPk + " = :" + draftPk, Map.of(draftPk, task.getTaskId()));
        Integer runDraftYes = this.named.queryForObject(selectQuery.toSQL(), selectQuery.toParam(), int.class);

        UpdateQuery updateQuery = new UpdateQuery(TASK);
        updateQuery.addSet("draft_yes = :draft_yes", Map.of("draft_yes", runDraftYes));
        updateQuery.addWhere("task_id = :task_id", Map.of("task_id", taskId));
        this.named.update(updateQuery.toSQL(), updateQuery.toParam());

        if (runDraftYes != null && runDraftYes >= draftYes) {
            List<String> where = new ArrayList<>();
            Map<String, Object> params = new HashMap<>();
            where.add(draftPk + " = :" + draftPk);
            params.put(draftPk, task.getTaskId());
            List<String> fromFields = Repository.lookupField(this.jdbcTemplate, draftTable);
            List<String> intoFields = new ArrayList<>(fromFields);

            intoFields.add(reviewPk);
            fromFields.add(draftPk);

            String query = "INSERT INTO " + reviewTable + "(" + StringUtils.join(intoFields, ", ") + ") SELECT " + StringUtils.join(fromFields, ", ") + " FROM " + draftTable + " WHERE " + StringUtils.join(where, " AND ");
            this.named.update(query, params);

            updateQuery = new UpdateQuery(TASK);
            updateQuery.addSet("action = :action", Map.of("action", "APPROVE"));
            updateQuery.addWhere("task_id = :task_id", Map.of("task_id", taskId));
            this.named.update(updateQuery.toSQL(), updateQuery.toParam());
        }
    }

    public void reviewReject(String taskId, String reason, Map<String, Object> systems) throws SQLException {
        SelectQuery selectQuery = new MySqlSelectQuery(TASK);
        selectQuery.addWhere("task_id = :task_id", Map.of("task_id", taskId));
        Task task = this.named.queryForObject(selectQuery.toSQL(), selectQuery.toParam(), Task.class);
        if (task == null) {
            throw new SQLException("task " + taskId + " is not found");
        }

        if (systems.get(this.userPk) == null || "".equals(systems.get(this.userPk))) {
            throw new SQLException(this.userPk + " is required");
        }

        String table = task.getTable();
        String draftTable = table + "_" + DRAFT_TABLE;
        String draftSummaryTable = draftTable + "_" + SUMMARY_TABLE;
        String draftPk = draftTable + "_id";
        String reviewTable = table + "_" + REVIEW_TABLE;
        String reviewPk = reviewTable + "_id";
        String reviewSummaryTable = reviewTable + "_" + SUMMARY_TABLE;
        String reviewSummaryPk = reviewSummaryTable + "_id";
        selectQuery = new MySqlSelectQuery(reviewTable);
        selectQuery.addWhere(reviewPk + " = :" + reviewPk, Map.of(reviewPk, task.getTaskId()));
        Map<String, Object> record = null;
        try {
            record = this.named.queryForMap(selectQuery.toSQL(), selectQuery.toParam());
        } catch (EmptyResultDataAccessException e) {
        }
        if (record == null) {
            throw new SQLException("task " + taskId + " is not found");
        }
        int reviewNo = (int) record.get("no");

        InsertQuery insertQuery = new InsertQuery(reviewSummaryTable);
        insertQuery.addValue(reviewSummaryPk, "uuid()");
        insertQuery.addValue(reviewPk, ":" + reviewPk, Map.of(reviewPk, task.getTaskId()));
        insertQuery.addValue(this.userPk, ":" + this.userPk, Map.of(this.userPk, systems.get(this.userPk)));
        insertQuery.addValue("reason", ":reason", Map.of("reason", reason));
        insertQuery.addValue("action", ":action", Map.of("action", "NO"));
        this.named.update(insertQuery.toSQL(), insertQuery.toParam());

        selectQuery = new MySqlSelectQuery(reviewSummaryTable);
        selectQuery.addField("COUNT(*)");
        selectQuery.addWhere("action = :action", Map.of("action", "NO"));
        selectQuery.addWhere(reviewPk + " = :" + reviewPk, Map.of(reviewPk, task.getTaskId()));
        Integer runReviewNo = this.named.queryForObject(selectQuery.toSQL(), selectQuery.toParam(), int.class);

        UpdateQuery updateQuery = new UpdateQuery(TASK);
        updateQuery.addSet("review_no = :review_no", Map.of("review_no", runReviewNo));
        updateQuery.addWhere("task_id = :task_id", Map.of("task_id", taskId));
        this.named.update(updateQuery.toSQL(), updateQuery.toParam());

        if (runReviewNo != null && runReviewNo >= reviewNo) {

            taskClean(reviewTable, reviewPk, reviewSummaryTable, task.getTaskId());

            DeleteQuery deleteQuery = new DeleteQuery(draftTable);
            deleteQuery.addWhere(draftPk + " = :" + draftPk, Map.of(draftPk, task.getTaskId()));
            this.named.update(deleteQuery.toSQL(), deleteQuery.toParam());
            deleteQuery = new DeleteQuery(draftSummaryTable);
            deleteQuery.addWhere(draftPk + " = :" + draftPk, Map.of(draftPk, task.getTaskId()));
            this.named.update(deleteQuery.toSQL(), deleteQuery.toParam());
        }
    }

    public void reviewApprove(String taskId, Map<String, Object> systems) throws SQLException {
        SelectQuery selectQuery = new MySqlSelectQuery(TASK);
        selectQuery.addWhere("task_id = :task_id", Map.of("task_id", taskId));
        Task task = this.named.queryForObject(selectQuery.toSQL(), selectQuery.toParam(), Task.class);
        if (task == null) {
            throw new SQLException("task " + taskId + " is not found");
        }

        selectQuery = new MySqlSelectQuery(INSTALL);
        selectQuery.addWhere("`table` = :table", Map.of("table", task.getTable()));
        Install install = this.named.queryForObject(selectQuery.toSQL(), selectQuery.toParam(), Install.class);

        if (systems.get(this.userPk) == null || "".equals(systems.get(this.userPk))) {
            throw new SQLException(this.userPk + " is required");
        }

        String table = task.getTable();
        String draftTable = table + "_" + DRAFT_TABLE;
        String draftSummaryTable = draftTable + "_" + SUMMARY_TABLE;
        String draftPk = draftTable + "_id";
        String historyTable = table + "_" + HISTORY_TABLE;
        String historySummaryTable = historyTable + "_" + SUMMARY_TABLE;
        String historySummaryPk = historySummaryTable + "_id";
        String historyPk = historyTable + "_id";
        String pk = install.getPk();
        String reviewTable = table + "_" + REVIEW_TABLE;
        String reviewPk = reviewTable + "_id";
        String reviewSummaryTable = reviewTable + "_" + SUMMARY_TABLE;
        String reviewSummaryPk = reviewSummaryTable + "_id";
        selectQuery = new MySqlSelectQuery(reviewTable);
        selectQuery.addWhere(reviewPk + " = :" + reviewPk, Map.of(reviewPk, task.getTaskId()));
        Map<String, Object> record = null;
        try {
            record = this.named.queryForMap(selectQuery.toSQL(), selectQuery.toParam());
        } catch (EmptyResultDataAccessException e) {
        }
        if (record == null) {
            throw new SQLException("task " + taskId + " is not found");
        }
        int reviewYes = (int) record.get("yes");
        String op = (String) record.get("op");

        InsertQuery insertQuery = new InsertQuery(reviewSummaryTable);
        insertQuery.addValue(reviewSummaryPk, "uuid()");
        insertQuery.addValue(reviewPk, ":" + reviewPk, Map.of(reviewPk, task.getTaskId()));
        insertQuery.addValue(this.userPk, ":" + this.userPk, Map.of(this.userPk, systems.get(this.userPk)));
        insertQuery.addValue("action", ":action", Map.of("action", "YES"));
        this.named.update(insertQuery.toSQL(), insertQuery.toParam());

        selectQuery = new MySqlSelectQuery(reviewSummaryTable);
        selectQuery.addField("COUNT(*)");
        selectQuery.addWhere("action = :action", Map.of("action", "YES"));
        selectQuery.addWhere(reviewPk + " = :" + reviewPk, Map.of(reviewPk, task.getTaskId()));
        Integer runReviewYes = this.named.queryForObject(selectQuery.toSQL(), selectQuery.toParam(), int.class);

        UpdateQuery updateQuery = new UpdateQuery(TASK);
        updateQuery.addSet("review_yes = :review_yes", Map.of("review_yes", runReviewYes));
        updateQuery.addWhere("task_id = :task_id", Map.of("task_id", taskId));
        this.named.update(updateQuery.toSQL(), updateQuery.toParam());

        if (runReviewYes != null && runReviewYes >= reviewYes) {
            if (install.isHistory()) {

                String historyId = this.jdbcTemplate.queryForObject("SELECT uuid() FROM DUAL", String.class);

                List<String> where = new ArrayList<>();
                Map<String, Object> param = new HashMap<>();
                where.add(reviewPk + " = :" + reviewPk);
                param.put(reviewPk, taskId);

                List<String> fromFields = Repository.lookupField(this.jdbcTemplate, historyTable);
                fromFields.remove("level");
                List<String> intoFields = new ArrayList<>(fromFields);

                intoFields.add("level");
                fromFields.add(":level");
                param.put("level", install.getLevel());

                intoFields.add(historyPk);
                fromFields.add(":" + historyPk);
                param.put(historyPk, historyId);

                String query = "INSERT INTO " + historyTable + "(" + StringUtils.join(intoFields, ", ") + ") SELECT " + StringUtils.join(fromFields, ", ") + " FROM " + reviewTable + " WHERE " + StringUtils.join(where, " AND ");
                this.named.update(query, param);

                where = new ArrayList<>();
                param = new HashMap<>();

                where.add(draftPk + " = :" + draftPk);
                param.put(draftPk, taskId);

                fromFields = Repository.lookupField(this.jdbcTemplate, draftSummaryTable);
                fromFields.remove(draftPk);
                intoFields = new ArrayList<>(fromFields);

                intoFields.add("level");
                fromFields.add(":level");
                param.put("level", "REVIEW");

                intoFields.add(historySummaryPk);
                fromFields.add("uuid()");

                intoFields.add(historyPk);
                fromFields.add(":" + historyPk);
                param.put(historyPk, historyId);

                query = "INSERT INTO " + historySummaryTable + "(" + StringUtils.join(intoFields, ", ") + ") SELECT " + StringUtils.join(fromFields, ", ") + " FROM " + draftSummaryTable + " WHERE " + StringUtils.join(where, " AND ");
                this.named.update(query, param);

                where = new ArrayList<>();
                param = new HashMap<>();

                where.add(reviewPk + " = :" + reviewPk);
                param.put(reviewPk, taskId);

                fromFields = Repository.lookupField(this.jdbcTemplate, reviewSummaryTable);
                fromFields.remove(reviewPk);
                intoFields = new ArrayList<>(fromFields);

                intoFields.add("level");
                fromFields.add(":level");
                param.put("level", "APPROVE");

                intoFields.add(historySummaryPk);
                fromFields.add("uuid()");

                intoFields.add(historyPk);
                fromFields.add(":" + historyPk);
                param.put(historyPk, historyId);

                query = "INSERT INTO " + historySummaryTable + "(" + StringUtils.join(intoFields, ", ") + ") SELECT " + StringUtils.join(fromFields, ", ") + " FROM " + reviewSummaryTable + " WHERE " + StringUtils.join(where, " AND ");
                this.named.update(query, param);
            }

            if ("INSERT".equals(op)) {
                List<String> where = new ArrayList<>();
                Map<String, Object> params = new HashMap<>();
                where.add(reviewPk + " = :" + reviewPk);
                params.put(reviewPk, task.getTaskId());
                List<String> intoFields = Repository.lookupField(this.jdbcTemplate, table);
                List<String> fromFields = new ArrayList<>();
                for (String intoField : intoFields) {
                    fromFields.add(AFTER + "_" + intoField);
                }

                intoFields.add(pk);
                fromFields.add(pk);

                String query = "INSERT INTO " + table + "(" + StringUtils.join(intoFields, ", ") + ") SELECT " + StringUtils.join(fromFields, ", ") + " FROM " + reviewTable + " WHERE " + StringUtils.join(where, " AND ");
                this.named.update(query, params);
            } else if ("UPDATE".equals(op)) {
                List<String> intoFields = Repository.lookupField(this.jdbcTemplate, table);

                selectQuery = new MySqlSelectQuery(reviewTable);
                selectQuery.addWhere(reviewPk + " = :" + reviewPk, Map.of(reviewPk, task.getTaskId()));
                Map<String, Object> from = this.named.queryForMap(selectQuery.toSQL(), selectQuery.toParam());

                updateQuery = new UpdateQuery(table);
                updateQuery.addWhere(pk + " = :" + pk, Map.of(pk, record.get(pk)));
                for (String intoField : intoFields) {
                    updateQuery.addSet(intoField + " = :" + intoField, Map.of(intoField, from.get(AFTER + "_" + intoField)));
                }
                this.named.update(updateQuery.toSQL(), updateQuery.toParam());
            } else if ("DELETE".equals(op)) {
                DeleteQuery deleteQuery = new DeleteQuery(table);
                deleteQuery.addWhere(pk + " = :" + pk, Map.of(pk, record.get(pk)));
                this.named.update(deleteQuery.toSQL(), deleteQuery.toParam());
            }

            DeleteQuery deleteQuery = new DeleteQuery(draftTable);
            deleteQuery.addWhere(draftPk + " = :" + draftPk, Map.of(draftPk, task.getTaskId()));
            this.named.update(deleteQuery.toSQL(), deleteQuery.toParam());

            deleteQuery = new DeleteQuery(draftSummaryTable);
            deleteQuery.addWhere(draftPk + " = :" + draftPk, Map.of(draftPk, task.getTaskId()));
            this.named.update(deleteQuery.toSQL(), deleteQuery.toParam());

            deleteQuery = new DeleteQuery(reviewTable);
            deleteQuery.addWhere(reviewPk + " = :" + reviewPk, Map.of(reviewPk, task.getTaskId()));
            this.named.update(deleteQuery.toSQL(), deleteQuery.toParam());

            deleteQuery = new DeleteQuery(reviewSummaryTable);
            deleteQuery.addWhere(reviewPk + " = :" + reviewPk, Map.of(reviewPk, task.getTaskId()));
            this.named.update(deleteQuery.toSQL(), deleteQuery.toParam());

            deleteQuery = new DeleteQuery(TASK);
            deleteQuery.addWhere("task_id = :task_id", Map.of("task_id", task.getTaskId()));
            this.named.update(deleteQuery.toSQL(), deleteQuery.toParam());
        }
    }

}