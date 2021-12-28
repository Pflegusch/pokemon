package com.pokemon;

import javax.persistence.*;

@Entity
@Table(name = "Fights")
public class Fight {
    private static int fights = 0;

    static Database database;
    
    @Id
    @Column(name = "fight_id")
    public int id = fights;
    
    public int price = 500;
    public int rounds = 0;

    @Transient
    public boolean running = true;

    @Transient
    public User[] users = new User[2];

    @Transient
    public User winner;

    @Transient
    public User loser;

    @Transient
    public User attacker;

    @Transient
    public User defender;

    @Transient
    private int attacker_pokemon_id = 0;

    @Transient
    private int defender_pokemon_id = 0;

    Fight() {}

    Fight(User user1, User user2) {
        this.users[0] = user1;
        this.users[1] = user2;
        this.id++;
        starter();
        fights++;
    }

    Fight(User user1, User user2, int price) {
        this.users[0] = user1;
        this.users[1] = user2;
        this.id++;
        this.price = price;
        starter();
        reset_current_ap(user1);
        reset_current_ap(user2);
        fights++;
    }

    private void starter() {
        if (this.users[0].pokemons.get(attacker_pokemon_id).speed >= this.users[1].pokemons.get(defender_pokemon_id).speed) {
            this.attacker = this.users[0];
            this.defender = this.users[1];
        } else {
            this.attacker = this.users[1];
            this.defender = this.users[0];
        }
    }

    private void reset_current_ap(User user) {
        for (Pokemon pokemon : user.pokemons) {
            if (pokemon != null) {
                for (Attack attack : pokemon.attacks) {
                    attack.current_ap = attack.max_ap;
                }
            }
        }
    }

    private void show_status(Pokemon pokemon) {
        System.out.println("---------------------------------");
        System.out.println(String.format("-- %s(Lv. %s) HP: %s", pokemon.name, pokemon.lv, pokemon.current_hp));
        System.out.println("---------------------------------");
    }

    private void show_attacks(Pokemon pokemon) {
        System.out.println(String.format("-- %s %s/%s(1) --- %s %s/%s(2) --", 
            pokemon.attacks.get(0).name, pokemon.attacks.get(0).current_ap, pokemon.attacks.get(0).max_ap,
            pokemon.attacks.get(1).name, pokemon.attacks.get(1).current_ap, pokemon.attacks.get(1).max_ap));
    
        System.out.println(String.format("-- %s %s/%s(3) --- %s %s/%s(4) --", 
            pokemon.attacks.get(2).name, pokemon.attacks.get(2).current_ap, pokemon.attacks.get(2).max_ap,
            pokemon.attacks.get(3).name, pokemon.attacks.get(3).current_ap, pokemon.attacks.get(3).max_ap));
        System.out.println("---------------------------------");
    }

    private void swap_attackers() {
        User tmp = this.attacker;
        this.attacker = this.defender;
        this.defender = tmp;

        int tmp_id = attacker_pokemon_id;
        attacker_pokemon_id = defender_pokemon_id;
        defender_pokemon_id = tmp_id;
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
        for (User user : users) {
            for (Pokemon pokemon : user.pokemons) {
                if (pokemon != null) {
                    pokemon.current_hp = pokemon.hp;
                }
            }
        }

        while (true) {
            for (User user : users) {
                if (!attacker.pokemons.get(attacker_pokemon_id).alive && attacker_pokemon_id < attacker.pokemons.size() - 1) {
                    if (attacker.pokemons.get(attacker_pokemon_id + 1) != null) {
                        attacker_pokemon_id++;
                    }
                }

                if (!defender.pokemons.get(defender_pokemon_id).alive && defender_pokemon_id < defender.pokemons.size() - 1) {
                    if (defender.pokemons.get(defender_pokemon_id + 1) != null) {
                        defender_pokemon_id++;
                    }
                }

                if (user.check_if_done()) {
                    this.running = false;
                    this.loser = user;
                    this.winner = this.defender;
                    database.update_users_balance(this.winner, this.price);
                    database.update_users_balance(this.loser, -this.price);
                    database.insert_fight(this);
                    System.out.println(String.format("User %s lost", user.username));
                }
            }

            if (!this.running) break;

            System.out.println(String.format("It's %s turn! Choose an attack:", attacker.username));
            show_status(this.attacker.pokemons.get(attacker_pokemon_id));
            show_attacks(this.attacker.pokemons.get(attacker_pokemon_id));

            int choice = get_input();
            Attack attack = this.attacker.pokemons.get(attacker_pokemon_id).attacks.get(choice - 1);
            System.out.println(String.format("User choose attack %s", attack.name));

            this.attacker.pokemons.get(attacker_pokemon_id).attack(attack, this.attacker.pokemons.get(attacker_pokemon_id));   
            swap_attackers();
            this.rounds++;
        }
    }
}
