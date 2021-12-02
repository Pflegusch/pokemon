package com.pokemon;

import java.util.Random;
import javax.persistence.*;

@Entity
@Table(name = "Pokemons")
public class Pokemon {
    private static int pokemons = 0;
    
    @Id
    @Column(name = "pokemon_id")
    public int id = pokemons;

    public int hp;
    public int lv;
    public int speed;

    public String name;
    
    @Transient
    public Attack[] attacks = new Attack[4];

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

    Pokemon(int hp, int lv, int speed, String name, Attack[] attacks, Type[] type, Type[] weaknesses) {
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

    public void attack(Attack attack, Pokemon other) {
        Random random = new Random();
        int accuracy_range = random.nextInt(100);
        if (100 - attack.accuracy < accuracy_range) {
            if ((other.current_hp - attack.damage) <= 0) {
                other.current_hp = 0;
                this.alive = false;
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
