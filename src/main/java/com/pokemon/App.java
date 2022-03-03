package com.pokemon;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.server.rest.HelloRest;
import com.server.rest.UserRest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.logging.Level;

@SpringBootApplication
@ComponentScan(basePackages = {"com.server.rest"})
public class App 
{
    public static void main(String[] args)
    {
        Database database = new Database();

        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.OFF);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mariadb");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        database.set_entitymanager(em);
        database.set_entitytransaction(tx);
        database.initialize();

        User.entityManager = em;
        Pokemon.connection = database.connection;
        Pokemon.entityManager = em;
        Fight.database = database;

        new HelloRest(em);
        new UserRest(em, tx);
        SpringApplication.run(App.class);


        // database.set_attacks(groudon);
        // database.set_attacks(glumanda);
        // database.set_attacks(glurak);

        // Fight fight = new Fight(ash, barry, 250);
        // fight.start();

        // database.insert_fight(fight);
        // database.update_trainers(fight);

        User pflegusch = em.find(User.class, "Pflegusch");
        pflegusch.set_pokemons();

        for (Pokemon pokemon : pflegusch.pokemons) {
            pokemon.set_attacks();
        }

        User alice = em.find(User.class, "alice");
        alice.set_pokemons();

        for (Pokemon pokemon : alice.pokemons) {
            pokemon.set_attacks();
        }

        Fight fight = new Fight(pflegusch, alice, 300);
        // fight.start();

        Shop shop = new Shop();
        Shop.entityManager = em;
        Shop.entityTransaction = tx;
        shop.fill_shop();
    }
}
