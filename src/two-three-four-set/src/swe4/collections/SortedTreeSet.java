package swe4.collections;

/** 
 * Interface that extends the previously declared SortedSet<T> interface.
 * @author  Michael Burgstaller
 * @version 1.0
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
