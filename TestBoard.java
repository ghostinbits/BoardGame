public class TestBoard {
    public static void main(String[] args) {
        Board board = new Board(3, 1, 5);
        Board computerwinner = new Board(3, 1, 5);
        //board.theBoard = new char[][]{{'c', 'h', 'h'}, {'h', 'c', 'c'}, {'h', 'c', 'e'}};
        //computerwinner.theBoard = new char[][]{{'c', 'h', 'h'}, {'h', 'c', 'c'}, {'h', 'e', 'c'}};

        Dictionary dict = board.makeDictionary();
        System.out.println(board.repeatedLayout(dict));
        board.storeLayout(dict, board.evaluate('c', 1));
        System.out.println(board.repeatedLayout(dict));
        System.out.println(board.isHumanTile(0, 1));
        System.out.println(board.isComputerTile(0, 0));
        System.out.println(board.positionIsEmpty(0, 0));
        System.out.println(board.positionIsEmpty(2, 2));
    }
}
