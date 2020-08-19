import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class WeightedGraph {
    static class Edge {
        int source;
		int destination;
        double weight;

        public Edge(int source, int destination, double currencies) {
            this.source = source;
            this.destination = destination;
            this.weight = currencies;
        }
        
        
    }

    static class Graph {
        int vertices;
        LinkedList<Edge> [] adjacencylist;

        Graph(int vertices) {
            this.vertices = vertices;
            adjacencylist = new LinkedList[vertices];
            //initialize adjacency lists for all the vertices
            for (int i = 0; i <vertices ; i++) {
                adjacencylist[i] = new LinkedList<>();
            }
        }

        public void addEgde(int source, int destination, double currencies) {
            Edge edge = new Edge(source, destination, currencies);
            adjacencylist[source].addFirst(edge); //for directed graph
        }
        
        
    }
    
    static double[] BellmanFord(Graph graph, int src) 
    { 
        int V = graph.vertices, E = graph.adjacencylist.length; 
        double dist[] = new double[V]; 
  
        for (int i = 0; i < V; ++i) 
            dist[i] = Integer.MAX_VALUE; 
        dist[src] = 0; 
  
        for (int i = 1; i < V; ++i) { 
            for (int j = 0; j < E; ++j) { 
                int u = graph.adjacencylist[i].get(j).source; 
                int v = graph.adjacencylist[i].get(j).destination; 
                double weight = graph.adjacencylist[i].get(j).weight; 
                if (dist[u] != Integer.MAX_VALUE && dist[u] + weight < dist[v]) 
                    dist[v] = dist[u] + weight; 
            } 
        } 
        return dist;
    }
    
    public static void main(String[] args) throws IOException {
    	double[][] currencies = new double[54][54];
		List<List<String>> records = new ArrayList<>();

		try (BufferedReader br = new BufferedReader
				(new FileReader("src/exchange rates.csv"))) {
			String line;
		    while ((line = br.readLine()) != null) {
		        String[] values = line.split(",", 0);
		        records.add(Arrays.asList(values));
		    }
		}
		
		for (int i = 1; i <= currencies.length; i++) {
			for (int j = 1; j <= currencies.length; j++) {
				currencies[i-1][j-1]=Double.parseDouble(records.get(i).get(j));
			}
		}
		
        int vertices = 54;
        Graph graph = new Graph(vertices);
        
        
        for(int i = 0; i < 54; i++)
        {
        	for(int j = 0; j < 54; j++)
        	{
        			graph.addEgde(i, j, -Math.log(currencies[j][i]));
        	}
        }
        
        Scanner s = new Scanner(System.in);
        System.out.println("Insert the currency:");
        String input = s.nextLine();

        int index = -1;
        
        
        for (int i = 1; i <= vertices; i++) {
			if(records.get(0).get(i).equals(input))
			{
				System.out.println("Source is: " + records.get(0).get(i));

				index = i-1;
				break;
			}
		}
        for (int i = 0; i < currencies.length; i++) {
			System.out.println(records.get(0).get(i+1) + 
					": max Exchange rate is " + Math.pow(Math.E, 
							BellmanFord(graph, index)[i])+
					" and direct rate is " +  currencies[index][i]);

		}

    }
}