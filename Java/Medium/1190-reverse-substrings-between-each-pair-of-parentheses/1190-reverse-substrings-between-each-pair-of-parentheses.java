import java.util.*;

class Solution {
    public String reverseParentheses(String s) {

        Stack<StringBuilder> stack = new Stack<>();

        StringBuilder current = new StringBuilder();

        for (char ch : s.toCharArray()) {

            if (ch == '(') {

                // Save the string before '('
                stack.push(current);

                // Start a new string inside parentheses
                current = new StringBuilder();

            } else if (ch == ')') {

                // Reverse the string inside parentheses
                current.reverse();

                // Get the string before '('
                StringBuilder previous = stack.pop();

                // Attach reversed string
                previous.append(current);

                current = previous;

            } else {

                // Normal character
                current.append(ch);
            }
        }

        return current.toString();
    }
}