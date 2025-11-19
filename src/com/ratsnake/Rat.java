package com.ratsnake;

import java.util.LinkedList;

public class Rat {
    private LinkedList<Point> body;
    private Direction direction;
    private boolean isAlive;

    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public static class Point {
        public int x, y;
        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
        
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }
    }

    public Rat(int startX, int startY) {
        body = new LinkedList<>();
        body.add(new Point(startX, startY));
        direction = Direction.RIGHT;
        isAlive = true;
    }

    public void setDirection(Direction newDirection) {
        // Prevent 180 degree turns
        if (this.direction == Direction.UP && newDirection == Direction.DOWN) return;
        if (this.direction == Direction.DOWN && newDirection == Direction.UP) return;
        if (this.direction == Direction.LEFT && newDirection == Direction.RIGHT) return;
        if (this.direction == Direction.RIGHT && newDirection == Direction.LEFT) return;
        this.direction = newDirection;
    }

    public void move() {
        if (!isAlive) return;

        Point head = body.getFirst();
        Point newHead = new Point(head.x, head.y);

        switch (direction) {
            case UP: newHead.y--; break;
            case DOWN: newHead.y++; break;
            case LEFT: newHead.x--; break;
            case RIGHT: newHead.x++; break;
        }

        body.addFirst(newHead);
        body.removeLast();
    }

    public void grow() {
        // Duplicate the tail to simulate growth (it will be "moved" in next step effectively)
        // Actually, standard snake logic: move adds head, if ate, don't remove tail.
        // My move() removes tail. So to grow, we just add the tail back or skip removing.
        // Let's adjust move logic or just add a dummy tail that gets fixed next move.
        // Better: The caller handles "eat" by not calling removeLast, or we have a specific grow method.
        // Let's change move() to NOT remove tail if we are growing? 
        // Simpler: 'grow' just adds a segment at the end.
        body.addLast(new Point(body.getLast().x, body.getLast().y));
    }
    
    public void moveAndGrow() {
         if (!isAlive) return;

        Point head = body.getFirst();
        Point newHead = new Point(head.x, head.y);

        switch (direction) {
            case UP: newHead.y--; break;
            case DOWN: newHead.y++; break;
            case LEFT: newHead.x--; break;
            case RIGHT: newHead.x++; break;
        }

        body.addFirst(newHead);
        // Do not remove last
    }

    public boolean checkSelfCollision() {
        Point head = body.getFirst();
        for (int i = 1; i < body.size(); i++) {
            if (head.equals(body.get(i))) {
                return true;
            }
        }
        return false;
    }

    public LinkedList<Point> getBody() {
        return body;
    }

    public Point getHead() {
        return body.getFirst();
    }
    
    public boolean isAlive() {
        return isAlive;
    }
    
    public void die() {
        isAlive = false;
    }
}
