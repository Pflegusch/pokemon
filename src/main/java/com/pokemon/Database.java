package com.pokemon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private String DB_URL = "jdbc:mysql://172.17.0.1:3306/";
    private String DB = "Pokemon";
    private String USER = "eich";
    private String PASS = "eich12344";

    private String LAST_SQL_COMMAND;

    public Connection connection;

    Database() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(String.format("%s%s", DB_URL, DB), USER, PASS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Database(String DB_URL, String DB, String USER, String PASS) {
        this.DB_URL = DB_URL;
        this.DB = DB;
        this.USER = USER;
        this.PASS = PASS;

        try {
            this.connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initialization() {
        String sql = "CREATE TABLE Trainer" +
                "(id INTEGER not null, " +
                "name VARCHAR(255) not null, " +
                "balance INTEGER, " +
                "PRIMARY KEY ( id ))";
        try {
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(sql);
            this.LAST_SQL_COMMAND = sql;
        } catch (SQLException e) {
            e.printStackTrace();
        }
               
    }

    public void get_table(String table) {
        try {
            String sql = String.format("SELECT * from %s", table);
            Statement statement = this.connection.createStatement();
            statement.executeQuery(sql);
            this.LAST_SQL_COMMAND = sql;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
