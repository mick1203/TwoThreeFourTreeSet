package swe4.tests;

import static org.junit.Assert.*;
import java.util.Comparator;
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

}
