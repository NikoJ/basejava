package com.urise.webapp.sql;

import com.urise.webapp.exception.StorageException;

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

    public <T> T transactionalExecute(SqlTransaction<T> executor) {
        try (Connection connection = connectionFactory.getConnection()) {
            try {
                connection.setAutoCommit(false);
                T res = executor.execute(connection);
                connection.commit();
                return res;
            } catch (SQLException e) {
                connection.rollback();
                throw ExceptionUtil.convertException(e);

            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @FunctionalInterface
    public interface FunctionExecute<T> {
        T execute(PreparedStatement preparedStatement) throws SQLException;
    }
}
