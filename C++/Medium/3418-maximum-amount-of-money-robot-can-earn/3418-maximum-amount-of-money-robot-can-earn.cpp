class Solution {
public:
    int m, n;
    vector<vector<vector<int>>> dp;

    int dfs(int i, int j, int k, vector<vector<int>>& coins) {
        // Outside the grid
        if (i >= m || j >= n)
            return INT_MIN / 2;

        // Destination
        if (i == m - 1 && j == n - 1) {
            // If we still have a neutralization,
            // we can choose between taking the coin
            // or neutralizing a negative value.
            if (k > 0)
                return max(coins[i][j], 0);

            return coins[i][j];
        }

        if (dp[i][j][k] != INT_MIN)
            return dp[i][j][k];

        // Don't neutralize current cell
        int ans = coins[i][j] +
                  max(
                      dfs(i + 1, j, k, coins),
                      dfs(i, j + 1, k, coins)
                  );

        // Neutralize current robber
        if (coins[i][j] < 0 && k > 0) {
            ans = max(
                ans,
                max(
                    dfs(i + 1, j, k - 1, coins),
                    dfs(i, j + 1, k - 1, coins)
                )
            );
        }

        return dp[i][j][k] = ans;
    }

    int maximumAmount(vector<vector<int>>& coins) {
        m = coins.size();
        n = coins[0].size();

        dp.assign(
            m,
            vector<vector<int>>(
                n,
                vector<int>(3, INT_MIN)
            )
        );

        return dfs(0, 0, 2, coins);
    }
};