package com.pokemon;

public class Trainer {
    private static int trainers = 1;

    public Pokemon[] pokemons = new Pokemon[6];
    public String name;
    public boolean ready = true;
    public Uint balance = new Uint(0);

    private int id = trainers;

    Trainer(String name) {
        this.name = name;
        trainers++;
    }

    public void update_balance(boolean has_won, int cash) {
        if (has_won) {
            balance.add(cash);
        } else {
            balance.substract(cash);
        }
    }
}
