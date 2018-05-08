package swe4.tests;

import static org.junit.Assert.*;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;

import org.junit.Test;

import swe4.collections.SortedTreeSet;
import swe4.collections.TwoThreeFourTreeSet;

public class TwoThreeFourSetTest extends SortedTreeSetTestBase {
    
    @Override
    protected <T> TwoThreeFourTreeSet<T> createSet(Comparator<T> comparator) {  
        return new TwoThreeFourTreeSet<T>(comparator);
    }

    @Test
    public void testHeight() {
        final int NELEMS = 10000;
        SortedTreeSet<Integer> set = createSet();

        for (int i=1; i<=NELEMS; i++) {
            set.add(i);
            int h = set.height();
            int n = set.size();
            assertTrue(
                "height(set) <= ld(size(set))+1", 
                h <= Math.log((double)n)/Math.log(2.0)+1
            );
        }
    }

    @Test
    public void testToString() {
        final int NELEMNS = 3;
        SortedTreeSet<Integer> set = createSet();

        for (int i = 0; i < NELEMNS; ++i) {
            set.add(i);
        }
        assertEquals("[(0,1,2){}]", set.toString());
    }

    @Test
    public void testRootSplitting() {
        SortedTreeSet<Integer> set = createSet();

        set.add(0);
        set.add(1);
        set.add(2);

        assertEquals("[(0,1,2){}]", set.toString());
        
        set.add(3);
        
        assertEquals("[(1){[(0){}][(2,3){}]}]", set.toString());
    }

    public class NoComparatorClass {
        int data;
        public NoComparatorClass(int i) {
            this.data = i;
        }
    }

    @Test(expected=ClassCastException.class)
    public void testComparatorException() {
        SortedTreeSet<NoComparatorClass> set = createSet();
        set.add(new NoComparatorClass(1));
        set.add(new NoComparatorClass(2));
    }

    @Test
    public void testNoComparatorClassWithComparator() {
        SortedTreeSet<NoComparatorClass> set = createSet(
            (NoComparatorClass l, NoComparatorClass r) -> l.data - r.data
        );

        set.add(new NoComparatorClass(5));
        set.add(new NoComparatorClass(2));
        set.add(new NoComparatorClass(1));
        set.add(new NoComparatorClass(50));

        assertEquals(set.size(), 4);
    }

    public class ComparatorClass implements Comparable {
        private int data;

        public ComparatorClass(int n) {
            this.data = n;
        }

        @Override
        public int compareTo(Object other) {
            return data - ((ComparatorClass) other).data;
        }
    }

    @Test
    public void testCustomObject() {
        SortedTreeSet<ComparatorClass> set = createSet();

        set.add(new ComparatorClass(-1));
        set.add(new ComparatorClass(3));
        set.add(new ComparatorClass(4));

        assertEquals(set.size(), 3);
    }

    @Test
    public void testHeightEmpty() {
        SortedTreeSet<Integer> set = createSet();
        assertEquals(set.height(), 0);
    }

   @Test
   public void testCreation() {
        SortedTreeSet<Integer> set = createSet();
        assertNotEquals(null, set);
   } 

   @Test
   public void testAddReverse() {
        SortedTreeSet<Integer> set = createSet();
        for (int i = 10000; i > 0; --i) {
            set.add(i);
        }
        assert(isSorted(set));
   }

    @Test
    public void testIteratorEmptySet() {
        SortedTreeSet<Integer> set = createSet();
        Iterator it = set.iterator();
        assert(it != null);
        assert(!it.hasNext());
    }

    @Test
    public void testRangeBasedFor() {
        final int NELEMS = 10;
        SortedTreeSet<Integer> set = createSet();

        for (int i=1; i<=NELEMS; i++) {
            set.add(i);
        }

        int count = 0;
        Integer min = Integer.MAX_VALUE;
        Integer max = Integer.MIN_VALUE;

        for (Integer i: set) {
            count++;
            min = Math.min(min, i);
            max = Math.max(max, i);
        }

        assertEquals(set.size(), count);
        assertEquals(set.first(), min);
        assertEquals(set.last(), max);
    }

}
