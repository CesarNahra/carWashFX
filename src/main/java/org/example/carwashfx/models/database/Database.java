package org.example.carwashfx.models.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface Database {

    public Connection connect();
    public void disconnect(Connection connection);
    public void commit(Connection connection);
    public void rollback(Connection connection);
}
