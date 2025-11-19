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
                char move = input.charAt(0);
                switch (move) {
                    case 'w': board.getRat().setDirection(Rat.Direction.UP); break;
                    case 's': board.getRat().setDirection(Rat.Direction.DOWN); break;
                    case 'a': board.getRat().setDirection(Rat.Direction.LEFT); break;
                    case 'd': board.getRat().setDirection(Rat.Direction.RIGHT); break;
                }
            }
            
            board.update();
            board.render();
        }
        
        System.out.println("Thanks for playing!");
        scanner.close();
    }
}
