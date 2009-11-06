package classes;

import java.util.ArrayList;

public class PriorityQueue
{
  private ArrayList<ArrayList> list;

  public PriorityQueue()
  {
    list = new ArrayList<ArrayList>();
  }

  private void swap(int ind1, int ind2)
  {
    ArrayList swap = list.get(ind1);
    list.set(ind1, list.get(ind2));
    list.set(ind2, swap);
  }

  /* Inserts an item with a given priority in the heap.
   * Complexity O(log n)*/
  public void insert(Object element, int priority)
  {
    int parent, index;
    // We set a list for every node containing (priority, element)
    ArrayList tmp = new ArrayList();

    tmp.add(priority);
    tmp.add(element);

    this.list.add(tmp);

    index = list.size()-1;

    /* We get the parent node's index using heaps proprieties */
    parent = (index-1)/2;

    while ((Integer)list.get(parent).get(0) <
        (Integer)list.get(index).get(0))
    {
      swap(index,parent);
      index = parent;
      parent = (index-1)/2;
    }
  }

  /* Returns the highest ranked element without removing it from the queue
   * Complexity should be O(1), since we always access to the first item */
  public ArrayList peekAtNext()
  {
    return list.get(0);
  }

  /* Reorders recursively the heap, from top to bottom */
  private void heapify(int parent)
  {
    int lchild = parent * 2 + 1;
    int rchild = lchild + 1;

    if(lchild < list.size())
    {
      if(rchild < list.size())
      {
        int toComp;

        if((Integer)list.get(rchild).get(0) >
            (Integer)list.get(lchild).get(0))
          toComp = rchild;
        else
          toComp = lchild;

        if((Integer)list.get(toComp).get(0) >
            (Integer)list.get(parent).get(0))
        {
          swap(toComp, parent);
          heapify(toComp);
        }
      }
      else
        if((Integer)list.get(lchild).get(0) >
            (Integer)list.get(parent).get(0))
          swap(lchild, parent);
    }
  }

  /* Returns and pops the highest ranked element, Complexity is O(log n)*/
  public ArrayList getNext()
  {
    ArrayList toReturn = list.get(0);

    list.set(0, list.get(list.size()-1));
    list.remove(list.size()-1);

    heapify(0);
    return toReturn;
  }
}
