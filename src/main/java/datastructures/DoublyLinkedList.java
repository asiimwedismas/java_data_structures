package datastructures;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class DoublyLinkedList<T> implements Iterable<T> {

    private int size = 0;
    private Node<T> head = null;
    private Node<T> tail = null;

    private static class Node<T> {
        private T data;
        private Node<T> prev, next;

        public Node(T data, Node<T> prev, Node<T> next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }

        @Override
        public String toString() {
            return data.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node<?> node = (Node<?>) o;
            return Objects.equals(data, node.data) &&
                    Objects.equals(prev, node.prev) &&
                    Objects.equals(next, node.next);
        }

        @Override
        public int hashCode() {
            return Objects.hash(data, prev, next);
        }
    }


    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public void clear() {
        Node<T> currentNode = head;
        while (currentNode != null) {
            Node<T> nextNode = currentNode.next;
            currentNode.prev = currentNode.next = null;
            currentNode.data = null;
            currentNode = nextNode;
        }

        head = tail = null;
        size = 0;
    }

    public void addLast(T elem) {
        if (isEmpty())
            head = tail = new Node<>(elem, null, null);
        else {
            Node<T> newNode = new Node<>(elem, tail, null);
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    public void addFirst(T elem) {
        if (isEmpty())
            head = tail = new Node<>(elem, null, null);
        else {
            Node<T> newNode = new Node<>(elem, null, head);
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    // Remove the first value at the head of the linked list, O(1)
    public T removeFirst() {
        T data;
        if (head == null)
            throw new IllegalStateException("Empty list");

        data = head.data;
        Node<T> nextNode = head.next;

        if (size == 1) {
            head = tail = null;
        } else {
            head.next = null;
            nextNode.prev = null;
            head = nextNode;
        }

        size--;
        return data;
    }

    // Remove the last value at the tail of the linked list, O(1)
    public T removeLast() {
        T data;

        if (tail == null)
            throw new IllegalStateException("Empty list");

        data = tail.data;
        Node<T> previousNode = tail.prev;

        if (size == 1) {
            head = tail = null;
        } else {
            tail.prev = null;
            previousNode.next = null;
            tail = previousNode;
        }

        size--;
        return data;
    }

    public void add(T elem) {
        addLast(elem);
    }

    // Add an element at a specified index
    public void addAt(int index, T data) throws Exception {
        if (index == 0) {
            addFirst(data);
            return;
        }

        if (index == size) {
            addLast(data);
            return;
        }

        if (!isIndexValid(index))
            throw new IndexOutOfBoundsException();

        Node<T> firstBreakNode = head;
        for (int counter = 0; counter != index - 1; counter++)
            firstBreakNode = firstBreakNode.next;
        Node<T> secondBreakPoint = firstBreakNode.next;

        Node<T> newNode = new Node<>(data, firstBreakNode, secondBreakPoint);
        firstBreakNode.next = newNode;
        secondBreakPoint.prev = newNode;

        size++;
    }

    private boolean isIndexValid(int index) {
        return index >= 0 && index < size;
    }

    // Check the value of the first node if it exists, O(1)
    public T peekFirst() {
        if (head != null)
            return head.data;
        else
            throw new IllegalArgumentException("Empty Linked list");
    }

    // Check the value of the last node if it exists, O(1)
    public T peekLast() {
        if (head != null)
            return tail.data;
        else
            throw new IllegalArgumentException("Empty Linked list");
    }


    // Remove an arbitrary node from the linked list, O(n)
    private T remove(Node<T> node) {
        if (isEmpty())
            throw new IllegalStateException("Empty list");

        if (node.next == null)
            return removeLast();

        if(node.prev == null)
            return removeFirst();

        node.prev.next = node.next;
        node.next.prev = node.prev;

        node.prev = node.next = null;
        size--;

        return node.data;
    }

    // Remove a node at a particular index, O(n)
    public T removeAt(int index) {
        if (isEmpty())
            throw new IllegalArgumentException("Empty list");

        if (!isIndexValid(index))
            throw new IndexOutOfBoundsException();

        if (index == size - 1)
            return removeLast();

        if(index == 0)
            return removeFirst();

        Node<T> nodeToDelete = head;
        for (int counter = 0; counter < index; counter++)
            nodeToDelete = nodeToDelete.next;


        return remove(nodeToDelete);
    }

    // Remove a particular value in the linked list, O(n)
    public boolean remove(Object obj) {
        boolean removeNow;
        for (Node<T> nodeToDelete = head; nodeToDelete != null; nodeToDelete = nodeToDelete.next) {
            if (obj == null) {
                removeNow = nodeToDelete.data == null;
            } else {
                removeNow = obj.equals(nodeToDelete.data);
            }

            if(removeNow){
                remove(nodeToDelete);
                return true;
            }
        }
        return false;
    }

    // Find the index of a particular value in the linked list, O(n)
    public int indexOf(Object obj) {
        int index = 0;
        for (Node<T> currentNode = head; index < size; index++, currentNode = currentNode.next) {
            if (obj == null) {
                if (currentNode.data == null) return index;
            } else {
                if (obj.equals(currentNode.data)) return index;
            }
        }

        return -1;
    }

    // Check is a value is contained within the linked list
    public boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> currentNode = head;

            @Override
            public boolean hasNext() {
                return currentNode.next != null;
            }

            @Override
            public T next() {
                T data = currentNode.data;
                currentNode = currentNode.next;
                return data;
            }
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        Node<T> trav = head;
        while (trav != null) {
            sb.append(trav.data);
            if (trav.next != null) {
                sb.append(", ");
            }
            trav = trav.next;
        }
        sb.append(" ]");
        return sb.toString();
    }
}
