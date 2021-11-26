package com.pokemon;

public class Trainer {
    private static int trainers = 1;

    public Pokemon[] pokemons = new Pokemon[6];
    public String name;
    public boolean ready = true;
    public Uint balance;

    private int id = trainers;

    Trainer(String name) {
        this.name = name;
        this.balance = new Uint(1000);
        trainers++;
    }

    Trainer(String name, Uint balance) {
        this.name = name; 
        this.balance = balance;
        trainers++;
    }

    public void update_balance(boolean has_won, int cash) {
        if (has_won) {
            balance.add(cash);
        } else {
            balance.substract(cash);
        }
    }

    public boolean check_if_done() {
        for (Pokemon pokemon : pokemons) {
            if (pokemon.hp != 0) {
                return false;
            }
        }
        return true;
    }
}
