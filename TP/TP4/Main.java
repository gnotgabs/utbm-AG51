import java.util.Random;

import classes.Graph;

public class Main
{
  public static final int SIZE=6;

  public static void main(String[] args)
  {
    int i;
    Random gen = new Random();

    Graph graph = new Graph(SIZE);

    int[] edge1 = {0,1,Math.abs(gen.nextInt()%100)};
    int[] edge2 = {0,4,Math.abs(gen.nextInt()%100)};
    int[] edge3 = {0,2,Math.abs(gen.nextInt()%100)};
    int[] edge4 = {1,4,Math.abs(gen.nextInt()%100)};
    int[] edge5 = {1,2,Math.abs(gen.nextInt()%100)};
    int[] edge6 = {2,3,Math.abs(gen.nextInt()%100)};
    int[] edge7 = {3,4,Math.abs(gen.nextInt()%100)};
    int[] edge8 = {3,5,Math.abs(gen.nextInt()%100)};

    graph.setEdges(edge1, edge2, edge3, edge4, edge5, edge6, edge7, edge8);

    long startDFS = System.nanoTime();
    graph.dfs();
    long endDFS = System.nanoTime();

    long startBFS = System.nanoTime();
    graph.bfs();
    long endBFS = System.nanoTime();

    long startMST = System.nanoTime();
    System.out.println(graph.mst());
    long endMST = System.nanoTime();

    long startDJK = System.nanoTime();
    System.out.println(graph.djikstra());
    long endDJK = System.nanoTime();

    System.out.println("Graph -> size : 6, BFS : " + (endBFS - startBFS) +
        "ns, DFS : " + (endDFS - startDFS) + "ns, MST : " +
        (endMST-startMST) + "ns, Djikstra : " + (endDJK-startDJK));

  }
}
