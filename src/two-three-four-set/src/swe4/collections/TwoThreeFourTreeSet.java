package swe4.collections;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class that implements a balanced tree. Otherwise called
 * a two-three-four tree because of its variable number of
 * pointers and landmark values.
 * @author  Michael Burgstaller
 */
public class TwoThreeFourTreeSet<T extends Object> 
    implements SortedTreeSet<T> {

    private Node root;

    private class Node {

        /**
         * Array of Node objects, can contain up to three subtrees as
         * specified in the instructions
         * 
         */
        public TwoThreeFourTreeSet<T>.Node[]   subtrees;

        /**
         * Array of T objects, specifying the value that separates two
         * subtrees
         */
        public T[]      landmarks;

        /**
         * Number of landmarks in the object
         */
        public int      landmarkCount;

        /**
         * A comparator object for the type T, can be null
         */
        private Comparator<T> c = null;

        /**
         * A Node represent the parent node of the current one and 
         * that helps traversing the tree
         */
        public Node parent;

        @SuppressWarnings("unchecked")
        public Node() {
            subtrees = new TwoThreeFourTreeSet.Node[4];
            landmarks = (T[]) new Object[3];
            landmarkCount = 0;
            parent = null;
        }

        public Node(Comparator<T> ca) {
            this();
            c = ca;
        }

        /**
         * Convert a given value to index of the subtree that value belongs
         * to.
         * @param n The value to be converted
         * @return  Index of the correct subtree
         */
        public int getIndexForValue(T n) {
            int idx = 0;
            while (idx < landmarkCount && compare(n, landmarks[idx]) >= 0) {
                idx++;
            }
            return idx;
        }

        public void prepareForInsertionAt(int n) {
            // copy necessary elements
            for (int i = landmarkCount; i > n; --i) {
                landmarks[i] = landmarks[i - 1];
                subtrees[i + 1] = subtrees[i];
            } 
        }

        public boolean isLeaf() {
            return subtrees[0] == null;
        }

        public boolean isFull() {
            return landmarkCount >= 3;
        }

        public void split() {           
            Node left = new Node(c);
            Node right = new Node(c);
            T middle = this.landmarks[1];
    
            // set parent of new nodes
            left.parent = this.parent;
            right.parent = this.parent;
    
            // set landmarks in child nodes
            left.landmarks[0] = this.landmarks[0];
            left.landmarkCount++;
            right.landmarks[0] = this.landmarks[2];
            right.landmarkCount++;
    
            // set subtrees in child nodes
            left.subtrees[0] = this.subtrees[0];
            left.subtrees[1] = this.subtrees[1];
            right.subtrees[0] = this.subtrees[2];
            right.subtrees[1] = this.subtrees[3];
  
            // set parent for subtrees
            for (int i = 0; i < 4; ++i) {
                Node subtree = this.subtrees[i];
                if (subtree != null) {
                    this.subtrees[i].parent = (i <= 1) ? left : right;
                }
            }

            // get index of current node and prepare parent
            int idx = this.parent.getIndexForValue(middle);
            this.parent.prepareForInsertionAt(idx);

            // write data to parent
            this.parent.landmarks[idx] = middle;
            this.parent.subtrees[idx] = left;
            this.parent.subtrees[idx + 1] = right;
            this.parent.landmarkCount++;

        }

        public boolean add(T elem) {
            // element has to be inserted.
            int idx = getIndexForValue(elem);
            if (isLeaf()) {
                // element has to be inserted in current node
                prepareForInsertionAt(idx);
                landmarks[idx] = elem;
                landmarkCount++;
                return true;
            } else {
                // element has to be inserted in a subtree
                Node subtree = subtrees[idx];
                return subtree.add(elem);
            }
        };

        public T get(T elem) {
            for (int i = 0; i < landmarkCount; ++i) {
                if (compare(elem, landmarks[i]) == 0) {
                    return landmarks[i];
                }
            }

            int idx = getIndexForValue(elem);
            Node subtree = subtrees[idx];

            // split if necessary
            if (isFull()) split();
        
            return isLeaf() ? null: (T) subtree.get(elem);
        }

        public int size() {
            int n = landmarkCount;
            if (!isLeaf()) {
                for (int i = 0; i <= landmarkCount; ++i) {
                    n += subtrees[i].size(); 
                }
            }
            return n;
        }

        public T first() throws NoSuchElementException {
            if (landmarkCount == 0) throw new NoSuchElementException();
            Node subtree = subtrees[0];
            return isLeaf()? landmarks[0]: (T) subtree.first();
        }

        public T last() throws NoSuchElementException {
            if (landmarkCount == 0) throw new NoSuchElementException();
            Node subtree = subtrees[landmarkCount];
            return isLeaf()? landmarks[landmarkCount - 1]: (T) subtree.last();
        } 

        public Comparator<T> comparator() {
            return c;
        };

        public int height() {
            int max = -1;
            for (int i = 0; i < landmarkCount; ++i) {
                Node subtree = subtrees[i];
                if (subtree != null) {
                    max = Math.max(max, subtree.height());
                }
            }
            return max + 1;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            sb.append("( ");
            for (int i = 0; i < landmarkCount; ++i) {
                sb.append(landmarks[i] + " ");
            }
            for (int i = 0; i <= landmarkCount; ++i) {
                Node s = subtrees[i];
                if (s != null) {
                    sb.append(subtrees[i].toString());
                }
            }
            sb.append(")");

            return sb.toString();
        }
    }

    private class TwoThreeFourTreeSetIterator implements Iterator<T> {
        private Node current;
        private int i;

        public TwoThreeFourTreeSetIterator(Node start) {
            current = start;
            i = 0;
        }

        public boolean hasNext() {
            return false;
        }
        public T next() {
        return null;
    }
    public TwoThreeFourTreeSet() {
        root = new Node();
    }

    public TwoThreeFourTreeSet(Comparator<T> ca) {
        root = new Node(ca);
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
        public int compare(T lhs, T rhs) {
            Comparator<T> c = comparator();
            if (c == null) {
                // assume T implements comparable
                return ((Comparable<T>) lhs).compareTo(rhs);
            } else {
                // compare with comparator
                return c.compare(lhs, rhs);
            }
        }

    private Node getFirstNode() {
        Node tmp = root;
        while(!tmp.isLeaf()) {
            tmp = tmp.subtrees[0];
        }
        return tmp;
    }

    @Override
    public boolean add(T elem) { 
        if (contains(elem)) return false;
        return root.add(elem);
    };

    @Override
    public T get(T elem) {
        // create new root if root is full
        if (root.isFull()) {
            Node n = new Node(comparator());
            root.parent = n;
            root.split();
            root = n;
        }
        return root.get(elem);
    }

    @Override
    public boolean contains(T elem) {
        return get(elem) != null;
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
        return new TwoThreeFourTreeSetIterator(getFirstNode());
    }

    @Override
    public int height() {
        return root.height();
    }

    @Override
    public String toString() {
        return "[\n" + root.toString() + "\n]";
    }

}