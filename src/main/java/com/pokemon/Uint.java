package com.pokemon;

public class Uint {
    public int number;
  
    Uint(int number) {
        this.number = number;
    }

    public void substract(int number) {
        if (this.number - number >= 0) {
            this.number -= number;
        } else {
            this.number = 0;
        }
    }

    public void add(int number) {
        this.number += number;
    }
}
