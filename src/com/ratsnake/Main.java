package com.ratsnake;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Board board = new Board(20, 10);
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Welcome to Rat Snake!");
        board.render();

        while (!board.isGameOver()) {
            System.out.print("Enter move (w/a/s/d): ");
            String input = scanner.nextLine().toLowerCase();
            
            if (input.equals("q")) {
                break;
            }
            
            if (input.length() > 0) {
                // Check for ANSI escape sequences for arrow keys
                // UP: \033[A, DOWN: \033[B, RIGHT: \033[C, LEFT: \033[D
                // Note: Scanner might strip some chars or encoding might vary, but we check common endings.
                
                if (input.equals("\u001B[A") || input.equalsIgnoreCase("w")) {
                     board.getRat().setDirection(Rat.Direction.UP);
                } else if (input.equals("\u001B[B") || input.equalsIgnoreCase("s")) {
                     board.getRat().setDirection(Rat.Direction.DOWN);
                } else if (input.equals("\u001B[D") || input.equalsIgnoreCase("a")) {
                     board.getRat().setDirection(Rat.Direction.LEFT);
                } else if (input.equals("\u001B[C") || input.equalsIgnoreCase("d")) {
                     board.getRat().setDirection(Rat.Direction.RIGHT);
                } else {
                    // Fallback for single char inputs if escape sequence fails or user types 'w'
                    char move = input.charAt(0);
                    switch (move) {
                        case 'w': board.getRat().setDirection(Rat.Direction.UP); break;
                        case 's': board.getRat().setDirection(Rat.Direction.DOWN); break;
                        case 'a': board.getRat().setDirection(Rat.Direction.LEFT); break;
                        case 'd': board.getRat().setDirection(Rat.Direction.RIGHT); break;
                    }
                }
            }
            
            board.update();
            board.render();
        }
        
        System.out.println("Thanks for playing!");
        scanner.close();
    }
}
