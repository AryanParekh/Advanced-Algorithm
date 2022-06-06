import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class FordFulkerson{
    int n;
    int parent[];
    int graph_main[][];
    
    public FordFulkerson(int n, int graph_main[][]){
        this.n = n;
        this.graph_main = graph_main;
        this.parent = new int[n];
    }
    public boolean bfs(int source, int destination, int graph[][]){
        boolean visited[] = new boolean[n];
        Queue<Integer> queue = new LinkedList<Integer>();
        for(int i=0;i<n;i++){
            visited[i]=false;
            parent[i]=-1;
        }
        queue.add(source);
        visited[source]=true;
        while(!queue.isEmpty()){
            int current = queue.remove();
            for(int i=0;i<n;i++){
                if(graph[current][i]>0 && !visited[i]){
                    queue.add(i);
                    parent[i]=current;
                    visited[i]=true;
                }
            }
        }
        return visited[destination];
    }
    public int fordFulkerson(int source, int sink){
        int max_flow = 0;
        int residual_graph[][] = new int[n][n];
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                residual_graph[i][j] = graph_main[i][j];
            }
        }
        while(bfs(source,sink,residual_graph)){
            int min_flow = Integer.MAX_VALUE;
            for(int s=sink;s!=source;s=parent[s]){
                int p = parent[s];
                min_flow = Math.min(min_flow,residual_graph[p][s]);
            }
            for(int s=sink;s!=source;s=parent[s]){
                int p = parent[s];
                residual_graph[p][s]-=min_flow;
                residual_graph[s][p]+=min_flow;
            }
            max_flow += min_flow;
        }
        return max_flow;
    }
    
    public static void main(String args[]){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter no. of nodes: ");
        int n = sc.nextInt();
        // int graph = new int[n][n];
        // for(int i=0;i<n;i++){
        //     for(int j=0;j<n;j++){
        //         graph[i][j] = sc.nextInt();
        //     }
        // }
        int graph[][] = {
            {0, 16, 13, 0, 0, 0},
            {0, 0,  10, 12, 0, 0},
            {0, 4, 0, 0, 14, 0},
            {0, 0, 9, 0, 0, 20},
            {0, 0, 0, 7, 0, 4},
            {0, 0, 0, 0, 0, 0},
        };
        System.out.println("Label nodes from 0 to "+(n-1));
        System.out.print("Enter source node: ");
        int source = sc.nextInt();
        System.out.print("Enter sink node: ");
        int sink = sc.nextInt();
        
        FordFulkerson ff = new FordFulkerson(n,graph);
        int max_flow = ff.fordFulkerson(source, sink);
        System.out.print("Maximum Flow: "+max_flow);
    }
}