import java.util.Random;

import classes.hashtable;
import classes.table;

public class Main {
  public static final int MAX=1000000;
  public static final int SAMPLES=1000;

  public static void main(String[] args)
  {
    int i;
    long search_time=0,
         insert_time=0,
         delete_time=0;
    Random gen = new Random();

    for(i=10;i<=MAX;i=i*10)
    {
      long start;
      table tab = new table(i);

      for(int j=0; j<SAMPLES; ++j) {
        start = System.nanoTime();
        tab.search(gen.nextInt());
        search_time += System.nanoTime() - start;

        start = System.nanoTime();
        tab.insert((Math.abs(gen.nextInt()))%i, gen.nextInt());
        insert_time += System.nanoTime() - start;

        start = System.nanoTime();
        tab.remove((Math.abs(gen.nextInt()))%i);
        delete_time += System.nanoTime() - start;
      }

      System.out.println("Table -> size : " + i + ", search : " +
        search_time/SAMPLES + "ns, insert : " + insert_time/SAMPLES +
        "ns, delete : " + delete_time/SAMPLES + "ns");
      search_time=0;
      insert_time=0;
      delete_time=0;
    }


    for(i=1;i<=MAX;i=i*10)
    {
      hashtable tab = new hashtable(i);

      for(int j=0; j<SAMPLES; ++j) {
        long start;

        start = System.nanoTime();
        tab.search(gen.nextInt());
        search_time += System.nanoTime() - start;

        start = System.nanoTime();
        tab.insert((Math.abs(gen.nextInt()))%i, gen.nextInt());
        insert_time += System.nanoTime() - start;

        start = System.nanoTime();
        tab.remove((Math.abs(gen.nextInt()))%i);
        delete_time += System.nanoTime() - start;
      }
      System.out.println("HashTable -> size : " + i + ", search : " +
        search_time/SAMPLES + "ns, insert : " + insert_time/SAMPLES +
        "ns, delete : " + delete_time/SAMPLES + "ns");

    search_time=0;
    insert_time=0;
    delete_time=0;
    }
  }
}
