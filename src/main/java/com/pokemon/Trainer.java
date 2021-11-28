package com.pokemon;

import javax.persistence.*;

@Entity
@Table(name = "Trainers")
public class Trainer {
    private static int trainers = 1;

    @Id
    public int id = trainers;

    @Transient
    public Pokemon[] pokemons = new Pokemon[6];

    public String name;
    public int balance;

    Trainer() {}

    Trainer(String name) {
        this.name = name;
        this.balance = 1000;
        trainers++;
    }

    Trainer(String name, int balance) {
        this.name = name; 
        this.balance = balance;
        trainers++;
    }

    public void update_balance(boolean has_won, int cash) {
        if (has_won) {
            balance += cash;
        } else {
            if (balance - cash >= 0) {
                this.balance -= cash;
            } else {
                this.balance = 0;
            }
        }
    }

    public boolean check_if_done() { 
        for (Pokemon pokemon : pokemons) {
            if (pokemon != null) {
                if (pokemon.hp > 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void heal_pokemons() {
        for (Pokemon pokemon : pokemons) {
            if (pokemon != null) {
                pokemon.heal();
            }      
        }
    }
}
