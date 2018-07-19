package com.urise.webapp.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void doQuery(String statement) {
        doQuery(statement, PreparedStatement::execute);
    }

    public <T> T doQuery(String statement, FunctionExecute<T> functionExecute) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(statement)
        ) {
            return functionExecute.execute(preparedStatement);
        } catch (SQLException e) {
            throw ExceptionUtil.convertException(e);
        }
    }

    @FunctionalInterface
    public interface FunctionExecute<T> {
        T execute(PreparedStatement preparedStatement) throws SQLException;
    }
}
