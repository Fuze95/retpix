package com.ratsnake;

public class Board {
    private int width;
    private int height;
    private Rat rat;
    private Cheese cheese;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.rat = new Rat(width / 2, height / 2);
        this.cheese = new Cheese();
        this.cheese.spawn(width, height);
    }

    public void update() {
        if (!rat.isAlive()) return;

        // Predict next move to check for cheese
        Rat.Point head = rat.getHead();
        // This is a bit tricky because we need to know where it *will* be.
        // Let's just move first, then check collisions.
        // If we hit cheese, we should have grown. 
        // Standard approach: Check if next position is cheese.
        
        // Let's simplify: Move, then check if head is on cheese.
        // If so, we needed to grow. But we already moved (tail removed).
        // So we need to add tail back? Or just use a flag.
        
        // Let's use the Rat's method.
        // We need to know if we are ABOUT to eat.
        // Let's just do: move, check collision.
        // If head == cheese, grow (add tail back).
        
        rat.move();
        
        if (rat.checkSelfCollision()) {
            rat.die();
            return;
        }
        
        Rat.Point newHead = rat.getHead();
        if (newHead.x < 0 || newHead.x >= width || newHead.y < 0 || newHead.y >= height) {
            rat.die();
            return;
        }

        if (newHead.x == cheese.getX() && newHead.y == cheese.getY()) {
            rat.grow(); // Adds the tail back
            cheese.spawn(width, height);
            // Ensure cheese doesn't spawn on rat
            while (isOnRat(cheese.getX(), cheese.getY())) {
                cheese.spawn(width, height);
            }
        }
    }
    
    private boolean isOnRat(int x, int y) {
        for (Rat.Point p : rat.getBody()) {
            if (p.x == x && p.y == y) return true;
        }
        return false;
    }

    public void render() {
        // Clear console (hacky way for Java CLI)
        // System.out.print("\033[H\033[2J");  // ANSI escape code, might not work in all Windows terminals
        // System.out.flush();
        
        // Alternative: Print many newlines
        // for (int i = 0; i < 50; ++i) System.out.println();

        StringBuilder sb = new StringBuilder();
        
        // Top border
        for (int i = 0; i < width + 2; i++) sb.append("#");
        sb.append("\n");

        for (int y = 0; y < height; y++) {
            sb.append("#"); // Left border
            for (int x = 0; x < width; x++) {
                if (x == cheese.getX() && y == cheese.getY()) {
                    sb.append("C"); // Cheese
                } else if (isHead(x, y)) {
                    sb.append("R"); // Rat Head
                } else if (isOnRat(x, y)) {
                    sb.append("o"); // Rat Body
                } else {
                    sb.append(" ");
                }
            }
            sb.append("#\n"); // Right border
        }

        // Bottom border
        for (int i = 0; i < width + 2; i++) sb.append("#");
        sb.append("\n");
        
        if (!rat.isAlive()) {
            sb.append("GAME OVER! Press 'q' to quit.\n");
        } else {
            sb.append("Use W/A/S/D to move, then Enter. Q to quit.\n");
        }

        System.out.println(sb.toString());
    }
    
    private boolean isHead(int x, int y) {
        Rat.Point head = rat.getHead();
        return head.x == x && head.y == y;
    }

    public Rat getRat() {
        return rat;
    }
    
    public boolean isGameOver() {
        return !rat.isAlive();
    }
}
