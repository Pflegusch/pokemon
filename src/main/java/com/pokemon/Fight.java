package com.pokemon;

import javax.persistence.*;

@Entity
@Table(name = "Fights")
public class Fight {
    private static int fights;

    @Transient
    public Trainer[] trainers = new Trainer[2];

    @Transient
    public Trainer winner;

    @Transient
    public Trainer loser;

    public int price = 500;

    @Id
    public int id = fights;

    public int rounds = 0;
    public boolean running = true;

    @Transient
    public Trainer attacker;
    @Transient
    public Trainer defender;

    Fight() {}

    Fight(Trainer trainer1, Trainer trainer2, int top_id) {
        this.trainers[0] = trainer1;
        this.trainers[1] = trainer2;
        this.id = top_id;
        starter();
        fights++;
    }

    Fight(Trainer trainer1, Trainer trainer2, int price, int top_id) {
        this.trainers[0] = trainer1;
        this.trainers[1] = trainer2;
        this.price = price;
        this.id = top_id;
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

    private void show_status(Pokemon pokemon) {
        System.out.println("---------------------------------");
        System.out.println(String.format("-- %s(Lv. %s) HP: %s --", pokemon.name, pokemon.lv, pokemon.hp));
        System.out.println("---------------------------------");
    }

    private void show_attacks(Pokemon pokemon) {
        System.out.println(String.format("-- %s %s/%s(1) --- %s %s/%s(2) --", 
            pokemon.attacks[0].name, pokemon.attacks[0].current_ap, pokemon.attacks[0].max_ap,
            pokemon.attacks[1].name, pokemon.attacks[1].current_ap, pokemon.attacks[1].max_ap));
    
        System.out.println(String.format("-- %s %s/%s(3) --- %s %s/%s(4) --", 
            pokemon.attacks[2].name, pokemon.attacks[2].current_ap, pokemon.attacks[2].max_ap,
            pokemon.attacks[3].name, pokemon.attacks[3].current_ap, pokemon.attacks[3].max_ap));
        System.out.println("---------------------------------");
    }

    private void swap_attackers() {
        Trainer tmp = this.attacker;
        this.attacker = this.defender;
        this.defender = tmp;
    }

    private int get_input() {
        int ret;
        while (true) {
            String input = System.console().readLine();
            ret = Integer.parseInt(input);
            if (ret < 1 || ret > 4) {
                System.out.println("Please choose an attack between 1 and 4!");
            } else {
                break;
            }
        }
        return ret;
    }

    public void start() {
        while (true) {
            if (!this.running) {
                break;
            }

            System.out.println(String.format("It's %s turn! Choose an attack:", attacker.name));
            show_status(this.attacker.pokemons[0]);
            show_attacks(this.attacker.pokemons[0]);

            int choice = get_input();
            Attack attack = this.attacker.pokemons[0].attacks[choice - 1];
            System.out.println(String.format("User choose attack %s", attack.name));

            // System.out.println(String.format("%s hp: %s", this.defender.pokemons[0].name, this.defender.pokemons[0].hp));
            this.attacker.pokemons[0].attack(attack, this.defender.pokemons[0]);
            // System.out.println(String.format("%s hp: %s", this.defender.pokemons[0].name, this.defender.pokemons[0].hp));
            
            swap_attackers();
            this.rounds++;

            for (Trainer trainer : trainers) {
                if (trainer.check_if_done()) {
                    this.running = false;
                    this.loser = trainer;
                    this.winner = this.defender;
                    // this.winner .update_balance(true, 500);
                    System.out.println(String.format("Trainer %s lost", trainer.name));
                }
            }
                
        }
    }
}
