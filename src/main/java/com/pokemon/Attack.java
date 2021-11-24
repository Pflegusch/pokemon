package com.pokemon;

public class Attack {
    private static int attacks = 1;

    private enum Type {Normal, Fire, Water, Earth};

    public String name;
    public Type type;
    public char damage;
    public char ap;
    public char accuracy;
    
    private boolean ko = false;

    Attack(String name) {
        this.name = name;
        this.type = Type.Normal;
        this.damage = 80;
        this.ap = 25;
        this.accuracy = 95;
        attacks++;
    }

    Attack(String name, Type type, char damage, char ap, char accuracy) {
        this.name = name;
        this.type = type;
        this.damage = damage;
        this.ap = ap;
        this.accuracy = accuracy;
        attacks++;
    }

    Attack(String name, Type type, char damage, char ap, char accuracy, boolean ko) {
        this.name = name;
        this.type = type;
        this.damage = damage;
        this.ap = ap;
        this.accuracy = accuracy;
        this.ko = ko;
        attacks++;
    }  
}