class Solution {
public:
    vector<int> lexicographicallySmallestArray(vector<int>& nums, int limit) {
        int n = nums.size();

        vector<pair<int, int>> arr;

        // Store {value, original index}
        for (int i = 0; i < n; i++) {
            arr.push_back({nums[i], i});
        }

        // Sort by value
        sort(arr.begin(), arr.end());

        vector<int> ans = nums;

        int i = 0;

        while (i < n) {
            int j = i;

            // Find the complete connected group
            while (j + 1 < n &&
                   arr[j + 1].first - arr[j].first <= limit) {
                j++;
            }

            // Get indices from this group
            vector<int> indices;

            for (int k = i; k <= j; k++) {
                indices.push_back(arr[k].second);
            }

            // Indices need to be processed from left to right
            sort(indices.begin(), indices.end());

            // Values are already sorted because arr is sorted
            for (int k = 0; k < indices.size(); k++) {
                ans[indices[k]] = arr[i + k].first;
            }

            i = j + 1;
        }

        return ans;
    }
};