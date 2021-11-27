package com.pokemon;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args)
    {
        Attack[] glurakAttacks = new Attack[4];
        glurakAttacks[0] = new Attack("Firestorm", Type.Fire, (char)100, (char)15, (char)90);
        glurakAttacks[1] = new Attack("Earthquake", Type.Ground, (char)100, (char)15, (char)100);
        glurakAttacks[2] = new Attack("Flamethrower", Type.Fire, (char)120, (char)15, (char)85);
        glurakAttacks[3] = new Attack("Heatwave", Type.Fire, (char)80, (char)30, (char)95);
        final Type[] glurakType = {Type.Earth, Type.Fire};
        final Type[] glurakWeaknesses = {Type.Water};
        Pokemon glurak = new Pokemon(
            260, 
            80,
            125,
            "Glurak",
            glurakAttacks, 
            glurakType, 
            glurakWeaknesses
        );

        Attack[] glumandaAttacks = new Attack[4];
        glumandaAttacks[0] = new Attack("Blaze Kick", Type.Fire, (char)100, (char)15, (char)90);
        glumandaAttacks[1] = new Attack("Blast Burn", Type.Fire, (char)100, (char)15, (char)100);
        glumandaAttacks[2] = new Attack("Fire Blast", Type.Fire, (char)120, (char)15, (char)85);
        glumandaAttacks[3] = new Attack("Thunder", Type.Electric, (char)80, (char)30, (char)95);
        final Type[] glumandaType = {Type.Fire, Type.Earth};
        final Type[] gluamandaWeaknesses = {Type.Water};
        Pokemon glumanda = new Pokemon(
            80, 
            18,
            180,
            "Glumanda",
            glumandaAttacks, 
            glumandaType, 
            gluamandaWeaknesses
        );

        Trainer ash = new Trainer("Ash", new Uint(5000));
        ash.pokemons[0] = glurak;
        Trainer barry = new Trainer("Barry", new Uint(2500));
        barry.pokemons[0] = glumanda;

        Fight fight = new Fight(ash, barry);
        // First attack now
        fight.attack();

    }
}
