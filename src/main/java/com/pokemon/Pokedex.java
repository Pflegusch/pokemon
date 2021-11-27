package com.pokemon;

public class Pokedex {
    Pokemon[] pokemons = new Pokemon[10];
    String region;

    Pokedex(Pokemon[] pokemons, String region) {
        this.pokemons = pokemons;
        this.region = region;
    }

    public Pokemon get_pokemon(int index) {
        return this.pokemons[index + 1];
    }
}
