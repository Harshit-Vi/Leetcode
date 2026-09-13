class Solution {
    public int largestOverlap(int[][] img1, int[][] img2) {
        int n = img1.length;
        int[] a = new int[n];
        int[] b = new int[n];

        // Convert every row into a bitmask
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                a[i] |= img1[i][j] << j;
                b[i] |= img2[i][j] << j;
            }
        }

        int ans = 0;

        // Try every vertical translation
        for (int dy = -(n - 1); dy <= n - 1; dy++) {

            int start1 = Math.max(0, -dy);
            int start2 = Math.max(0, dy);
            int rows = n - Math.abs(dy);

            // Try every horizontal translation
            for (int dx = -(n - 1); dx <= n - 1; dx++) {

                int overlap = 0;

                for (int i = 0; i < rows; i++) {
                    int x = a[start1 + i];
                    int y = b[start2 + i];

                    if (dx > 0) {
                        x >>= dx;
                    } else {
                        x <<= -dx;
                    }

                    overlap += Integer.bitCount(x & y);
                }

                ans = Math.max(ans, overlap);
            }
        }

        return ans;
    }
}