package cs2114.restaurant;

import java.util.Iterator;
import java.util.NoSuchElementException;

//-------------------------------------------------------------------------
/**
 * A circular, doubly-linked list.
 *
 * @param <E> the type of element stored in the list
 *
 * @author jackrg94
 * @version 2015.08.05
 */
public class CircularLinkedList<E> implements CircularList<E>
{
    //~ Fields ................................................................

    private Node<E> currentNode;
    private int size;


    //~ Constructors ..........................................................
    /**
     * Create a new CircularLinkedList object.
     */
    public CircularLinkedList()
    {
        currentNode = null;
        size = 0;
    }


    //~ Public methods ........................................................

    // ----------------------------------------------------------
    /**
     * Moves to the next node in the list.
     */
    public void next()
    {
        if (size > 0) {
            currentNode = currentNode.next();
        }
    }


    // ----------------------------------------------------------
    /**
     * Moves to the previous node in the list.
     */
    public void previous()
    {
        if (size > 0) {
            currentNode = currentNode.previous();
        }
    }


    // ----------------------------------------------------------
    /**
     * Returns the data at the current node location.
     * @return  data at location
     */
    public E getCurrent()
    {
        if (currentNode != null) {
            return currentNode.data();
        }
        else {
            throw new NoSuchElementException(
                "The List is Empty, no data to retrieve.");
        }
    }


    // ----------------------------------------------------------
    /**
     * Returns the number of items in the list
     * @return      size of list
     */
    public int size()
    {
        return size;
    }


    // ----------------------------------------------------------
    /**
     * adds a new item to the list before the current node and increments
     * the size.
     * @param item  data to add to list
     */
    public void add(E item)
    {
        Node<E> newNode = new Node<E>(item);
        //if list is empty, add at current location
        if (size == 0) {
            currentNode = newNode;
            currentNode.join(newNode);
        }
        //if list isn't empty, add node to the current location's predecessor
        //joins the previous node with the new node and the new node to the
        //current node. then preceeds to move the current loc. to the new node.
        else {
            Node<E> previous = currentNode.previous();
            previous.split();
            previous.join(newNode);
            newNode.join(currentNode);
            currentNode = newNode;
        }
        size++;
    }


    // ----------------------------------------------------------
    /**
     * Removes the current node from the list, returning the data that was
     * located at that location.
     * @return      the data at the point of removal
     */
    public E removeCurrent()
    {
        //if list is empty, throws meaningful exception
        if (size == 0) {
            throw new NoSuchElementException(
                "The list is empty. Cannot remove non-existant item");
        }
        // is list isn't empty, removes node at current location by
        // splitting the previous and current, then joining current's old
        // next with previous. resets current to old next and returns the data
        // at old current. then decrements size
        else {
            Node<E> previous = currentNode.previous();
            Node<E> next = currentNode.next();
            E data = currentNode.data();
            previous.split();
            currentNode.split();
            previous.join(next);
            currentNode = next;
            size--;
            return data;
        }
    }


    // ----------------------------------------------------------
    /**
     * Removes all items from the list
     */
    public void clear()
    {
        currentNode = null;
        size = 0;
    }


    // ----------------------------------------------------------
    /**
     * Takes all Node's in list and retrieves their data to output as a string
     * separated by commas and surrounded by brackets.
     * @return      string of data to return
     */
    public String toString()
    {
        //if list is empty, just return brackets
        if (size == 0) {
            return "[]";
        }
        // if list isn't empty, iterate through list, get data and add it
        // to return string
        else {
            String str = "";
            Node<E> holdCurrent = currentNode;
            for (int i = 0; i < size - 1; i++) {
                str += holdCurrent.data() + ", ";
                holdCurrent = holdCurrent.next();
            }
            //the last node should not have comma and space afterwards
            //this is why for loop is decremented by one.
            str += holdCurrent.data();
            return "[" + str + "]";
        }
    }


    // ----------------------------------------------------------
    /**
     * this iterator method
     * @return Iterator<E>  the circularlinkedlistiterator
     */
    public Iterator<E> iterator()
    {
        return new CircularLinkedListIterator();
    }


    //~ Inner classes .........................................................
    /**
     * // --------------------------------------------------------------------
    /**
     *  Iterator for the linked list circular class.
     *
     *  @author Jack Guttman
     *  @version Aug 5, 2015
     */

    // ----------------------------------------------------------
    private class CircularLinkedListIterator implements Iterator<E>
    {
        //~ Fields ............................................................
        private Node<E> iterator1;
        private boolean currentNodeReached;


        //~ Constructors ......................................................

        // ----------------------------------------------------------
        /**
         * constructor for the list iterator
         * sets the start as the current node and keeps track
         * of a loop around the circular list using a boolean
         */
        public CircularLinkedListIterator()
        {
            iterator1 = currentNode;
            currentNodeReached = false;
        }


        //~ Public methods ....................................................

        // ----------------------------------------------------------
        /**
         * creates a hasNext method for list
         * @return    true if there is a next iteration
         */
        public boolean hasNext()
        {
            if (iterator1 != null) {
                if (!currentNodeReached) {
                    return true;
                }

                return iterator1 != currentNode;
            }
            return false;
        }


        // ----------------------------------------------------------
        /**
         * creates a next method for the list
         * @return  null for empty list
         */
        public E next()
        {
            if (hasNext()) {
                E data = iterator1.data();
                iterator1 = iterator1.next();
                currentNodeReached = true;
                return data;
            }
            return null;
        }


        // ----------------------------------------------------------
        /**
         * creates a remove method for list
         */
        public void remove()
        {
            throw new UnsupportedOperationException(
                    "List is empty");
        }
    }
}
