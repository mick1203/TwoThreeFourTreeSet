package swe4.collections;
import  java.util.Iterator;

/**
 * Class that implements a balanced tree. Otherwise called
 * a two-three-four tree because of its variable number of
 * pointers and landmark values.
 * @author  Michael Burgstaller
 * @version 1.0
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