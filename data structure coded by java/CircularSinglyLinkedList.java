import java.util.NoSuchElementException;
/**
 * Your implementation of a CircularSinglyLinkedList without a tail pointer.
 *
 * @author Yiyeon Kim
 * @version 1.0
 * @userid ykim879
 * @GTID 903550379
 *
 * Collaborators: myself
 *
 * Resources: lecture
 */
public class CircularSinglyLinkedList<T> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private CircularSinglyLinkedListNode<T> head;
    private int size;

    /*
     * Do not add a constructor.
     */

    /**
     * Adds the data to the specified index.
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new data
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The index is out of bound");
        } else if (data == null) {
            throw new IllegalArgumentException("The given data should not be empty");
        } else if (isEmpty()) {
            CircularSinglyLinkedListNode<T> newNode = new CircularSinglyLinkedListNode<>(data, null);
            newNode.setNext(newNode);
            head = newNode;
        } else {
            CircularSinglyLinkedListNode<T> newNode = new CircularSinglyLinkedListNode<>(data, null);
            if (index == 0 || index == size) {
                newNode.setNext(head.getNext());
                head.setNext(newNode);
                newNode.setData(head.getData());
                head.setData(data);
                if (index == size) {
                    head = head.getNext();
                }
            } else {
                CircularSinglyLinkedListNode<T> current = head;
                for (int i = 0; i < index - 1; i++) {
                    current = current.getNext();
                }
                newNode.setNext(current.getNext());
                current.setNext(newNode);
            }
        }
        size++;
    }

    /**
     * Adds the data to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        addAtIndex(0, data);
    }

    /**
     * Adds the data to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {

        addAtIndex(size, data);
    }

    /**
     * Removes and returns the data at the specified index.
     *
     * Must be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        T result = null;
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index is out of the bound");
        } else if (size == 1) {
            result = head.getData();
            head = null;
        } else {
            if (index == 0) {
                result = head.getData();
                CircularSinglyLinkedListNode<T> nextNode = head.getNext();
                head.setData(nextNode.getData());
                head.setNext(nextNode.getNext());
            } else {
                CircularSinglyLinkedListNode<T> current = head;
                for (int i = 0; i < index - 1; i++) {
                    current = current.getNext();
                }
                result = current.getNext().getData();
                current.setNext(current.getNext().getNext());
            }
        }
        size--;
        return result;
    }

    /**
     * Removes and returns the first data of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (isEmpty()) {
            throw new NoSuchElementException("It is an empty list");
        }
        return removeAtIndex(0);
    }

    /**
     * Removes and returns the last data of the list.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (isEmpty()) {
            throw new NoSuchElementException("It is an empty list");
        }
        return removeAtIndex(size - 1);
    }

    /**
     * Returns the data at the specified index.
     *
     * Should be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("the index is out of the bound");
        } else {
            CircularSinglyLinkedListNode<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
            return current.getData();
        }
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(n).
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        T result = null;
        CircularSinglyLinkedListNode<T> current = head;
        CircularSinglyLinkedListNode<T> beforelastOccurence = null;
        if (data == null) {
            throw new IllegalArgumentException("This is an empty list");
        } else if (size == 1 && head.getData() == data) {
            result = head.getData();
            head = null;
            size--;
            return result;
        } else {
            if (current != null) {
                if (current.getData() == data) {
                    beforelastOccurence = current;
                }
                for (int i = 0; i < size - 1; i++) {
                    if (current.getNext().getData() == data) {
                        beforelastOccurence = current;
                    }
                    current = current.getNext();
                }
            }
        }
        if (beforelastOccurence == null) {
            throw new NoSuchElementException("There is no such element");
        } else if (beforelastOccurence == head && beforelastOccurence.getNext().getData() != data) {
            head.setData(head.getNext().getData());
            head.setNext(head.getNext().getNext());
            result = beforelastOccurence.getData();
        } else {
            result = beforelastOccurence.getNext().getData();
            beforelastOccurence.setNext(beforelastOccurence.getNext().getNext());
        }
        size--;
        return result;
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return the array of length size holding all of the data (not the
     * nodes) in the list in the same order
     */
    public T[] toArray() {
        T[] result = (T[]) new Object[size];
        CircularSinglyLinkedListNode<T> current = head;
        for (int i = 0; i < size; i++) {
            result[i] = current.getData();
            current = current.getNext();
        }
        return result;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public CircularSinglyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
