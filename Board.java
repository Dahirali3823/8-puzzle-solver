import java.lang.Math;
import java.util.ArrayList;
import java.util.List;
import java.lang.Object;

public class Board {
    private int[][] board;

    public Board(int[][] tiles) { // construct a board from an N-by-N array of tiles
        int n = tiles.length;
        board = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = tiles[i][j];
            }
        }

    }

    public int hamming() {// return number of blocks out of place
        int sum = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != 0 && board[i][j] != i * board.length + j + 1) { // board[2][2] = i * board.length + j
                                                                                   // + 1
                    sum++; // 9 = 2*3 + 2 + 1
                }
            }
        }
        return sum;
    }

    public int manhattan() { // return sum of Manhattan distances between blocks and goal
        int sum = 0;

        // need to get the current position and their goal position
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] != 0) {
                    int goalrow = (board[i][j] - 1) / board.length;
                    int goalcolumn = (board[i][j] - 1) % board.length;
                    sum += (Math.abs(i - goalrow)) + (Math.abs(j - goalcolumn));

                }
            }
        }
        return sum;
    }

    public boolean equals(Object y) { // does this board equal y
        Board second = (Board) y;
        if (y == null || !(y instanceof Board)) return false;
        if (this.board.length != second.board.length) {
            return false;
        }
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board.length; j++) {
                if (this.board[i][j] != second.board[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    public int[][] copyBoard() {
        int[][] value = new int[board.length][board.length]; // create 2d array same size as board array
        // copy values over
        for (int x = 0; x < board.length; x++) {
            value[x] = board[x].clone();
        }
        return value;
    }

    public Iterable<Board> neighbors() { // return an Iterable of all neighboring board positions
        List<Board> neighbors = new ArrayList<>();
        int N = board.length;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == 0) {
                    if (i + 1 < N) { // down
                        int[][] value = copyBoard();


                        // 1 2 4
                        // 3 0 8 value[i][j]
                        // 5 6 7 value[i+1][j]
                        // swap
                        int temp = value[i][j];
                        value[i][j] = value[i + 1][j]; 
                        value[i + 1][j] = temp; 

                        neighbors.add(new Board(value));
                    }
                    if (i - 1 >= 0) { // up
                        int[][] value = copyBoard();

                        int temp = value[i][j];
                        value[i][j] = value[i - 1][j]; 
                        value[i - 1][j] = temp; 

                        neighbors.add(new Board(value));
                    }
                    if (j + 1 < N) { // right
                        int[][] value = copyBoard();

                        int temp = value[i][j];
                        value[i][j] = value[i][j+1]; 
                        value[i][j+1] = temp; 
                        neighbors.add(new Board(value));
                    }
                    if (j - 1 >= 0) { // left
                        int[][] value = copyBoard();

                        // swap
                        int temp = value[i][j];
                        value[i][j] = value[i][j-1]; 
                        value[i][j-1] = temp; 

                        neighbors.add(new Board(value));
                    }
                }

            }
        }

        return neighbors;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("");
        for(int i = 0; i< board.length; i++){
            for(int j = 0; j < board.length; j++){

                if (j == board[i].length-1){
                    sb.append(board[i][j] + "\n");
                }
                else{
                    sb.append(board[i][j] + " ");
                }
            }
        }
        return sb.toString();
    } // return a string representation of the board

    // test client
    public static void main(String[] args) {
        int[][] tiles = {
                { 3, 4, 5 },
                { 0, 7, 6 },
                { 8, 2, 1 }
        };
        int[][] vas = {
                { 3, 4, 5 },
                { 0, 7, 6 },
                { 8, 2, 1 }
        };
        int [][] test = {
            { 1, 0, 3 },
            { 4, 2, 5 },
            { 6, 7, 8 }
    };

        // Create a new Board object with the test tiles
        Board board = new Board(tiles);
        Board news = new Board(vas);
        Board value = new Board(test);
        System.out.println("Are they equal " + board.equals(news));
        System.out.println("Hamming Distance: " + board.hamming());
        System.out.println("Manhattan Distance:" + value.manhattan());
        System.out.println("These are the neighbors of this tile:");
        for (Board neighbor : board.neighbors()) {
            System.out.println(neighbor);
        }


    }
}
