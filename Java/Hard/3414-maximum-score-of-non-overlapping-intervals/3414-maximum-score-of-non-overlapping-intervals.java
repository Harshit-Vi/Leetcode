class Solution {
    public int[] maximumWeight(List<List<Integer>> intervals) {
        int n = intervals.size();
        Integer[] idx = new Integer[n];
        for (int i = 0; i < n; i++) idx[i] = i;
        // sort by right endpoint
        Arrays.sort(idx, (a, b) -> intervals.get(a).get(1) - intervals.get(b).get(1));

        int[] r = new int[n];
        int[] l = new int[n];
        int[] origIndex = new int[n];
        for (int i = 0; i < n; i++) {
            l[i] = intervals.get(idx[i]).get(0);
            r[i] = intervals.get(idx[i]).get(1);
            origIndex[i] = idx[i];
        }
        long[] w = new long[n];
        for (int i = 0; i < n; i++) w[i] = intervals.get(idx[i]).get(2);

        // p[i] = largest index j < i such that r[j] < l[i] (binary search since r sorted)
        int[] p = new int[n];
        for (int i = 0; i < n; i++) {
            int lo = 0, hi = i - 1, res = -1;
            while (lo <= hi) {
                int mid = (lo + hi) / 2;
                if (r[mid] < l[i]) { res = mid; lo = mid + 1; }
                else hi = mid - 1;
            }
            p[i] = res;
        }

        // dp[i][k] = best (score, lexicographically smallest list of original indices) 
        // using intervals among first i (0..i-1 in sorted order), choosing at most k intervals
        long[][] dp = new long[n + 1][5];
        // store choice: dp[i][k] built from dp[i-1][k] (skip i-1) or dp[p[i-1]+1][k-1] + i-1 (take i-1)
        // We'll reconstruct via parent pointers and use lists for lexicographic comparison.

        List<Integer>[][] choice = new List[n + 1][5];
        for (int k = 0; k <= 4; k++) {
            dp[0][k] = 0;
            choice[0][k] = new ArrayList<>();
        }
        for (int i = 1; i <= n; i++) {
            for (int k = 0; k <= 4; k++) {
                // option 1: don't take interval i-1
                long bestScore = dp[i - 1][k];
                List<Integer> bestList = choice[i - 1][k];

                // option 2: take interval i-1 (if k >= 1)
                if (k >= 1) {
                    int prevIdx = p[i - 1] + 1; // dp index (0-based dp array uses i meaning first i intervals)
                    long candScore = dp[prevIdx][k - 1] + w[i - 1];
                    List<Integer> candList = new ArrayList<>(choice[prevIdx][k - 1]);
                    candList.add(origIndex[i - 1]);

                    if (candScore > bestScore) {
                        bestScore = candScore;
                        bestList = candList;
                    } else if (candScore == bestScore) {
                        List<Integer> sortedCand = new ArrayList<>(candList);
                        Collections.sort(sortedCand);
                        List<Integer> sortedBest = new ArrayList<>(bestList);
                        Collections.sort(sortedBest);
                        if (isLexSmaller(sortedCand, sortedBest)) {
                            bestScore = candScore;
                            bestList = candList;
                        }
                    }
                }
                dp[i][k] = bestScore;
                choice[i][k] = bestList;
            }
        }

        List<Integer> result = new ArrayList<>(choice[n][4]);
        Collections.sort(result);
        int[] ans = new int[result.size()];
        for (int i = 0; i < ans.length; i++) ans[i] = result.get(i);
        return ans;
    }

    private boolean isLexSmaller(List<Integer> a, List<Integer> b) {
        int n = Math.max(a.size(), b.size());
        for (int i = 0; i < n; i++) {
            if (i >= a.size()) return true;
            if (i >= b.size()) return false;
            if (!a.get(i).equals(b.get(i))) return a.get(i) < b.get(i);
        }
        return false;
    }
}