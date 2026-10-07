class Solution {

    public List<String> removeInvalidParentheses(String s) {
        List<String> ans = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        queue.offer(s);
        visited.add(s);

        boolean found = false;

        while (!queue.isEmpty()) {

            String current = queue.poll();

            if (isValid(current)) {
                ans.add(current);
                found = true;
            }

            // If valid strings were found at this level,
            // don't remove any more characters.
            if (found) {
                continue;
            }

            for (int i = 0; i < current.length(); i++) {

                // Only remove parentheses
                if (current.charAt(i) != '(' &&
                    current.charAt(i) != ')') {
                    continue;
                }

                String next = current.substring(0, i)
                           + current.substring(i + 1);

                if (!visited.contains(next)) {
                    visited.add(next);
                    queue.offer(next);
                }
            }
        }

        return ans;
    }

    private boolean isValid(String s) {
        int count = 0;

        for (char c : s.toCharArray()) {

            if (c == '(') {
                count++;
            }
            else if (c == ')') {
                count--;

                if (count < 0) {
                    return false;
                }
            }
        }

        return count == 0;
    }
}