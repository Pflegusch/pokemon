package com.pokemon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class Database {
    private String DB_URL = "jdbc:mysql://172.17.0.1:3306/";
    private String DB = "Pokemon";
    private String USER = "eich";
    private String PASS = "eich12344";

    private EntityManager em;
    private EntityTransaction tx;
    private Connection connection;

    private String LAST_SQL_COMMAND;

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

    Database(String DB_URL, String DB, String USER, String PASS, 
            EntityManager em, EntityTransaction tx) {
        this.DB_URL = DB_URL;
        this.DB = DB;
        this.USER = USER;
        this.PASS = PASS;
        this.em = em;
        this.tx = tx;

        try {
            this.connection = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void set_entitymanager(EntityManager em) {
        this.em = em;
    }

    public void set_entitytransaction(EntityTransaction tx) {
        this.tx = tx;
    }

    public void initialize() {
        if (!table_exist("Trainers")) initialize_trainers();
        if (!table_exist("Attacks")) initialize_attacks();
        if (!table_exist("Pokemons")) initialize_pokemons();
        if (!table_exist("Fights")) initialize_fights();
        
    }

    private void initialize_trainers() {
        String sql = "CREATE TABLE Trainers" +
            "(trainer_id INTEGER AUTO_INCREMENT, " +
            "name VARCHAR(255) not null, " +
            "balance INTEGER, " +
            "PRIMARY KEY ( trainer_id ))";

        em.getTransaction();
        tx.begin();
        em.createNativeQuery(sql).executeUpdate();
        tx.commit();
        this.LAST_SQL_COMMAND = sql;
    }

    private void initialize_pokemons() {
        String sql = "CREATE TABLE Pokemons" +
            "(pokemon_id INTEGER AUTO_INCREMENT, " +
            "name VARCHAR(255) not null, " +
            "lv INTEGER not null, " +
            "hp INTEGER not null, " +
            "speed INTEGER not null, " +
            "type VARCHAR(255) not null, " +
            "weakness VARCHAR(255) not null, " +
            "attack_1 VARCHAR(255) not null, " + 
            "attack_2 VARCHAR(255) not null, " + 
            "attack_3 VARCHAR(255) not null, " + 
            "attack_4 VARCHAR(255) not null, " + 
            "FOREIGN KEY (attack_1) REFERENCES Attacks(name), " +
            "FOREIGN KEY (attack_2) REFERENCES Attacks(name), " +
            "FOREIGN KEY (attack_3) REFERENCES Attacks(name), " +
            "FOREIGN KEY (attack_4) REFERENCES Attacks(name), " +
            "PRIMARY KEY ( pokemon_id ))";

        em.getTransaction();
        tx.begin();
        em.createNativeQuery(sql).executeUpdate();
        tx.commit();
        this.LAST_SQL_COMMAND = sql;
    }

    private void initialize_fights() {
        String sql = "CREATE TABLE Fights" +
            "(fight_id INTEGER AUTO_INCREMENT, " +
            "rounds INTEGER DEFAULT null, " +
            "price INTEGER DEFAULT null, " +
            "winner INTEGER not null, " +
            "loser INTEGER not null, " +
            "created_at DATETIME, " +
            "PRIMARY KEY ( fight_id ), " +
            "FOREIGN KEY (winner) REFERENCES Trainers(trainer_id), " +
            "FOREIGN KEY (loser) REFERENCES Trainers(trainer_id))";

        em.getTransaction();
        tx.begin();
        em.createNativeQuery(sql).executeUpdate();
        tx.commit();
        this.LAST_SQL_COMMAND = sql;
    }

    private void initialize_attacks() {
        String sql = "CREATE TABLE Attacks" +
            "(name VARCHAR(255) not null, " +
            "type VARCHAR(255) not null, " +
            "damage INTEGER DEFAULT null, " +
            "ap INTEGER not null, " + 
            "accuracy INTEGER not null, " +
            "ko BOOLEAN DEFAULT false, " +
            "PRIMARY KEY ( name ))";

        em.getTransaction();
        tx.begin();
        em.createNativeQuery(sql).executeUpdate();
        tx.commit();
        this.LAST_SQL_COMMAND = sql;
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
        String sql = String.format("INSERT INTO Trainers VALUES (NULL, '%s', %s)",
            trainer.name, trainer.balance);

        em.getTransaction();
        tx.begin();
        em.createNativeQuery(sql).executeUpdate();
        tx.commit();
        this.LAST_SQL_COMMAND = sql;
    }

    public void insert_pokemon(Pokemon pokemon) {
        int lv = pokemon.lv;
        String name = pokemon.name;
        String types = "";
        String weaknesses = "";

        for (Type type : pokemon.type) {
            types += type.toString() + ",";
        }
        types = types.substring(0, types.length() - 1);
        
        for (Type type : pokemon.weaknesses) {
            weaknesses += type.toString() + ",";
        }
        weaknesses = weaknesses.substring(0, weaknesses.length() - 1);

        String a1 = pokemon.attacks[0].name;
        String a2 = pokemon.attacks[1].name;
        String a3 = pokemon.attacks[2].name;
        String a4 = pokemon.attacks[3].name;

        String sql = String.format("INSERT INTO Pokemons VALUES " + 
            "(NULL, '%s', %s, '%s', '%s', '%s', '%s', '%s', '%s')",
            name, lv, types, weaknesses, a1, a2, a3, a4);

        em.getTransaction();
        tx.begin();
        em.createNativeQuery(sql).executeUpdate();
        tx.commit();
        this.LAST_SQL_COMMAND = sql;
    }

    public void insert_fight(Fight fight) {
        int rounds = fight.rounds;    
        int winner_id = fight.winner.id;
        int loser_id = fight.loser.id;
        int price = fight.price;

        String sql = String.format("INSERT INTO Fights VALUES (NULL, %s, %s, %s, %s, NOW())",
                rounds, price, winner_id, loser_id);
        em.getTransaction();
        tx.begin();
        em.createNativeQuery(sql).executeUpdate();
        tx.commit();
        this.LAST_SQL_COMMAND = sql;
    }

    public void update_trainers(Fight fight) {
        String sql_winner = String.format("UPDATE Trainers SET balance = balance + %s WHERE trainer_id = %s",
                fight.price, fight.winner.id);
        String sql_loser= String.format("UPDATE Trainers SET balance = balance - %s WHERE trainer_id = %s",
                fight.price, fight.loser.id);

        em.getTransaction();
        tx.begin();
        em.createNativeQuery(sql_winner).executeUpdate();
        this.LAST_SQL_COMMAND = sql_winner;
        em.createNativeQuery(sql_loser).executeUpdate();
        this.LAST_SQL_COMMAND = sql_loser;
        tx.commit();
    }

    public void insert_attack(Attack attack) {
        String name = attack.name;
        String type = attack.type.toString();
        int damage = attack.damage;
        int ap = attack.max_ap;
        int accuracy = attack.accuracy;

        String sql = String.format("INSERT INTO Attacks VALUES ('%s', '%s', %s, %s, %s)",
                name, type, damage, ap, accuracy);
        em.getTransaction();
        tx.begin();
        em.createNativeQuery(sql).executeUpdate();
        tx.commit();
        this.LAST_SQL_COMMAND = sql;
    }

    public int get_top_id() throws SQLException {
        Statement statement = this.connection.createStatement();
        ResultSet set = statement.executeQuery("SELECT fight_id FROM Fights ORDER BY fight_id DESC LIMIT 1");
        int result = 0;
        while (set.next()) {
            result = set.getInt(1) + 1;
        }

        return result;
    }

    public void set_attacks(Pokemon pokemon) {
        String sql = String.format("SELECT attack_1, attack_2, attack_3, attack_4 FROM Pokemons WHERE pokemon_id = %s", pokemon.id);
        Statement statement;
        ResultSet set;
        try {
            statement = this.connection.createStatement();
            set = statement.executeQuery(sql);
            if (set.next()) {
                for (int id = 0; id < 4; ++id) {
                    pokemon.attacks[id] = em.find(Attack.class, set.getString(id + 1));
                } 
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
