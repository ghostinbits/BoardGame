public class Board implements BoardADT {

    private int board_size;
    private int empty_positions;  // Minimum empty
    private int max_levels;
    private int current_empty;
    private char[][] theBoard;

    public Board (int board_size, int empty_positions, int max_levels) {
        this.board_size = board_size;
        this.empty_positions = empty_positions;
        this.max_levels = max_levels;

        this.theBoard = new char[this.board_size][this.board_size];  // Board initialisation
        for (char[] row : this.theBoard) {
            java.util.Arrays.fill(row, 'e');
        }
    }

    private static boolean isPrime(int k) {
        for (int i = k - 1; i > 1; i--) {
            if (k % i == 0) {
                return false;
            }
        }
        return (k > 1);
    }

    private static int nearestPrime(int k) {  // returns a prime less than k
        int i = k;
        while (!isPrime(i)) {
            i--;
        }
        return i;
    }

    private String inString() {  // returns theBoard in the form of a string
        StringBuilder boardLayout = new StringBuilder();
        for (int i = 0; i < this.board_size; i++) {
            for (int j = 0; j < this.board_size; j++) {
                boardLayout.append(theBoard[i][j]);
            }
        }
        return boardLayout.toString();
    }

    public int countEmpty() {
        return this.inString().length() - this.inString().replace("e", "").length();
    }

    public Dictionary makeDictionary() {
        return new Dictionary(nearestPrime(10000));
    }

    public int repeatedLayout(Dictionary dict) {
        Layout layout = new Layout(this.inString(), 0);
        if (dict.has(this.inString())) {
            return dict.getScore(layout.getBoardLayout());
        }
        else {
            return -1;
        }
    }

    public void storeLayout(Dictionary dict, int score) {
        Layout layout = new Layout(this.inString(), score);
        try {
            dict.put(layout);
        } catch (DictionaryException e) {
            e.printStackTrace();
        }
    }

    public void saveTile(int row, int col, char symbol) {
        this.theBoard[row][col] = symbol;
    }

    public boolean isComputerTile(int row, int column) {
        return this.theBoard[row][column] == 'c';
    }

    public boolean isHumanTile(int row, int column) {
        return this.theBoard[row][column] == 'h';
    }

    public boolean positionIsEmpty(int row, int col) {
        return this.theBoard[row][col] == 'e';
    }

    public boolean positionHasAdjacent(int row, int col, char symbol) {

        boolean hasAdjacent = false;  // Assume no adjacent [symbol] tile
        for (int i = row - 1; i <= row + 1; i++) {
            if (i >= 0 && i < this.board_size) {  // Skip out-of-bound searches
                for (int j = col - 1; j <= col + 1; j++) {
                    if (j >= 0 && j < this.board_size) {  // Skip out-of-bound searches
                        // one adjacent tile changes the logical expression to true
                        hasAdjacent = hasAdjacent || this.theBoard[i][j] == symbol;
                    }
                }
            }
        }
        return hasAdjacent;
    }

    public boolean isDraw(char symbol, int empty_positions) {
        if (winner('c') || winner('h')) {  // either wins
            return false;
        }
        else if (this.empty_positions == 0) {
            return true;
        }
        else if (this.empty_positions > 0 && this.countEmpty() == this.empty_positions) {
            boolean draw = true;  // similar logic to positionHasAdjacent()

            // searches for empty positions
            for (int i = 0; i < this.board_size; i++) {
                for (int j = 0; j < this.board_size; j++) {
                    if (positionIsEmpty(i, j)) {
                        // game is draw if and only if every empty position has no adjacent [symbol] tiles
                        draw = draw && !this.positionHasAdjacent(i, j, symbol);
                    }
                }
            }
            return draw;
        }
        return false;
    }

    public boolean winner(char symbol) {
        for (int i = 0; i < this.board_size; i++) {  // Row has same tiles
            boolean rowSame = true;
            for (int j = 0; j < this.board_size; j++) {
                rowSame = rowSame && (this.theBoard[i][0] == this.theBoard[i][j]);
            }
            if (rowSame) {
                return this.theBoard[i][0] == symbol;
            }
        }

        for (int j = 0; j < this.board_size; j++) {  // Column has same tiles
            boolean colSame = true;
            for (int i = 0; i < this.board_size; i++) {
                colSame = colSame && (this.theBoard[0][j] == this.theBoard[i][j]);
            }
            if (colSame) {
                return this.theBoard[0][j] == symbol;
            }
        }

        boolean diagSame = true;  // diagonal has same tiles
        for (int i = 0; i < this.board_size; i++) {
            diagSame = diagSame && (this.theBoard[0][0] == this.theBoard[i][i]);
        }
        if (diagSame) {
            return this.theBoard[0][0] == symbol;
        }

        boolean tdiagSame = true;  // diagonal of the transpose of the board has same tiles
        for (int i = 0; i < this.board_size; i++) {
            tdiagSame = tdiagSame && (this.theBoard[0][this.board_size - 1] == this.theBoard[i][this.board_size - i - 1]);
        }
        if (tdiagSame) {
            return this.theBoard[0][this.board_size - 1] == symbol;
        }

        return false;  // fallback
    }

    public int evaluate(char symbol, int empty_positions) {
        if (this.winner('c')) {
            return 3;
        }
        else if (this.winner('h')) {
            return 0;
        }
        else {
            return (this.isDraw(symbol, empty_positions)) ? 2 : 1;
        }
    }
}
