import java.util.ArrayList;
import java.util.List;

public class Q_N_3_a {
    public static void main(String[] args) {
        int[] nums = {5, 2, 4, 11};
        int n = nums.length;
        int half = n / 2;

        // generate all possible subsets of the first half
        List<List<Integer>> subsets = new ArrayList<>();
        for (int i = 0; i < (1 << half); i++) {
            List<Integer> subset = new ArrayList<>();
            for (int j = 0; j < half; j++) {
                if ((i & (1 << j)) > 0) {
                    subset.add(nums[j]);
                }
            }
            subsets.add(subset);
        }

        // compute the minimum product difference
        int minDiff = Integer.MAX_VALUE;
        for (List<Integer> subset : subsets) {
            List<Integer> complement = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if (!subset.contains(nums[i])) {
                    complement.add(nums[i]);
                }
            }
            int prod1 = computeProduct(subset);
            int prod2 = computeProduct(complement);
            int diff = Math.abs(prod1 - prod2);
            minDiff = Math.min(minDiff, diff);
        }

        // output the result
        System.out.println(minDiff);
    }

    private static int computeProduct(List<Integer> nums) {
        int product = 1;
        for (int num : nums) {
            product *= num;
        }
        return product;
    }
}