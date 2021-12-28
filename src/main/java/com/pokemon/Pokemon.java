package com.pokemon;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;

import javax.persistence.*;

@Entity
@Table(name = "Pokemons")
public class Pokemon {
    private static int pokemons = 0;

    static EntityManager entityManager;
    static Connection connection;
    
    @Id
    @Column(name = "pokemon_id")
    public int id = pokemons;

    public int hp;
    public int lv;
    public int speed;

    public String name;
    public String owner;
    
    @Transient
    public List<Attack> attacks = new ArrayList<Attack>();

    @Transient
    public Type[] type = new Type[2];

    @Transient
    public Type[] weaknesses = new Type[2];

    @Transient
    public boolean alive = true;

    @Transient
    public int current_hp;

    Pokemon() {}

    Pokemon(int hp, int lv, int speed, String name) {
        this.hp = hp;
        this.current_hp = hp;
        this.lv = lv;
        this.speed = speed;
        this.name = name;
        pokemons++;
    }

    Pokemon(int hp, int lv, int speed, String name, List<Attack> attacks, Type[] type, Type[] weaknesses) {
        this.hp = hp;
        this.current_hp = hp;
        this.lv = lv;
        this.speed = speed;
        this.name = name;
        this.attacks = attacks;
        this.type = type;
        this.weaknesses = weaknesses;
        pokemons++;
    }

    public void set_attacks() {
        final int number_of_attacks = 4;
        Statement statement;
        ResultSet set;

        for (int attack_id = 1; attack_id <= number_of_attacks; attack_id++) {
            String sql = String.format("select Attacks.name from Attacks, Pokemons where Attacks.name = Pokemons.attack_%s and Pokemons.name = '%s';"
                        , attack_id, this.name);      
            try {
                statement = connection.createStatement();
                set = statement.executeQuery(sql);
                while(set.next()) {
                    Attack attack = entityManager.find(Attack.class, set.getString("name"));
                    if (attack != null) {
                        entityManager.detach(attack);
                        this.attacks.add(attack);
                    }       
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void attack(Attack attack, Pokemon other) {
        Random random = new Random();
        int accuracy_range = random.nextInt(100);
        if (100 - attack.accuracy < accuracy_range) {
            if ((other.current_hp - attack.damage) <= 0) {
                other.current_hp = 0;
                other.alive = false;
            } else {
                 other.current_hp -= attack.damage;
            } 
            attack.current_ap--;
        } else {
            attack.current_ap--;
        }
    }

    public void heal() {
        this.current_hp = hp;
    }
}
