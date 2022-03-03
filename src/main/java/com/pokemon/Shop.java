package com.pokemon;

import java.util.List;

import javax.persistence.*;

public class Shop {
    static EntityManager entityManager;
    static EntityTransaction entityTransaction;

    Shop() {}

    public void fill_shop() {
        String sql = "from Pokemon p where p.owner = NULL";
        List<Pokemon> pokemons = entityManager.createQuery(sql, Pokemon.class).getResultList();

        for (Pokemon pokemon : pokemons) {
            sql = String.format("INSERT INTO Shop VALUES (%s, DEFAULT)", pokemon.id);  
            try {
                entityManager.getTransaction();
                entityTransaction.begin();
                entityManager.createNativeQuery(sql).executeUpdate();
                entityTransaction.commit(); 
            } catch (PersistenceException e) {
                e.printStackTrace();
                entityTransaction.rollback();
            }
        }
    }

    public void set_price(Pokemon pokemon, int price) {
        String sql = String.format("UPDATE Shop SET price = %s where pokemon_id = %s", price, pokemon.id);
        entityManager.getTransaction();
        entityTransaction.begin();
        entityManager.createNativeQuery(sql).executeUpdate();
        entityTransaction.commit();
    }
}
