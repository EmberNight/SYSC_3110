public class Node {

    private Player value;
    private Node nextNode;

    public Node(Player value) {
        this.value = value;
    }
    public Node getNextNode() {
        return nextNode;
    }
    public void setNextNode(Node newNode)
    {
        nextNode = newNode;
    }
    public Player getValue() {
        return value;
    }

}
