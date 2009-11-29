package classes;

import java.lang.IndexOutOfBoundsException;
import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.Random;

public class Graph {
  static private final int ROOT = 0;
  /* Adjacency matrix. If an element is different from 0, then we have a
   * link between these two nodes. The value is the weight of the edge */
  private int[][] adjacency;
  private ArrayList visited;
  private int size;
  private int nb_edges;

  public Graph(int M) {
    this.size = M;
    this.nb_edges = 0;
    this.adjacency = new int[M][M];
    this.visited = new ArrayList();
  }

  public Graph(int M, double probability) {
    this.size = M;
    this.nb_edges = 0;
    this.adjacency = new int[M][M];
    this.visited = new ArrayList();

    ArrayList<Integer> notconnected = new ArrayList();
    ArrayList<Integer> connected = new ArrayList();
    Random rGen = new Random();

    connected.add(0);
    for(int i=1; i<size; i++)
      notconnected.add(i);

    while(!notconnected.isEmpty()) {
      int node_index = Math.abs(rGen.nextInt()%notconnected.size());
      int node = notconnected.get(node_index);

      int mount_index = Math.abs(rGen.nextInt()%connected.size());
      int mount_point = connected.get(mount_index);

      int weight = Math.abs(rGen.nextInt()%100)+1;

      this.adjacency[node][mount_point] = weight;
      this.adjacency[mount_point][node] = weight;
      this.nb_edges += 2;

      if (rGen.nextFloat() > probability) {
        connected.add(node);
        notconnected.remove(node_index);
      }
    }
  }

  public void setEdges(int[] ... edges) {
    for(int[] edge: edges) {
      if(edge[2] >= 100 || edge[2] < 0)
        throw new IndexOutOfBoundsException("Priority not in bounds");

      this.adjacency[edge[0]][edge[1]] = edge[2];
      this.adjacency[edge[1]][edge[0]] = edge[2];
      this.nb_edges += 2;
    }
  }

  public ArrayList<Integer> getNeighbours(int node) {
    ArrayList<Integer> ngbh = new ArrayList<Integer>();
    for(int i=0; i<this.size; ++i) {
      if(this.adjacency[node][i] != 0) {
        ngbh.add(i);
      }
    }
    return ngbh;
  }

  /* Implements a breadth first search algorithm, using a queue.
   * While we have something in queue, every child node is queued,
   * while a node is polled. This way, we ensure to always go to
   * "brothers" first, and then to childnodes */
  public void bfs() {
    this.visited = new ArrayList();
    ArrayBlockingQueue queue = new ArrayBlockingQueue(this.nb_edges);
    queue.add(this.ROOT);
    while(queue.size() > 0) {
      int node = (Integer)queue.poll();
      this.visited.add(node);
      for(Integer child: this.getNeighbours(node)) {
        if(!this.visited.contains(child)) {
          queue.add(child);
        }
      }
    }
  }

  /* Implements a depth first search algorithm, using a stack.
   * While we have something in stack, every child node is pushed,
   * while a node is poped. This way, we ensure to always go to
   * childnodes first, and then to "brothers" */
  public void dfs() {
    this.visited = new ArrayList();
    Stack stack = new Stack();
    stack.push(this.ROOT);
    while(stack.size() > 0) {
      int node = (Integer)stack.pop();
      this.visited.add(node);
      for(Integer child: this.getNeighbours(node)) {
        if(!this.visited.contains(child)) {
          stack.push(child);
        }
      }
    }
  }

  /* Returns a list of every nodes linked to the given node
   * returned lists are [weight, linked node, given node]
   */
  public ArrayList<ArrayList<Integer>> getEdges(int node) {
    ArrayList<ArrayList<Integer>> ngbh = new ArrayList();
    for(int i=0; i<this.size; ++i) {
      if(this.adjacency[node][i] != 0) {
        ArrayList tmp = new ArrayList();
        tmp.add(this.adjacency[node][i]);
        tmp.add(i);
        tmp.add(node);
        ngbh.add(tmp);
      }
    }
    return ngbh;
  }

  /* Get the lowest weighted neighbour for a given node */
  public ArrayList<Integer> getLowestEdge(int node) {
    ArrayList<Integer> lowest = new ArrayList<Integer>();
    lowest.add(100);
    lowest.add(null);
    for(ArrayList child: this.getEdges(node)) {
      if((Integer)child.get(0) < lowest.get(0)) {
        lowest.set(0,(Integer)child.get(0));
        lowest.set(1,(Integer)child.get(1));
      }
    }
    return lowest;
  }

  /* We here sort the edges by priority and return a list of couples
   * (priority, node associated). A more efficient sort would probably
   * be much better on dense graphs, but we here process very small
   * (probably <10) amount of data, so selection sort fits well. */
  public void sortEdges(ArrayList<ArrayList<Integer>> toSort) {

    for(int i=0; i < toSort.size(); ++i) {
      int min = i;
      for(int j = i+1; j < toSort.size(); ++j) {
        if(((Integer)toSort.get(j).get(0)) <
            ((Integer)toSort.get(min).get(0))) {
          min = j;
        }
      }
      if(min != i) {
        ArrayList swap = toSort.get(i);
        toSort.set(i, toSort.get(min));
        toSort.set(min, swap);
      }
    }
  }


  /* Implements a minimum spanning tree algorithm. We are using a greedy
   * algorithm, the Prim's algorithm. For uniqueness reason with others
   * algorithms, we use a adjacency matrix (complexity O(n^2), n being the
   * number of vertices, but a binary heap or a Fibonacci heap used with
   * an adjacency list would drop the complexity to a logarithmic one. */
  public ArrayList mst() {
    ArrayList<ArrayList<Integer>> edges = new ArrayList();
    ArrayList<Integer> vertices = new ArrayList();

    /* We add the starting node */
    vertices.add(this.ROOT);

    /* While all the vertices aren't linked by the tree */
    while(vertices.size() < this.size) {
      int i;
      ArrayList<ArrayList<Integer>> availEdges = new ArrayList();
      for(Integer node: vertices) {
        availEdges.addAll(this.getEdges(node));
      }
      this.sortEdges(availEdges);

      for(i=0; i<availEdges.size(); ++i) {
        if(!vertices.contains(availEdges.get(i).get(1)))
          break;
      }
      if(i < availEdges.size()) {
        vertices.add(availEdges.get(i).get(1));
        edges.add(availEdges.get(i));
      }
    }

    return edges;
  }

  public ArrayList<Integer> djikstra() {
    this.visited = new ArrayList();
    ArrayList<Integer> dist = new ArrayList();

    /* Initialize the arrays */
    for(int i=0; i < this.size; ++i) {
      dist.add(Integer.MAX_VALUE);
    }

    /* The distance from the source to itself is nil */
    dist.set(this.ROOT, 0);

    while(this.visited.size() < this.size) {
      ArrayList<ArrayList<Integer>> availEdges;
      int toExplore = -1;
      int minDistance = Integer.MAX_VALUE;

      for(int i=0; i < dist.size(); ++i) {
        if(dist.get(i) < minDistance && !visited.contains(i)) {
          minDistance = dist.get(i);
          toExplore = i;
        }
      }

      availEdges = this.getEdges(toExplore);
      this.visited.add(toExplore);
      for(ArrayList<Integer> child : availEdges) {
        if(dist.get(child.get(1)) > minDistance + child.get(0)) {
          dist.set(child.get(1), minDistance + child.get(0));
        }
      }

    }
    return dist;
  }
}
