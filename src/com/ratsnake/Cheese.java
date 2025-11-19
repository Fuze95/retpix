package com.ratsnake;

import java.util.Random;

public class Cheese {
    private int x;
    private int y;
    private Random random;

    public Cheese() {
        this.random = new Random();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void spawn(int width, int height) {
        this.x = random.nextInt(width);
        this.y = random.nextInt(height);
    }
}
