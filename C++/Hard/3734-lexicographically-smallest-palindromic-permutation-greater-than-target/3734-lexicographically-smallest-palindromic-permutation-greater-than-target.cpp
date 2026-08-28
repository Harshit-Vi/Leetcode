class Solution {
public:
    string lexPalindromicPermutation(string s, string target) {
        int n = s.size();
        vector<int> cnt(26, 0);

        for (char c : s)
            cnt[c - 'a']++;

        // A palindrome can exist only if:
        // even length -> every count is even
        // odd length  -> exactly one count is odd
        int odd = 0;
        int mid = -1;

        for (int i = 0; i < 26; i++) {
            if (cnt[i] % 2) {
                odd++;
                mid = i;
            }
        }

        if (odd > 1)
            return "";

        string half;
        vector<int> halfCnt(26);

        for (int i = 0; i < 26; i++) {
            halfCnt[i] = cnt[i] / 2;
        }

        int m = n / 2;

        /*
            Build the lexicographically smallest first half
            that can eventually make the palindrome > target.
        */

        // First try to make the whole palindrome equal to target
        // for as long as possible.
        string prefix;

        for (int i = 0; i < m; i++) {
            int c = target[i] - 'a';

            if (halfCnt[c] > 0) {
                prefix += target[i];
                halfCnt[c]--;
            } else {
                // We cannot match target[i].
                // Try the smallest character greater than target[i].
                for (int x = c + 1; x < 26; x++) {
                    if (halfCnt[x] > 0) {
                        string h = prefix;
                        h += char('a' + x);
                        halfCnt[x]--;

                        appendSmallest(h, halfCnt);
                        return buildPalindrome(h, mid, n);
                    }
                }

                // Nothing larger at this position.
                // Backtrack.
                while (!prefix.empty()) {
                    char last = prefix.back();
                    prefix.pop_back();

                    halfCnt[last - 'a']++;

                    int pos = prefix.size();
                    int need = target[pos] - 'a';

                    for (int x = need + 1; x < 26; x++) {
                        if (halfCnt[x] > 0) {
                            string h = prefix;
                            h += char('a' + x);
                            halfCnt[x]--;

                            appendSmallest(h, halfCnt);
                            return buildPalindrome(h, mid, n);
                        }
                    }
                }

                return "";
            }
        }

        /*
            We successfully matched target's first half.

            But the resulting palindrome might be:
              1. exactly target
              2. smaller than target
              3. already greater than target

            Compare it directly.
        */

        string candidate = buildPalindrome(prefix, mid, n);

        if (candidate > target)
            return candidate;

        /*
            Candidate <= target.
            We need to increase the first half.

            Since the suffix of a palindrome is determined by
            the first half, changing a character in the first half
            is enough.
        */

        while (!prefix.empty()) {
            char last = prefix.back();
            prefix.pop_back();

            halfCnt[last - 'a']++;

            int pos = prefix.size();

            // Try smallest character > target[pos].
            int need = target[pos] - 'a';

            for (int x = need + 1; x < 26; x++) {
                if (halfCnt[x] > 0) {
                    string h = prefix;
                    h += char('a' + x);
                    halfCnt[x]--;

                    appendSmallest(h, halfCnt);

                    return buildPalindrome(h, mid, n);
                }
            }
        }

        return "";
    }

private:
    void appendSmallest(string& h, vector<int>& cnt) {
        for (int c = 0; c < 26; c++) {
            while (cnt[c] > 0) {
                h += char('a' + c);
                cnt[c]--;
            }
        }
    }

    string buildPalindrome(const string& half, int mid, int n) {
        string ans = half;

        if (n % 2)
            ans += char('a' + mid);

        for (int i = (int)half.size() - 1; i >= 0; i--)
            ans += half[i];

        return ans;
    }
};