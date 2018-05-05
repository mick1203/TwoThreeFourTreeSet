package swe4.collections;

import  java.util.Iterator;
import  java.util.Comparator;

/**
 * Interface that specifies how a SortedSet has to be implemented.
 * @author  Michael Burgstaller
 * @since   1.0
 */
public interface SortedSet<T> extends Iterable<T> {
    
    /**
     * Adds another element to the set.
     * @param  elem Element to be added to the set 
     * @return      Boolean value, specifies whether insertion was successful 
     * or not.
     * @since       1.0
     */
    boolean add(T elem);
    
    /**
     * Gets an element of the set that is equal to the given one.
     * If there is no equal element this function returns null
     * @param elem  Element to be searched in the set
     * @return      Reference to the same element in the set, null if no equal 
     * element.
     * @since       1.0
     */
    T   get(T elem);

    /**
     * Checks wether or not a given element is in the set.
     * @param elem  Element to be checked if it exists in the set.
     * @return      Boolean value, true if the element is in the set, 
     * false if otherwise.
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