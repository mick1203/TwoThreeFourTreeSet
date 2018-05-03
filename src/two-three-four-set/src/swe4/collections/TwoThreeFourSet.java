package swe4.collections;

import java.util.Comparator;
import java.util.Iterator;

/**
 * Class that implements a balanced tree. Otherwise called
 * a two-three-four tree because of its variable number of
 * pointers and landmark values.
 * @author  Michael Burgstaller
 * @version 1.0
 * @since   1.0
 */
public class TwoThreeFourSet<T> implements SortedTreeSet<T> {
    
    @SuppressWarnings("unchecked")
    private class Node<T> {
        /**
         * Array of subtrees, can contain up to three subtrees as
         * specified in the instructions
         * 
         */
        private Node[]  subtrees    = new Node[4];
        private T[]     landmarks   = (T[]) new Object[3];

        public Node() {

        };

    }

    public TwoThreeFourSet() {
        
    }

    @Override
    public boolean add(T elem) {
        // TODO Create add method 
        return true;
    };

    @Override
    public T get(T elem) {
        // TODO Create get method
        return null;
    }

    @Override
    public boolean contains(T elem) {
        // TODO create contains method
        return true;
    }

    @Override
    public int size() {
        // TODO Create size method
        return 0;
    }

    @Override
    public T first() {
        // TODO Create first method
        return null;
    }

    @Override
    public T last() {
        // TODO Create last method
        return null;
    } 

    @Override
    public Comparator<T> comparator() {
        // TODO Create comparator method
        return null;
    };

    @Override
    public Iterator<T> iterator() {
        // TODO Create iterator method
        return null;
    }

    @Override
    public int height() {
        // TODO Create height method
        return 0;
    }

}