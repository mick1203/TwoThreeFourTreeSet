package swe4.collections;

import java.util.Comparator;
import java.util.Iterator;

/**
 * Class that implements a balanced tree. Otherwise called
 * a two-three-four tree because of its variable number of
 * pointers and landmark values.
 * @author  Michael Burgstaller
 */
public class TwoThreeFourTreeSet<T> 
    implements SortedTreeSet<T> {

    private Node root;

    private class Node {
        /**
         * Array of Node objects, can contain up to three subtrees as
         * specified in the instructions
         * 
         */
        public TwoThreeFourTreeSet[] subtrees;

        /**
         * Array of T objects, specifying the value that separates two
         * subtrees
         */
        public T[] landmarks;

        /**
         * Number of landmarks in the object
         */
        public int landmarkCount;

        /**
         * A comparator object for the type T, can be null
         */
        public Comparator<T> c = null;

        /**
         * A TwoThreeFourTreeSet
         */
        public TwoThreeFourTreeSet parent;

        @SuppressWarnings("unchecked")
        public Node() {
            subtrees = new TwoThreeFourTreeSet[4];
            landmarks = (T[]) new Object[3];
            landmarkCount = 0;
        }

        public Node(Comparator<T> ca) {
            this();
            c = ca;
        }

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

        public boolean add(T elem) {
            // TODO add method
            if (contains(elem)) return false;

            // element hast to be inserted.
            
            


            return true;
        };

        @SuppressWarnings("unchecked")
        public T get(T elem) {
            for (int i = 0; i < landmarkCount; ++i) {
                if (compare(elem, landmarks[i]) == 0) {
                    return landmarks[i];
                }
            }

            int idx = getIndexForValue(elem);
            TwoThreeFourTreeSet subtree = subtrees[idx];
        
            return subtree == null ? null: (T) subtree.get(elem);
        }

        public boolean contains(T elem) {
            return get(elem) != null;
        }

        public int size() {
            int n = landmarkCount;
            for (int i = 0; i <= landmarkCount; ++i) {
                TwoThreeFourTreeSet subtree = subtrees[i];
                if (subtree != null) {
                    n += subtree.size();
                }
            }
            return n;
        }

        @SuppressWarnings("unchecked")
        public T first() {
            TwoThreeFourTreeSet subtree = subtrees[0];
            if (subtree != null) {
                return (T) subtree.first();
            } else {
                return landmarks[0];
            }
        }

        @SuppressWarnings("unchecked")
        public T last() {
            TwoThreeFourTreeSet subtree = subtrees[landmarkCount];
            if (subtree != null) {
                return (T) subtree.last();
            } else {
                return landmarks[landmarkCount - 1];
            }
        } 

        public Comparator<T> comparator() {
            return c;
        };

        public Iterator<T> iterator() {
            // TODO Create iterator method
            return null;
        }

        public int height() {
            int max = -1;
            for (int i = 0; i < landmarkCount; ++i) {
                TwoThreeFourTreeSet subtree = subtrees[i];
                if (subtree != null) {
                    max = Math.max(max, subtree.height());
                }
            }
            return max + 1;
        }

    }

    public TwoThreeFourTreeSet() {
        root = new Node();
    }

    public TwoThreeFourTreeSet(Comparator<T> ca) {
        root = new Node(ca);
    }

    @Override
    public boolean add(T elem) {
        return root.add(elem);
    };

    @Override
    public T get(T elem) {
        return root.get(elem);
    }

    @Override
    public boolean contains(T elem) {
        return root.contains(elem);
    }

    @Override
    public int size() {
        return root.size();
    }

    @Override
    public T first() {
        return root.first();
    }

    @Override
    public T last() {
        return root.last();
    } 

    @Override
    public Comparator<T> comparator() {
        return root.comparator();
    };

    @Override
    public Iterator<T> iterator() {
        return root.iterator();
    }

    @Override
    public int height() {
        return root.height();
    }

}