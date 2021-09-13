package graphs;

import java.util.LinkedList;

/*Name: Maria Jane Wehbe
* Last Modification: Apr 20, 2021
*/

public class Graph {
	
	private int V;
	private int[] vertexList;
	private int E;
	private LinkedList<Integer>[] edgeList;
	private int counter;
	
	public Graph(int V)
	{
		this.V = V;
		E = 0;
		vertexList = new int[10];
		edgeList = (LinkedList<Integer>[]) new LinkedList[10];
		counter = 0;
	}
	
	public void addVertex(int V)
	{
		if(counter == vertexList.length)
			increaseSize();
		vertexList[counter] = V;
		edgeList[counter] = new LinkedList<Integer>();
		counter++;
	}
	
	public void increaseSize()
	{
		int [] tempV = new int[vertexList.length * 2 + 1];
		LinkedList<Integer>[] tempE = new LinkedList[edgeList.length * 2 +1];
		for(int i=0; i<vertexList.length; i++)
		{
			tempV[i] = vertexList[i];
		}
		vertexList = tempV;
		
		for(int i=0; i<edgeList.length; i++)
		{
			tempE[i] = edgeList[i];
		}
		edgeList = tempE;
		
	}

	public int getV() {
		return V;
	}

	public void setV(int v) {
		V = v;
	}

	public int getE() {
		return E;
	}

	public void setE(int e) {
		E = e;
	}

	public boolean exists(int a, int b)
	{
		//checks to see if an edge exists between a and b
		if(edgeList[a].contains(b) && edgeList[b].contains(a))
			return true;
		else
			return false;
	}
	
	public void addEdge(int a, int b)
	{
		int aID = getVertexIndex(a);
		int bID = getVertexIndex(b);
		
		if(aID == -99)
		{
			System.out.println("Vertex " + a + " does not exist.");
			return;
		}
		if(bID == -99)
		{
			System.out.println("Vertex " + b + " does not exist.");
			return;
		}
		if(exists(a ,b))
		{
			System.out.println("An edge already exists");
			return;
		}
		edgeList[aID].addFirst(b);
		edgeList[bID].addFirst(a);
	}
	
	public int getVertexIndex(int v)
	{
		for(int i=0; i<counter; i++)
		{
			if(vertexList[i] == v)
				return i;
		}
		return -99;
	}
	
	public void deleteEdge(int a, int b)
	{
		int aID = getVertexIndex(a);
		int bID = getVertexIndex(b);
		if(aID == -99)
		{
			System.out.println("Vertex " + a + " does not exist.");
			return;
		}
		if(bID == -99)
		{
			System.out.println("Vertex " + b + " does not exist.");
			return;
		}
		if(!exists(a, b))
		{
			System.out.println("There are no edges between those two vertices.");
			return;
		}
		edgeList[aID].remove(b);
		edgeList[bID].remove(a);
	}
	
	//Question 2
	/*We start by a vertex v in the graph, then we mark it as visited.
	 * Then we check whether it is colored or not, if not, we color it as 1
	 * Then the graph is traversed by DFS where we put the color of the neighbor as 1 if v is 0 or as 0 if v is 1
	 * After the DFS traversal, we check if the graph violates the colorability rules.
	 * It does not violate it if for every vertex of a certain color, all of its neighbors are the opposite color.
	 * */
	public void DFSColor(int v, boolean[] visited, int[] colored)
	{
		int index = getVertexIndex(v);
		if(visited[index] == true)
			return;
		else
		{
			 if(colored[index] != 0 && colored[index] != 1)
				 colored[index] = 1;
		}
		visited[index] = true;
		for(int i=0; i<edgeList[v].size(); i++)
		{
			int n = edgeList[v].get(i);
			int ind = getVertexIndex(n);
			if(visited[ind] == false)
			{
				if(colored[index] == 1)
					colored[ind] = 0;
				else if(colored[index] == 0)
					colored[ind] = 1;
				DFSColor(n, visited, colored);
			}
		}
	}

	public void DFSColorability(int v)
	{
		boolean[] visited = new boolean[counter];
		int[] colored = new int[counter]; //0s and 1s being the colors
		DFSColor(v, visited, colored);
		if(validateColorability(colored) == true)
			System.out.println("There is no coloring violation in the graph.");
		else
			System.out.println("There is a coloring violation in the graph");
	}
	
	public boolean isAll1(int[] colored, LinkedList<Integer> l)
	{
		for(int i=0; i<l.size(); i++)
		{
			int n = l.get(i);
			int ind = getVertexIndex(n);
			if(colored[ind] == 0)
				return false;
		}
		return true;
	}
	
	public boolean isAll0(int[] colored, LinkedList<Integer> l)
	{
		for(int i=0; i<l.size(); i++)
		{
			int n = l.get(i);
			int ind = getVertexIndex(n);
			if(colored[ind] == 1)
				return false;
		}
		return true;
	}
	
	public boolean validateColorability(int[] colored)
	{
		for(int i=0; i<counter; i++)
		{
			if(edgeList[i].size() != 0)
			{
				if(colored[i] == 1)
				{
					if(isAll0(colored, edgeList[i]) == false)
					{
						return false;
					}
				}
				else
				{
					if(isAll1(colored, edgeList[i]) == false)
					{
						return false;
					}
				}
			}
				
		}
		return true;
	}

	//Question 1
	public void DFS(int v, boolean[] visited, int max)
    {
		int index = getVertexIndex(v);
		if(visited[index] == true)
			return;
		else
		{
			visited[index] = true;
			System.out.print(v + " ");
			max++;
		}

        for(int i=0; i<edgeList[v].size(); i++)
		{
			int n = edgeList[v].get(i);
			int ind = getVertexIndex(n);
			if(visited[ind] == false)
			{
				DFS(n, visited, max);
			}
		}
    }
    public void connectedComponents()
    {
    	int n = 0;
    	int max = 0;
        boolean[] visited = new boolean[counter];
        for (int i = 0; i < counter; i++) 
        {
            if (!visited[i]) {
                DFS(i, visited, n);
                if(n > max)
                	max = n;
                System.out.println();
            }
        }
        System.out.println("The largest component is of size: " + max);
    }
	
}
