public class Dictionary implements DictionaryADT {
    private LinkedList[] storage;
    private int size;
    private final int x = 33;

    public Dictionary(int size) {
        this.size = size;
        this.storage = new LinkedList[size];
        java.util.Arrays.fill(this.storage, null);
    }

    public int put(Layout data) throws DictionaryException {
        if (this.has(data.getBoardLayout())) {
            throw new DictionaryException("Data already exist.");
        }
        else {
            int position = this.polynomialHashFunction(data.getBoardLayout(), this.x, size);
            if (this.storage[position] == null) {
                this.storage[position] = new LinkedList();
            }
            this.storage[position].add(data);
            return (this.storage[position].getLength() == 1) ? 0 : 1;
        }
    }

    public void remove(String boardLayout) throws DictionaryException {
        if (!this.has(boardLayout)) {
            throw new DictionaryException("No such data.");
        }
        else {
            int position = this.polynomialHashFunction(boardLayout, this.x, size);
            this.storage[position].delete(boardLayout);
        }
    }

    public int getScore(String boardLayout) {
        if (!this.has(boardLayout)) {
            return -1;
        }
        else {
            int position = this.polynomialHashFunction(boardLayout, this.x, size);
            this.storage[position].search(boardLayout);
            return this.storage[position].getTarget().getValue().getScore();
        }
    }

    public boolean has(String boardLayout) {
        int position = this.polynomialHashFunction(boardLayout, this.x, size);
        if (this.storage[position] == null) {
            return false;
        }
        else {
            return this.storage[position].search(boardLayout);
        }
    }

    private int polynomialHashFunction(String boardLayout, int x, int M) {
        x = this.x;
        int u = boardLayout.charAt(boardLayout.length() - 1);

        for (int i = boardLayout.length() - 2; i > -1; i--) {
            u = (u * x + boardLayout.charAt(i)) % M;
        }

        return u;
    }

}
