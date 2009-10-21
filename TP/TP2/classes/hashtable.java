package classes;

import java.util.Hashtable;
import java.util.Random;

public class hashtable {
  private Hashtable table = new Hashtable();
  public hashtable(int size)
  {
    Random gen = new Random();
    for(int j=0;j<size;++j)
      table.put(gen.nextInt(), gen.nextInt());
  }

  public boolean search(int toFind)
  {
    return table.contains(toFind);
  }

  public void insert(int key, int toInsert)
  {
      table.put(key, toInsert);
  }

  public void remove(int key)
  {
      table.remove(key);
  }
}
