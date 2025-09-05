package Main;

import java.util.Scanner;

class TicTacToe {

    private static final int BOARD_SIZE = 3;

    private static int[][] board;
    private static int currentPlayer;
    private static Node moveStackTop;

    private static class Node {
        int[] data;
        Node next;

        Node(int[] data) {
            this.data = data;
        }
    }

    public static void main(String[] args) {
        initializeBoard();
        currentPlayer = 1;
        moveStackTop = null;

        Scanner scanner = new Scanner(System.in);
        while (true) {
            printBoard();
            System.out.println("Player " + currentPlayer + "'s turn:");
            System.out.print("Enter row (1-" + BOARD_SIZE + "): ");
            int row = scanner.nextInt() - 1;
            System.out.print("Enter column (1-" + BOARD_SIZE + "): ");
            int col = scanner.nextInt() - 1;
            if (makeMove(row, col)) {
                if (checkForWin()) {
                    printBoard();
                    System.out.println("Player " + currentPlayer + " wins!");
                    break;
                }
                if (checkForDraw()) {
                    printBoard();
                    System.out.println("It's a draw!");
                    break;
                }
                currentPlayer = currentPlayer == 1 ? 2 : 1;
            } else {
                System.out.println("Invalid move, try again.");
            }
        }
        scanner.close();
    }

    private static void initializeBoard() {
        board = new int[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = 0;
            }
        }
    }

    private static void printBoard() {
        System.out.println("  1 2 3");

        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == 0) {
                    System.out.print(" ");
                } else if (board[i][j] == 1) {
                    System.out.print("X");
                } else if (board[i][j] == 2) {
                    System.out.print("O");
                }
                if (j < BOARD_SIZE - 1) {
                    System.out.print("|");
                }
            }
            System.out.println();
            if (i < BOARD_SIZE - 1) {
                System.out.println("  -----");
            }
        }
    }

    private static boolean makeMove(int row, int col) {
        if (row < 0 || row >= BOARD_SIZE || col < 0 || col >= BOARD_SIZE) {
            return false;
        }
        if (board[row][col] != 0) {
            return false;
        }
        board[row][col] = currentPlayer;
        int[] move = {row, col};
        Node newNode = new Node(move);
        newNode.next = moveStackTop;
        moveStackTop = newNode;
        return true;
    }

    private static boolean checkForWin() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != 0) {
                return true;
            }
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != 0) {
                return true;
            }
        }
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != 0) {
            return true;
        }
        if (board[2][0] == board[1][1] && board[1][1] == board[0][2] && board[2][0] != 0) {
            return true;
        }
        return false;
    }

    private static boolean checkForDraw() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }
}

