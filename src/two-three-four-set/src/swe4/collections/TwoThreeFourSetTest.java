package swe4.collections;

import static   org.junit.Assert.*;
import          org.junit.Test;

public class TwoThreeFourSetTest {

        @Test
        public void createIntSetTest() {
            TwoThreeFourSet<String> set = new TwoThreeFourSet<>();
            assertNotEquals(set, null);
        }

        @Test
        public void sizeTest() {
            TwoThreeFourSet<Double> set = new TwoThreeFourSet<>();
            assertEquals(set.size(), 0);
        }

        @Test
        public void addTest() {
            TwoThreeFourSet set = new TwoThreeFourSet<>();

            System.out.println(set.size());

            assertEquals(set.size(), 0);
            set.add(3);
            assertEquals(set.size(), 1);
        } 

        @Test
        public void heightTest() {
            TwoThreeFourSet<Boolean> set = new TwoThreeFourSet<>();
            assertEquals(set.height(), 0);
        }

}