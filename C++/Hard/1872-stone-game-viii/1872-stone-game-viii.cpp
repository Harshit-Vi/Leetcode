class Solution {
public:
    int stoneGameVIII(vector<int>& stones) {
        int n = stones.size();

        // Convert stones into prefix sums
        for (int i = 1; i < n; i++) {
            stones[i] += stones[i - 1];
        }

        // If Alice takes all stones in the first move
        int ans = stones[n - 1];

        // Try all possible positions for the first move
        for (int i = n - 2; i > 0; i--) {
            ans = max(ans, stones[i] - ans);
        }

        return ans;
    }
};