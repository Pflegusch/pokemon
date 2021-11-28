package com.pokemon;

import java.util.Random;
import javax.persistence.*;

@Entity
@Table(name = "Pokedex")
public class Pokemon {
    private static int pokemons = 1;

    @Transient
    public int hp = 0;

    @Transient
    public int lv = 0;

    @Transient
    public int speed = 0;

    public String name;
    @Transient
    public Attack[] attacks = new Attack[4];

    @Transient
    public Type[] type = new Type[2];

    @Transient
    public Type[] weaknesses = new Type[2];

    @Transient
    public boolean alive = true;

    @Id
    public int id = pokemons;

    @Transient
    private int max_hp;

    @Transient
    private int current_exp = 0;

    Pokemon() {}

    Pokemon(int hp, int lv, int speed, String name, Attack[] attacks, Type[] type, Type[] weaknesses) {
        this.hp = hp;
        this.max_hp = hp;
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
            if ((other.hp - attack.damage) <= 0) {
                other.hp = 0;
                this.alive = false;
            } else {
                 other.hp -= attack.damage;
            } 
            attack.current_ap--;
        } else {
            attack.current_ap--;
        }
    }

    public void heal() {
        this.hp = max_hp;
    }
}
