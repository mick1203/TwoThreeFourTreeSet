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

        /**
         * Prepares a node for insertion at a given index, this means that
         * the subtree references and the landmarks are shifted to
         * accomodate the incoming values
         * @param   n specifying the index at which should be cleared
         */
        public void prepareForInsertionAt(int n) {
            // copy necessary elements
            for (int i = landmarkCount; i > n; --i) {
                landmarks[i] = landmarks[i - 1];
                subtrees[i + 1] = subtrees[i];
            } 
        }

        /**
         * Checks if a node is a leaf, e.g. if it is at the bottom of the
         * hierarchy and has no subtrees
         * @return  Boolean stating if node is a leaf
         */
        public boolean isLeaf() {
            return subtrees[0] == null;
        }

        /**
         * Checks if a node is full, e.g. if it has three landmarks in it
         * @return  Boolean stating if the node is full
         */
        public boolean isFull() {
            return landmarkCount >= 3;
        }


        /**
         * Splits a node in two subnodes, passes them the comparator via the
         * constructor, resets the subnodes parent, moves the landmarks to
         * the correct subnode, resets the subtrees parents, updates parent
        */
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

        /**
         * Adds a node to the structure, is guaranteed to find a fitting node
         * because the balancing of the tree is done with every get(), so no
         * need to check for splitting
         * @param   elem Element that should be inserted in the node
         * @return  Boolean stating wether insertion was successfull
         */
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

        /**
         * Returns a reference to a node saved in the nodes or any of its
         * subtrees. Also handles the balancing of the tree if any nodes are
         * full by splitting them.
         * @param   elem , element to be searched in the node and its subtrees
         * @return  Reference to the element if it is in the node, null
         * otherwise
         */
        public T get(T elem) {
            for (int i = 0; i < landmarkCount; ++i) {
                if (compare(elem, landmarks[i]) == 0) return landmarks[i];
            }

            int idx = getIndexForValue(elem);
            Node subtree = subtrees[idx];

            // split if necessary
            if (isFull()) split();
        
            return isLeaf() ? null: (T) subtree.get(elem);
        }

        /**
         * Returns the number of element saved in the node and its subtrees.
         * @return  Count of elements
         */
        public int size() {
            int n = landmarkCount;
            if (!isLeaf()) {
                for (int i = 0; i <= landmarkCount; ++i) {
                    n += subtrees[i].size(); 
                }
            }
            return n;
        }

        /**
         * Gets the first element in a node and its subtrees.
         * @throws  NoSuchElementException if there is no first element
         * @return  Reference to the first element
         */
        public T first() throws NoSuchElementException {
            if (landmarkCount == 0) throw new NoSuchElementException();
            Node subtree = subtrees[0];
            T element = landmarks[0];
            return isLeaf()? element: (T) subtree.first();
        }

        /**
         * Gets the last element in a node and its subtrees.
         * @throws  NoSuchElementException if there is no last element
         * @return  Reference to the last element
         */
        public T last() throws NoSuchElementException {
            if (landmarkCount == 0) throw new NoSuchElementException();
            Node subtree = subtrees[landmarkCount];
            T element = landmarks[landmarkCount - 1];
            return isLeaf()? element : (T) subtree.last();
        } 

        /**
         * Gets the comparator that is used in the node and its subtrees
         * @return Comparator object
         */
        public Comparator<T> comparator() {
            return c;
        };

        /**
         * Gets the maximum height of the nodes subtrees
         * @return  Height of the tree
         */
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

        /**
         * Converts the data structure to a String
         * @return  The tree in String format
         */
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("[" + toNodeString() + "{");
            for (int i = 0; i <= landmarkCount; ++i) {
                Node s = subtrees[i];
                if (s != null) {
                    sb.append(subtrees[i].toString());
                }
            }
            sb.append("}]");
            return sb.toString();
        }

        public String toNodeString() {
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            for (int i = 0; i < landmarkCount; ++i) {
                sb.append(landmarks[i]);
                if (i < landmarkCount - 1) sb.append(",");
            }
            sb.append(")");
            return sb.toString();
        }

        public String toPHTPAString(String indent, Boolean last) {
            StringBuilder sb = new StringBuilder();
            String intersection = (last ? "╚═" : "╠═");
            sb.append(indent + intersection + this.toNodeString() + "\n");
            if (!isLeaf()) {
                indent += (last ? "    " : "║   ");
                for (int i = landmarkCount; i >= 0; --i) {
                    sb.append(this.subtrees[i].toPHTPAString(indent, false));
                }
                sb.append(subtrees[0].toPHTPAString(indent, true));    
            } 
            return sb.toString();
        }
    }
    /**
     * Iterator class for the class TwoThreeFourTreeSet<T>
     */
    private class TwoThreeFourTreeSetIterator implements Iterator<T> {
        private Node currentNode;
        private int i;

        public TwoThreeFourTreeSetIterator(Node start) {
            currentNode = start;
            i = 0;
        }

        /**
         * Loads value from current parameters, sets it to null if 
         * parameters are invalid
         */
        private T loadCurrent() {
            return currentNode == null ? null: currentNode.landmarks[i];
        }

        public boolean hasNext() {
            return loadCurrent() != null;
        }

        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            
            T tmp = loadCurrent();
            
            if (currentNode.isLeaf()) {
                if (i >= currentNode.landmarkCount - 1) {
                    // precondition: node is finished
                    do {
                        currentNode = currentNode.parent;
                        if (currentNode != null) {
                            i = currentNode.getIndexForValue(tmp);
                        }
                    } while (
                        currentNode != null &&
                        i == currentNode.landmarkCount
                    );
                    if (tmp == last()) currentNode = null;
                } else {
                    i++;
                }
            } else {
                // precondition: next subtree
                currentNode = currentNode.subtrees[i + 1];
                while (!currentNode.isLeaf()) {
                    currentNode = currentNode.subtrees[0];
                }
                i = 0;
            }
            
            return tmp;
        }
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

    /**
     * Gets the first node in the data structure
     * @return Reference to the first node in the structure
     */
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
        return root.toString();
    }

    public String toBeautifulString() {
        return root.toPHTPAString("", true);
    }

}