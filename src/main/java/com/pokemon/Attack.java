package com.pokemon;

import javax.persistence.*;

@Entity
@Table(name = "Attacks")
public class Attack {
    private static int attacks = 0;

    @Id
    public String name;

    @Transient
    public int id = attacks;

    public int accuracy;
    public boolean ko;
    public int damage;

    @Enumerated(EnumType.STRING)
    public Type type;

    @Column(name = "ap")
    public int max_ap;

    @Transient
    public int current_ap;
    
    Attack() {}

    Attack(String name) {
        this.name = name;
        this.type = Type.Normal;
        this.damage = 80;
        this.max_ap = 25;
        this.current_ap = max_ap;
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
        this.ko= ko;
        attacks++;
    }
}