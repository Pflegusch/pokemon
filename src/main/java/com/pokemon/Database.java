package com.pokemon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.sql.ResultSet;

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
        String sql;
        Statement statement;
        if (!table_exist("Trainers")) {
            sql = "CREATE TABLE Trainers" +
                "(id INTEGER not null, " +
                "name VARCHAR(255) not null, " +
                "balance INTEGER, " +
                "PRIMARY KEY ( id ))";
            try {
                statement = this.connection.createStatement();
                statement.executeUpdate(sql);
                this.LAST_SQL_COMMAND = sql;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (!table_exist("Pokedex")) {
            sql = "CREATE TABLE Pokedex" +
                "(id INTEGER not null, " +
                "name VARCHAR(255) not null, " +
                "type VARCHAR(255) not null, " +
                "weakness VARCHAR(255) not null, " +
                "PRIMARY KEY ( id ))";

            try {
                statement = this.connection.createStatement();
                statement.executeUpdate(sql);
                this.LAST_SQL_COMMAND = sql;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (!table_exist("Fights")) {
            sql = "CREATE TABLE Fights" +
                "(id INTEGER not null, " +
                "running BOOLEAN, " +
                "rounds INTEGER DEFAULT null, " +
                "winner INTEGER DEFAULT null, " +
                "loser INTEGER DEFAULT null, " +
                "price INTEGER DEFAULT null, " +
                "trainers VARCHAR(255) not null, " + 
                "PRIMARY KEY ( id ))";

            try {
                statement = this.connection.createStatement();
                statement.executeUpdate(sql);
                this.LAST_SQL_COMMAND = sql;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (!table_exist("Attacks")) {
            sql = "CREATE TABLE Attacks" +
                "(id INTEGER not null, " +
                "name VARCHAR(255) not null, " +
                "type VARCHAR(255) not null, " +
                "damage INTEGER DEFAULT null, " +
                "ap INTEGER not null, " + 
                "accuracy INTEGER not null, " +
                "PRIMARY KEY ( id ))";

            try {
                statement = this.connection.createStatement();
                statement.executeUpdate(sql);
                this.LAST_SQL_COMMAND = sql;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void get_table(String table) {
        try {
            String sql = String.format("SELECT * from %s", table);
            Statement statement = this.connection.createStatement();
            ResultSet set = statement.executeQuery(sql);
            this.LAST_SQL_COMMAND = sql;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String _LAST_SQL_COMMAND() {
        return this.LAST_SQL_COMMAND;
    }

    public boolean table_exist(String tableName) {
        boolean tExists = false;
        try (ResultSet rs = this.connection.getMetaData().getTables(null, null, tableName, null)) {
            while (rs.next()) { 
                String tName = rs.getString("TABLE_NAME");
                if (tName != null && tName.equals(tableName)) {
                    tExists = true;
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tExists;
    }

    public void insert_trainer(Trainer trainer) {
        try {
            String sql = String.format("INSERT INTO Trainers VALUES (%s, '%s', %s)",
                    trainer.id, trainer.name, trainer.balance);
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(sql);
            this.LAST_SQL_COMMAND = sql;
        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } 
    }

    public void insert_pokemon(Pokemon pokemon) {
        int id = pokemon.id;
        String name, types, weaknesses;
        name = pokemon.name;
        types = "";
        weaknesses = "";
        for (Type type : pokemon.type) {
            types += type.toString() + ",";
        }
        types = types.substring(0, types.length() - 1);

        for (Type type : pokemon.weaknesses) {
            weaknesses += type.toString() + ",";
        }
        weaknesses = weaknesses.substring(0, weaknesses.length() - 1);

        try {
            String sql = String.format("INSERT INTO Pokedex VALUES (%s, '%s', '%s', '%s')",
                    id, name, types, weaknesses);
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(sql);
            this.LAST_SQL_COMMAND = sql;
        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert_fight(Fight fight) {
        int id = fight.id;
        boolean running = fight.running;
        int rounds = fight.rounds;    
        Trainer winner = fight.winner;
        int winner_id;
        Trainer loser = fight.loser;
        int price = fight.price;
        int loser_id;
        String trainers = "";

        if (winner != null) {
            winner_id = winner.id;
        } else {
            winner_id = -1;
        }

        if (loser != null) {
            loser_id = loser.id;
        } else {
            loser_id = -1;
        }

        for (Trainer trainer : fight.trainers) {
            trainers += trainer.name + ",";
        }
        trainers = trainers.substring(0, trainers.length() - 1);

        System.out.println("DEBUG ID: " + id);

        try {
            String sql = String.format("INSERT INTO Fights VALUES (%s, %s, %s, %s, %s, %s, '%s')",
                    id, running, rounds, winner_id, loser_id, price, trainers);
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(sql);
            this.LAST_SQL_COMMAND = sql;
        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update_trainers(Fight fight) {
        try {
            String sql_winner = String.format("UPDATE Trainers SET balance = balance + %s WHERE id = %s",
                    fight.price, fight.winner.id);
            String sql_loser= String.format("UPDATE Trainers SET balance = balance - %s WHERE id = %s",
                    fight.price, fight.loser.id);
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(sql_winner);
            this.LAST_SQL_COMMAND = sql_winner;

            statement.executeUpdate(sql_loser);
            this.LAST_SQL_COMMAND = sql_loser;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert_attack(Attack attack) {
        int id = attack.id;
        String name = attack.name;
        String type = attack.type.toString();
        int damage = attack.damage;
        int ap = attack.max_ap;
        int accuracy = attack.accuracy;

        try {
            String sql = String.format("INSERT INTO Attacks VALUES (%s, '%s', '%s', %s, %s, %s)",
                    id, name, type, damage, ap, accuracy);
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(sql);
            this.LAST_SQL_COMMAND = sql;
        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int get_top_id() throws SQLException {
        Statement statement = this.connection.createStatement();
        ResultSet set = statement.executeQuery("SELECT id FROM Fights ORDER BY id DESC LIMIT 1");
        int result = -1;
        while (set.next()) {
            result = set.getInt(1) + 1;
        }

        return result;
    }
}
