package Main;

import java.util.Random;
import java.util.Scanner;

class SnakeGame {
    private static final int BOARD_SIZE = 20;
    private static final int NUM_OBSTACLES = 20;
    private static final int SNAKE_INITIAL_LENGTH = 3;

    private static int[][] board;
    private static int snakeLength;
    private static Node head;
    private static Node tail;
    private static int[] food;
    private static boolean gameOver;
    private static Random random;

    private static class Node {
        private int[] data;
        private Node next;

        public Node(int[] data) {
            this.data = data;
        }
    }

    public static void main(String[] args) {
        initializeBoard();
        placeObstacles();
        placeFood();
        placeSnake();

        Scanner scanner = new Scanner(System.in);
        while (!gameOver) {
            printBoard();
            System.out.print("Enter direction (up/down/left/right): ");
            String direction = scanner.nextLine().trim().toLowerCase();
            moveSnake(direction);
            checkForCollisions();
        }
        scanner.close();

        System.out.println("Game over!");
    }

    private static void initializeBoard() {
        board = new int[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = 0;
            }
        }
    }

    private static void placeObstacles() {
        random = new Random();
        for (int i = 0; i < NUM_OBSTACLES; i++) {
            int row = random.nextInt(BOARD_SIZE);
            int col = random.nextInt(BOARD_SIZE);
            if (board[row][col] == 0) {
                board[row][col] = -1;
            } else {
                i--;
            }
        }
    }

    private static void placeFood() {
        random = new Random();
        int row = random.nextInt(BOARD_SIZE);
        int col = random.nextInt(BOARD_SIZE);
        while (board[row][col] != 0) {
            row = random.nextInt(BOARD_SIZE);
            col = random.nextInt(BOARD_SIZE);
        }
        food = new int[]{row, col};
        board[row][col] = 1;
    }

    private static void placeSnake() {
        snakeLength = SNAKE_INITIAL_LENGTH;
        int row = random.nextInt(BOARD_SIZE);
        int col = random.nextInt(BOARD_SIZE);
        while (col + snakeLength >= BOARD_SIZE) {
            col--;
        }
        for (int i = 0; i < snakeLength; i++) {
            int[] segment = new int[]{row, col + i};
            Node newNode = new Node(segment);
            if (i == 0) {
                head = newNode;
            } else {
                tail.next = newNode;
            }
            tail = newNode;
            board[row][col + i] = snakeLength - i;
        }
    }

    private static void printBoard() {
        System.out.println("Score: " + (snakeLength - SNAKE_INITIAL_LENGTH));
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == 0) {
                    System.out.print(" ");
                } else if (board[i][j] == -1) {
                    System.out.print("#");
                } else if (board[i][j] == 1) {
                    System.out.print("*");
                } else {
                    System.out.print("o");
                }
            }
            System.out.println();
        }
    }

    private static void moveSnake(String direction) {
        if (tail == null) {
            gameOver = true;
            return;
        }
        int[] newHeadPos = new int[2];
        int[] oldHeadPos = head.data;
        if (direction.equals("up")) {
            newHeadPos[0] = oldHeadPos[0] - 1;
            newHeadPos[1] = oldHeadPos[1];
        } else if (direction.equals("down")) {
            newHeadPos[0] = oldHeadPos[0] + 1;
            newHeadPos[1] = oldHeadPos[1];
        } else if (direction.equals("left")) {
            newHeadPos[0] = oldHeadPos[0];
            newHeadPos[1] = oldHeadPos[1] - 1;
        } else if (direction.equals("right")) {
            newHeadPos[0] = oldHeadPos[0];
            newHeadPos[1] = oldHeadPos[1] + 1;
        }
        Node newHead = new Node(newHeadPos);
        head.next = newHead;
        head = newHead;
        board[newHeadPos[0]][newHeadPos[1]] = snakeLength;
        if (newHeadPos[0] == food[0] && newHeadPos[1] == food[1]) {
            snakeLength++;
            placeFood();
        } else {
            int[] oldTailPos = tail.data;
            board[oldTailPos[0]][oldTailPos[1]] = 0;
            tail = tail.next;
        }
    }

    private static void checkForCollisions() {
        int[] headPos = head.data;
        if (headPos[0] < 0 || headPos[0] >= BOARD_SIZE || headPos[1] < 0 || headPos[1] >= BOARD_SIZE) {
            gameOver = true;
        } else if (board[headPos[0]][headPos[1]] == -1) {
            gameOver = true;
        } else if (board[headPos[0]][headPos[1]] > 0) {
            gameOver = false;
        }
    }
}
