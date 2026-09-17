import java.util.*;

class Solution {
    public int minSumOfLengths(int[] arr, int target) {

        int n = arr.length;
        int INF = 1000000;

        int[] best = new int[n];

        Arrays.fill(best, INF);

        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);

        int prefix = 0;
        int answer = INF;

        for (int i = 0; i < n; i++) {

            prefix += arr[i];

            // Carry previous best
            if (i > 0) {
                best[i] = best[i - 1];
            }

            // Need prefix[j] = prefix[i] - target
            if (map.containsKey(prefix - target)) {

                int j = map.get(prefix - target);
                int length = i - j;

                // Combine with a subarray ending before j
                if (j >= 0 && best[j] != INF) {
                    answer = Math.min(answer, length + best[j]);
                }

                // Current subarray can become the best one
                best[i] = Math.min(best[i], length);
            }

            map.put(prefix, i);
        }

        return answer == INF ? -1 : answer;
    }
}