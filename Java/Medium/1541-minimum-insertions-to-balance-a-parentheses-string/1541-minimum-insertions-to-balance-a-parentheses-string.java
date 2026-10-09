class Solution {
    public int minInsertions(String s) {
        int open = 0;
        int ans = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == '(') {
                open++;
            } else {
                // If the next character is also ')',
                // we have a pair of closing brackets.
                if (i + 1 < s.length() && s.charAt(i + 1) == ')') {
                    i++;
                } else {
                    // Insert one ')' to complete the pair.
                    ans++;
                }

                if (open > 0) {
                    open--;
                } else {
                    // Insert '(' to match this closing pair.
                    ans++;
                }
            }
        }

        return ans + 2 * open;
    }
}