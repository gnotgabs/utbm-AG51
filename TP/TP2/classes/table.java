package classes;

import java.util.Random;

public class table
{
  private int[] table;
  public table(int size)
  {
    Random gen = new Random();
    table = new int[size];
  }

  public boolean search(int toFind)
  {
    for(int i=0;i<table.length;++i)
      if(table[i] == toFind)
        return true;
    return false;
  }

  public void insert(int index, int toInsert)
  {
    int[] tmp = new int[table.length + 1];
    int i;

    for(i=0;i<index;++i)
      tmp[i] = table[i];
    tmp[index] = toInsert;
    for(i=index;i<table.length;++i)
      tmp[i+1] = table[i];

    table = tmp;
  }

  public void remove(int index)
  {
    int[] tmp = new int[table.length - 1];
    int i;

    for(i=0;i<index;++i)
      tmp[i] = table[i];
    for(i=index;i<(tmp.length);++i)
      tmp[i] = table[i+1];

    table = tmp;
  }
}
