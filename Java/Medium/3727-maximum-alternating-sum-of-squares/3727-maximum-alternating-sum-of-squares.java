
import java.util.Arrays;

class Solution {
    public long maxAlternatingSum(int[] nums) {
        int n = nums.length;
        long[] arr = new long[n];

        for (int i = 0; i < n; i++) {
            arr[i] = Math.abs((long) nums[i]);
        }

        Arrays.sort(arr);

        long ans = 0;

        for (int i = 0; i < n; i++) {
            long square = arr[i] * arr[i];

            if (i < n / 2) {
                ans -= square;
            } else {
                ans += square;
            }
        }

        return ans;
    }
}
