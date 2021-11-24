package com.pokemon;

public class Pokemon {
    private static int pokemons = 1;

    public int hp = 0;
    public int lv = 0;
    public String name;
    public String[] attacks = new String[4];
    public String[] type = new String[2];
    public String[] weaknesses = new String[2];

    private int id = pokemons;
    private int current_exp = 0;
    private boolean ready = false;

    Pokemon(int hp, int lv, String name, String[] attacks, String[] type, String[] weaknesses) {
        this.hp = hp;
        this.lv = lv;
        this.name = name;
        this.attacks = attacks;
        this.type = type;
        this.weaknesses = weaknesses;

        if (this.lv >= 30) {
            this.ready = true;
        }
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
