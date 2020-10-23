public class CircularLinkedList {

    private Node head;
    private Node tail;

    public CircularLinkedList() {
        this.head = null;
        this.tail = null;
    }


    public void addNode(Player value) {
        Node newNode = new Node(value);
        if (head == null) {
            head = newNode;
        } else {
            tail.setNextNode(newNode);
        }

        tail = newNode;
        tail.setNextNode(head);
    }

    public void deleteNode(Player value) {
        Node currentNode = head;
        if (currentNode.getValue().equals(value)) {
            head = head.getNextNode();
            tail.setNextNode(head);
        } else {
            do {
                Node nextNode = currentNode.getNextNode();
                if (nextNode.getValue().equals(value)) {
                    currentNode.setNextNode(nextNode.getNextNode());
                    break;
                }
            } while (currentNode != head);
        }
    }

    public boolean hasNode(Player searchValue) {
        Node currentNode = head;
        if (head == null) {
            return false;
        } else {
            do {
                if (currentNode.getValue().getName().equals(searchValue.getName())) {
                    return true;
                }
            } while (currentNode != head);
            return false;
        }
    }

    public Player returnNextNode(Player searchValue) {
        Node currentNode = head;
        do {
            if (currentNode.getValue().getName().equals(searchValue.getName())) {
                return currentNode.getNextNode().getValue();
            }
        } while (currentNode != head);
        return null;
    }

    public Player getHead() {
        return head.getValue();
    }

    public int getSize() {
        Node currentNode = head;
        int count = 1;
        if (head == null) {
            return 0;
        } else {
            do {
                currentNode = currentNode.getNextNode();
                count = count + 1;

            } while (currentNode != head);
            return count;
        }
    }
}


