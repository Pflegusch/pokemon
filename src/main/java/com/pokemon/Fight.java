package com.pokemon;

public class Fight {
    private static int fights = 1;

    public Trainer[] trainers = new Trainer[2];
    public Trainer winner;
    public Trainer loser;

    private int id = fights;
    private int rounds = 0;
    private Trainer turn;

    Fight(Trainer trainer1, Trainer Trainer2) {
        this.trainers[0] = trainer1;
        this.trainers[1] = trainer1;
        fights++;
    }


}
