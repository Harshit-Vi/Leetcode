class Solution {
public:
    string lexGreaterPermutation(string s, string target) {
        int n = s.size();
        vector<int> cnt(26, 0);

        for (char c : s) {
            cnt[c - 'a']++;
        }

        string ans;

        // First try to match target exactly for as long as possible.
        for (int i = 0; i < n; ++i) {
            int c = target[i] - 'a';

            if (cnt[c] > 0) {
                ans.push_back(target[i]);
                cnt[c]--;
            } else {
                // Cannot match target[i].
                // Try putting something larger here.
                for (int x = c + 1; x < 26; ++x) {
                    if (cnt[x] > 0) {
                        ans.push_back('a' + x);
                        cnt[x]--;

                        appendSmallest(ans, cnt);
                        return ans;
                    }
                }

                // Nothing larger works here, so backtrack.
                while (!ans.empty()) {
                    char old = ans.back();
                    ans.pop_back();

                    cnt[old - 'a']++;

                    int need = target[ans.size()] - 'a';

                    for (int x = need + 1; x < 26; ++x) {
                        if (cnt[x] > 0) {
                            ans.push_back('a' + x);
                            cnt[x]--;

                            appendSmallest(ans, cnt);
                            return ans;
                        }
                    }
                }

                return "";
            }
        }

        // We formed exactly target, but answer must be strictly greater.
        // Backtrack to find the rightmost position we can increase.
        while (!ans.empty()) {
            char old = ans.back();
            ans.pop_back();

            cnt[old - 'a']++;

            int need = target[ans.size()] - 'a';

            for (int x = need + 1; x < 26; ++x) {
                if (cnt[x] > 0) {
                    ans.push_back('a' + x);
                    cnt[x]--;

                    appendSmallest(ans, cnt);
                    return ans;
                }
            }
        }

        return "";
    }

private:
    void appendSmallest(string& ans, vector<int>& cnt) {
        for (int c = 0; c < 26; ++c) {
            while (cnt[c] > 0) {
                ans.push_back('a' + c);
                cnt[c]--;
            }
        }
    }
};
