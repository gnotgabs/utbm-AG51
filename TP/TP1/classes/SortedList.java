package classes;

import java.util.ArrayList;
import java.util.Random;

public class SortedList extends ArrayList {
  public SortedList() {
    super();
  }

  public SortedList(int size) {
    super();
    Random gen = new Random();
    for(int i=0; i<size; ++i) {
        this.add(gen.nextInt());
    }
  }

  /* Insertion sort : average complexity O(n^2)*/
  public void insertionSort()
  {
    int i,j;
    int cpt;
    int element;

    for(i = 1; i < this.size(); ++i)
    {
      element = (Integer) this.get(i);
      cpt = i-1;
      while(cpt >= 0 && (Integer)this.get(cpt) > element)
      {
        this.set(cpt+1, this.get(cpt));
        cpt--;
      }
      this.set(cpt+1, element);
    }
  }

  /* Selection sort, average complexity O(n^2)*/
  public void selectionSort()
  {
    int i,j;
    for(i=0; i<this.size(); ++i)
    {
      for(j=i+1; j<this.size(); ++j)
       {
        int tmp;
        if((Integer)this.get(i)>(Integer)this.get(j))
         {
          tmp = (Integer)this.get(j);
          this.set(j, this.get(i));
          this.set(i, tmp);
        }
      }
    }
  }

  /* Merges two lists in place by givind the bounds in the array */
  private void merge(int begin, int mid, int end)
  {
    int index1=0, index2=0, index=begin;
    SortedList list1 = new SortedList();
    SortedList list2 = new SortedList();

    for(int i=begin; i <= mid; ++i)
      list1.add(this.get(i));

    for(int i=mid+1; i <= end; ++i)
      list2.add(this.get(i));

    while(index1 < list1.size() && index2 < list2.size())
    {
      if((Integer)list1.get(index1) <= (Integer)list2.get(index2))
      {
        this.set(index, list1.get(index1));
        ++index1;
      }
      else
      {
        this.set(index, list2.get(index2));
        ++index2;
      }
      ++index;
    }

    while(index1 < list1.size())
    {
      this.set(index, list1.get(index1));
      ++index1;
      ++index;
    }

    while(index2 < list2.size())
    {
      this.set(index, list2.get(index2));
      ++index2;
      ++index;
    }
  }

  private void merge_recurse(int begin, int end)
  {
    if(begin < end)
    {
      int mid = (end-begin)/2 + begin;

      this.merge_recurse(begin, mid);
      this.merge_recurse(mid+1, end);

      this.merge(begin, mid, end);
    }
  }

  /* Merge Sort, average complexity : O(n log n)
   * Recursively splits the table in two lists, sort it by recursing, and
   * merges them. */
  public void mergeSort()
  {
    this.merge_recurse(0,this.size()-1);
  }

  /* Converts the array to an heap */
  private void heapify(int count)
  {
    int start = this.size()/2;

    while(start >= 0)
    {
      siftDown(start,count-1);
      --start;
    }
  }

  private void swap(int index1, int index2)
  {
    Object tmp = this.get(index1);
    this.set(index1, this.get(index2));
    this.set(index2, tmp);
  }

  /* Converts the tree to an heap */
  private void siftDown(int start, int end)
  {
    int root=start;

    while((root * 2 + 1)<= end)
    {
      int child = root * 2 + 1;

      if(child<end && (Integer)this.get(child)<(Integer)this.get(child+1))
        ++child;
      if((Integer)this.get(root)<(Integer)this.get(child))
      {
        swap(root, child);
        root = child;
      }
      else
        return;
    }
  }

  /* Heap Sort : average complexity O(n log n)
   * Converts a list to a binary heap, to pop every -sorted- items. */
  public void heapSort()
  {
    int end;
    heapify(this.size());
    end = this.size()-1;

    while(end > 0)
    {
      swap(end, 0);
      siftDown(0, end-1);
      --end;
    }
  }
}
