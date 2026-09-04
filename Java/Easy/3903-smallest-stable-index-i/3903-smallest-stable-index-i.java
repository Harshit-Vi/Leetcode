class Solution {
    public int firstStableIndex(int[] nums, int k) {
        int n = nums.length;

        int[] suffixMin = new int[n];

        // Find minimum from i to n-1
        suffixMin[n - 1] = nums[n - 1];

        for (int i = n - 2; i >= 0; i--) {
            suffixMin[i] = Math.min(nums[i], suffixMin[i + 1]);
        }

        // Find maximum from 0 to i
        int prefixMax = nums[0];

        for (int i = 0; i < n; i++) {
            prefixMax = Math.max(prefixMax, nums[i]);

            // Instability score
            int instability = prefixMax - suffixMin[i];

            if (instability <= k) {
                return i;
            }
        }

        return -1;
    }
}