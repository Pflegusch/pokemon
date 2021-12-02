package com.pokemon;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import java.util.logging.Level;

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
        
        // final Type[] glurakType = {Type.Fire, Type.Earth};
        // final Type[] glurakWeaknesses = {Type.Water};
        // Pokemon glurak = new Pokemon(
        //     360, 
        //     80,
        //     125,
        //     "Glurak"
        // );
        // glurak.attacks[0] = em.find(Attack.class, "Blast Burn");
        // glurak.attacks[1] = em.find(Attack.class, "Blaze Kick");
        // glurak.attacks[2] = em.find(Attack.class, "Earthquake");
        // glurak.attacks[3] = em.find(Attack.class, "Fire Blast");
        // glurak.type = glurakType;
        // glurak.weaknesses = glurakWeaknesses;
        // //database.insert_pokemon(glurak);

        // final Type[] glumandaType = {Type.Fire, Type.Earth};
        // final Type[] gluamandaWeaknesses = {Type.Water};
        // Pokemon glumanda = new Pokemon(
        //     290, 
        //     70,
        //     180,
        //     "Glumanda"
        // );
        // glumanda.attacks[0] = em.find(Attack.class, "Fire Blast");
        // glumanda.attacks[1] = em.find(Attack.class, "Heatwave");
        // glumanda.attacks[2] = em.find(Attack.class, "Hyper Beam");
        // glumanda.attacks[3] = em.find(Attack.class, "Blast Burn");
        // glumanda.weaknesses = gluamandaWeaknesses;
        // glumanda.type = glumandaType;
        // //database.insert_pokemon(glumanda);

        // // Trainer ash = new Trainer("Ash", 5000);
        // // ash.pokemons[0] = glurak;
        // // Trainer barry = new Trainer("Barry", 5000);
        // // barry.pokemons[0] = glumanda;

        Trainer ash = em.find(Trainer.class, 1);
        Trainer barry = em.find(Trainer.class, 2);
        
        Pokemon glumanda = em.find(Pokemon.class, 1);
        Pokemon groudon = em.find(Pokemon.class, 2);

        ash.pokemons[0] = groudon;
        barry.pokemons[0] = glumanda;

        database.set_attacks(groudon);
        database.set_attacks(glumanda);

        Fight fight = new Fight(ash, barry, 250);
        fight.start();
        
        for (Trainer trainer : fight.trainers) {
            trainer.heal_pokemons();
        }

        database.insert_fight(fight);
        database.update_trainers(fight);
    }
}
