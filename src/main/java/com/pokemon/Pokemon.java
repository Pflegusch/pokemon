package com.pokemon;

public class Pokemon {
    private static int pokemons = 1;

    public int hp = 0;
    public int lv = 0;
    public String name;
    public Attack[] attacks = new Attack[4];
    public Type[] type = new Type[2];
    public Type[] weaknesses = new Type[2];

    private int id = pokemons;
    private int current_exp = 0;
    private boolean ready = false;

    Pokemon(int hp, int lv, String name, Attack[] attacks, Type[] type, Type[] weaknesses) {
        this.hp = hp;
        this.lv = lv;
        this.name = name;
        this.attacks = attacks;
        this.type = type;
        this.weaknesses = weaknesses;
        pokemons++;
    }

    public void add_exp(int exp) {
        this.current_exp += exp;
    }

    public int get_id() {
        return this.id;
    }

    public boolean is_ready() {
        return this.ready;
    }

}
