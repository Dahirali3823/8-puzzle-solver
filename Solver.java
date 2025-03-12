import java.util.PriorityQueue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import java.util.Comparator;
import java.util.LinkedList;

public class Solver {

    private static class SearchNode {
        private Board board;
        private int moves;
        private SearchNode previous;
        private int priority;

        public SearchNode(Board board, int moves, SearchNode previous) {
            // Constructor code here
            this.board = board;
            this.moves = moves;
            this.previous = previous;
            this.priority = board.manhattan() + moves;
        }
    }

    private SearchNode solutionNode;

    public Solver(Board initial) {
        // ensure the board exists
        if (initial == null) {
            throw new IllegalArgumentException("Board can't be null");
        }

        if (!isSolvable(initial)) {
            solutionNode = null;
            return;
        }
        // create a priorty queue of search nodes(min heap) ensuring the lowest priorty
        // node is in front
        PriorityQueue<SearchNode> pq = new PriorityQueue<>(Comparator.comparingInt(node -> node.priority));

        // add the first board to the priority queue with search node of zero moves and
        // no prev
        pq.add(new SearchNode(initial, 0, null));

        // ensure pq isn't empty while adding nodes/finding solution
        while (!pq.isEmpty()) {
            // get the current lowest node
            SearchNode current = pq.poll(); // get lowest priority node

            // if the current board's hamming is 0(all blocks in place) that is solutionnode
            if (current.board.hamming() == 0) {
                solutionNode = current;
                break;
            }
            // loop through neighbors of current and ensure they havent been visited and add
            // them to the priorty queue
            for (Board neighbor : current.board.neighbors()) {
                if (current.previous != null && neighbor.equals(current.previous.board)) {
                    continue;
                }
                pq.add(new SearchNode(neighbor, current.moves + 1, current));

            }

        }
    }

    public int moves() {
        // Method code here
        return (solutionNode != null) ? solutionNode.moves : -1;

    }

    public boolean isSolvable(Board initial) {
        int copyboard[][] = initial.copyBoard();
        int n = copyboard.length;
        int flattend[] = new int[n * n - 1];
        int index = 0;
        int zero = -1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int curr = copyboard[i][j];
                if (curr == 0) {
                    zero = i;
                } else {
                    flattend[index++] = curr;
                }
            }
        }

        int inversions = Countinversions(flattend);

        if (n % 2 == 1) {
            return inversions % 2 == 0; // odd: returns true if inversions are even
        } else {
            // If n is even, solvability depends on both inversions and the row of the blank
            // Even grid: solvable if the sum of inversions and the blank row (from the
            // bottom) is even
            int blankRowFromBottom = n - zero - 1; // Calculate blank row position from the bottom
            return (inversions + blankRowFromBottom) % 2 == 0;
        }

    }

    public int Countinversions(int arr[]) {
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    count++;
                }
            }
        }

        return count;
    }

    public Iterable<Board> solution() {
        if (solutionNode == null) {
            return null;
        }
        LinkedList<Board> path = new LinkedList<>();
        for (SearchNode node = solutionNode; node != null; node = node.previous) {
            path.addFirst(node.board);
        }
        return path;
    }

    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Usage: java Solver <filename>");
            return;
        }
        String filename = args[0];
        try (Scanner scanner = new Scanner(new File(filename))) {
            int n = scanner.nextInt();
            int tile[][] = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    tile[i][j] = scanner.nextInt();
                }
            }
            Board board = new Board(tile);
            Solver solve = new Solver(board);
            if (solve.moves() == -1) {
                System.out.println("Board is not solvable");
            } else {
                System.out.println("Minimum moves: " + solve.moves());
                for (Board neighbor : solve.solution()) {
                    System.out.println(neighbor);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        }

        // default test code
        /*
         * int test = 2;
         * if (test == 1) {
         * int[][] tiles = {
         * { 0, 1, 3 },
         * { 4, 2, 5 },
         * { 7, 8, 6 }
         * };
         * Scanner sc = new Scanner(System.in);
         * Board value = new Board(tiles);
         * Solver solver = new Solver(value);
         * if (!solver.isSolvable()) {
         * System.out.println("Board is not solvable");
         * } else {
         * System.out.println("Minimum moves: " + solver.moves());
         * for (Board board : solver.solution()) {
         * System.out.println(board);
         * }
         * }
         * }
         */
    }
}
