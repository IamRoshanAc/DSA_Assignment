import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Q_N_2_b {
    private int[] parents;
    private int[] depth;
    private List<Integer>[] child;
    private int[] subtreeSize;
    private int[] serviceCenters;
    private int n;

    public int minServiceCenters(int n, List<Integer>[] children) {
        this.n = n;
        this.child = children;
        parents = new int[n];
        depth = new int[n];
        subtreeSize = new int[n];
        serviceCenters = new int[n];
        Arrays.fill(parents, -1);
        Arrays.fill(serviceCenters, -1);
        dfs1(0, -1);
        return dfs2(0, -1);
    }

    private void dfs1(int node, int parent) {
        parents[node] = parent;
        depth[node] = parent == -1 ? 0 : depth[parent] + 1;
        subtreeSize[node] = 1;
        for (int child : child[node]) {
            dfs1(child, node);
            subtreeSize[node] += subtreeSize[child];
        }
    }

    private int dfs2(int node, int parent) {
        if (serviceCenters[node] != -1) return serviceCenters[node];
        int count = 0;
        for (int child : child[node]) {
            count += dfs2(child, node);
        }
        int option1 = count;
        int option2 = n - subtreeSize[node];
        serviceCenters[node] = Math.min(option1, option2) + 1;
        return serviceCenters[node];
    }

    public static void main(String[] args) {
        List<Integer>[] children = new List[5];
        for (int i = 0; i < 5; i++) {
            children[i] = new ArrayList<>();
        }
        children[0].add(1);
        children[0].add(2);
        children[0].add(3);
        Q_N_2_b sc = new Q_N_2_b();
        System.out.println(sc.minServiceCenters(5, children));
    }
}