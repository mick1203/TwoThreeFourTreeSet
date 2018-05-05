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
public class TwoThreeFourSet<T> 
    implements SortedTreeSet<T> {
    
    /**
     * Array of Node objects, can contain up to three subtrees as
     * specified in the instructions
     * 
     */
    private TwoThreeFourSet[] subtrees;

    /**
     * Array of T objects, specifying the value that separates two
     * subtrees
     */
    private T[] landmarks;

    /**
     * Number of landmarks in the object
     */
    private int landmarkCount;

    /**
     * A comparator object for the type T, can be null
     */
    private Comparator<T> c = null;

    /**
     * Compare function wrapper, allows c to be null
     * @param   lhs Object to be compared
     * @param   rhs Object the first one is compared to
     * @return  Returns a negative number, 0 or a positive number
     * depending on the first object being less than, equal, or
     * greater than the second one.
     */
    @SuppressWarnings("unchecked")
    private int compare(T lhs, T rhs) {
        if (c == null) {
            // assume T implements comparable
            return ((Comparable<T>) lhs).compareTo(rhs);
        } else {
            // compare with comparator
            return c.compare(lhs, rhs);
        }
    }

    /**
     * Convert a given value to index of the subtree that value belongs
     * to.
     * @param n The value to be converted
     * @return  Index of the correct subtree
     */
    private int getIndexForValue(T n) {
        int idx = 0;
        while (idx < landmarkCount && compare(n, landmarks[idx]) >= 0) {
            idx++;
        }
        return idx;
    }

    @SuppressWarnings("unchecked")
    public TwoThreeFourSet() {
        subtrees = new TwoThreeFourSet[4];
        landmarks = (T[]) new Object[3];
        landmarkCount = 0;
    }

    public TwoThreeFourSet(Comparator<T> ca) {
        this();
        c = ca;
    }

    @Override
    public boolean add(T elem) {
        // TODO add method
        if (contains(elem)) return false;

        // element hast to be inserted.
        
        


        return true;
    };

    @Override
    @SuppressWarnings("unchecked")
    public T get(T elem) {
        for (int i = 0; i < landmarkCount; ++i) {
            if (compare(elem, landmarks[i]) == 0) {
                return landmarks[i];
            }
        }

        int idx = getIndexForValue(elem);
        TwoThreeFourSet subtree = subtrees[idx];
    
        return subtree == null ? null: (T) subtree.get(elem);
    }

    @Override
    public boolean contains(T elem) {
        return get(elem) != null;
    }

    @Override
    public int size() {
        int n = landmarkCount;
        for (int i = 0; i <= landmarkCount; ++i) {
            TwoThreeFourSet subtree = subtrees[i];
            if (subtree != null) {
                n += subtree.size();
            }
        }
        return n;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T first() {
        TwoThreeFourSet subtree = subtrees[0];
        if (subtree != null) {
            return (T) subtree.first();
        } else {
            return landmarks[0];
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public T last() {
        TwoThreeFourSet subtree = subtrees[landmarkCount];
        if (subtree != null) {
            return (T) subtree.last();
        } else {
            return landmarks[landmarkCount - 1];
        }
    } 

    @Override
    public Comparator<T> comparator() {
        return c;
    };

    @Override
    public Iterator<T> iterator() {
        // TODO Create iterator method
        return null;
    }

    @Override
    public int height() {
        int max = -1;
        for (int i = 0; i < landmarkCount; ++i) {
            TwoThreeFourSet subtree = subtrees[i];
            if (subtree != null) {
                max = Math.max(max, subtree.height());
            }
        }
        return max + 1;
    }

}