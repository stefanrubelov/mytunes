package com.easv.gringofy.dal.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class QueryBuilder {
    private String selectClause = "*";
    private String fromClause = "";
    private List<String> whereClauses = new ArrayList<>();
    private List<Object> parameters = new ArrayList<>();
    private List<String> joinClauses = new ArrayList<>();
    private String orderByClause = "";
    private Integer top = null;
    private List<String> unionClauses = new ArrayList<>();
    private List<String> setClauses = new ArrayList<>();
    private boolean delete = false;
    private boolean update = false;
    private boolean get = false;
    private boolean insert = false;
    private ArrayList<String> insertColumnsPlaceholders;
    private final DBConnection dbConnection;

    public QueryBuilder() {
        this.dbConnection = new DBConnection();
        insertColumnsPlaceholders = new ArrayList<>();
    }

    public QueryBuilder select(String... columns) {
        if (columns.equals("*")) {
            this.selectClause = "*";
        } else {
            this.selectClause = String.join(", ", columns);
        }
        return this;
    }

    public QueryBuilder from(String table) {
        this.fromClause = table;
        return this;
    }

    public QueryBuilder table(String table) {
        this.fromClause = table;
        return this;
    }

    public QueryBuilder where(String condition, Object... params) {
        whereClauses.add(condition);
        Collections.addAll(parameters, params);
        return this;
    }

    public QueryBuilder join(String table, String condition, String type) {
        this.joinClauses.add(type.toUpperCase() + " JOIN " + table + " ON " + condition);
        return this;
    }

    public QueryBuilder innerJoin(String table, String condition) {
        return join(table, condition, "INNER");
    }

    public QueryBuilder leftJoin(String table, String condition) {
        return join(table, condition, "LEFT");
    }

    public QueryBuilder rightJoin(String table, String condition) {
        return join(table, condition, "RIGHT");
    }

    public QueryBuilder union(QueryBuilder otherQuery) {
        this.unionClauses.add(otherQuery.build());
        return this;
    }

    public QueryBuilder orderBy(String column, String direction) {
        this.orderByClause = column + " " + direction.toUpperCase();
        return this;
    }

    public QueryBuilder top(int top) {
        this.top = top;
        return this;
    }

    public QueryBuilder set(String column, Object value) {
        String valueString;

        if (value instanceof String) {
            valueString = "'" + value + "'";
        } else if (value instanceof LocalDateTime) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            valueString = "'" + ((LocalDateTime) value).format(formatter) + "'";
        } else {
            valueString = value.toString();
        }

        setClauses.add(column + " = " + valueString);
        return this;
    }

    public QueryBuilder when(boolean condition, Consumer<QueryBuilder> callback) {
        if (condition) {
            callback.accept(this);
        }
        return this;
    }

    public QueryBuilder insert(String column, Object value) {
        return insert(new String[]{column}, new Object[]{value});
    }

    public QueryBuilder insert(String[] columns, Object[] values) {
        if (columns.length != values.length) {
            throw new IllegalArgumentException("Columns and values must have the same length.");
        }
        for (int i = 0; i < columns.length; i++) {
            insertColumnsPlaceholders.add(columns[i]);
        }

        for (Object value : values) {
            parameters.add(value);
        }

        return this;
    }

    public String build() {
        if (fromClause.isEmpty()) {
            throw new IllegalStateException("FROM clause is required.");
        }

        StringBuilder query = new StringBuilder();

        if (delete) {
            query.append("DELETE ");

            if (top != null) {
                query.append("TOP ").append(top).append(" ");
            }
            query.append(selectClause).append(" FROM ").append(fromClause);

        } else if (update) {
            query.append("UPDATE ").append(fromClause).append(" SET ").append(String.join(", ", setClauses));
        } else if (get) {
            query.append("SELECT ");

            if (top != null) {
                query.append("TOP ").append(top).append(" ");
            }

            query.append(selectClause).append(" FROM ").append(fromClause);
        } else if (insert) {
            query.append("INSERT INTO ").append(fromClause).append(" (").append(String.join(", ", insertColumnsPlaceholders)).append(") VALUES (")
                    .append(String.join(", ", Collections.nCopies(parameters.size(), "?")))
                    .append(")");

        }

        if (!joinClauses.isEmpty()) {
            query.append(" ").append(String.join(" ", joinClauses));
        }

        if (!whereClauses.isEmpty()) {
            query.append(" WHERE ").append(String.join(" AND ", whereClauses));
        }

        if (!orderByClause.isEmpty()) {
            query.append(" ORDER BY ").append(orderByClause);
        }

        if (!unionClauses.isEmpty()) {
            query.append(" ").append(String.join(" UNION ", unionClauses));
        }

        return query.toString();
    }

    public boolean save() {
        this.insert = true;
        String sql = build();
        System.out.println(parameters);
        System.out.println(sql);
        try {
            Connection connection = dbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("SQL INSERT Execution Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            resetFields();
        }
    }

    public ResultSet get() {
        this.get = true;
        String sql = build();

        try {
            Connection connection = dbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < this.parameters.size(); i++) {
                preparedStatement.setObject(i + 1, this.parameters.get(i));
            }
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            System.err.println("SQL SELECT Execution Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public boolean update() {
        this.update = true;

        String sql = build();

        try {
            Connection connection = dbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }
            return preparedStatement.execute();
        } catch (SQLException e) {
            System.err.println("SQL UPDATE Execution Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete() {
        this.delete = true;

        String sql = build();

        try {
            Connection connection = dbConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }
            return preparedStatement.execute();
        } catch (SQLException e) {
            System.err.println("SQL DELETE Execution Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void resetFields() {
        selectClause = "*";
        fromClause = "";
        whereClauses.clear();
        parameters.clear();
        joinClauses.clear();
        orderByClause = "";
        top = null;
        unionClauses.clear();
    }
}