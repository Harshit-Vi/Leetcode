import java.util.*;

class Solution {
    public int minimumDistance(int[] nums) {
        Map<Integer, List<Integer>> map = new HashMap<>();

        // Store positions of every value
        for (int i = 0; i < nums.length; i++) {
            map.computeIfAbsent(nums[i], x -> new ArrayList<>()).add(i);
        }

        int ans = Integer.MAX_VALUE;

        // Check every three consecutive occurrences
        for (List<Integer> positions : map.values()) {
            for (int i = 0; i + 2 < positions.size(); i++) {
                int first = positions.get(i);
                int third = positions.get(i + 2);

                int distance = 2 * (third - first);
                ans = Math.min(ans, distance);
            }
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}