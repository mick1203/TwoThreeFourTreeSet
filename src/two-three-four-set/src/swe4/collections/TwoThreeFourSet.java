/** 
 * Class for the 6th programming exercise.
 * The general purpose of this class is to create a data structure
 * that resembles a tree. The difficulty lies in making sure that
 * the tree is always balanced. 
 * @author  Michael Burgstaller
 * @version 1.0
 * @since   1.0
*/

package swe4.collections;

import  java.util.Iterator;

/**
 * Interface that builds the basis for this excercise.
 * @since   1.0
 */
public interface SortedSet<T> extends Iterable<T> {
    
    /**
     * Adds another element to the set.
     * @return  Boolean value, specifies whether insertion was successful or 
     * not.
     * @since   1.0
     */
    boolean add(T elem);
    
    /**
     * Gets an element of the set that is equal to the given one.
     * If there is no equal element this function returns null
     * @return  Reference to the same element in the set, null if no equal 
     * element.
     * @since   1.0
     */
    T   get(T elem);

    /**
     * Checks wether or not a given element is in the set.
     * @return  Boolean value, true if the element is in the set, false if
     * otherwise.
     * @since   1.0
     */
    boolean contains(T elem);

    /**
     * Counts the number of elements in the set
     * @return  Int value, specifying the number of elements in the set.
     */
    int size();

    /**
     * Gets the smallest element in the set.
     * @return  Reference to the smallest element in the set.
     * @since   1.0
     */
    T   first();

    /**
     * Gets the biggest element in the set.
     * @return  Reference to the biggest element in the set.
     * @since   1.0
     */
    T   last();

    /**
     * Gets the comparator of the set or null if the standard sorting order
     * is used.
     * @return  Comparator or null in case of standard sorting order
     * @since   1.0
     */
    Comparator<T>   comparator();

    /**
     * Gets the iterator that iterates over the set in order.
     * @return  Iterator, iterating in order over the set
     * @since   1.0
     */
    Iterator<T> iterator();

}

/**
 * Interface that extends the basis.
 * @since   1.0
 */
public interface SortedTreeSet<T> extends SortedSet<T> {
    
    /**
     * Gets the height of the tree.
     * @return  Int value, specifying the height of the tree
     * @since   1.0
     */
    int height();
}

/**
 * Class that implements a balanced tree. Otherwise called
 * a two-three-four tree because of its variable number of
 * pointers and landmark values.
 * @since   1.0
 */
public class TwoThreeFourSet<T> implements SortedTreeSet<T> {
    private class Node<T> {
        /**
         * Array of subtrees, can contain up to three subtrees as
         * specified in the instructions
         * 
         */
        private Node[]  subtrees    = new Node[3];
        private T[]     landmarks   = new T[4];

        public Node() {

        };

    }

    public TwoThreeFourSet() {
        
    }
}