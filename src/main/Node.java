public class Node {

    private String value;
    private Node nextNode;

    public Node(String value) {
        this.value = value;
    }
    public Node getNextNode() {
        return nextNode;
    }
    public void setNextNode(Node newNode)
    {
        nextNode = newNode;
    }
    public String getValue() {
        return value;
    }

}
