import java.util.NoSuchElementException;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;
import java.util.Collection;
/**
 * Your implementation of a BST.
 *
 * @author Yiyeon Kim
 * @version 1.0
 * @userid ykim879
 * @GTID 903550379
 *
 * Collaborators: did it by myself
 *
 * Resources: lectures
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is a null");
        }
        for (T temp : data) {
            add(temp);
        }
        size = data.size();
    }

    /**
     * Adds the data to the tree.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Given data is a null");
        }
        root = add(data, root);
        size++;
    }
    /**
     * Adds the data to the tree.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * act as helper method for add(T data)
     *
     * @param data the data to add
     * @param current the node it is traversing
     * @return code to add
     */
    private BSTNode<T> add(T data, BSTNode<T> current) {
        if (current == null) {
            return new BSTNode<T>(data);
        }
        if (data.compareTo(current.getData()) == 0) {
            size--;
        } else if (data.compareTo(current.getData()) < 0) {
            current.setLeft(add(data, current.getLeft()));
        } else if (data.compareTo(current.getData()) > 0) {
            current.setRight(add(data, current.getRight()));
        }
        return current;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data can't be null");
        }
        BSTNode<T> resultDummy = new BSTNode<>(null);
        root = remove(data, root, resultDummy);
        size--;
        return resultDummy.getData();
    }
    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * act as helper method for remove(T data)
     * @param data the data to remove
     * @param current the node it is traversing
     * @param result the data to remove
     * @return the data that was removed
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    private BSTNode<T> remove(T data, BSTNode<T> current, BSTNode<T> result) {
        if (current == null) {
            throw new NoSuchElementException("There is no such element");
        }
        if (data.compareTo(current.getData()) > 0) {
            current.setRight(remove(data, current.getRight(), result));
        } else if (data.compareTo(current.getData()) < 0) {
            current.setLeft(remove(data, current.getLeft(), result));
        } else {
            result.setData(current.getData());
            if (current.getLeft() == null && current.getRight() == null) {
                return null;
            } else if (current.getLeft() == null && current.getRight() != null) {
                return current.getRight();
            } else if (current.getLeft() != null && current.getRight() == null) {
                return current.getLeft();
            } else {
                BSTNode<T> dummyNode = new BSTNode<>(null);
                current.setRight(removeSuccessor(current.getRight(), dummyNode));
                current.setData(dummyNode.getData());
            }

        }
        return current;
    }
    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param successor the current node to find the successor
     * @param dummy store successor data
     * @return successor
     */
    private BSTNode<T> removeSuccessor(BSTNode<T> successor, BSTNode<T> dummy) {
        if (successor.getLeft() == null) {
            dummy.setData(successor.getData());
            if (successor.getRight() == null) {
                return null;
            } else {
                return successor.getRight();
            }
        }
        successor.setLeft(removeSuccessor(successor.getLeft(), dummy));
        return successor;
    }
    /**
     * Returns the data from the tree matching the given parameter.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null");
        }
        return get(data, root).getData();
    }
    /**
     * helper to return the data from the tree matching the given parameter.
     * @param data the data to search for
     * @param current current node
     * @return the data in the tree equal to the parameter
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    private BSTNode<T> get(T data, BSTNode<T> current) {
        if (current == null) {
            throw new NoSuchElementException("There's no such element");
        } else if (data.compareTo(current.getData()) < 0) {
            return get(data, current.getLeft());
        } else if (data.compareTo(current.getData()) > 0) {
            return get(data, current.getRight());
        } else {
            return current;
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
        }
        return contains(data, root);
    }
    /**
     * helper to returns whether or not data matching the given parameter is contained
     * within the tree.
     * @param data the data to search for
     * @param current current node
     * @return true if the parameter is contained within the tree, false
     * otherwise
     */
    private boolean contains(T data, BSTNode<T> current) {
        if (current == null) {
            return false;
        }
        if (current.getData().compareTo(data) == 0) {
            return true;
        } else if (current.getData().compareTo(data) < 0) {
            return contains(data, current.getRight());
        } else {
            return contains(data, current.getLeft());
        }
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> list = new LinkedList<>();
        list = preorder(list, root);
        return list;
    }
    /**
     * Helper to generate a pre-order traversal of the tree.
     * @param list preorder list
     * @param current current Node
     * @return the preorder traversal of the tree
     */
    private List<T> preorder(List<T> list, BSTNode<T> current) {
        if (current == null) {
            return list;
        }
        list.add(current.getData());
        list = preorder(list, current.getLeft());
        list = preorder(list, current.getRight());
        return list;
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> list = new LinkedList<>();
        list = inorder(list, root);
        return list;
    }
    /**
     * Helper to generate an in-order traversal of the tree.
     * @param list inorder list
     * @param current current Node
     * @return the inorder traversal of the tree
     */
    private List<T> inorder(List<T> list, BSTNode<T> current) {
        if (current == null) {
            return list;
        }
        list = inorder(list, current.getLeft());
        list.add(current.getData());
        list = inorder(list, current.getRight());
        return list;
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> list = new LinkedList<>();
        list = postorder(list, root);
        return list;
    }
    /**
     * Helper to generate a post-order traversal of the tree.
     *
     * @param list postorder list
     * @param current current node
     * @return the postorder traversal of the tree
     */
    private List<T> postorder(List<T> list, BSTNode<T> current) {
        if (current == null) {
            return list;
        }
        list = postorder(list, current.getLeft());
        list = postorder(list, current.getRight());
        list.add(current.getData());
        return list;
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        Queue<BSTNode<T>> queue = new LinkedList<>();
        List<T> result = new LinkedList<>();
        BSTNode<T> current = root;
        if (size != 0) {
            queue.add(root);
        }
        while (!queue.isEmpty()) {
            if (current.getLeft() != null) {
                queue.add(current.getLeft());
            }
            if (current.getRight() != null) {
                queue.add(current.getRight());
            }
            result.add(queue.remove().getData());
            current = queue.peek();
        }
        return result;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        } else if (root.getLeft() == null && root.getRight() == null) {
            return 0;
        }
        return Math.max(height(root.getLeft()), height(root.getRight())) + 1;
    }
    /**
     * Helper to returns the height of the root of the tree.
     *
     * @param current current Node
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    private int height(BSTNode<T> current) {
        if (current == null) {
            return -1;
        }
        if (current.getRight() == null && current.getLeft() == null) {
            return 0;
        }
        return Math.max(height(current.getLeft()), height(current.getRight())) + 1;
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Generates a list of the max data per level from the top to the bottom
     * of the tree. (Another way to think about this is to get the right most
     * data per level from top to bottom.)
     *
     * This list should contain the last node of each level.
     *
     * If the tree is empty, an empty list should be returned.
     *
     * Ex:
     * Given the following BST composed of Integers
     *      2
     *    /   \
     *   1     4
     *  /     / \
     * 0     3   5
     * getMaxDataPerLevel() should return the list [2, 4, 5] - 2 is the max
     * data of level 0, 4 is the max data of level 1, and 5 is the max data of
     * level 2
     *
     * Ex:
     * Given the following BST composed of Integers
     *               50
     *           /        \
     *         25         75
     *       /    \
     *      12    37
     *     /  \    \
     *   10   15   40
     *  /
     * 13
     * getMaxDataPerLevel() should return the list [50, 75, 37, 40, 13] - 50 is
     * the max data of level 0, 75 is the max data of level 1, 37 is the
     * max data of level 2, etc.
     *
     * Must be O(n).
     *
     * @return the list containing the max data of each level
     */
    public List<T> getMaxDataPerLevel() {
        List<T> list = new ArrayList<>(50);
        return getMaxDataPerLevel(root, 0, list);
    }
    /**
     * Helper to generates a list of the max data per level from the top to the bottom
     * of the tree.
     * @param current current node
     * @param depth depth of current node
     * @param list list with maximum data
     * @return the list containing the max data of each level
     */
    private List<T> getMaxDataPerLevel(BSTNode<T> current, int depth, List<T> list) {
        if (current == null) {
            return list;
        }
        if (list.size() == depth) {
            list.add(depth, current.getData());
        }
        getMaxDataPerLevel(current.getRight(), depth + 1, list);
        getMaxDataPerLevel(current.getLeft(), depth + 1, list);
        return list;
    }
    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
