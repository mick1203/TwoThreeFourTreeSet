package swe4.collections;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1) return;

        int count = Integer.parseInt(args[0]);
        TwoThreeFourTreeSet<Integer> set = new TwoThreeFourTreeSet<>();

        for (int i = 0; i < count; ++i) {
            set.add(i);
        }

        System.out.println(set.toBeautifulString());
    }

}