import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;


public class DirectedWeightedGraph {

    float [][] matrix;
    int nodes;

    // TODO: the diagonal elements will always be zero. Hence we could reduce the dimension by one.
    public DirectedWeightedGraph(int nodes) {
        this.nodes = nodes;
        matrix = new float[nodes][nodes];
    }

    public DirectedWeightedGraph(int nodes, float[] entries) {
        this.nodes = nodes;
        this.matrix = new float[nodes][nodes];
        if (entries.length != (nodes - 1) * nodes / 2)
            throw new RuntimeException("Invalid number of entries for this number of nodes.");
        int pos = 0;
        for (int i = 0; i < nodes - 1; i++) {
            for (int j = i + 1; j < nodes; j++) {
                this.matrix[i][j] = entries[pos];
                pos++;
            }
        }
    }

    public DirectedWeightedGraph(int nodes, int edges) {
        this(nodes);
        Random r = new Random();
        for (int i = 0; i < edges; i++) {
            int from = r.nextInt(nodes);
            int to = r.nextInt(nodes);
            float weight = r.nextFloat();
            this.addEdge(from, to, weight);
        }
    }

    // Elements in the matrix with from >= to are always 0
    // edge weights may be positive or negative or zero.
    public void addEdge(int from, int to, float weight) {
        if (from < to) { this.matrix[from][to] += weight; }
        if (to < from) { this.matrix[to][from] -= weight; }
        // do nothing if from == to
    }

    public float get(int from, int to) {
        if (from <= to) return this.matrix[from][to];
        else return this.matrix[to][from] * -1;
    }

    public List<Integer> outgoing(int node) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < nodes; i++) {
            if (this.get(node,i) > 0) result.add(i);
        }
        return result;
    }


}
