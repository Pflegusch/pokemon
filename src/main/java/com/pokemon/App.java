package com.pokemon;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args)
    {
        String[] glurakAttacks = {"Firestorm", "Earthquake", "Heat Wave", "Eruption"};
        String[] glurakType = {"Fire", "Earth"};
        String[] glurakWeaknesses = {"Water", "Psycho"};
        Pokemon glurak = new Pokemon(
            260, 
            80, 
            "Glurak",
            glurakAttacks, 
            glurakType, 
            glurakWeaknesses
        );

        String[] glumandaAttacks = {"Firestorm", "Earthquake", "Heat Wave", "Eruption"};
        String[] glumandaType = {"Fire", "Earth"};
        String[] gluamandaWeaknesses = {"Water", "Psycho"};
        Pokemon glumanda = new Pokemon(
            80, 
            18,
            "Glumanda",
            glumandaAttacks, 
            glumandaType, 
            gluamandaWeaknesses
        );

    }
}
