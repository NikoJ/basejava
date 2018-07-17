package com.urise.webapp.sql;

import com.urise.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory1) {
        this.connectionFactory = connectionFactory1;
    }

    public void doQuery(String statement, FunctionExecute functionExecute) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(statement)
        ) {
            functionExecute.execute(preparedStatement);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public <T> T doQuery(boolean isReturn, String statement, FunctionExecuteWithReturn<T> functionExecuteWithReturn) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(statement)
        ) {
            return functionExecuteWithReturn.execute(preparedStatement);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @FunctionalInterface
    public interface FunctionExecute {
        void execute(PreparedStatement preparedStatement) throws SQLException;
    }

    @FunctionalInterface
    public interface FunctionExecuteWithReturn<T> {
        T execute(PreparedStatement preparedStatement) throws SQLException;
    }
}
