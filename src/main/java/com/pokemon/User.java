package com.pokemon;

import java.util.List;
import java.util.ArrayList;

import javax.persistence.*;

@Entity
@Table(name = "Users")
public class User {
    private static int users = 0;

    static EntityManager entityManager;

    @Id
    public String username;

    public String email;
    public String password;

    @Transient
    public List<Pokemon> pokemons = new ArrayList<Pokemon>();

    public int balance;

    User() {}

    public void set_pokemons() {
        String sql = String.format("from Pokemon p where p.owner = '%s'", this.username);
        List<Pokemon> pokemons = entityManager.createQuery(sql, Pokemon.class).getResultList();

        for (Pokemon pokemon : pokemons) {
            this.pokemons.add(pokemon);
        }
    }

    public boolean check_if_done() {
        for (Pokemon pokemon : pokemons) {
            if (pokemon != null) {
                if (pokemon.current_hp > 0) {
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
