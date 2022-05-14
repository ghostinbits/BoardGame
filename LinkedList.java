public class LinkedList {

    class Node {
        Layout value = null;
        Node next = null;

        public Layout getValue() {
            return this.value;
        }
    }

    private Node head;
    private Node tail;
    private Node target;
    private Node pre_target;
    private int length;

    public LinkedList() {
        head = new Node();
        tail = head;
        length = 0;
    }

    public void add(Layout value) {
        tail.value = value;
        tail.next = new Node();
        tail = tail.next;
        length++;
    }

    public boolean search(String boardLayout) {
        for (Node i = head, lastI = head; i != null; i = i.next, lastI = i) {

            if (i.value == null) {
                break;
            }
            if (i.value.getBoardLayout().equals(boardLayout)) {
                pre_target = lastI;
                target = i;
                return true;
            }

        }

        return false;
    }

    public boolean delete(String boardLayout) {
        if (search(boardLayout)) {
            pre_target.next = target.next;
            target = null;
            length--;
            return true;
        }
        else {
            return false;
        }
    }

    public Node getTarget() {
        return this.target;
    }

    public int getLength() {
        return this.length;
    }

}
