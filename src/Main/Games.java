package Main;

import java.util.Scanner;

public class Games {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Which game would you like to play?");
        System.out.println("1. Snake");
        System.out.println("2. Tic Tac Toe");
        int choice = scanner.nextInt();
        if (choice == 1) {
            SnakeGame.main(null);
        } else if (choice == 2) {
            TicTacToe.main(null);
        } else {
            System.out.println("Invalid choice");
        }
        scanner.close();
    }
}
