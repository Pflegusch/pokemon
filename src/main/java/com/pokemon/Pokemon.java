package com.pokemon;

import java.util.Random;

public class Pokemon {
    private static int pokemons = 1;

    public int hp = 0;
    public int lv = 0;
    public int speed = 0;
    public String name;
    public Attack[] attacks = new Attack[4];
    public Type[] type = new Type[2];
    public Type[] weaknesses = new Type[2];
    public boolean alive = true;

    private int id = pokemons;
    private int current_exp = 0;

    Pokemon(int hp, int lv, int speed, String name, Attack[] attacks, Type[] type, Type[] weaknesses) {
        this.hp = hp;
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

    public void add_exp(int exp) {
        this.current_exp += exp;
    }

    public int get_id() {
        return this.id;
    }
}
