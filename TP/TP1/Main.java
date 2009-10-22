import java.util.Random;

import java.util.ArrayList;
import classes.SortedList;

public class Main {
  public static final int MAX=1000000;
  public static final int SAMPLES=30;

  public static void main(String[] args)
  {
    int i;
    long insertion_time=0,
         selection_time=0,
         heap_time=0,
         merge_time=0;

    for(i=10;i<=MAX;i=i*10)
    {
      long start;

      for(int j=0; j<SAMPLES; ++j) {
        SortedList list = new SortedList(i);
        start = System.nanoTime();
        list.insertionSort();
        insertion_time += System.nanoTime() - start;

        list = new SortedList(i);
        start = System.nanoTime();
        list.selectionSort();
        selection_time += System.nanoTime() - start;

        list = new SortedList(i);
        start = System.nanoTime();
        list.heapSort();
        heap_time += System.nanoTime() - start;

        list = new SortedList(i);
        start = System.nanoTime();
        list.mergeSort();
        merge_time += System.nanoTime() - start;
      }

      System.out.println("Sorts -> size : " + i + ", Insertion : " +
        insertion_time/SAMPLES + " ns, Selection : " +
        selection_time/SAMPLES + " ns, Heap : " + heap_time/SAMPLES +
        " ns, Merge : " + merge_time/SAMPLES + " ns");

      insertion_time=0;
      selection_time=0;
      heap_time=0;
      merge_time=0;
    }
  }
}
