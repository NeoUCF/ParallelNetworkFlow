import java.io.*;
import java.util.*;

/**
 * EdmondsKarpParallel
 * Original by Pedro Contipelli
 * Edited by Glenn Eric Hartwell
 * COP4520
 * Network Flow Parallelization Project 
 */
public class EdmondsKarpSequential
{

    public static void main(String[] args)
    {
		Graph graph = new Graph();
		
        int maxFlow = 0;
		
		long start = System.currentTimeMillis();
        while (true)
        {
            Edge[] path = graph.getAugPath();

            // If sink was NOT reached, no augmenting path was found.
			// Algorithm terminates and prints out max flow.
			if (path[graph.sink] == null)
                break;
        
            // If sink WAS reached, we will push more flow through the path
            int pushFlow = Integer.MAX_VALUE;
            
            // Finds maximum flow that can be pushed through given path
            // by finding the minimum residual flow of every edge in the path
            for (Edge e = path[graph.sink]; e != null; e = path[e.u])
                pushFlow = Math.min(pushFlow , e.capacity - e.flow);
            
            // Adds to flow values and subtracts from reverse flow values in path
            for (Edge e = path[graph.sink]; e != null; e = path[e.u])
            {
                e.flow += pushFlow;
                // e.reverse.flow -= pushFlow;
            }
            
            maxFlow += pushFlow;
        }
		long end = System.currentTimeMillis();
        System.out.println("Max Flow: " + maxFlow);
		System.out.println("Execution time: " + (end - start));
    }
    
}

class Graph 
{
    public Node[] matrix;
    public int numNodes;
    public int source;
    public int sink;

    Graph()
    {
        Scanner fpScanner = new Scanner(System.in);
		
		System.out.print("Please enter your graph file name: ");
		String filename = fpScanner.nextLine();
		Scanner scan;
		try
		{
			scan = new Scanner(new File(filename));
			fpScanner.close();
		}
		catch (Exception e)
		{
			System.out.println("Looks like we couldn't find your file. Make sure it is in your current directory and run this program again.");
			return;
		}
		
		this.numNodes = scan.nextInt();
		this.source = 0;
		this.sink = numNodes - 1;
		this.matrix = new Node[numNodes];

		// Initialize each node
		for (int i = 0; i < numNodes; i++)
			matrix[i] = new Node();

		// No need for edge count, just number of nodes
		// Initialize each edge
		while (scan.hasNext()) 
		{
			int u = scan.nextInt();
			int v = scan.nextInt();
			int c = scan.nextInt();
			
			// Note edge "b" is not actually in the input matrix
			// It is a construct that allows us to solve the problem
			Edge a = new Edge(u , v , 0, c);
			// Edge b = new Edge(v , u , 0, 0);
			
			// Set pointer from each edge "a" to
			// its reverse edge "b" and vice versa
			// a.setReverse(b);
			// b.setReverse(a);
			
			matrix[u].edges.add(a);
			// matrix[v].edges.add(b);
		}
        scan.close();
    }

    public Edge[] getAugPath()
    {
        Edge[] path = new Edge[numNodes];

        Queue<Node> q = new ArrayDeque<>();
        q.add(matrix[source]);

        while (!q.isEmpty())
        {
            Node curr = q.remove();

            for (Edge e : curr.edges)
            {
                if (path[e.v] == null && e.v != source && e.capacity > e.flow)
                {
                    // Add Edge to augmenting path
                    path[e.v] = e;
                    // Add connected Node to queue
                    q.add(matrix[e.v]);
                }
            }
        }

        return path;
    }
}

class Node
{
	// List of edges also includes reverse edges that
	// are not in original given graph (for push-back flow)
	List<Edge> edges = new ArrayList<>();

}

class Edge
{
	
	int u, v, flow, capacity;
	Edge reverse;

	public Edge(int u, int v, int flow, int capacity)
	{
		this.u = u;
		this.v = v;
		this.flow = flow;
		this.capacity = capacity;
	}

	public void setReverse(Edge e)
	{
		reverse = e;
	}

}