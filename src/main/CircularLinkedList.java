public class CircularLinkedList {
    private Node head = null;
    private Node tail = null;

    public void addNode(String value){
        Node newNode = new Node(value);
        if(head == null) {
            head = newNode;
        } else {
            tail.setNextNode(newNode);
        }

        tail = newNode;
        tail.setNextNode(head);
    }
    public void deleteNode(String value)
    {
        Node currentNode = head;
        if(currentNode.getValue().equals(value)){
            head = head.getNextNode();
            tail.setNextNode(head);
        }
        else{
            do {
                Node nextNode = currentNode.getNextNode();
                if(nextNode.getValue().equals(value))
                {
                    currentNode.setNextNode(nextNode.getNextNode());
                    break;
                }
            }while(currentNode!= head);
        }
    }

    public boolean hasNode(String searchValue){
        Node currentNode = head;
        if(head == null)
        {
            return false;
        }
        else{
            do{
                if(currentNode.getValue().equals(searchValue)){
                    return true;
                }
            }while(currentNode!= head);
            return false;
        }
    }
}
