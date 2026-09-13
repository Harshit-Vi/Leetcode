class Solution {
    public int largestOverlap(int[][] img1, int[][] img2) {
        int n = img1.length;

        List<int[]> ones1 = new ArrayList<>();
        List<int[]> ones2 = new ArrayList<>();

        // Store coordinates of all 1s
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (img1[i][j] == 1) {
                    ones1.add(new int[]{i, j});
                }

                if (img2[i][j] == 1) {
                    ones2.add(new int[]{i, j});
                }
            }
        }

        Map<String, Integer> count = new HashMap<>();
        int ans = 0;

        // Try every possible relative displacement
        for (int[] a : ones1) {
            for (int[] b : ones2) {
                int dr = b[0] - a[0];
                int dc = b[1] - a[1];

                String key = dr + "," + dc;

                int current = count.getOrDefault(key, 0) + 1;
                count.put(key, current);

                ans = Math.max(ans, current);
            }
        }

        return ans;
    }
}