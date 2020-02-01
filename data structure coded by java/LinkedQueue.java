import java.util.NoSuchElementException;
/**
 * Your implementation of a LinkedQueue. It should NOT be circular.
 *
 * @author Yiyeon KIm
 * @version 1.0
 * @userid ykim879
 * @GTID 903550379
 *
 * Collaborators: myself
 *
 * Resources: lecture
 */
public class LinkedQueue<T> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;

    /*
     * Do not add a constructor.
     */

    /**
     * Adds the data to the back of the queue.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the queue
     * @throws IllegalArgumentException if data is null
     */
    public void enqueue(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is a null");
        }
        LinkedNode<T> newNode = new LinkedNode<>(data, null);
        if (size == 0) {
            head = newNode;
            tail = head;
        } else {
            tail.setNext(newNode);
            tail = newNode;
        }
        size++;
    }

    /**
     * Removes and returns the data from the front of the queue.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the queue
     * @throws NoSuchElementException if the queue is empty
     */
    public T dequeue() {
        if (size == 0) {
            throw new NoSuchElementException("There is no element in the list");
        }
        T result = head.getData();
        head = head.getNext();
        size--;
        if (size == 0) {
            tail = head;
        }
        return result;
    }

    /**
     * Returns the data from the front of the queue without removing it.
     *
     * Must be O(1).
     *
     * @return the data located at the front of the queue
     * @throws NoSuchElementException if the queue is empty
     */
    public T peek() {
        if (size == 0) {
            throw new NoSuchElementException("There is no element");
        }
        return head.getData();
    }

    /**
     * Returns the head node of the queue.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the queue
     */
    public LinkedNode<T> getHead() {
        // DO NOT MODIFY THIS METHOD!
        return head;
    }

    /**
     * Returns the tail node of the queue.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the queue
     */
    public LinkedNode<T> getTail() {
        // DO NOT MODIFY THIS METHOD!
        return tail;
    }

    /**
     * Returns the size of the queue.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the queue
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
