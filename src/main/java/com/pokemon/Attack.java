package com.pokemon;

public class Attack {
    private static int attacks = 1;

    public String name;
    public Type type;
    public int damage;
    public int max_ap;
    public int current_ap;
    public int accuracy;
    
    public int id = attacks;
    private boolean ko = false;

    Attack(String name) {
        this.name = name;
        this.type = Type.Normal;
        this.damage = 80;
        this.max_ap = 25;
        this.accuracy = 95;
        attacks++;
    }

    Attack(String name, Type type, int damage, int ap, int accuracy) {
        this.name = name;
        this.type = type;
        this.damage = damage;
        this.max_ap = ap;
        this.current_ap = ap;
        this.accuracy = accuracy;
        attacks++;
    }

    Attack(String name, Type type, int damage, int ap, int accuracy, boolean ko) {
        this.name = name;
        this.type = type;
        this.damage = damage;
        this.max_ap = ap;
        this.current_ap = ap;
        this.accuracy = accuracy;
        this.ko = ko;
        attacks++;
    }
}