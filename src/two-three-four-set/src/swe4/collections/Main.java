package swe4.collections;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import java.lang.Thread;

public class Main {

    private static void generateDATFiles(int n) {
        FileWriter file = null;
        BufferedWriter writer = null;

        try {
            file = new FileWriter("dat/sorted.dat");
            writer = new BufferedWriter(file);

            int count = n;
            TwoThreeFourTreeSet<Integer> set = new TwoThreeFourTreeSet<>();

            writer.append("number_of_elements height\n");
            for (int i = 0; i < count; ++i) {
                set.add(i);
                writer.append(i + " " + set.height() + "\n");
            }
            
            writer.close();
            file.close();
            
            file = new FileWriter("dat/random.dat");
            writer = new BufferedWriter(file);
            
            TwoThreeFourTreeSet<Integer> randomSet = new TwoThreeFourTreeSet<>();
            Random rand = new Random();
            
            writer.append("number_of_elements height\n");
            for (int i = 0; i < count; ++i) {
                randomSet.add(rand.nextInt());
                writer.append(i + " " + randomSet.height() + "\n");
            }

            file = new FileWriter("dat/logarithm.dat");
            writer = new BufferedWriter(file);
                        
            writer.append("number_of_elements height\n");
            for (int i = 0; i < count; ++i) {
                writer.append(i + " " + Math.log((double) i) + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null)
                    writer.close();
                if (file != null)
                    file.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            generateDATFiles(10000);
            return;
        };

        int count = Integer.parseInt(args[0]);

        TwoThreeFourTreeSet<Integer> set = new TwoThreeFourTreeSet<>();
        Random rand = new Random();

        for (int i = 0; i < count; ++i) {
            set.add(rand.nextInt(count * 2));
            
            try {
                Thread.sleep(100);
                System.out.print("\033[H\033[2J");
                System.out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println(set.toBeautifulString());
        }
 
    }

}