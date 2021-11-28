package com.pokemon;

import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class App 
{
    public static void main(String[] args)
    {
        Attack[] glurakAttacks = new Attack[4];
        glurakAttacks[0] = new Attack("Firestorm", Type.Fire, 120, 15, 80);
        glurakAttacks[1] = new Attack("Earthquake", Type.Ground, 60, 15, 100);
        glurakAttacks[2] = new Attack("Flamethrower", Type.Fire, 75, 15, 85);
        glurakAttacks[3] = new Attack("Heatwave", Type.Fire, 80, 30, 95);
        final Type[] glurakType = {Type.Fire, Type.Earth};
        final Type[] glurakWeaknesses = {Type.Water};
        Pokemon glurak = new Pokemon(
            360, 
            80,
            125,
            "Glurak",
            glurakAttacks, 
            glurakType, 
            glurakWeaknesses
        );

        Attack[] glumandaAttacks = new Attack[4];
        glumandaAttacks[0] = new Attack("Blaze Kick", Type.Fire, 110, 15, 80);
        glumandaAttacks[1] = new Attack("Blast Burn", Type.Fire, 60, 15, 100);
        glumandaAttacks[2] = new Attack("Fire Blast", Type.Fire, 75, 15, 85);
        glumandaAttacks[3] = new Attack("Thunder", Type.Electric, 80, 30, 95);
        final Type[] glumandaType = {Type.Fire, Type.Earth};
        final Type[] gluamandaWeaknesses = {Type.Water};
        Pokemon glumanda = new Pokemon(
            290, 
            70,
            180,
            "Glumanda",
            glumandaAttacks, 
            glumandaType, 
            gluamandaWeaknesses
        );

        Database database = new Database();
        database.initialization();

        int top_id = 0;
        try {
            top_id = database.get_top_id();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Trainer ash = new Trainer("Ash", 5000);
        ash.pokemons[0] = glurak;
        Trainer barry = new Trainer("Barry", 5000);
        barry.pokemons[0] = glumanda;

        Fight fight = new Fight(ash, barry, 250, top_id);
        top_id++;
        fight.start();
        
        for (Trainer trainer : fight.trainers) {
            trainer.heal_pokemons();
        }

        database.insert_fight(fight);
        database.update_trainers(fight);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mariadb");
        EntityManager em = emf.createEntityManager();

        Fight t = em.find(Fight.class, 0);
        System.out.println("Trainer price: " + t.price);

        // fight = new Fight(ash, barry, top_id);
        // top_id++;
        // fight.start();

        // database.insert_fight(fight);
        // database.update_trainers(fight);

    }
}
