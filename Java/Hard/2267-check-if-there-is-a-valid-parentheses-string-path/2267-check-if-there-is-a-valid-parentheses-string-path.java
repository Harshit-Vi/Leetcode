class Solution {
    public boolean hasValidPath(char[][] grid) {

        int m = grid.length;
        int n = grid[0].length;

        // Total characters in the path
        int length = m + n - 1;

        // A valid parentheses string must have even length
        if (length % 2 == 1) {
            return false;
        }

        // Maximum possible balance is length
        boolean[][][] dp = new boolean[m][n][length + 1];

        // Starting cell must be '('
        if (grid[0][0] == ')') {
            return false;
        }

        dp[0][0][1] = true;

        for (int i = 0; i < m; i++) {

            for (int j = 0; j < n; j++) {

                for (int balance = 0; balance <= length; balance++) {

                    if (!dp[i][j][balance]) {
                        continue;
                    }

                    // Move down
                    if (i + 1 < m) {

                        int newBalance;

                        if (grid[i + 1][j] == '(') {
                            newBalance = balance + 1;
                        } else {
                            newBalance = balance - 1;
                        }

                        // Balance cannot be negative
                        if (newBalance >= 0) {
                            dp[i + 1][j][newBalance] = true;
                        }
                    }

                    // Move right
                    if (j + 1 < n) {

                        int newBalance;

                        if (grid[i][j + 1] == '(') {
                            newBalance = balance + 1;
                        } else {
                            newBalance = balance - 1;
                        }

                        // Balance cannot be negative
                        if (newBalance >= 0) {
                            dp[i][j + 1][newBalance] = true;
                        }
                    }
                }
            }
        }

        return dp[m - 1][n - 1][0];
    }
}