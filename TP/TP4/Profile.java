import java.util.Random;

import classes.Graph;

public class Profile {
  public static final int MAX=10000;
  public static final int SAMPLES=20;
  public static final double NB_EDGES=0.5;

  public static void main(String[] args)
  {
    int i;
    Random gen = new Random();
    long bfs_time=0,
         dfs_time=0,
         mst_time=0,
         djikstra_time=0;

    for(i=10;i<=MAX;i=i*10)
    {
      long start;
      Graph graph = new Graph(i, 1./(NB_EDGES*i));
      System.out.println("Graph generated!");

      for(int j=0; j<SAMPLES; ++j) {
        System.out.println("Taking the " + (j+1) + "th sample");

        System.out.println("Launching BFS");
        start = System.nanoTime();
        graph.bfs();
        bfs_time += System.nanoTime() - start;

        System.out.println("Lauching DFS");
        start = System.nanoTime();
        graph.dfs();
        dfs_time += System.nanoTime() - start;


        System.out.println("Launching Djikstra");
        start = System.nanoTime();
        if(i<100)
          System.out.println(graph.djikstra());
        else
          graph.djikstra();
        djikstra_time += System.nanoTime() - start;

        System.out.println("Lauching Spanning Tree");
        start = System.nanoTime();
        if(i<100)
          System.out.println(graph.mst());
        else
          graph.mst();
        mst_time += System.nanoTime() - start;
      }

      System.out.println("Graphs -> SIZE : " + i + ", BFS : " +
        bfs_time/SAMPLES + " ns, DFS : " + dfs_time/SAMPLES +
        " ns, Spanning Tree : " + mst_time/SAMPLES + " ns, Djikstra : " +
        djikstra_time/SAMPLES + " ns");
      bfs_time=0;
      dfs_time=0;
      mst_time=0;
      djikstra_time=0;
    }
  }
}
