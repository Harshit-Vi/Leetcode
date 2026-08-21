import java.util.*;

class Solution {
    public long findKthSmallest(int[] coins, int k) {

        // Remove duplicate coins
        Arrays.sort(coins);

        List<Integer> filtered = new ArrayList<>();

        for (int coin : coins) {
            boolean redundant = false;

            for (int x : filtered) {
                if (coin % x == 0) {
                    redundant = true;
                    break;
                }
            }

            if (!redundant) {
                filtered.add(coin);
            }
        }

        int n = filtered.size();

        long[] arr = new long[n];
        for (int i = 0; i < n; i++) {
            arr[i] = filtered.get(i);
        }

        // Count how many distinct valid amounts are <= x
        long left = 1;
        long right = arr[0] * k;

        while (left < right) {
            long mid = left + (right - left) / 2;

            if (count(mid, arr, n) >= k) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    private long count(long x, long[] coins, int n) {

        long total = 0;

        // Inclusion-Exclusion
        for (int mask = 1; mask < (1 << n); mask++) {

            long lcm = 1;
            int bits = 0;
            boolean valid = true;

            for (int i = 0; i < n; i++) {

                if ((mask & (1 << i)) != 0) {

                    bits++;

                    lcm = lcm(lcm, coins[i]);

                    // No multiple of lcm can be <= x
                    if (lcm > x) {
                        valid = false;
                        break;
                    }
                }
            }

            if (!valid) {
                continue;
            }

            long multiples = x / lcm;

            if (bits % 2 == 1) {
                total += multiples;
            } else {
                total -= multiples;
            }
        }

        return total;
    }

    private long gcd(long a, long b) {

        while (b != 0) {
            long temp = a % b;
            a = b;
            b = temp;
        }

        return a;
    }

    private long lcm(long a, long b) {

        long g = gcd(a, b);

        // Prevent unnecessary overflow
        if (a > Long.MAX_VALUE / (b / g)) {
            return Long.MAX_VALUE;
        }

        return a / g * b;
    }
}