package org.example.carwashfx.models.database;

public class DatabaseFactory {
    public static Database getDatabase(String dbName){
        if(dbName.equals("postgresql")){
            return new DatabasePostgreSQL();
        }else if(dbName.equals("mysql")){
            return new DatabaseMySQL();
        }
        return null;
    }
}