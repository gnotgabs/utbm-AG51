import java.util.Random;

import classes.PriorityQueue;

public class Main {
  public static final int MAX=1000000;
  public static final int SAMPLES=10000;

  public static void main(String[] args)
  {
    int i;
    long get_time=0,
         insert_time=0,
         pop_time=0;
    Random gen = new Random();

    for(i=10;i<=MAX;i=i*10)
    {
      long start;
      PriorityQueue queue = new PriorityQueue();

      for(int j=0; j<i;++j)
        queue.insert(gen.nextInt(), Math.abs(gen.nextInt()%i));

      for(int j=0; j<SAMPLES; ++j) {
        start = System.nanoTime();
        queue.insert(gen.nextInt(), Math.abs(gen.nextInt()%i));
        insert_time += System.nanoTime() - start;

        start = System.nanoTime();
        queue.peekAtNext();
        get_time += System.nanoTime() - start;

        start = System.nanoTime();
        queue.getNext();
        pop_time += System.nanoTime() - start;
      }

      System.out.println("Queue -> size : " + i + ", Pop : " +
        pop_time/SAMPLES + "ns, insert : " + insert_time/SAMPLES +
        "ns, Peek : " + get_time/SAMPLES + "ns");
      get_time=0;
      insert_time=0;
      pop_time=0;
    }
  }
}
