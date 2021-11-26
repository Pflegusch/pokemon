package com.pokemon;

import java.util.Scanner;

public class Fight {
    private static int fights = 1;

    public Trainer[] trainers = new Trainer[2];
    public Trainer winner;
    public Trainer loser;

    private int id = fights;
    private int rounds = 0;
    public Trainer attacker;
    public Trainer defender;

    Fight(Trainer trainer1, Trainer trainer2) {
        this.trainers[0] = trainer1;
        this.trainers[1] = trainer2;
        starter();
        fights++;
    }

    private void starter() {
        if (this.trainers[0].pokemons[0].speed >= this.trainers[1].pokemons[0].speed) {
            this.attacker = this.trainers[0];
            this.defender = this.trainers[1];
        } else {
            this.attacker = this.trainers[1];
            this.defender = this.trainers[0];
        }
    }

    private void show_attacks(Pokemon pokemon) {
        System.out.println("---------------------------------");
        System.out.println(String.format("-- %s(1) --- %s(2) --", pokemon.attacks[0].name, pokemon.attacks[1].name));
        System.out.println(String.format("-- %s(3) --- %s(4) --", pokemon.attacks[2].name, pokemon.attacks[3].name));
        System.out.println("---------------------------------");
    }

    public void attack() {
        System.out.println(String.format("It's %s turn! Choose an attack:", attacker.name));
        show_attacks(this.attacker.pokemons[0]);

        Scanner reader = new Scanner(System.in);
        int n = reader.nextInt();
        Attack attack = this.attacker.pokemons[0].attacks[n - 1];
        System.out.println(String.format("User choose attack %s", attack.name));
        reader.close();

        System.out.println(this.defender.pokemons[0].hp);
        this.attacker.pokemons[0].attack(attack, this.defender.pokemons[0]);
        System.out.println(this.defender.pokemons[0].hp);
    }
}
